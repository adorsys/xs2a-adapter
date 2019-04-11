package de.adorsys.xs2a.gateway.service.impl;

import de.adorsys.xs2a.gateway.adapter.AdapterManager;
import de.adorsys.xs2a.gateway.adapter.AdapterRegistry;
import de.adorsys.xs2a.gateway.service.*;

public class PaymentServiceImpl implements PaymentService {

    @Override
    public PaymentInitiationRequestResponse initiateSinglePayment(String paymentProduct, Object body, Headers headers) {
        return getPaymentService(headers).initiateSinglePayment(paymentProduct, body, headers);
    }

    @Override
    public SinglePaymentInitiationInformationWithStatusResponse getSinglePaymentInformation(String paymentProduct,
                                                                                            String paymentId,
                                                                                            Headers headers) {
        return getPaymentService(headers).getSinglePaymentInformation(paymentProduct, paymentId, headers);
    }

    @Override
    public PaymentInitiationScaStatusResponse getPaymentInitiationScaStatus(String paymentService,
                                                                            String paymentProduct,
                                                                            String paymentId,
                                                                            String authorisationId,
                                                                            Headers headers) {
        return getPaymentService(headers).getPaymentInitiationScaStatus(paymentService, paymentProduct, paymentId,
                                                                        authorisationId, headers);
    }

    @Override
    public PaymentInitiationStatus getSinglePaymentInitiationStatus(String paymentProduct,
                                                                    String paymentId,
                                                                    Headers headers) {
        return getPaymentService(headers).getSinglePaymentInitiationStatus(paymentProduct, paymentId, headers);
    }

    @Override
    public PaymentInitiationAuthorisationResponse getPaymentInitiationAuthorisation(String paymentService,
                                                                                    String paymentProduct,
                                                                                    String paymentId,
                                                                                    Headers headers) {
        return getPaymentService(headers).getPaymentInitiationAuthorisation(paymentService, paymentProduct,
                                                                            paymentId, headers);
    }

    private PaymentService getPaymentService(Headers headers) {
        String bankCode = headers.toMap().get(Headers.X_GTW_BANK_CODE);
        AdapterManager manager = AdapterRegistry.getInstance().getAdapterManager(bankCode);
        return manager.getPaymentService();
    }
}
