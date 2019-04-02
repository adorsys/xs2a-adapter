package de.adorsys.xs2a.gateway.service.impl;

import de.adorsys.xs2a.gateway.service.PaymentInitiationHeaders;
import de.adorsys.xs2a.gateway.service.PaymentInitiationRequestResponse;
import de.adorsys.xs2a.gateway.service.PaymentService;

public class PaymentServiceImpl implements PaymentService {

    private PaymentService paymentService = new DeutscheBankPaymentService();

    @Override
    public PaymentInitiationRequestResponse initiatePayment(String paymentService, String paymentProduct, Object body, PaymentInitiationHeaders headers) {
        return this.paymentService.initiatePayment(paymentService, paymentProduct, body, headers);
    }
}
