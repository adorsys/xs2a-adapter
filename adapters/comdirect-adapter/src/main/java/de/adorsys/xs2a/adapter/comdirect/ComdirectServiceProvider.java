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

package de.adorsys.xs2a.adapter.comdirect;

import de.adorsys.xs2a.adapter.api.*;
import de.adorsys.xs2a.adapter.api.http.HttpClientFactory;
import de.adorsys.xs2a.adapter.api.link.LinksRewriter;
import de.adorsys.xs2a.adapter.api.model.Aspsp;
import de.adorsys.xs2a.adapter.impl.AbstractAdapterServiceProvider;
import de.adorsys.xs2a.adapter.impl.BasePaymentInitiationService;

public class ComdirectServiceProvider extends AbstractAdapterServiceProvider implements Oauth2ServiceProvider {

    @Override
    public AccountInformationService getAccountInformationService(Aspsp aspsp,
                                                                  HttpClientFactory httpClientFactory,
                                                                  Pkcs12KeyStore keyStore,
                                                                  LinksRewriter linksRewriter) {
        return getAccountInformationService(aspsp, httpClientFactory, linksRewriter);
    }

    @Override
    public AccountInformationService getAccountInformationService(Aspsp aspsp,
                                                                  HttpClientFactory httpClientFactory,
                                                                  LinksRewriter linksRewriter) {
        return new ComdirectAccountInformationService(aspsp,
            httpClientFactory,
            linksRewriter);
    }

    @Override
    public PaymentInitiationService getPaymentInitiationService(Aspsp aspsp,
                                                                HttpClientFactory httpClientFactory,
                                                                Pkcs12KeyStore keyStore,
                                                                LinksRewriter linksRewriter) {
        return getPaymentInitiationService(aspsp, httpClientFactory, linksRewriter);
    }

    @Override
    public PaymentInitiationService getPaymentInitiationService(Aspsp aspsp,
                                                                HttpClientFactory httpClientFactory,
                                                                LinksRewriter linksRewriter) {
        return new BasePaymentInitiationService(aspsp,
            httpClientFactory.getHttpClient(getAdapterId()),
            linksRewriter,
            httpClientFactory.getHttpClientConfig().getLogSanitizer());
    }

    @Override
    public String getAdapterId() {
        return "comdirect-adapter";
    }

    @Override
    public Oauth2Service getOauth2Service(Aspsp aspsp, HttpClientFactory httpClientFactory, Pkcs12KeyStore keyStore) {
        return getOauth2Service(aspsp, httpClientFactory);
    }

    @Override
    public Oauth2Service getOauth2Service(Aspsp aspsp, HttpClientFactory httpClientFactory) {
        return ComdirectOauth2Service.create(aspsp,
            httpClientFactory.getHttpClient(getAdapterId()),
            httpClientFactory.getHttpClientConfig().getKeyStore(),
            httpClientFactory.getHttpClientConfig().getLogSanitizer());
    }
}
