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

import de.adorsys.xs2a.gateway.service.PaymentInitiationService;
import de.adorsys.xs2a.gateway.service.impl.VerlagPaymentInitiationService;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class VerlagPaymentInitiationServiceProvider implements PaymentInitiationServiceProvider {
    private static final String BASE_URI = "https://www.sandbox-bvxs2a.de/nationalbank/v1";
    private Set<String> bankCodes = Collections.unmodifiableSet(new HashSet<>(Collections.singletonList("25040090")));
    private VerlagPaymentInitiationService paymentInitiationService;


    @Override
    public Set<String> getBankCodes() {
        return bankCodes;
    }

    @Override
    public PaymentInitiationService getPaymentInitiationService() {
        if (paymentInitiationService == null) {
            paymentInitiationService = new VerlagPaymentInitiationService(BASE_URI);
        }
        return paymentInitiationService;
    }
}
