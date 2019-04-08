package de.adorsys.xs2a.gateway.service;

public interface PaymentService {
    PaymentInitiationRequestResponse initiateSinglePayment(String paymentProduct, Object body, Headers headers);

    SinglePaymentInitiationInformationWithStatusResponse getSinglePaymentInformation(String paymentProduct, String paymentId, Headers headers);

    PaymentInitiationScaStatusResponse getPaymentInitiationScaStatus(String paymentService, String paymentProduct, String paymentId, String authorisationId, Headers headers);

    PaymentInitiationStatus getSinglePaymentInitiationStatus(String paymentProduct, String paymentId, Headers headers);
}
