package de.adorsys.xs2a.adapter.service;

import de.adorsys.xs2a.adapter.service.exception.NotAcceptableException;
import de.adorsys.xs2a.adapter.service.model.*;
import de.adorsys.xs2a.adapter.validation.PaymentInitiationValidationService;

public interface PaymentInitiationService extends PaymentInitiationValidationService {
    String SINGLE_PAYMENTS = "payments";

    /**
     * @deprecated This method is no longer acceptable and will be removed in release v.0.0.13
     * <p>Use {@link #initiatePayment} instead</p>
     */
    @Deprecated
    default Response<PaymentInitiationRequestResponse> initiateSinglePayment(String paymentProduct,
                                                                             RequestHeaders requestHeaders,
                                                                             RequestParams requestParams,
                                                                             Object body) {
        return initiatePayment(SINGLE_PAYMENTS, paymentProduct, requestHeaders, requestParams, body);
    }

    Response<PaymentInitiationRequestResponse> initiatePayment(String paymentService,
                                                               String paymentProduct,
                                                               RequestHeaders requestHeaders,
                                                               RequestParams requestParams,
                                                               Object body);

    Response<SinglePaymentInitiationInformationWithStatusResponse> getSinglePaymentInformation(
        String paymentProduct,
        String paymentId,
        RequestHeaders requestHeaders,
        RequestParams requestParams);

    Response<PeriodicPaymentInitiationInformationWithStatusResponse> getPeriodicPaymentInformation(
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

    /**
     * @deprecated This method is no longer acceptable and will be removed in release v.0.0.13
     * <p>Use {@link #getPaymentInformationAsString} instead</p>
     */
    @Deprecated
    default Response<String> getSinglePaymentInformationAsString(String paymentProduct,
                                                                 String paymentId,
                                                                 RequestHeaders requestHeaders,
                                                                 RequestParams requestParams) {
        return getPaymentInformationAsString(SINGLE_PAYMENTS, paymentProduct, paymentId, requestHeaders, requestParams);
    }

    Response<String> getPaymentInformationAsString(String paymentService,
                                                   String paymentProduct,
                                                   String paymentId,
                                                   RequestHeaders requestHeaders,
                                                   RequestParams requestParams);

    Response<PaymentInitiationScaStatusResponse> getPaymentInitiationScaStatus(String paymentService,
                                                                               String paymentProduct,
                                                                               String paymentId,
                                                                               String authorisationId,
                                                                               RequestHeaders requestHeaders,
                                                                               RequestParams requestParams);

    /**
     * @throws NotAcceptableException if response content type is not json
     * @deprecated This method is no longer acceptable and will be removed in release v.0.0.13
     * <p>Use {@link #getPaymentInitiationStatus} instead</p>
     */
    @Deprecated
    default Response<PaymentInitiationStatus> getSinglePaymentInitiationStatus(String paymentProduct,
                                                                               String paymentId,
                                                                               RequestHeaders requestHeaders,
                                                                               RequestParams requestParams) {
        return getPaymentInitiationStatus(SINGLE_PAYMENTS, paymentProduct, paymentId, requestHeaders, requestParams);
    }

    Response<PaymentInitiationStatus> getPaymentInitiationStatus(String paymentService,
                                                                 String paymentProduct,
                                                                 String paymentId,
                                                                 RequestHeaders requestHeaders,
                                                                 RequestParams requestParams);

    /**
     * @deprecated This method is no longer acceptable and will be removed in release v.0.0.13
     * <p>Use {@link #getPaymentInitiationStatusAsString} instead</p>
     */
    @Deprecated
    default Response<String> getSinglePaymentInitiationStatusAsString(String paymentProduct,
                                                                      String paymentId,
                                                                      RequestHeaders requestHeaders,
                                                                      RequestParams requestParams) {
        return getPaymentInitiationStatusAsString(SINGLE_PAYMENTS, paymentProduct, paymentId, requestHeaders, requestParams);
    }

    Response<String> getPaymentInitiationStatusAsString(String paymentService,
                                                        String paymentProduct,
                                                        String paymentId,
                                                        RequestHeaders requestHeaders,
                                                        RequestParams requestParams);

    Response<PaymentInitiationAuthorisationResponse> getPaymentInitiationAuthorisation(String paymentService,
                                                                                       String paymentProduct,
                                                                                       String paymentId,
                                                                                       RequestHeaders requestHeaders,
                                                                                       RequestParams requestParams);

    /**
     * @deprecated This method is no longer acceptable and will be removed in release v.0.0.13
     * <p>Use {@link #startPaymentAuthorisation)} instead</p>
     */
    @Deprecated
    default Response<StartScaProcessResponse> startSinglePaymentAuthorisation(String paymentProduct,
                                                                              String paymentId,
                                                                              RequestHeaders requestHeaders,
                                                                              RequestParams requestParams) {
        return startPaymentAuthorisation(SINGLE_PAYMENTS, paymentProduct, paymentId, requestHeaders, requestParams);
    }

    Response<StartScaProcessResponse> startPaymentAuthorisation(String paymentService,
                                                                String paymentProduct,
                                                                String paymentId,
                                                                RequestHeaders requestHeaders,
                                                                RequestParams requestParams);

    /**
     * @deprecated This method is no longer acceptable and will be removed in release v.0.0.13
     * <p>Use {@link #startPaymentAuthorisation} instead</p>
     */
    @Deprecated
    default Response<StartScaProcessResponse> startSinglePaymentAuthorisation(String paymentProduct,
                                                                      String paymentId,
                                                                      RequestHeaders requestHeaders,
                                                                      RequestParams requestParams,
                                                                      UpdatePsuAuthentication updatePsuAuthentication) {
        return startPaymentAuthorisation(SINGLE_PAYMENTS, paymentProduct, paymentId, requestHeaders,
            requestParams, updatePsuAuthentication);
    }

    Response<StartScaProcessResponse> startPaymentAuthorisation(String paymentService,
                                                                String paymentProduct,
                                                                String paymentId,
                                                                RequestHeaders requestHeaders,
                                                                RequestParams requestParams,
                                                                UpdatePsuAuthentication updatePsuAuthentication);

    Response<SelectPsuAuthenticationMethodResponse> updatePaymentPsuData(String paymentService,
                                                                         String paymentProduct,
                                                                         String paymentId,
                                                                         String authorisationId,
                                                                         RequestHeaders requestHeaders,
                                                                         RequestParams requestParams,
                                                                         SelectPsuAuthenticationMethod selectPsuAuthenticationMethod);

    Response<ScaStatusResponse> updatePaymentPsuData(String paymentService,
                                                     String paymentProduct,
                                                     String paymentId,
                                                     String authorisationId,
                                                     RequestHeaders requestHeaders,
                                                     RequestParams requestParams,
                                                     TransactionAuthorisation transactionAuthorisation);

    Response<UpdatePsuAuthenticationResponse> updatePaymentPsuData(String paymentService,
                                                                   String paymentProduct,
                                                                   String paymentId,
                                                                   String authorisationId,
                                                                   RequestHeaders requestHeaders,
                                                                   RequestParams requestParams,
                                                                   UpdatePsuAuthentication updatePsuAuthentication);
}
