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

package de.adorsys.xs2a.adapter.adorsys;

import de.adorsys.xs2a.adapter.api.*;
import de.adorsys.xs2a.adapter.api.config.AdapterConfig;
import de.adorsys.xs2a.adapter.api.http.HttpClient;
import de.adorsys.xs2a.adapter.api.http.HttpClientFactory;
import de.adorsys.xs2a.adapter.api.http.Request;
import de.adorsys.xs2a.adapter.api.link.LinksRewriter;
import de.adorsys.xs2a.adapter.api.model.Aspsp;
import de.adorsys.xs2a.adapter.impl.BasePaymentInitiationService;
import de.adorsys.xs2a.adapter.impl.http.RequestSigningInterceptor;
import de.adorsys.xs2a.adapter.impl.oauth2.api.BaseOauth2Api;
import de.adorsys.xs2a.adapter.impl.oauth2.api.model.AuthorisationServerMetaData;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class AdorsysIntegServiceProvider
    implements AccountInformationServiceProvider, PaymentInitiationServiceProvider, Oauth2ServiceProvider {

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
            keyStore != null ? getInterceptor(keyStore) : null,
            linksRewriter);
    }

    //todo: https://jira.adorsys.de/browse/XS2AAD-706
    Request.Builder.Interceptor getInterceptor(Pkcs12KeyStore keyStore) {
        if (requestSigningEnabled) {
            RequestSigningInterceptor requestSigningInterceptor = new RequestSigningInterceptor(keyStore);
            return requestBuilder -> {
                oauthHeaderInterceptor.preHandle(requestBuilder);
                requestBuilder.header(RequestHeaders.DATE,
                    DateTimeFormatter.RFC_1123_DATE_TIME.format(ZonedDateTime.now()));
                requestSigningInterceptor.preHandle(requestBuilder);
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
        return "adorsys-adapter";
    }
}
