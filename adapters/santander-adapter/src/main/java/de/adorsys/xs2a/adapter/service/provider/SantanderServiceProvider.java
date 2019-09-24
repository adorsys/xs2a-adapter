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

import de.adorsys.xs2a.adapter.security.AccessTokenService;
import de.adorsys.xs2a.adapter.service.AccountInformationService;
import de.adorsys.xs2a.adapter.service.PaymentInitiationService;
import de.adorsys.xs2a.adapter.service.impl.SantanderAccessTokenService;
import de.adorsys.xs2a.adapter.service.impl.SantanderAccountInformationService;

public class SantanderServiceProvider implements AccountInformationServiceProvider, PaymentInitiationServiceProvider {
    private final AccessTokenService tokenService = SantanderAccessTokenService.getInstance();

    @Override
    public AccountInformationService getAccountInformationService(String baseUrl) {
        return new SantanderAccountInformationService(baseUrl, tokenService);
    }

    @Override
    public PaymentInitiationService getPaymentInitiationService(String baseUrl) {
        throw new UnsupportedOperationException("PIS service is not supported for Santander adapter");
    }

    @Override
    public String getAdapterId() {
        return "santander-adapter";
    }
}
