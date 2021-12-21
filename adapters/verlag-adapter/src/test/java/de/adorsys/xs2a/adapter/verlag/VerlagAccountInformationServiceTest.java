package de.adorsys.xs2a.adapter.verlag;

import de.adorsys.xs2a.adapter.api.RequestHeaders;
import de.adorsys.xs2a.adapter.api.RequestParams;
import de.adorsys.xs2a.adapter.api.Response;
import de.adorsys.xs2a.adapter.api.ResponseHeaders;
import de.adorsys.xs2a.adapter.api.http.HttpClient;
import de.adorsys.xs2a.adapter.api.http.HttpClientConfig;
import de.adorsys.xs2a.adapter.api.http.HttpClientFactory;
import de.adorsys.xs2a.adapter.api.http.Request;
import de.adorsys.xs2a.adapter.api.link.LinksRewriter;
import de.adorsys.xs2a.adapter.api.model.Aspsp;
import de.adorsys.xs2a.adapter.api.model.OK200TransactionDetails;
import de.adorsys.xs2a.adapter.api.model.TransactionsResponse200Json;
import de.adorsys.xs2a.adapter.impl.http.RequestBuilderImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.util.AbstractMap;
import java.util.Map;
import java.util.stream.Stream;

import static de.adorsys.xs2a.adapter.api.http.ContentType.*;
import static java.util.Collections.singletonMap;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VerlagAccountInformationServiceTest {

    private static final String URI = "https://foo.boo";
    private static final String ACCOUNT_ID = "accountId";
    private static final String REMITTANCE_INFORMATION_STRUCTURED = "remittanceInformationStructuredStringValue";

    private VerlagAccountInformationService accountInformationService;
    @Mock
    private HttpClient httpClient;
    @Mock
    private Aspsp aspsp;
    @Mock
    private LinksRewriter linksRewriter;
    @Mock
    private AbstractMap.SimpleImmutableEntry<String, String> apiKey;
    @Mock
    private HttpClientFactory httpClientFactory;
    @Mock
    private HttpClientConfig httpClientConfig;

    @BeforeEach
    void setUp() {
        when(httpClientFactory.getHttpClient(any(), any(), any())).thenReturn(httpClient);
        when(httpClientFactory.getHttpClientConfig()).thenReturn(httpClientConfig);

        accountInformationService = new VerlagAccountInformationService(aspsp, apiKey, httpClientFactory, null, linksRewriter);
    }

    @Test
    void getTransactionList() {
        String rawResponse = "{\n" +
            "  \"transactions\": {\n" +
            "    \"booked\": [\n" +
            "      {\n" +
            "        \"remittanceInformationStructured\": {" +
            "           \"reference\": \"" + REMITTANCE_INFORMATION_STRUCTURED + "\"\n" +
            "         }\n" +
            "      }\n" +
            "    ]\n" +
            "  }\n" +
            "}";

        when(httpClient.get(anyString()))
            .thenReturn(new RequestBuilderImpl(httpClient, "GET", URI));
        when(httpClient.send(any(), any()))
            .thenAnswer(invocationOnMock -> {
                HttpClient.ResponseHandler responseHandler = invocationOnMock.getArgument(1, HttpClient.ResponseHandler.class);
                return new Response<>(-1,
                    responseHandler.apply(200,
                        new ByteArrayInputStream(rawResponse.getBytes()),
                        ResponseHeaders.emptyResponseHeaders()),
                    null);
            });

        Response<?> actualResponse
            = accountInformationService.getTransactionList(ACCOUNT_ID, RequestHeaders.empty(), RequestParams.empty());

        verify(httpClient, times(1)).get(anyString());
        verify(httpClient, times(1)).send(any(), any());

        assertNotNull(actualResponse);
        Object body = actualResponse.getBody();
        assertTrue(body instanceof TransactionsResponse200Json);
        String remittanceInformationStructured = ((TransactionsResponse200Json) body).getTransactions()
            .getBooked()
            .get(0)
            .getRemittanceInformationStructured();
        assertEquals(REMITTANCE_INFORMATION_STRUCTURED, remittanceInformationStructured);
    }

    @Test
    void getTransactionDetails() {
        String rawResponse = "{\n" +
            "  \"transactionsDetails\": {\n" +
            "    \"remittanceInformationStructured\": {" +
            "       \"reference\": \"" + REMITTANCE_INFORMATION_STRUCTURED + "\"\n" +
            "       }\n" +
            "  }\n" +
            "}";

        when(httpClient.get(anyString()))
            .thenReturn(new RequestBuilderImpl(httpClient, "GET", URI));
        when(httpClient.send(any(), any()))
            .thenAnswer(invocationOnMock -> {
                HttpClient.ResponseHandler responseHandler = invocationOnMock.getArgument(1, HttpClient.ResponseHandler.class);
                return new Response<>(-1,
                    responseHandler.apply(200,
                        new ByteArrayInputStream(rawResponse.getBytes()),
                        ResponseHeaders.emptyResponseHeaders()),
                    null);
            });

        Response<?> actualResponse
            = accountInformationService.getTransactionDetails(ACCOUNT_ID, "transactionId", RequestHeaders.empty(), RequestParams.empty());

        verify(httpClient, times(1)).get(anyString());
        verify(httpClient, times(1)).send(any(), any());

        assertNotNull(actualResponse);
        Object body = actualResponse.getBody();
        assertTrue(body instanceof OK200TransactionDetails);
        String remittanceInformationStructured = ((OK200TransactionDetails) body).getTransactionsDetails()
            .getRemittanceInformationStructured();
        assertEquals(REMITTANCE_INFORMATION_STRUCTURED, remittanceInformationStructured);
    }

    @ParameterizedTest
    @MethodSource("requestHeaders")
    @SuppressWarnings("unchecked")
    void getTransactionListAsString(RequestHeaders requestHeaders, String expectedValue) {
        Request.Builder requestBuilder = spy(new RequestBuilderImpl(httpClient, "GET", URI));
        ArgumentCaptor<Map<String, String>> headersCaptor = ArgumentCaptor.forClass(Map.class);

        when(httpClient.get(anyString())).thenReturn(requestBuilder);

        accountInformationService.getTransactionListAsString(ACCOUNT_ID, requestHeaders, RequestParams.empty());

        verify(requestBuilder, times(1)).headers(headersCaptor.capture());

        Map<String, String> actualHeaders = headersCaptor.getValue();

        assertFalse(actualHeaders.isEmpty());
        assertTrue(actualHeaders.containsValue(expectedValue));
    }

    private static Stream<Arguments> requestHeaders() {
        return Stream.of(arguments(RequestHeaders.empty(), APPLICATION_JSON),
            arguments(RequestHeaders.fromMap(singletonMap(RequestHeaders.ACCEPT, "")), APPLICATION_JSON),
            arguments(RequestHeaders.fromMap(singletonMap(RequestHeaders.ACCEPT, null)), APPLICATION_JSON),
            arguments(RequestHeaders.fromMap(singletonMap(RequestHeaders.ACCEPT, ALL)), APPLICATION_JSON),
            arguments(RequestHeaders.fromMap(singletonMap(RequestHeaders.ACCEPT, APPLICATION_XML)), APPLICATION_JSON),
            arguments(RequestHeaders.fromMap(singletonMap(RequestHeaders.ACCEPT, TEXT_PLAIN)), TEXT_PLAIN),
            arguments(RequestHeaders.fromMap(singletonMap(RequestHeaders.ACCEPT, APPLICATION_JSON)), APPLICATION_JSON),
            arguments(RequestHeaders.fromMap(singletonMap(RequestHeaders.ACCEPT, APPLICATION_XML + "," + ALL)), APPLICATION_JSON),
            arguments(RequestHeaders.fromMap(singletonMap(RequestHeaders.ACCEPT, APPLICATION_JSON + "," + TEXT_PLAIN)), APPLICATION_JSON),
            arguments(RequestHeaders.fromMap(singletonMap(RequestHeaders.ACCEPT, APPLICATION_XML + "," + TEXT_PLAIN)), APPLICATION_JSON)
        );

    }
}
