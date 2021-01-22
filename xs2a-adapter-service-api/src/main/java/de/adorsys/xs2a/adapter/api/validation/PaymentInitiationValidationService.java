package de.adorsys.xs2a.adapter.api.validation;

import de.adorsys.xs2a.adapter.api.RequestHeaders;
import de.adorsys.xs2a.adapter.api.RequestParams;
import de.adorsys.xs2a.adapter.api.model.*;

import java.util.Collections;
import java.util.List;

public interface PaymentInitiationValidationService {

    default List<ValidationError> validateInitiatePayment(PaymentService paymentService,
                                                          PaymentProduct paymentProduct,
                                                          RequestHeaders requestHeaders,
                                                          RequestParams requestParams,
                                                          Object body) {
        return Collections.emptyList();
    }

    default List<ValidationError> validateGetSinglePaymentInformation(PaymentProduct paymentProduct,
                                                                      String paymentId,
                                                                      RequestHeaders requestHeaders,
                                                                      RequestParams requestParams) {
        return Collections.emptyList();
    }

    default List<ValidationError> validateGetPeriodicPaymentInformation(PaymentProduct paymentProduct,
                                                                        String paymentId,
                                                                        RequestHeaders requestHeaders,
                                                                        RequestParams requestParams) {
        return Collections.emptyList();
    }

    default List<ValidationError> validateGetPeriodicPain001PaymentInformation(PaymentProduct paymentProduct,
                                                                               String paymentId,
                                                                               RequestHeaders requestHeaders,
                                                                               RequestParams requestParams) {
        return Collections.emptyList();
    }

    default List<ValidationError> validateGetPaymentInitiationScaStatus(PaymentService paymentService,
                                                                        PaymentProduct paymentProduct,
                                                                        String paymentId,
                                                                        String authorisationId,
                                                                        RequestHeaders requestHeaders,
                                                                        RequestParams requestParams) {
        return Collections.emptyList();
    }

    default List<ValidationError> validateGetPaymentInitiationStatus(PaymentService paymentService,
                                                                     PaymentProduct paymentProduct,
                                                                     String paymentId,
                                                                     RequestHeaders requestHeaders,
                                                                     RequestParams requestParams) {
        return Collections.emptyList();
    }

    default List<ValidationError> validateGetPaymentInitiationStatusAsString(PaymentService paymentService,
                                                                             PaymentProduct paymentProduct,
                                                                             String paymentId,
                                                                             RequestHeaders requestHeaders,
                                                                             RequestParams requestParams) {
        return Collections.emptyList();
    }

    default List<ValidationError> validateGetPaymentInitiationAuthorisation(PaymentService paymentService,
                                                                            PaymentProduct paymentProduct,
                                                                            String paymentId,
                                                                            RequestHeaders requestHeaders,
                                                                            RequestParams requestParams) {
        return Collections.emptyList();
    }

    default List<ValidationError> validateStartPaymentAuthorisation(PaymentService paymentService,
                                                                    PaymentProduct paymentProduct,
                                                                    String paymentId,
                                                                    RequestHeaders requestHeaders,
                                                                    RequestParams requestParams) {
        return Collections.emptyList();
    }

    default List<ValidationError> validateStartPaymentAuthorisation(PaymentService paymentService,
                                                                    PaymentProduct paymentProduct,
                                                                    String paymentId,
                                                                    RequestHeaders requestHeaders,
                                                                    RequestParams requestParams,
                                                                    UpdatePsuAuthentication body) {
        return Collections.emptyList();
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

    default List<ValidationError> validateUpdatePaymentPsuData(PaymentService paymentService,
                                                               PaymentProduct paymentProduct,
                                                               String paymentId,
                                                               String authorisationId,
                                                               RequestHeaders requestHeaders,
                                                               RequestParams requestParams,
                                                               TransactionAuthorisation body) {
        return Collections.emptyList();
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
