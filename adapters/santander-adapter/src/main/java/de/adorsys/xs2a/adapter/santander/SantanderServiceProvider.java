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

package de.adorsys.xs2a.adapter.santander;

import de.adorsys.xs2a.adapter.http.HttpClient;
import de.adorsys.xs2a.adapter.http.HttpClientFactory;
import de.adorsys.xs2a.adapter.service.*;
import de.adorsys.xs2a.adapter.service.link.LinksRewriter;
import de.adorsys.xs2a.adapter.service.model.Aspsp;
import de.adorsys.xs2a.adapter.service.provider.AccountInformationServiceProvider;
import de.adorsys.xs2a.adapter.service.provider.PaymentInitiationServiceProvider;

public class SantanderServiceProvider
    implements AccountInformationServiceProvider, PaymentInitiationServiceProvider, Oauth2ServiceProvider {

    @Override
    public AccountInformationService getAccountInformationService(Aspsp aspsp,
                                                                  HttpClientFactory httpClientFactory,
                                                                  Pkcs12KeyStore keyStore,
                                                                  LinksRewriter linksRewriter) {
        HttpClient httpClient = httpClientFactory.getHttpClient(getAdapterId());
        SantanderAccessTokenService tokenService = getAccessTokenService(httpClient);
        return new SantanderAccountInformationService(aspsp, tokenService, httpClient, linksRewriter);
    }

    private SantanderAccessTokenService getAccessTokenService(HttpClient httpClient) {
        SantanderAccessTokenService tokenService = SantanderAccessTokenService.getInstance();
        tokenService.setHttpClient(httpClient);
        return tokenService;
    }

    @Override
    public PaymentInitiationService getPaymentInitiationService(Aspsp aspsp,
                                                                HttpClientFactory httpClientFactory,
                                                                Pkcs12KeyStore keyStore,
                                                                LinksRewriter linksRewriter) {
        HttpClient httpClient = httpClientFactory.getHttpClient(getAdapterId());
        SantanderAccessTokenService accessTokenService = getAccessTokenService(httpClient);
        return new SantanderPaymentInitiationService(aspsp, httpClient, linksRewriter, accessTokenService);
    }

    @Override
    public String getAdapterId() {
        return "santander-adapter";
    }

    @Override
    public Oauth2Service getOauth2Service(Aspsp aspsp, HttpClientFactory httpClientFactory, Pkcs12KeyStore keyStore) {
        HttpClient httpClient = httpClientFactory.getHttpClient(getAdapterId());
        return SantanderOauth2Service.create(aspsp, httpClient, keyStore);
    }
}
