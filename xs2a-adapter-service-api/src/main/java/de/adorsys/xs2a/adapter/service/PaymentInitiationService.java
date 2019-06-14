package de.adorsys.xs2a.adapter.service;

import de.adorsys.xs2a.adapter.service.exception.NotAcceptableException;
import de.adorsys.xs2a.adapter.service.model.*;

public interface PaymentInitiationService {
    GeneralResponse<PaymentInitiationRequestResponse> initiateSinglePayment(String paymentProduct,
                                                                            RequestHeaders requestHeaders,
                                                                            Object body);

    GeneralResponse<SinglePaymentInitiationInformationWithStatusResponse> getSinglePaymentInformation(String paymentProduct,
                                                                                                      String paymentId,
                                                                                                      RequestHeaders requestHeaders);

    GeneralResponse<PaymentInitiationScaStatusResponse> getPaymentInitiationScaStatus(String paymentService,
                                                                                      String paymentProduct,
                                                                                      String paymentId,
                                                                                      String authorisationId,
                                                                                      RequestHeaders requestHeaders);

    /**
     * @throws NotAcceptableException if response content type is not json
     */
    GeneralResponse<PaymentInitiationStatus> getSinglePaymentInitiationStatus(String paymentProduct,
                                                                              String paymentId,
                                                                              RequestHeaders requestHeaders);

    GeneralResponse<String> getSinglePaymentInitiationStatusAsString(String paymentProduct,
                                                                     String paymentId,
                                                                     RequestHeaders requestHeaders);

    GeneralResponse<PaymentInitiationAuthorisationResponse> getPaymentInitiationAuthorisation(String paymentService,
                                                                                              String paymentProduct,
                                                                                              String paymentId,
                                                                                              RequestHeaders requestHeaders);

    GeneralResponse<StartScaProcessResponse> startSinglePaymentAuthorisation(String paymentProduct,
                                                                             String paymentId,
                                                                             RequestHeaders requestHeaders,
                                                                             UpdatePsuAuthentication updatePsuAuthentication);

    GeneralResponse<SelectPsuAuthenticationMethodResponse> updateConsentsPsuData(String paymentService,
                                                                                 String paymentProduct,
                                                                                 String paymentId,
                                                                                 String authorisationId,
                                                                                 RequestHeaders requestHeaders,
                                                                                 SelectPsuAuthenticationMethod selectPsuAuthenticationMethod);

    GeneralResponse<ScaStatusResponse> updateConsentsPsuData(String paymentService,
                                                             String paymentProduct,
                                                             String paymentId,
                                                             String authorisationId,
                                                             RequestHeaders requestHeaders,
                                                             TransactionAuthorisation transactionAuthorisation);

    GeneralResponse<UpdatePsuAuthenticationResponse> updateConsentsPsuData(String paymentService,
                                                                           String paymentProduct,
                                                                           String paymentId,
                                                                           String authorisationId,
                                                                           RequestHeaders requestHeaders,
                                                                           UpdatePsuAuthentication updatePsuAuthentication
    );
}
