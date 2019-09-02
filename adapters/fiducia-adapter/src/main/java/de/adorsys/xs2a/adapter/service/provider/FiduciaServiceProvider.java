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

import de.adorsys.xs2a.adapter.http.HttpClient;
import de.adorsys.xs2a.adapter.http.RequestSigningInterceptor;
import de.adorsys.xs2a.adapter.service.PaymentInitiationService;
import de.adorsys.xs2a.adapter.service.AccountInformationService;
import de.adorsys.xs2a.adapter.service.impl.FiduciaAccountInformationService;
import de.adorsys.xs2a.adapter.service.impl.FiduciaPaymentInitiationService;

public class FiduciaServiceProvider implements AccountInformationServiceProvider, PaymentInitiationServiceProvider {

    private final RequestSigningInterceptor requestSigningInterceptor = new RequestSigningInterceptor();

    @Override
    public AccountInformationService getAccountInformationService(String baseUrl) {
        FiduciaAccountInformationService accountInformationService = new FiduciaAccountInformationService(baseUrl);
        accountInformationService.setHttpClient(HttpClient.newHttpClientWithSignature(requestSigningInterceptor));
        return accountInformationService;
    }

    @Override
    public PaymentInitiationService getPaymentInitiationService(String baseUrl) {
        FiduciaPaymentInitiationService paymentInitiationService = new FiduciaPaymentInitiationService(baseUrl);
        paymentInitiationService.setHttpClient(HttpClient.newHttpClientWithSignature(requestSigningInterceptor));
        return paymentInitiationService;
    }

    @Override
    public String getAdapterId() {
        return "fiducia-adapter";
    }
}
