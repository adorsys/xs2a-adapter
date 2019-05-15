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

import de.adorsys.xs2a.gateway.security.AccessTokenService;
import de.adorsys.xs2a.gateway.service.PaymentInitiationService;
import de.adorsys.xs2a.gateway.service.ais.AccountInformationService;
import de.adorsys.xs2a.gateway.service.impl.DkbAccessTokenService;
import de.adorsys.xs2a.gateway.service.impl.DkbAccountInformationService;
import de.adorsys.xs2a.gateway.service.impl.DkbPaymentInitiationService;

import java.util.Collections;
import java.util.Set;

public class DkbServiceProvider implements AccountInformationServiceProvider, PaymentInitiationServiceProvider {

    private static final String BASE_URI = "https://api.dkb.de/psd2/1.3.2/v1/";
    private final AccessTokenService tokenService = DkbAccessTokenService.getInstance();
    private PaymentInitiationService paymentInitiationService;
    private AccountInformationService accountInformationService;

    @Override
    public Set<String> getBankCodes() {
        return Collections.unmodifiableSet(Collections.singleton("12030000"));
    }

    @Override
    public PaymentInitiationService getPaymentInitiationService() {
        if (paymentInitiationService == null) {
            paymentInitiationService = new DkbPaymentInitiationService(BASE_URI, tokenService);
        }
        return paymentInitiationService;
    }

    @Override
    public AccountInformationService getAccountInformationService() {
        if (accountInformationService == null) {
            accountInformationService = new DkbAccountInformationService(BASE_URI, tokenService);
        }
        return accountInformationService;
    }
}