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

package de.adorsys.xs2a.adapter.adorsys;

import de.adorsys.xs2a.adapter.api.RequestHeaders;
import de.adorsys.xs2a.adapter.api.RequestParams;
import de.adorsys.xs2a.adapter.api.Response;
import de.adorsys.xs2a.adapter.api.ResponseHeaders;
import de.adorsys.xs2a.adapter.api.http.HttpClient;
import de.adorsys.xs2a.adapter.api.http.HttpClientConfig;
import de.adorsys.xs2a.adapter.api.http.HttpClientFactory;
import de.adorsys.xs2a.adapter.api.http.Interceptor;
import de.adorsys.xs2a.adapter.api.link.LinksRewriter;
import de.adorsys.xs2a.adapter.api.model.Aspsp;
import de.adorsys.xs2a.adapter.api.model.OK200TransactionDetails;
import de.adorsys.xs2a.adapter.api.model.TransactionsResponse200Json;
import de.adorsys.xs2a.adapter.impl.http.RequestBuilderImpl;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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

    private AdorsysAccountInformationService accountInformationService;
    @Mock
    private HttpClient httpClient;
    @Mock
    private Aspsp aspsp;
    @Mock
    private LinksRewriter linksRewriter;
    @Mock
    private HttpClientFactory httpClientFactory;
    @Mock
    private HttpClientConfig httpClientConfig;
    @Spy
    private final List<Interceptor> interceptors = new LinkedList<>();

    @BeforeEach
    void setUp() {
        when(httpClientFactory.getHttpClient(any())).thenReturn(httpClient);
        when(httpClientFactory.getHttpClientConfig()).thenReturn(httpClientConfig);

        accountInformationService = new AdorsysAccountInformationService(aspsp, httpClientFactory, interceptors, linksRewriter);
    }

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
                    .equals(REMITTANCE_INFORMATION_STRUCTURED));
    }

    @Test
    void getTransactionDetails() {
        String rawResponse = "{\n" +
            "  \"transactionsDetails\": {\n" +
            "       \"transactionDetails\": {\n" +
            "           \"remittanceInformationStructuredArray\": [" +
            "              \"" + REMITTANCE_INFORMATION_STRUCTURED + "\"\n" +
            "           ]\n" +
            "       }\n" +
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
                    .equals(REMITTANCE_INFORMATION_STRUCTURED));
    }
}
