package de.adorsys.xs2a.adapter.ing;

import de.adorsys.xs2a.adapter.http.*;
import de.adorsys.xs2a.adapter.ing.model.*;
import de.adorsys.xs2a.adapter.service.RequestHeaders;
import de.adorsys.xs2a.adapter.service.Response;

import static java.util.Objects.requireNonNull;

// version: 1.1.12
public class IngPaymentInitiationApi {

    private static final String API_VERSION = "/v1/";

    private final String baseUri;
    private final HttpClient httpClient;
    private final JsonMapper jsonMapper = new JacksonObjectMapper();

    public IngPaymentInitiationApi(String baseUri, HttpClient httpClient) {
        this.baseUri = baseUri;
        this.httpClient = httpClient;
    }

    public Response<IngPaymentInitiationResponse> initiatePayment(String paymentService,
                                                                  IngPaymentProduct paymentProduct,
                                                                  String requestId,
                                                                  String tppRedirectUri,
                                                                  String psuIpAddress,
                                                                  Request.Builder.Interceptor clientAuthentication,
                                                                  IngPaymentInstruction body) {
        String uri = StringUri.fromElements(baseUri, API_VERSION, paymentService, requireNonNull(paymentProduct).toString());
        return httpClient.post(uri)
            .header(RequestHeaders.X_REQUEST_ID, requestId)
            .header(RequestHeaders.TPP_REDIRECT_URI, tppRedirectUri)
            .header(RequestHeaders.PSU_IP_ADDRESS, psuIpAddress)
            .jsonBody(jsonMapper.writeValueAsString(body))
            .send(clientAuthentication, ResponseHandlers.jsonResponseHandler(IngPaymentInitiationResponse.class));
    }

    public Response<IngPaymentInstruction> getPaymentDetails(String paymentService,
                                                             IngPaymentProduct paymentProduct,
                                                             String paymentId,
                                                             String requestId,
                                                             String psuIpAddress,
                                                             IngClientAuthentication clientAuthentication) {
        String uri = StringUri.fromElements(baseUri, API_VERSION, paymentService, requireNonNull(paymentProduct).toString(),
            requireNonNull(paymentId));
        return httpClient.get(uri)
            .header(RequestHeaders.X_REQUEST_ID, requestId)
            .header(RequestHeaders.PSU_IP_ADDRESS, psuIpAddress)
            .send(clientAuthentication, ResponseHandlers.jsonResponseHandler(IngPaymentInstruction.class));
    }

    public Response<IngPaymentStatusResponse> getPaymentStatus(String paymentService,
                                                               IngPaymentProduct paymentProduct,
                                                               String paymentId,
                                                               String requestId,
                                                               String psuIpAddress,
                                                               IngClientAuthentication clientAuthentication) {
        String uri = StringUri.fromElements(baseUri, API_VERSION, paymentService, requireNonNull(paymentProduct).toString(),
            requireNonNull(paymentId), "status");
        return httpClient.get(uri)
            .header(RequestHeaders.X_REQUEST_ID, requestId)
            .header(RequestHeaders.PSU_IP_ADDRESS, psuIpAddress)
            .send(clientAuthentication, ResponseHandlers.jsonResponseHandler(IngPaymentStatusResponse.class));
    }

    public Response<IngPaymentInitiationResponse> initiatePaymentXml(String paymentService,
                                                                     IngXmlPaymentProduct paymentProduct,
                                                                     String requestId,
                                                                     String tppRedirectUri,
                                                                     String psuIpAddress,
                                                                     Request.Builder.Interceptor clientAuthentication,
                                                                     String body) {
        String uri = StringUri.fromElements(baseUri, API_VERSION, paymentService, requireNonNull(paymentProduct).toString());
        return httpClient.post(uri)
            .header(RequestHeaders.X_REQUEST_ID, requestId)
            .header(RequestHeaders.TPP_REDIRECT_URI, tppRedirectUri)
            .header(RequestHeaders.PSU_IP_ADDRESS, psuIpAddress)
            .xmlBody(body)
            .send(clientAuthentication, ResponseHandlers.jsonResponseHandler(IngPaymentInitiationResponse.class));
    }


    public Response<String> getPaymentDetailsXml(String paymentService,
                                                 IngXmlPaymentProduct paymentProduct,
                                                 String paymentId,
                                                 String requestId,
                                                 String psuIpAddress,
                                                 IngClientAuthentication clientAuthentication) {
        String uri = StringUri.fromElements(baseUri, API_VERSION, paymentService, requireNonNull(paymentProduct).toString(),
            requireNonNull(paymentId));
        return httpClient.get(uri)
            .header(RequestHeaders.X_REQUEST_ID, requestId)
            .header(RequestHeaders.PSU_IP_ADDRESS, psuIpAddress)
            .send(clientAuthentication, ResponseHandlers.stringResponseHandler());
    }

    public Response<String> getPaymentStatusXml(String paymentService,
                                                IngXmlPaymentProduct paymentProduct,
                                                String paymentId,
                                                String requestId,
                                                String psuIpAddress,
                                                IngClientAuthentication clientAuthentication) {
        String uri = StringUri.fromElements(baseUri, API_VERSION, paymentService, requireNonNull(paymentProduct).toString(),
            requireNonNull(paymentId), "status");
        return httpClient.get(uri)
            .header(RequestHeaders.X_REQUEST_ID, requestId)
            .header(RequestHeaders.PSU_IP_ADDRESS, psuIpAddress)
            .send(clientAuthentication, ResponseHandlers.stringResponseHandler());
    }
}
