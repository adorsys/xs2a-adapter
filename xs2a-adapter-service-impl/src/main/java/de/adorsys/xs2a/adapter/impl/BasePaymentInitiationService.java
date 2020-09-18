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

package de.adorsys.xs2a.adapter.impl;

import de.adorsys.xs2a.adapter.api.PaymentInitiationService;
import de.adorsys.xs2a.adapter.api.RequestHeaders;
import de.adorsys.xs2a.adapter.api.RequestParams;
import de.adorsys.xs2a.adapter.api.Response;
import de.adorsys.xs2a.adapter.api.http.HttpClient;
import de.adorsys.xs2a.adapter.api.http.Request;
import de.adorsys.xs2a.adapter.api.link.LinksRewriter;
import de.adorsys.xs2a.adapter.api.model.*;
import de.adorsys.xs2a.adapter.impl.http.StringUri;
import de.adorsys.xs2a.adapter.impl.http.wiremock.WiremockStubDifferenceDetectingInterceptor;
import de.adorsys.xs2a.adapter.impl.link.identity.IdentityLinksRewriter;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import static de.adorsys.xs2a.adapter.api.validation.Validation.requireValid;
import static de.adorsys.xs2a.adapter.impl.http.ResponseHandlers.*;
import static java.util.function.Function.identity;

public class BasePaymentInitiationService extends AbstractService implements PaymentInitiationService {
    private static final LinksRewriter DEFAULT_LINKS_REWRITER = new IdentityLinksRewriter();

    protected static final String V1 = "v1";
    protected final Aspsp aspsp;
    private final Request.Builder.Interceptor requestBuilderInterceptor;
    private final LinksRewriter linksRewriter;
    private final WiremockStubDifferenceDetectingInterceptor wiremockStubDifferenceDetectingInterceptor;

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
        this.wiremockStubDifferenceDetectingInterceptor = new WiremockStubDifferenceDetectingInterceptor(aspsp);
    }

    @Override
    public Response<PaymentInitationRequestResponse201> initiatePayment(PaymentService paymentService,
                                                                        PaymentProduct paymentProduct,
                                                                        RequestHeaders requestHeaders,
                                                                        RequestParams requestParams,
                                                                        Object body) {
        return initiatePayment(
            paymentService,
            paymentProduct,
            requestHeaders,
            requestParams,
            body,
            PaymentInitationRequestResponse201.class,
            identity());
    }

    protected <T> Response<PaymentInitationRequestResponse201> initiatePayment(PaymentService paymentService,
                                                                               PaymentProduct paymentProduct,
                                                                               RequestHeaders requestHeaders,
                                                                               RequestParams requestParams,
                                                                               Object body,
                                                                               Class<T> klass,
                                                                               Function<T, PaymentInitationRequestResponse201> mapper) {
        return initiatePayment(paymentService, paymentProduct, body, requestHeaders, requestParams, mapper, jsonResponseHandler(klass));
    }

    protected <T> Response<PaymentInitationRequestResponse201> initiatePayment(PaymentService paymentService,
                                                                               PaymentProduct paymentProduct,
                                                                               Object body,
                                                                               RequestHeaders requestHeaders,
                                                                               RequestParams requestParams,
                                                                               Function<T, PaymentInitationRequestResponse201> mapper,
                                                                               HttpClient.ResponseHandler<T> responseHandler) {
        requireValid(validateInitiatePayment(paymentService, paymentProduct, requestHeaders, requestParams, body));

        String uri = StringUri.fromElements(getPaymentBaseUri(), paymentService, paymentProduct);
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
        } else if (isXml(paymentProduct)) {
            requestBuilder.xmlBody((String) body);
        } else {
            requestBuilder.jsonBody(jsonMapper.writeValueAsString(
                jsonMapper.convertValue(body, getPaymentInitiationBodyClass(paymentService))));
        }

        Response<T> response = requestBuilder.send(responseHandler,
            requestBuilderInterceptor,
            wiremockStubDifferenceDetectingInterceptor);
        PaymentInitationRequestResponse201 paymentInitiationRequestResponse = mapper.apply(response.getBody());
        paymentInitiationRequestResponse.setLinks(linksRewriter.rewrite(paymentInitiationRequestResponse.getLinks()));

        return new Response<>(response.getStatusCode(), paymentInitiationRequestResponse, response.getHeaders());
    }

    protected boolean isXml(PaymentProduct paymentProduct) {
        return paymentProduct.toString().startsWith("pain.001");
    }

    @Override
    public Response<PaymentInitiationWithStatusResponse> getSinglePaymentInformation(PaymentProduct paymentProduct,
                                                                                     String paymentId,
                                                                                     RequestHeaders requestHeaders,
                                                                                     RequestParams requestParams) {

        requireValid(validateGetSinglePaymentInformation(paymentProduct, paymentId, requestHeaders, requestParams));
        return getPaymentInformation(PaymentService.PAYMENTS,
            paymentProduct,
            paymentId,
            requestHeaders,
            requestParams,
            jsonResponseHandler(PaymentInitiationWithStatusResponse.class));
    }

    private <T> Response<T> getPaymentInformation(PaymentService paymentService,
                                                  PaymentProduct paymentProduct,
                                                  String paymentId,
                                                  RequestHeaders requestHeaders,
                                                  RequestParams requestParams,
                                                  HttpClient.ResponseHandler<T> responseHandler) {
        String uri = StringUri.fromElements(getPaymentBaseUri(), paymentService, paymentProduct, paymentId);
        uri = buildUri(uri, requestParams);

        Map<String, String> headersMap = populateGetHeaders(requestHeaders.toMap());
        return httpClient.get(uri)
                   .headers(headersMap)
                   .send(responseHandler, requestBuilderInterceptor, wiremockStubDifferenceDetectingInterceptor);
    }

    @Override
    public Response<PeriodicPaymentInitiationWithStatusResponse> getPeriodicPaymentInformation(PaymentProduct paymentProduct,
                                                                                               String paymentId,
                                                                                               RequestHeaders requestHeaders,
                                                                                               RequestParams requestParams) {
        return getPeriodicPaymentInformation(paymentProduct,
            paymentId,
            requestHeaders,
            requestParams,
            PeriodicPaymentInitiationWithStatusResponse.class,
            identity());
    }

    protected <T> Response<PeriodicPaymentInitiationWithStatusResponse> getPeriodicPaymentInformation(
        PaymentProduct paymentProduct,
        String paymentId,
        RequestHeaders requestHeaders,
        RequestParams requestParams,
        Class<T> klass,
        Function<T, PeriodicPaymentInitiationWithStatusResponse> mapper
    ) {
        requireValid(validateGetPeriodicPaymentInformation(paymentProduct, paymentId, requestHeaders, requestParams));
        return getPaymentInformation(PaymentService.PERIODIC_PAYMENTS,
            paymentProduct,
            paymentId,
            requestHeaders,
            requestParams,
            jsonResponseHandler(klass))
            .map(mapper);
    }

    @Override
    public Response<PeriodicPaymentInitiationMultipartBody> getPeriodicPain001PaymentInformation(PaymentProduct paymentProduct,
                                                                                                 String paymentId,
                                                                                                 RequestHeaders requestHeaders,
                                                                                                 RequestParams requestParams) {
        return getPeriodicPain001PaymentInformation(paymentProduct,
            paymentId,
            requestHeaders,
            requestParams,
            PeriodicPaymentInitiationMultipartBody.class,
            identity());
    }

    protected <T> Response<PeriodicPaymentInitiationMultipartBody> getPeriodicPain001PaymentInformation(
        PaymentProduct paymentProduct,
        String paymentId,
        RequestHeaders requestHeaders,
        RequestParams requestParams,
        Class<T> klass,
        Function<T, PeriodicPaymentInitiationMultipartBody> mapper
    ) {
        requireValid(validateGetPeriodicPain001PaymentInformation(paymentProduct, paymentId, requestHeaders, requestParams));
        return getPaymentInformation(PaymentService.PERIODIC_PAYMENTS,
            paymentProduct,
            paymentId,
            requestHeaders,
            requestParams,
            multipartFormDataResponseHandler(klass))
            .map(mapper);
    }

    @Override
    public Response<String> getPaymentInformationAsString(PaymentService paymentService,
                                                          PaymentProduct paymentProduct,
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
    public Response<ScaStatusResponse> getPaymentInitiationScaStatus(PaymentService paymentService,
                                                                     PaymentProduct paymentProduct,
                                                                     String paymentId,
                                                                     String authorisationId,
                                                                     RequestHeaders requestHeaders,
                                                                     RequestParams requestParams) {
        return getPaymentInitiationScaStatus(paymentService,
            paymentProduct,
            paymentId,
            authorisationId,
            requestHeaders,
            requestParams,
            ScaStatusResponse.class,
            identity());
    }

    protected <T> Response<ScaStatusResponse> getPaymentInitiationScaStatus(PaymentService paymentService,
                                                                            PaymentProduct paymentProduct,
                                                                            String paymentId,
                                                                            String authorisationId,
                                                                            RequestHeaders requestHeaders,
                                                                            RequestParams requestParams,
                                                                            Class<T> klass,
                                                                            Function<T, ScaStatusResponse> mapper) {
        requireValid(validateGetPaymentInitiationScaStatus(paymentService,
            paymentProduct,
            paymentId,
            authorisationId,
            requestHeaders,
            requestParams));
        String uri = StringUri.fromElements(getPaymentBaseUri(),
            paymentService,
            paymentProduct,
            paymentId,
            AUTHORISATIONS,
            authorisationId);
        uri = buildUri(uri, requestParams);
        Map<String, String> headersMap = populateGetHeaders(requestHeaders.toMap());
        return httpClient.get(uri)
            .headers(headersMap)
            .send(jsonResponseHandler(klass), requestBuilderInterceptor, wiremockStubDifferenceDetectingInterceptor)
            .map(mapper);
    }

    @Override
    public Response<PaymentInitiationStatusResponse200Json> getPaymentInitiationStatus(PaymentService paymentService,
                                                                                       PaymentProduct paymentProduct,
                                                                                       String paymentId,
                                                                                       RequestHeaders requestHeaders,
                                                                                       RequestParams requestParams) {
        requireValid(validateGetPaymentInitiationStatus(paymentService, paymentProduct, paymentId, requestHeaders,
            requestParams));

        String uri = getPaymentInitiationStatusUri(paymentService, paymentProduct, paymentId);
        uri = buildUri(uri, requestParams);
        Map<String, String> headersMap = populateGetHeaders(requestHeaders.toMap());

        return httpClient.get(uri)
            .headers(headersMap)
            .send(jsonResponseHandler(PaymentInitiationStatusResponse200Json.class),
                requestBuilderInterceptor,
                wiremockStubDifferenceDetectingInterceptor);
    }

    @Override
    public Response<String> getPaymentInitiationStatusAsString(PaymentService paymentService,
                                                               PaymentProduct paymentProduct,
                                                               String paymentId,
                                                               RequestHeaders requestHeaders,
                                                               RequestParams requestParams) {
        requireValid(validateGetPaymentInitiationStatusAsString(paymentService, paymentProduct, paymentId, requestHeaders, requestParams));

        String uri = getPaymentInitiationStatusUri(paymentService, paymentProduct, paymentId);
        uri = buildUri(uri, requestParams);
        Map<String, String> headersMap = populateGetHeaders(requestHeaders.toMap());

        return httpClient.get(uri)
                   .headers(headersMap)
                   .send(stringResponseHandler(), requestBuilderInterceptor, wiremockStubDifferenceDetectingInterceptor);
    }

    private String getPaymentInitiationStatusUri(PaymentService paymentService,
                                                 PaymentProduct paymentProduct,
                                                 String paymentId) {
        return StringUri.fromElements(getPaymentBaseUri(), paymentService, paymentProduct, paymentId, STATUS);
    }

    @Override
    public Response<Authorisations> getPaymentInitiationAuthorisation(PaymentService paymentService,
                                                                      PaymentProduct paymentProduct,
                                                                      String paymentId,
                                                                      RequestHeaders requestHeaders,
                                                                      RequestParams requestParams) {
        return getPaymentInitiationAuthorisation(paymentService,
            paymentProduct,
            paymentId,
            requestHeaders,
            requestParams,
            Authorisations.class,
            identity());
    }

    protected <T> Response<Authorisations> getPaymentInitiationAuthorisation(PaymentService paymentService,
                                                                             PaymentProduct paymentProduct,
                                                                             String paymentId,
                                                                             RequestHeaders requestHeaders,
                                                                             RequestParams requestParams,
                                                                             Class<T> klass,
                                                                             Function<T, Authorisations> mapper) {
        requireValid(validateGetPaymentInitiationAuthorisation(paymentService,
            paymentProduct,
            paymentId,
            requestHeaders,
            requestParams));
        String uri = StringUri.fromElements(getPaymentBaseUri(),
            paymentService,
            paymentProduct,
            paymentId,
            AUTHORISATIONS);
        uri = buildUri(uri, requestParams);
        Map<String, String> headersMap = populateGetHeaders(requestHeaders.toMap());
        return httpClient.get(uri)
            .headers(headersMap)
            .send(jsonResponseHandler(klass), requestBuilderInterceptor, wiremockStubDifferenceDetectingInterceptor)
            .map(mapper);
    }

    @Override
    public Response<StartScaprocessResponse> startPaymentAuthorisation(PaymentService paymentService,
                                                                       PaymentProduct paymentProduct,
                                                                       String paymentId,
                                                                       RequestHeaders requestHeaders,
                                                                       RequestParams requestParams) {
        return startPaymentAuthorisation(paymentService,
            paymentProduct,
            paymentId,
            requestHeaders,
            requestParams,
            StartScaprocessResponse.class,
            identity());
    }

    protected <T> Response<StartScaprocessResponse> startPaymentAuthorisation(PaymentService paymentService,
                                                                              PaymentProduct paymentProduct,
                                                                              String paymentId,
                                                                              RequestHeaders requestHeaders,
                                                                              RequestParams requestParams,
                                                                              Class<T> klass,
                                                                              Function<T, StartScaprocessResponse> mapper) {
        requireValid(validateStartPaymentAuthorisation(paymentService, paymentProduct, paymentId, requestHeaders, requestParams));

        String uri = StringUri.fromElements(getPaymentBaseUri(), paymentService, paymentProduct, paymentId, AUTHORISATIONS);
        uri = buildUri(uri, requestParams);
        Map<String, String> headersMap = populatePostHeaders(requestHeaders.toMap());

        Response<StartScaprocessResponse> response = httpClient.post(uri)
            .headers(headersMap)
            .emptyBody(true)
            .send(jsonResponseHandler(klass), requestBuilderInterceptor, wiremockStubDifferenceDetectingInterceptor)
            .map(mapper);

        Optional.ofNullable(response.getBody())
            .ifPresent(body -> body.setLinks(linksRewriter.rewrite(body.getLinks())));

        return response;
    }

    @Override
    public Response<StartScaprocessResponse> startPaymentAuthorisation(PaymentService paymentService,
                                                                       PaymentProduct paymentProduct,
                                                                       String paymentId,
                                                                       RequestHeaders requestHeaders,
                                                                       RequestParams requestParams,
                                                                       UpdatePsuAuthentication updatePsuAuthentication) {
        return startPaymentAuthorisation(paymentService,
            paymentProduct,
            paymentId,
            requestHeaders,
            requestParams,
            updatePsuAuthentication,
            StartScaprocessResponse.class,
            identity());
    }

    protected <T> Response<StartScaprocessResponse> startPaymentAuthorisation(PaymentService paymentService,
                                                                              PaymentProduct paymentProduct,
                                                                              String paymentId,
                                                                              RequestHeaders requestHeaders,
                                                                              RequestParams requestParams,
                                                                              UpdatePsuAuthentication updatePsuAuthentication,
                                                                              Class<T> klass,
                                                                              Function<T, StartScaprocessResponse> mapper) {
        requireValid(validateStartPaymentAuthorisation(paymentService, paymentProduct, paymentId, requestHeaders,
            requestParams, updatePsuAuthentication));

        String uri = StringUri.fromElements(getPaymentBaseUri(), paymentService, paymentProduct, paymentId,
            AUTHORISATIONS);
        uri = buildUri(uri, requestParams);
        Map<String, String> headersMap = populatePostHeaders(requestHeaders.toMap());
        String body = jsonMapper.writeValueAsString(updatePsuAuthentication);

        Response<T> response = httpClient.post(uri)
            .jsonBody(body)
            .headers(headersMap)
            .send(jsonResponseHandler(klass), requestBuilderInterceptor, wiremockStubDifferenceDetectingInterceptor);
        StartScaprocessResponse startScaProcessResponse = mapper.apply(response.getBody());
        startScaProcessResponse.setLinks(linksRewriter.rewrite(startScaProcessResponse.getLinks()));

        return new Response<>(response.getStatusCode(), startScaProcessResponse, response.getHeaders());
    }

    @Override
    public Response<UpdatePsuAuthenticationResponse> updatePaymentPsuData(PaymentService paymentService,
                                                                          PaymentProduct paymentProduct,
                                                                          String paymentId,
                                                                          String authorisationId,
                                                                          RequestHeaders requestHeaders,
                                                                          RequestParams requestParams,
                                                                          UpdatePsuAuthentication updatePsuAuthentication) {
        return updatePaymentPsuData(paymentService,
            paymentProduct,
            paymentId,
            authorisationId,
            requestHeaders,
            requestParams,
            updatePsuAuthentication,
            UpdatePsuAuthenticationResponse.class,
            identity());
    }

    protected <T> Response<UpdatePsuAuthenticationResponse> updatePaymentPsuData(PaymentService paymentService,
                                                                                 PaymentProduct paymentProduct,
                                                                                 String paymentId,
                                                                                 String authorisationId,
                                                                                 RequestHeaders requestHeaders,
                                                                                 RequestParams requestParams,
                                                                                 UpdatePsuAuthentication updatePsuAuthentication,
                                                                                 Class<T> klass,
                                                                                 Function<T, UpdatePsuAuthenticationResponse> mapper) {
        requireValid(validateUpdatePaymentPsuData(paymentService,
            paymentProduct,
            paymentId,
            authorisationId,
            requestHeaders,
            requestParams,
            updatePsuAuthentication));

        String uri = StringUri.fromElements(getPaymentBaseUri(), paymentService, paymentProduct, paymentId, AUTHORISATIONS, authorisationId);
        uri = buildUri(uri, requestParams);
        Map<String, String> headersMap = populatePutHeaders(requestHeaders.toMap());
        headersMap = addPsuIdTypeHeader(headersMap);
        String body = jsonMapper.writeValueAsString(updatePsuAuthentication);

        Response<T> response = httpClient.put(uri)
            .jsonBody(body)
            .headers(headersMap)
            .send(jsonResponseHandler(klass), requestBuilderInterceptor, wiremockStubDifferenceDetectingInterceptor);
        UpdatePsuAuthenticationResponse updatePsuAuthenticationResponse = mapper.apply(response.getBody());
        updatePsuAuthenticationResponse.setLinks(linksRewriter.rewrite(updatePsuAuthenticationResponse.getLinks()));

        return new Response<>(response.getStatusCode(), updatePsuAuthenticationResponse, response.getHeaders());
    }

    @Override
    public Response<SelectPsuAuthenticationMethodResponse> updatePaymentPsuData(PaymentService paymentService,
                                                                                PaymentProduct paymentProduct,
                                                                                String paymentId,
                                                                                String authorisationId,
                                                                                RequestHeaders requestHeaders,
                                                                                RequestParams requestParams,
                                                                                SelectPsuAuthenticationMethod selectPsuAuthenticationMethod) {
        return updatePaymentPsuData(paymentService,
            paymentProduct,
            paymentId,
            authorisationId,
            requestHeaders,
            requestParams,
            selectPsuAuthenticationMethod,
            SelectPsuAuthenticationMethodResponse.class,
            identity());
    }

    protected <T> Response<SelectPsuAuthenticationMethodResponse> updatePaymentPsuData(PaymentService paymentService,
                                                                                       PaymentProduct paymentProduct,
                                                                                       String paymentId,
                                                                                       String authorisationId,
                                                                                       RequestHeaders requestHeaders,
                                                                                       RequestParams requestParams,
                                                                                       SelectPsuAuthenticationMethod selectPsuAuthenticationMethod,
                                                                                       Class<T> klass,
                                                                                       Function<T, SelectPsuAuthenticationMethodResponse> mapper) {
        requireValid(validateUpdatePaymentPsuData(paymentService,
            paymentProduct,
            paymentId,
            authorisationId,
            requestHeaders,
            requestParams,
            selectPsuAuthenticationMethod));

        String uri = StringUri.fromElements(getPaymentBaseUri(), paymentService, paymentProduct, paymentId, AUTHORISATIONS, authorisationId);
        uri = buildUri(uri, requestParams);
        Map<String, String> headersMap = populatePutHeaders(requestHeaders.toMap());
        headersMap = addPsuIdTypeHeader(headersMap);
        String body = jsonMapper.writeValueAsString(selectPsuAuthenticationMethod);

        Response<T> response = httpClient.put(uri)
            .jsonBody(body)
            .headers(headersMap)
            .send(jsonResponseHandler(klass), requestBuilderInterceptor, wiremockStubDifferenceDetectingInterceptor);
        SelectPsuAuthenticationMethodResponse selectPsuAuthenticationMethodResponse = mapper.apply(response.getBody());
        selectPsuAuthenticationMethodResponse.setLinks(linksRewriter.rewrite(selectPsuAuthenticationMethodResponse.getLinks()));

        return new Response<>(response.getStatusCode(), selectPsuAuthenticationMethodResponse, response.getHeaders());
    }

    @Override
    public Response<ScaStatusResponse> updatePaymentPsuData(PaymentService paymentService,
                                                            PaymentProduct paymentProduct,
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

    protected <T> Response<ScaStatusResponse> updatePaymentPsuData(PaymentService paymentService,
                                                                   PaymentProduct paymentProduct,
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
            .send(jsonResponseHandler(klass), requestBuilderInterceptor, wiremockStubDifferenceDetectingInterceptor);

        ScaStatusResponse scaStatusResponse = mapper.apply(response.getBody());
        return new Response<>(response.getStatusCode(), scaStatusResponse, response.getHeaders());
    }

    protected String getUpdatePaymentPsuDataUri(PaymentService paymentService,
                                                PaymentProduct paymentProduct,
                                                String paymentId,
                                                String authorisationId) {
        return StringUri.fromElements(getPaymentBaseUri(),
            paymentService,
            paymentProduct,
            paymentId,
            AUTHORISATIONS,
            authorisationId);
    }

    protected String getIdpUri() {
        return aspsp.getIdpUrl();
    }

    protected String getPaymentBaseUri() {
        return StringUri.fromElements(aspsp.getUrl(), V1);
    }
}
