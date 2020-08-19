package de.adorsys.xs2a.adapter.api.validation;

import de.adorsys.xs2a.adapter.api.RequestHeaders;
import de.adorsys.xs2a.adapter.api.RequestParams;
import de.adorsys.xs2a.adapter.api.model.SelectPsuAuthenticationMethod;
import de.adorsys.xs2a.adapter.api.model.TransactionAuthorisation;
import de.adorsys.xs2a.adapter.api.model.UpdatePsuAuthentication;

import java.util.Collections;
import java.util.List;

public interface PaymentInitiationValidationService {
    default List<ValidationError> validateInitiatePayment(String paymentService,
                                                          String paymentProduct,
                                                          RequestHeaders requestHeaders,
                                                          RequestParams requestParams,
                                                          Object body) {
        return Collections.emptyList();
    }

    default List<ValidationError> validateGetSinglePaymentInformation(String paymentProduct,
                                                                      String paymentId,
                                                                      RequestHeaders requestHeaders,
                                                                      RequestParams requestParams) {
        return Collections.emptyList();
    }

    default List<ValidationError> validateGetPeriodicPaymentInformation(String paymentProduct,
                                                                        String paymentId,
                                                                        RequestHeaders requestHeaders,
                                                                        RequestParams requestParams) {
        return Collections.emptyList();
    }

    default List<ValidationError> validateGetPeriodicPain001PaymentInformation(String paymentProduct,
                                                                               String paymentId,
                                                                               RequestHeaders requestHeaders,
                                                                               RequestParams requestParams) {
        return Collections.emptyList();
    }

    default List<ValidationError> validateGetPaymentInitiationScaStatus(String paymentService,
                                                                        String paymentProduct,
                                                                        String paymentId,
                                                                        String authorisationId,
                                                                        RequestHeaders requestHeaders,
                                                                        RequestParams requestParams) {
        return Collections.emptyList();
    }

    default List<ValidationError> validateGetPaymentInitiationStatus(String paymentService,
                                                                     String paymentProduct,
                                                                     String paymentId,
                                                                     RequestHeaders requestHeaders,
                                                                     RequestParams requestParams) {
        return Collections.emptyList();
    }

    default List<ValidationError> validateGetSinglePaymentInitiationStatusAsString(String paymentProduct,
                                                                                   String paymentId,
                                                                                   RequestHeaders requestHeaders,
                                                                                   RequestParams requestParams) {
        return Collections.emptyList();
    }

    default List<ValidationError> validateGetPaymentInitiationAuthorisation(String paymentService,
                                                                            String paymentProduct,
                                                                            String paymentId,
                                                                            RequestHeaders requestHeaders,
                                                                            RequestParams requestParams) {
        return Collections.emptyList();
    }

    default List<ValidationError> validateStartPaymentAuthorisation(String paymentService,
                                                                    String paymentProduct,
                                                                    String paymentId,
                                                                    RequestHeaders requestHeaders,
                                                                    RequestParams requestParams) {
        return Collections.emptyList();
    }

    default List<ValidationError> validateStartPaymentAuthorisation(String paymentService,
                                                                    String paymentProduct,
                                                                    String paymentId,
                                                                    RequestHeaders requestHeaders,
                                                                    RequestParams requestParams,
                                                                    UpdatePsuAuthentication updatePsuAuthentication) {
        return Collections.emptyList();
    }

    default List<ValidationError> validateUpdatePaymentPsuData(String paymentService,
                                                               String paymentProduct,
                                                               String paymentId,
                                                               String authorisationId,
                                                               RequestHeaders requestHeaders,
                                                               RequestParams requestParams,
                                                               SelectPsuAuthenticationMethod selectPsuAuthenticationMethod) {
        return Collections.emptyList();
    }

    default List<ValidationError> validateUpdatePaymentPsuData(String paymentService,
                                                               String paymentProduct,
                                                               String paymentId,
                                                               String authorisationId,
                                                               RequestHeaders requestHeaders,
                                                               RequestParams requestParams,
                                                               TransactionAuthorisation transactionAuthorisation) {
        return Collections.emptyList();
    }

    default List<ValidationError> validateUpdatePaymentPsuData(String paymentService,
                                                               String paymentProduct,
                                                               String paymentId,
                                                               String authorisationId,
                                                               RequestHeaders requestHeaders,
                                                               RequestParams requestParams,
                                                               UpdatePsuAuthentication updatePsuAuthentication) {
        return Collections.emptyList();
    }
}
