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

import de.adorsys.xs2a.adapter.adapter.BaseAccountInformationService;
import de.adorsys.xs2a.adapter.adapter.BasePaymentInitiationService;
import de.adorsys.xs2a.adapter.service.PaymentInitiationService;
import de.adorsys.xs2a.adapter.service.ais.AccountInformationService;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class SpardaServiceProvider implements AccountInformationServiceProvider, PaymentInitiationServiceProvider {

    private static final String BASE_URI = "https://sandbox.sparda.de.schulung.sparda.de/v1";
    private static final String BANK_NAME = "Sparda bank";
    private Set<String> bankCodes = Collections.unmodifiableSet(new HashSet<>(Collections.singletonList("sparda-bank")));
    private AccountInformationService accountInformationService;
    private PaymentInitiationService paymentInitiationService;

    @Override
    public Set<String> getBankCodes() {
        return bankCodes;
    }

    @Override
    public AccountInformationService getAccountInformationService() {
        if (accountInformationService == null) {
            accountInformationService = new BaseAccountInformationService(BASE_URI);
        }
        return accountInformationService;
    }

    @Override
    public PaymentInitiationService getPaymentInitiationService() {
        if (paymentInitiationService == null) {
            paymentInitiationService = new BasePaymentInitiationService(BASE_URI);
        }
        return paymentInitiationService;
    }

    @Override
    public String getBankName() {
        return BANK_NAME;
    }
}
