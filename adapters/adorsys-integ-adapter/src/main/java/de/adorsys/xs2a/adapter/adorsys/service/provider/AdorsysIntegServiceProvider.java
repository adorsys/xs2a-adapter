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
import de.adorsys.xs2a.adapter.adorsys.service.AdorsysAccountInformationService;
import de.adorsys.xs2a.adapter.adorsys.service.AdorsysIntegOauth2Service;
import de.adorsys.xs2a.adapter.adorsys.service.api.Oauth2Api;
import de.adorsys.xs2a.adapter.http.HttpClient;
import de.adorsys.xs2a.adapter.http.HttpClientFactory;
import de.adorsys.xs2a.adapter.service.*;
import de.adorsys.xs2a.adapter.service.model.Aspsp;
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
    public AccountInformationService getAccountInformationService(Aspsp aspsp, HttpClientFactory httpClientFactory, Pkcs12KeyStore keyStore) {
        return new AdorsysAccountInformationService(aspsp, httpClientFactory.getHttpClient(getAdapterId()), oauthHeaderInterceptor);
    }

    @Override
    public Oauth2Service getOauth2Service(Aspsp aspsp, HttpClientFactory httpClientFactory, Pkcs12KeyStore keyStore) {
        HttpClient httpClient = httpClientFactory.getHttpClient(getAdapterId());
        return new AdorsysIntegOauth2Service(aspsp, httpClient, new Oauth2Api(httpClient));
    }

    @Override
    public String getAdapterId() {
        return "adorsys-integ-adapter";
    }
}
