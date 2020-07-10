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

import de.adorsys.xs2a.adapter.adapter.link.identity.IdentityLinksRewriter;
import de.adorsys.xs2a.adapter.api.model.*;
import de.adorsys.xs2a.adapter.http.HttpClient;
import de.adorsys.xs2a.adapter.http.Request;
import de.adorsys.xs2a.adapter.http.StringUri;
import de.adorsys.xs2a.adapter.service.PaymentInitiationService;
import de.adorsys.xs2a.adapter.service.RequestHeaders;
import de.adorsys.xs2a.adapter.service.RequestParams;
import de.adorsys.xs2a.adapter.service.Response;
import de.adorsys.xs2a.adapter.service.link.LinksRewriter;
import de.adorsys.xs2a.adapter.service.model.Aspsp;

import javax.ws.rs.core.MediaType;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import static de.adorsys.xs2a.adapter.http.ResponseHandlers.jsonResponseHandler;
import static de.adorsys.xs2a.adapter.http.ResponseHandlers.stringResponseHandler;
import static de.adorsys.xs2a.adapter.validation.Validation.requireValid;
import static java.util.function.Function.identity;

public class BasePaymentInitiationService extends AbstractService implements PaymentInitiationService {
    private static final LinksRewriter DEFAULT_LINKS_REWRITER = new IdentityLinksRewriter();

    protected static final String V1 = "v1";
    protected static final String SINGLE_PAYMENTS = "payments";
    protected static final String PERIODIC_PAYMENTS = "periodic-payments";
    protected final Aspsp aspsp;
    private final Request.Builder.Interceptor requestBuilderInterceptor;
    private final LinksRewriter linksRewriter;

    public BasePaymentInitiationService(Aspsp aspsp, HttpClient httpClient) {
        this(aspsp, httpClient, null, DEFAULT_LINKS_REWRITER);
    }

    public BasePaymentInitiationService(Aspsp aspsp,
                                        HttpClient httpClient,
                                        Request.Builder.Interceptor requestBuilderInterceptor) {
        this(aspsp, httpClient, requestBuilderInterceptor, DEFAULT_LINKS_REWRITER);
    }

    public BasePaymentInitiationService(Aspsp aspsp,
                                        HttpClient httpClient,
                                        LinksRewriter linksRewriter) {
        this(aspsp, httpClient, null, linksRewriter);
    }

    public BasePaymentInitiationService(Aspsp aspsp,
                                        HttpClient httpClient,
                                        Request.Builder.Interceptor requestBuilderInterceptor,
                                        LinksRewriter linksRewriter) {
        super(httpClient);
        this.aspsp = aspsp;
        this.requestBuilderInterceptor = requestBuilderInterceptor;
        this.linksRewriter = linksRewriter;
    }

    @Override
    public Response<PaymentInitationRequestResponse201> initiatePayment(String paymentService,
                                                                        String paymentProduct,
                                                                        RequestHeaders requestHeaders,
                                                                        RequestParams requestParams,
                                                                        Object body) {
        return initiatePayment(
            paymentService,
            StandardPaymentProduct.fromSlug(paymentProduct),
            body,
            requestHeaders,
            requestParams,
            PaymentInitationRequestResponse201.class,
            identity());
    }

    protected <T> Response<PaymentInitationRequestResponse201> initiatePayment(String paymentService,
                                                                               StandardPaymentProduct paymentProduct,
                                                                               Object body,
                                                                               RequestHeaders requestHeaders,
                                                                               RequestParams requestParams,
                                                                               Class<T> klass,
                                                                               Function<T, PaymentInitationRequestResponse201> mapper) {
        return initiatePayment(paymentService, paymentProduct, body, requestHeaders, requestParams, mapper, jsonResponseHandler(klass));
    }

    protected <T> Response<PaymentInitationRequestResponse201> initiatePayment(String paymentService,
                                                                               StandardPaymentProduct paymentProduct,
                                                                               Object body,
                                                                               RequestHeaders requestHeaders,
                                                                               RequestParams requestParams,
                                                                               Function<T, PaymentInitationRequestResponse201> mapper,
                                                                               HttpClient.ResponseHandler<T> responseHandler) {
        requireValid(validateInitiatePayment(paymentService, paymentProduct.getSlug(), requestHeaders, requestParams, body));

        String uri = StringUri.fromElements(getPaymentBaseUri(), paymentService, paymentProduct.getSlug());
        uri = buildUri(uri, requestParams);
        Map<String, String> headersMap = populatePostHeaders(requestHeaders.toMap());
        headersMap = addPsuIdTypeHeader(headersMap);
        Request.Builder requestBuilder = httpClient.post(uri)
            .headers(headersMap);
        if (body instanceof PeriodicPaymentInitiationMultipartBody) {
            PeriodicPaymentInitiationMultipartBody multipartBody = (PeriodicPaymentInitiationMultipartBody) body;
            requestBuilder.addXmlPart("xml_sct", (String) multipartBody.getXml_sct());
            requestBuilder.addJsonPart("json_standingorderType",
                jsonMapper.writeValueAsString(multipartBody.getJson_standingorderType()));
        } else {
            switch (paymentProduct.getMediaType()) {
                case MediaType.APPLICATION_JSON:
                    requestBuilder.jsonBody(jsonMapper.writeValueAsString(
                        jsonMapper.convertValue(body, getPaymentInitiationBodyClass(paymentService))));
                    break;
                case MediaType.APPLICATION_XML:
                    requestBuilder.xmlBody((String) body);
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported payment product media type");
            }
        }

        Response<T> response = requestBuilder.send(requestBuilderInterceptor, responseHandler);
        PaymentInitationRequestResponse201 paymentInitiationRequestResponse = mapper.apply(response.getBody());
        paymentInitiationRequestResponse.setLinks(linksRewriter.rewrite(paymentInitiationRequestResponse.getLinks()));

        return new Response<>(response.getStatusCode(), paymentInitiationRequestResponse, response.getHeaders());
    }

    @Override
    public Response<PaymentInitiationWithStatusResponse> getSinglePaymentInformation(String paymentProduct,
                                                                                     String paymentId,
                                                                                     RequestHeaders requestHeaders,
                                                                                     RequestParams requestParams) {

        requireValid(validateGetSinglePaymentInformation(paymentProduct, paymentId, requestHeaders, requestParams));
        return getPaymentInformation(SINGLE_PAYMENTS,
            paymentProduct,
            paymentId,
            requestHeaders,
            requestParams,
            jsonResponseHandler(PaymentInitiationWithStatusResponse.class));
    }

    private <T> Response<T> getPaymentInformation(String paymentService,
                                                  String paymentProduct,
                                                  String paymentId,
                                                  RequestHeaders requestHeaders,
                                                  RequestParams requestParams,
                                                  HttpClient.ResponseHandler<T> responseHandler) {
        String uri = StringUri.fromElements(getPaymentBaseUri(), paymentService, paymentProduct, paymentId);
        uri = buildUri(uri, requestParams);

        Map<String, String> headersMap = populateGetHeaders(requestHeaders.toMap());
        return httpClient.get(uri)
                   .headers(headersMap)
                   .send(requestBuilderInterceptor, responseHandler);
    }

    @Override
    public Response<PeriodicPaymentInitiationWithStatusResponse> getPeriodicPaymentInformation(String paymentProduct,
                                                                                               String paymentId,
                                                                                               RequestHeaders requestHeaders,
                                                                                               RequestParams requestParams) {
        requireValid(validateGetPeriodicPaymentInformation(paymentProduct, paymentId, requestHeaders, requestParams));
        return getPaymentInformation(PERIODIC_PAYMENTS,
            paymentProduct,
            paymentId,
            requestHeaders,
            requestParams,
            jsonResponseHandler(PeriodicPaymentInitiationWithStatusResponse.class));
    }

    @Override
    public Response<String> getPaymentInformationAsString(String paymentService,
                                                          String paymentProduct,
                                                          String paymentId,
                                                          RequestHeaders requestHeaders,
                                                          RequestParams requestParams) {
        return getPaymentInformation(paymentService,
            paymentProduct,
            paymentId,
            requestHeaders,
            requestParams,
            stringResponseHandler());
    }

    @Override
    public Response<ScaStatusResponse> getPaymentInitiationScaStatus(String paymentService,
                                                                     String paymentProduct,
                                                                     String paymentId,
                                                                     String authorisationId,
                                                                     RequestHeaders requestHeaders,
                                                                     RequestParams requestParams) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Response<PaymentInitiationStatusResponse200Json> getPaymentInitiationStatus(String paymentService,
                                                                                       String paymentProduct,
                                                                                       String paymentId,
                                                                                       RequestHeaders requestHeaders,
                                                                                       RequestParams requestParams) {
        requireValid(validateGetPaymentInitiationStatus(paymentService, paymentProduct, paymentProduct, requestHeaders,
            requestParams));

        return getPaymentInitiationStatus(paymentService,
            StandardPaymentProduct.fromSlug(paymentProduct),
            paymentId,
            requestHeaders,
            requestParams);
    }

    private Response<PaymentInitiationStatusResponse200Json> getPaymentInitiationStatus(String paymentService,
                                                                                        StandardPaymentProduct paymentProduct,
                                                                                        String paymentId,
                                                                                        RequestHeaders requestHeaders,
                                                                                        RequestParams requestParams) {
        String uri = getPaymentInitiationStatusUri(paymentService, paymentProduct.getSlug(), paymentId);
        uri = buildUri(uri, requestParams);
        Map<String, String> headersMap = populateGetHeaders(requestHeaders.toMap());

        return httpClient.get(uri)
            .headers(headersMap)
            .send(requestBuilderInterceptor, jsonResponseHandler(PaymentInitiationStatusResponse200Json.class));
    }

    @Override
    public Response<String> getPaymentInitiationStatusAsString(String paymentService,
                                                               String paymentProduct,
                                                               String paymentId,
                                                               RequestHeaders requestHeaders,
                                                               RequestParams requestParams) {
        requireValid(validateGetSinglePaymentInitiationStatusAsString(paymentProduct, paymentId, requestHeaders, requestParams));

        String uri = getPaymentInitiationStatusUri(paymentService, paymentProduct, paymentId);
        uri = buildUri(uri, requestParams);
        Map<String, String> headersMap = populateGetHeaders(requestHeaders.toMap());

        return httpClient.get(uri)
                   .headers(headersMap)
                   .send(requestBuilderInterceptor, stringResponseHandler());
    }

    private String getPaymentInitiationStatusUri(String paymentService, String paymentProduct, String paymentId) {
        return StringUri.fromElements(getPaymentBaseUri(), paymentService, paymentProduct, paymentId, STATUS);
    }

    @Override
    public Response<Authorisations> getPaymentInitiationAuthorisation(String paymentService,
                                                                      String paymentProduct,
                                                                      String paymentId,
                                                                      RequestHeaders requestHeaders,
                                                                      RequestParams requestParams) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Response<StartScaprocessResponse> startPaymentAuthorisation(String paymentService,
                                                                       String paymentProduct,
                                                                       String paymentId,
                                                                       RequestHeaders requestHeaders,
                                                                       RequestParams requestParams) {
        requireValid(validateStartPaymentAuthorisation(paymentService, paymentProduct, paymentId, requestHeaders, requestParams));

        String uri = StringUri.fromElements(getPaymentBaseUri(), paymentService, paymentProduct, paymentId, AUTHORISATIONS);
        uri = buildUri(uri, requestParams);
        Map<String, String> headersMap = populatePostHeaders(requestHeaders.toMap());

        Response<StartScaprocessResponse> response = httpClient.post(uri)
            .headers(headersMap)
            .emptyBody(true)
            .send(requestBuilderInterceptor, jsonResponseHandler(StartScaprocessResponse.class));

        Optional.ofNullable(response.getBody())
            .ifPresent(body -> body.setLinks(linksRewriter.rewrite(body.getLinks())));

        return response;
    }

    @Override
    public Response<StartScaprocessResponse> startPaymentAuthorisation(String paymentService,
                                                                       String paymentProduct,
                                                                       String paymentId,
                                                                       RequestHeaders requestHeaders,
                                                                       RequestParams requestParams,
                                                                       UpdatePsuAuthentication updatePsuAuthentication) {
        return startPaymentAuthorisation(paymentService,
            StandardPaymentProduct.fromSlug(paymentProduct),
            paymentId,
            requestHeaders,
            requestParams,
            updatePsuAuthentication,
            StartScaprocessResponse.class,
            identity());
    }

    protected <T> Response<StartScaprocessResponse> startPaymentAuthorisation(String paymentService,
                                                                              PaymentProduct paymentProduct,
                                                                              String paymentId,
                                                                              RequestHeaders requestHeaders,
                                                                              RequestParams requestParams,
                                                                              UpdatePsuAuthentication updatePsuAuthentication,
                                                                              Class<T> klass,
                                                                              Function<T, StartScaprocessResponse> mapper) {
        requireValid(validateStartPaymentAuthorisation(paymentService, paymentProduct.getSlug(), paymentId, requestHeaders,
            requestParams, updatePsuAuthentication));

        String uri = StringUri.fromElements(getPaymentBaseUri(), paymentService, paymentProduct.getSlug(), paymentId,
            AUTHORISATIONS);
        uri = buildUri(uri, requestParams);
        Map<String, String> headersMap = populatePostHeaders(requestHeaders.toMap());
        String body = jsonMapper.writeValueAsString(updatePsuAuthentication);

        Response<T> response = httpClient.post(uri)
            .jsonBody(body)
            .headers(headersMap)
            .send(requestBuilderInterceptor, jsonResponseHandler(klass));
        StartScaprocessResponse startScaProcessResponse = mapper.apply(response.getBody());
        startScaProcessResponse.setLinks(linksRewriter.rewrite(startScaProcessResponse.getLinks()));

        return new Response<>(response.getStatusCode(), startScaProcessResponse, response.getHeaders());
    }

    @Override
    public Response<UpdatePsuAuthenticationResponse> updatePaymentPsuData(String paymentService,
                                                                          String paymentProduct,
                                                                          String paymentId,
                                                                          String authorisationId,
                                                                          RequestHeaders requestHeaders,
                                                                          RequestParams requestParams,
                                                                          UpdatePsuAuthentication updatePsuAuthentication) {
        return updatePaymentPsuData(paymentService,
            StandardPaymentProduct.fromSlug(paymentProduct),
            paymentId,
            authorisationId,
            requestHeaders,
            requestParams,
            updatePsuAuthentication,
            UpdatePsuAuthenticationResponse.class,
            identity());
    }

    protected <T> Response<UpdatePsuAuthenticationResponse> updatePaymentPsuData(String paymentService,
                                                                                 PaymentProduct paymentProduct,
                                                                                 String paymentId,
                                                                                 String authorisationId,
                                                                                 RequestHeaders requestHeaders,
                                                                                 RequestParams requestParams,
                                                                                 UpdatePsuAuthentication updatePsuAuthentication,
                                                                                 Class<T> klass,
                                                                                 Function<T, UpdatePsuAuthenticationResponse> mapper) {
        requireValid(validateUpdatePaymentPsuData(paymentService,
            paymentProduct.getSlug(),
            paymentId,
            authorisationId,
            requestHeaders,
            requestParams,
            updatePsuAuthentication));

        String uri = StringUri.fromElements(getPaymentBaseUri(), paymentService, paymentProduct.getSlug(), paymentId, AUTHORISATIONS, authorisationId);
        uri = buildUri(uri, requestParams);
        Map<String, String> headersMap = populatePutHeaders(requestHeaders.toMap());
        headersMap = addPsuIdTypeHeader(headersMap);
        String body = jsonMapper.writeValueAsString(updatePsuAuthentication);

        Response<T> response = httpClient.put(uri)
            .jsonBody(body)
            .headers(headersMap)
            .send(requestBuilderInterceptor, jsonResponseHandler(klass));
        UpdatePsuAuthenticationResponse updatePsuAuthenticationResponse = mapper.apply(response.getBody());
        updatePsuAuthenticationResponse.setLinks(linksRewriter.rewrite(updatePsuAuthenticationResponse.getLinks()));

        return new Response<>(response.getStatusCode(), updatePsuAuthenticationResponse, response.getHeaders());
    }

    @Override
    public Response<SelectPsuAuthenticationMethodResponse> updatePaymentPsuData(String paymentService,
                                                                                String paymentProduct,
                                                                                String paymentId,
                                                                                String authorisationId,
                                                                                RequestHeaders requestHeaders,
                                                                                RequestParams requestParams,
                                                                                SelectPsuAuthenticationMethod selectPsuAuthenticationMethod) {
        return updatePaymentPsuData(paymentService,
            StandardPaymentProduct.fromSlug(paymentProduct),
            paymentId,
            authorisationId,
            requestHeaders,
            requestParams,
            selectPsuAuthenticationMethod,
            SelectPsuAuthenticationMethodResponse.class,
            identity());
    }

    protected <T> Response<SelectPsuAuthenticationMethodResponse> updatePaymentPsuData(String paymentService,
                                                                                       PaymentProduct paymentProduct,
                                                                                       String paymentId,
                                                                                       String authorisationId,
                                                                                       RequestHeaders requestHeaders,
                                                                                       RequestParams requestParams,
                                                                                       SelectPsuAuthenticationMethod selectPsuAuthenticationMethod,
                                                                                       Class<T> klass,
                                                                                       Function<T, SelectPsuAuthenticationMethodResponse> mapper) {
        requireValid(validateUpdatePaymentPsuData(paymentService,
            paymentProduct.getSlug(),
            paymentId,
            authorisationId,
            requestHeaders,
            requestParams,
            selectPsuAuthenticationMethod));

        String uri = StringUri.fromElements(getPaymentBaseUri(), paymentService, paymentProduct.getSlug(), paymentId, AUTHORISATIONS, authorisationId);
        uri = buildUri(uri, requestParams);
        Map<String, String> headersMap = populatePutHeaders(requestHeaders.toMap());
        headersMap = addPsuIdTypeHeader(headersMap);
        String body = jsonMapper.writeValueAsString(selectPsuAuthenticationMethod);

        Response<T> response = httpClient.put(uri)
            .jsonBody(body)
            .headers(headersMap)
            .send(requestBuilderInterceptor, jsonResponseHandler(klass));
        SelectPsuAuthenticationMethodResponse selectPsuAuthenticationMethodResponse = mapper.apply(response.getBody());
        selectPsuAuthenticationMethodResponse.setLinks(linksRewriter.rewrite(selectPsuAuthenticationMethodResponse.getLinks()));

        return new Response<>(response.getStatusCode(), selectPsuAuthenticationMethodResponse, response.getHeaders());
    }

    @Override
    public Response<ScaStatusResponse> updatePaymentPsuData(String paymentService,
                                                            String paymentProduct,
                                                            String paymentId,
                                                            String authorisationId,
                                                            RequestHeaders requestHeaders,
                                                            RequestParams requestParams,
                                                            TransactionAuthorisation transactionAuthorisation) {
        return updatePaymentPsuData(paymentService,
            paymentProduct,
            paymentId,
            authorisationId,
            requestHeaders,
            requestParams,
            transactionAuthorisation,
            ScaStatusResponse.class,
            identity());
    }

    protected <T> Response<ScaStatusResponse> updatePaymentPsuData(String paymentService,
                                                                   String paymentProduct,
                                                                   String paymentId,
                                                                   String authorisationId,
                                                                   RequestHeaders requestHeaders,
                                                                   RequestParams requestParams,
                                                                   TransactionAuthorisation transactionAuthorisation,
                                                                   Class<T> klass,
                                                                   Function<T, ScaStatusResponse> mapper) {
        requireValid(validateUpdatePaymentPsuData(paymentService,
            paymentProduct,
            paymentId,
            authorisationId,
            requestHeaders,
            requestParams,
            transactionAuthorisation));

        String uri = getUpdatePaymentPsuDataUri(paymentService, paymentProduct, paymentId, authorisationId);
        uri = buildUri(uri, requestParams);
        Map<String, String> headersMap = populatePutHeaders(requestHeaders.toMap());
        headersMap = addPsuIdTypeHeader(headersMap);
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

    protected String getIdpUri() {
        return aspsp.getIdpUrl();
    }

    protected String getPaymentBaseUri() {
        return StringUri.fromElements(aspsp.getUrl(), V1);
    }
}
