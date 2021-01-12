package de.adorsys.xs2a.adapter.crealogix;

import de.adorsys.xs2a.adapter.api.*;
import de.adorsys.xs2a.adapter.api.http.HttpClient;
import de.adorsys.xs2a.adapter.api.http.Request;
import de.adorsys.xs2a.adapter.api.link.LinksRewriter;
import de.adorsys.xs2a.adapter.api.model.*;
import de.adorsys.xs2a.adapter.crealogix.model.CrealogixPaymentInitiationWithStatusResponse;
import de.adorsys.xs2a.adapter.impl.http.RequestBuilderImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static de.adorsys.xs2a.adapter.api.model.PaymentProduct.SEPA_CREDIT_TRANSFERS;
import static de.adorsys.xs2a.adapter.api.model.PaymentService.PAYMENTS;
import static java.util.Collections.singletonMap;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class CrealogixPaymentInitiationServiceTest {

    private static final String AUTHORIZATION = "Authorization";
    private static final String AUTHORIZATION_VALUE = "Bearer foo";
    private static final String PAYMENT_ID = "paymentId";
    private static final String AUTHORIZATION_ID = "authorizationId";
    private final HttpClient httpClient = mock(HttpClient.class);
    private final LinksRewriter linksRewriter = mock(LinksRewriter.class);
    private final Aspsp aspsp = getAspsp();
    private PaymentInitiationService service;

    @BeforeEach
    void setUp() {
        service = new CrealogixPaymentInitiationService(aspsp, httpClient, linksRewriter, null);
    }

    @Test
    void getSinglePaymentInformation() {
        when(httpClient.get(any())).thenReturn(getRequestBuilder("GET"));
        when(httpClient.send(any(), any()))
            .thenReturn(new Response<>(-1, getResponseBody(), ResponseHeaders.emptyResponseHeaders()));

        Response<?> actualResponse = service.getSinglePaymentInformation(SEPA_CREDIT_TRANSFERS,
            "paymentId",
            RequestHeaders.fromMap(singletonMap(AUTHORIZATION, AUTHORIZATION_VALUE)),
            RequestParams.empty());

        assertThat(actualResponse)
            .isNotNull()
            .extracting(Response::getBody)
            .isInstanceOf(PaymentInitiationWithStatusResponse.class);
    }

    @Test
    void initiatePayment() {
        when(httpClient.post(anyString())).thenReturn(getRequestBuilder("POST"));
        when(httpClient.send(any(), any()))
            .thenReturn(new Response<>(
                -1,
                new PaymentInitationRequestResponse201(),
                ResponseHeaders.emptyResponseHeaders()));

        Response<PaymentInitationRequestResponse201> actualResponse
            = service.initiatePayment(
                PAYMENTS,
                SEPA_CREDIT_TRANSFERS,
                RequestHeaders.fromMap(singletonMap(AUTHORIZATION, AUTHORIZATION_VALUE)),
                RequestParams.empty(),
                new PaymentInitiationJson()
            );

        verify(httpClient, times(1)).post(anyString());
        verify(httpClient, times(1)).send(any(), any());

        assertThat(actualResponse)
            .isNotNull()
            .extracting(Response::getBody)
            .isInstanceOf(PaymentInitationRequestResponse201.class);
    }

    @Test
    void getPeriodicPaymentInformation() {
        when(httpClient.get(anyString()))
            .thenReturn(getRequestBuilder("GET"));
        when(httpClient.send(any(), any()))
            .thenReturn(getResponse(new PeriodicPaymentInitiationWithStatusResponse()));

        Response<PeriodicPaymentInitiationWithStatusResponse> actualResponse
            = service.getPeriodicPaymentInformation(SEPA_CREDIT_TRANSFERS,
                PAYMENT_ID,
                RequestHeaders.fromMap(singletonMap(AUTHORIZATION, AUTHORIZATION_VALUE)),
                RequestParams.empty());

        assertThat(actualResponse)
            .isNotNull();
    }

    @Test
    void getPeriodicPain001PaymentInformation() {
        when(httpClient.get(anyString()))
            .thenReturn(getRequestBuilder("GET"));
        when(httpClient.send(any(), any()))
            .thenReturn(getResponse(new PeriodicPaymentInitiationMultipartBody()));

        Response<PeriodicPaymentInitiationMultipartBody> actualResponse
            = service.getPeriodicPain001PaymentInformation(SEPA_CREDIT_TRANSFERS,
                PAYMENT_ID,
                RequestHeaders.fromMap(singletonMap(AUTHORIZATION, AUTHORIZATION_VALUE)),
                RequestParams.empty());

        assertThat(actualResponse)
            .isNotNull();
    }

    @Test
    void getPaymentInformationAsString() {
        when(httpClient.get(anyString()))
            .thenReturn(getRequestBuilder("GET"));
        when(httpClient.send(any(), any()))
            .thenReturn(getResponse("body"));

        Response<String> actualResponse = service.getPaymentInformationAsString(PAYMENTS,
            SEPA_CREDIT_TRANSFERS,
            PAYMENT_ID,
            RequestHeaders.fromMap(singletonMap(AUTHORIZATION, AUTHORIZATION_VALUE)),
            RequestParams.empty());

        assertThat(actualResponse)
            .isNotNull();
    }

    @Test
    void getPaymentInitiationScaStatus() {
        when(httpClient.get(anyString()))
            .thenReturn(getRequestBuilder("GET"));
        when(httpClient.send(any(), any()))
            .thenReturn(getResponse(new ScaStatusResponse()));

        Response<ScaStatusResponse> actualResponse = service.getPaymentInitiationScaStatus(PAYMENTS,
            SEPA_CREDIT_TRANSFERS,
            PAYMENT_ID,
            AUTHORIZATION_ID,
            RequestHeaders.fromMap(singletonMap(AUTHORIZATION, AUTHORIZATION_VALUE)),
            RequestParams.empty());

        assertThat(actualResponse)
            .isNotNull();
    }

    @Test
    void getPaymentInitiationStatus() {
        when(httpClient.get(anyString()))
            .thenReturn(getRequestBuilder("GET"));
        when(httpClient.send(any(), any()))
            .thenReturn(getResponse(new PaymentInitiationStatusResponse200Json()));

        Response<PaymentInitiationStatusResponse200Json> actualResponse = service.getPaymentInitiationStatus(PAYMENTS,
            SEPA_CREDIT_TRANSFERS,
            PAYMENT_ID,
            RequestHeaders.fromMap(singletonMap(AUTHORIZATION, AUTHORIZATION_VALUE)),
            RequestParams.empty());

        assertThat(actualResponse)
            .isNotNull();
    }

    @Test
    void getPaymentInitiationStatusAsString() {
        when(httpClient.get(anyString()))
            .thenReturn(getRequestBuilder("GET"));
        when(httpClient.send(any(), any()))
            .thenReturn(getResponse("body"));

        Response<String> actualResponse = service.getPaymentInitiationStatusAsString(PAYMENTS,
            SEPA_CREDIT_TRANSFERS,
            PAYMENT_ID,
            RequestHeaders.fromMap(singletonMap(AUTHORIZATION, AUTHORIZATION_VALUE)),
            RequestParams.empty());

        assertThat(actualResponse)
            .isNotNull();
    }

    @Test
    void getPaymentInitiationAuthorisation() {
        when(httpClient.get(anyString()))
            .thenReturn(getRequestBuilder("GET"));
        when(httpClient.send(any(), any()))
            .thenReturn(getResponse(new Authorisations()));

        Response<Authorisations> actualResponse = service.getPaymentInitiationAuthorisation(PAYMENTS,
            SEPA_CREDIT_TRANSFERS,
            PAYMENT_ID,
            RequestHeaders.fromMap(singletonMap(AUTHORIZATION, AUTHORIZATION_VALUE)),
            RequestParams.empty());

        assertThat(actualResponse)
            .isNotNull();
    }

    @Test
    void startPaymentAuthorisation() {
        when(httpClient.post(anyString()))
            .thenReturn(getRequestBuilder("POST"));
        when(httpClient.send(any(), any()))
            .thenReturn(getResponse(new StartScaprocessResponse()));

        Response<StartScaprocessResponse> actualResponse = service.startPaymentAuthorisation(PAYMENTS,
            SEPA_CREDIT_TRANSFERS,
            PAYMENT_ID,
            RequestHeaders.fromMap(singletonMap(AUTHORIZATION, AUTHORIZATION_VALUE)),
            RequestParams.empty());

        assertThat(actualResponse)
            .isNotNull();
    }

    @Test
    void startPaymentAuthorisation_withPsuAuthentication() {
        when(httpClient.post(anyString()))
            .thenReturn(getRequestBuilder("POST"));
        when(httpClient.send(any(), any()))
            .thenReturn(getResponse(new StartScaprocessResponse()));

        Response<StartScaprocessResponse> actualResponse = service.startPaymentAuthorisation(PAYMENTS,
            SEPA_CREDIT_TRANSFERS,
            PAYMENT_ID,
            RequestHeaders.fromMap(singletonMap(AUTHORIZATION, AUTHORIZATION_VALUE)),
            RequestParams.empty(),
            new UpdatePsuAuthentication());

        assertThat(actualResponse)
            .isNotNull();
    }

    @Test
    void updatePaymentPsuData_psuAuthentication() {
        when(httpClient.put(anyString()))
            .thenReturn(getRequestBuilder("PUT"));
        when(httpClient.send(any(), any()))
            .thenReturn(getResponse(new UpdatePsuAuthenticationResponse()));

        Response<UpdatePsuAuthenticationResponse> actualResponse = service.updatePaymentPsuData(PAYMENTS,
            SEPA_CREDIT_TRANSFERS,
            PAYMENT_ID,
            AUTHORIZATION_ID,
            RequestHeaders.fromMap(singletonMap(AUTHORIZATION, AUTHORIZATION_VALUE)),
            RequestParams.empty(),
            new UpdatePsuAuthentication());

        assertThat(actualResponse)
            .isNotNull();
    }

    @Test
    void testUpdatePaymentPsuData() {
        when(httpClient.put(anyString()))
            .thenReturn(getRequestBuilder("PUT"));
        when(httpClient.send(any(), any()))
            .thenReturn(getResponse(new SelectPsuAuthenticationMethodResponse()));

        Response<SelectPsuAuthenticationMethodResponse> actualResponse = service.updatePaymentPsuData(PAYMENTS,
            SEPA_CREDIT_TRANSFERS,
            PAYMENT_ID,
            AUTHORIZATION_ID,
            RequestHeaders.fromMap(singletonMap(AUTHORIZATION, AUTHORIZATION_VALUE)),
            RequestParams.empty(),
            new SelectPsuAuthenticationMethod());

        assertThat(actualResponse)
            .isNotNull();
    }

    @Test
    void testUpdatePaymentPsuData1() {
        when(httpClient.put(anyString()))
            .thenReturn(getRequestBuilder("PUT"));
        when(httpClient.send(any(), any()))
            .thenReturn(getResponse(new ScaStatusResponse()));

        Response<ScaStatusResponse> actualResponse = service.updatePaymentPsuData(PAYMENTS,
            SEPA_CREDIT_TRANSFERS,
            PAYMENT_ID,
            AUTHORIZATION_ID,
            RequestHeaders.fromMap(singletonMap(AUTHORIZATION, AUTHORIZATION_VALUE)),
            RequestParams.empty(),
            new TransactionAuthorisation());

        assertThat(actualResponse)
            .isNotNull();
    }

    private <T> Response<T> getResponse(T body) {
        return new Response<>(-1, body, ResponseHeaders.emptyResponseHeaders());
    }

    private Request.Builder getRequestBuilder(String method) {
        return new RequestBuilderImpl(httpClient, method, "");
    }

    private CrealogixPaymentInitiationWithStatusResponse getResponseBody() {
        return new CrealogixPaymentInitiationWithStatusResponse();
    }

    private Aspsp getAspsp() {
        Aspsp aspsp = new Aspsp();
        aspsp.setUrl("https://url.com");
        return aspsp;
    }
}
