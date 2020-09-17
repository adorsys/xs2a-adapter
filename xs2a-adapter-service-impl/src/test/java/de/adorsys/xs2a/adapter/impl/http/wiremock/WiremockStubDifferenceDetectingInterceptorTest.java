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
import de.adorsys.xs2a.adapter.api.model.AccountAccess;
import de.adorsys.xs2a.adapter.api.model.AccountReference;
import de.adorsys.xs2a.adapter.api.model.Aspsp;
import de.adorsys.xs2a.adapter.api.model.Consents;
import de.adorsys.xs2a.adapter.impl.http.RequestBuilderImpl;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class WiremockStubDifferenceDetectingInterceptorTest {

    public static final String X_REQUEST_ID = "some id";
    private final HttpClient httpClient = mock(HttpClient.class);
    private final Aspsp aspsp = getAspsp();
    private final Request.Builder.Interceptor interceptor = new WiremockStubDifferenceDetectingInterceptor(aspsp);

    @Test
    void apply() {
        Request.Builder builder = new RequestBuilderImpl(httpClient, null, null);
        assertEquals(builder, interceptor.apply(builder));
    }

    @Test
    void postHandle_allMatch() {
        RequestBuilderImpl request = new RequestBuilderImpl(httpClient, "GET", "/v1/accounts");
        request.header(RequestHeaders.X_REQUEST_ID, X_REQUEST_ID);
        request.header(RequestHeaders.CONSENT_ID, "consent id");

        Response<?> actualResponse = interceptor
            .postHandle(request, new Response<>(200, "", ResponseHeaders.emptyResponseHeaders()));

        assertFalse(actualResponse.getHeaders().getHeadersMap().containsKey(ResponseHeaders.X_ASPSP_CHANGES_DETECTED));
    }

    @Test
    void postHandle_notMatchingHeaders() throws JsonProcessingException {
        RequestBuilderImpl request = new RequestBuilderImpl(httpClient, "POST", "/v1/consents");
        request.jsonBody(writeValueAsString(getRequestBody()));

        Response<?> actualResponse = interceptor
            .postHandle(request, new Response<>(200, "", ResponseHeaders.emptyResponseHeaders()));

        assertTrue(actualResponse.getHeaders().getHeadersMap().containsKey(ResponseHeaders.X_ASPSP_CHANGES_DETECTED));
        assertTrue(actualResponse.getHeaders().getHeadersMap().get(ResponseHeaders.X_ASPSP_CHANGES_DETECTED).contains("request-headers"));
        assertFalse(actualResponse.getHeaders().getHeadersMap().get(ResponseHeaders.X_ASPSP_CHANGES_DETECTED).contains("request-payload"));
    }

    @Test
    void postHandle_notMatchingBody() throws JsonProcessingException {
        RequestBuilderImpl request = new RequestBuilderImpl(httpClient, "POST", "/v1/consents");
        request.jsonBody(writeValueAsString(new Consents()));
        request.header(RequestHeaders.X_REQUEST_ID, X_REQUEST_ID);
        request.header(RequestHeaders.PSU_ID, "max.musterman");
        request.header(RequestHeaders.CONTENT_TYPE, "application/json; charset=UTF-8");
        request.header(RequestHeaders.PSU_IP_ADDRESS, "0.0.0.0");
        request.header(RequestHeaders.TPP_REDIRECT_URI, "http://example.com");

        Response<?> actualResponse = interceptor
            .postHandle(request, new Response<>(200, "", ResponseHeaders.emptyResponseHeaders()));

        assertTrue(actualResponse.getHeaders().getHeadersMap().containsKey(ResponseHeaders.X_ASPSP_CHANGES_DETECTED));
        assertTrue(actualResponse.getHeaders().getHeadersMap().get(ResponseHeaders.X_ASPSP_CHANGES_DETECTED).contains("request-payload"));
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
