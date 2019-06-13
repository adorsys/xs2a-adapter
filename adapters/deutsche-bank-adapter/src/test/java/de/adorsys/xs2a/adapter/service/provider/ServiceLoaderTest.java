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

import org.junit.Test;

import java.util.ServiceLoader;

import static org.assertj.core.api.Assertions.assertThat;

public class ServiceLoaderTest {

    @Test
    public void getPaymentInitiationServiceProvider() {
        ServiceLoader<PaymentInitiationServiceProvider> loader = ServiceLoader.load(PaymentInitiationServiceProvider.class);
        PaymentInitiationServiceProvider provider = loader.iterator().next();

        assertThat(provider).isInstanceOf(DeutscheBankServiceProvider.class);
        assertThat(provider.getBankCodes()).contains("50010517");
        assertThat(provider.getPaymentInitiationService()).isNotNull();
    }

    @Test
    public void getAccountInformationServiceProvider() {
        ServiceLoader<AccountInformationServiceProvider> loader = ServiceLoader.load(AccountInformationServiceProvider.class);
        AccountInformationServiceProvider provider = loader.iterator().next();

        assertThat(provider).isInstanceOf(DeutscheBankServiceProvider.class);
        assertThat(provider.getBankCodes()).contains("50010517");
        assertThat(provider.getAccountInformationService()).isNotNull();
    }
}
