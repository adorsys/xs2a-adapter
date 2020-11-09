package de.adorsys.xs2a.adapter.deutschebank;

import de.adorsys.xs2a.adapter.api.RequestHeaders;
import de.adorsys.xs2a.adapter.api.RequestParams;
import de.adorsys.xs2a.adapter.api.Response;
import de.adorsys.xs2a.adapter.api.ResponseHeaders;
import de.adorsys.xs2a.adapter.api.http.HttpClient;
import de.adorsys.xs2a.adapter.api.http.Request;
import de.adorsys.xs2a.adapter.api.model.*;
import de.adorsys.xs2a.adapter.deutschebank.model.DeutscheBankOK200TransactionDetails;
import de.adorsys.xs2a.adapter.deutschebank.model.DeutscheBankTransactionResponse200Json;
import de.adorsys.xs2a.adapter.impl.http.RequestBuilderImpl;
import de.adorsys.xs2a.adapter.impl.link.identity.IdentityLinksRewriter;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static java.util.Collections.emptyMap;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

class DeutscheBankAccountInformationServiceTest {

    private static final String BASE_URL = "https://simulator-xs2a.db.com/ais/DE/SB-DB";
    private static final Aspsp ASPSP = buildAspspWithUrl();
    private static final String CONSENT_URL = BASE_URL + "/v1/consents";
    private static final String ACCOUNT_ID = "accountId";
    private final HttpClient httpClient = mock(HttpClient.class);
    private final DeutscheBankAccountInformationService service =
            new DeutscheBankAccountInformationService(ASPSP, httpClient, null, new IdentityLinksRewriter(), null);

    @Test
    void createConsent() {

        Request.Builder requestBuilder = new RequestBuilderImpl(httpClient, "POST", CONSENT_URL);
        when(httpClient.post(eq(CONSENT_URL)))
            .thenReturn(requestBuilder);
        when(httpClient.send(any(), any()))
            .thenReturn(new Response<>(200, new ConsentsResponse201(), ResponseHeaders.fromMap(emptyMap())));

        service.createConsent(RequestHeaders.fromMap(emptyMap()), RequestParams.empty(), new Consents());

        verify(httpClient, times(1)).post(eq(CONSENT_URL));
        Map<String, String> headers = requestBuilder.headers();
        assertThat(headers)
            .isNotNull()
            .isNotEmpty()
            .containsKey(RequestHeaders.DATE)
            .containsKey(RequestHeaders.PSU_ID)
            .containsEntry(RequestHeaders.CONTENT_TYPE, "application/json");
    }

    @Test
    void getTransactionList() {
        when(httpClient.get(anyString()))
            .thenReturn(new RequestBuilderImpl(httpClient, "GET", BASE_URL));
        when(httpClient.send(any(), any()))
            .thenReturn(new Response<>(-1, new DeutscheBankTransactionResponse200Json(), ResponseHeaders.emptyResponseHeaders()));

        Response<?> actualResponse
            = service.getTransactionList(ACCOUNT_ID, RequestHeaders.empty(), RequestParams.empty());

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
            .thenReturn(new RequestBuilderImpl(httpClient, "GET", BASE_URL));
        when(httpClient.send(any(), any()))
            .thenReturn(new Response<>(-1, new DeutscheBankOK200TransactionDetails(), ResponseHeaders.emptyResponseHeaders()));

        Response<?> actualResponse
            = service.getTransactionDetails(ACCOUNT_ID, "transactionId", RequestHeaders.empty(), RequestParams.empty());

        verify(httpClient, times(1)).get(anyString());
        verify(httpClient, times(1)).send(any(), any());

        assertThat(actualResponse)
            .isNotNull()
            .extracting(Response::getBody)
            .isInstanceOf(OK200TransactionDetails.class);
    }

    private static Aspsp buildAspspWithUrl() {
        Aspsp aspsp = new Aspsp();
        aspsp.setUrl(BASE_URL);
        return aspsp;
    }
}
