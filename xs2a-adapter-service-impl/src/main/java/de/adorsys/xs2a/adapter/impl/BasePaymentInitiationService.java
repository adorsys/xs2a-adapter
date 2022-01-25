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

package de.adorsys.xs2a.adapter.impl;

import de.adorsys.xs2a.adapter.api.PaymentInitiationService;
import de.adorsys.xs2a.adapter.api.RequestHeaders;
import de.adorsys.xs2a.adapter.api.RequestParams;
import de.adorsys.xs2a.adapter.api.Response;
import de.adorsys.xs2a.adapter.api.http.HttpClient;
import de.adorsys.xs2a.adapter.api.http.HttpLogSanitizer;
import de.adorsys.xs2a.adapter.api.http.Interceptor;
import de.adorsys.xs2a.adapter.api.http.Request;
import de.adorsys.xs2a.adapter.api.link.LinksRewriter;
import de.adorsys.xs2a.adapter.api.model.*;
import de.adorsys.xs2a.adapter.impl.http.ResponseHandlers;
import de.adorsys.xs2a.adapter.impl.http.StringUri;
import de.adorsys.xs2a.adapter.impl.link.identity.IdentityLinksRewriter;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import static de.adorsys.xs2a.adapter.api.validation.Validation.requireValid;
import static java.util.function.Function.identity;

public class BasePaymentInitiationService extends AbstractService implements PaymentInitiationService {
    private static final LinksRewriter DEFAULT_LINKS_REWRITER = new IdentityLinksRewriter();

    protected static final String V1 = "v1";
    protected final Aspsp aspsp;
    private final List<Interceptor> interceptors;
    private final LinksRewriter linksRewriter;
    private final ResponseHandlers responseHandlers;

    public BasePaymentInitiationService(Aspsp aspsp,
                                        HttpClient httpClient,
                                        List<Interceptor> interceptors) {
        this(aspsp, httpClient, interceptors, DEFAULT_LINKS_REWRITER, null);
    }

    public BasePaymentInitiationService(Aspsp aspsp,
                                        HttpClient httpClient,
                                        LinksRewriter linksRewriter) {
        this(aspsp, httpClient, Collections.emptyList(), linksRewriter, null);
    }

    public BasePaymentInitiationService(Aspsp aspsp,
                                        HttpClient httpClient,
                                        LinksRewriter linksRewriter,
                                        HttpLogSanitizer logSanitizer) {
        this(aspsp, httpClient, Collections.emptyList(), linksRewriter, logSanitizer);
    }

    public BasePaymentInitiationService(Aspsp aspsp,
                                        HttpClient httpClient,
                                        List<Interceptor> interceptors,
                                        LinksRewriter linksRewriter) {
        this(aspsp, httpClient, interceptors, linksRewriter, null);
    }

    public BasePaymentInitiationService(Aspsp aspsp,
                                        HttpClient httpClient,
                                        List<Interceptor> interceptors,
                                        LinksRewriter linksRewriter,
                                        HttpLogSanitizer logSanitizer) {
        super(httpClient);
        this.aspsp = aspsp;
        this.interceptors = Optional.ofNullable(interceptors).orElseGet(Collections::emptyList);
        this.linksRewriter = linksRewriter;
        this.responseHandlers = new ResponseHandlers(logSanitizer);
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
        return initiatePayment(paymentService, paymentProduct, body, requestHeaders, requestParams, mapper, responseHandlers.jsonResponseHandler(klass));
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

        Response<T> response = requestBuilder.send(responseHandler, interceptors);
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
            responseHandlers.jsonResponseHandler(PaymentInitiationWithStatusResponse.class));
    }

    protected  <T> Response<T> getPaymentInformation(PaymentService paymentService,
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
                   .send(responseHandler, interceptors);
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
            responseHandlers.jsonResponseHandler(klass))
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
            responseHandlers.multipartFormDataResponseHandler(klass))
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
            responseHandlers.stringResponseHandler());
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
            .send(responseHandlers.jsonResponseHandler(klass), interceptors)
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
            .send(responseHandlers.jsonResponseHandler(PaymentInitiationStatusResponse200Json.class), interceptors);
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
                   .send(responseHandlers.stringResponseHandler(), interceptors);
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
            .send(responseHandlers.jsonResponseHandler(klass), interceptors)
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
            .send(responseHandlers.jsonResponseHandler(klass), interceptors)
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
            .send(responseHandlers.jsonResponseHandler(klass), interceptors);
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
            .send(responseHandlers.jsonResponseHandler(klass), interceptors);
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
            .send(responseHandlers.jsonResponseHandler(klass), interceptors);
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
            .send(responseHandlers.jsonResponseHandler(klass), interceptors);

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
