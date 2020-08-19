package de.adorsys.xs2a.adapter.api;

import de.adorsys.xs2a.adapter.api.model.*;
import de.adorsys.xs2a.adapter.api.validation.PaymentInitiationValidationService;

public interface PaymentInitiationService extends PaymentInitiationValidationService {

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

    Response<PaymentInitiationStatusResponse200Json> getPaymentInitiationStatus(String paymentService,
                                                                 String paymentProduct,
                                                                 String paymentId,
                                                                 RequestHeaders requestHeaders,
                                                                 RequestParams requestParams);

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

    Response<StartScaprocessResponse> startPaymentAuthorisation(String paymentService,
                                                                String paymentProduct,
                                                                String paymentId,
                                                                RequestHeaders requestHeaders,
                                                                RequestParams requestParams);

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
