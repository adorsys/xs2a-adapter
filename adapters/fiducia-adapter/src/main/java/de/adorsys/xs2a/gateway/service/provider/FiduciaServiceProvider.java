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

package de.adorsys.xs2a.gateway.service.provider;

import de.adorsys.xs2a.gateway.http.HttpClient;
import de.adorsys.xs2a.gateway.http.RequestSigningInterceptor;
import de.adorsys.xs2a.gateway.service.PaymentInitiationService;
import de.adorsys.xs2a.gateway.service.ais.AccountInformationService;
import de.adorsys.xs2a.gateway.service.impl.FiduciaAccountInformationService;
import de.adorsys.xs2a.gateway.service.impl.FiduciaPaymentInitiationService;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class FiduciaServiceProvider implements AccountInformationServiceProvider, PaymentInitiationServiceProvider {

    private static final String BASE_URI = "https://xs2a-test.fiduciagad.de/xs2a/v1";
    private static final String BANK_NAME = "Fiducia";
    private final RequestSigningInterceptor requestSigningInterceptor = new RequestSigningInterceptor();

    private Set<String> bankCodes = Collections.unmodifiableSet(new HashSet<>(Collections.singletonList("88888888")));
    private FiduciaAccountInformationService accountInformationService;
    private FiduciaPaymentInitiationService paymentInitiationService;

    @Override
    public Set<String> getBankCodes() {
        return bankCodes;
    }

    @Override
    public AccountInformationService getAccountInformationService() {
        if (accountInformationService == null) {
            accountInformationService = new FiduciaAccountInformationService(BASE_URI);
            accountInformationService.setHttpClient(HttpClient.newHttpClientWithSignature(requestSigningInterceptor));
        }
        return accountInformationService;
    }

    @Override
    public PaymentInitiationService getPaymentInitiationService() {
        if (paymentInitiationService == null) {
            paymentInitiationService = new FiduciaPaymentInitiationService(BASE_URI);
            paymentInitiationService.setHttpClient(HttpClient.newHttpClientWithSignature(requestSigningInterceptor));
        }
        return paymentInitiationService;
    }

    @Override
    public String getBankName() {
        return BANK_NAME;
    }
}
