/*
 * Copyright 2018-2022 adorsys GmbH & Co KG
 *
 * This program is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or (at
 * your option) any later version. This program is distributed in the hope that
 * it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see https://www.gnu.org/licenses/.
 *
 * This project is also available under a separate commercial license. You can
 * contact us at psd2@adorsys.com.
 */

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
