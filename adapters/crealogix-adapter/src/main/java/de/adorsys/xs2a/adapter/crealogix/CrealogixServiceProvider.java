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

package de.adorsys.xs2a.adapter.crealogix;

import de.adorsys.xs2a.adapter.api.*;
import de.adorsys.xs2a.adapter.api.http.HttpClient;
import de.adorsys.xs2a.adapter.api.http.HttpClientFactory;
import de.adorsys.xs2a.adapter.api.link.LinksRewriter;
import de.adorsys.xs2a.adapter.api.model.Aspsp;

public class CrealogixServiceProvider implements AccountInformationServiceProvider, PaymentInitiationServiceProvider {

    private final CrealogixAccessTokenService tokenService = CrealogixAccessTokenService.getInstance();

    @Override
    public AccountInformationService getAccountInformationService(Aspsp aspsp,
                                                                  HttpClientFactory httpClientFactory,
                                                                  Pkcs12KeyStore keyStore,
                                                                  LinksRewriter linksRewriter) {
        HttpClient httpClient = provideHttpClient(httpClientFactory);
        return new CrealogixAccountInformationService(aspsp, tokenService, httpClient, linksRewriter);
    }

    private HttpClient provideHttpClient(HttpClientFactory httpClientFactory) {
        HttpClient httpClient = httpClientFactory.getHttpClient(getAdapterId());
        tokenService.setHttpClient(httpClient);
        return httpClient;
    }

    @Override
    public PaymentInitiationService getPaymentInitiationService(Aspsp aspsp,
                                                                HttpClientFactory httpClientFactory,
                                                                Pkcs12KeyStore keyStore,
                                                                LinksRewriter linksRewriter) {
        HttpClient httpClient = provideHttpClient(httpClientFactory);
        return new CrealogixPaymentInitiationService(aspsp, tokenService, httpClient, linksRewriter);
    }

    @Override
    public String getAdapterId() {
        return "crealogix-adapter";
    }
}
