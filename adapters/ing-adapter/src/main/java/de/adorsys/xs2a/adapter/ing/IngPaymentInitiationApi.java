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

package de.adorsys.xs2a.adapter.ing;

import de.adorsys.xs2a.adapter.api.RequestHeaders;
import de.adorsys.xs2a.adapter.api.Response;
import de.adorsys.xs2a.adapter.api.http.HttpClient;
import de.adorsys.xs2a.adapter.api.http.HttpLogSanitizer;
import de.adorsys.xs2a.adapter.api.http.Interceptor;
import de.adorsys.xs2a.adapter.impl.http.JacksonObjectMapper;
import de.adorsys.xs2a.adapter.impl.http.JsonMapper;
import de.adorsys.xs2a.adapter.impl.http.ResponseHandlers;
import de.adorsys.xs2a.adapter.impl.http.StringUri;
import de.adorsys.xs2a.adapter.ing.model.*;

import java.util.List;

import static java.util.Objects.requireNonNull;

// version: 1.1.12
public class IngPaymentInitiationApi {

    static final String PAYMENTS = "/v1/payments";
    static final String PERIODIC_PAYMENTS = "/v1/periodic-payments/";
    static final String STATUS = "status";

    private final String baseUri;
    private final HttpClient httpClient;
    private final ResponseHandlers responseHandlers;
    private final JsonMapper jsonMapper = new JacksonObjectMapper();

    public IngPaymentInitiationApi(String baseUri, HttpClient httpClient, HttpLogSanitizer logSanitizer) {
        this.baseUri = baseUri;
        this.httpClient = httpClient;
        this.responseHandlers = new ResponseHandlers(logSanitizer);
    }

    public Response<IngPaymentInitiationResponse> initiatePayment(IngPaymentProduct paymentProduct,
                                                                  String requestId,
                                                                  String tppRedirectUri,
                                                                  String psuIpAddress,
                                                                  List<Interceptor> interceptors,
                                                                  IngPaymentInstruction body) {
        String uri = StringUri.fromElements(baseUri, PAYMENTS, requireNonNull(paymentProduct).toString());
        return httpClient.post(uri)
                   .header(RequestHeaders.X_REQUEST_ID, requestId)
                   .header(RequestHeaders.TPP_REDIRECT_URI, tppRedirectUri)
                   .header(RequestHeaders.PSU_IP_ADDRESS, psuIpAddress)
                   .jsonBody(jsonMapper.writeValueAsString(body))
                   .send(responseHandlers.jsonResponseHandler(IngPaymentInitiationResponse.class), interceptors);
    }

    public Response<IngPaymentInstruction> getPaymentDetails(IngPaymentProduct paymentProduct,
                                                             String paymentId,
                                                             String requestId,
                                                             String psuIpAddress,
                                                             List<Interceptor> interceptors) {
        String uri = StringUri.fromElements(baseUri, PAYMENTS, requireNonNull(paymentProduct).toString(),
                                            requireNonNull(paymentId));
        return httpClient.get(uri)
                   .header(RequestHeaders.X_REQUEST_ID, requestId)
                   .header(RequestHeaders.PSU_IP_ADDRESS, psuIpAddress)
                   .send(responseHandlers.jsonResponseHandler(IngPaymentInstruction.class), interceptors);
    }

    public Response<IngPaymentStatusResponse> getPaymentStatus(IngPaymentProduct paymentProduct,
                                                               String paymentId,
                                                               String requestId,
                                                               String psuIpAddress,
                                                               List<Interceptor> interceptors) {
        String uri = StringUri.fromElements(baseUri, PAYMENTS, requireNonNull(paymentProduct).toString(),
                                            requireNonNull(paymentId), STATUS);
        return httpClient.get(uri)
                   .header(RequestHeaders.X_REQUEST_ID, requestId)
                   .header(RequestHeaders.PSU_IP_ADDRESS, psuIpAddress)
                   .send(responseHandlers.jsonResponseHandler(IngPaymentStatusResponse.class), interceptors);
    }

    public Response<IngPaymentInitiationResponse> initiatePaymentXml(IngXmlPaymentProduct paymentProduct,
                                                                     String requestId,
                                                                     String tppRedirectUri,
                                                                     String psuIpAddress,
                                                                     List<Interceptor> interceptors,
                                                                     String body) {
        String uri = StringUri.fromElements(baseUri, PAYMENTS, requireNonNull(paymentProduct).toString());
        return httpClient.post(uri)
                   .header(RequestHeaders.X_REQUEST_ID, requestId)
                   .header(RequestHeaders.TPP_REDIRECT_URI, tppRedirectUri)
                   .header(RequestHeaders.PSU_IP_ADDRESS, psuIpAddress)
                   .xmlBody(body)
                   .send(responseHandlers.jsonResponseHandler(IngPaymentInitiationResponse.class), interceptors);
    }

    public Response<String> getPaymentDetailsXml(IngXmlPaymentProduct paymentProduct,
                                                 String paymentId,
                                                 String requestId,
                                                 String psuIpAddress,
                                                 List<Interceptor> interceptors) {
        String uri = StringUri.fromElements(baseUri, PAYMENTS, requireNonNull(paymentProduct).toString(),
                                            requireNonNull(paymentId));
        return httpClient.get(uri)
                   .header(RequestHeaders.X_REQUEST_ID, requestId)
                   .header(RequestHeaders.PSU_IP_ADDRESS, psuIpAddress)
                   .send(responseHandlers.stringResponseHandler(), interceptors);
    }

    public Response<String> getPaymentStatusXml(IngXmlPaymentProduct paymentProduct,
                                                String paymentId,
                                                String requestId,
                                                String psuIpAddress,
                                                List<Interceptor> interceptors) {
        String uri = StringUri.fromElements(baseUri, PAYMENTS, requireNonNull(paymentProduct).toString(),
                                            requireNonNull(paymentId), STATUS);
        return httpClient.get(uri)
                   .header(RequestHeaders.X_REQUEST_ID, requestId)
                   .header(RequestHeaders.PSU_IP_ADDRESS, psuIpAddress)
                   .send(responseHandlers.stringResponseHandler(), interceptors);
    }

    public Response<IngPeriodicPaymentInitiationResponse> initiatePeriodicPayment(IngPaymentProduct paymentProduct,
                                                                                  String requestId,
                                                                                  String tppRedirectUri,
                                                                                  String psuIpAddress,
                                                                                  List<Interceptor> interceptors,
                                                                                  IngPeriodicPaymentInitiationJson body) {
        String uri = StringUri.fromElements(baseUri, PERIODIC_PAYMENTS, requireNonNull(paymentProduct).toString());
        return httpClient.post(uri)
                   .header(RequestHeaders.X_REQUEST_ID, requestId)
                   .header(RequestHeaders.TPP_REDIRECT_URI, tppRedirectUri)
                   .header(RequestHeaders.PSU_IP_ADDRESS, psuIpAddress)
                   .jsonBody(jsonMapper.writeValueAsString(body))
                   .send(responseHandlers.jsonResponseHandler(IngPeriodicPaymentInitiationResponse.class), interceptors);
    }

    public Response<IngPeriodicPaymentInitiationJson> getPeriodicPaymentDetails(IngPaymentProduct paymentProduct,
                                                                                String paymentId,
                                                                                String requestId,
                                                                                String psuIpAddress,
                                                                                List<Interceptor> interceptors) {
        String uri = StringUri.fromElements(baseUri, PERIODIC_PAYMENTS, requireNonNull(paymentProduct).toString(),
                                            requireNonNull(paymentId));
        return httpClient.get(uri)
                   .header(RequestHeaders.X_REQUEST_ID, requestId)
                   .header(RequestHeaders.PSU_IP_ADDRESS, psuIpAddress)
                   .send(responseHandlers.jsonResponseHandler(IngPeriodicPaymentInitiationJson.class), interceptors);
    }

    public Response<IngPaymentAgreementStatusResponse> getPeriodicPaymentStatus(IngPaymentProduct paymentProduct,
                                                                                String paymentId,
                                                                                String requestId,
                                                                                String psuIpAddress,
                                                                                List<Interceptor> interceptors) {
        String uri = StringUri.fromElements(baseUri, PERIODIC_PAYMENTS, requireNonNull(paymentProduct).toString(),
                                            requireNonNull(paymentId), STATUS);
        return httpClient.get(uri)
                   .header(RequestHeaders.X_REQUEST_ID, requestId)
                   .header(RequestHeaders.PSU_IP_ADDRESS, psuIpAddress)
                   .send(responseHandlers.jsonResponseHandler(IngPaymentAgreementStatusResponse.class), interceptors);
    }

    public Response<IngPeriodicPaymentInitiationResponse> initiatePeriodicPaymentXml(IngXmlPaymentProduct paymentProduct,
                                                                                     String requestId,
                                                                                     String tppRedirectUri,
                                                                                     String psuIpAddress,
                                                                                     List<Interceptor> interceptors,
                                                                                     IngPeriodicPaymentInitiationXml body) {
        String uri = StringUri.fromElements(baseUri, PERIODIC_PAYMENTS, requireNonNull(paymentProduct).toString());
        return httpClient.post(uri)
                   .header(RequestHeaders.X_REQUEST_ID, requestId)
                   .header(RequestHeaders.TPP_REDIRECT_URI, tppRedirectUri)
                   .header(RequestHeaders.PSU_IP_ADDRESS, psuIpAddress)
                   .addXmlPart("xml_sct", body.getXml_sct())
                   .addPlainTextPart("startDate", body.getStartDate())
                   .addPlainTextPart("endDate", body.getEndDate())
                   .addPlainTextPart("frequency", body.getFrequency())
                   .addPlainTextPart("dayOfExecution", body.getDayOfExecution())
                   .send(responseHandlers.jsonResponseHandler(IngPeriodicPaymentInitiationResponse.class), interceptors);
    }

    public Response<IngPeriodicPaymentInitiationXml> getPeriodicPaymentDetailsXml(IngXmlPaymentProduct paymentProduct,
                                                                                  String paymentId,
                                                                                  String requestId,
                                                                                  String psuIpAddress,
                                                                                  List<Interceptor> interceptors) {
        String uri = StringUri.fromElements(baseUri, PERIODIC_PAYMENTS, requireNonNull(paymentProduct).toString(),
                                            requireNonNull(paymentId));
        return httpClient.get(uri)
                   .header(RequestHeaders.X_REQUEST_ID, requestId)
                   .header(RequestHeaders.PSU_IP_ADDRESS, psuIpAddress)
                   .send(responseHandlers.multipartFormDataResponseHandler(IngPeriodicPaymentInitiationXml.class), interceptors);
    }

    public Response<String> getPeriodicPaymentStatusXml(IngXmlPaymentProduct paymentProduct,
                                                        String paymentId,
                                                        String requestId,
                                                        String psuIpAddress,
                                                        List<Interceptor> interceptors) {
        String uri = StringUri.fromElements(baseUri, PERIODIC_PAYMENTS, requireNonNull(paymentProduct).toString(),
                                            requireNonNull(paymentId), STATUS);
        return httpClient.get(uri)
                   .header(RequestHeaders.X_REQUEST_ID, requestId)
                   .header(RequestHeaders.PSU_IP_ADDRESS, psuIpAddress)
                   .send(responseHandlers.stringResponseHandler(), interceptors);
    }
}
