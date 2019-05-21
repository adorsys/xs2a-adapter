#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
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

package ${package}.service.provider;

import ${package}.adapter.BaseAccountInformationService;
import ${package}.adapter.BasePaymentInitiationService;
import ${package}.service.PaymentInitiationService;
import ${package}.service.ais.AccountInformationService;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ${classNamePrefix}ServiceProvider implements AccountInformationServiceProvider, PaymentInitiationServiceProvider {

    private static final String BASE_URI = "${baseUri}";
    private Set<String> bankCodes = Collections.unmodifiableSet(new HashSet<>(Collections.singletonList("${bankCode}")));
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
}