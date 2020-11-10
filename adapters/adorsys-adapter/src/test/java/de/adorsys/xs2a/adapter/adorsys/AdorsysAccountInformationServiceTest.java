package de.adorsys.xs2a.adapter.adorsys;

import de.adorsys.xs2a.adapter.api.RequestHeaders;
import de.adorsys.xs2a.adapter.api.RequestParams;
import de.adorsys.xs2a.adapter.api.Response;
import de.adorsys.xs2a.adapter.api.ResponseHeaders;
import de.adorsys.xs2a.adapter.api.http.HttpClient;
import de.adorsys.xs2a.adapter.api.http.Interceptor;
import de.adorsys.xs2a.adapter.api.link.LinksRewriter;
import de.adorsys.xs2a.adapter.api.model.Aspsp;
import de.adorsys.xs2a.adapter.api.model.OK200TransactionDetails;
import de.adorsys.xs2a.adapter.api.model.TransactionsResponse200Json;
import de.adorsys.xs2a.adapter.impl.http.RequestBuilderImpl;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdorsysAccountInformationServiceTest {
    private static final String ACCEPT_ALL = "*/*";
    private static final String ACCEPT_JSON = "application/json";
    private static final String ACCEPT_XML = "application/xml";
    private static final String URI = "https://foo.boo";
    private static final String ACCOUNT_ID = "accountId";
    private static final String REMITTANCE_INFORMATION_STRUCTURED = "remittanceInformationStructuredStringValue";

    @InjectMocks
    private AdorsysAccountInformationService accountInformationService;
    @Mock
    private HttpClient httpClient;
    @Mock
    private Aspsp aspsp;
    @Mock
    private LinksRewriter linksRewriter;
    @Spy
    private final List<Interceptor> interceptors = new LinkedList<>();

    @Test
    void populatePostHeaders_withoutAcceptHeader() {
        Map<String, String> actual = accountInformationService.populatePostHeaders(new HashMap<>());

        assertThat(actual).containsEntry(RequestHeaders.ACCEPT, ACCEPT_JSON);
    }

    @Test
    void populatePostHeaders_acceptAll() {
        HashMap<String, String> headers = new HashMap<>();
        headers.put(RequestHeaders.ACCEPT, ACCEPT_ALL);

        Map<String, String> actual = accountInformationService.populatePostHeaders(headers);

        assertThat(actual).containsEntry(RequestHeaders.ACCEPT, ACCEPT_JSON);
    }

    @Test
    void populatePostHeaders_acceptJson() {
        HashMap<String, String> headers = new HashMap<>();
        headers.put(RequestHeaders.ACCEPT, ACCEPT_JSON);

        Map<String, String> actual = accountInformationService.populatePostHeaders(headers);

        assertThat(actual).containsEntry(RequestHeaders.ACCEPT, ACCEPT_JSON);
    }

    @Test
    void populatePostHeaders_acceptXml() {
        HashMap<String, String> headers = new HashMap<>();
        headers.put(RequestHeaders.ACCEPT, ACCEPT_XML);

        Map<String, String> actual = accountInformationService.populatePostHeaders(headers);

        assertThat(actual).containsEntry(RequestHeaders.ACCEPT, ACCEPT_XML);
    }

    @Test
    void populatePutHeaders_withoutAcceptHeader() {
        Map<String, String> actual = accountInformationService.populatePutHeaders(new HashMap<>());

        assertThat(actual).containsEntry(RequestHeaders.ACCEPT, ACCEPT_JSON);
    }

    @Test
    void populatePutHeaders_acceptAll() {
        HashMap<String, String> headers = new HashMap<>();
        headers.put(RequestHeaders.ACCEPT, ACCEPT_ALL);

        Map<String, String> actual = accountInformationService.populatePutHeaders(headers);

        assertThat(actual).containsEntry(RequestHeaders.ACCEPT, ACCEPT_JSON);
    }

    @Test
    void populatePutHeaders_acceptJson() {
        HashMap<String, String> headers = new HashMap<>();
        headers.put(RequestHeaders.ACCEPT, ACCEPT_JSON);

        Map<String, String> actual = accountInformationService.populatePutHeaders(headers);

        assertThat(actual).containsEntry(RequestHeaders.ACCEPT, ACCEPT_JSON);
    }

    @Test
    void populatePutHeaders_acceptXml() {
        HashMap<String, String> headers = new HashMap<>();
        headers.put(RequestHeaders.ACCEPT, ACCEPT_XML);

        Map<String, String> actual = accountInformationService.populatePutHeaders(headers);

        assertThat(actual).containsEntry(RequestHeaders.ACCEPT, ACCEPT_XML);
    }

    @Test
    void populateGetHeaders_withoutAcceptHeader() {
        Map<String, String> actual = accountInformationService.populateGetHeaders(new HashMap<>());

        assertThat(actual).containsEntry(RequestHeaders.ACCEPT, ACCEPT_JSON);
    }

    @Test
    void populateGetHeaders_acceptAll() {
        HashMap<String, String> headers = new HashMap<>();
        headers.put(RequestHeaders.ACCEPT, ACCEPT_ALL);

        Map<String, String> actual = accountInformationService.populateGetHeaders(headers);

        assertThat(actual).containsEntry(RequestHeaders.ACCEPT, ACCEPT_JSON);
    }

    @Test
    void populateGetHeaders_acceptJson() {
        HashMap<String, String> headers = new HashMap<>();
        headers.put(RequestHeaders.ACCEPT, ACCEPT_JSON);

        Map<String, String> actual = accountInformationService.populateGetHeaders(headers);

        assertThat(actual).containsEntry(RequestHeaders.ACCEPT, ACCEPT_JSON);
    }

    @Test
    void populateGetHeaders_acceptXml() {
        HashMap<String, String> headers = new HashMap<>();
        headers.put(RequestHeaders.ACCEPT, ACCEPT_XML);

        Map<String, String> actual = accountInformationService.populateGetHeaders(headers);

        assertThat(actual).containsEntry(RequestHeaders.ACCEPT, ACCEPT_XML);
    }

    @Test
    void populateDeleteHeaders_withoutAcceptHeader() {
        Map<String, String> actual = accountInformationService.populateDeleteHeaders(new HashMap<>());

        assertThat(actual).containsEntry(RequestHeaders.ACCEPT, ACCEPT_JSON);
    }

    @Test
    void populateDeleteHeaders_acceptAll() {
        HashMap<String, String> headers = new HashMap<>();
        headers.put(RequestHeaders.ACCEPT, ACCEPT_ALL);

        Map<String, String> actual = accountInformationService.populateDeleteHeaders(headers);

        assertThat(actual).containsEntry(RequestHeaders.ACCEPT, ACCEPT_JSON);
    }

    @Test
    void populateDeleteHeaders_acceptJson() {
        HashMap<String, String> headers = new HashMap<>();
        headers.put(RequestHeaders.ACCEPT, ACCEPT_JSON);

        Map<String, String> actual = accountInformationService.populateDeleteHeaders(headers);

        assertThat(actual).containsEntry(RequestHeaders.ACCEPT, ACCEPT_JSON);
    }

    @Test
    void populateDeleteHeaders_acceptXml() {
        HashMap<String, String> headers = new HashMap<>();
        headers.put(RequestHeaders.ACCEPT, ACCEPT_XML);

        Map<String, String> actual = accountInformationService.populateDeleteHeaders(headers);

        assertThat(actual).containsEntry(RequestHeaders.ACCEPT, ACCEPT_XML);
    }

    @Test
    void getTransactionList() {
        String rawResponse = "{\n" +
            "  \"transactions\": {\n" +
            "    \"booked\": [\n" +
            "      {\n" +
            "        \"remittanceInformationStructuredArray\": [" +
            "           \"" + REMITTANCE_INFORMATION_STRUCTURED + "\"\n" +
            "        ]\n" +
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

        assertThat(actualResponse)
            .isNotNull()
            .extracting(Response::getBody)
            .asInstanceOf(InstanceOfAssertFactories.type(TransactionsResponse200Json.class))
            .matches(body ->
                body.getTransactions()
                    .getBooked()
                    .get(0)
                    .getRemittanceInformationStructuredArray()
                    .get(0)
                    .getReference()
                    .equals(REMITTANCE_INFORMATION_STRUCTURED));
    }

    @Test
    void getTransactionDetails() {
        String rawResponse = "{\n" +
            "  \"transactionsDetails\": {\n" +
            "      \"remittanceInformationStructuredArray\": [" +
            "         \"" + REMITTANCE_INFORMATION_STRUCTURED + "\"\n" +
            "      ]\n" +
            "  }\n" +
            "}";

        when(httpClient.get(anyString()))
            .thenReturn(new RequestBuilderImpl(httpClient, "GET", URI));
        when(httpClient.send(any(), any()))
            .thenAnswer(invocationOnMock -> {
                HttpClient.ResponseHandler responseHandler = invocationOnMock.getArgument(1, HttpClient.ResponseHandler.class);
                return new Response<>(
                    -1,
                    responseHandler.apply(200, new ByteArrayInputStream(rawResponse.getBytes()), ResponseHeaders.emptyResponseHeaders()),
                    null);
            });

        Response<?> actualResponse
            = accountInformationService.getTransactionDetails(ACCOUNT_ID, "transactionId", RequestHeaders.empty(), RequestParams.empty());

        verify(httpClient, times(1)).get(anyString());
        verify(httpClient, times(1)).send(any(), any());

        assertThat(actualResponse)
            .isNotNull()
            .extracting(Response::getBody)
            .asInstanceOf(InstanceOfAssertFactories.type(OK200TransactionDetails.class))
            .matches(body ->
                body.getTransactionsDetails()
                    .getRemittanceInformationStructuredArray()
                    .get(0)
                    .getReference()
                    .equals(REMITTANCE_INFORMATION_STRUCTURED));
    }
}
