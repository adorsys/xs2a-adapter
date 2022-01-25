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

package de.adorsys.xs2a.adapter.verlag;

import de.adorsys.xs2a.adapter.api.AccountInformationService;
import de.adorsys.xs2a.adapter.api.DownloadService;
import de.adorsys.xs2a.adapter.api.PaymentInitiationService;
import de.adorsys.xs2a.adapter.api.http.HttpClientConfig;
import de.adorsys.xs2a.adapter.api.http.HttpClientFactory;
import de.adorsys.xs2a.adapter.api.model.Aspsp;
import de.adorsys.xs2a.adapter.impl.BaseDownloadService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class VerlagServiceProviderTest {

    private VerlagServiceProvider serviceProvider;
    private final HttpClientConfig httpClientConfig = mock(HttpClientConfig.class);
    private final HttpClientFactory httpClientFactory = mock(HttpClientFactory.class);
    private final Aspsp aspsp = new Aspsp();

    @BeforeEach
    void setUp() {
        serviceProvider = new VerlagServiceProvider();
        when(httpClientFactory.getHttpClientConfig()).thenReturn(httpClientConfig);
    }

    @Test
    void getAccountInformationService() {
        AccountInformationService actualService
            = serviceProvider.getAccountInformationService(aspsp, httpClientFactory, null);

        assertThat(actualService)
            .isExactlyInstanceOf(VerlagAccountInformationService.class);
    }

    @Test
    void getPaymentInitiationService() {
        PaymentInitiationService actualService
            = serviceProvider.getPaymentInitiationService(aspsp, httpClientFactory, null);

        assertThat(actualService)
            .isExactlyInstanceOf(VerlagPaymentInitiationService.class);
    }

    @Test
    void getDownloadService() {
        DownloadService actualService
            = serviceProvider.getDownloadService("", httpClientFactory);

        assertThat(actualService)
            .isExactlyInstanceOf(BaseDownloadService.class);
    }
}
