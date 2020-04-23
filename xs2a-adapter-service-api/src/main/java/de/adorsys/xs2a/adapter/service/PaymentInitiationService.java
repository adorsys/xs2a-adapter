package de.adorsys.xs2a.adapter.service;

import de.adorsys.xs2a.adapter.service.exception.NotAcceptableException;
import de.adorsys.xs2a.adapter.service.model.*;
import de.adorsys.xs2a.adapter.validation.PaymentInitiationValidationService;

public interface PaymentInitiationService extends PaymentInitiationValidationService {

    Response<PaymentInitiationRequestResponse> initiateSinglePayment(String paymentProduct,
                                                                     RequestHeaders requestHeaders,
                                                                     RequestParams requestParams,
                                                                     Object body);

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

    Response<String> getSinglePaymentInformationAsString(String paymentProduct,
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
     */
    Response<PaymentInitiationStatus> getSinglePaymentInitiationStatus(String paymentProduct,
                                                                       String paymentId,
                                                                       RequestHeaders requestHeaders,
                                                                       RequestParams requestParams);

    Response<String> getSinglePaymentInitiationStatusAsString(String paymentProduct,
                                                              String paymentId,
                                                              RequestHeaders requestHeaders,
                                                              RequestParams requestParams);

    Response<PaymentInitiationAuthorisationResponse> getPaymentInitiationAuthorisation(String paymentService,
                                                                                       String paymentProduct,
                                                                                       String paymentId,
                                                                                       RequestHeaders requestHeaders,
                                                                                       RequestParams requestParams);

    Response<StartScaProcessResponse> startSinglePaymentAuthorisation(String paymentProduct,
                                                                      String paymentId,
                                                                      RequestHeaders requestHeaders,
                                                                      RequestParams requestParams);

    Response<StartScaProcessResponse> startSinglePaymentAuthorisation(String paymentProduct,
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
