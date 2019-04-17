/*
 * Copyright 2018-2018 adorsys GmbH & Co KG
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.adorsys.xs2a.gateway.adapter;

import de.adorsys.xs2a.gateway.service.*;

public interface PaymentInitiationServiceWrapper extends PaymentInitiationService {
    @Override
    default PaymentInitiationRequestResponse initiateSinglePayment(String paymentProduct, Object body, Headers headers) {
        throw new UnsupportedOperationException();
    }

    @Override
    default SinglePaymentInitiationInformationWithStatusResponse getSinglePaymentInformation(String paymentProduct, String paymentId, Headers headers) {
        throw new UnsupportedOperationException();
    }

    @Override
    default PaymentInitiationScaStatusResponse getPaymentInitiationScaStatus(String paymentService, String paymentProduct, String paymentId, String authorisationId, Headers headers) {
        throw new UnsupportedOperationException();
    }

    @Override
    default PaymentInitiationStatus getSinglePaymentInitiationStatus(String paymentProduct, String paymentId, Headers headers) {
        throw new UnsupportedOperationException();
    }

    @Override
    default PaymentInitiationAuthorisationResponse getPaymentInitiationAuthorisation(String paymentService, String paymentProduct, String paymentId, Headers headers) {
        throw new UnsupportedOperationException();
    }
}
