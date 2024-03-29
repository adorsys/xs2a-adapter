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

package de.adorsys.xs2a.adapter.consors;

import de.adorsys.xs2a.adapter.api.RequestHeaders;
import de.adorsys.xs2a.adapter.api.RequestParams;
import de.adorsys.xs2a.adapter.api.Response;
import de.adorsys.xs2a.adapter.api.ResponseHeaders;
import de.adorsys.xs2a.adapter.api.http.HttpClient;
import de.adorsys.xs2a.adapter.api.http.HttpClientConfig;
import de.adorsys.xs2a.adapter.api.http.HttpClientFactory;
import de.adorsys.xs2a.adapter.api.http.Request;
import de.adorsys.xs2a.adapter.api.link.LinksRewriter;
import de.adorsys.xs2a.adapter.api.model.*;
import de.adorsys.xs2a.adapter.impl.http.RequestBuilderImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static de.adorsys.xs2a.adapter.api.RequestHeaders.PSU_ID;
import static de.adorsys.xs2a.adapter.api.model.PaymentProduct.SEPA_CREDIT_TRANSFERS;
import static de.adorsys.xs2a.adapter.api.model.PaymentService.PAYMENTS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConsorsPaymentInitiationServiceTest {

    private ConsorsPaymentInitiationService service;
    @Mock
    private HttpClient client;
    @Mock
    private HttpClientFactory httpClientFactory;
    @Mock
    private HttpClientConfig httpClientConfig;
    @Mock
    private LinksRewriter rewriter;
    @Spy
    private final Aspsp aspsp = buildAspsp();

    @Captor
    private ArgumentCaptor<Request.Builder> builderCaptor;

    @BeforeEach
    void setUp() {
        when(httpClientFactory.getHttpClient(any())).thenReturn(client);
        when(httpClientFactory.getHttpClientConfig()).thenReturn(httpClientConfig);

        service = new ConsorsPaymentInitiationService(aspsp, httpClientFactory, rewriter);
    }

    @Test
    void initiatePayment_noPsuId() {
        when(client.post(any())).thenReturn(new RequestBuilderImpl(client, null, null));
        when(client.send(any(), any()))
            .thenReturn(new Response<>(-1,
                new PaymentInitationRequestResponse201(),
                ResponseHeaders.emptyResponseHeaders()));

        service.initiatePayment(PAYMENTS,
            SEPA_CREDIT_TRANSFERS,
            RequestHeaders.empty(),
            RequestParams.empty(),
            new PaymentInitiationJson());

        verify(client, times(1)).send(builderCaptor.capture(), any());

        Map<String, String> actualHeaders = builderCaptor.getValue().headers();
        assertThat(actualHeaders)
            .isEmpty();
    }

    @Test
    void initiatePayment_psuIdAvailable() {
        Map<String, String> headers = new HashMap<>();
        headers.put(PSU_ID, "foo");

        when(client.post(any())).thenReturn(new RequestBuilderImpl(client, null, null));
        when(client.send(any(), any()))
            .thenReturn(new Response<>(-1,
                new PaymentInitationRequestResponse201(),
                ResponseHeaders.emptyResponseHeaders()));

        service.initiatePayment(PAYMENTS,
            SEPA_CREDIT_TRANSFERS,
            RequestHeaders.fromMap(headers),
            RequestParams.empty(),
            new PaymentInitiationJson());

        verify(client, times(1)).send(builderCaptor.capture(), any());

        Map<String, String> actualHeaders = builderCaptor.getValue().headers();
        assertThat(actualHeaders)
            .isEmpty();
    }

    private Aspsp buildAspsp() {
        Aspsp aspsp = new Aspsp();
        aspsp.setUrl("https://example.com");
        return aspsp;
    }
}
