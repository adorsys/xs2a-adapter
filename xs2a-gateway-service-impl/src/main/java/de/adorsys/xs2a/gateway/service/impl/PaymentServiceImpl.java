package de.adorsys.xs2a.gateway.service.impl;

import de.adorsys.xs2a.gateway.service.*;

public class PaymentServiceImpl implements PaymentService {

    private PaymentService paymentService = new DeutscheBankPaymentService();

    @Override
    public PaymentInitiationRequestResponse initiateSinglePayment(String paymentProduct, Object body, Headers headers) {
        return paymentService.initiateSinglePayment(paymentProduct, body, headers);
    }

    @Override
    public SinglePaymentInitiationInformationWithStatusResponse getSinglePaymentInformation(String paymentProduct, String paymentId, Headers headers) {
        return paymentService.getSinglePaymentInformation(paymentProduct, paymentId, headers);
    }

    @Override
    public PaymentInitiationScaStatusResponse getPaymentInitiationScaStatus(String paymentService, String paymentProduct, String paymentId, String authorisationId, Headers headers) {
        return this.paymentService.getPaymentInitiationScaStatus(paymentService, paymentProduct, paymentId, authorisationId, headers);
    }

    @Override
    public PaymentInitiationStatus getSinglePaymentInitiationStatus(String paymentProduct, String paymentId, Headers headers) {
        return paymentService.getSinglePaymentInitiationStatus(paymentProduct, paymentId, headers);
    }
}
