package de.adorsys.xs2a.adapter.remote;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import de.adorsys.xs2a.adapter.api.RequestHeaders;
import de.adorsys.xs2a.adapter.api.RequestParams;
import de.adorsys.xs2a.adapter.api.Response;
import de.adorsys.xs2a.adapter.api.ResponseHeaders;
import de.adorsys.xs2a.adapter.api.model.*;
import de.adorsys.xs2a.adapter.remote.client.PaymentInitiationClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.util.Collection;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

class RemotePaymentInitiationServiceTest {
    public static final String CONSENT_ID = "consentId";
    public static final String CONTENT_TYPE = "application/json";
    public static final String X_REQUEST_ID = "1234567";
    public static final int HTTP_STATUS_CREATED = 201;
    public static final int HTTP_STATUS_OK = 200;
    public static final String AUTHORIZATION_ID = "AUTHORIZATION_ID";
    public static final String PAYMENT_ID = "payment-id";

    private RemotePaymentInitiationService service;
    private PaymentInitiationClient client;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final ResponseEntity entity = mock(ResponseEntity.class);

    @BeforeEach
    public void setUp() {
        client = mock(PaymentInitiationClient.class);
        service = new RemotePaymentInitiationService(client);
    }

    @Test
    void initiatePayment_ObjectNode() {

        PaymentInitationRequestResponse201 responseBody = new PaymentInitationRequestResponse201();

        doReturn(entity).when(client).initiatePayment(eq(PaymentService.PAYMENTS),
                                                      eq(PaymentProduct.SEPA_CREDIT_TRANSFERS),
                                                      anyMap(),
                                                      anyMap(),
                                                      any(ObjectNode.class));
        doReturn(HTTP_STATUS_CREATED).when(entity).getStatusCodeValue();
        doReturn(responseBody).when(entity).getBody();
        doReturn(buildHttpHeaders()).when(entity).getHeaders();

        Response<PaymentInitationRequestResponse201> response = service.initiatePayment(PaymentService.PAYMENTS,
                                                                                        PaymentProduct.SEPA_CREDIT_TRANSFERS,
                                                                                        RequestHeaders.empty(),
                                                                                        RequestParams.empty(),
                                                                                        new PaymentInitiationJson());

        assertThat(response.getBody()).isEqualTo(responseBody);
        assertResponseHeaders(response.getHeaders());
    }

    @Test
    void initiatePayment_Periodic() {

        PaymentInitationRequestResponse201 responseBody = new PaymentInitationRequestResponse201();

        doReturn(entity).when(client).initiatePayment(eq(PaymentService.PAYMENTS),
                                                      eq(PaymentProduct.SEPA_CREDIT_TRANSFERS),
                                                      anyMap(),
                                                      anyMap(),
                                                      any(PeriodicPaymentInitiationMultipartBody.class));
        doReturn(HTTP_STATUS_CREATED).when(entity).getStatusCodeValue();
        doReturn(responseBody).when(entity).getBody();
        doReturn(buildHttpHeaders()).when(entity).getHeaders();

        Response<PaymentInitationRequestResponse201> response = service.initiatePayment(PaymentService.PAYMENTS,
                                                                                        PaymentProduct.SEPA_CREDIT_TRANSFERS,
                                                                                        RequestHeaders.empty(),
                                                                                        RequestParams.empty(),
                                                                                        new PeriodicPaymentInitiationMultipartBody());

        assertThat(response.getBody()).isEqualTo(responseBody);
        assertResponseHeaders(response.getHeaders());
    }

    @Test
    void initiatePayment_Xml() {

        PaymentInitationRequestResponse201 responseBody = new PaymentInitationRequestResponse201();

        doReturn(entity).when(client).initiatePayment(eq(PaymentService.PAYMENTS),
                                                      eq(PaymentProduct.SEPA_CREDIT_TRANSFERS),
                                                      anyMap(),
                                                      anyMap(),
                                                      any(String.class));
        doReturn(HTTP_STATUS_CREATED).when(entity).getStatusCodeValue();
        doReturn(responseBody).when(entity).getBody();
        doReturn(buildHttpHeaders()).when(entity).getHeaders();

        Response<PaymentInitationRequestResponse201> response =
            service.initiatePayment(PaymentService.PAYMENTS,
                                    PaymentProduct.SEPA_CREDIT_TRANSFERS,
                                    RequestHeaders.empty(),
                                    RequestParams.empty(),
                                    "<xml/>");

        assertThat(response.getBody()).isEqualTo(responseBody);
        assertResponseHeaders(response.getHeaders());
    }

    @Test
    void getSinglePaymentInformation() {
        PaymentInitiationWithStatusResponse responseBody = new PaymentInitiationWithStatusResponse();

        doReturn(entity).when(client).getPaymentInformation(eq(PaymentService.PAYMENTS),
                                                            eq(PaymentProduct.SEPA_CREDIT_TRANSFERS),
                                                            eq(PAYMENT_ID),
                                                            anyMap(),
                                                            anyMap());
        doReturn(HTTP_STATUS_OK).when(entity).getStatusCodeValue();
        doReturn(responseBody).when(entity).getBody();
        doReturn(buildHttpHeaders()).when(entity).getHeaders();

        Response<PaymentInitiationWithStatusResponse> response =
            service.getSinglePaymentInformation(PaymentProduct.SEPA_CREDIT_TRANSFERS,
                                                PAYMENT_ID,
                                                RequestHeaders.empty(),
                                                RequestParams.empty());
        assertThat(response.getBody()).isEqualTo(responseBody);
        assertResponseHeaders(response.getHeaders());
    }

    @Test
    void getPeriodicPaymentInformation() {
        PeriodicPaymentInitiationWithStatusResponse responseBody = new PeriodicPaymentInitiationWithStatusResponse();

        doReturn(entity).when(client).getPaymentInformation(eq(PaymentService.PERIODIC_PAYMENTS),
                                                            eq(PaymentProduct.SEPA_CREDIT_TRANSFERS),
                                                            eq(PAYMENT_ID),
                                                            anyMap(),
                                                            anyMap());
        doReturn(HTTP_STATUS_OK).when(entity).getStatusCodeValue();
        doReturn(responseBody).when(entity).getBody();
        doReturn(buildHttpHeaders()).when(entity).getHeaders();

        Response<PeriodicPaymentInitiationWithStatusResponse> response =
            service.getPeriodicPaymentInformation(PaymentProduct.SEPA_CREDIT_TRANSFERS,
                                                  PAYMENT_ID,
                                                  RequestHeaders.empty(),
                                                  RequestParams.empty());
        assertThat(response.getBody()).isEqualTo(responseBody);
        assertResponseHeaders(response.getHeaders());
    }

    @Test
    void getPeriodicPain001PaymentInformation() {
        PeriodicPaymentInitiationMultipartBody responseBody = new PeriodicPaymentInitiationMultipartBody();

        doReturn(entity).when(client).getPaymentInformation(eq(PaymentService.PERIODIC_PAYMENTS),
                                                            eq(PaymentProduct.SEPA_CREDIT_TRANSFERS),
                                                            eq(PAYMENT_ID),
                                                            anyMap(),
                                                            anyMap());
        doReturn(HTTP_STATUS_OK).when(entity).getStatusCodeValue();
        doReturn(responseBody).when(entity).getBody();
        doReturn(buildHttpHeaders()).when(entity).getHeaders();

        Response<PeriodicPaymentInitiationMultipartBody> response =
            service.getPeriodicPain001PaymentInformation(PaymentProduct.SEPA_CREDIT_TRANSFERS,
                                                         PAYMENT_ID,
                                                         RequestHeaders.empty(),
                                                         RequestParams.empty());
        assertThat(response.getBody()).isEqualTo(responseBody);
        assertResponseHeaders(response.getHeaders());
    }

    @Test
    void getPaymentInformationAsString() {
        String responseBody = "response in string format";

        doReturn(entity).when(client).getPaymentInformation(eq(PaymentService.PAYMENTS),
                                                            eq(PaymentProduct.SEPA_CREDIT_TRANSFERS),
                                                            eq(PAYMENT_ID),
                                                            anyMap(),
                                                            anyMap());
        doReturn(HTTP_STATUS_OK).when(entity).getStatusCodeValue();
        doReturn(responseBody).when(entity).getBody();
        doReturn(buildHttpHeaders()).when(entity).getHeaders();

        Response<String> response =
            service.getPaymentInformationAsString(
                PaymentService.PAYMENTS,
                PaymentProduct.SEPA_CREDIT_TRANSFERS,
                PAYMENT_ID,
                RequestHeaders.empty(),
                RequestParams.empty());
        assertThat(response.getBody()).isEqualTo(responseBody);
        assertResponseHeaders(response.getHeaders());
    }

    @Test
    void getPaymentInitiationScaStatus() {
        ScaStatusResponse responseBody = new ScaStatusResponse();

        doReturn(entity).when(client).getPaymentInitiationScaStatus(eq(PaymentService.PAYMENTS),
                                                                    eq(PaymentProduct.SEPA_CREDIT_TRANSFERS),
                                                                    eq(PAYMENT_ID),
                                                                    eq(AUTHORIZATION_ID),
                                                                    anyMap(),
                                                                    anyMap());
        doReturn(HTTP_STATUS_OK).when(entity).getStatusCodeValue();
        doReturn(responseBody).when(entity).getBody();
        doReturn(buildHttpHeaders()).when(entity).getHeaders();

        Response<ScaStatusResponse> response =
            service.getPaymentInitiationScaStatus(
                PaymentService.PAYMENTS,
                PaymentProduct.SEPA_CREDIT_TRANSFERS,
                PAYMENT_ID,
                AUTHORIZATION_ID,
                RequestHeaders.empty(),
                RequestParams.empty());
        assertThat(response.getBody()).isEqualTo(responseBody);
        assertResponseHeaders(response.getHeaders());
    }

    @Test
    void getPaymentInitiationStatus() {
        PaymentInitiationStatusResponse200Json responseBody = new PaymentInitiationStatusResponse200Json();

        doReturn(entity).when(client).getPaymentInitiationStatus(eq(PaymentService.PAYMENTS),
                                                                 eq(PaymentProduct.SEPA_CREDIT_TRANSFERS),
                                                                 eq(PAYMENT_ID),
                                                                 anyMap(),
                                                                 anyMap());
        doReturn(HTTP_STATUS_OK).when(entity).getStatusCodeValue();
        doReturn(responseBody).when(entity).getBody();
        doReturn(buildHttpHeaders()).when(entity).getHeaders();

        Response<PaymentInitiationStatusResponse200Json> response =
            service.getPaymentInitiationStatus(
                PaymentService.PAYMENTS,
                PaymentProduct.SEPA_CREDIT_TRANSFERS,
                PAYMENT_ID,
                RequestHeaders.empty(),
                RequestParams.empty());
        assertThat(response.getBody()).isEqualTo(responseBody);
        assertResponseHeaders(response.getHeaders());
    }

    @Test
    void getPaymentInitiationStatusAsString() throws JsonProcessingException {
        Collection<String> responseBody = Collections.singletonList("payment initiation status in string format");

        doReturn(entity).when(client).getPaymentInitiationStatus(eq(PaymentService.PAYMENTS),
                                                                 eq(PaymentProduct.SEPA_CREDIT_TRANSFERS),
                                                                 eq(PAYMENT_ID),
                                                                 anyMap(),
                                                                 anyMap());
        doReturn(HTTP_STATUS_OK).when(entity).getStatusCodeValue();
        doReturn(responseBody).when(entity).getBody();
        doReturn(buildHttpHeaders()).when(entity).getHeaders();

        Response<String> response =
            service.getPaymentInitiationStatusAsString(
                PaymentService.PAYMENTS,
                PaymentProduct.SEPA_CREDIT_TRANSFERS,
                PAYMENT_ID,
                RequestHeaders.empty(),
                RequestParams.empty());
        assertThat(response.getBody()).isEqualTo(objectMapper.writeValueAsString(responseBody));
        assertResponseHeaders(response.getHeaders());
    }

    @Test
    void getPaymentInitiationAuthorisation() {
        Authorisations responseBody = new Authorisations();

        doReturn(entity).when(client).getPaymentInitiationAuthorisation(eq(PaymentService.PAYMENTS),
                                                                        eq(PaymentProduct.SEPA_CREDIT_TRANSFERS),
                                                                        eq(PAYMENT_ID),
                                                                        anyMap(),
                                                                        anyMap());
        doReturn(HTTP_STATUS_OK).when(entity).getStatusCodeValue();
        doReturn(responseBody).when(entity).getBody();
        doReturn(buildHttpHeaders()).when(entity).getHeaders();

        Response<Authorisations> response =
            service.getPaymentInitiationAuthorisation(
                PaymentService.PAYMENTS,
                PaymentProduct.SEPA_CREDIT_TRANSFERS,
                PAYMENT_ID,
                RequestHeaders.empty(),
                RequestParams.empty());
        assertThat(response.getBody()).isEqualTo(responseBody);
        assertResponseHeaders(response.getHeaders());
    }

    @Test
    void startPaymentAuthorisation() {
        StartScaprocessResponse responseBody = new StartScaprocessResponse();

        doReturn(entity).when(client).startPaymentAuthorisation(eq(PaymentService.PAYMENTS),
                                                                eq(PaymentProduct.SEPA_CREDIT_TRANSFERS),
                                                                eq(PAYMENT_ID),
                                                                anyMap(),
                                                                anyMap(),
                                                                any(ObjectNode.class));
        doReturn(HTTP_STATUS_OK).when(entity).getStatusCodeValue();
        doReturn(responseBody).when(entity).getBody();
        doReturn(buildHttpHeaders()).when(entity).getHeaders();

        Response<StartScaprocessResponse> response =
            service.startPaymentAuthorisation(
                PaymentService.PAYMENTS,
                PaymentProduct.SEPA_CREDIT_TRANSFERS,
                PAYMENT_ID,
                RequestHeaders.empty(),
                RequestParams.empty());
        assertThat(response.getBody()).isEqualTo(responseBody);
        assertResponseHeaders(response.getHeaders());
    }

    @Test
    void startPaymentAuthorisation_UpdatePsuAuthentication() {
        StartScaprocessResponse responseBody = new StartScaprocessResponse();
        UpdatePsuAuthentication requestBody = new UpdatePsuAuthentication();

        doReturn(entity).when(client).startPaymentAuthorisation(eq(PaymentService.PAYMENTS),
                                                                eq(PaymentProduct.SEPA_CREDIT_TRANSFERS),
                                                                eq(PAYMENT_ID),
                                                                anyMap(),
                                                                anyMap(),
                                                                any(ObjectNode.class));
        doReturn(HTTP_STATUS_OK).when(entity).getStatusCodeValue();
        doReturn(responseBody).when(entity).getBody();
        doReturn(buildHttpHeaders()).when(entity).getHeaders();

        Response<StartScaprocessResponse> response =
            service.startPaymentAuthorisation(
                PaymentService.PAYMENTS,
                PaymentProduct.SEPA_CREDIT_TRANSFERS,
                PAYMENT_ID,
                RequestHeaders.empty(),
                RequestParams.empty(),
                requestBody);
        assertThat(response.getBody()).isEqualTo(responseBody);
        assertResponseHeaders(response.getHeaders());
    }

    @Test
    void updatePaymentPsuData_UpdatePsuAuthentication() {
        UpdatePsuAuthenticationResponse responseBody = new UpdatePsuAuthenticationResponse();
        UpdatePsuAuthentication requestBody = new UpdatePsuAuthentication();

        doReturn(entity).when(client).updatePaymentPsuData(eq(PaymentService.PAYMENTS),
                                                                eq(PaymentProduct.SEPA_CREDIT_TRANSFERS),
                                                                eq(PAYMENT_ID),
                                                                eq(AUTHORIZATION_ID),
                                                                anyMap(),
                                                                anyMap(),
                                                                any(ObjectNode.class));
        doReturn(HTTP_STATUS_OK).when(entity).getStatusCodeValue();
        doReturn(responseBody).when(entity).getBody();
        doReturn(buildHttpHeaders()).when(entity).getHeaders();

        Response<UpdatePsuAuthenticationResponse> response =
            service.updatePaymentPsuData(
                PaymentService.PAYMENTS,
                PaymentProduct.SEPA_CREDIT_TRANSFERS,
                PAYMENT_ID,
                AUTHORIZATION_ID,
                RequestHeaders.empty(),
                RequestParams.empty(),
                requestBody);
        assertThat(response.getBody()).isEqualTo(responseBody);
        assertResponseHeaders(response.getHeaders());
    }

    @Test
    void updatePaymentPsuData_SelectPsuAuthenticationMethod() {
        SelectPsuAuthenticationMethodResponse responseBody = new SelectPsuAuthenticationMethodResponse();
        SelectPsuAuthenticationMethod requestBody = new SelectPsuAuthenticationMethod();

        doReturn(entity).when(client).updatePaymentPsuData(eq(PaymentService.PAYMENTS),
                                                                eq(PaymentProduct.SEPA_CREDIT_TRANSFERS),
                                                                eq(PAYMENT_ID),
                                                                eq(AUTHORIZATION_ID),
                                                                anyMap(),
                                                                anyMap(),
                                                                any(ObjectNode.class));
        doReturn(HTTP_STATUS_OK).when(entity).getStatusCodeValue();
        doReturn(responseBody).when(entity).getBody();
        doReturn(buildHttpHeaders()).when(entity).getHeaders();

        Response<SelectPsuAuthenticationMethodResponse> response =
            service.updatePaymentPsuData(
                PaymentService.PAYMENTS,
                PaymentProduct.SEPA_CREDIT_TRANSFERS,
                PAYMENT_ID,
                AUTHORIZATION_ID,
                RequestHeaders.empty(),
                RequestParams.empty(),
                requestBody);
        assertThat(response.getBody()).isEqualTo(responseBody);
        assertResponseHeaders(response.getHeaders());
    }

    @Test
    void updatePaymentPsuData_TransactionAuthorisation() {
        ScaStatusResponse responseBody = new ScaStatusResponse();
        TransactionAuthorisation requestBody = new TransactionAuthorisation();

        doReturn(entity).when(client).updatePaymentPsuData(eq(PaymentService.PAYMENTS),
                                                                eq(PaymentProduct.SEPA_CREDIT_TRANSFERS),
                                                                eq(PAYMENT_ID),
                                                                eq(AUTHORIZATION_ID),
                                                                anyMap(),
                                                                anyMap(),
                                                                any(ObjectNode.class));
        doReturn(HTTP_STATUS_OK).when(entity).getStatusCodeValue();
        doReturn(responseBody).when(entity).getBody();
        doReturn(buildHttpHeaders()).when(entity).getHeaders();

        Response<ScaStatusResponse> response =
            service.updatePaymentPsuData(
                PaymentService.PAYMENTS,
                PaymentProduct.SEPA_CREDIT_TRANSFERS,
                PAYMENT_ID,
                AUTHORIZATION_ID,
                RequestHeaders.empty(),
                RequestParams.empty(),
                requestBody);
        assertThat(response.getBody()).isEqualTo(responseBody);
        assertResponseHeaders(response.getHeaders());
    }

    private HttpHeaders buildHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.put(ResponseHeaders.CONTENT_TYPE, Collections.singletonList(CONTENT_TYPE));
        headers.put(ResponseHeaders.X_REQUEST_ID, Collections.singletonList(X_REQUEST_ID));
        return headers;
    }

    private static void assertResponseHeaders(ResponseHeaders headers) {
        assertThat(headers.getHeadersMap()).hasSize(2);
        assertThat(headers.getHeader(ResponseHeaders.CONTENT_TYPE)).isEqualTo(CONTENT_TYPE);
        assertThat(headers.getHeader(ResponseHeaders.X_REQUEST_ID)).isEqualTo(X_REQUEST_ID);
    }
}
