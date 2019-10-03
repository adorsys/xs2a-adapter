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

import de.adorsys.xs2a.adapter.http.HttpClient;
import de.adorsys.xs2a.adapter.http.Request;
import de.adorsys.xs2a.adapter.http.StringUri;
import de.adorsys.xs2a.adapter.service.PaymentInitiationService;
import de.adorsys.xs2a.adapter.service.RequestHeaders;
import de.adorsys.xs2a.adapter.service.Response;
import de.adorsys.xs2a.adapter.service.model.*;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import java.util.Map;
import java.util.function.Function;

import static de.adorsys.xs2a.adapter.http.ResponseHandlers.jsonResponseHandler;
import static de.adorsys.xs2a.adapter.http.ResponseHandlers.stringResponseHandler;
import static java.util.function.Function.identity;

public class BasePaymentInitiationService extends AbstractService implements PaymentInitiationService {

    protected static final String V1 = "v1";
    protected static final String PAYMENTS = "payments";
    protected final String baseUri;
    private final Request.Builder.Interceptor requestBuilderInterceptor;

    public BasePaymentInitiationService(String baseUri, HttpClient httpClient) {
        this(baseUri, httpClient, null);
    }

    public BasePaymentInitiationService(String baseUri,
                                        HttpClient httpClient,
                                        Request.Builder.Interceptor requestBuilderInterceptor) {
        super(httpClient);
        this.baseUri = baseUri;
        this.requestBuilderInterceptor = requestBuilderInterceptor;
    }

    @Override
    public Response<PaymentInitiationRequestResponse> initiateSinglePayment(String paymentProduct, RequestHeaders requestHeaders, Object body) {
        return initiateSinglePayment(StandardPaymentProduct.fromSlug(paymentProduct), body, requestHeaders, PaymentInitiationRequestResponse.class, identity());
    }

    protected <T> Response<PaymentInitiationRequestResponse> initiateSinglePayment(StandardPaymentProduct paymentProduct,
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

        Response<T> response = httpClient.post(StringUri.fromElements(getSinglePaymentBaseUri(), paymentProduct.getSlug()))
            .jsonBody(bodyString)
            .headers(headersMap)
            .send(requestBuilderInterceptor, jsonResponseHandler(klass));
        PaymentInitiationRequestResponse paymentInitiationRequestResponse = mapper.apply(response.getBody());
        return new Response<>(response.getStatusCode(), paymentInitiationRequestResponse, response.getHeaders());
    }

    @Override
    public Response<SinglePaymentInitiationInformationWithStatusResponse> getSinglePaymentInformation(String paymentProduct,
                                                                                                      String paymentId,
                                                                                                      RequestHeaders requestHeaders) {
        return getSinglePaymentInformation(StandardPaymentProduct.fromSlug(paymentProduct), paymentId, requestHeaders);
    }

    private Response<SinglePaymentInitiationInformationWithStatusResponse> getSinglePaymentInformation(StandardPaymentProduct paymentProduct,
                                                                                                       String paymentId,
                                                                                                       RequestHeaders requestHeaders) {
        String uri = StringUri.fromElements(getSinglePaymentBaseUri(), paymentProduct.getSlug(), paymentId);

        Map<String, String> headersMap = populateGetHeaders(requestHeaders.toMap());
        return httpClient.get(uri)
            .headers(headersMap)
            .send(requestBuilderInterceptor, jsonResponseHandler(SinglePaymentInitiationInformationWithStatusResponse.class));
    }

    @Override
    public Response<PaymentInitiationScaStatusResponse> getPaymentInitiationScaStatus(String paymentService, String paymentProduct, String paymentId, String authorisationId, RequestHeaders requestHeaders) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Response<PaymentInitiationStatus> getSinglePaymentInitiationStatus(String paymentProduct, String paymentId, RequestHeaders requestHeaders) {
        return getSinglePaymentInitiationStatus(StandardPaymentProduct.fromSlug(paymentProduct), paymentId, requestHeaders);
    }

    private Response<PaymentInitiationStatus> getSinglePaymentInitiationStatus(StandardPaymentProduct paymentProduct,
                                                                               String paymentId,
                                                                               RequestHeaders requestHeaders) {
        String uri = getSinglePaymentInitiationStatusUri(paymentProduct.getSlug(), paymentId);
        Map<String, String> headersMap = populateGetHeaders(requestHeaders.toMap());

        return httpClient.get(uri)
            .headers(headersMap)
            .send(requestBuilderInterceptor, jsonResponseHandler(PaymentInitiationStatus.class));
    }

    @Override
    public Response<String> getSinglePaymentInitiationStatusAsString(String paymentProduct, String paymentId, RequestHeaders requestHeaders) {
        String uri = getSinglePaymentInitiationStatusUri(paymentProduct, paymentId);
        Map<String, String> headersMap = populateGetHeaders(requestHeaders.toMap());

        return httpClient.get(uri)
            .headers(headersMap)
            .send(requestBuilderInterceptor, stringResponseHandler());
    }

    private String getSinglePaymentInitiationStatusUri(String paymentProduct, String paymentId) {
        return StringUri.fromElements(getSinglePaymentBaseUri(), paymentProduct, paymentId, STATUS);
    }

    @Override
    public Response<PaymentInitiationAuthorisationResponse> getPaymentInitiationAuthorisation(String paymentService, String paymentProduct, String paymentId, RequestHeaders requestHeaders) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Response<StartScaProcessResponse> startSinglePaymentAuthorisation(String paymentProduct,
                                                                             String paymentId,
                                                                             RequestHeaders requestHeaders,
                                                                             UpdatePsuAuthentication updatePsuAuthentication) {
        return startSinglePaymentAuthorisation(StandardPaymentProduct.fromSlug(paymentProduct), paymentId, requestHeaders, updatePsuAuthentication, StartScaProcessResponse.class, identity());
    }

    protected <T> Response<StartScaProcessResponse> startSinglePaymentAuthorisation(PaymentProduct paymentProduct,
                                                                                    String paymentId,
                                                                                    RequestHeaders requestHeaders,
                                                                                    UpdatePsuAuthentication updatePsuAuthentication,
                                                                                    Class<T> klass,
                                                                                    Function<T, StartScaProcessResponse> mapper) {
        String uri = StringUri.fromElements(getSinglePaymentBaseUri(), paymentProduct.getSlug(), paymentId, AUTHORISATIONS);
        Map<String, String> headersMap = populatePostHeaders(requestHeaders.toMap());
        String body = jsonMapper.writeValueAsString(updatePsuAuthentication);

        Response<T> response = httpClient.post(uri)
            .jsonBody(body)
            .headers(headersMap)
            .send(requestBuilderInterceptor, jsonResponseHandler(klass));
        StartScaProcessResponse startScaProcessResponse = mapper.apply(response.getBody());
        return new Response<>(response.getStatusCode(), startScaProcessResponse, response.getHeaders());
    }

    @Override
    public Response<UpdatePsuAuthenticationResponse> updatePaymentPsuData(String paymentService, String paymentProduct, String paymentId, String authorisationId, RequestHeaders requestHeaders, UpdatePsuAuthentication updatePsuAuthentication) {
        return updatePaymentPsuData(paymentService, StandardPaymentProduct.fromSlug(paymentProduct), paymentId, authorisationId, requestHeaders, updatePsuAuthentication, UpdatePsuAuthenticationResponse.class, identity());
    }

    protected <T> Response<UpdatePsuAuthenticationResponse> updatePaymentPsuData(String paymentService,
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

        Response<T> response = httpClient.put(uri)
            .jsonBody(body)
            .headers(headersMap)
            .send(requestBuilderInterceptor, jsonResponseHandler(klass));
        UpdatePsuAuthenticationResponse updatePsuAuthenticationResponse = mapper.apply(response.getBody());
        return new Response<>(response.getStatusCode(), updatePsuAuthenticationResponse, response.getHeaders());
    }

    @Override
    public Response<SelectPsuAuthenticationMethodResponse> updatePaymentPsuData(String paymentService, String paymentProduct, String paymentId, String authorisationId, RequestHeaders requestHeaders, SelectPsuAuthenticationMethod selectPsuAuthenticationMethod) {
        return updatePaymentPsuData(paymentService, StandardPaymentProduct.fromSlug(paymentProduct), paymentId, authorisationId, requestHeaders, selectPsuAuthenticationMethod, SelectPsuAuthenticationMethodResponse.class, identity());
    }

    protected <T> Response<SelectPsuAuthenticationMethodResponse> updatePaymentPsuData(String paymentService,
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

        Response<T> response = httpClient.put(uri)
            .jsonBody(body)
            .headers(headersMap)
            .send(requestBuilderInterceptor, jsonResponseHandler(klass));
        SelectPsuAuthenticationMethodResponse selectPsuAuthenticationMethodResponse = mapper.apply(response.getBody());
        return new Response<>(response.getStatusCode(), selectPsuAuthenticationMethodResponse, response.getHeaders());
    }

    @Override
    public Response<ScaStatusResponse> updatePaymentPsuData(String paymentService, String paymentProduct, String paymentId, String authorisationId, RequestHeaders requestHeaders, TransactionAuthorisation transactionAuthorisation) {
        return updatePaymentPsuData(paymentService, paymentProduct, paymentId, authorisationId, requestHeaders, transactionAuthorisation, ScaStatusResponse.class, identity());
    }

    protected <T> Response<ScaStatusResponse> updatePaymentPsuData(String paymentService,
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

        Response<T> response = httpClient.put(uri)
            .jsonBody(body)
            .headers(headersMap)
            .send(requestBuilderInterceptor, jsonResponseHandler(klass));

        ScaStatusResponse scaStatusResponse = mapper.apply(response.getBody());
        return new Response<>(response.getStatusCode(), scaStatusResponse, response.getHeaders());
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
