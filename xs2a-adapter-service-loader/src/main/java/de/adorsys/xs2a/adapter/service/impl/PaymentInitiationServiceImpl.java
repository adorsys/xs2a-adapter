package de.adorsys.xs2a.adapter.service.impl;

import de.adorsys.xs2a.adapter.service.*;
import de.adorsys.xs2a.adapter.service.model.*;

public class PaymentInitiationServiceImpl implements PaymentInitiationService {
    private final AdapterServiceLoader adapterServiceLoader;

    public PaymentInitiationServiceImpl(AdapterServiceLoader adapterServiceLoader) {
        this.adapterServiceLoader = adapterServiceLoader;
    }

    @Override
    public GeneralResponse<PaymentInitiationRequestResponse> initiateSinglePayment(String paymentProduct,
                                                                                   RequestHeaders requestHeaders,
                                                                                   Object body) {
        return getPaymentInitiationService(requestHeaders)
                   .initiateSinglePayment(paymentProduct, requestHeaders, body);
    }

    private PaymentInitiationService getPaymentInitiationService(RequestHeaders requestHeaders) {
        return adapterServiceLoader.getPaymentInitiationService(requestHeaders.removeAspspId());
    }

    @Override
    public GeneralResponse<SinglePaymentInitiationInformationWithStatusResponse> getSinglePaymentInformation(String paymentProduct,
                                                                                                             String paymentId,
                                                                                                             RequestHeaders requestHeaders) {
        return getPaymentInitiationService(requestHeaders)
                   .getSinglePaymentInformation(paymentProduct, paymentId, requestHeaders);
    }

    @Override
    public GeneralResponse<PaymentInitiationScaStatusResponse> getPaymentInitiationScaStatus(String paymentService,
                                                                                             String paymentProduct,
                                                                                             String paymentId,
                                                                                             String authorisationId,
                                                                                             RequestHeaders requestHeaders) {
        return getPaymentInitiationService(requestHeaders)
                   .getPaymentInitiationScaStatus(paymentService, paymentProduct, paymentId, authorisationId, requestHeaders);
    }

    @Override
    public GeneralResponse<PaymentInitiationStatus> getSinglePaymentInitiationStatus(String paymentProduct,
                                                                                     String paymentId,
                                                                                     RequestHeaders requestHeaders) {
        return getPaymentInitiationService(requestHeaders)
                   .getSinglePaymentInitiationStatus(paymentProduct, paymentId, requestHeaders);
    }

    @Override
    public GeneralResponse<String> getSinglePaymentInitiationStatusAsString(String paymentProduct,
                                                                            String paymentId,
                                                                            RequestHeaders requestHeaders) {
        return getPaymentInitiationService(requestHeaders)
                   .getSinglePaymentInitiationStatusAsString(paymentProduct, paymentId, requestHeaders);
    }

    @Override
    public GeneralResponse<PaymentInitiationAuthorisationResponse> getPaymentInitiationAuthorisation(String paymentService,
                                                                                                     String paymentProduct,
                                                                                                     String paymentId,
                                                                                                     RequestHeaders requestHeaders) {
        return getPaymentInitiationService(requestHeaders)
                   .getPaymentInitiationAuthorisation(paymentService, paymentProduct, paymentId, requestHeaders);
    }

    @Override
    public GeneralResponse<StartScaProcessResponse> startSinglePaymentAuthorisation(String paymentProduct,
                                                                                    String paymentId,
                                                                                    RequestHeaders requestHeaders,
                                                                                    UpdatePsuAuthentication updatePsuAuthentication) {
        return getPaymentInitiationService(requestHeaders)
                   .startSinglePaymentAuthorisation(paymentProduct, paymentId, requestHeaders, updatePsuAuthentication);
    }

    @Override
    public GeneralResponse<SelectPsuAuthenticationMethodResponse> updatePaymentPsuData(String paymentService,
                                                                                       String paymentProduct,
                                                                                       String paymentId,
                                                                                       String authorisationId,
                                                                                       RequestHeaders requestHeaders,
                                                                                       SelectPsuAuthenticationMethod selectPsuAuthenticationMethod) {
        return getPaymentInitiationService(requestHeaders)
                   .updatePaymentPsuData(paymentService, paymentProduct, paymentId, authorisationId, requestHeaders, selectPsuAuthenticationMethod);
    }

    @Override
    public GeneralResponse<ScaStatusResponse> updatePaymentPsuData(String paymentService,
                                                                   String paymentProduct,
                                                                   String paymentId,
                                                                   String authorisationId,
                                                                   RequestHeaders requestHeaders,
                                                                   TransactionAuthorisation transactionAuthorisation) {
        return getPaymentInitiationService(requestHeaders)
                   .updatePaymentPsuData(paymentService, paymentProduct, paymentId, authorisationId, requestHeaders, transactionAuthorisation);
    }

    @Override
    public GeneralResponse<UpdatePsuAuthenticationResponse> updatePaymentPsuData(String paymentService,
                                                                                 String paymentProduct,
                                                                                 String paymentId,
                                                                                 String authorisationId,
                                                                                 RequestHeaders requestHeaders,
                                                                                 UpdatePsuAuthentication updatePsuAuthentication) {
        return getPaymentInitiationService(requestHeaders)
                   .updatePaymentPsuData(paymentService, paymentProduct, paymentId, authorisationId, requestHeaders, updatePsuAuthentication);
    }
}
