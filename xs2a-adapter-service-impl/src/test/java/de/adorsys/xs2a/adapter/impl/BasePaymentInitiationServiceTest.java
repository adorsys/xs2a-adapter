package de.adorsys.xs2a.adapter.impl;

import de.adorsys.xs2a.adapter.api.RequestHeaders;
import de.adorsys.xs2a.adapter.api.RequestParams;
import de.adorsys.xs2a.adapter.api.Response;
import de.adorsys.xs2a.adapter.api.http.HttpClient;
import de.adorsys.xs2a.adapter.api.http.Interceptor;
import de.adorsys.xs2a.adapter.api.http.Request;
import de.adorsys.xs2a.adapter.api.model.*;
import de.adorsys.xs2a.adapter.impl.http.RequestBuilderImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class BasePaymentInitiationServiceTest {

    private static final PaymentService PAYMENT_SERVICE = PaymentService.PAYMENTS;
    public static final PaymentProduct SEPA_CREDIT_TRANSFERS = PaymentProduct.SEPA_CREDIT_TRANSFERS;
    public static final PaymentProduct PAIN_SEPA_CREDIT_TRANSFERS = PaymentProduct.PAIN_001_SEPA_CREDIT_TRANSFERS;
    public static final String BASE_URI = "https://base.uri";
    private static final String IDP_URL = "https://idp.url";
    public static final String PAYMENTS_URI = BASE_URI + "/v1/payments";
    public static final String PAYMENTID = "paymentId";
    public static final String AUTHORISATIONID = "authorisationId";
    public static final String UPDATE_PAYMENT_PSU_DATA_URI = BASE_URI + "/v1/" + PAYMENT_SERVICE + "/" +
                                                                 SEPA_CREDIT_TRANSFERS + "/" + PAYMENTID + "/authorisations/" + AUTHORISATIONID;
    private static final Aspsp ASPSP = buildAspspWithUrls();

    private RequestHeaders headers = RequestHeaders.fromMap(new HashMap<>());

    @Mock
    private HttpClient httpClient;

    @Mock
    private Interceptor interceptor;

    @Spy
    private Request.Builder requestBuilder = new RequestBuilderImpl(httpClient, null, null);

    @Captor
    private ArgumentCaptor<String> uriCaptor, bodyCaptor;

    @Captor
    private ArgumentCaptor<Map<String, String>> headersCaptor;

    private BasePaymentInitiationService initiationService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        initiationService = new BasePaymentInitiationService(ASPSP, httpClient, Collections.singletonList(interceptor));
    }

    @Test
    void initiatePayment_singlePaymentsSepaCreditTransfers() {
        PaymentInitiationJson body = new PaymentInitiationJson();
        PaymentInitationRequestResponse201 example = new PaymentInitationRequestResponse201();

        when(httpClient.post(any())).thenReturn(requestBuilder);
        doReturn(dummyResponse(example)).when(requestBuilder).send(any(), eq(Collections.singletonList(interceptor)));

        Response<PaymentInitationRequestResponse201> response
            = initiationService.initiatePayment(PAYMENT_SERVICE, SEPA_CREDIT_TRANSFERS, headers, RequestParams.empty(), body);

        verify(httpClient, times(1)).post(uriCaptor.capture());
        verify(requestBuilder, times(1)).jsonBody(bodyCaptor.capture());
        verify(requestBuilder, times(1)).headers(headersCaptor.capture());
        verify(requestBuilder, times(1)).send(any(), eq(Collections.singletonList(interceptor)));

        assertThat(uriCaptor.getValue()).isEqualTo(PAYMENTS_URI + "/" + SEPA_CREDIT_TRANSFERS);
        assertThat(bodyCaptor.getValue()).isEqualTo("{}");
        assertThat(headersCaptor.getValue()).isEqualTo(headers.toMap());
        assertThat(response.getBody()).isEqualTo(example);
    }

    @Test
    void initiatePayment_singlePaymentsPainSepaCreditTransfers() {
        Object body = "body";
        PaymentInitationRequestResponse201 example = new PaymentInitationRequestResponse201();

        when(httpClient.post(any())).thenReturn(requestBuilder);
        doReturn(dummyResponse(example)).when(requestBuilder).send(any(), eq(Collections.singletonList(interceptor)));

        Response<PaymentInitationRequestResponse201> response
            = initiationService.initiatePayment(PAYMENT_SERVICE, PAIN_SEPA_CREDIT_TRANSFERS, headers, RequestParams.empty(), body);

        verify(httpClient, times(1)).post(uriCaptor.capture());
        verify(requestBuilder, times(1)).xmlBody(bodyCaptor.capture());
        verify(requestBuilder, times(1)).send(any(), eq(Collections.singletonList(interceptor)));

        assertThat(uriCaptor.getValue()).isEqualTo(PAYMENTS_URI + "/" + PAIN_SEPA_CREDIT_TRANSFERS);
        assertThat(bodyCaptor.getValue()).isEqualTo("body");
        assertThat(response.getBody()).isEqualTo(example);
    }

    @Test
    void getSinglePaymentInformation() {
        PaymentInitiationWithStatusResponse example = new PaymentInitiationWithStatusResponse();

        when(httpClient.get(any())).thenReturn(requestBuilder);
        doReturn(dummyResponse(example)).when(requestBuilder).send(any(), eq(Collections.singletonList(interceptor)));

        Response<PaymentInitiationWithStatusResponse> response
            = initiationService.getSinglePaymentInformation(SEPA_CREDIT_TRANSFERS, PAYMENTID, headers, RequestParams.empty());

        verify(httpClient, times(1)).get(uriCaptor.capture());
        verify(requestBuilder, times(1)).headers(headersCaptor.capture());
        verify(requestBuilder, times(1)).send(any(), eq(Collections.singletonList(interceptor)));

        assertThat(uriCaptor.getValue()).isEqualTo(PAYMENTS_URI + "/" + SEPA_CREDIT_TRANSFERS + "/" + PAYMENTID);
        assertThat(headersCaptor.getValue()).isEqualTo(headers.toMap());
        assertThat(response.getBody()).isEqualTo(example);
    }

    @Test
    void getPaymentInitiationScaStatus() {
        ScaStatusResponse example = new ScaStatusResponse();
        when(httpClient.get(any())).thenReturn(requestBuilder);
        doReturn(dummyResponse(example)).when(requestBuilder).send(any(), eq(Collections.singletonList(interceptor)));

        Response<ScaStatusResponse> response = initiationService.getPaymentInitiationScaStatus(PAYMENT_SERVICE,
                                                                                               SEPA_CREDIT_TRANSFERS,
                                                                                               PAYMENTID,
                                                                                               AUTHORISATIONID,
                                                                                               RequestHeaders.empty(),
                                                                                               RequestParams.empty());

        verify(httpClient, times(1)).get(uriCaptor.capture());
        verify(requestBuilder, times(1)).headers(headersCaptor.capture());
        verify(requestBuilder, times(1)).send(any(), eq(Collections.singletonList(interceptor)));
        assertThat(uriCaptor.getValue())
            .isEqualTo(PAYMENTS_URI + "/" + SEPA_CREDIT_TRANSFERS + "/" + PAYMENTID + "/authorisations/" + AUTHORISATIONID);
        assertThat(headersCaptor.getValue()).isEqualTo(headers.toMap());
        assertThat(response.getBody()).isEqualTo(example);
    }

    @Test
    void getPaymentInitiationStatus() {
        PaymentInitiationStatusResponse200Json example = new PaymentInitiationStatusResponse200Json();

        when(httpClient.get(any())).thenReturn(requestBuilder);
        doReturn(dummyResponse(example)).when(requestBuilder).send(any(), eq(Collections.singletonList(interceptor)));

        Response<PaymentInitiationStatusResponse200Json> response = initiationService.getPaymentInitiationStatus(
            PAYMENT_SERVICE,
            SEPA_CREDIT_TRANSFERS,
            PAYMENTID,
            headers,
            RequestParams.empty());

        verify(httpClient, times(1)).get(uriCaptor.capture());
        verify(requestBuilder, times(1)).headers(headersCaptor.capture());
        verify(requestBuilder, times(1)).send(any(), eq(Collections.singletonList(interceptor)));

        assertThat(uriCaptor.getValue()).isEqualTo(PAYMENTS_URI + "/" + SEPA_CREDIT_TRANSFERS + "/" + PAYMENTID + "/status");
        assertThat(headersCaptor.getValue()).isEqualTo(headers.toMap());
        assertThat(response.getBody()).isEqualTo(example);
    }

    @Test
    void getSinglePaymentInitiationStatus_asString() {
        String example = "statusResponse";

        when(httpClient.get(any())).thenReturn(requestBuilder);
        doReturn(dummyResponse(example)).when(requestBuilder).send(any(), eq(Collections.singletonList(interceptor)));

        Response<String> response = initiationService.getPaymentInitiationStatusAsString(
            PAYMENT_SERVICE,
            SEPA_CREDIT_TRANSFERS,
            PAYMENTID,
            headers,
            RequestParams.empty());

        verify(httpClient, times(1)).get(uriCaptor.capture());
        verify(requestBuilder, times(1)).headers(headersCaptor.capture());
        verify(requestBuilder, times(1)).send(any(), eq(Collections.singletonList(interceptor)));

        assertThat(uriCaptor.getValue()).isEqualTo(PAYMENTS_URI + "/" + SEPA_CREDIT_TRANSFERS + "/" + PAYMENTID + "/status");
        assertThat(headersCaptor.getValue()).isEqualTo(headers.toMap());
        assertThat(response.getBody()).isEqualTo(example);
    }

    @Test
    void getPaymentInitiationAuthorisation() {
        Authorisations example = new Authorisations();
        when(httpClient.get(any())).thenReturn(requestBuilder);
        doReturn(dummyResponse(example)).when(requestBuilder).send(any(), eq(Collections.singletonList(interceptor)));

        Response<Authorisations> response = initiationService.getPaymentInitiationAuthorisation(PAYMENT_SERVICE,
                                                                                                SEPA_CREDIT_TRANSFERS,
                                                                                                PAYMENTID,
                                                                                                RequestHeaders.empty(),
                                                                                                RequestParams.empty());

        verify(httpClient, times(1)).get(uriCaptor.capture());
        verify(requestBuilder, times(1)).headers(headersCaptor.capture());
        verify(requestBuilder, times(1)).send(any(), eq(Collections.singletonList(interceptor)));
        assertThat(uriCaptor.getValue()).isEqualTo(PAYMENTS_URI + "/" + SEPA_CREDIT_TRANSFERS + "/" + PAYMENTID + "/authorisations");
        assertThat(headersCaptor.getValue()).isEqualTo(headers.toMap());
        assertThat(response.getBody()).isEqualTo(example);
    }

    @Test
    void startSinglePaymentAuthorisation() {
        UpdatePsuAuthentication body = new UpdatePsuAuthentication();
        StartScaprocessResponse example = new StartScaprocessResponse();

        when(httpClient.post(any())).thenReturn(requestBuilder);
        doReturn(dummyResponse(example)).when(requestBuilder).send(any(), eq(Collections.singletonList(interceptor)));

        Response<StartScaprocessResponse> response =
            initiationService.startPaymentAuthorisation(PAYMENT_SERVICE,
                                                        SEPA_CREDIT_TRANSFERS,
                                                        PAYMENTID,
                                                        headers,
                                                        RequestParams.empty(),
                                                        body);

        verify(httpClient, times(1)).post(uriCaptor.capture());
        verify(requestBuilder, times(1)).jsonBody(bodyCaptor.capture());
        verify(requestBuilder, times(1)).headers(headersCaptor.capture());
        verify(requestBuilder, times(1)).send(any(), eq(Collections.singletonList(interceptor)));

        assertThat(uriCaptor.getValue()).isEqualTo(PAYMENTS_URI + "/" + SEPA_CREDIT_TRANSFERS + "/" + PAYMENTID + "/authorisations");
        assertThat(bodyCaptor.getValue()).isEqualTo("{}");
        assertThat(headersCaptor.getValue()).isEqualTo(headers.toMap());
        assertThat(response.getBody()).isEqualTo(example);
    }

    @Test
    void updatePaymentPsuData_passwordStage() {
        UpdatePsuAuthentication body = new UpdatePsuAuthentication();
        UpdatePsuAuthenticationResponse example = new UpdatePsuAuthenticationResponse();

        when(httpClient.put(any())).thenReturn(requestBuilder);
        doReturn(dummyResponse(example)).when(requestBuilder).send(any(), eq(Collections.singletonList(interceptor)));

        Response<UpdatePsuAuthenticationResponse> response =
            initiationService.updatePaymentPsuData(PAYMENT_SERVICE,
                                                   SEPA_CREDIT_TRANSFERS,
                                                   PAYMENTID,
                                                   AUTHORISATIONID,
                                                   headers,
                                                   RequestParams.empty(),
                                                   body);

        verify(httpClient, times(1)).put(uriCaptor.capture());
        verify(requestBuilder, times(1)).jsonBody(bodyCaptor.capture());
        verify(requestBuilder, times(1)).headers(headersCaptor.capture());
        verify(requestBuilder, times(1)).send(any(), eq(Collections.singletonList(interceptor)));

        assertThat(uriCaptor.getValue()).isEqualTo(UPDATE_PAYMENT_PSU_DATA_URI);
        assertThat(bodyCaptor.getValue()).isEqualTo("{}");
        assertThat(headersCaptor.getValue()).isEqualTo(headers.toMap());
        assertThat(response.getBody()).isEqualTo(example);
    }

    @Test
    void updatePaymentPsuData_selectScaMethodStage() {
        SelectPsuAuthenticationMethod body = new SelectPsuAuthenticationMethod();
        SelectPsuAuthenticationMethodResponse example = new SelectPsuAuthenticationMethodResponse();

        when(httpClient.put(any())).thenReturn(requestBuilder);
        doReturn(dummyResponse(example)).when(requestBuilder).send(any(), eq(Collections.singletonList(interceptor)));

        Response<SelectPsuAuthenticationMethodResponse> response =
            initiationService.updatePaymentPsuData(PAYMENT_SERVICE,
                                                   SEPA_CREDIT_TRANSFERS,
                                                   PAYMENTID,
                                                   AUTHORISATIONID,
                                                   headers,
                                                   RequestParams.empty(),
                                                   body);

        verify(httpClient, times(1)).put(uriCaptor.capture());
        verify(requestBuilder, times(1)).jsonBody(bodyCaptor.capture());
        verify(requestBuilder, times(1)).headers(headersCaptor.capture());
        verify(requestBuilder, times(1)).send(any(), eq(Collections.singletonList(interceptor)));

        assertThat(uriCaptor.getValue()).isEqualTo(UPDATE_PAYMENT_PSU_DATA_URI);
        assertThat(bodyCaptor.getValue()).isEqualTo("{}");
        assertThat(headersCaptor.getValue()).isEqualTo(headers.toMap());
        assertThat(response.getBody()).isEqualTo(example);
    }

    @Test
    void updatePaymentPsuData_otpStage() {
        TransactionAuthorisation body = new TransactionAuthorisation();
        ScaStatusResponse example = new ScaStatusResponse();

        when(httpClient.put(any())).thenReturn(requestBuilder);
        doReturn(dummyResponse(example)).when(requestBuilder).send(any(), eq(Collections.singletonList(interceptor)));

        Response<ScaStatusResponse> response =
            initiationService.updatePaymentPsuData(PAYMENT_SERVICE,
                                                   SEPA_CREDIT_TRANSFERS,
                                                   PAYMENTID,
                                                   AUTHORISATIONID,
                                                   headers,
                                                   RequestParams.empty(),
                                                   body);

        verify(httpClient, times(1)).put(uriCaptor.capture());
        verify(requestBuilder, times(1)).jsonBody(bodyCaptor.capture());
        verify(requestBuilder, times(1)).headers(headersCaptor.capture());
        verify(requestBuilder, times(1)).send(any(), eq(Collections.singletonList(interceptor)));

        assertThat(uriCaptor.getValue()).isEqualTo(UPDATE_PAYMENT_PSU_DATA_URI);
        assertThat(bodyCaptor.getValue()).isEqualTo("{}");
        assertThat(headersCaptor.getValue()).isEqualTo(headers.toMap());
        assertThat(response.getBody()).isEqualTo(example);
    }

    private <T> Response<T> dummyResponse(T body) {
        return new Response<>(-1, body, null);
    }

    private static Aspsp buildAspspWithUrls() {
        Aspsp aspsp = new Aspsp();
        aspsp.setUrl(BASE_URI);
        aspsp.setIdpUrl(IDP_URL);
        return aspsp;
    }
}
