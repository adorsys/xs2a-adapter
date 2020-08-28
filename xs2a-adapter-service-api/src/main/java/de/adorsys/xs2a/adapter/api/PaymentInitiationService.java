package de.adorsys.xs2a.adapter.api;

import de.adorsys.xs2a.adapter.api.model.*;
import de.adorsys.xs2a.adapter.api.validation.PaymentInitiationValidationService;

public interface PaymentInitiationService extends PaymentInitiationValidationService {

    /**
     * @deprecated use the version with enum PaymentService/Product parameters
     */
    @Deprecated
    default Response<PaymentInitationRequestResponse201> initiatePayment(String paymentService,
                                                                         String paymentProduct,
                                                                         RequestHeaders requestHeaders,
                                                                         RequestParams requestParams,
                                                                         Object body) {
        return initiatePayment(PaymentService.fromValue(paymentService),
            PaymentProduct.fromValue(paymentProduct),
            requestHeaders,
            requestParams,
            body);
    }


    Response<PaymentInitationRequestResponse201> initiatePayment(PaymentService paymentService,
                                                                 PaymentProduct paymentProduct,
                                                                 RequestHeaders requestHeaders,
                                                                 RequestParams requestParams,
                                                                 Object body);
    /**
     * @deprecated use the version with enum PaymentProduct parameter
     */
    @Deprecated
    default Response<PaymentInitiationWithStatusResponse> getSinglePaymentInformation(String paymentProduct,
                                                                                      String paymentId,
                                                                                      RequestHeaders requestHeaders,
                                                                                      RequestParams requestParams) {

        return getSinglePaymentInformation(PaymentProduct.fromValue(paymentProduct),
            paymentId,
            requestHeaders,
            requestParams);
    }

    Response<PaymentInitiationWithStatusResponse> getSinglePaymentInformation(PaymentProduct paymentProduct,
                                                                              String paymentId,
                                                                              RequestHeaders requestHeaders,
                                                                              RequestParams requestParams);

    /**
     * @deprecated use the version with enum PaymentProduct parameter
     */
    @Deprecated
    default Response<PeriodicPaymentInitiationWithStatusResponse> getPeriodicPaymentInformation(
        String paymentProduct,
        String paymentId,
        RequestHeaders requestHeaders,
        RequestParams requestParams) {

        return getPeriodicPaymentInformation(PaymentProduct.fromValue(paymentProduct),
            paymentId,
            requestHeaders,
            requestParams);
    }

    Response<PeriodicPaymentInitiationWithStatusResponse> getPeriodicPaymentInformation(PaymentProduct paymentProduct,
                                                                                        String paymentId,
                                                                                        RequestHeaders requestHeaders,
                                                                                        RequestParams requestParams);

    /**
     * @deprecated use the version with enum PaymentProduct parameter
     */
    @Deprecated
    default Response<PeriodicPaymentInitiationMultipartBody> getPeriodicPain001PaymentInformation(
        String paymentProduct,
        String paymentId,
        RequestHeaders requestHeaders,
        RequestParams requestParams) {

        return getPeriodicPain001PaymentInformation(PaymentProduct.fromValue(paymentProduct),
            paymentId,
            requestHeaders,
            requestParams);
    }

    Response<PeriodicPaymentInitiationMultipartBody> getPeriodicPain001PaymentInformation(PaymentProduct paymentProduct,
                                                                                          String paymentId,
                                                                                          RequestHeaders requestHeaders,
                                                                                          RequestParams requestParams);

    /**
     * @deprecated use the version with enum PaymentService/Product parameters
     */
    @Deprecated
    default Response<String> getPaymentInformationAsString(String paymentService,
                                                           String paymentProduct,
                                                           String paymentId,
                                                           RequestHeaders requestHeaders,
                                                           RequestParams requestParams) {
        return getPaymentInformationAsString(PaymentService.fromValue(paymentService),
            PaymentProduct.fromValue(paymentProduct),
            paymentId,
            requestHeaders,
            requestParams);
    }

    Response<String> getPaymentInformationAsString(PaymentService paymentService,
                                                   PaymentProduct paymentProduct,
                                                   String paymentId,
                                                   RequestHeaders requestHeaders,
                                                   RequestParams requestParams);

    /**
     * @deprecated use the version with enum PaymentService/Product parameters
     */
    @Deprecated
    default Response<ScaStatusResponse> getPaymentInitiationScaStatus(String paymentService,
                                                                      String paymentProduct,
                                                                      String paymentId,
                                                                      String authorisationId,
                                                                      RequestHeaders requestHeaders,
                                                                      RequestParams requestParams) {
        return getPaymentInitiationScaStatus(PaymentService.fromValue(paymentService),
            PaymentProduct.fromValue(paymentProduct),
            paymentId,
            authorisationId,
            requestHeaders,
            requestParams);
    }

    Response<ScaStatusResponse> getPaymentInitiationScaStatus(PaymentService paymentService,
                                                              PaymentProduct paymentProduct,
                                                              String paymentId,
                                                              String authorisationId,
                                                              RequestHeaders requestHeaders,
                                                              RequestParams requestParams);

    /**
     * @deprecated use the version with enum PaymentService/Product parameters
     */
    @Deprecated
    default Response<PaymentInitiationStatusResponse200Json> getPaymentInitiationStatus(String paymentService,
                                                                                        String paymentProduct,
                                                                                        String paymentId,
                                                                                        RequestHeaders requestHeaders,
                                                                                        RequestParams requestParams) {
        return getPaymentInitiationStatus(PaymentService.fromValue(paymentService),
            PaymentProduct.fromValue(paymentProduct),
            paymentId,
            requestHeaders,
            requestParams);
    }

    Response<PaymentInitiationStatusResponse200Json> getPaymentInitiationStatus(PaymentService paymentService,
                                                                                PaymentProduct paymentProduct,
                                                                                String paymentId,
                                                                                RequestHeaders requestHeaders,
                                                                                RequestParams requestParams);

    /**
     * @deprecated use the version with enum PaymentService/Product parameters
     */
    @Deprecated
    default Response<String> getPaymentInitiationStatusAsString(String paymentService,
                                                                String paymentProduct,
                                                                String paymentId,
                                                                RequestHeaders requestHeaders,
                                                                RequestParams requestParams) {
        return getPaymentInitiationStatusAsString(PaymentService.fromValue(paymentService),
            PaymentProduct.fromValue(paymentProduct),
            paymentId,
            requestHeaders,
            requestParams);
    }


    Response<String> getPaymentInitiationStatusAsString(PaymentService paymentService,
                                                        PaymentProduct paymentProduct,
                                                        String paymentId,
                                                        RequestHeaders requestHeaders,
                                                        RequestParams requestParams);
    /**
     * @deprecated use the version with enum PaymentService/Product parameters
     */
    @Deprecated
    default Response<Authorisations> getPaymentInitiationAuthorisation(String paymentService,
                                                                       String paymentProduct,
                                                                       String paymentId,
                                                                       RequestHeaders requestHeaders,
                                                                       RequestParams requestParams) {
        return getPaymentInitiationAuthorisation(PaymentService.fromValue(paymentService),
            PaymentProduct.fromValue(paymentProduct),
            paymentId,
            requestHeaders,
            requestParams);
    }

    Response<Authorisations> getPaymentInitiationAuthorisation(PaymentService paymentService,
                                                               PaymentProduct paymentProduct,
                                                               String paymentId,
                                                               RequestHeaders requestHeaders,
                                                               RequestParams requestParams);

    /**
     * @deprecated use the version with enum PaymentService/Product parameters
     */
    @Deprecated
    default Response<StartScaprocessResponse> startPaymentAuthorisation(String paymentService,
                                                                        String paymentProduct,
                                                                        String paymentId,
                                                                        RequestHeaders requestHeaders,
                                                                        RequestParams requestParams) {
        return startPaymentAuthorisation(PaymentService.fromValue(paymentService),
            PaymentProduct.fromValue(paymentProduct),
            paymentId,
            requestHeaders,
            requestParams);
    }

    Response<StartScaprocessResponse> startPaymentAuthorisation(PaymentService paymentService,
                                                                PaymentProduct paymentProduct,
                                                                String paymentId,
                                                                RequestHeaders requestHeaders,
                                                                RequestParams requestParams);

    /**
     * @deprecated use the version with enum PaymentService/Product parameters
     */
    @Deprecated
    default Response<StartScaprocessResponse> startPaymentAuthorisation(String paymentService,
                                                                        String paymentProduct,
                                                                        String paymentId,
                                                                        RequestHeaders requestHeaders,
                                                                        RequestParams requestParams,
                                                                        UpdatePsuAuthentication body) {

        return startPaymentAuthorisation(PaymentService.fromValue(paymentService),
            PaymentProduct.fromValue(paymentProduct),
            paymentId,
            requestHeaders,
            requestParams,
            body);
    }

    Response<StartScaprocessResponse> startPaymentAuthorisation(PaymentService paymentService,
                                                                PaymentProduct paymentProduct,
                                                                String paymentId,
                                                                RequestHeaders requestHeaders,
                                                                RequestParams requestParams,
                                                                UpdatePsuAuthentication body);

    /**
     * @deprecated use the version with enum PaymentService/Product parameters
     */
    @Deprecated
    default Response<SelectPsuAuthenticationMethodResponse> updatePaymentPsuData(String paymentService,
                                                                                 String paymentProduct,
                                                                                 String paymentId,
                                                                                 String authorisationId,
                                                                                 RequestHeaders requestHeaders,
                                                                                 RequestParams requestParams,
                                                                                 SelectPsuAuthenticationMethod body) {
        return updatePaymentPsuData(PaymentService.fromValue(paymentService),
            PaymentProduct.fromValue(paymentProduct),
            paymentId,
            authorisationId,
            requestHeaders,
            requestParams,
            body);
    }

    Response<SelectPsuAuthenticationMethodResponse> updatePaymentPsuData(PaymentService paymentService,
                                                                         PaymentProduct paymentProduct,
                                                                         String paymentId,
                                                                         String authorisationId,
                                                                         RequestHeaders requestHeaders,
                                                                         RequestParams requestParams,
                                                                         SelectPsuAuthenticationMethod body);

    /**
     * @deprecated use the version with enum PaymentService/Product parameters
     */
    @Deprecated
    default Response<ScaStatusResponse> updatePaymentPsuData(String paymentService,
                                                             String paymentProduct,
                                                             String paymentId,
                                                             String authorisationId,
                                                             RequestHeaders requestHeaders,
                                                             RequestParams requestParams,
                                                             TransactionAuthorisation body) {
        return updatePaymentPsuData(PaymentService.fromValue(paymentService),
            PaymentProduct.fromValue(paymentProduct),
            paymentId,
            authorisationId,
            requestHeaders,
            requestParams,
            body);
    }

    Response<ScaStatusResponse> updatePaymentPsuData(PaymentService paymentService,
                                                     PaymentProduct paymentProduct,
                                                     String paymentId,
                                                     String authorisationId,
                                                     RequestHeaders requestHeaders,
                                                     RequestParams requestParams,
                                                     TransactionAuthorisation body);

    /**
     * @deprecated use the version with enum PaymentService/Product parameters
     */
    @Deprecated
    default Response<UpdatePsuAuthenticationResponse> updatePaymentPsuData(String paymentService,
                                                                   String paymentProduct,
                                                                   String paymentId,
                                                                   String authorisationId,
                                                                   RequestHeaders requestHeaders,
                                                                   RequestParams requestParams,
                                                                   UpdatePsuAuthentication body) {
        return updatePaymentPsuData(PaymentService.fromValue(paymentService),
            PaymentProduct.fromValue(paymentProduct),
            paymentId,
            authorisationId,
            requestHeaders,
            requestParams,
            body);
    }

    Response<UpdatePsuAuthenticationResponse> updatePaymentPsuData(PaymentService paymentService,
                                                                   PaymentProduct paymentProduct,
                                                                   String paymentId,
                                                                   String authorisationId,
                                                                   RequestHeaders requestHeaders,
                                                                   RequestParams requestParams,
                                                                   UpdatePsuAuthentication body);
}
