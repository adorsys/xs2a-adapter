package de.adorsys.xs2a.gateway.service.impl;

import de.adorsys.xs2a.gateway.service.PaymentInitiationHeaders;
import de.adorsys.xs2a.gateway.service.PaymentInitiationRequestResponse;
import de.adorsys.xs2a.gateway.service.PaymentService;

public class DeutscheBankPaymentService implements PaymentService {
    @Override
    public PaymentInitiationRequestResponse initiatePayment(String paymentService, String paymentProduct, Object body, PaymentInitiationHeaders headers) {
        throw new UnsupportedOperationException();
    }
}
