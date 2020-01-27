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

package de.adorsys.xs2a.adapter.service.provider;

import de.adorsys.xs2a.adapter.adapter.BaseDownloadService;
import de.adorsys.xs2a.adapter.http.HttpClientFactory;
import de.adorsys.xs2a.adapter.service.*;
import de.adorsys.xs2a.adapter.service.impl.DeutscheBankAccountInformationService;
import de.adorsys.xs2a.adapter.service.impl.DeutscheBankPaymentInitiationService;
import de.adorsys.xs2a.adapter.service.impl.DeutscheBankPsuPasswordEncryptionService;
import de.adorsys.xs2a.adapter.service.impl.PsuIdTypeHeaderInterceptor;
import de.adorsys.xs2a.adapter.service.model.Aspsp;

public class DeutscheBankServiceProvider
    implements AccountInformationServiceProvider, PaymentInitiationServiceProvider, DownloadServiceProvider {
    private static final String SERVICE_GROUP_PLACEHOLDER = "{Service Group}";
    private static final PsuIdTypeHeaderInterceptor psuIdTypeHeaderInterceptor = new PsuIdTypeHeaderInterceptor();
    private static final PsuPasswordEncryptionService psuPasswordEncryptionService = DeutscheBankPsuPasswordEncryptionService.getInstance();

    @Override
    public AccountInformationService getAccountInformationService(Aspsp aspsp,
                                                                  HttpClientFactory httpClientFactory,
                                                                  Pkcs12KeyStore keyStore) {
        aspsp.setUrl(aspsp.getUrl().replace(SERVICE_GROUP_PLACEHOLDER, "ais"));
        return new DeutscheBankAccountInformationService(aspsp,
            httpClientFactory.getHttpClient(getAdapterId()),
            psuIdTypeHeaderInterceptor,
            psuPasswordEncryptionService);
    }

    @Override
    public PaymentInitiationService getPaymentInitiationService(String baseUrl,
                                                                HttpClientFactory httpClientFactory,
                                                                Pkcs12KeyStore keyStore) {
        return new DeutscheBankPaymentInitiationService(baseUrl.replace(SERVICE_GROUP_PLACEHOLDER, "pis"),
            httpClientFactory.getHttpClient(getAdapterId()),
            psuIdTypeHeaderInterceptor);
    }

    @Override
    public DownloadService getDownloadService(String baseUrl,
                                              HttpClientFactory httpClientFactory,
                                              Pkcs12KeyStore keyStore) {
        return new BaseDownloadService(baseUrl, httpClientFactory.getHttpClient(getAdapterId()));
    }

    @Override
    public String getAdapterId() {
        return "deutsche-bank-adapter";
    }
}
