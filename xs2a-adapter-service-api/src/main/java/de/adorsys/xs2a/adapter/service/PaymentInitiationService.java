package de.adorsys.xs2a.adapter.service;

import de.adorsys.xs2a.adapter.api.model.*;
import de.adorsys.xs2a.adapter.service.exception.NotAcceptableException;
import de.adorsys.xs2a.adapter.validation.PaymentInitiationValidationService;

public interface PaymentInitiationService extends PaymentInitiationValidationService {

    Response<PaymentInitationRequestResponse201> initiateSinglePayment(String paymentProduct,
                                                                       RequestHeaders requestHeaders,
                                                                       RequestParams requestParams,
                                                                       Object body);

    Response<PaymentInitiationWithStatusResponse> getSinglePaymentInformation(
        String paymentProduct,
        String paymentId,
        RequestHeaders requestHeaders,
        RequestParams requestParams);

    Response<String> getSinglePaymentInformationAsString(String paymentProduct,
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
     * @return
     */
    Response<PaymentInitiationStatusResponse200Json> getSinglePaymentInitiationStatus(String paymentProduct,
                                                                                      String paymentId,
                                                                                      RequestHeaders requestHeaders,
                                                                                      RequestParams requestParams);

    Response<String> getSinglePaymentInitiationStatusAsString(String paymentProduct,
                                                              String paymentId,
                                                              RequestHeaders requestHeaders,
                                                              RequestParams requestParams);

    Response<Authorisations> getPaymentInitiationAuthorisation(String paymentService,
                                                               String paymentProduct,
                                                               String paymentId,
                                                               RequestHeaders requestHeaders,
                                                               RequestParams requestParams);

    Response<StartScaprocessResponse> startSinglePaymentAuthorisation(String paymentProduct,
                                                                      String paymentId,
                                                                      RequestHeaders requestHeaders,
                                                                      RequestParams requestParams);

    Response<StartScaprocessResponse> startSinglePaymentAuthorisation(String paymentProduct,
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
