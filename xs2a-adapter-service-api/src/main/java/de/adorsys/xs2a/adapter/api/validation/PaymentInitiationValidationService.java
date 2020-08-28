package de.adorsys.xs2a.adapter.api.validation;

import de.adorsys.xs2a.adapter.api.RequestHeaders;
import de.adorsys.xs2a.adapter.api.RequestParams;
import de.adorsys.xs2a.adapter.api.model.*;

import java.util.Collections;
import java.util.List;

public interface PaymentInitiationValidationService {
    /**
     * @deprecated use the version with enum PaymentService/Product parameters
     */
    @Deprecated
    default List<ValidationError> validateInitiatePayment(String paymentService,
                                                          String paymentProduct,
                                                          RequestHeaders requestHeaders,
                                                          RequestParams requestParams,
                                                          Object body) {
        return validateInitiatePayment(PaymentService.fromValue(paymentService),
            PaymentProduct.fromValue(paymentProduct),
            requestHeaders,
            requestParams,
            body);
    }

    default List<ValidationError> validateInitiatePayment(PaymentService paymentService,
                                                          PaymentProduct paymentProduct,
                                                          RequestHeaders requestHeaders,
                                                          RequestParams requestParams,
                                                          Object body) {
        return Collections.emptyList();
    }

    /**
     * @deprecated use the version with enum PaymentProduct parameter
     */
    @Deprecated
    default List<ValidationError> validateGetSinglePaymentInformation(String paymentProduct,
                                                                      String paymentId,
                                                                      RequestHeaders requestHeaders,
                                                                      RequestParams requestParams) {
        return validateGetSinglePaymentInformation(PaymentProduct.fromValue(paymentProduct),
            paymentId,
            requestHeaders,
            requestParams);
    }

    default List<ValidationError> validateGetSinglePaymentInformation(PaymentProduct paymentProduct,
                                                                      String paymentId,
                                                                      RequestHeaders requestHeaders,
                                                                      RequestParams requestParams) {
        return Collections.emptyList();
    }

    /**
     * @deprecated use the version with enum PaymentProduct parameter
     */
    @Deprecated
    default List<ValidationError> validateGetPeriodicPaymentInformation(String paymentProduct,
                                                                        String paymentId,
                                                                        RequestHeaders requestHeaders,
                                                                        RequestParams requestParams) {
        return validateGetPeriodicPaymentInformation(PaymentProduct.fromValue(paymentProduct),
            paymentId,
            requestHeaders,
            requestParams);
    }

    default List<ValidationError> validateGetPeriodicPaymentInformation(PaymentProduct paymentProduct,
                                                                        String paymentId,
                                                                        RequestHeaders requestHeaders,
                                                                        RequestParams requestParams) {
        return Collections.emptyList();
    }

    /**
     * @deprecated use the version with enum PaymentProduct parameter
     */
    @Deprecated
    default List<ValidationError> validateGetPeriodicPain001PaymentInformation(String paymentProduct,
                                                                               String paymentId,
                                                                               RequestHeaders requestHeaders,
                                                                               RequestParams requestParams) {
        return validateGetPeriodicPain001PaymentInformation(PaymentProduct.fromValue(paymentProduct),
            paymentId,
            requestHeaders,
            requestParams);
    }

    default List<ValidationError> validateGetPeriodicPain001PaymentInformation(PaymentProduct paymentProduct,
                                                                               String paymentId,
                                                                               RequestHeaders requestHeaders,
                                                                               RequestParams requestParams) {
        return Collections.emptyList();
    }

    /**
     * @deprecated use the version with enum PaymentService/Product parameters
     */
    @Deprecated
    default List<ValidationError> validateGetPaymentInitiationScaStatus(String paymentService,
                                                                        String paymentProduct,
                                                                        String paymentId,
                                                                        String authorisationId,
                                                                        RequestHeaders requestHeaders,
                                                                        RequestParams requestParams) {
        return validateGetPaymentInitiationScaStatus(PaymentService.fromValue(paymentService),
            PaymentProduct.fromValue(paymentProduct),
            paymentId,
            authorisationId,
            requestHeaders,
            requestParams);
    }

    default List<ValidationError> validateGetPaymentInitiationScaStatus(PaymentService paymentService,
                                                                        PaymentProduct paymentProduct,
                                                                        String paymentId,
                                                                        String authorisationId,
                                                                        RequestHeaders requestHeaders,
                                                                        RequestParams requestParams) {
        return Collections.emptyList();
    }

    /**
     * @deprecated use the version with enum PaymentService/Product parameters
     */
    @Deprecated
    default List<ValidationError> validateGetPaymentInitiationStatus(String paymentService,
                                                                     String paymentProduct,
                                                                     String paymentId,
                                                                     RequestHeaders requestHeaders,
                                                                     RequestParams requestParams) {
        return validateGetPaymentInitiationStatus(PaymentService.fromValue(paymentService),
            PaymentProduct.fromValue(paymentProduct),
            paymentId,
            requestHeaders,
            requestParams);
    }

    default List<ValidationError> validateGetPaymentInitiationStatus(PaymentService paymentService,
                                                                     PaymentProduct paymentProduct,
                                                                     String paymentId,
                                                                     RequestHeaders requestHeaders,
                                                                     RequestParams requestParams) {
        return Collections.emptyList();
    }


    /**
     * @deprecated use the version with enum PaymentService/Product parameters
     */
    @Deprecated
    default List<ValidationError> validateGetPaymentInitiationStatusAsString(String paymentService,
                                                                             String paymentProduct,
                                                                             String paymentId,
                                                                             RequestHeaders requestHeaders,
                                                                             RequestParams requestParams) {
        return validateGetPaymentInitiationStatusAsString(PaymentService.fromValue(paymentService),
            PaymentProduct.fromValue(paymentProduct),
            paymentId,
            requestHeaders,
            requestParams);
    }

    default List<ValidationError> validateGetPaymentInitiationStatusAsString(PaymentService paymentService,
                                                                             PaymentProduct paymentProduct,
                                                                             String paymentId,
                                                                             RequestHeaders requestHeaders,
                                                                             RequestParams requestParams) {
        return Collections.emptyList();
    }

    /**
     * @deprecated use the version with enum PaymentService/Product parameters
     */
    @Deprecated
    default List<ValidationError> validateGetPaymentInitiationAuthorisation(String paymentService,
                                                                            String paymentProduct,
                                                                            String paymentId,
                                                                            RequestHeaders requestHeaders,
                                                                            RequestParams requestParams) {
        return validateGetPaymentInitiationAuthorisation(PaymentService.fromValue(paymentService),
            PaymentProduct.fromValue(paymentProduct),
            paymentId,
            requestHeaders,
            requestParams);
    }

    default List<ValidationError> validateGetPaymentInitiationAuthorisation(PaymentService paymentService,
                                                                            PaymentProduct paymentProduct,
                                                                            String paymentId,
                                                                            RequestHeaders requestHeaders,
                                                                            RequestParams requestParams) {
        return Collections.emptyList();
    }

    /**
     * @deprecated use the version with enum PaymentService/Product parameters
     */
    @Deprecated
    default List<ValidationError> validateStartPaymentAuthorisation(String paymentService,
                                                                    String paymentProduct,
                                                                    String paymentId,
                                                                    RequestHeaders requestHeaders,
                                                                    RequestParams requestParams) {
        return validateStartPaymentAuthorisation(PaymentService.fromValue(paymentService),
            PaymentProduct.fromValue(paymentProduct),
            paymentId,
            requestHeaders,
            requestParams);
    }

    default List<ValidationError> validateStartPaymentAuthorisation(PaymentService paymentService,
                                                                    PaymentProduct paymentProduct,
                                                                    String paymentId,
                                                                    RequestHeaders requestHeaders,
                                                                    RequestParams requestParams) {
        return Collections.emptyList();
    }

    /**
     * @deprecated use the version with enum PaymentService/Product parameters
     */
    @Deprecated
    default List<ValidationError> validateStartPaymentAuthorisation(String paymentService,
                                                                    String paymentProduct,
                                                                    String paymentId,
                                                                    RequestHeaders requestHeaders,
                                                                    RequestParams requestParams,
                                                                    UpdatePsuAuthentication body) {
        return validateStartPaymentAuthorisation(PaymentService.fromValue(paymentService),
            PaymentProduct.fromValue(paymentProduct),
            paymentId,
            requestHeaders,
            requestParams,
            body);
    }

    default List<ValidationError> validateStartPaymentAuthorisation(PaymentService paymentService,
                                                                    PaymentProduct paymentProduct,
                                                                    String paymentId,
                                                                    RequestHeaders requestHeaders,
                                                                    RequestParams requestParams,
                                                                    UpdatePsuAuthentication body) {
        return Collections.emptyList();
    }

    /**
     * @deprecated use the version with enum PaymentService/Product parameters
     */
    @Deprecated
    default List<ValidationError> validateUpdatePaymentPsuData(String paymentService,
                                                               String paymentProduct,
                                                               String paymentId,
                                                               String authorisationId,
                                                               RequestHeaders requestHeaders,
                                                               RequestParams requestParams,
                                                               SelectPsuAuthenticationMethod body) {
        return validateUpdatePaymentPsuData(PaymentService.fromValue(paymentService),
            PaymentProduct.fromValue(paymentProduct),
            paymentId,
            authorisationId,
            requestHeaders,
            requestParams,
            body);
    }

    default List<ValidationError> validateUpdatePaymentPsuData(PaymentService paymentService,
                                                               PaymentProduct paymentProduct,
                                                               String paymentId,
                                                               String authorisationId,
                                                               RequestHeaders requestHeaders,
                                                               RequestParams requestParams,
                                                               SelectPsuAuthenticationMethod body) {
        return Collections.emptyList();
    }

    /**
     * @deprecated use the version with enum PaymentService/Product parameters
     */
    @Deprecated
    default List<ValidationError> validateUpdatePaymentPsuData(String paymentService,
                                                               String paymentProduct,
                                                               String paymentId,
                                                               String authorisationId,
                                                               RequestHeaders requestHeaders,
                                                               RequestParams requestParams,
                                                               TransactionAuthorisation body) {
        return validateUpdatePaymentPsuData(PaymentService.fromValue(paymentService),
            PaymentProduct.fromValue(paymentProduct),
            paymentId,
            authorisationId,
            requestHeaders,
            requestParams,
            body);
    }

    default List<ValidationError> validateUpdatePaymentPsuData(PaymentService paymentService,
                                                               PaymentProduct paymentProduct,
                                                               String paymentId,
                                                               String authorisationId,
                                                               RequestHeaders requestHeaders,
                                                               RequestParams requestParams,
                                                               TransactionAuthorisation body) {
        return Collections.emptyList();
    }

    /**
     * @deprecated use the version with enum PaymentService/Product parameters
     */
    @Deprecated
    default List<ValidationError> validateUpdatePaymentPsuData(String paymentService,
                                                               String paymentProduct,
                                                               String paymentId,
                                                               String authorisationId,
                                                               RequestHeaders requestHeaders,
                                                               RequestParams requestParams,
                                                               UpdatePsuAuthentication body) {
        return validateUpdatePaymentPsuData(PaymentService.fromValue(paymentService),
            PaymentProduct.fromValue(paymentProduct),
            paymentId,
            authorisationId,
            requestHeaders,
            requestParams,
            body);
    }

    default List<ValidationError> validateUpdatePaymentPsuData(PaymentService paymentService,
                                                               PaymentProduct paymentProduct,
                                                               String paymentId,
                                                               String authorisationId,
                                                               RequestHeaders requestHeaders,
                                                               RequestParams requestParams,
                                                               UpdatePsuAuthentication body) {
        return Collections.emptyList();
    }
}
