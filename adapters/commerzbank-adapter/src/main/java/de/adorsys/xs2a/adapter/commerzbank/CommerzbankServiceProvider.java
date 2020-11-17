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

package de.adorsys.xs2a.adapter.commerzbank;

import de.adorsys.xs2a.adapter.api.*;
import de.adorsys.xs2a.adapter.api.http.HttpClientConfig;
import de.adorsys.xs2a.adapter.api.http.HttpClientFactory;
import de.adorsys.xs2a.adapter.api.link.LinksRewriter;
import de.adorsys.xs2a.adapter.api.model.Aspsp;
import de.adorsys.xs2a.adapter.impl.BasePaymentInitiationService;
import de.adorsys.xs2a.adapter.impl.http.BaseHttpClientConfig;

public class CommerzbankServiceProvider
    implements AccountInformationServiceProvider, PaymentInitiationServiceProvider, Oauth2ServiceProvider {

    @Override
    public AccountInformationService getAccountInformationService(Aspsp aspsp,
                                                                  HttpClientFactory httpClientFactory,
                                                                  Pkcs12KeyStore keyStore,
                                                                  LinksRewriter linksRewriter) {
        return new CommerzbankAccountInformationService(aspsp, httpClientFactory.getHttpClient(getAdapterId()),
            linksRewriter, null);
    }

    @Override
    public AccountInformationService getAccountInformationService(Aspsp aspsp, LinksRewriter linksRewriter, HttpClientConfig httpClientConfig) {
        return new CommerzbankAccountInformationService(aspsp,
            httpClientConfig.getClientFactory().getHttpClient(getAdapterId()),
            linksRewriter,
            httpClientConfig.getLogSanitizer());
    }

    @Override
    public PaymentInitiationService getPaymentInitiationService(Aspsp aspsp,
                                                                HttpClientFactory httpClientFactory,
                                                                Pkcs12KeyStore keyStore,
                                                                LinksRewriter linksRewriter) {
        return new BasePaymentInitiationService(aspsp,
            httpClientFactory.getHttpClient(getAdapterId()),
            linksRewriter);
    }

    @Override
    public PaymentInitiationService getPaymentInitiationService(Aspsp aspsp, HttpClientConfig clientConfig, LinksRewriter linksRewriter) {
        return new BasePaymentInitiationService(aspsp,
            clientConfig.getClientFactory().getHttpClient(getAdapterId()),
            linksRewriter,
            clientConfig.getLogSanitizer());
    }

    @Override
    public String getAdapterId() {
        return "commerzbank-adapter";
    }

    @Override
    public Oauth2Service getOauth2Service(Aspsp aspsp, HttpClientFactory httpClientFactory, Pkcs12KeyStore keyStore) {
        return getOauth2Service(aspsp, new BaseHttpClientConfig(null, keyStore, httpClientFactory));
    }

    @Override
    public Oauth2Service getOauth2Service(Aspsp aspsp,
                                          HttpClientConfig clientConfig) {
        return CommerzbankOauth2Service.create(aspsp,
            clientConfig.getClientFactory().getHttpClient(getAdapterId()),
            clientConfig.getKeyStore(),
            clientConfig.getLogSanitizer());
    }
}
