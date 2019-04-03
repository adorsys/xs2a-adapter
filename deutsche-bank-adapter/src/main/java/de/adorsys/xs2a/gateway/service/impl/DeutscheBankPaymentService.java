package de.adorsys.xs2a.gateway.service.impl;

import de.adorsys.xs2a.gateway.service.*;

public class DeutscheBankPaymentService implements PaymentService {
    @Override
    public PaymentInitiationRequestResponse initiatePayment(String paymentService, String paymentProduct, Object body, PaymentInitiationHeaders headers) {
        throw new UnsupportedOperationException();
    }

    @Override
    public PaymentInformationResponse getPaymentInformation(String paymentService, String paymentProduct, String paymentId, PaymentInformationHeaders headers) {
        throw new UnsupportedOperationException();
    }
}
