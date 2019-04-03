package de.adorsys.xs2a.gateway.service;

public interface PaymentService {
    PaymentInitiationRequestResponse initiatePayment(String paymentService, String paymentProduct, Object body, PaymentInitiationHeaders headers);

    PaymentInformationResponse getPaymentInformation(String paymentService, String paymentProduct, String paymentId, PaymentInformationHeaders headers);
}
