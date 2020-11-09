package de.adorsys.xs2a.adapter.commerzbank;

import de.adorsys.xs2a.adapter.api.RequestHeaders;
import de.adorsys.xs2a.adapter.api.RequestParams;
import de.adorsys.xs2a.adapter.api.Response;
import de.adorsys.xs2a.adapter.api.ResponseHeaders;
import de.adorsys.xs2a.adapter.api.http.HttpClient;
import de.adorsys.xs2a.adapter.api.link.LinksRewriter;
import de.adorsys.xs2a.adapter.api.model.Aspsp;
import de.adorsys.xs2a.adapter.api.model.OK200TransactionDetails;
import de.adorsys.xs2a.adapter.api.model.ReadAccountBalanceResponse200;
import de.adorsys.xs2a.adapter.api.model.TransactionsResponse200Json;
import de.adorsys.xs2a.adapter.commerzbank.model.CommerzbankBalanceReport;
import de.adorsys.xs2a.adapter.commerzbank.model.CommerzbankOK200TransactionDetails;
import de.adorsys.xs2a.adapter.commerzbank.model.CommerzbankTransactionsReport;
import de.adorsys.xs2a.adapter.impl.http.RequestBuilderImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommerzbankAccountInformationServiceTest {

    private static final String URI = "https://foo.boo";
    private static final String ACCOUNT_ID = "accountId";

    @InjectMocks
    private CommerzbankAccountInformationService accountInformationService;
    @Mock
    private HttpClient httpClient;
    @Mock
    private Aspsp aspsp;
    @Mock
    private LinksRewriter linksRewriter;

    @Test
    void getTransactionList() {
        when(httpClient.get(anyString()))
            .thenReturn(new RequestBuilderImpl(httpClient, "GET", URI));
        when(httpClient.send(any(), any()))
            .thenReturn(new Response<>(-1, new CommerzbankTransactionsReport(), ResponseHeaders.emptyResponseHeaders()));

        Response<?> actualResponse
            = accountInformationService.getTransactionList(ACCOUNT_ID, RequestHeaders.empty(), RequestParams.empty());

        verify(httpClient, times(1)).get(anyString());
        verify(httpClient, times(1)).send(any(), any());

        assertThat(actualResponse)
            .isNotNull()
            .extracting(Response::getBody)
            .isInstanceOf(TransactionsResponse200Json.class);
    }

    @Test
    void getBalances() {
        when(httpClient.get(anyString()))
            .thenReturn(new RequestBuilderImpl(httpClient, "GET", URI));
        when(httpClient.send(any(), any()))
            .thenReturn(new Response<>(-1, new CommerzbankBalanceReport(), ResponseHeaders.emptyResponseHeaders()));

        Response<?> actualResponse
            = accountInformationService.getBalances(ACCOUNT_ID, RequestHeaders.empty(), RequestParams.empty());

        verify(httpClient, times(1)).get(anyString());
        verify(httpClient, times(1)).send(any(), any());

        assertThat(actualResponse)
            .isNotNull()
            .extracting(Response::getBody)
            .isInstanceOf(ReadAccountBalanceResponse200.class);
    }

    @Test
    void getTransactionDetails() {
        when(httpClient.get(anyString()))
            .thenReturn(new RequestBuilderImpl(httpClient, "GET", URI));
        when(httpClient.send(any(), any()))
            .thenReturn(new Response<>(-1, new CommerzbankOK200TransactionDetails(), ResponseHeaders.emptyResponseHeaders()));

        Response<?> actualResponse
            = accountInformationService.getTransactionDetails(ACCOUNT_ID, "transactionId", RequestHeaders.empty(), RequestParams.empty());

        verify(httpClient, times(1)).get(anyString());
        verify(httpClient, times(1)).send(any(), any());

        assertThat(actualResponse)
            .isNotNull()
            .extracting(Response::getBody)
            .isInstanceOf(OK200TransactionDetails.class);
    }
}
