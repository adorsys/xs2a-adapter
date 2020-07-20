package de.adorsys.xs2a.adapter.service.impl;

import de.adorsys.xs2a.adapter.api.model.*;
import de.adorsys.xs2a.adapter.service.PaymentInitiationService;
import de.adorsys.xs2a.adapter.service.RequestHeaders;
import de.adorsys.xs2a.adapter.service.RequestParams;
import de.adorsys.xs2a.adapter.service.loader.AdapterServiceLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentInitiationServiceImplTest {

    private static final String PAYMENT_SERVICE = PaymentService.PAYMENTS.toString();
    private static final String PAYMENT_PRODUCT = PaymentProduct.SEPA_CREDIT_TRANSFERS.toString();
    private static final RequestHeaders headers = RequestHeaders.fromMap(new HashMap<>());
    private static final RequestParams params = RequestParams.empty();
    private static final String PAYMENT_ID = "paymentId";
    private static final String AUTHORISATION_ID = "authorisationId";

    @InjectMocks
    private PaymentInitiationServiceImpl service;

    @Mock
    private AdapterServiceLoader serviceLoader;

    @Mock
    private PaymentInitiationService paymentInitiationService;

    @BeforeEach
    void setUp() {
        when(serviceLoader.getPaymentInitiationService(any(RequestHeaders.class)))
            .thenReturn(paymentInitiationService);
    }

    @Test
    void initiatePayment() {
        service.initiatePayment(PAYMENT_SERVICE, PAYMENT_PRODUCT, headers, params, new Object());

        verify(serviceLoader, times(1))
            .getPaymentInitiationService(any(RequestHeaders.class));
        verify(paymentInitiationService, times(1))
            .initiatePayment(anyString(), anyString(), any(RequestHeaders.class), any(RequestParams.class), any());
    }

    @Test
    void getSinglePaymentInformation() {
        service.getSinglePaymentInformation(PAYMENT_PRODUCT, PAYMENT_ID, headers, params);

        verify(serviceLoader, times(1))
            .getPaymentInitiationService(any(RequestHeaders.class));
        verify(paymentInitiationService, times(1))
            .getSinglePaymentInformation(anyString(), anyString(), any(RequestHeaders.class), any(RequestParams.class));
    }

    @Test
    void getPeriodicPaymentInformation() {
        service.getPeriodicPaymentInformation(PAYMENT_PRODUCT, PAYMENT_ID, headers, params);

        verify(serviceLoader, times(1))
            .getPaymentInitiationService(any(RequestHeaders.class));
        verify(paymentInitiationService, times(1))
            .getPeriodicPaymentInformation(anyString(), anyString(), any(RequestHeaders.class), any(RequestParams.class));
    }

    @Test
    void getPaymentInformationAsString() {
        service.getPaymentInformationAsString(PAYMENT_SERVICE, PAYMENT_PRODUCT, PAYMENT_ID, headers, params);

        verify(serviceLoader, times(1))
            .getPaymentInitiationService(any(RequestHeaders.class));
        verify(paymentInitiationService, times(1))
            .getPaymentInformationAsString(anyString(), anyString(), anyString(), any(RequestHeaders.class), any(RequestParams.class));
    }

    @Test
    void getPaymentInitiationScaStatus() {
        service.getPaymentInitiationScaStatus(PAYMENT_SERVICE, PAYMENT_PRODUCT, PAYMENT_ID, AUTHORISATION_ID, headers, params);

        verify(serviceLoader, times(1))
            .getPaymentInitiationService(any(RequestHeaders.class));
        verify(paymentInitiationService, times(1))
            .getPaymentInitiationScaStatus(anyString(),
                anyString(),
                anyString(),
                anyString(),
                any(RequestHeaders.class),
                any(RequestParams.class));
    }

    @Test
    void getPaymentInitiationStatus() {
        service.getPaymentInitiationStatus(PAYMENT_SERVICE, PAYMENT_PRODUCT, PAYMENT_ID, headers, params);

        verify(serviceLoader, times(1))
            .getPaymentInitiationService(any(RequestHeaders.class));
        verify(paymentInitiationService, times(1))
            .getPaymentInitiationStatus(anyString(), anyString(), anyString(), any(RequestHeaders.class), any(RequestParams.class));
    }

    @Test
    void getPaymentInitiationStatusAsString() {
        service.getPaymentInitiationStatusAsString(PAYMENT_SERVICE, PAYMENT_PRODUCT, PAYMENT_ID, headers, params);

        verify(serviceLoader, times(1))
            .getPaymentInitiationService(any(RequestHeaders.class));
        verify(paymentInitiationService, times(1))
            .getPaymentInitiationStatusAsString(anyString(),
                anyString(),
                anyString(),
                any(RequestHeaders.class),
                any(RequestParams.class));
    }

    @Test
    void getPaymentInitiationAuthorisation() {
        service.getPaymentInitiationAuthorisation(PAYMENT_SERVICE, PAYMENT_PRODUCT, PAYMENT_ID, headers, params);

        verify(serviceLoader, times(1))
            .getPaymentInitiationService(any(RequestHeaders.class));
        verify(paymentInitiationService, times(1))
            .getPaymentInitiationAuthorisation(anyString(),
                anyString(),
                anyString(),
                any(RequestHeaders.class),
                any(RequestParams.class));
    }

    @Test
    void startPaymentAuthorisation() {
        service.startPaymentAuthorisation(PAYMENT_SERVICE, PAYMENT_PRODUCT, PAYMENT_ID, headers, params);

        verify(serviceLoader, times(1))
            .getPaymentInitiationService(any(RequestHeaders.class));
        verify(paymentInitiationService, times(1))
            .startPaymentAuthorisation(anyString(), anyString(), anyString(), any(RequestHeaders.class), any(RequestParams.class));
    }

    @Test
    void startPaymentAuthorisation_updatePsuAuthentication() {
        service.startPaymentAuthorisation(PAYMENT_SERVICE, PAYMENT_PRODUCT, PAYMENT_ID, headers, params, new UpdatePsuAuthentication());

        verify(serviceLoader, times(1))
            .getPaymentInitiationService(any(RequestHeaders.class));
        verify(paymentInitiationService, times(1))
            .startPaymentAuthorisation(anyString(),
                anyString(),
                anyString(),
                any(RequestHeaders.class),
                any(RequestParams.class),
                any(UpdatePsuAuthentication.class));
    }

    @Test
    void updatePaymentPsuData_selectPsuAuthenticationMethod() {
        service.updatePaymentPsuData(PAYMENT_SERVICE,
            PAYMENT_PRODUCT,
            PAYMENT_ID,
            AUTHORISATION_ID,
            headers,
            params,
            new SelectPsuAuthenticationMethod());

        verify(serviceLoader, times(1))
            .getPaymentInitiationService(any(RequestHeaders.class));
        verify(paymentInitiationService, times(1))
            .updatePaymentPsuData(anyString(),
                anyString(),
                anyString(),
                anyString(),
                any(RequestHeaders.class),
                any(RequestParams.class),
                any(SelectPsuAuthenticationMethod.class));
    }

    @Test
    void updatePaymentPsuData_transactionAuthorisation() {
        service.updatePaymentPsuData(PAYMENT_SERVICE,
            PAYMENT_PRODUCT,
            PAYMENT_ID,
            AUTHORISATION_ID,
            headers,
            params,
            new TransactionAuthorisation());

        verify(serviceLoader, times(1))
            .getPaymentInitiationService(any(RequestHeaders.class));
        verify(paymentInitiationService, times(1))
            .updatePaymentPsuData(anyString(),
                anyString(),
                anyString(),
                anyString(),
                any(RequestHeaders.class),
                any(RequestParams.class),
                any(TransactionAuthorisation.class));
    }

    @Test
    void updatePaymentPsuData_updatePsuAuthentication() {
        service.updatePaymentPsuData(PAYMENT_SERVICE,
            PAYMENT_PRODUCT,
            PAYMENT_ID,
            AUTHORISATION_ID,
            headers,
            params,
            new UpdatePsuAuthentication());

        verify(serviceLoader, times(1))
            .getPaymentInitiationService(any(RequestHeaders.class));
        verify(paymentInitiationService, times(1))
            .updatePaymentPsuData(anyString(),
                anyString(),
                anyString(),
                anyString(),
                any(RequestHeaders.class),
                any(RequestParams.class),
                any(UpdatePsuAuthentication.class));
    }
}
