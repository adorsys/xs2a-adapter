package de.adorsys.xs2a.adapter.ing;

import de.adorsys.xs2a.adapter.http.HttpClient;
import de.adorsys.xs2a.adapter.http.Request;
import de.adorsys.xs2a.adapter.impl.http.JacksonObjectMapper;
import de.adorsys.xs2a.adapter.impl.http.JsonMapper;
import de.adorsys.xs2a.adapter.impl.http.ResponseHandlers;
import de.adorsys.xs2a.adapter.impl.http.StringUri;
import de.adorsys.xs2a.adapter.ing.model.*;
import de.adorsys.xs2a.adapter.service.RequestHeaders;
import de.adorsys.xs2a.adapter.service.Response;

import static java.util.Objects.requireNonNull;

// version: 1.1.12
public class IngPaymentInitiationApi {

    private static final String PAYMENTS = "/v1/payments";
    private static final String PERIODIC_PAYMENTS = "/v1/periodic-payments/";
    private static final String STATUS = "status";

    private final String baseUri;
    private final HttpClient httpClient;
    private final JsonMapper jsonMapper = new JacksonObjectMapper();

    public IngPaymentInitiationApi(String baseUri, HttpClient httpClient) {
        this.baseUri = baseUri;
        this.httpClient = httpClient;
    }

    public Response<IngPaymentInitiationResponse> initiatePayment(IngPaymentProduct paymentProduct,
                                                                  String requestId,
                                                                  String tppRedirectUri,
                                                                  String psuIpAddress,
                                                                  Request.Builder.Interceptor clientAuthentication,
                                                                  IngPaymentInstruction body) {
        String uri = StringUri.fromElements(baseUri, PAYMENTS, requireNonNull(paymentProduct).toString());
        return httpClient.post(uri)
            .header(RequestHeaders.X_REQUEST_ID, requestId)
            .header(RequestHeaders.TPP_REDIRECT_URI, tppRedirectUri)
            .header(RequestHeaders.PSU_IP_ADDRESS, psuIpAddress)
            .jsonBody(jsonMapper.writeValueAsString(body))
            .send(clientAuthentication, ResponseHandlers.jsonResponseHandler(IngPaymentInitiationResponse.class));
    }

    public Response<IngPaymentInstruction> getPaymentDetails(IngPaymentProduct paymentProduct,
                                                             String paymentId,
                                                             String requestId,
                                                             String psuIpAddress,
                                                             IngClientAuthentication clientAuthentication) {
        String uri = StringUri.fromElements(baseUri, PAYMENTS, requireNonNull(paymentProduct).toString(),
            requireNonNull(paymentId));
        return httpClient.get(uri)
            .header(RequestHeaders.X_REQUEST_ID, requestId)
            .header(RequestHeaders.PSU_IP_ADDRESS, psuIpAddress)
            .send(clientAuthentication, ResponseHandlers.jsonResponseHandler(IngPaymentInstruction.class));
    }

    public Response<IngPaymentStatusResponse> getPaymentStatus(IngPaymentProduct paymentProduct,
                                                               String paymentId,
                                                               String requestId,
                                                               String psuIpAddress,
                                                               IngClientAuthentication clientAuthentication) {
        String uri = StringUri.fromElements(baseUri, PAYMENTS, requireNonNull(paymentProduct).toString(),
            requireNonNull(paymentId), STATUS);
        return httpClient.get(uri)
            .header(RequestHeaders.X_REQUEST_ID, requestId)
            .header(RequestHeaders.PSU_IP_ADDRESS, psuIpAddress)
            .send(clientAuthentication, ResponseHandlers.jsonResponseHandler(IngPaymentStatusResponse.class));
    }

    public Response<IngPaymentInitiationResponse> initiatePaymentXml(IngXmlPaymentProduct paymentProduct,
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
            .send(clientAuthentication, ResponseHandlers.jsonResponseHandler(IngPaymentInitiationResponse.class));
    }


    public Response<String> getPaymentDetailsXml(IngXmlPaymentProduct paymentProduct,
                                                 String paymentId,
                                                 String requestId,
                                                 String psuIpAddress,
                                                 IngClientAuthentication clientAuthentication) {
        String uri = StringUri.fromElements(baseUri, PAYMENTS, requireNonNull(paymentProduct).toString(),
            requireNonNull(paymentId));
        return httpClient.get(uri)
            .header(RequestHeaders.X_REQUEST_ID, requestId)
            .header(RequestHeaders.PSU_IP_ADDRESS, psuIpAddress)
            .send(clientAuthentication, ResponseHandlers.stringResponseHandler());
    }

    public Response<String> getPaymentStatusXml(IngXmlPaymentProduct paymentProduct,
                                                String paymentId,
                                                String requestId,
                                                String psuIpAddress,
                                                IngClientAuthentication clientAuthentication) {
        String uri = StringUri.fromElements(baseUri, PAYMENTS, requireNonNull(paymentProduct).toString(),
            requireNonNull(paymentId), STATUS);
        return httpClient.get(uri)
            .header(RequestHeaders.X_REQUEST_ID, requestId)
            .header(RequestHeaders.PSU_IP_ADDRESS, psuIpAddress)
            .send(clientAuthentication, ResponseHandlers.stringResponseHandler());
    }

    public Response<IngPeriodicPaymentInitiationResponse> initiatePeriodicPayment(IngPaymentProduct paymentProduct,
                                                                                  String requestId,
                                                                                  String tppRedirectUri,
                                                                                  String psuIpAddress,
                                                                                  Request.Builder.Interceptor clientAuthentication,
                                                                                  IngPeriodicPaymentInitiationJson body) {
        String uri = StringUri.fromElements(baseUri, PERIODIC_PAYMENTS, requireNonNull(paymentProduct).toString());
        return httpClient.post(uri)
            .header(RequestHeaders.X_REQUEST_ID, requestId)
            .header(RequestHeaders.TPP_REDIRECT_URI, tppRedirectUri)
            .header(RequestHeaders.PSU_IP_ADDRESS, psuIpAddress)
            .jsonBody(jsonMapper.writeValueAsString(body))
            .send(clientAuthentication, ResponseHandlers.jsonResponseHandler(IngPeriodicPaymentInitiationResponse.class));
    }

    public Response<IngPeriodicPaymentInitiationJson> getPeriodicPaymentDetails(IngPaymentProduct paymentProduct,
                                                                                String paymentId,
                                                                                String requestId,
                                                                                String psuIpAddress,
                                                                                IngClientAuthentication clientAuthentication) {
        String uri = StringUri.fromElements(baseUri, PERIODIC_PAYMENTS, requireNonNull(paymentProduct).toString(),
            requireNonNull(paymentId));
        return httpClient.get(uri)
            .header(RequestHeaders.X_REQUEST_ID, requestId)
            .header(RequestHeaders.PSU_IP_ADDRESS, psuIpAddress)
            .send(clientAuthentication, ResponseHandlers.jsonResponseHandler(IngPeriodicPaymentInitiationJson.class));
    }

    public Response<IngPaymentAgreementStatusResponse> getPeriodicPaymentStatus(IngPaymentProduct paymentProduct,
                                                                                String paymentId,
                                                                                String requestId,
                                                                                String psuIpAddress,
                                                                                IngClientAuthentication clientAuthentication) {
        String uri = StringUri.fromElements(baseUri, PERIODIC_PAYMENTS, requireNonNull(paymentProduct).toString(),
            requireNonNull(paymentId), STATUS);
        return httpClient.get(uri)
            .header(RequestHeaders.X_REQUEST_ID, requestId)
            .header(RequestHeaders.PSU_IP_ADDRESS, psuIpAddress)
            .send(clientAuthentication, ResponseHandlers.jsonResponseHandler(IngPaymentAgreementStatusResponse.class));
    }

    public Response<IngPeriodicPaymentInitiationResponse> initiatePeriodicPaymentXml(IngXmlPaymentProduct paymentProduct,
                                                                                     String requestId,
                                                                                     String tppRedirectUri,
                                                                                     String psuIpAddress,
                                                                                     IngClientAuthentication clientAuthentication,
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
            .send(clientAuthentication, ResponseHandlers.jsonResponseHandler(IngPeriodicPaymentInitiationResponse.class));
    }

    public Response<IngPeriodicPaymentInitiationXml> getPeriodicPaymentDetailsXml(IngXmlPaymentProduct paymentProduct,
                                                                                  String paymentId,
                                                                                  String requestId,
                                                                                  String psuIpAddress,
                                                                                  IngClientAuthentication clientAuthentication) {
        String uri = StringUri.fromElements(baseUri, PERIODIC_PAYMENTS, requireNonNull(paymentProduct).toString(),
            requireNonNull(paymentId));
        return httpClient.get(uri)
            .header(RequestHeaders.X_REQUEST_ID, requestId)
            .header(RequestHeaders.PSU_IP_ADDRESS, psuIpAddress)
            .send(clientAuthentication, ResponseHandlers.multipartFormDataResponseHandler(IngPeriodicPaymentInitiationXml.class));
    }

    public Response<String> getPeriodicPaymentStatusXml(IngXmlPaymentProduct paymentProduct,
                                                       String paymentId,
                                                       String requestId,
                                                       String psuIpAddress,
                                                       IngClientAuthentication clientAuthentication) {
        String uri = StringUri.fromElements(baseUri, PERIODIC_PAYMENTS, requireNonNull(paymentProduct).toString(),
            requireNonNull(paymentId), STATUS);
        return httpClient.get(uri)
            .header(RequestHeaders.X_REQUEST_ID, requestId)
            .header(RequestHeaders.PSU_IP_ADDRESS, psuIpAddress)
            .send(clientAuthentication, ResponseHandlers.stringResponseHandler());
    }
}
