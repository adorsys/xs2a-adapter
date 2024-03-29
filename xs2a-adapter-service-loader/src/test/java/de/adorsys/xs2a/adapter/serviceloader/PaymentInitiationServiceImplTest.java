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

package de.adorsys.xs2a.adapter.serviceloader;

import de.adorsys.xs2a.adapter.api.PaymentInitiationService;
import de.adorsys.xs2a.adapter.api.RequestHeaders;
import de.adorsys.xs2a.adapter.api.RequestParams;
import de.adorsys.xs2a.adapter.api.model.*;
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

    private static final PaymentService PAYMENT_SERVICE = PaymentService.PAYMENTS;
    private static final PaymentProduct PAYMENT_PRODUCT = PaymentProduct.SEPA_CREDIT_TRANSFERS;
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
            .initiatePayment(eq(PAYMENT_SERVICE),
                eq(PAYMENT_PRODUCT),
                any(RequestHeaders.class),
                any(RequestParams.class),
                any());
    }

    @Test
    void getSinglePaymentInformation() {
        service.getSinglePaymentInformation(PAYMENT_PRODUCT, PAYMENT_ID, headers, params);

        verify(serviceLoader, times(1))
            .getPaymentInitiationService(any(RequestHeaders.class));
        verify(paymentInitiationService, times(1))
            .getSinglePaymentInformation(eq(PAYMENT_PRODUCT),
                anyString(),
                any(RequestHeaders.class),
                any(RequestParams.class));
    }

    @Test
    void getPeriodicPaymentInformation() {
        service.getPeriodicPaymentInformation(PAYMENT_PRODUCT, PAYMENT_ID, headers, params);

        verify(serviceLoader, times(1))
            .getPaymentInitiationService(any(RequestHeaders.class));
        verify(paymentInitiationService, times(1))
            .getPeriodicPaymentInformation(eq(PAYMENT_PRODUCT),
                anyString(),
                any(RequestHeaders.class),
                any(RequestParams.class));
    }

    @Test
    void getPaymentInformationAsString() {
        service.getPaymentInformationAsString(PAYMENT_SERVICE, PAYMENT_PRODUCT, PAYMENT_ID, headers, params);

        verify(serviceLoader, times(1))
            .getPaymentInitiationService(any(RequestHeaders.class));
        verify(paymentInitiationService, times(1))
            .getPaymentInformationAsString(eq(PAYMENT_SERVICE),
                eq(PAYMENT_PRODUCT),
                anyString(),
                any(RequestHeaders.class),
                any(RequestParams.class));
    }

    @Test
    void getPaymentInitiationScaStatus() {
        service.getPaymentInitiationScaStatus(PAYMENT_SERVICE, PAYMENT_PRODUCT, PAYMENT_ID, AUTHORISATION_ID, headers, params);

        verify(serviceLoader, times(1))
            .getPaymentInitiationService(any(RequestHeaders.class));
        verify(paymentInitiationService, times(1))
            .getPaymentInitiationScaStatus(eq(PAYMENT_SERVICE),
                eq(PAYMENT_PRODUCT),
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
            .getPaymentInitiationStatus(eq(PAYMENT_SERVICE),
                eq(PAYMENT_PRODUCT),
                anyString(),
                any(RequestHeaders.class),
                any(RequestParams.class));
    }

    @Test
    void getPaymentInitiationStatusAsString() {
        service.getPaymentInitiationStatusAsString(PAYMENT_SERVICE, PAYMENT_PRODUCT, PAYMENT_ID, headers, params);

        verify(serviceLoader, times(1))
            .getPaymentInitiationService(any(RequestHeaders.class));
        verify(paymentInitiationService, times(1))
            .getPaymentInitiationStatusAsString(eq(PAYMENT_SERVICE),
                eq(PAYMENT_PRODUCT),
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
            .getPaymentInitiationAuthorisation(eq(PAYMENT_SERVICE),
                eq(PAYMENT_PRODUCT),
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
            .startPaymentAuthorisation(eq(PAYMENT_SERVICE),
                eq(PAYMENT_PRODUCT),
                anyString(),
                any(RequestHeaders.class),
                any(RequestParams.class));
    }

    @Test
    void startPaymentAuthorisation_updatePsuAuthentication() {
        service.startPaymentAuthorisation(PAYMENT_SERVICE, PAYMENT_PRODUCT, PAYMENT_ID, headers, params, new UpdatePsuAuthentication());

        verify(serviceLoader, times(1))
            .getPaymentInitiationService(any(RequestHeaders.class));
        verify(paymentInitiationService, times(1))
            .startPaymentAuthorisation(eq(PAYMENT_SERVICE),
                eq(PAYMENT_PRODUCT),
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
            .updatePaymentPsuData(eq(PAYMENT_SERVICE),
                eq(PAYMENT_PRODUCT),
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
            .updatePaymentPsuData(eq(PAYMENT_SERVICE),
                eq(PAYMENT_PRODUCT),
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
            .updatePaymentPsuData(eq(PAYMENT_SERVICE),
                eq(PAYMENT_PRODUCT),
                anyString(),
                anyString(),
                any(RequestHeaders.class),
                any(RequestParams.class),
                any(UpdatePsuAuthentication.class));
    }
}
