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
import de.adorsys.xs2a.adapter.service.AccountInformationService;
import de.adorsys.xs2a.adapter.service.PaymentInitiationService;
import de.adorsys.xs2a.adapter.service.config.AdapterConfig;
import de.adorsys.xs2a.adapter.service.impl.VerlagAccountInformationService;
import de.adorsys.xs2a.adapter.service.impl.VerlagPaymentInitiationService;

import java.util.AbstractMap;

public class VerlagServiceProvider implements AccountInformationServiceProvider, PaymentInitiationServiceProvider {

    private static final String VERLAG_API_KEY_NAME = "verlag.apikey.name";
    private static final String VERLAG_API_KEY_VALUE = "verlag.apikey.value";

    private static AbstractMap.SimpleImmutableEntry<String, String> apiKeyEntry;

    static {
        String apiKeyName = AdapterConfig.readProperty(VERLAG_API_KEY_NAME, "");
        String apiKeyValue = AdapterConfig.readProperty(VERLAG_API_KEY_VALUE, "");
        apiKeyEntry = new AbstractMap.SimpleImmutableEntry<>(apiKeyName, apiKeyValue);
    }

    @Override
    public AccountInformationService getAccountInformationService(String baseUrl, HttpClient httpClient) {
        return new VerlagAccountInformationService(baseUrl, apiKeyEntry, httpClient);
    }

    @Override
    public PaymentInitiationService getPaymentInitiationService(String baseUrl, HttpClient httpClient) {
        return new VerlagPaymentInitiationService(baseUrl, apiKeyEntry, httpClient);
    }

    @Override
    public String getAdapterId() {
        return "verlag-adapter";
    }
}
