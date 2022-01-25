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

import de.adorsys.xs2a.adapter.api.AccountInformationService;
import de.adorsys.xs2a.adapter.api.DownloadService;
import de.adorsys.xs2a.adapter.api.PaymentInitiationService;
import de.adorsys.xs2a.adapter.api.http.HttpClientConfig;
import de.adorsys.xs2a.adapter.api.http.HttpClientFactory;
import de.adorsys.xs2a.adapter.api.http.HttpLogSanitizer;
import de.adorsys.xs2a.adapter.api.model.Aspsp;
import de.adorsys.xs2a.adapter.impl.BaseDownloadService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class DeutscheBankServiceProviderTest {

    private static final String URL = "https://url.com/";
    private static final String URL_WITH_PLACEHOLDER = URL + DeutscheBankServiceProvider.SERVICE_GROUP_PLACEHOLDER;

    private DeutscheBankServiceProvider serviceProvider;
    private Aspsp aspsp;

    private final HttpClientConfig clientConfig = mock(HttpClientConfig.class);
    private final HttpLogSanitizer logSanitizer = mock(HttpLogSanitizer.class);
    private final HttpClientFactory factory = mock(HttpClientFactory.class);

    ArgumentCaptor<String> urlCaptor = ArgumentCaptor.forClass(String.class);

    @BeforeEach
    void setUp() {
        serviceProvider = new DeutscheBankServiceProvider();
        aspsp = spy(getAspsp());

        when(factory.getHttpClientConfig()).thenReturn(clientConfig);
        when(clientConfig.getLogSanitizer()).thenReturn(logSanitizer);
    }

    @Test
    void getAccountInformationService() {
        AccountInformationService ais = serviceProvider.getAccountInformationService(aspsp, factory, null);

        assertThat(ais)
            .isExactlyInstanceOf(DeutscheBankAccountInformationService.class);
        assertUrl("ais");
    }

    private void assertUrl(String service) {
        verify(aspsp, times(1)).getUrl();
        verify(aspsp, times(1)).setUrl(urlCaptor.capture());

        assertThat(urlCaptor.getValue())
            .isNotNull()
            .contains(URL + service);
    }

    @Test
    void getPaymentInitiationService() {
        PaymentInitiationService pis = serviceProvider.getPaymentInitiationService(aspsp, factory, null);

        assertThat(pis)
            .isExactlyInstanceOf(DeutscheBankPaymentInitiationService.class);
        assertUrl("pis");
    }

    @Test
    void getDownloadService() {
        DownloadService downloadService = serviceProvider.getDownloadService(URL, factory);

        assertThat(downloadService)
            .isExactlyInstanceOf(BaseDownloadService.class);
    }

    private Aspsp getAspsp() {
        Aspsp aspsp = new Aspsp();
        aspsp.setUrl(URL_WITH_PLACEHOLDER);
        return aspsp;
    }
}
