package de.adorsys.xs2a.adapter.service;

import de.adorsys.xs2a.adapter.service.exception.NotAcceptableException;
import de.adorsys.xs2a.adapter.service.model.*;

public interface PaymentInitiationService {
    @Deprecated
    default Response<PaymentInitiationRequestResponse> initiateSinglePayment(String paymentProduct,
                                                                             RequestHeaders requestHeaders,
                                                                             Object body) {
        return initiateSinglePayment(paymentProduct, requestHeaders, RequestParams.empty(), body);
    }

    Response<PaymentInitiationRequestResponse> initiateSinglePayment(String paymentProduct,
                                                                     RequestHeaders requestHeaders,
                                                                     RequestParams requestParams,
                                                                     Object body);

    @Deprecated
    default Response<SinglePaymentInitiationInformationWithStatusResponse> getSinglePaymentInformation(String paymentProduct,
                                                                                                       String paymentId,
                                                                                                       RequestHeaders requestHeaders) {
        return getSinglePaymentInformation(paymentProduct, paymentId, requestHeaders, RequestParams.empty());
    }

    Response<SinglePaymentInitiationInformationWithStatusResponse> getSinglePaymentInformation(
        String paymentProduct,
        String paymentId,
        RequestHeaders requestHeaders,
        RequestParams requestParams);

    @Deprecated
    default Response<PaymentInitiationScaStatusResponse> getPaymentInitiationScaStatus(String paymentService,
                                                                                       String paymentProduct,
                                                                                       String paymentId,
                                                                                       String authorisationId,
                                                                                       RequestHeaders requestHeaders) {
        return getPaymentInitiationScaStatus(paymentService,
            paymentProduct,
            paymentId,
            authorisationId,
            requestHeaders,
            RequestParams.empty());
    }

    Response<PaymentInitiationScaStatusResponse> getPaymentInitiationScaStatus(String paymentService,
                                                                               String paymentProduct,
                                                                               String paymentId,
                                                                               String authorisationId,
                                                                               RequestHeaders requestHeaders,
                                                                               RequestParams requestParams);

    @Deprecated
    default Response<PaymentInitiationStatus> getSinglePaymentInitiationStatus(String paymentProduct,
                                                                               String paymentId,
                                                                               RequestHeaders requestHeaders) {
        return getSinglePaymentInitiationStatus(paymentProduct, paymentId, requestHeaders, RequestParams.empty());
    }

    /**
     * @throws NotAcceptableException if response content type is not json
     */
    Response<PaymentInitiationStatus> getSinglePaymentInitiationStatus(String paymentProduct,
                                                                       String paymentId,
                                                                       RequestHeaders requestHeaders,
                                                                       RequestParams requestParams);

    @Deprecated
    default Response<String> getSinglePaymentInitiationStatusAsString(String paymentProduct,
                                                              String paymentId,
                                                              RequestHeaders requestHeaders) {
        return getSinglePaymentInitiationStatusAsString(paymentProduct,
            paymentId,
            requestHeaders,
            RequestParams.empty());
    }

    Response<String> getSinglePaymentInitiationStatusAsString(String paymentProduct,
                                                              String paymentId,
                                                              RequestHeaders requestHeaders,
                                                              RequestParams requestParams);

    @Deprecated
    default Response<PaymentInitiationAuthorisationResponse> getPaymentInitiationAuthorisation(String paymentService,
                                                                                       String paymentProduct,
                                                                                       String paymentId,
                                                                                       RequestHeaders requestHeaders) {
        return getPaymentInitiationAuthorisation(paymentService,
            paymentProduct,
            paymentId,
            requestHeaders,
            RequestParams.empty());
    }

    Response<PaymentInitiationAuthorisationResponse> getPaymentInitiationAuthorisation(String paymentService,
                                                                                       String paymentProduct,
                                                                                       String paymentId,
                                                                                       RequestHeaders requestHeaders,
                                                                                       RequestParams requestParams);

    @Deprecated
    default Response<StartScaProcessResponse> startSinglePaymentAuthorisation(String paymentProduct,
                                                                      String paymentId,
                                                                      RequestHeaders requestHeaders,
                                                                      UpdatePsuAuthentication updatePsuAuthentication) {
        return startSinglePaymentAuthorisation(paymentProduct,
            paymentId,
            requestHeaders,
            RequestParams.empty(),
            updatePsuAuthentication);
    }

    Response<StartScaProcessResponse> startSinglePaymentAuthorisation(String paymentProduct,
                                                                      String paymentId,
                                                                      RequestHeaders requestHeaders,
                                                                      RequestParams requestParams,
                                                                      UpdatePsuAuthentication updatePsuAuthentication);

    @Deprecated
    default Response<SelectPsuAuthenticationMethodResponse> updatePaymentPsuData(String paymentService,
                                                                         String paymentProduct,
                                                                         String paymentId,
                                                                         String authorisationId,
                                                                         RequestHeaders requestHeaders,
                                                                         SelectPsuAuthenticationMethod selectPsuAuthenticationMethod) {
        return updatePaymentPsuData(paymentService,
            paymentProduct,
            paymentId,
            authorisationId,
            requestHeaders,
            RequestParams.empty(),
            selectPsuAuthenticationMethod);
    }

    Response<SelectPsuAuthenticationMethodResponse> updatePaymentPsuData(String paymentService,
                                                                         String paymentProduct,
                                                                         String paymentId,
                                                                         String authorisationId,
                                                                         RequestHeaders requestHeaders,
                                                                         RequestParams requestParams,
                                                                         SelectPsuAuthenticationMethod selectPsuAuthenticationMethod);

    @Deprecated
    default Response<ScaStatusResponse> updatePaymentPsuData(String paymentService,
                                                     String paymentProduct,
                                                     String paymentId,
                                                     String authorisationId,
                                                     RequestHeaders requestHeaders,
                                                     TransactionAuthorisation transactionAuthorisation) {
        return updatePaymentPsuData(paymentService,
            paymentProduct,
            paymentId,
            authorisationId,
            requestHeaders,
            RequestParams.empty(),
            transactionAuthorisation);
    }

    Response<ScaStatusResponse> updatePaymentPsuData(String paymentService,
                                                     String paymentProduct,
                                                     String paymentId,
                                                     String authorisationId,
                                                     RequestHeaders requestHeaders,
                                                     RequestParams requestParams,
                                                     TransactionAuthorisation transactionAuthorisation);

    @Deprecated
    default Response<UpdatePsuAuthenticationResponse> updatePaymentPsuData(String paymentService,
                                                                   String paymentProduct,
                                                                   String paymentId,
                                                                   String authorisationId,
                                                                   RequestHeaders requestHeaders,
                                                                   UpdatePsuAuthentication updatePsuAuthentication) {
        return updatePaymentPsuData(paymentService,
            paymentProduct,
            paymentId,
            authorisationId,
            requestHeaders,
            RequestParams.empty(),
            updatePsuAuthentication);
    }

    Response<UpdatePsuAuthenticationResponse> updatePaymentPsuData(String paymentService,
                                                                   String paymentProduct,
                                                                   String paymentId,
                                                                   String authorisationId,
                                                                   RequestHeaders requestHeaders,
                                                                   RequestParams requestParams,
                                                                   UpdatePsuAuthentication updatePsuAuthentication);
}
