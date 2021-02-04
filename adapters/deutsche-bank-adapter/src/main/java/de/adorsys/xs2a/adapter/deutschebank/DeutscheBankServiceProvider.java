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

package de.adorsys.xs2a.adapter.deutschebank;

import de.adorsys.xs2a.adapter.api.*;
import de.adorsys.xs2a.adapter.api.http.HttpClientFactory;
import de.adorsys.xs2a.adapter.api.link.LinksRewriter;
import de.adorsys.xs2a.adapter.api.model.Aspsp;
import de.adorsys.xs2a.adapter.impl.AbstractAdapterServiceProvider;
import de.adorsys.xs2a.adapter.impl.BaseDownloadService;

public class DeutscheBankServiceProvider extends AbstractAdapterServiceProvider implements DownloadServiceProvider {
    public static final String SERVICE_GROUP_PLACEHOLDER = "{Service Group}";
    private final PsuIdTypeHeaderInterceptor psuIdTypeHeaderInterceptor = new PsuIdTypeHeaderInterceptor();
    private final PsuPasswordEncryptionService psuPasswordEncryptionService = DeutscheBankPsuPasswordEncryptionService.getInstance();

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
        aspsp.setUrl(aspsp.getUrl().replace(SERVICE_GROUP_PLACEHOLDER, "ais"));
        return new DeutscheBankAccountInformationService(aspsp,
            httpClientFactory,
            getInterceptors(aspsp, psuIdTypeHeaderInterceptor),
            linksRewriter,
            psuPasswordEncryptionService);
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
        aspsp.setUrl(aspsp.getUrl().replace(SERVICE_GROUP_PLACEHOLDER, "pis"));
        return new DeutscheBankPaymentInitiationService(aspsp,
            httpClientFactory,
            getInterceptors(aspsp, psuIdTypeHeaderInterceptor),
            linksRewriter);
    }

    @Override
    public DownloadService getDownloadService(String baseUrl,
                                              HttpClientFactory httpClientFactory,
                                              Pkcs12KeyStore keyStore) {
        return getDownloadService(baseUrl, httpClientFactory);
    }

    @Override
    public DownloadService getDownloadService(String baseUrl,
                                              HttpClientFactory httpClientFactory) {
        return new BaseDownloadService(baseUrl,
            httpClientFactory.getHttpClient(getAdapterId()),
            httpClientFactory.getHttpClientConfig().getLogSanitizer());
    }

    @Override
    public String getAdapterId() {
        return "deutsche-bank-adapter";
    }
}
