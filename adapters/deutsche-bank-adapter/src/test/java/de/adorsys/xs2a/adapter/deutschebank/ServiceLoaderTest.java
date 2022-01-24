/*
 * Copyright 2018-2022 adorsys GmbH & Co KG
 *
 * This program is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or (at
 * your option) any later version. This program is distributed in the hope that
 * it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see https://www.gnu.org/licenses/.
 *
 * This project is also available under a separate commercial license. You can
 * contact us at psd2@adorsys.com.
 */

package de.adorsys.xs2a.adapter.deutschebank;

import de.adorsys.xs2a.adapter.api.AccountInformationServiceProvider;
import de.adorsys.xs2a.adapter.api.PaymentInitiationServiceProvider;
import de.adorsys.xs2a.adapter.api.http.HttpClientConfig;
import de.adorsys.xs2a.adapter.api.http.HttpClientFactory;
import de.adorsys.xs2a.adapter.api.model.Aspsp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ServiceLoader;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ServiceLoaderTest {
    private static final Aspsp ASPSP = buildAspspWithUrl();
    private HttpClientFactory httpClientFactory = mock(HttpClientFactory.class);
    private final HttpClientConfig httpClientConfig = mock(HttpClientConfig.class);;

    @BeforeEach
    void setUp() {
        when(httpClientFactory.getHttpClientConfig()).thenReturn(httpClientConfig);
    }

    @Test
    void getPaymentInitiationServiceProvider() {
        ServiceLoader<PaymentInitiationServiceProvider> loader = ServiceLoader.load(PaymentInitiationServiceProvider.class);
        PaymentInitiationServiceProvider provider = loader.iterator().next();

        assertThat(provider).isInstanceOf(DeutscheBankServiceProvider.class);
        assertThat(provider.getPaymentInitiationService(ASPSP, httpClientFactory, null)).isNotNull();
    }

    @Test
    void getAccountInformationServiceProvider() {
        ServiceLoader<AccountInformationServiceProvider> loader = ServiceLoader.load(AccountInformationServiceProvider.class);
        AccountInformationServiceProvider provider = loader.iterator().next();

        assertThat(provider).isInstanceOf(DeutscheBankServiceProvider.class);
        assertThat(provider.getAccountInformationService(ASPSP, httpClientFactory, null)).isNotNull();
    }

    private static Aspsp buildAspspWithUrl() {
        Aspsp aspsp = new Aspsp();
        aspsp.setUrl("url");
        return aspsp;
    }
}
