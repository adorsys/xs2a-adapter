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

package de.adorsys.xs2a.adapter.ing;

import de.adorsys.xs2a.adapter.api.Response;
import de.adorsys.xs2a.adapter.api.ResponseHeaders;
import de.adorsys.xs2a.adapter.api.http.HttpClient;
import de.adorsys.xs2a.adapter.api.http.Interceptor;
import de.adorsys.xs2a.adapter.api.http.Request;
import de.adorsys.xs2a.adapter.impl.http.StringUri;
import de.adorsys.xs2a.adapter.ing.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static de.adorsys.xs2a.adapter.ing.IngPaymentInitiationApi.*;
import static de.adorsys.xs2a.adapter.ing.model.IngPaymentProduct.SEPA_CREDIT_TRANSFERS;
import static de.adorsys.xs2a.adapter.ing.model.IngXmlPaymentProduct.PAIN_001_SEPA_CREDIT_TRANSFERS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IngPaymentInitiationApiTest {

    private static final ResponseHeaders emptyResponseHeaders = ResponseHeaders.emptyResponseHeaders();
    private static final String PAYMENT_ID = "paymentId";
    private static final String REQUEST_ID = "requestId";
    private static final String PSU_IP_ADDRESS = "0.0.0.0";
    private static final List<Interceptor> INTERCEPTORS = Collections.emptyList();
    private static final String TPP_REDIRECT_URI = "https://redirect.uri";

    private IngPaymentInitiationApi paymentInitiationApi;
    @Mock
    private HttpClient httpClient;
    @Mock
    private Request.Builder builder;
    @Captor
    private ArgumentCaptor<String> uriCaptor;

    private final String baseUrl = "https://url.base";

    @BeforeEach
    void setUp() {
        paymentInitiationApi = new IngPaymentInitiationApi(baseUrl, httpClient, null);
    }

    @Test
    void getPaymentDetails() {
        String expectedUri = StringUri.fromElements(baseUrl, PAYMENTS, SEPA_CREDIT_TRANSFERS, PAYMENT_ID);

        when(httpClient.get(expectedUri)).thenReturn(builder);
        when(builder.header(anyString(), anyString())).thenReturn(builder);
        when(builder.send(any(), anyList())).thenReturn(getResponse(new IngPaymentInstruction()));

        Response<IngPaymentInstruction> actualResponse
            = paymentInitiationApi.getPaymentDetails(SEPA_CREDIT_TRANSFERS, PAYMENT_ID, REQUEST_ID, PSU_IP_ADDRESS, INTERCEPTORS);

        verify(httpClient, times(1)).get(uriCaptor.capture());
        verify(builder, times(2)).header(anyString(), anyString());
        verify(builder, times(1)).send(any(), anyList());

        assertThat(uriCaptor.getValue())
            .isEqualTo(expectedUri);

        assertThat(actualResponse)
            .isNotNull()
            .matches(r -> r.getStatusCode() == 200);
    }

    @Test
    void initiatePaymentXml() {
        String expectedUri = StringUri.fromElements(baseUrl, PAYMENTS, PAIN_001_SEPA_CREDIT_TRANSFERS);
        final String body = "<test></test>";

        when(httpClient.post(expectedUri)).thenReturn(builder);
        when(builder.header(anyString(), anyString())).thenReturn(builder);
        when(builder.xmlBody(anyString())).thenReturn(builder);
        when(builder.send(any(), anyList())).thenReturn(getResponse(new IngPaymentInitiationResponse()));

        Response<IngPaymentInitiationResponse> actualResponse
            = paymentInitiationApi.initiatePaymentXml(PAIN_001_SEPA_CREDIT_TRANSFERS, REQUEST_ID, TPP_REDIRECT_URI, PSU_IP_ADDRESS, INTERCEPTORS, body);

        verify(httpClient, times(1)).post(uriCaptor.capture());
        verify(builder, times(3)).header(anyString(), anyString());
        verify(builder, times(1)).xmlBody(anyString());
        verify(builder, times(1)).send(any(), anyList());

        assertThat(uriCaptor.getValue())
            .isEqualTo(expectedUri);

        assertThat(actualResponse)
            .isNotNull()
            .matches(r -> r.getStatusCode() == 200);
    }

    @Test
    void getPaymentDetailsXml() {
        String expectedUri = StringUri.fromElements(baseUrl, PAYMENTS, PAIN_001_SEPA_CREDIT_TRANSFERS, PAYMENT_ID);

        when(httpClient.get(expectedUri)).thenReturn(builder);
        when(builder.header(anyString(), anyString())).thenReturn(builder);
        when(builder.send(any(), anyList())).thenReturn(getResponse("<response></response>"));

        Response<String> actualResponse
            = paymentInitiationApi.getPaymentDetailsXml(PAIN_001_SEPA_CREDIT_TRANSFERS, PAYMENT_ID, REQUEST_ID, PSU_IP_ADDRESS, INTERCEPTORS);

        verify(httpClient, times(1)).get(uriCaptor.capture());
        verify(builder, times(2)).header(anyString(), anyString());
        verify(builder, times(1)).send(any(), anyList());

        assertThat(uriCaptor.getValue())
            .isEqualTo(expectedUri);

        assertThat(actualResponse)
            .isNotNull()
            .matches(r -> r.getStatusCode() == 200);
    }

    @Test
    void getPaymentStatusXml() {
        String expectedUri = StringUri.fromElements(baseUrl, PAYMENTS, PAIN_001_SEPA_CREDIT_TRANSFERS, PAYMENT_ID, STATUS);

        when(httpClient.get(expectedUri)).thenReturn(builder);
        when(builder.header(anyString(), anyString())).thenReturn(builder);
        when(builder.send(any(), anyList())).thenReturn(getResponse("<response></response>"));

        Response<String> actualResponse
            = paymentInitiationApi.getPaymentStatusXml(PAIN_001_SEPA_CREDIT_TRANSFERS, PAYMENT_ID, REQUEST_ID, PSU_IP_ADDRESS, INTERCEPTORS);

        verify(httpClient, times(1)).get(uriCaptor.capture());
        verify(builder, times(2)).header(anyString(), anyString());
        verify(builder, times(1)).send(any(), anyList());

        assertThat(uriCaptor.getValue())
            .isEqualTo(expectedUri);

        assertThat(actualResponse)
            .isNotNull()
            .matches(r -> r.getStatusCode() == 200);
    }

    @Test
    void getPeriodicPaymentDetails() {
        String expectedUri = StringUri.fromElements(baseUrl, PERIODIC_PAYMENTS, SEPA_CREDIT_TRANSFERS, PAYMENT_ID);

        when(httpClient.get(expectedUri)).thenReturn(builder);
        when(builder.header(anyString(), anyString())).thenReturn(builder);
        when(builder.send(any(), anyList())).thenReturn(getResponse(new IngPeriodicPaymentInitiationJson()));

        Response<IngPeriodicPaymentInitiationJson> actualResponse
            = paymentInitiationApi.getPeriodicPaymentDetails(SEPA_CREDIT_TRANSFERS, PAYMENT_ID, REQUEST_ID, PSU_IP_ADDRESS, INTERCEPTORS);

        verify(httpClient, times(1)).get(uriCaptor.capture());
        verify(builder, times(2)).header(anyString(), anyString());
        verify(builder, times(1)).send(any(), anyList());

        assertThat(uriCaptor.getValue())
            .isEqualTo(expectedUri);

        assertThat(actualResponse)
            .isNotNull()
            .matches(r -> r.getStatusCode() == 200);
    }

    @Test
    void initiatePeriodicPaymentXml() {
        String expectedUri = StringUri.fromElements(baseUrl, PERIODIC_PAYMENTS, PAIN_001_SEPA_CREDIT_TRANSFERS);
        final IngPeriodicPaymentInitiationXml body = new IngPeriodicPaymentInitiationXml();

        when(httpClient.post(expectedUri)).thenReturn(builder);
        when(builder.header(anyString(), anyString())).thenReturn(builder);
        when(builder.addXmlPart(anyString(), any())).thenReturn(builder);
        when(builder.addPlainTextPart(anyString(), any())).thenReturn(builder);
        when(builder.send(any(), anyList())).thenReturn(getResponse(new IngPeriodicPaymentInitiationResponse()));

        Response<IngPeriodicPaymentInitiationResponse> actualResponse
            = paymentInitiationApi.initiatePeriodicPaymentXml(PAIN_001_SEPA_CREDIT_TRANSFERS, REQUEST_ID, TPP_REDIRECT_URI, PSU_IP_ADDRESS, INTERCEPTORS, body);

        verify(httpClient, times(1)).post(uriCaptor.capture());
        verify(builder, times(3)).header(anyString(), anyString());
        verify(builder, times(1)).addXmlPart(anyString(), any());
        verify(builder, times(4)).addPlainTextPart(anyString(), any());
        verify(builder, times(1)).send(any(), anyList());

        assertThat(uriCaptor.getValue())
            .isEqualTo(expectedUri);

        assertThat(actualResponse)
            .isNotNull()
            .matches(r -> r.getStatusCode() == 200);
    }

    @Test
    void getPeriodicPaymentStatusXml() {
        String expectedUri = StringUri.fromElements(baseUrl, PERIODIC_PAYMENTS, PAIN_001_SEPA_CREDIT_TRANSFERS, PAYMENT_ID, STATUS);

        when(httpClient.get(expectedUri)).thenReturn(builder);
        when(builder.header(anyString(), anyString())).thenReturn(builder);
        when(builder.send(any(), anyList())).thenReturn(getResponse("<response></response>"));

        Response<String> actualResponse
            = paymentInitiationApi.getPeriodicPaymentStatusXml(PAIN_001_SEPA_CREDIT_TRANSFERS, PAYMENT_ID, REQUEST_ID, PSU_IP_ADDRESS, INTERCEPTORS);

        verify(httpClient, times(1)).get(uriCaptor.capture());
        verify(builder, times(2)).header(anyString(), anyString());
        verify(builder, times(1)).send(any(), anyList());

        assertThat(uriCaptor.getValue())
            .isEqualTo(expectedUri);

        assertThat(actualResponse)
            .isNotNull()
            .matches(r -> r.getStatusCode() == 200);
    }

    private <T> Response<T> getResponse(T body) {
        return new Response<>(200, body, emptyResponseHeaders);
    }
}
