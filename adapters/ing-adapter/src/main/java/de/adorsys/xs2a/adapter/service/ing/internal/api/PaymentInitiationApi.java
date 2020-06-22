package de.adorsys.xs2a.adapter.service.ing.internal.api;

import de.adorsys.xs2a.adapter.http.*;
import de.adorsys.xs2a.adapter.service.RequestHeaders;
import de.adorsys.xs2a.adapter.service.Response;
import de.adorsys.xs2a.adapter.service.ing.internal.api.model.*;

import static java.util.Objects.requireNonNull;

// version: 1.1.12
public class PaymentInitiationApi {

    private static final String PAYMENTS = "/v1/payments";

    private final String baseUri;
    private final HttpClient httpClient;
    private final JsonMapper jsonMapper = new JsonMapper();

    public PaymentInitiationApi(String baseUri, HttpClient httpClient) {
        this.baseUri = baseUri;
        this.httpClient = httpClient;
    }

    public Response<PaymentInitiationResponse> initiatePayment(PaymentProduct paymentProduct,
                                                               String requestId,
                                                               String tppRedirectUri,
                                                               String psuIpAddress,
                                                               Request.Builder.Interceptor clientAuthentication,
                                                               PaymentInstruction body) {
        String uri = StringUri.fromElements(baseUri, PAYMENTS, requireNonNull(paymentProduct).toString());
        return httpClient.post(uri)
            .header(RequestHeaders.X_REQUEST_ID, requestId)
            .header(RequestHeaders.TPP_REDIRECT_URI, tppRedirectUri)
            .header(RequestHeaders.PSU_IP_ADDRESS, psuIpAddress)
            .jsonBody(jsonMapper.writeValueAsString(body))
            .send(clientAuthentication, ResponseHandlers.jsonResponseHandler(PaymentInitiationResponse.class));
    }

    public Response<PaymentInstruction> getPaymentDetails(PaymentProduct paymentProduct,
                                                          String paymentId,
                                                          String requestId,
                                                          String psuIpAddress,
                                                          ClientAuthentication clientAuthentication) {
        String uri = StringUri.fromElements(baseUri, PAYMENTS, requireNonNull(paymentProduct).toString(),
            requireNonNull(paymentId));
        return httpClient.get(uri)
            .header(RequestHeaders.X_REQUEST_ID, requestId)
            .header(RequestHeaders.PSU_IP_ADDRESS, psuIpAddress)
            .send(clientAuthentication, ResponseHandlers.jsonResponseHandler(PaymentInstruction.class));
    }

    public Response<PaymentStatusResponse> getPaymentStatus(PaymentProduct paymentProduct,
                                                            String paymentId,
                                                            String requestId,
                                                            String psuIpAddress,
                                                            ClientAuthentication clientAuthentication) {
        String uri = StringUri.fromElements(baseUri, PAYMENTS, requireNonNull(paymentProduct).toString(),
            requireNonNull(paymentId), "status");
        return httpClient.get(uri)
            .header(RequestHeaders.X_REQUEST_ID, requestId)
            .header(RequestHeaders.PSU_IP_ADDRESS, psuIpAddress)
            .send(clientAuthentication, ResponseHandlers.jsonResponseHandler(PaymentStatusResponse.class));
    }

    public Response<PaymentInitiationResponse> initiatePaymentXml(XmlPaymentProduct paymentProduct,
                                                                  String requestId,
                                                                  String tppRedirectUri,
                                                                  String psuIpAddress,
                                                                  Request.Builder.Interceptor clientAuthentication,
                                                                  String body) {
        String uri = StringUri.fromElements(baseUri, PAYMENTS, requireNonNull(paymentProduct).toString());
        return httpClient.post(uri)
            .header(RequestHeaders.X_REQUEST_ID, requestId)
            .header(RequestHeaders.TPP_REDIRECT_URI, tppRedirectUri)
            .header(RequestHeaders.PSU_IP_ADDRESS, psuIpAddress)
            .xmlBody(body)
            .send(clientAuthentication, ResponseHandlers.jsonResponseHandler(PaymentInitiationResponse.class));
    }


    public Response<String> getPaymentDetailsXml(XmlPaymentProduct paymentProduct,
                                                 String paymentId,
                                                 String requestId,
                                                 String psuIpAddress,
                                                 ClientAuthentication clientAuthentication) {
        String uri = StringUri.fromElements(baseUri, PAYMENTS, requireNonNull(paymentProduct).toString(),
            requireNonNull(paymentId));
        return httpClient.get(uri)
            .header(RequestHeaders.X_REQUEST_ID, requestId)
            .header(RequestHeaders.PSU_IP_ADDRESS, psuIpAddress)
            .send(clientAuthentication, ResponseHandlers.stringResponseHandler());
    }

    public Response<String> getPaymentStatusXml(XmlPaymentProduct paymentProduct,
                                                String paymentId,
                                                String requestId,
                                                String psuIpAddress,
                                                ClientAuthentication clientAuthentication) {
        String uri = StringUri.fromElements(baseUri, PAYMENTS, requireNonNull(paymentProduct).toString(),
            requireNonNull(paymentId), "status");
        return httpClient.get(uri)
            .header(RequestHeaders.X_REQUEST_ID, requestId)
            .header(RequestHeaders.PSU_IP_ADDRESS, psuIpAddress)
            .send(clientAuthentication, ResponseHandlers.stringResponseHandler());
    }
}
