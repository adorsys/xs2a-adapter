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

public class SparkasseServiceProvider implements AccountInformationServiceProvider, PaymentInitiationServiceProvider {

    private final Set<String> BANK_CODES = Collections.unmodifiableSet(new HashSet<>(Collections.singletonList("99999999")));
    private final String BASE_URI = "https://xs2a-sandbox.f-i-apim.de:8444/fixs2a-env/xs2a-api/12345678/v1";
    private AccountInformationService accountInformationService;
    private PaymentInitiationService paymentInitiationService;

    @Override
    public Set<String> getBankCodes() {
        return BANK_CODES;
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
}
