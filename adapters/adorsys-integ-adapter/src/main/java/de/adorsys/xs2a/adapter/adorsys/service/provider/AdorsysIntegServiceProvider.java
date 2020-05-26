/*
 * Copyright 2018-2018 adorsys GmbH & Co KG
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.adorsys.xs2a.adapter.adorsys.service.provider;

import de.adorsys.xs2a.adapter.adapter.BasePaymentInitiationService;
import de.adorsys.xs2a.adapter.adapter.oauth2.api.BaseOauth2Api;
import de.adorsys.xs2a.adapter.adapter.oauth2.api.model.AuthorisationServerMetaData;
import de.adorsys.xs2a.adapter.adorsys.service.AdorsysAccountInformationService;
import de.adorsys.xs2a.adapter.adorsys.service.AdorsysIntegOauth2Service;
import de.adorsys.xs2a.adapter.http.HttpClient;
import de.adorsys.xs2a.adapter.http.HttpClientFactory;
import de.adorsys.xs2a.adapter.http.Request;
import de.adorsys.xs2a.adapter.http.RequestSigningInterceptor;
import de.adorsys.xs2a.adapter.service.*;
import de.adorsys.xs2a.adapter.service.config.AdapterConfig;
import de.adorsys.xs2a.adapter.service.link.LinksRewriter;
import de.adorsys.xs2a.adapter.service.model.Aspsp;
import de.adorsys.xs2a.adapter.service.provider.AccountInformationServiceProvider;
import de.adorsys.xs2a.adapter.service.provider.PaymentInitiationServiceProvider;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class AdorsysIntegServiceProvider
    implements AccountInformationServiceProvider, PaymentInitiationServiceProvider, Oauth2ServiceFactory {

    private final OauthHeaderInterceptor oauthHeaderInterceptor = new OauthHeaderInterceptor();
    private final boolean requestSigningEnabled =
        Boolean.parseBoolean(AdapterConfig.readProperty("adorsys.request_signing.enabled", "false"));

    @Override
    public PaymentInitiationService getPaymentInitiationService(Aspsp aspsp,
                                                                HttpClientFactory httpClientFactory,
                                                                Pkcs12KeyStore keyStore,
                                                                LinksRewriter linksRewriter) {
        return new BasePaymentInitiationService(aspsp,
            httpClientFactory.getHttpClient(getAdapterId()),
            getInterceptor(keyStore),
            linksRewriter);
    }

    private Request.Builder.Interceptor getInterceptor(Pkcs12KeyStore keyStore) {
        if (requestSigningEnabled) {
            RequestSigningInterceptor requestSigningInterceptor = new RequestSigningInterceptor(keyStore);
            return requestBuilder -> {
                oauthHeaderInterceptor.apply(requestBuilder);
                requestBuilder.header(RequestHeaders.DATE,
                    DateTimeFormatter.RFC_1123_DATE_TIME.format(ZonedDateTime.now()));
                requestSigningInterceptor.apply(requestBuilder);
                String certificate = requestBuilder.headers().get(RequestHeaders.TPP_SIGNATURE_CERTIFICATE);
                requestBuilder.header(RequestHeaders.TPP_SIGNATURE_CERTIFICATE,
                    "-----BEGIN CERTIFICATE-----" + certificate + "-----END CERTIFICATE-----");
                return requestBuilder;
            };
        }
        return oauthHeaderInterceptor;
    }

    @Override
    public AccountInformationService getAccountInformationService(Aspsp aspsp,
                                                                  HttpClientFactory httpClientFactory,
                                                                  Pkcs12KeyStore keyStore,
                                                                  LinksRewriter linksRewriter) {
        return new AdorsysAccountInformationService(aspsp, httpClientFactory.getHttpClient(getAdapterId()),
            getInterceptor(keyStore), linksRewriter);
    }

    @Override
    public Oauth2Service getOauth2Service(Aspsp aspsp, HttpClientFactory httpClientFactory, Pkcs12KeyStore keyStore) {
        HttpClient httpClient = httpClientFactory.getHttpClient(getAdapterId());
        return new AdorsysIntegOauth2Service(aspsp, httpClient,
            new BaseOauth2Api<>(httpClient, AuthorisationServerMetaData.class));
    }

    @Override
    public String getAdapterId() {
        return "adorsys-integ-adapter";
    }
}
