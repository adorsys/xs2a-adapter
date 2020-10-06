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
import de.adorsys.xs2a.adapter.api.model.*;
import de.adorsys.xs2a.adapter.impl.http.RequestBuilderImpl;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class WiremockStubDifferenceDetectingInterceptorTest {

    private static final String X_REQUEST_ID_VALUE = "some-id";
    private static final String CONSENT_ID_VALUE = "consent-id";
    private static final String CONSENTS_URL = "https://bank.com/v1/consents";
    private static final String POST = "POST";
    private static final String REQUEST_URL_VALUE = "url";
    private static final String REQUEST_HEADERS_VALUE = "request-headers";
    private static final String REQUEST_PAYLOAD_VALUE = "request-payload";
    private static final String RESPONSE_PAYLOAD_VALUE = "response-payload";
    private static final String PSU_ID_VALUE = "max.musterman";
    private static final String CONTENT_TYPE_VALUE = "application/json; charset=UTF-8";
    private static final String PSU_IP_ADDRESS_VALUE = "0.0.0.0";
    private static final String TPP_REDIRECT_URI_VALUE = "http://example.com";
    private static final String IBAN = "IBAN";
    private static final String CURRENCY = "EUR";
    private static final HrefType HREF_TYPE = getHrefType();

    private final HttpClient httpClient = mock(HttpClient.class);
    private final Aspsp aspsp = getAspsp();
    private final Interceptor interceptor = new WiremockStubDifferenceDetectingInterceptor(aspsp);

    @Test
    void postHandle_allMatch() throws JsonProcessingException {
        RequestBuilderImpl request = new RequestBuilderImpl(httpClient, "GET", "https://bank.com/v1/accounts");
        request.header(RequestHeaders.X_REQUEST_ID, X_REQUEST_ID_VALUE);
        request.header(RequestHeaders.CONSENT_ID, CONSENT_ID_VALUE);

        Response<?> actualResponse = interceptor
                                        .postHandle(request, getResponse(writeValueAsString(getAccountListBody())));

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
                                        .postHandle(request, getResponse(writeValueAsString(getAccountListBody())));

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
                                        .postHandle(request, getResponse(writeValueAsString(getAccountListBody())));

        assertThat(actualResponse.getHeaders().getHeadersMap())
            .containsKey(ResponseHeaders.X_GTW_ASPSP_CHANGES_DETECTED)
            .extractingByKey(ResponseHeaders.X_GTW_ASPSP_CHANGES_DETECTED)
            .matches(val -> val.contains(REQUEST_URL_VALUE));
    }

    private <T> Response<T> getResponse(T body) {
        return new Response<>(200, body, ResponseHeaders.emptyResponseHeaders());
    }

    @Test
    void postHandle_notMatchingHeaders() throws JsonProcessingException {
        RequestBuilderImpl request = new RequestBuilderImpl(httpClient, POST, CONSENTS_URL);
        request.jsonBody(writeValueAsString(getRequestBody()));

        Response<?> actualResponse = interceptor
                                         .postHandle(request, getResponse(writeValueAsString(getConsentResponse())));

        assertThat(actualResponse.getHeaders().getHeadersMap())
            .containsKey(ResponseHeaders.X_GTW_ASPSP_CHANGES_DETECTED)
            .extractingByKey(ResponseHeaders.X_GTW_ASPSP_CHANGES_DETECTED)
            .matches(val -> val.contains(REQUEST_HEADERS_VALUE))
            .matches(val -> !val.contains(REQUEST_PAYLOAD_VALUE))
            .matches(val -> !val.contains(RESPONSE_PAYLOAD_VALUE));
    }

    private ConsentsResponse201 getConsentResponse() {
        ConsentsResponse201 consentsResponse = new ConsentsResponse201();
        consentsResponse.setConsentStatus(ConsentStatus.VALID);
        consentsResponse.setConsentId(CONSENT_ID_VALUE);
        consentsResponse.setLinks(getLinks("updatePsuAuthentication", "self", "status", "scaStatus"));
        return consentsResponse;
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
                                         .postHandle(request, getResponse(writeValueAsString(getConsentResponse())));

        assertThat(actualResponse.getHeaders().getHeadersMap())
            .containsKey(ResponseHeaders.X_GTW_ASPSP_CHANGES_DETECTED)
            .extractingByKey(ResponseHeaders.X_GTW_ASPSP_CHANGES_DETECTED)
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
                                         .postHandle(request, getResponse(new ConsentsResponse201()));

        assertThat(actualResponse.getHeaders().getHeadersMap())
            .containsKey(ResponseHeaders.X_GTW_ASPSP_CHANGES_DETECTED)
            .extractingByKey(ResponseHeaders.X_GTW_ASPSP_CHANGES_DETECTED)
            .matches(val -> val.contains(RESPONSE_PAYLOAD_VALUE))
            .matches(val -> !val.contains(REQUEST_PAYLOAD_VALUE))
            .matches(val -> !val.contains(REQUEST_HEADERS_VALUE));
    }

    @Test
    void postHandle_nothingMatches() throws JsonProcessingException {
        RequestBuilderImpl request = new RequestBuilderImpl(httpClient, POST, CONSENTS_URL);
        request.jsonBody(writeValueAsString(new Consents()));

        Response<?> actualResponse = interceptor
                                         .postHandle(request, getResponse(""));

        assertThat(actualResponse.getHeaders().getHeadersMap())
            .containsKey(ResponseHeaders.X_GTW_ASPSP_CHANGES_DETECTED)
            .extractingByKey(ResponseHeaders.X_GTW_ASPSP_CHANGES_DETECTED)
            .matches(val -> val.contains(RESPONSE_PAYLOAD_VALUE))
            .matches(val -> val.contains(REQUEST_PAYLOAD_VALUE))
            .matches(val -> val.contains(REQUEST_HEADERS_VALUE));
    }

    private Aspsp getAspsp() {
        Aspsp aspsp = new Aspsp();
        aspsp.setAdapterId("adorsys-adapter");
        return aspsp;
    }

    private AccountList getAccountListBody() {
        AccountList accountList = new AccountList();
        accountList.setAccounts(Collections.singletonList(getAccountDetails()));
        return accountList;
    }

    private AccountDetails getAccountDetails() {
        AccountDetails details = new AccountDetails();
        details.setResourceId("some value");
        details.setIban(IBAN);
        details.setCurrency(CURRENCY);
        details.setName("user");
        details.setDisplayName("display user");
        details.setProduct("product");
        details.setCashAccountType("cash type");
        details.setStatus(AccountStatus.ENABLED);
        details.setLinkedAccounts("linked account");
        details.setUsage(AccountDetails.Usage.ORGA);
        details.setLinks(getLinks("balances", "transactions"));
        return details;
    }

    private Map<String, HrefType> getLinks(String... args) {
        if (args.length == 0) {
            return new HashMap<>();
        }

        Map<String, HrefType> links = new HashMap<>();
        for (String arg : args) {
            links.put(arg, HREF_TYPE);
        }
        return links;
    }

    private static HrefType getHrefType() {
        HrefType hrefType = new HrefType();
        hrefType.setHref("http://foo.boo");
        return hrefType;
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
        accountReference.setIban(IBAN);
        accountReference.setCurrency(CURRENCY);
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
