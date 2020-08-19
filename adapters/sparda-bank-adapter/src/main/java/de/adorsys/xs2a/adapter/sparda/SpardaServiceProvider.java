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

package de.adorsys.xs2a.adapter.sparda;

import de.adorsys.xs2a.adapter.api.*;
import de.adorsys.xs2a.adapter.api.config.AdapterConfig;
import de.adorsys.xs2a.adapter.api.http.HttpClientFactory;
import de.adorsys.xs2a.adapter.api.link.LinksRewriter;
import de.adorsys.xs2a.adapter.api.model.Aspsp;

public class SpardaServiceProvider implements AccountInformationServiceProvider, PaymentInitiationServiceProvider,
    Oauth2ServiceProvider {
    private static final SpardaJwtService JWT_SERVICE = new SpardaJwtService();

    @Override
    public AccountInformationService getAccountInformationService(Aspsp aspsp,
                                                                  HttpClientFactory httpClientFactory,
                                                                  Pkcs12KeyStore keyStore,
                                                                  LinksRewriter linksRewriter) {
        return new SpardaAccountInformationService(aspsp, httpClientFactory.getHttpClient(getAdapterId()),
            linksRewriter, JWT_SERVICE);
    }

    @Override
    public PaymentInitiationService getPaymentInitiationService(Aspsp aspsp,
                                                                HttpClientFactory httpClientFactory,
                                                                Pkcs12KeyStore keyStore,
                                                                LinksRewriter linksRewriter) {
        return new SpardaPaymentInitiationService(aspsp, httpClientFactory.getHttpClient(getAdapterId()),
            linksRewriter, JWT_SERVICE);
    }

    @Override
    public Oauth2Service getOauth2Service(Aspsp aspsp, HttpClientFactory httpClientFactory, Pkcs12KeyStore keyStore) {
        return SpardaOauth2Service.create(aspsp,
            httpClientFactory.getHttpClient(getAdapterId()),
            keyStore,
            AdapterConfig.readProperty("sparda.client_id"));
    }

    @Override
    public String getAdapterId() {
        return "sparda-bank-adapter";
    }
}
