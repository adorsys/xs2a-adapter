package de.adorsys.xs2a.adapter.api;

import de.adorsys.xs2a.adapter.api.model.*;
import de.adorsys.xs2a.adapter.api.validation.PaymentInitiationValidationService;

public interface PaymentInitiationService extends PaymentInitiationValidationService {

    Response<PaymentInitationRequestResponse201> initiatePayment(PaymentService paymentService,
                                                                 PaymentProduct paymentProduct,
                                                                 RequestHeaders requestHeaders,
                                                                 RequestParams requestParams,
                                                                 Object body);

    Response<PaymentInitiationWithStatusResponse> getSinglePaymentInformation(PaymentProduct paymentProduct,
                                                                              String paymentId,
                                                                              RequestHeaders requestHeaders,
                                                                              RequestParams requestParams);

    Response<PeriodicPaymentInitiationWithStatusResponse> getPeriodicPaymentInformation(PaymentProduct paymentProduct,
                                                                                        String paymentId,
                                                                                        RequestHeaders requestHeaders,
                                                                                        RequestParams requestParams);

    Response<PeriodicPaymentInitiationMultipartBody> getPeriodicPain001PaymentInformation(PaymentProduct paymentProduct,
                                                                                          String paymentId,
                                                                                          RequestHeaders requestHeaders,
                                                                                          RequestParams requestParams);

    Response<String> getPaymentInformationAsString(PaymentService paymentService,
                                                   PaymentProduct paymentProduct,
                                                   String paymentId,
                                                   RequestHeaders requestHeaders,
                                                   RequestParams requestParams);

    Response<ScaStatusResponse> getPaymentInitiationScaStatus(PaymentService paymentService,
                                                              PaymentProduct paymentProduct,
                                                              String paymentId,
                                                              String authorisationId,
                                                              RequestHeaders requestHeaders,
                                                              RequestParams requestParams);

    Response<PaymentInitiationStatusResponse200Json> getPaymentInitiationStatus(PaymentService paymentService,
                                                                                PaymentProduct paymentProduct,
                                                                                String paymentId,
                                                                                RequestHeaders requestHeaders,
                                                                                RequestParams requestParams);

    Response<String> getPaymentInitiationStatusAsString(PaymentService paymentService,
                                                        PaymentProduct paymentProduct,
                                                        String paymentId,
                                                        RequestHeaders requestHeaders,
                                                        RequestParams requestParams);

    Response<Authorisations> getPaymentInitiationAuthorisation(PaymentService paymentService,
                                                               PaymentProduct paymentProduct,
                                                               String paymentId,
                                                               RequestHeaders requestHeaders,
                                                               RequestParams requestParams);

    Response<StartScaprocessResponse> startPaymentAuthorisation(PaymentService paymentService,
                                                                PaymentProduct paymentProduct,
                                                                String paymentId,
                                                                RequestHeaders requestHeaders,
                                                                RequestParams requestParams);

    Response<StartScaprocessResponse> startPaymentAuthorisation(PaymentService paymentService,
                                                                PaymentProduct paymentProduct,
                                                                String paymentId,
                                                                RequestHeaders requestHeaders,
                                                                RequestParams requestParams,
                                                                UpdatePsuAuthentication body);

    Response<SelectPsuAuthenticationMethodResponse> updatePaymentPsuData(PaymentService paymentService,
                                                                         PaymentProduct paymentProduct,
                                                                         String paymentId,
                                                                         String authorisationId,
                                                                         RequestHeaders requestHeaders,
                                                                         RequestParams requestParams,
                                                                         SelectPsuAuthenticationMethod body);

    Response<ScaStatusResponse> updatePaymentPsuData(PaymentService paymentService,
                                                     PaymentProduct paymentProduct,
                                                     String paymentId,
                                                     String authorisationId,
                                                     RequestHeaders requestHeaders,
                                                     RequestParams requestParams,
                                                     TransactionAuthorisation body);

    Response<UpdatePsuAuthenticationResponse> updatePaymentPsuData(PaymentService paymentService,
                                                                   PaymentProduct paymentProduct,
                                                                   String paymentId,
                                                                   String authorisationId,
                                                                   RequestHeaders requestHeaders,
                                                                   RequestParams requestParams,
                                                                   UpdatePsuAuthentication body);
}
