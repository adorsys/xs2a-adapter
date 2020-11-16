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
import de.adorsys.xs2a.adapter.api.http.HttpClient;
import de.adorsys.xs2a.adapter.api.http.HttpClientFactory;
import de.adorsys.xs2a.adapter.api.http.HttpLogSanitizer;
import de.adorsys.xs2a.adapter.api.http.Interceptor;
import de.adorsys.xs2a.adapter.api.link.LinksRewriter;
import de.adorsys.xs2a.adapter.api.model.Aspsp;
import de.adorsys.xs2a.adapter.impl.BasePaymentInitiationService;
import de.adorsys.xs2a.adapter.impl.oauth2.api.BaseOauth2Api;
import de.adorsys.xs2a.adapter.impl.oauth2.api.model.AuthorisationServerMetaData;

import java.util.ArrayList;
import java.util.List;

public class AdorsysIntegServiceProvider
    implements AccountInformationServiceProvider, PaymentInitiationServiceProvider, Oauth2ServiceProvider {

    private final OauthHeaderInterceptor oauthHeaderInterceptor = new OauthHeaderInterceptor();

    @Override
    public PaymentInitiationService getPaymentInitiationService(Aspsp aspsp,
                                                                HttpClientFactory httpClientFactory,
                                                                Pkcs12KeyStore keyStore,
                                                                LinksRewriter linksRewriter,
                                                                HttpLogSanitizer logSanitizer) {
        return new BasePaymentInitiationService(aspsp,
                                                httpClientFactory.getHttpClient(getAdapterId(), logSanitizer),
                                                getInterceptors(keyStore),
                                                linksRewriter,
                                                logSanitizer);
    }

    private List<Interceptor> getInterceptors(Pkcs12KeyStore keyStore) {
        List<Interceptor> interceptors = new ArrayList<>();
        interceptors.add(oauthHeaderInterceptor);
        if (keyStore != null) {
            interceptors.add(new AdorsysSigningHeadersInterceptor(keyStore));
        }
        return interceptors;
    }

    @Override
    public AccountInformationService getAccountInformationService(Aspsp aspsp,
                                                                  HttpClientFactory httpClientFactory,
                                                                  Pkcs12KeyStore keyStore,
                                                                  LinksRewriter linksRewriter,
                                                                  HttpLogSanitizer logSanitizer) {
        return new AdorsysAccountInformationService(aspsp,
                                                    httpClientFactory.getHttpClient(getAdapterId(), logSanitizer),
                                                    getInterceptors(keyStore),
                                                    linksRewriter,
                                                    logSanitizer);
    }

    @Override
    public Oauth2Service getOauth2Service(Aspsp aspsp, HttpClientFactory httpClientFactory, Pkcs12KeyStore keyStore, HttpLogSanitizer logSanitizer) {
        HttpClient httpClient = httpClientFactory.getHttpClient(getAdapterId(), logSanitizer);
        return new AdorsysIntegOauth2Service(aspsp, httpClient,
                                             new BaseOauth2Api<>(httpClient, AuthorisationServerMetaData.class));
    }

    @Override
    public String getAdapterId() {
        return "adorsys-adapter";
    }
}
