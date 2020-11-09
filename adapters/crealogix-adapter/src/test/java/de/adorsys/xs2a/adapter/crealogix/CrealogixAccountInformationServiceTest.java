package de.adorsys.xs2a.adapter.crealogix;

import de.adorsys.xs2a.adapter.api.*;
import de.adorsys.xs2a.adapter.api.http.HttpClient;
import de.adorsys.xs2a.adapter.api.link.LinksRewriter;
import de.adorsys.xs2a.adapter.api.model.*;
import de.adorsys.xs2a.adapter.crealogix.model.CrealogixOK200TransactionDetails;
import de.adorsys.xs2a.adapter.crealogix.model.CrealogixTransactionResponse200Json;
import de.adorsys.xs2a.adapter.impl.http.RequestBuilderImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static java.util.Collections.singletonMap;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class CrealogixAccountInformationServiceTest {

    private static final String AUTHORIZATION = "Authorization";
    private static final String AUTHORIZATION_VALUE = "Bearer foo";
    private static final String URI = "https://foo.boo";
    public static final String ACCOUNT_ID = "accountId";
    private final HttpClient httpClient = mock(HttpClient.class);
    private final LinksRewriter linksRewriter = mock(LinksRewriter.class);
    private static final Aspsp aspsp = getAspsp();
    private AccountInformationService service;

    @BeforeEach
    void setUp() {
        service = new CrealogixAccountInformationService(aspsp, httpClient, linksRewriter);
    }

    @Test
    void createConsent() {
        when(httpClient.post(anyString())).thenReturn(new RequestBuilderImpl(httpClient, "POST", URI));
        when(httpClient.send(any(), any()))
            .thenReturn(new Response<>(
                -1,
                new ConsentsResponse201(),
                ResponseHeaders.emptyResponseHeaders()));

        Response<ConsentsResponse201> actualResponse
            = service.createConsent(
                RequestHeaders.fromMap(singletonMap(AUTHORIZATION, AUTHORIZATION_VALUE)),
                RequestParams.empty(),
                new Consents());

        verify(httpClient, times(1)).post(anyString());
        verify(httpClient, times(1)).send(any(), any());

        assertThat(actualResponse)
            .isNotNull()
            .extracting(Response::getBody)
            .isInstanceOf(ConsentsResponse201.class);
    }

    @Test
    void getTransactionList() {
        when(httpClient.get(anyString()))
            .thenReturn(new RequestBuilderImpl(httpClient, "GET", URI));
        when(httpClient.send(any(), any()))
            .thenReturn(new Response<>(-1, new CrealogixTransactionResponse200Json(), ResponseHeaders.emptyResponseHeaders()));

        Response<?> actualResponse
            = service.getTransactionList(
                ACCOUNT_ID,
                RequestHeaders.fromMap(singletonMap(AUTHORIZATION, AUTHORIZATION_VALUE)),
                RequestParams.empty());

        verify(httpClient, times(1)).get(anyString());
        verify(httpClient, times(1)).send(any(), any());

        assertThat(actualResponse)
            .isNotNull()
            .extracting(Response::getBody)
            .isInstanceOf(TransactionsResponse200Json.class);
    }

    @Test
    void getTransactionDetails() {
        when(httpClient.get(anyString()))
            .thenReturn(new RequestBuilderImpl(httpClient, "GET", URI));
        when(httpClient.send(any(), any()))
            .thenReturn(new Response<>(-1, new CrealogixOK200TransactionDetails(), ResponseHeaders.emptyResponseHeaders()));

        Response<?> actualResponse
            = service.getTransactionDetails(
                ACCOUNT_ID,
                "transactionId",
                RequestHeaders.fromMap(singletonMap(AUTHORIZATION, AUTHORIZATION_VALUE)),
                RequestParams.empty());

        verify(httpClient, times(1)).get(anyString());
        verify(httpClient, times(1)).send(any(), any());

        assertThat(actualResponse)
            .isNotNull()
            .extracting(Response::getBody)
            .isInstanceOf(OK200TransactionDetails.class);
    }

    private static Aspsp getAspsp() {
        Aspsp aspsp = new Aspsp();
        aspsp.setUrl("https://url.com");
        return aspsp;
    }
}
