package de.adorsys.xs2a.adapter.validation;

import de.adorsys.xs2a.adapter.service.RequestHeaders;
import de.adorsys.xs2a.adapter.service.RequestParams;
import de.adorsys.xs2a.adapter.service.model.SelectPsuAuthenticationMethod;
import de.adorsys.xs2a.adapter.service.model.TransactionAuthorisation;
import de.adorsys.xs2a.adapter.service.model.UpdatePsuAuthentication;

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
