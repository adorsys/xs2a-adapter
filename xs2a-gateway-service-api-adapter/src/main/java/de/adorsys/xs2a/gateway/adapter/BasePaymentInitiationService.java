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
import de.adorsys.xs2a.gateway.service.model.UpdatePsuAuthentication;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import java.util.Map;

public abstract class BasePaymentInitiationService extends AbstractService implements PaymentInitiationService {

    private static final String PAYMENTS = "payments";

    @Override
    public GeneralResponse<PaymentInitiationRequestResponse> initiateSinglePayment(String paymentProduct, Object body, RequestHeaders requestHeaders) {
        return initiateSinglePayment(StandardPaymentProduct.fromSlug(paymentProduct), body, requestHeaders);
    }

    private GeneralResponse<PaymentInitiationRequestResponse> initiateSinglePayment(StandardPaymentProduct paymentProduct,
                                                                                    Object body,
                                                                                    RequestHeaders requestHeaders) {

        Map<String, String> headersMap = populatePostHeaders(requestHeaders.toMap());
        String bodyString;
        switch (paymentProduct.getMediaType()) {
            case MediaType.APPLICATION_JSON:
                bodyString = jsonMapper.writeValueAsString(jsonMapper.convertValue(body, SinglePaymentInitiationBody.class));
                break;
            case MediaType.APPLICATION_XML:
                bodyString = (String) body;
                headersMap.put(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_XML);
                break;
            default:
                throw new IllegalArgumentException("Unsupported payment product media type");
        }

        return httpClient.post(
                StringUri.fromElements(getBaseUri(), PAYMENTS, paymentProduct.getSlug()),
                bodyString,
                headersMap,
                responseHandler(PaymentInitiationRequestResponse.class)
        );
    }

    @Override
    public GeneralResponse<SinglePaymentInitiationInformationWithStatusResponse> getSinglePaymentInformation(String paymentProduct,
                                                                                            String paymentId,
                                                                                            RequestHeaders requestHeaders) {
        return getSinglePaymentInformation(StandardPaymentProduct.fromSlug(paymentProduct), paymentId, requestHeaders);
    }

    private GeneralResponse<SinglePaymentInitiationInformationWithStatusResponse> getSinglePaymentInformation(StandardPaymentProduct paymentProduct,
                                                                                                              String paymentId,
                                                                                                              RequestHeaders requestHeaders) {
        String uri = StringUri.fromElements(getBaseUri(), PAYMENTS, paymentProduct.getSlug(), paymentId);

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
        return getSinglePaymentInitiationStatus(StandardPaymentProduct.fromSlug(paymentProduct), paymentId, requestHeaders);
    }

    private GeneralResponse<PaymentInitiationStatus> getSinglePaymentInitiationStatus(StandardPaymentProduct paymentProduct,
                                                                                      String paymentId,
                                                                                      RequestHeaders requestHeaders) {
        String uri = StringUri.fromElements(getBaseUri(), PAYMENTS, paymentProduct.getSlug(), paymentId, STATUS);
        Map<String, String> headersMap = populateGetHeaders(requestHeaders.toMap());

        return httpClient.get(uri, headersMap, responseHandler(PaymentInitiationStatus.class));
    }

    @Override
    public GeneralResponse<PaymentInitiationAuthorisationResponse> getPaymentInitiationAuthorisation(String paymentService, String paymentProduct, String paymentId, RequestHeaders requestHeaders) {
        throw new UnsupportedOperationException();
    }

    @Override
    public GeneralResponse<StartScaProcessResponse> startSinglePaymentAuthorisation(String paymentProduct,
                                                                                    String paymentId,
                                                                                    RequestHeaders requestHeaders,
                                                                                    UpdatePsuAuthentication updatePsuAuthentication) {
        return startSinglePaymentAuthorisation(StandardPaymentProduct.fromSlug(paymentProduct), paymentId,
                requestHeaders, updatePsuAuthentication);
    }

    private GeneralResponse<StartScaProcessResponse> startSinglePaymentAuthorisation(PaymentProduct paymentProduct,
                                                                             String paymentId,
                                                                             RequestHeaders requestHeaders,
                                                                             UpdatePsuAuthentication updatePsuAuthentication) {
        String uri = StringUri.fromElements(getBaseUri(), PAYMENTS, paymentProduct.getSlug(), paymentId, AUTHORISATIONS);
        Map<String, String> headersMap = populateGetHeaders(requestHeaders.toMap());
        String body = jsonMapper.writeValueAsString(updatePsuAuthentication);
        return httpClient.post(uri, body, headersMap, responseHandler(StartScaProcessResponse.class));
    }

    protected abstract String getBaseUri();
}
