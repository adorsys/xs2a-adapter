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
