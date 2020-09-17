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
import de.adorsys.xs2a.adapter.api.http.Request;
import de.adorsys.xs2a.adapter.api.model.*;
import de.adorsys.xs2a.adapter.impl.http.RequestBuilderImpl;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class WiremockStubDifferenceDetectingInterceptorTest {

    private static final String X_REQUEST_ID_VALUE = "some-id";
    private static final String CONSENTS_URL = "https://bank.com/v1/consents";
    private static final String POST = "POST";
    private static final String REQUEST_HEADERS_VALUE = "request-headers";
    private static final String REQUEST_PAYLOAD_VALUE = "request-payload";
    private static final String RESPONSE_PAYLOAD_VALUE = "response-payload";
    private static final String PSU_ID_VALUE = "max.musterman";
    private static final String CONTENT_TYPE_VALUE = "application/json; charset=UTF-8";
    private static final String PSU_IP_ADDRESS_VALUE = "0.0.0.0";
    private static final String TPP_REDIRECT_URI_VALUE = "http://example.com";

    private final HttpClient httpClient = mock(HttpClient.class);
    private final Aspsp aspsp = getAspsp();
    private final Request.Builder.Interceptor interceptor = new WiremockStubDifferenceDetectingInterceptor(aspsp);

    @Test
    void apply() {
        Request.Builder builder = new RequestBuilderImpl(httpClient, null, null);
        assertThat(builder)
            .isEqualTo(interceptor.apply(builder));
    }

    @Test
    void postHandle_allMatch() {
        RequestBuilderImpl request = new RequestBuilderImpl(httpClient, "GET", "https://bank.com/v1/accounts");
        request.header(RequestHeaders.X_REQUEST_ID, X_REQUEST_ID_VALUE);
        request.header(RequestHeaders.CONSENT_ID, "consent-id");

        Response<?> actualResponse = interceptor
            .postHandle(request, new Response<>(200, "", ResponseHeaders.emptyResponseHeaders()));

        assertThat(actualResponse.getHeaders().getHeadersMap())
            .doesNotContainKey(ResponseHeaders.X_ASPSP_CHANGES_DETECTED);
    }

    @Test
    void postHandle_notMatchingHeaders() throws JsonProcessingException {
        RequestBuilderImpl request = new RequestBuilderImpl(httpClient, POST, CONSENTS_URL);
        request.jsonBody(writeValueAsString(getRequestBody()));

        Response<?> actualResponse = interceptor
            .postHandle(request, new Response<>(200, "", ResponseHeaders.emptyResponseHeaders()));

        assertThat(actualResponse.getHeaders().getHeadersMap())
            .containsKey(ResponseHeaders.X_ASPSP_CHANGES_DETECTED)
            .extractingByKey(ResponseHeaders.X_ASPSP_CHANGES_DETECTED)
            .matches(val -> val.contains(REQUEST_HEADERS_VALUE))
            .matches(val -> !val.contains(REQUEST_PAYLOAD_VALUE))
            .matches(val -> !val.contains(RESPONSE_PAYLOAD_VALUE));
    }

    @Test
    void postHandle_notMatchingRequestBody() throws JsonProcessingException {
        RequestBuilderImpl request = new RequestBuilderImpl(httpClient, POST, CONSENTS_URL);
        request.jsonBody(writeValueAsString(new Consents()));
        request.header(RequestHeaders.X_REQUEST_ID, X_REQUEST_ID_VALUE);
        request.header(RequestHeaders.PSU_ID, PSU_ID_VALUE);
        request.header(RequestHeaders.CONTENT_TYPE, CONTENT_TYPE_VALUE);
        request.header(RequestHeaders.PSU_IP_ADDRESS, PSU_IP_ADDRESS_VALUE);
        request.header(RequestHeaders.TPP_REDIRECT_URI, TPP_REDIRECT_URI_VALUE);

        Response<?> actualResponse = interceptor
            .postHandle(request, new Response<>(200, "", ResponseHeaders.emptyResponseHeaders()));

        assertThat(actualResponse.getHeaders().getHeadersMap())
            .containsKey(ResponseHeaders.X_ASPSP_CHANGES_DETECTED)
            .extractingByKey(ResponseHeaders.X_ASPSP_CHANGES_DETECTED)
            .matches(val -> val.contains(REQUEST_PAYLOAD_VALUE))
            .matches(val -> !val.contains(REQUEST_HEADERS_VALUE))
            .matches(val -> !val.contains(RESPONSE_PAYLOAD_VALUE));
    }

    @Test
    void postHandle_notMatchingResponseBody() throws JsonProcessingException {
        RequestBuilderImpl request = new RequestBuilderImpl(httpClient, POST, CONSENTS_URL);
        request.jsonBody(writeValueAsString(getRequestBody()));
        request.header(RequestHeaders.X_REQUEST_ID, X_REQUEST_ID_VALUE);
        request.header(RequestHeaders.PSU_ID, PSU_ID_VALUE);
        request.header(RequestHeaders.CONTENT_TYPE, CONTENT_TYPE_VALUE);
        request.header(RequestHeaders.PSU_IP_ADDRESS, PSU_IP_ADDRESS_VALUE);
        request.header(RequestHeaders.TPP_REDIRECT_URI, TPP_REDIRECT_URI_VALUE);

        Response<?> actualResponse = interceptor
            .postHandle(request, new Response<>(200, new ConsentsResponse201(), ResponseHeaders.emptyResponseHeaders()));

        assertThat(actualResponse.getHeaders().getHeadersMap())
            .containsKey(ResponseHeaders.X_ASPSP_CHANGES_DETECTED)
            .extractingByKey(ResponseHeaders.X_ASPSP_CHANGES_DETECTED)
            .matches(val -> val.contains(RESPONSE_PAYLOAD_VALUE))
            .matches(val -> !val.contains(REQUEST_PAYLOAD_VALUE))
            .matches(val -> !val.contains(REQUEST_HEADERS_VALUE));
    }

    @Test
    void postHandle_nothingMatches() throws JsonProcessingException {
        RequestBuilderImpl request = new RequestBuilderImpl(httpClient, POST, CONSENTS_URL);
        request.jsonBody(writeValueAsString(new Consents()));

        Response<?> actualResponse = interceptor
            .postHandle(request, new Response<>(200, new ConsentsResponse201(), ResponseHeaders.emptyResponseHeaders()));

        assertThat(actualResponse.getHeaders().getHeadersMap())
            .containsKey(ResponseHeaders.X_ASPSP_CHANGES_DETECTED)
            .extractingByKey(ResponseHeaders.X_ASPSP_CHANGES_DETECTED)
            .matches(val -> val.contains(RESPONSE_PAYLOAD_VALUE))
            .matches(val -> val.contains(REQUEST_PAYLOAD_VALUE))
            .matches(val -> val.contains(REQUEST_HEADERS_VALUE));
    }

    private Aspsp getAspsp() {
        Aspsp aspsp = new Aspsp();
        aspsp.setName("adorsys-adapter");
        return aspsp;
    }

    private Consents getRequestBody() {
        Consents requestBody = new Consents();
        requestBody.setAccess(getAccess());
        requestBody.setCombinedServiceIndicator(false);
        requestBody.setRecurringIndicator(true);
        requestBody.setValidUntil(LocalDate.now());
        requestBody.setFrequencyPerDay(4);
        return requestBody;
    }

    private AccountAccess getAccess() {
        AccountAccess accountAccess = new AccountAccess();
        accountAccess.setAccounts(singletonList(getAccountReference()));
        accountAccess.setBalances(singletonList(getAccountReference()));
        accountAccess.setTransactions(singletonList(getAccountReference()));
        return accountAccess;
    }

    private AccountReference getAccountReference() {
        AccountReference accountReference = new AccountReference();
        accountReference.setIban("IBAN");
        accountReference.setCurrency("EUR");
        return accountReference;
    }

    private <T> String writeValueAsString(T object) throws JsonProcessingException {
        return new ObjectMapper()
            .setSerializationInclusion(JsonInclude.Include.NON_NULL)
            .registerModule(new JavaTimeModule()
                .addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern("yyyyMMdd"))))
            .writeValueAsString(object);
    }
}
