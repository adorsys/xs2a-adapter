package de.adorsys.xs2a.adapter.service;

import de.adorsys.xs2a.adapter.api.model.*;
import de.adorsys.xs2a.adapter.service.exception.NotAcceptableException;
import de.adorsys.xs2a.adapter.validation.PaymentInitiationValidationService;

public interface PaymentInitiationService extends PaymentInitiationValidationService {

    /**
     * @deprecated This method is no longer acceptable and will be removed in release v.0.1.1
     * <p>Use {@link #initiatePayment} instead</p>
     */
    @Deprecated
    default Response<PaymentInitationRequestResponse201> initiateSinglePayment(String paymentProduct,
                                                                               RequestHeaders requestHeaders,
                                                                               RequestParams requestParams,
                                                                               Object body) {
        return initiatePayment(PaymentService.PAYMENTS.toString(), paymentProduct, requestHeaders, requestParams, body);
    }

    Response<PaymentInitationRequestResponse201> initiatePayment(String paymentService,
                                                                 String paymentProduct,
                                                                 RequestHeaders requestHeaders,
                                                                 RequestParams requestParams,
                                                                 Object body);

    Response<PaymentInitiationWithStatusResponse> getSinglePaymentInformation(
        String paymentProduct,
        String paymentId,
        RequestHeaders requestHeaders,
        RequestParams requestParams);

    Response<PeriodicPaymentInitiationWithStatusResponse> getPeriodicPaymentInformation(
        String paymentProduct,
        String paymentId,
        RequestHeaders requestHeaders,
        RequestParams requestParams);

    Response<PeriodicPaymentInitiationMultipartBody> getPeriodicPain001PaymentInformation(String paymentProduct,
                                                                                          String paymentId,
                                                                                          RequestHeaders requestHeaders,
                                                                                          RequestParams requestParams);

    @Deprecated
    default Response<ScaStatusResponse> getPaymentInitiationScaStatus(String paymentService,
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
     * @deprecated This method is no longer acceptable and will be removed in release v.0.1.1
     * <p>Use {@link #getPaymentInformationAsString} instead</p>
     */
    @Deprecated
    default Response<String> getSinglePaymentInformationAsString(String paymentProduct,
                                                                 String paymentId,
                                                                 RequestHeaders requestHeaders,
                                                                 RequestParams requestParams) {
        return getPaymentInformationAsString(PaymentService.PAYMENTS.toString(), paymentProduct, paymentId, requestHeaders, requestParams);
    }

    Response<String> getPaymentInformationAsString(String paymentService,
                                                   String paymentProduct,
                                                   String paymentId,
                                                   RequestHeaders requestHeaders,
                                                   RequestParams requestParams);

    Response<ScaStatusResponse> getPaymentInitiationScaStatus(String paymentService,
                                                              String paymentProduct,
                                                              String paymentId,
                                                              String authorisationId,
                                                              RequestHeaders requestHeaders,
                                                              RequestParams requestParams);

    /**
     * @throws NotAcceptableException if response content type is not json
     * @deprecated This method is no longer acceptable and will be removed in release v.0.1.1
     * <p>Use {@link #getPaymentInitiationStatus} instead</p>
     */
    @Deprecated
    default Response<PaymentInitiationStatusResponse200Json> getSinglePaymentInitiationStatus(String paymentProduct,
                                                                               String paymentId,
                                                                               RequestHeaders requestHeaders,
                                                                               RequestParams requestParams) {
        return getPaymentInitiationStatus(PaymentService.PAYMENTS.toString(), paymentProduct, paymentId, requestHeaders, requestParams);
    }

    Response<PaymentInitiationStatusResponse200Json> getPaymentInitiationStatus(String paymentService,
                                                                 String paymentProduct,
                                                                 String paymentId,
                                                                 RequestHeaders requestHeaders,
                                                                 RequestParams requestParams);

    /**
     * @deprecated This method is no longer acceptable and will be removed in release v.0.1.1
     * <p>Use {@link #getPaymentInitiationStatusAsString} instead</p>
     */
    @Deprecated
    default Response<String> getSinglePaymentInitiationStatusAsString(String paymentProduct,
                                                                      String paymentId,
                                                                      RequestHeaders requestHeaders,
                                                                      RequestParams requestParams) {
        return getPaymentInitiationStatusAsString(PaymentService.PAYMENTS.toString(), paymentProduct, paymentId, requestHeaders, requestParams);
    }

    Response<String> getPaymentInitiationStatusAsString(String paymentService,
                                                        String paymentProduct,
                                                        String paymentId,
                                                        RequestHeaders requestHeaders,
                                                        RequestParams requestParams);

    Response<Authorisations> getPaymentInitiationAuthorisation(String paymentService,
                                                               String paymentProduct,
                                                               String paymentId,
                                                               RequestHeaders requestHeaders,
                                                               RequestParams requestParams);

    /**
     * @deprecated This method is no longer acceptable and will be removed in release v.0.1.1
     * <p>Use {@link #startPaymentAuthorisation)} instead</p>
     */
    @Deprecated
    default Response<StartScaprocessResponse> startSinglePaymentAuthorisation(String paymentProduct,
                                                                              String paymentId,
                                                                              RequestHeaders requestHeaders,
                                                                              RequestParams requestParams) {
        return startPaymentAuthorisation(PaymentService.PAYMENTS.toString(), paymentProduct, paymentId, requestHeaders, requestParams);
    }

    Response<StartScaprocessResponse> startPaymentAuthorisation(String paymentService,
                                                                String paymentProduct,
                                                                String paymentId,
                                                                RequestHeaders requestHeaders,
                                                                RequestParams requestParams);

    /**
     * @deprecated This method is no longer acceptable and will be removed in release v.0.1.1
     * <p>Use {@link #startPaymentAuthorisation} instead</p>
     */
    @Deprecated
    default Response<StartScaprocessResponse> startSinglePaymentAuthorisation(String paymentProduct,
                                                                              String paymentId,
                                                                              RequestHeaders requestHeaders,
                                                                              RequestParams requestParams,
                                                                              UpdatePsuAuthentication updatePsuAuthentication) {
        return startPaymentAuthorisation(PaymentService.PAYMENTS.toString(), paymentProduct, paymentId, requestHeaders,
            requestParams, updatePsuAuthentication);
    }

    Response<StartScaprocessResponse> startPaymentAuthorisation(String paymentService,
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
