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

import de.adorsys.xs2a.gateway.http.StringUri;
import de.adorsys.xs2a.gateway.service.*;

import java.util.Map;

public abstract class BasePaymentInitiationService extends AbstractService implements PaymentInitiationService {

    private static final String PAYMENTS = "payments";
    private static final String SEPA_CREDIT_TRANSFERS = "sepa-credit-transfers";

    @Override
    public GeneralResponse<PaymentInitiationRequestResponse> initiateSinglePayment(String paymentProduct, Object body, RequestHeaders requestHeaders) {
        requireSepaCreditTransfer(paymentProduct);

        Map<String, String> headersMap = populatePostHeaders(requestHeaders.toMap());
        String bodyString = jsonMapper.writeValueAsString(jsonMapper.convertValue(body, SinglePaymentInitiationBody.class));

        return httpClient.post(
                getSingleSepaCreditTransferUri(),
                bodyString,
                headersMap,
                responseHandler(PaymentInitiationRequestResponse.class)
        );
    }

    @Override
    public GeneralResponse<SinglePaymentInitiationInformationWithStatusResponse> getSinglePaymentInformation(String paymentProduct,
                                                                                            String paymentId,
                                                                                            RequestHeaders requestHeaders) {
        requireSepaCreditTransfer(paymentProduct);

        String uri = StringUri.fromElements(getSingleSepaCreditTransferUri(), paymentId);

        Map<String, String> headersMap = populateGetHeaders(requestHeaders.toMap());
        return httpClient.get(uri, headersMap,
                              responseHandler(SinglePaymentInitiationInformationWithStatusResponse.class));
    }

    @Override
    public GeneralResponse<PaymentInitiationScaStatusResponse> getPaymentInitiationScaStatus(String paymentService, String paymentProduct, String paymentId, String authorisationId, RequestHeaders requestHeaders) {
        throw new UnsupportedOperationException();
    }

    @Override
    public GeneralResponse<PaymentInitiationStatus> getSinglePaymentInitiationStatus(String paymentProduct, String paymentId, RequestHeaders requestHeaders) {
        requireSepaCreditTransfer(paymentProduct);
        String uri = StringUri.fromElements(getSingleSepaCreditTransferUri(), paymentId, STATUS);
        Map<String, String> headersMap = populateGetHeaders(requestHeaders.toMap());

        return httpClient.get(uri, headersMap, responseHandler(PaymentInitiationStatus.class));

    }

    private void requireSepaCreditTransfer(String paymentProduct) {
        if (!paymentProduct.equalsIgnoreCase(SEPA_CREDIT_TRANSFERS)) {
            throw new UnsupportedOperationException(paymentProduct);
        }
    }

    @Override
    public GeneralResponse<PaymentInitiationAuthorisationResponse> getPaymentInitiationAuthorisation(String paymentService, String paymentProduct, String paymentId, RequestHeaders requestHeaders) {
        throw new UnsupportedOperationException();
    }

    protected String getSingleSepaCreditTransferUri() {
        return StringUri.fromElements(getBaseUri(), PAYMENTS, SEPA_CREDIT_TRANSFERS);
    }

    protected abstract String getBaseUri();
}
