package de.adorsys.xs2a.adapter.service.impl;

import de.adorsys.xs2a.adapter.api.model.*;
import de.adorsys.xs2a.adapter.http.HttpClient;
import de.adorsys.xs2a.adapter.http.Request;
import de.adorsys.xs2a.adapter.impl.http.RequestBuilderImpl;
import de.adorsys.xs2a.adapter.service.RequestHeaders;
import de.adorsys.xs2a.adapter.service.RequestParams;
import de.adorsys.xs2a.adapter.service.Response;
import de.adorsys.xs2a.adapter.service.ResponseHeaders;
import de.adorsys.xs2a.adapter.service.link.LinksRewriter;
import de.adorsys.xs2a.adapter.service.model.Aspsp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static de.adorsys.xs2a.adapter.service.impl.UnicreditHeaders.DEFAULT_PSU_ID_TYPE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class UnicreditPaymentInitiationServiceTest {

    private static final String BASE_URL = "https://simulator-xs2a.db.com/p9is/DE/SB-DB";
    private static final Aspsp ASPSP = buildAspspWithUrl();
    private static final String INITIATE_PAYMENT_URL = BASE_URL + "/v1/payments/sepa-credit-transfers";
    private static final String PAYMENT_ID = "payment-id";
    private static final String PAYMENT_ID_URL = INITIATE_PAYMENT_URL + "/" + PAYMENT_ID;
    private static final String WRONG_PSU_ID_TYPE = "PSU_ID_TYPE";
    private static final String AUTHORISATION_ID = "authorisation-id";
    private static final String AUTHORISATION_URL = PAYMENT_ID_URL + "?authenticationCurrentNumber=" + AUTHORISATION_ID;
    private static final String TPP_REDIRECT_URI = "http://example.com";
    private static final String POST_METHOD = "POST";
    private HttpClient httpClient;
    private LinksRewriter linksRewriter;
    private UnicreditPaymentInitiationService paymentInitiationService;

    @BeforeEach
    void setUp() {
        httpClient = mock(HttpClient.class);
        linksRewriter = mock(LinksRewriter.class);
        paymentInitiationService = new UnicreditPaymentInitiationService(ASPSP, httpClient, linksRewriter);
    }

    @Test
    void initiatePayment_wrongPsuIdTypeValue() {
        Map<String, String> headersMap = new HashMap<>();
        headersMap.put(RequestHeaders.PSU_ID_TYPE, WRONG_PSU_ID_TYPE);
        headersMap.put(RequestHeaders.TPP_REDIRECT_URI, TPP_REDIRECT_URI);

        Request.Builder requestBuilder = new RequestBuilderImpl(httpClient, POST_METHOD, INITIATE_PAYMENT_URL);
        when(httpClient.post(eq(INITIATE_PAYMENT_URL)))
            .thenReturn(requestBuilder);
        when(httpClient.send(any(), any()))
            .thenReturn(new Response<>(200, new PaymentInitationRequestResponse201(), ResponseHeaders.fromMap(headersMap)));

        paymentInitiationService.initiatePayment(PaymentService.PAYMENTS.toString(),
                                                 PaymentProduct.SEPA_CREDIT_TRANSFERS.toString(),
                                                 RequestHeaders.fromMap(headersMap),
                                                 RequestParams.empty(),
                                                 new PaymentInitiationJson());

        verify(httpClient, times(1)).post(eq(INITIATE_PAYMENT_URL));
        assertThat(requestBuilder.headers()).containsEntry(RequestHeaders.PSU_ID_TYPE, DEFAULT_PSU_ID_TYPE);
    }

    @Test
    void initiatePayment_defaultPsuIdTypeValue() {
        Map<String, String> headersMap = new HashMap<>();
        headersMap.put(RequestHeaders.PSU_ID_TYPE, DEFAULT_PSU_ID_TYPE);
        headersMap.put(RequestHeaders.TPP_REDIRECT_URI, TPP_REDIRECT_URI);

        Request.Builder requestBuilder = new RequestBuilderImpl(httpClient, POST_METHOD, INITIATE_PAYMENT_URL);
        when(httpClient.post(eq(INITIATE_PAYMENT_URL)))
            .thenReturn(requestBuilder);
        when(httpClient.send(any(), any()))
            .thenReturn(new Response<>(200, new PaymentInitationRequestResponse201(), ResponseHeaders.fromMap(headersMap)));

        paymentInitiationService.initiatePayment(PaymentService.PAYMENTS.toString(),
                                                 PaymentProduct.SEPA_CREDIT_TRANSFERS.toString(),
                                                 RequestHeaders.fromMap(headersMap),
                                                 RequestParams.empty(),
                                                 new PaymentInitiationJson());

        verify(httpClient, times(1)).post(eq(INITIATE_PAYMENT_URL));
        assertThat(requestBuilder.headers()).containsEntry(RequestHeaders.PSU_ID_TYPE, DEFAULT_PSU_ID_TYPE);
    }

    @Test
    void initiatePayment_alternativeAcceptedPsuIdTypeValue() {
        Map<String, String> headersMap = new HashMap<>();
        headersMap.put(RequestHeaders.PSU_ID_TYPE, UnicreditHeaders.UCE_BANKING_GLOBAL);
        headersMap.put(RequestHeaders.TPP_REDIRECT_URI, TPP_REDIRECT_URI);

        Request.Builder requestBuilder = new RequestBuilderImpl(httpClient, POST_METHOD, INITIATE_PAYMENT_URL);
        when(httpClient.post(eq(INITIATE_PAYMENT_URL)))
            .thenReturn(requestBuilder);
        when(httpClient.send(any(), any()))
            .thenReturn(new Response<>(200, new PaymentInitationRequestResponse201(), ResponseHeaders.fromMap(headersMap)));

        paymentInitiationService.initiatePayment(PaymentService.PAYMENTS.toString(),
                                                 PaymentProduct.SEPA_CREDIT_TRANSFERS.toString(),
                                                 RequestHeaders.fromMap(headersMap),
                                                 RequestParams.empty(),
                                                 new PaymentInitiationJson());

        verify(httpClient, times(1)).post(eq(INITIATE_PAYMENT_URL));
        assertThat(requestBuilder.headers()).containsEntry(RequestHeaders.PSU_ID_TYPE, UnicreditHeaders.UCE_BANKING_GLOBAL);
    }

    @Test
    void updatePaymentPsuData() {
        Request.Builder requestBuilder = spy(new RequestBuilderImpl(httpClient, "PUT", AUTHORISATION_URL));
        ScaStatusResponse statusResponse = new ScaStatusResponse();

        when(httpClient.put(anyString())).thenReturn(requestBuilder);
        doReturn(new Response<>(200,
                                statusResponse,
                                ResponseHeaders.fromMap(Collections.emptyMap()))).when(requestBuilder).send(any(), any());

        paymentInitiationService.updatePaymentPsuData(PaymentService.PAYMENTS.toString(),
                                                      PaymentProduct.SEPA_CREDIT_TRANSFERS.toString(),
                                                      PAYMENT_ID,
                                                      AUTHORISATION_ID,
                                                      RequestHeaders.fromMap(Collections.emptyMap()),
                                                      RequestParams.empty(),
                                                      new TransactionAuthorisation());

        assertThat(requestBuilder.headers()).containsEntry(RequestHeaders.PSU_ID_TYPE, DEFAULT_PSU_ID_TYPE);
    }

    private static Aspsp buildAspspWithUrl() {
        Aspsp aspsp = new Aspsp();
        aspsp.setUrl(BASE_URL);
        return aspsp;
    }
}
