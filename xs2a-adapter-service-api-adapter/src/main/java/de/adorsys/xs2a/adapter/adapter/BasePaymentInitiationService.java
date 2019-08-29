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

package de.adorsys.xs2a.adapter.adapter;

import de.adorsys.xs2a.adapter.http.StringUri;
import de.adorsys.xs2a.adapter.service.*;
import de.adorsys.xs2a.adapter.service.model.*;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import java.util.Map;
import java.util.function.Function;

import static java.util.function.Function.identity;

public class BasePaymentInitiationService extends AbstractService implements PaymentInitiationService {

    private static final String V1 = "v1";
    private static final String PAYMENTS = "payments";
    private final String baseUri;

    public BasePaymentInitiationService(String baseUri) {
        this.baseUri = baseUri;
    }

    @Override
    public GeneralResponse<PaymentInitiationRequestResponse> initiateSinglePayment(String paymentProduct, RequestHeaders requestHeaders, Object body) {
        return initiateSinglePayment(StandardPaymentProduct.fromSlug(paymentProduct), body, requestHeaders, PaymentInitiationRequestResponse.class, identity());
    }

    protected <T> GeneralResponse<PaymentInitiationRequestResponse> initiateSinglePayment(StandardPaymentProduct paymentProduct,
                                                                                    Object body,
                                                                                    RequestHeaders requestHeaders,
                                                                                    Class<T> klass,
                                                                                    Function<T, PaymentInitiationRequestResponse> mapper) {

        Map<String, String> headersMap = populatePostHeaders(requestHeaders.toMap());
        String bodyString;
        switch (paymentProduct.getMediaType()) {
            case MediaType.APPLICATION_JSON:
                bodyString = jsonMapper.writeValueAsString(jsonMapper.convertValue(body, getSinglePaymentInitiationBodyClass()));
                break;
            case MediaType.APPLICATION_XML:
                bodyString = (String) body;
                headersMap.put(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_XML);
                break;
            default:
                throw new IllegalArgumentException("Unsupported payment product media type");
        }

        GeneralResponse<T> response = httpClient.post(StringUri.fromElements(baseUri, V1, PAYMENTS, paymentProduct.getSlug()), bodyString, headersMap, jsonResponseHandler(klass));
        PaymentInitiationRequestResponse paymentInitiationRequestResponse = mapper.apply(response.getResponseBody());
        return new GeneralResponse<>(response.getStatusCode(), paymentInitiationRequestResponse, response.getResponseHeaders());
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
        String uri = StringUri.fromElements(getSinglePaymentBaseUri(), paymentProduct.getSlug(), paymentId);

        Map<String, String> headersMap = populateGetHeaders(requestHeaders.toMap());
        return httpClient.get(uri, headersMap,
                jsonResponseHandler(SinglePaymentInitiationInformationWithStatusResponse.class));
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
        String uri = getSinglePaymentInitiationStatusUri(paymentProduct.getSlug(), paymentId);
        Map<String, String> headersMap = populateGetHeaders(requestHeaders.toMap());

        return httpClient.get(uri, headersMap, jsonResponseHandler(PaymentInitiationStatus.class));
    }

    @Override
    public GeneralResponse<String> getSinglePaymentInitiationStatusAsString(String paymentProduct, String paymentId, RequestHeaders requestHeaders) {
        String uri = getSinglePaymentInitiationStatusUri(paymentProduct, paymentId);
        Map<String, String> headersMap = populateGetHeaders(requestHeaders.toMap());

        return httpClient.get(uri, headersMap, stringResponseHandler());
    }

    private String getSinglePaymentInitiationStatusUri(String paymentProduct, String paymentId) {
        return StringUri.fromElements(getSinglePaymentBaseUri(), paymentProduct, paymentId, STATUS);
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
        return startSinglePaymentAuthorisation(StandardPaymentProduct.fromSlug(paymentProduct), paymentId, requestHeaders, updatePsuAuthentication, StartScaProcessResponse.class, identity());
    }

    protected <T> GeneralResponse<StartScaProcessResponse> startSinglePaymentAuthorisation(PaymentProduct paymentProduct,
                                                                                           String paymentId,
                                                                                           RequestHeaders requestHeaders,
                                                                                           UpdatePsuAuthentication updatePsuAuthentication,
                                                                                           Class<T> klass,
                                                                                           Function<T, StartScaProcessResponse> mapper) {
        String uri = StringUri.fromElements(getSinglePaymentBaseUri(), paymentProduct.getSlug(), paymentId, AUTHORISATIONS);
        Map<String, String> headersMap = populatePostHeaders(requestHeaders.toMap());
        String body = jsonMapper.writeValueAsString(updatePsuAuthentication);

        GeneralResponse<T> response = httpClient.post(uri, body, headersMap, jsonResponseHandler(klass));
        StartScaProcessResponse startScaProcessResponse = mapper.apply(response.getResponseBody());
        return new GeneralResponse<>(response.getStatusCode(), startScaProcessResponse, response.getResponseHeaders());
    }

    @Override
    public GeneralResponse<UpdatePsuAuthenticationResponse> updatePaymentPsuData(String paymentService, String paymentProduct, String paymentId, String authorisationId, RequestHeaders requestHeaders, UpdatePsuAuthentication updatePsuAuthentication) {
        return updatePaymentPsuData(paymentService, StandardPaymentProduct.fromSlug(paymentProduct), paymentId, authorisationId, requestHeaders, updatePsuAuthentication, UpdatePsuAuthenticationResponse.class, identity());
    }

    protected <T> GeneralResponse<UpdatePsuAuthenticationResponse> updatePaymentPsuData(String paymentService,
                                                                                        PaymentProduct paymentProduct,
                                                                                        String paymentId,
                                                                                        String authorisationId,
                                                                                        RequestHeaders requestHeaders,
                                                                                        UpdatePsuAuthentication updatePsuAuthentication,
                                                                                        Class<T> klass,
                                                                                        Function<T, UpdatePsuAuthenticationResponse> mapper) {
        String uri = StringUri.fromElements(getPaymentBaseUri(), paymentService, paymentProduct.getSlug(), paymentId, AUTHORISATIONS, authorisationId);
        Map<String, String> headersMap = populatePutHeaders(requestHeaders.toMap());
        String body = jsonMapper.writeValueAsString(updatePsuAuthentication);

        GeneralResponse<T> response = httpClient.put(uri, body, headersMap, jsonResponseHandler(klass));
        UpdatePsuAuthenticationResponse updatePsuAuthenticationResponse = mapper.apply(response.getResponseBody());
        return new GeneralResponse<>(response.getStatusCode(), updatePsuAuthenticationResponse, response.getResponseHeaders());
    }

    @Override
    public GeneralResponse<SelectPsuAuthenticationMethodResponse> updatePaymentPsuData(String paymentService, String paymentProduct, String paymentId, String authorisationId, RequestHeaders requestHeaders, SelectPsuAuthenticationMethod selectPsuAuthenticationMethod) {
        return updatePaymentPsuData(paymentService, StandardPaymentProduct.fromSlug(paymentProduct), paymentId, authorisationId, requestHeaders, selectPsuAuthenticationMethod, SelectPsuAuthenticationMethodResponse.class, identity());
    }

    protected <T> GeneralResponse<SelectPsuAuthenticationMethodResponse> updatePaymentPsuData(String paymentService,
                                                                                              PaymentProduct paymentProduct,
                                                                                              String paymentId,
                                                                                              String authorisationId,
                                                                                              RequestHeaders requestHeaders,
                                                                                              SelectPsuAuthenticationMethod selectPsuAuthenticationMethod,
                                                                                              Class<T> klass,
                                                                                              Function<T, SelectPsuAuthenticationMethodResponse> mapper) {
        String uri = StringUri.fromElements(getPaymentBaseUri(), paymentService, paymentProduct.getSlug(), paymentId, AUTHORISATIONS, authorisationId);
        Map<String, String> headersMap = populatePutHeaders(requestHeaders.toMap());
        String body = jsonMapper.writeValueAsString(selectPsuAuthenticationMethod);

        GeneralResponse<T> response = httpClient.put(uri, body, headersMap, jsonResponseHandler(klass));
        SelectPsuAuthenticationMethodResponse selectPsuAuthenticationMethodResponse = mapper.apply(response.getResponseBody());
        return new GeneralResponse<>(response.getStatusCode(), selectPsuAuthenticationMethodResponse, response.getResponseHeaders());
    }

    @Override
    public GeneralResponse<ScaStatusResponse> updatePaymentPsuData(String paymentService, String paymentProduct, String paymentId, String authorisationId, RequestHeaders requestHeaders, TransactionAuthorisation transactionAuthorisation) {
        return updatePaymentPsuData(paymentService, paymentProduct, paymentId, authorisationId, requestHeaders, transactionAuthorisation, ScaStatusResponse.class, identity());
    }

    protected <T> GeneralResponse<ScaStatusResponse> updatePaymentPsuData(String paymentService,
                                                                          String paymentProduct,
                                                                          String paymentId,
                                                                          String authorisationId,
                                                                          RequestHeaders requestHeaders,
                                                                          TransactionAuthorisation transactionAuthorisation,
                                                                          Class<T> klass,
                                                                          Function<T, ScaStatusResponse> mapper) {
        String uri = getUpdatePaymentPsuDataUri(paymentService, paymentProduct, paymentId, authorisationId);
        Map<String, String> headersMap = populatePutHeaders(requestHeaders.toMap());
        String body = jsonMapper.writeValueAsString(transactionAuthorisation);

        GeneralResponse<T> response = httpClient.put(uri, body, headersMap, jsonResponseHandler(klass));

        ScaStatusResponse scaStatusResponse = mapper.apply(response.getResponseBody());
        return new GeneralResponse<>(response.getStatusCode(), scaStatusResponse, response.getResponseHeaders());
    }

    protected String getUpdatePaymentPsuDataUri(String paymentService, String paymentProduct, String paymentId, String authorisationId) {
        return StringUri.fromElements(getPaymentBaseUri(), paymentService, paymentProduct, paymentId, AUTHORISATIONS, authorisationId);
    }

    protected String getSinglePaymentBaseUri() {
        return StringUri.fromElements(getPaymentBaseUri(), PAYMENTS);
    }

    protected String getPaymentBaseUri() {
        return StringUri.fromElements(baseUri, V1);
    }
}
