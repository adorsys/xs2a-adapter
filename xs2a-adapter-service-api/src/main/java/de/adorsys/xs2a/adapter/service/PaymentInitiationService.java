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

    Response<SinglePaymentInitiationInformationWithStatusResponse> getSinglePaymentInformation(String paymentProduct,
                                                                                               String paymentId,
                                                                                               RequestHeaders requestHeaders);

    Response<PaymentInitiationScaStatusResponse> getPaymentInitiationScaStatus(String paymentService,
                                                                               String paymentProduct,
                                                                               String paymentId,
                                                                               String authorisationId,
                                                                               RequestHeaders requestHeaders);

    /**
     * @throws NotAcceptableException if response content type is not json
     */
    Response<PaymentInitiationStatus> getSinglePaymentInitiationStatus(String paymentProduct,
                                                                       String paymentId,
                                                                       RequestHeaders requestHeaders);

    Response<String> getSinglePaymentInitiationStatusAsString(String paymentProduct,
                                                              String paymentId,
                                                              RequestHeaders requestHeaders);

    Response<PaymentInitiationAuthorisationResponse> getPaymentInitiationAuthorisation(String paymentService,
                                                                                       String paymentProduct,
                                                                                       String paymentId,
                                                                                       RequestHeaders requestHeaders);

    Response<StartScaProcessResponse> startSinglePaymentAuthorisation(String paymentProduct,
                                                                      String paymentId,
                                                                      RequestHeaders requestHeaders,
                                                                      UpdatePsuAuthentication updatePsuAuthentication);

    Response<SelectPsuAuthenticationMethodResponse> updatePaymentPsuData(String paymentService,
                                                                         String paymentProduct,
                                                                         String paymentId,
                                                                         String authorisationId,
                                                                         RequestHeaders requestHeaders,
                                                                         SelectPsuAuthenticationMethod selectPsuAuthenticationMethod);

    Response<ScaStatusResponse> updatePaymentPsuData(String paymentService,
                                                     String paymentProduct,
                                                     String paymentId,
                                                     String authorisationId,
                                                     RequestHeaders requestHeaders,
                                                     TransactionAuthorisation transactionAuthorisation);

    Response<UpdatePsuAuthenticationResponse> updatePaymentPsuData(String paymentService,
                                                                   String paymentProduct,
                                                                   String paymentId,
                                                                   String authorisationId,
                                                                   RequestHeaders requestHeaders,
                                                                   UpdatePsuAuthentication updatePsuAuthentication
    );
}
