package de.adorsys.xs2a.adapter.impl.http.wiremock;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import de.adorsys.xs2a.adapter.api.RequestHeaders;
import de.adorsys.xs2a.adapter.api.Response;
import de.adorsys.xs2a.adapter.api.ResponseHeaders;
import de.adorsys.xs2a.adapter.api.http.HttpClient;
import de.adorsys.xs2a.adapter.api.http.Interceptor;
import de.adorsys.xs2a.adapter.api.model.Aspsp;
import de.adorsys.xs2a.adapter.api.model.Consents;
import de.adorsys.xs2a.adapter.api.model.ConsentsResponse201;
import de.adorsys.xs2a.adapter.impl.http.RequestBuilderImpl;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

import static de.adorsys.xs2a.adapter.impl.http.wiremock.TestInstancesSupplier.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class WiremockStubDifferenceDetectingInterceptorTest {

    private static final String X_REQUEST_ID_VALUE = "some-id";
    private static final String CONSENTS_URL = "https://bank.com/v1/consents";
    private static final String INITIATE_PAYMENT_PAYMENTS_PAIN_URL = "https://bank.com/v1/payments/pain.001-sepa-credit-transfers";
    private static final String INITIATE_PAYMENT_PERIODIC_PAIN_URL = "https://bank.com/v1/periodic-payments/pain.001-sepa-credit-transfers";
    private static final String POST = "POST";
    private static final String GET = "GET";
    private static final String REQUEST_URL_VALUE = "url";
    private static final String REQUEST_HEADERS_VALUE = "request-headers";
    private static final String REQUEST_PAYLOAD_VALUE = "request-payload";
    private static final String RESPONSE_PAYLOAD_VALUE = "response-payload";
    private static final String PSU_ID_VALUE = "max.musterman";
    private static final String CONTENT_TYPE_JSON_VALUE = "application/json; charset=UTF-8";
    private static final String CONTENT_TYPE_XML_VALUE = "application/xml; charset=ISO-8859-1";
    private static final String CONTENT_TYPE_MULTIPART_VALUE = "multipart/form-data; boundary=123456";
    private static final String PSU_IP_ADDRESS_VALUE = "0.0.0.0";
    private static final String TPP_REDIRECT_URI_VALUE = "http://example.com";
    private static final String XML_BODY = "<test></test>";
    private static final String PAYMENT_INITIATION_BODY_FILE = "payment-initiation-body.xml";

    private static final Logger logger = LoggerFactory.getLogger(WiremockStubDifferenceDetectingInterceptorTest.class);

    private final HttpClient httpClient = mock(HttpClient.class);
    private final Aspsp aspsp = getAspsp();
    private final Interceptor interceptor = new WiremockStubDifferenceDetectingInterceptor(aspsp);

    @Test
    void postHandle_allMatch() throws JsonProcessingException {
        RequestBuilderImpl request = new RequestBuilderImpl(httpClient, GET, "https://bank.com/v1/accounts");
        request.header(RequestHeaders.X_REQUEST_ID, X_REQUEST_ID_VALUE);
        request.header(RequestHeaders.CONSENT_ID, "consent-id");

        Response<?> actualResponse = interceptor
                                         .postHandle(request,
                                                     getResponse(writeValueAsString(getAccountList()),
                                                                 getResponseHeaders(ResponseHeaders.CONTENT_TYPE, CONTENT_TYPE_JSON_VALUE)));

        assertThat(actualResponse.getHeaders().getHeadersMap())
            .doesNotContainKey(ResponseHeaders.X_GTW_ASPSP_CHANGES_DETECTED);
    }

    @Test
    void postHandle_ContentTypeNotMatching() throws JsonProcessingException {
        RequestBuilderImpl request = new RequestBuilderImpl(httpClient, POST, CONSENTS_URL);
        request.header(RequestHeaders.X_REQUEST_ID, X_REQUEST_ID_VALUE);
        request.header(RequestHeaders.CONTENT_TYPE, "application/json");
        request.header(RequestHeaders.PSU_ID, PSU_ID_VALUE);
        request.header(RequestHeaders.PSU_IP_ADDRESS, PSU_IP_ADDRESS_VALUE);
        request.header(RequestHeaders.TPP_REDIRECT_URI, TPP_REDIRECT_URI_VALUE);

        Response<?> actualResponse = interceptor
                                         .postHandle(request,
                                                     getResponse(writeValueAsString(getAccountList()),
                                                                 getResponseHeaders(ResponseHeaders.CONTENT_TYPE, CONTENT_TYPE_JSON_VALUE)));

        assertThat(actualResponse.getHeaders().getHeadersMap())
            .containsKey(ResponseHeaders.X_GTW_ASPSP_CHANGES_DETECTED)
            .extractingByKey(ResponseHeaders.X_GTW_ASPSP_CHANGES_DETECTED)
            .matches(val -> val.contains(REQUEST_HEADERS_VALUE));
    }

    @Test
    void postHandle_ParamsNotMatching() throws JsonProcessingException {
        RequestBuilderImpl request = new RequestBuilderImpl(httpClient, "GET", "/v1/accounts/123123/transactions?dateFrom=1990-01-01&dateTo=1990-12-31d&bookingStatus=booked&withBalance=false");
        request.header(RequestHeaders.X_REQUEST_ID, X_REQUEST_ID_VALUE);
        request.header(RequestHeaders.CONTENT_TYPE, "application/json");
        request.header(RequestHeaders.PSU_ID, PSU_ID_VALUE);
        request.header(RequestHeaders.PSU_IP_ADDRESS, PSU_IP_ADDRESS_VALUE);
        request.header(RequestHeaders.TPP_REDIRECT_URI, TPP_REDIRECT_URI_VALUE);

        Response<?> actualResponse = interceptor
                                         .postHandle(request, getResponse(writeValueAsString(getAccountList()),
                                                                          getResponseHeaders(ResponseHeaders.CONTENT_TYPE, CONTENT_TYPE_JSON_VALUE)));

        assertThat(actualResponse.getHeaders().getHeadersMap())
            .containsKey(ResponseHeaders.X_GTW_ASPSP_CHANGES_DETECTED)
            .extractingByKey(ResponseHeaders.X_GTW_ASPSP_CHANGES_DETECTED)
            .matches(val -> val.contains(REQUEST_URL_VALUE));
    }

    @Test
    void postHandle_notMatchingHeaders() throws JsonProcessingException {
        RequestBuilderImpl request = new RequestBuilderImpl(httpClient, POST, CONSENTS_URL);
        request.jsonBody(writeValueAsString(getConsents()));
        request.header(ResponseHeaders.CONTENT_TYPE, CONTENT_TYPE_JSON_VALUE);

        Response<?> actualResponse = interceptor
                                         .postHandle(request,
                                                     getResponse(writeValueAsString(getConsentResponse201()),
                                                                 getResponseHeaders(ResponseHeaders.CONTENT_TYPE, CONTENT_TYPE_JSON_VALUE)));

        assertThat(actualResponse.getHeaders().getHeadersMap())
            .containsKey(ResponseHeaders.X_GTW_ASPSP_CHANGES_DETECTED)
            .extractingByKey(ResponseHeaders.X_GTW_ASPSP_CHANGES_DETECTED)
            .matches(val -> val.contains(REQUEST_HEADERS_VALUE))
            .matches(val -> !val.contains(REQUEST_PAYLOAD_VALUE))
            .matches(val -> !val.contains(RESPONSE_PAYLOAD_VALUE));
    }

    @Test
    void postHandle_jsonBody_notMatchingRequestBody() throws JsonProcessingException {
        RequestBuilderImpl request = new RequestBuilderImpl(httpClient, POST, CONSENTS_URL);
        request.jsonBody(writeValueAsString(new Consents()));
        request.header(RequestHeaders.X_REQUEST_ID, X_REQUEST_ID_VALUE);
        request.header(RequestHeaders.PSU_ID, PSU_ID_VALUE);
        request.header(RequestHeaders.CONTENT_TYPE, CONTENT_TYPE_JSON_VALUE);
        request.header(RequestHeaders.PSU_IP_ADDRESS, PSU_IP_ADDRESS_VALUE);
        request.header(RequestHeaders.TPP_REDIRECT_URI, TPP_REDIRECT_URI_VALUE);

        Response<?> actualResponse = interceptor
                                         .postHandle(request,
                                                     getResponse(writeValueAsString(getConsentResponse201()),
                                                                 getResponseHeaders(ResponseHeaders.CONTENT_TYPE, CONTENT_TYPE_JSON_VALUE)));

        assertThat(actualResponse.getHeaders().getHeadersMap())
            .containsKey(ResponseHeaders.X_GTW_ASPSP_CHANGES_DETECTED)
            .extractingByKey(ResponseHeaders.X_GTW_ASPSP_CHANGES_DETECTED)
            .matches(val -> val.contains(REQUEST_PAYLOAD_VALUE))
            .matches(val -> !val.contains(REQUEST_HEADERS_VALUE))
            .matches(val -> !val.contains(RESPONSE_PAYLOAD_VALUE));
    }

    @Test
    void postHandle_jsonBody_notMatchingResponseBody() throws JsonProcessingException {
        RequestBuilderImpl request = new RequestBuilderImpl(httpClient, POST, CONSENTS_URL);
        request.jsonBody(writeValueAsString(getConsents()));
        request.header(RequestHeaders.X_REQUEST_ID, X_REQUEST_ID_VALUE);
        request.header(RequestHeaders.PSU_ID, PSU_ID_VALUE);
        request.header(RequestHeaders.CONTENT_TYPE, CONTENT_TYPE_JSON_VALUE);
        request.header(RequestHeaders.PSU_IP_ADDRESS, PSU_IP_ADDRESS_VALUE);
        request.header(RequestHeaders.TPP_REDIRECT_URI, TPP_REDIRECT_URI_VALUE);

        Response<?> actualResponse = interceptor
                                         .postHandle(request,
                                                     getResponse(new ConsentsResponse201(),
                                                                 getResponseHeaders(ResponseHeaders.CONTENT_TYPE, CONTENT_TYPE_JSON_VALUE)));

        assertThat(actualResponse.getHeaders().getHeadersMap())
            .containsKey(ResponseHeaders.X_GTW_ASPSP_CHANGES_DETECTED)
            .extractingByKey(ResponseHeaders.X_GTW_ASPSP_CHANGES_DETECTED)
            .matches(val -> val.contains(RESPONSE_PAYLOAD_VALUE))
            .matches(val -> !val.contains(REQUEST_PAYLOAD_VALUE))
            .matches(val -> !val.contains(REQUEST_HEADERS_VALUE));
    }

    @Test
    void postHandle_jsonBody_notMatchingRequestResponseBodyValues() throws JsonProcessingException {
        Consents requestBody = getConsents();
        requestBody.setFrequencyPerDay(1);
        ConsentsResponse201 responseBody = getConsentResponse201();
        responseBody.setConsentId("wrong_id");

        RequestBuilderImpl request = new RequestBuilderImpl(httpClient, POST, CONSENTS_URL);
        request.jsonBody(writeValueAsString(requestBody));
        request.header(RequestHeaders.X_REQUEST_ID, X_REQUEST_ID_VALUE);
        request.header(RequestHeaders.PSU_ID, PSU_ID_VALUE);
        request.header(RequestHeaders.CONTENT_TYPE, CONTENT_TYPE_JSON_VALUE);
        request.header(RequestHeaders.PSU_IP_ADDRESS, PSU_IP_ADDRESS_VALUE);
        request.header(RequestHeaders.TPP_REDIRECT_URI, TPP_REDIRECT_URI_VALUE);

        Response<?> actualResponse = interceptor
                                         .postHandle(request,
                                                     getResponse(writeValueAsString(responseBody),
                                                                 getResponseHeaders(ResponseHeaders.CONTENT_TYPE, CONTENT_TYPE_JSON_VALUE)));

        assertThat(actualResponse.getHeaders().getHeadersMap())
            .containsKey(ResponseHeaders.X_GTW_ASPSP_CHANGES_DETECTED)
            .extractingByKey(ResponseHeaders.X_GTW_ASPSP_CHANGES_DETECTED)
            .matches(val -> val.contains(RESPONSE_PAYLOAD_VALUE))
            .matches(val -> val.contains(REQUEST_PAYLOAD_VALUE))
            .matches(val -> !val.contains(REQUEST_HEADERS_VALUE));
    }

    @Test
    void postHandle_nothingMatches() throws JsonProcessingException {
        RequestBuilderImpl request = new RequestBuilderImpl(httpClient, POST, CONSENTS_URL);
        request.jsonBody(writeValueAsString(new Consents()));
        request.header(ResponseHeaders.CONTENT_TYPE, CONTENT_TYPE_JSON_VALUE);

        Response<?> actualResponse = interceptor
                                         .postHandle(request,
                                                     getResponse("",
                                                                 getResponseHeaders(ResponseHeaders.CONTENT_TYPE, CONTENT_TYPE_JSON_VALUE)));

        assertThat(actualResponse.getHeaders().getHeadersMap())
            .containsKey(ResponseHeaders.X_GTW_ASPSP_CHANGES_DETECTED)
            .extractingByKey(ResponseHeaders.X_GTW_ASPSP_CHANGES_DETECTED)
            .matches(val -> val.contains(RESPONSE_PAYLOAD_VALUE))
            .matches(val -> val.contains(REQUEST_PAYLOAD_VALUE))
            .matches(val -> val.contains(REQUEST_HEADERS_VALUE));
    }

    @Test
    void postHandle_xmlBody_allMatches() {
        RequestBuilderImpl request
            = new RequestBuilderImpl(httpClient, POST, INITIATE_PAYMENT_PAYMENTS_PAIN_URL);
        String body = getXmlAsString(PAYMENT_INITIATION_BODY_FILE);
        request.xmlBody(body);
        request.header(RequestHeaders.X_REQUEST_ID, X_REQUEST_ID_VALUE);
        request.header(RequestHeaders.PSU_ID, PSU_ID_VALUE);
        request.header(RequestHeaders.CONTENT_TYPE, CONTENT_TYPE_XML_VALUE);
        request.header(RequestHeaders.PSU_IP_ADDRESS, PSU_IP_ADDRESS_VALUE);
        request.header(RequestHeaders.TPP_REDIRECT_URI, TPP_REDIRECT_URI_VALUE);

        Response<?> actualResponse = interceptor
                                         .postHandle(request,
                                                     getResponse(getPaymentInitiationRequestResponse201(),
                                                                 getResponseHeaders(ResponseHeaders.CONTENT_TYPE, CONTENT_TYPE_JSON_VALUE)));

        assertThat(actualResponse.getHeaders().getHeadersMap())
            .doesNotContainKey(ResponseHeaders.X_GTW_ASPSP_CHANGES_DETECTED);
    }

    @Test
    void postHandle_xmlBody_notMatchingResponseBody() {
        RequestBuilderImpl request
            = new RequestBuilderImpl(httpClient, GET, "https://bank.com/v1/payments/pain.001-sepa-credit-transfers/foo/status");
        request.header(RequestHeaders.X_REQUEST_ID, X_REQUEST_ID_VALUE);
        request.header(RequestHeaders.DIGEST, "digest");
        request.header(RequestHeaders.DATE, "date");
        request.header(RequestHeaders.SIGNATURE, "signature");
        request.header(RequestHeaders.TPP_SIGNATURE_CERTIFICATE, "certificate");

        Response<?> actualResponse = interceptor
                                         .postHandle(request,
                                                     getResponse(XML_BODY,
                                                                 getResponseHeaders(ResponseHeaders.CONTENT_TYPE, CONTENT_TYPE_XML_VALUE)));

        assertThat(actualResponse.getHeaders().getHeadersMap())
            .containsKey(ResponseHeaders.X_GTW_ASPSP_CHANGES_DETECTED)
            .extractingByKey(ResponseHeaders.X_GTW_ASPSP_CHANGES_DETECTED)
            .matches(val -> val.contains(RESPONSE_PAYLOAD_VALUE))
            .matches(val -> !val.contains(REQUEST_PAYLOAD_VALUE))
            .matches(val -> !val.contains(REQUEST_HEADERS_VALUE));
    }

    @Test
    void postHandle_xmlBody_notMatchingRequestBody() {
        RequestBuilderImpl request
            = new RequestBuilderImpl(httpClient, POST, INITIATE_PAYMENT_PAYMENTS_PAIN_URL);
        request.xmlBody(XML_BODY);
        request.header(RequestHeaders.X_REQUEST_ID, X_REQUEST_ID_VALUE);
        request.header(RequestHeaders.PSU_ID, PSU_ID_VALUE);
        request.header(RequestHeaders.CONTENT_TYPE, CONTENT_TYPE_XML_VALUE);
        request.header(RequestHeaders.PSU_IP_ADDRESS, PSU_IP_ADDRESS_VALUE);
        request.header(RequestHeaders.TPP_REDIRECT_URI, TPP_REDIRECT_URI_VALUE);

        Response<?> actualResponse = interceptor
                                         .postHandle(request,
                                                     getResponse(getPaymentInitiationRequestResponse201(),
                                                                 getResponseHeaders(ResponseHeaders.CONTENT_TYPE, CONTENT_TYPE_JSON_VALUE)));

        assertThat(actualResponse.getHeaders().getHeadersMap())
            .containsKey(ResponseHeaders.X_GTW_ASPSP_CHANGES_DETECTED)
            .extractingByKey(ResponseHeaders.X_GTW_ASPSP_CHANGES_DETECTED)
            .matches(val -> val.contains(REQUEST_PAYLOAD_VALUE))
            .matches(val -> !val.contains(REQUEST_HEADERS_VALUE))
            .matches(val -> !val.contains(RESPONSE_PAYLOAD_VALUE));
    }

    @Test
    void postHandle_multipartBody_allMatches() throws JsonProcessingException {
        RequestBuilderImpl request
            = new RequestBuilderImpl(httpClient, POST, INITIATE_PAYMENT_PERIODIC_PAIN_URL);
        String body = getXmlAsString(PAYMENT_INITIATION_BODY_FILE);
        request.addXmlPart("xml_sct", body);
        request.addJsonPart("json_standingorderType",
            writeValueAsString(getPeriodicPaymentInitiationXmlPart2StandingorderTypeJson()));
        request.header(RequestHeaders.X_REQUEST_ID, X_REQUEST_ID_VALUE);
        request.header(RequestHeaders.PSU_ID, PSU_ID_VALUE);
        request.header(RequestHeaders.CONTENT_TYPE, CONTENT_TYPE_MULTIPART_VALUE);

        Response<?> actualResponse = interceptor
            .postHandle(request,
                getResponse(getPaymentInitiationRequestResponse201(),
                    getResponseHeaders(ResponseHeaders.CONTENT_TYPE, CONTENT_TYPE_JSON_VALUE)));

        assertThat(actualResponse.getHeaders().getHeadersMap())
            .doesNotContainKey(ResponseHeaders.X_GTW_ASPSP_CHANGES_DETECTED);
    }

    @Test
    void postHandle_multipartBody_notMatchingMultipartBody() {
        RequestBuilderImpl request
            = new RequestBuilderImpl(httpClient, POST, INITIATE_PAYMENT_PERIODIC_PAIN_URL);
        request.addXmlPart("xml_fake", XML_BODY);
        request.addJsonPart("json_standingorderFake", "{}");
        request.header(RequestHeaders.X_REQUEST_ID, X_REQUEST_ID_VALUE);
        request.header(RequestHeaders.PSU_ID, PSU_ID_VALUE);
        request.header(RequestHeaders.CONTENT_TYPE, CONTENT_TYPE_MULTIPART_VALUE);

        Response<?> actualResponse = interceptor
            .postHandle(request,
                getResponse(getPaymentInitiationRequestResponse201(),
                    getResponseHeaders(ResponseHeaders.CONTENT_TYPE, CONTENT_TYPE_JSON_VALUE)));

        assertThat(actualResponse.getHeaders().getHeadersMap())
            .containsKey(ResponseHeaders.X_GTW_ASPSP_CHANGES_DETECTED)
            .extractingByKey(ResponseHeaders.X_GTW_ASPSP_CHANGES_DETECTED)
            .matches(val -> val.contains(REQUEST_PAYLOAD_VALUE))
            .matches(val -> !val.contains(REQUEST_HEADERS_VALUE))
            .matches(val -> !val.contains(RESPONSE_PAYLOAD_VALUE));
    }

    @Test
    void isWiremockSupported() {
        assertThat(WiremockStubDifferenceDetectingInterceptor.isWiremockSupported("adorsys-adapter"))
            .isTrue();
        assertThat(WiremockStubDifferenceDetectingInterceptor.isWiremockSupported("foo-adapter"))
            .isFalse();
    }

    private String getXmlAsString(String path) {
        String xml = null;
        try {
            URI uri = getClass().getClassLoader().getResource(path).toURI();
            xml = Files.lines(Paths.get(uri)).collect(Collectors.joining());
        } catch (IOException | URISyntaxException e) {
            logger.error("Failed to read xml file", e);
        }
        return xml;
    }

    private <T> String writeValueAsString(T object) throws JsonProcessingException {
        return new ObjectMapper()
                   .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                   .registerModule(new JavaTimeModule()
                                       .addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern("yyyyMMdd"))))
                   .writeValueAsString(object);
    }
}
