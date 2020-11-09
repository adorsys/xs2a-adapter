package de.adorsys.xs2a.adapter.verlag;

import de.adorsys.xs2a.adapter.api.RequestHeaders;
import de.adorsys.xs2a.adapter.api.RequestParams;
import de.adorsys.xs2a.adapter.api.Response;
import de.adorsys.xs2a.adapter.api.ResponseHeaders;
import de.adorsys.xs2a.adapter.api.http.HttpClient;
import de.adorsys.xs2a.adapter.api.link.LinksRewriter;
import de.adorsys.xs2a.adapter.api.model.Aspsp;
import de.adorsys.xs2a.adapter.api.model.OK200TransactionDetails;
import de.adorsys.xs2a.adapter.api.model.TransactionsResponse200Json;
import de.adorsys.xs2a.adapter.impl.http.RequestBuilderImpl;
import de.adorsys.xs2a.adapter.verlag.model.VerlagOK200TransactionDetails;
import de.adorsys.xs2a.adapter.verlag.model.VerlagTransactionResponse200Json;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.AbstractMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VerlagAccountInformationServiceTest {

    private static final String URI = "https://foo.boo";
    private static final String ACCOUNT_ID = "accountId";

    @InjectMocks
    private VerlagAccountInformationService accountInformationService;
    @Mock
    private HttpClient httpClient;
    @Mock
    private Aspsp aspsp;
    @Mock
    private LinksRewriter linksRewriter;
    @Mock
    private AbstractMap.SimpleImmutableEntry<String, String> apiKey;

    @Test
    void getTransactionList() {
        when(httpClient.get(anyString()))
            .thenReturn(new RequestBuilderImpl(httpClient, "GET", URI));
        when(httpClient.send(any(), any()))
            .thenReturn(new Response<>(-1, new VerlagTransactionResponse200Json(), ResponseHeaders.emptyResponseHeaders()));

        Response<?> actualResponse
            = accountInformationService.getTransactionList(ACCOUNT_ID, RequestHeaders.empty(), RequestParams.empty());

        verify(httpClient, times(1)).get(anyString());
        verify(httpClient, times(1)).send(any(), any());

        assertNotNull(actualResponse);
        assertTrue(actualResponse.getBody() instanceof TransactionsResponse200Json);
    }

    @Test
    void getTransactionDetails() {
        when(httpClient.get(anyString()))
            .thenReturn(new RequestBuilderImpl(httpClient, "GET", URI));
        when(httpClient.send(any(), any()))
            .thenReturn(new Response<>(-1, new VerlagOK200TransactionDetails(), ResponseHeaders.emptyResponseHeaders()));

        Response<?> actualResponse
            = accountInformationService.getTransactionDetails(ACCOUNT_ID, "transactionId", RequestHeaders.empty(), RequestParams.empty());

        verify(httpClient, times(1)).get(anyString());
        verify(httpClient, times(1)).send(any(), any());

        assertNotNull(actualResponse);
        assertTrue(actualResponse.getBody() instanceof OK200TransactionDetails);
    }
}
