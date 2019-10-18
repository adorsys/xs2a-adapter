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

import de.adorsys.xs2a.adapter.adapter.BaseAccountInformationService;
import de.adorsys.xs2a.adapter.adapter.BasePaymentInitiationService;
import de.adorsys.xs2a.adapter.adorsys.service.AdorsysIntegOauth2Service;
import de.adorsys.xs2a.adapter.http.HttpClientFactory;
import de.adorsys.xs2a.adapter.service.*;
import de.adorsys.xs2a.adapter.service.provider.AccountInformationServiceProvider;
import de.adorsys.xs2a.adapter.service.provider.PaymentInitiationServiceProvider;

public class AdorsysIntegServiceProvider
    implements AccountInformationServiceProvider, PaymentInitiationServiceProvider, Oauth2ServiceFactory {

    private final OauthHeaderInterceptor oauthHeaderInterceptor = new OauthHeaderInterceptor();

    @Override
    public PaymentInitiationService getPaymentInitiationService(String baseUrl, HttpClientFactory httpClientFactory, Pkcs12KeyStore keyStore) {
        return new BasePaymentInitiationService(baseUrl, httpClientFactory.getHttpClient(getAdapterId()), oauthHeaderInterceptor);
    }

    @Override
    public AccountInformationService getAccountInformationService(String baseUrl, HttpClientFactory httpClientFactory, Pkcs12KeyStore keyStore) {
        return new BaseAccountInformationService(baseUrl, httpClientFactory.getHttpClient(getAdapterId()), oauthHeaderInterceptor);
    }

    @Override
    public Oauth2Service getOauth2Service(String baseUrl, HttpClientFactory httpClientFactory, Pkcs12KeyStore keyStore) {
        // a crutch for two distinct hosts for authorization and token endpoints using single field in the config
        String[] urls = baseUrl.split("\\s+");
        if (urls.length > 2) {
            throw new IllegalArgumentException("Maximum of two space separated urls is expected");
        }
        return new AdorsysIntegOauth2Service(urls[0], urls[urls.length - 1], httpClientFactory.getHttpClient(getAdapterId()));
    }

    @Override
    public String getAdapterId() {
        return "adorsys-integ-adapter";
    }
}
