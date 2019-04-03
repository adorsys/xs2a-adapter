package de.adorsys.xs2a.gateway.service.impl;

import de.adorsys.xs2a.gateway.service.*;

public class PaymentServiceImpl implements PaymentService {

    private PaymentService paymentService = new DeutscheBankPaymentService();

    @Override
    public PaymentInitiationRequestResponse initiatePayment(String paymentService, String paymentProduct, Object body, PaymentInitiationHeaders headers) {
        return this.paymentService.initiatePayment(paymentService, paymentProduct, body, headers);
    }

    @Override
    public PaymentInformationResponse getPaymentInformation(String paymentService, String paymentProduct, String paymentId, PaymentInformationHeaders headers) {
        return this.paymentService.getPaymentInformation(paymentService, paymentProduct, paymentId, headers);
    }
}
