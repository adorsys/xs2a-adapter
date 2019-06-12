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

package de.adorsys.xs2a.gateway.adorsys.service.provider;

import de.adorsys.xs2a.gateway.adapter.BaseAccountInformationService;
import de.adorsys.xs2a.gateway.adapter.BasePaymentInitiationService;
import de.adorsys.xs2a.gateway.service.PaymentInitiationService;
import de.adorsys.xs2a.gateway.service.ais.AccountInformationService;
import de.adorsys.xs2a.gateway.service.provider.AccountInformationServiceProvider;
import de.adorsys.xs2a.gateway.service.provider.PaymentInitiationServiceProvider;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class AdorsysIntegServiceProvider implements AccountInformationServiceProvider, PaymentInitiationServiceProvider {

    private static final String DEFAULT_BASE_URI = "http://localhost:8089/v1";
    private static final String BASE_URI_ENV = "adorsys-integ.base_uri";
    private static final String BANK_NAME = "adorsys xs2a";
    private Set<String> bankCodes = Collections.unmodifiableSet(new HashSet<>(Collections.singletonList("adorsys-integ")));
    private AccountInformationService accountInformationService;
    private BasePaymentInitiationService paymentInitiationService;

    @Override
    public Set<String> getBankCodes() {
        return bankCodes;
    }

    @Override
    public PaymentInitiationService getPaymentInitiationService() {
        if (paymentInitiationService == null) {
            paymentInitiationService = new BasePaymentInitiationService(getBaseUri());
        }
        return paymentInitiationService;
    }

    @Override
    public AccountInformationService getAccountInformationService() {
        if (accountInformationService == null) {
            accountInformationService = new BaseAccountInformationService(getBaseUri());
        }
        return accountInformationService;
    }

    private static String getBaseUri() {
        String baseUri = System.getenv(BASE_URI_ENV);
        return baseUri == null || baseUri.trim().isEmpty() ? DEFAULT_BASE_URI : baseUri.trim();
    }

    @Override
    public String getBankName() {
        return BANK_NAME;
    }
}
