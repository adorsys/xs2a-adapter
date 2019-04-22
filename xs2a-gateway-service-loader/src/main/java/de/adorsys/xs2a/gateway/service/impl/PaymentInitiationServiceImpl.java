package de.adorsys.xs2a.gateway.service.impl;

import de.adorsys.xs2a.gateway.service.*;
import de.adorsys.xs2a.gateway.service.provider.BankNotSupportedException;
import de.adorsys.xs2a.gateway.service.provider.PaymentInitiationServiceProvider;

import java.util.ServiceLoader;
import java.util.stream.StreamSupport;

public class PaymentInitiationServiceImpl implements PaymentInitiationService {

    @Override
    public GeneralResponse<PaymentInitiationRequestResponse> initiateSinglePayment(String paymentProduct, Object body, RequestHeaders requestHeaders) {
        return getPaymentService(requestHeaders).initiateSinglePayment(paymentProduct, body, requestHeaders);
    }

    @Override
    public GeneralResponse<SinglePaymentInitiationInformationWithStatusResponse> getSinglePaymentInformation(String paymentProduct,
                                                                                            String paymentId,
                                                                                            RequestHeaders requestHeaders) {
        return getPaymentService(requestHeaders).getSinglePaymentInformation(paymentProduct, paymentId, requestHeaders);
    }

    @Override
    public GeneralResponse<PaymentInitiationScaStatusResponse> getPaymentInitiationScaStatus(String paymentService,
                                                                            String paymentProduct,
                                                                            String paymentId,
                                                                            String authorisationId,
                                                                            RequestHeaders requestHeaders) {
        return getPaymentService(requestHeaders).getPaymentInitiationScaStatus(paymentService, paymentProduct, paymentId,
                                                                        authorisationId, requestHeaders);
    }

    @Override
    public GeneralResponse<PaymentInitiationStatus> getSinglePaymentInitiationStatus(String paymentProduct,
                                                                    String paymentId,
                                                                    RequestHeaders requestHeaders) {
        return getPaymentService(requestHeaders).getSinglePaymentInitiationStatus(paymentProduct, paymentId, requestHeaders);
    }

    @Override
    public GeneralResponse<PaymentInitiationAuthorisationResponse> getPaymentInitiationAuthorisation(String paymentService,
                                                                                    String paymentProduct,
                                                                                    String paymentId,
                                                                                    RequestHeaders requestHeaders) {
        return getPaymentService(requestHeaders).getPaymentInitiationAuthorisation(paymentService, paymentProduct,
                                                                            paymentId, requestHeaders);
    }

    private PaymentInitiationService getPaymentService(RequestHeaders requestHeaders) {
        String bankCode = requestHeaders.toMap().get(RequestHeaders.X_GTW_BANK_CODE);
        ServiceLoader<PaymentInitiationServiceProvider> loader =
                ServiceLoader.load(PaymentInitiationServiceProvider.class);
        return StreamSupport.stream(loader.spliterator(), false)
                       .filter(pis -> pis.getBankCode().equalsIgnoreCase(bankCode))
                       .findFirst().orElseThrow(() -> new BankNotSupportedException(bankCode))
                       .getPaymentInitiationService();
    }

}
