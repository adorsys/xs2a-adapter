package de.adorsys.xs2a.adapter.adapter;

import de.adorsys.xs2a.adapter.http.HttpClient;
import de.adorsys.xs2a.adapter.http.Request;
import de.adorsys.xs2a.adapter.http.RequestBuilderImpl;
import de.adorsys.xs2a.adapter.service.RequestHeaders;
import de.adorsys.xs2a.adapter.service.RequestParams;
import de.adorsys.xs2a.adapter.service.Response;
import de.adorsys.xs2a.adapter.service.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class BasePaymentInitiationServiceTest {

    public static final String SEPA_CREDIT_TRANSFERS = "sepa-credit-transfers";
    public static final String PAIN_SEPA_CREDIT_TRANSFERS = "pain.001-sepa-credit-transfers";
    public static final String BASE_URI = "https://base.uri";
    public static final String PAYMENTS_URI = BASE_URI + "/v1/payments";
    public static final String PAYMENTID = "paymentId";
    public static final String AUTHORISATIONID = "authorisationId";
    public static final String PAYMENT_SERVICE = "paymentService";
    public static final String UPDATE_PAYMENT_PSU_DATA_URI = BASE_URI + "/v1/" + PAYMENT_SERVICE + "/" +
        SEPA_CREDIT_TRANSFERS + "/" + PAYMENTID + "/authorisations/" + AUTHORISATIONID;

    private RequestHeaders headers = RequestHeaders.fromMap(new HashMap<>());

    @Mock
    private HttpClient httpClient;

    @Mock
    private Request.Builder.Interceptor interceptor;

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
        initiationService = new BasePaymentInitiationService(BASE_URI, httpClient, interceptor);
    }

    @Test
    void initiateSinglePayment_sepaCreditTransfers() {
        Object body = new SinglePaymentInitiationBody();
        PaymentInitiationRequestResponse example = new PaymentInitiationRequestResponse();

        when(httpClient.post(any())).thenReturn(requestBuilder);
        doReturn(dummyResponse(example)).when(requestBuilder).send(argThat(inter -> Objects.equals(inter, interceptor)), any());

        Response<PaymentInitiationRequestResponse> response
            = initiationService.initiateSinglePayment(SEPA_CREDIT_TRANSFERS, headers, RequestParams.empty(), body);

        verify(httpClient, times(1)).post(uriCaptor.capture());
        verify(requestBuilder, times(1)).jsonBody(bodyCaptor.capture());
        verify(requestBuilder, times(1)).headers(headersCaptor.capture());
        verify(requestBuilder, times(1)).send(any(), any());

        assertThat(uriCaptor.getValue()).isEqualTo(PAYMENTS_URI + "/" + SEPA_CREDIT_TRANSFERS);
        assertThat(bodyCaptor.getValue()).isEqualTo("{}");
        assertThat(headersCaptor.getValue()).isEqualTo(headers.toMap());
        assertThat(response.getBody()).isEqualTo(example);
    }

    @Test
    void initiateSinglePayment_painSepaCreditTransfers() {
        Object body = "body";
        PaymentInitiationRequestResponse example = new PaymentInitiationRequestResponse();
        Map<String, String> painHeaders = headers.toMap();
        painHeaders.put(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_XML);

        when(httpClient.post(any())).thenReturn(requestBuilder);
        doReturn(dummyResponse(example)).when(requestBuilder).send(argThat(inter -> Objects.equals(inter, interceptor)), any());

        Response<PaymentInitiationRequestResponse> response
            = initiationService.initiateSinglePayment(PAIN_SEPA_CREDIT_TRANSFERS, headers, RequestParams.empty(), body);

        verify(httpClient, times(1)).post(uriCaptor.capture());
        verify(requestBuilder, times(1)).jsonBody(bodyCaptor.capture());
        verify(requestBuilder, times(1)).headers(headersCaptor.capture());
        verify(requestBuilder, times(1)).send(any(), any());

        assertThat(uriCaptor.getValue()).isEqualTo(PAYMENTS_URI + "/" + PAIN_SEPA_CREDIT_TRANSFERS);
        assertThat(bodyCaptor.getValue()).isEqualTo("body");
        assertThat(headersCaptor.getValue()).isEqualTo(painHeaders);
        assertThat(response.getBody()).isEqualTo(example);
    }

    @Test
    void getSinglePaymentInformation() {
        SinglePaymentInitiationInformationWithStatusResponse example = new SinglePaymentInitiationInformationWithStatusResponse();

        when(httpClient.get(any())).thenReturn(requestBuilder);
        doReturn(dummyResponse(example)).when(requestBuilder).send(argThat(inter -> Objects.equals(inter, interceptor)), any());

        Response<SinglePaymentInitiationInformationWithStatusResponse> response
            = initiationService.getSinglePaymentInformation(SEPA_CREDIT_TRANSFERS, PAYMENTID, headers, RequestParams.empty());

        verify(httpClient, times(1)).get(uriCaptor.capture());
        verify(requestBuilder, times(1)).headers(headersCaptor.capture());
        verify(requestBuilder, times(1)).send(any(), any());

        assertThat(uriCaptor.getValue()).isEqualTo(PAYMENTS_URI + "/" + SEPA_CREDIT_TRANSFERS + "/" + PAYMENTID);
        assertThat(headersCaptor.getValue()).isEqualTo(headers.toMap());
        assertThat(response.getBody()).isEqualTo(example);
    }

    @Test
    void getPaymentInitiationScaStatus_exceptionExpected() {

        assertThrows(UnsupportedOperationException.class,
            () -> initiationService.getPaymentInitiationScaStatus(null, null, null, null, null));
    }

    @Test
    void getSinglePaymentInitiationStatus() {
        PaymentInitiationStatus example = new PaymentInitiationStatus();

        when(httpClient.get(any())).thenReturn(requestBuilder);
        doReturn(dummyResponse(example)).when(requestBuilder).send(argThat(inter -> Objects.equals(inter, interceptor)), any());

        Response<PaymentInitiationStatus> response
            = initiationService.getSinglePaymentInitiationStatus(SEPA_CREDIT_TRANSFERS, PAYMENTID, headers);

        verify(httpClient, times(1)).get(uriCaptor.capture());
        verify(requestBuilder, times(1)).headers(headersCaptor.capture());
        verify(requestBuilder, times(1)).send(any(), any());

        assertThat(uriCaptor.getValue()).isEqualTo(PAYMENTS_URI + "/" + SEPA_CREDIT_TRANSFERS + "/" + PAYMENTID + "/status");
        assertThat(headersCaptor.getValue()).isEqualTo(headers.toMap());
        assertThat(response.getBody()).isEqualTo(example);
    }

    @Test
    void getSinglePaymentInitiationStatus_asString() {
        String example = "statusResponse";

        when(httpClient.get(any())).thenReturn(requestBuilder);
        doReturn(dummyResponse(example)).when(requestBuilder).send(argThat(inter -> Objects.equals(inter, interceptor)), any());

        Response<String> response
            = initiationService.getSinglePaymentInitiationStatusAsString(SEPA_CREDIT_TRANSFERS, PAYMENTID, headers);

        verify(httpClient, times(1)).get(uriCaptor.capture());
        verify(requestBuilder, times(1)).headers(headersCaptor.capture());
        verify(requestBuilder, times(1)).send(any(), any());

        assertThat(uriCaptor.getValue()).isEqualTo(PAYMENTS_URI + "/" + SEPA_CREDIT_TRANSFERS + "/" + PAYMENTID + "/status");
        assertThat(headersCaptor.getValue()).isEqualTo(headers.toMap());
        assertThat(response.getBody()).isEqualTo(example);
    }

    @Test
    void getPaymentInitiationAuthorisation_exceptionExpected() {

        assertThrows(UnsupportedOperationException.class,
            () -> initiationService.getPaymentInitiationAuthorisation(null, null, null, null));
    }

    @Test
    void startSinglePaymentAuthorisation() {
        UpdatePsuAuthentication body = new UpdatePsuAuthentication();
        StartScaProcessResponse example = new StartScaProcessResponse();

        when(httpClient.post(any())).thenReturn(requestBuilder);
        doReturn(dummyResponse(example)).when(requestBuilder).send(argThat(inter -> Objects.equals(inter, interceptor)), any());

        Response<StartScaProcessResponse> response
            = initiationService.startSinglePaymentAuthorisation(SEPA_CREDIT_TRANSFERS, PAYMENTID, headers, body);

        verify(httpClient, times(1)).post(uriCaptor.capture());
        verify(requestBuilder, times(1)).jsonBody(bodyCaptor.capture());
        verify(requestBuilder, times(1)).headers(headersCaptor.capture());
        verify(requestBuilder, times(1)).send(any(), any());

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
        doReturn(dummyResponse(example)).when(requestBuilder).send(argThat(inter -> Objects.equals(inter, interceptor)), any());

        Response<UpdatePsuAuthenticationResponse> response
            = initiationService.updatePaymentPsuData(PAYMENT_SERVICE, SEPA_CREDIT_TRANSFERS, PAYMENTID, AUTHORISATIONID, headers, body);

        verify(httpClient, times(1)).put(uriCaptor.capture());
        verify(requestBuilder, times(1)).jsonBody(bodyCaptor.capture());
        verify(requestBuilder, times(1)).headers(headersCaptor.capture());
        verify(requestBuilder, times(1)).send(any(), any());

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
        doReturn(dummyResponse(example)).when(requestBuilder).send(argThat(inter -> Objects.equals(inter, interceptor)), any());

        Response<SelectPsuAuthenticationMethodResponse> response
            = initiationService.updatePaymentPsuData(PAYMENT_SERVICE, SEPA_CREDIT_TRANSFERS, PAYMENTID, AUTHORISATIONID, headers, body);

        verify(httpClient, times(1)).put(uriCaptor.capture());
        verify(requestBuilder, times(1)).jsonBody(bodyCaptor.capture());
        verify(requestBuilder, times(1)).headers(headersCaptor.capture());
        verify(requestBuilder, times(1)).send(any(), any());

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
        doReturn(dummyResponse(example)).when(requestBuilder).send(argThat(inter -> Objects.equals(inter, interceptor)), any());

        Response<ScaStatusResponse> response
            = initiationService.updatePaymentPsuData(PAYMENT_SERVICE, SEPA_CREDIT_TRANSFERS, PAYMENTID, AUTHORISATIONID, headers, body);

        verify(httpClient, times(1)).put(uriCaptor.capture());
        verify(requestBuilder, times(1)).jsonBody(bodyCaptor.capture());
        verify(requestBuilder, times(1)).headers(headersCaptor.capture());
        verify(requestBuilder, times(1)).send(any(), any());

        assertThat(uriCaptor.getValue()).isEqualTo(UPDATE_PAYMENT_PSU_DATA_URI);
        assertThat(bodyCaptor.getValue()).isEqualTo("{}");
        assertThat(headersCaptor.getValue()).isEqualTo(headers.toMap());
        assertThat(response.getBody()).isEqualTo(example);
    }

    private <T> Response<T> dummyResponse(T body) {
        return new Response<>(-1, body, null);
    }
}
