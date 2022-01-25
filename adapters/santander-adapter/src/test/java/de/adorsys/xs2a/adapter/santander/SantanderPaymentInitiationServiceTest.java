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

package de.adorsys.xs2a.adapter.santander;

import de.adorsys.xs2a.adapter.api.*;
import de.adorsys.xs2a.adapter.api.exception.RequestAuthorizationValidationException;
import de.adorsys.xs2a.adapter.api.http.HttpClient;
import de.adorsys.xs2a.adapter.api.http.HttpClientConfig;
import de.adorsys.xs2a.adapter.api.http.HttpClientFactory;
import de.adorsys.xs2a.adapter.api.http.Request;
import de.adorsys.xs2a.adapter.api.link.LinksRewriter;
import de.adorsys.xs2a.adapter.api.model.*;
import de.adorsys.xs2a.adapter.api.validation.ValidationError;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;

import static de.adorsys.xs2a.adapter.api.model.PaymentProduct.SEPA_CREDIT_TRANSFERS;
import static de.adorsys.xs2a.adapter.api.model.PaymentService.PAYMENTS;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SantanderPaymentInitiationServiceTest {

    private static final RequestHeaders HEADERS_WITH_AUTHORISATION
        = RequestHeaders.fromMap(Map.of(RequestHeaders.AUTHORIZATION, "Bearer foo"));
    private static final RequestParams EMPTY_PARAMS = RequestParams.empty();
    private static final RequestHeaders EMPTY_HEADERS = RequestHeaders.empty();
    private static Request.Builder requestBuilder;

    private SantanderPaymentInitiationService paymentInitiationService;

    @Mock
    private HttpClientFactory httpClientFactory;
    @Mock
    private HttpClientConfig httpClientConfig;
    @Mock
    private SantanderOauth2Service oauth2Service;
    @Mock
    private Aspsp aspsp;
    @Mock
    private Pkcs12KeyStore keyStore;
    @Mock
    private HttpClient httpClient;
    @Mock
    private LinksRewriter linksRewriter;

    @BeforeEach
    void setUp() {
        requestBuilder = mock(Request.Builder.class);

        when(httpClientFactory.getHttpClient(any(), any())).thenReturn(httpClient);
        when(httpClientFactory.getHttpClientConfig()).thenReturn(httpClientConfig);
        when(httpClientConfig.getKeyStore()).thenReturn(keyStore);

        paymentInitiationService = new SantanderPaymentInitiationService(aspsp, httpClientFactory, linksRewriter, oauth2Service);
    }

    @Test
    @SuppressWarnings("unchecked")
    void initiatePayment() {
        ArgumentCaptor<Map<String, String>> headersCaptor = ArgumentCaptor.forClass(Map.class);

        when(httpClient.post(anyString())).thenReturn(requestBuilder);
        when(requestBuilder.headers(anyMap())).thenReturn(requestBuilder);
        when(requestBuilder.send(any(), any()))
            .thenReturn(new Response<>(-1, new PaymentInitationRequestResponse201(), ResponseHeaders.emptyResponseHeaders()));

        paymentInitiationService.initiatePayment(PAYMENTS, SEPA_CREDIT_TRANSFERS, EMPTY_HEADERS, EMPTY_PARAMS, null);

        verify(requestBuilder, times(1)).headers(headersCaptor.capture());

        Map<String, String> actualHeaders = headersCaptor.getValue();

        assertThat(actualHeaders)
            .isNotEmpty()
            .containsEntry(RequestHeaders.AUTHORIZATION, "Bearer null");
    }

    @Test
    void getPaymentInitiationStatus() {
        Map<String, String> headerWithIpAddressAndAuthorisation = Map.of(RequestHeaders.PSU_IP_ADDRESS, "0.0.0.0",
            RequestHeaders.AUTHORIZATION, "Bearer foo");

        when(httpClient.get(anyString())).thenReturn(requestBuilder);
        when(requestBuilder.headers(anyMap())).thenReturn(requestBuilder);

        assertThatCode(() -> paymentInitiationService.getPaymentInitiationStatus(PAYMENTS, SEPA_CREDIT_TRANSFERS, "", RequestHeaders.fromMap(headerWithIpAddressAndAuthorisation), EMPTY_PARAMS))
            .doesNotThrowAnyException();
        assertThatThrownBy(() -> paymentInitiationService.getPaymentInitiationStatus(PAYMENTS, SEPA_CREDIT_TRANSFERS, "", EMPTY_HEADERS, EMPTY_PARAMS))
            .isExactlyInstanceOf(RequestAuthorizationValidationException.class);
    }

    @Test
    void validateGetPaymentInitiationStatus() {
        Map<String, String> headerWithPsuIpAddress = Map.of(RequestHeaders.PSU_IP_ADDRESS, "0.0.0.0");

        List<ValidationError> shouldBeEmpty
            = paymentInitiationService.validateGetPaymentInitiationStatus(null, null, null, RequestHeaders.fromMap(headerWithPsuIpAddress), null);

        List<ValidationError> shouldHaveError
            = paymentInitiationService.validateGetPaymentInitiationStatus(null, null, null, EMPTY_HEADERS, null);

        assertThat(shouldBeEmpty)
            .isEmpty();
        assertThat(shouldHaveError)
            .isNotEmpty()
            .contains(ValidationError.required(RequestHeaders.PSU_IP_ADDRESS));
    }

    @Test
    void getSinglePaymentInformation() {
        when(httpClient.get(anyString())).thenReturn(requestBuilder);
        when(requestBuilder.headers(anyMap())).thenReturn(requestBuilder);

        assertThatCode(() -> paymentInitiationService.getSinglePaymentInformation(SEPA_CREDIT_TRANSFERS, "", HEADERS_WITH_AUTHORISATION, EMPTY_PARAMS))
            .doesNotThrowAnyException();
        assertThatThrownBy(() -> paymentInitiationService.getSinglePaymentInformation(SEPA_CREDIT_TRANSFERS, "", EMPTY_HEADERS, EMPTY_PARAMS))
            .isExactlyInstanceOf(RequestAuthorizationValidationException.class);
    }

    @Test
    void throwUnsupportedOperationException() {
        assertThatThrownBy(() -> paymentInitiationService.getPaymentInitiationAuthorisation(null, null, null, null, null))
            .isExactlyInstanceOf(UnsupportedOperationException.class);
        assertThatThrownBy(() -> paymentInitiationService.startPaymentAuthorisation(null, null, null, null, null))
            .isExactlyInstanceOf(UnsupportedOperationException.class);
        assertThatThrownBy(() -> paymentInitiationService.startPaymentAuthorisation(null, null, null, null, null, null))
            .isExactlyInstanceOf(UnsupportedOperationException.class);
        assertThatThrownBy(() -> paymentInitiationService.updatePaymentPsuData(null, null, null, null, null, null, (UpdatePsuAuthentication) null))
            .isExactlyInstanceOf(UnsupportedOperationException.class);
        assertThatThrownBy(() -> paymentInitiationService.updatePaymentPsuData(null, null, null, null, null, null, (SelectPsuAuthenticationMethod) null))
            .isExactlyInstanceOf(UnsupportedOperationException.class);
        assertThatThrownBy(() -> paymentInitiationService.updatePaymentPsuData(null, null, null, null, null, null, (TransactionAuthorisation) null))
            .isExactlyInstanceOf(UnsupportedOperationException.class);
        assertThatThrownBy(() -> paymentInitiationService.getPaymentInitiationScaStatus(null, null, null, null, null, null))
            .isExactlyInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    void getPeriodicPaymentInformation() {
        when(httpClient.get(anyString())).thenReturn(requestBuilder);
        when(requestBuilder.headers(anyMap())).thenReturn(requestBuilder);
        when(requestBuilder.send(any(), any()))
            .thenReturn(new Response<>(-1, new PeriodicPaymentInitiationWithStatusResponse(), ResponseHeaders.emptyResponseHeaders()));

        assertThatCode(() -> paymentInitiationService.getPeriodicPaymentInformation(SEPA_CREDIT_TRANSFERS, "", HEADERS_WITH_AUTHORISATION, EMPTY_PARAMS))
            .doesNotThrowAnyException();
        assertThatThrownBy(() -> paymentInitiationService.getPeriodicPaymentInformation(SEPA_CREDIT_TRANSFERS, "", EMPTY_HEADERS, EMPTY_PARAMS))
            .isExactlyInstanceOf(RequestAuthorizationValidationException.class);
    }
}
