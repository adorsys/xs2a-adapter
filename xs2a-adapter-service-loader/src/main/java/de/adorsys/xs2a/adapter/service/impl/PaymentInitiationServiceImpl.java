package de.adorsys.xs2a.adapter.service.impl;

import de.adorsys.xs2a.adapter.service.*;
import de.adorsys.xs2a.adapter.service.model.*;

public class PaymentInitiationServiceImpl implements PaymentInitiationService {
    private final BankServiceLoader bankServiceLoader;

    public PaymentInitiationServiceImpl(BankServiceLoader bankServiceLoader) {
        this.bankServiceLoader = bankServiceLoader;
    }

    @Override
    public GeneralResponse<PaymentInitiationRequestResponse> initiateSinglePayment(String paymentProduct,
                                                                                   RequestHeaders requestHeaders,
                                                                                   Object body) {
        return bankServiceLoader
                   .getPaymentInitiationService(requestHeaders.removeBankCode())
                   .initiateSinglePayment(paymentProduct, requestHeaders, body);
    }

    @Override
    public GeneralResponse<SinglePaymentInitiationInformationWithStatusResponse> getSinglePaymentInformation(String paymentProduct,
                                                                                                             String paymentId,
                                                                                                             RequestHeaders requestHeaders) {
        return bankServiceLoader
                   .getPaymentInitiationService(requestHeaders.removeBankCode())
                   .getSinglePaymentInformation(paymentProduct, paymentId, requestHeaders);
    }

    @Override
    public GeneralResponse<PaymentInitiationScaStatusResponse> getPaymentInitiationScaStatus(String paymentService,
                                                                                             String paymentProduct,
                                                                                             String paymentId,
                                                                                             String authorisationId,
                                                                                             RequestHeaders requestHeaders) {
        return bankServiceLoader
                   .getPaymentInitiationService(requestHeaders.removeBankCode())
                   .getPaymentInitiationScaStatus(paymentService, paymentProduct, paymentId, authorisationId, requestHeaders);
    }

    @Override
    public GeneralResponse<PaymentInitiationStatus> getSinglePaymentInitiationStatus(String paymentProduct,
                                                                                     String paymentId,
                                                                                     RequestHeaders requestHeaders) {
        return bankServiceLoader
                   .getPaymentInitiationService(requestHeaders.removeBankCode())
                   .getSinglePaymentInitiationStatus(paymentProduct, paymentId, requestHeaders);
    }

    @Override
    public GeneralResponse<String> getSinglePaymentInitiationStatusAsString(String paymentProduct,
                                                                            String paymentId,
                                                                            RequestHeaders requestHeaders) {
        return bankServiceLoader
                   .getPaymentInitiationService(requestHeaders.removeBankCode())
                   .getSinglePaymentInitiationStatusAsString(paymentProduct, paymentId, requestHeaders);
    }

    @Override
    public GeneralResponse<PaymentInitiationAuthorisationResponse> getPaymentInitiationAuthorisation(String paymentService,
                                                                                                     String paymentProduct,
                                                                                                     String paymentId,
                                                                                                     RequestHeaders requestHeaders) {
        return bankServiceLoader
                   .getPaymentInitiationService(requestHeaders.removeBankCode())
                   .getPaymentInitiationAuthorisation(paymentService, paymentProduct, paymentId, requestHeaders);
    }

    @Override
    public GeneralResponse<StartScaProcessResponse> startSinglePaymentAuthorisation(String paymentProduct,
                                                                                    String paymentId,
                                                                                    RequestHeaders requestHeaders,
                                                                                    UpdatePsuAuthentication updatePsuAuthentication) {
        return bankServiceLoader
                   .getPaymentInitiationService(requestHeaders.removeBankCode())
                   .startSinglePaymentAuthorisation(paymentProduct, paymentId, requestHeaders, updatePsuAuthentication);
    }

    @Override
    public GeneralResponse<SelectPsuAuthenticationMethodResponse> updatePaymentPsuData(String paymentService,
                                                                                       String paymentProduct,
                                                                                       String paymentId,
                                                                                       String authorisationId,
                                                                                       RequestHeaders requestHeaders,
                                                                                       SelectPsuAuthenticationMethod selectPsuAuthenticationMethod) {
        return bankServiceLoader
                   .getPaymentInitiationService(requestHeaders.removeBankCode())
                   .updatePaymentPsuData(paymentService, paymentProduct, paymentId, authorisationId, requestHeaders, selectPsuAuthenticationMethod);
    }

    @Override
    public GeneralResponse<ScaStatusResponse> updatePaymentPsuData(String paymentService,
                                                                   String paymentProduct,
                                                                   String paymentId,
                                                                   String authorisationId,
                                                                   RequestHeaders requestHeaders,
                                                                   TransactionAuthorisation transactionAuthorisation) {
        return bankServiceLoader
                   .getPaymentInitiationService(requestHeaders.removeBankCode())
                   .updatePaymentPsuData(paymentService, paymentProduct, paymentId, authorisationId, requestHeaders, transactionAuthorisation);
    }

    @Override
    public GeneralResponse<UpdatePsuAuthenticationResponse> updatePaymentPsuData(String paymentService,
                                                                                 String paymentProduct,
                                                                                 String paymentId,
                                                                                 String authorisationId,
                                                                                 RequestHeaders requestHeaders,
                                                                                 UpdatePsuAuthentication updatePsuAuthentication) {
        return bankServiceLoader
                   .getPaymentInitiationService(requestHeaders.removeBankCode())
                   .updatePaymentPsuData(paymentService, paymentProduct, paymentId, authorisationId, requestHeaders, updatePsuAuthentication);
    }
}
