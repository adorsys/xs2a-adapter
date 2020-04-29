package de.adorsys.xs2a.adapter.remote.service.impl.psd2;

import de.adorsys.xs2a.adapter.remote.api.psd2.Psd2PaymentInitiationClient;
import de.adorsys.xs2a.adapter.rest.psd2.model.*;
import de.adorsys.xs2a.adapter.service.Response;
import de.adorsys.xs2a.adapter.service.psd2.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class RemotePsd2PaymentInitiationServiceTest {

    private static final String TRANSACTION_STATUS = "transaction status";
    private static final String PAYMENT_ID = "payment id";
    private static final String AUTHORISATION_ID = "authorisation id";
    private static final String SCA_STATUS = "sca status";

    @InjectMocks
    RemotePsd2PaymentInitiationService service;

    @Mock
    Psd2PaymentInitiationClient client;

    @Captor
    ArgumentCaptor<String> stringCaptor;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void initiatePayment_withObjectBody() throws IOException {
        when(client.initiatePayment(anyString(), anyString(), anyMap(), anyMap(), any(PaymentInitiationTO.class)))
            .thenReturn(new ResponseEntity<>(createPaymentInitiationRequestResponseTO(), new HttpHeaders(), HttpStatus.OK));

        Response<PaymentInitiationRequestResponse> actual = service
            .initiatePayment(PaymentService.PAYMENTS, PaymentProduct.SEPA_CREDIT_TRANSFERS, new HashMap<>(), new HashMap<>(), new PaymentInitiation());

        verify(client, times(1)).initiatePayment(stringCaptor.capture(), stringCaptor.capture(), anyMap(), anyMap(), any(PaymentInitiationTO.class));

        assertEquals(2, stringCaptor.getAllValues().size());
        assertEquals(PaymentServiceTO.PAYMENTS.toString(), stringCaptor.getAllValues().get(0));
        assertEquals(PaymentProductTO.SEPA_CREDIT_TRANSFERS.toString(), stringCaptor.getAllValues().get(1));
        assertNotNull(actual);
        assertEquals(200, actual.getStatusCode());
        assertNotNull(actual.getHeaders());
        assertEquals(TRANSACTION_STATUS, actual.getBody().getTransactionStatus());
    }

    @Test
    void initiatePayment_withStringBody() throws IOException {
        when(client.initiatePayment(anyString(), anyString(), anyMap(), anyMap(), anyString()))
            .thenReturn(new ResponseEntity<>(createPaymentInitiationRequestResponseTO(), new HttpHeaders(), HttpStatus.OK));

        Response<PaymentInitiationRequestResponse> actual = service
            .initiatePayment(PaymentService.PAYMENTS, PaymentProduct.SEPA_CREDIT_TRANSFERS, new HashMap<>(), new HashMap<>(), "string body");

        verify(client, times(1)).initiatePayment(stringCaptor.capture(), stringCaptor.capture(), anyMap(), anyMap(), stringCaptor.capture());

        assertEquals(3, stringCaptor.getAllValues().size());
        assertEquals(PaymentServiceTO.PAYMENTS.toString(), stringCaptor.getAllValues().get(0));
        assertEquals(PaymentProductTO.SEPA_CREDIT_TRANSFERS.toString(), stringCaptor.getAllValues().get(1));
        assertEquals("string body", stringCaptor.getAllValues().get(2));
        assertNotNull(actual);
        assertEquals(200, actual.getStatusCode());
        assertNotNull(actual.getHeaders());
        assertEquals(TRANSACTION_STATUS, actual.getBody().getTransactionStatus());
    }

    @Test
    void getPaymentInformation() throws IOException {
        when(client.getPaymentInformation(anyString(), anyString(), anyString(), anyMap(), anyMap()))
            .thenReturn(new ResponseEntity<>("body", new HttpHeaders(), HttpStatus.OK));

        Response<Object> actual = service
            .getPaymentInformation(PaymentService.BULK_PAYMENTS, PaymentProduct.CROSS_BORDER_CREDIT_TRANSFERS, PAYMENT_ID, new HashMap<>(), new HashMap<>());

        verify(client, times(1)).getPaymentInformation(stringCaptor.capture(), stringCaptor.capture(), stringCaptor.capture(), anyMap(), anyMap());

        assertEquals(3, stringCaptor.getAllValues().size());
        assertEquals(PaymentServiceTO.BULK_PAYMENTS.toString(), stringCaptor.getAllValues().get(0));
        assertEquals(PaymentProductTO.CROSS_BORDER_CREDIT_TRANSFERS.toString(), stringCaptor.getAllValues().get(1));
        assertEquals(PAYMENT_ID, stringCaptor.getAllValues().get(2));
        assertNotNull(actual);
        assertEquals(200, actual.getStatusCode());
        assertNotNull(actual.getHeaders());
        assertNotNull(actual.getBody());
    }

    @Test
    void getPaymentInitiationStatus() throws IOException {
        when(client.getPaymentInitiationStatus(anyString(), anyString(), anyString(), anyMap(), anyMap()))
            .thenReturn(new ResponseEntity<>("body", new HttpHeaders(), HttpStatus.OK));

        Response<Object> actual = service
            .getPaymentInitiationStatus(PaymentService.PERIODIC_PAYMENTS, PaymentProduct.TARGET_2_PAYMENTS, PAYMENT_ID, new HashMap<>(), new HashMap<>());

        verify(client, times(1))
            .getPaymentInitiationStatus(stringCaptor.capture(), stringCaptor.capture(), stringCaptor.capture(), anyMap(), anyMap());

        assertEquals(3, stringCaptor.getAllValues().size());
        assertEquals(PaymentServiceTO.PERIODIC_PAYMENTS.toString(), stringCaptor.getAllValues().get(0));
        assertEquals(PaymentProductTO.TARGET_2_PAYMENTS.toString(), stringCaptor.getAllValues().get(1));
        assertEquals(PAYMENT_ID, stringCaptor.getAllValues().get(2));
        assertNotNull(actual);
        assertEquals(200, actual.getStatusCode());
        assertNotNull(actual.getHeaders());
        assertNotNull(actual.getBody());
    }

    @Test
    void getPaymentInitiationAuthorisation() throws IOException {
        when(client.getPaymentInitiationAuthorisation(anyString(), anyString(), anyString(), anyMap(), anyMap()))
            .thenReturn(new ResponseEntity<>(new AuthorisationsTO(), new HttpHeaders(), HttpStatus.OK));

        Response<Authorisations> actual = service
            .getPaymentInitiationAuthorisation(PaymentService.PAYMENTS, PaymentProduct.SEPA_CREDIT_TRANSFERS, PAYMENT_ID, new HashMap<>(), new HashMap<>());

        verify(client, times(1))
            .getPaymentInitiationAuthorisation(stringCaptor.capture(), stringCaptor.capture(), stringCaptor.capture(), anyMap(), anyMap());

        assertEquals(3, stringCaptor.getAllValues().size());
        assertEquals(PaymentServiceTO.PAYMENTS.toString(), stringCaptor.getAllValues().get(0));
        assertEquals(PaymentProductTO.SEPA_CREDIT_TRANSFERS.toString(), stringCaptor.getAllValues().get(1));
        assertEquals(PAYMENT_ID, stringCaptor.getAllValues().get(2));
        assertNotNull(actual);
        assertEquals(200, actual.getStatusCode());
        assertNotNull(actual.getHeaders());
        assertNotNull(actual.getBody());
    }

    @Test
    void startPaymentAuthorisation() throws IOException {
        when(client.startPaymentAuthorisation(anyString(), anyString(), anyString(), anyMap(), anyMap(), any(UpdateAuthorisationTO.class)))
            .thenReturn(new ResponseEntity<>(createStartScaProcessResponseTO(), new HttpHeaders(), HttpStatus.OK));

        Response<StartScaProcessResponse> actual = service
            .startPaymentAuthorisation(PaymentService.PAYMENTS, PaymentProduct.SEPA_CREDIT_TRANSFERS, PAYMENT_ID, new HashMap<>(), new HashMap<>(), new UpdateAuthorisation());

        verify(client, times(1))
            .startPaymentAuthorisation(stringCaptor.capture(), stringCaptor.capture(), stringCaptor.capture(), anyMap(), anyMap(), any(UpdateAuthorisationTO.class));

        assertEquals(3, stringCaptor.getAllValues().size());
        assertEquals(PaymentServiceTO.PAYMENTS.toString(), stringCaptor.getAllValues().get(0));
        assertEquals(PaymentProductTO.SEPA_CREDIT_TRANSFERS.toString(), stringCaptor.getAllValues().get(1));
        assertEquals(PAYMENT_ID, stringCaptor.getAllValues().get(2));
        assertNotNull(actual);
        assertEquals(200, actual.getStatusCode());
        assertNotNull(actual.getHeaders());
        assertEquals(SCA_STATUS, actual.getBody().getScaStatus());
    }

    @Test
    void getPaymentInitiationScaStatus() throws IOException {
        when(client.getPaymentInitiationScaStatus(anyString(), anyString(), anyString(), anyString(), anyMap(), anyMap()))
            .thenReturn(new ResponseEntity<>(createScaStatusResponseTO(), new HttpHeaders(), HttpStatus.OK));

        Response<ScaStatusResponse> actual = service
            .getPaymentInitiationScaStatus(PaymentService.PAYMENTS, PaymentProduct.SEPA_CREDIT_TRANSFERS, PAYMENT_ID, AUTHORISATION_ID, new HashMap<>(), new HashMap<>());

        verify(client, times(1))
            .getPaymentInitiationScaStatus(stringCaptor.capture(), stringCaptor.capture(), stringCaptor.capture(), stringCaptor.capture(), anyMap(), anyMap());

        assertEquals(4, stringCaptor.getAllValues().size());
        assertEquals(PaymentServiceTO.PAYMENTS.toString(), stringCaptor.getAllValues().get(0));
        assertEquals(PaymentProductTO.SEPA_CREDIT_TRANSFERS.toString(), stringCaptor.getAllValues().get(1));
        assertEquals(PAYMENT_ID, stringCaptor.getAllValues().get(2));
        assertEquals(AUTHORISATION_ID, stringCaptor.getAllValues().get(3));
        assertNotNull(actual);
        assertEquals(200, actual.getStatusCode());
        assertNotNull(actual.getHeaders());
        assertEquals(SCA_STATUS, actual.getBody().getScaStatus());
    }

    @Test
    void updatePaymentPsuData() throws IOException {
        when(client.updatePaymentPsuData(anyString(), anyString(), anyString(), anyString(), anyMap(), anyMap(), any(UpdateAuthorisationTO.class)))
            .thenReturn(new ResponseEntity<>(createUpdateAuthorisationResponseTO(), new HttpHeaders(), HttpStatus.OK));

        Response<UpdateAuthorisationResponse> actual = service
            .updatePaymentPsuData(PaymentService.PAYMENTS, PaymentProduct.SEPA_CREDIT_TRANSFERS, PAYMENT_ID, AUTHORISATION_ID, new HashMap<>(), new HashMap<>(), new UpdateAuthorisation());

        verify(client, times(1))
            .updatePaymentPsuData(stringCaptor.capture(), stringCaptor.capture(), stringCaptor.capture(), stringCaptor.capture(), anyMap(), anyMap(), any(UpdateAuthorisationTO.class));

        assertEquals(4, stringCaptor.getAllValues().size());
        assertEquals(PaymentServiceTO.PAYMENTS.toString(), stringCaptor.getAllValues().get(0));
        assertEquals(PaymentProductTO.SEPA_CREDIT_TRANSFERS.toString(), stringCaptor.getAllValues().get(1));
        assertEquals(PAYMENT_ID, stringCaptor.getAllValues().get(2));
        assertEquals(AUTHORISATION_ID, stringCaptor.getAllValues().get(3));
        assertNotNull(actual);
        assertEquals(200, actual.getStatusCode());
        assertNotNull(actual.getHeaders());
        assertEquals(SCA_STATUS, actual.getBody().getScaStatus());
    }

    private PaymentInitiationRequestResponseTO createPaymentInitiationRequestResponseTO() {
        PaymentInitiationRequestResponseTO response = new PaymentInitiationRequestResponseTO();
        response.setTransactionStatus(TRANSACTION_STATUS);

        return response;
    }

    private StartScaProcessResponseTO createStartScaProcessResponseTO() {
        StartScaProcessResponseTO response = new StartScaProcessResponseTO();
        response.setScaStatus(SCA_STATUS);

        return response;
    }

    private ScaStatusResponseTO createScaStatusResponseTO() {
        ScaStatusResponseTO response = new ScaStatusResponseTO();
        response.setScaStatus(SCA_STATUS);

        return response;
    }

    private UpdateAuthorisationResponseTO createUpdateAuthorisationResponseTO() {
        UpdateAuthorisationResponseTO response = new UpdateAuthorisationResponseTO();
        response.setScaStatus(SCA_STATUS);

        return response;
    }
}
