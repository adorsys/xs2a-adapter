package de.adorsys.xs2a.gateway.service.impl;

import de.adorsys.xs2a.gateway.service.*;
import de.adorsys.xs2a.gateway.service.provider.BankNotSupportedException;
import de.adorsys.xs2a.gateway.service.provider.PaymentInitiationServiceProvider;

import java.util.ServiceLoader;
import java.util.stream.StreamSupport;

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
        ServiceLoader<PaymentInitiationServiceProvider> loader =
                ServiceLoader.load(PaymentInitiationServiceProvider.class);
        return StreamSupport.stream(loader.spliterator(), false)
                       .filter(pis -> pis.getBankCode().equalsIgnoreCase(bankCode))
                       .findFirst().orElseThrow(() -> new BankNotSupportedException(bankCode))
                       .getPaymentInitiationService();
    }

}
