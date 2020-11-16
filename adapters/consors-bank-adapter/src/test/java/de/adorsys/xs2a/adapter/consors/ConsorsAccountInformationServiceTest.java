package de.adorsys.xs2a.adapter.consors;

import de.adorsys.xs2a.adapter.api.*;
import de.adorsys.xs2a.adapter.api.http.ContentType;
import de.adorsys.xs2a.adapter.api.http.HttpClient;
import de.adorsys.xs2a.adapter.api.http.Request;
import de.adorsys.xs2a.adapter.api.model.Aspsp;
import de.adorsys.xs2a.adapter.api.model.Consents;
import de.adorsys.xs2a.adapter.api.model.ConsentsResponse201;
import de.adorsys.xs2a.adapter.api.model.TransactionsResponse200Json;
import de.adorsys.xs2a.adapter.impl.http.AbstractHttpClient;
import de.adorsys.xs2a.adapter.impl.link.identity.IdentityLinksRewriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;

import static de.adorsys.xs2a.adapter.api.RequestHeaders.PSU_ID;
import static org.assertj.core.api.Assertions.assertThat;

class ConsorsAccountInformationServiceTest {

    private final HttpClient httpClient = Mockito.spy(AbstractHttpClient.class);
    private final AccountInformationService service = new ConsorsServiceProvider()
        .getAccountInformationService(new Aspsp(), (x, y, z, d) -> httpClient, null, new IdentityLinksRewriter(), null);
    private ArgumentCaptor<Request.Builder> builderCaptor;

    @BeforeEach
    void setUp() {
        builderCaptor = ArgumentCaptor.forClass(Request.Builder.class);
    }

    @Test
    void createConsent_noPsuId() {
        Mockito.when(httpClient.send(Mockito.any(), Mockito.any()))
            .thenReturn(new Response<>(-1, new ConsentsResponse201(), ResponseHeaders.emptyResponseHeaders()));

        service.createConsent(RequestHeaders.empty(), RequestParams.empty(), new Consents());

        Mockito.verify(httpClient, Mockito.times(1))
            .send(builderCaptor.capture(), Mockito.any());

        Map<String, String> actualHeaders = builderCaptor.getValue().headers();
        assertThat(actualHeaders)
            .isNotEmpty()
            .containsEntry(PSU_ID, "");
    }

    @Test
    void createConsent_blankPsuId() {
        Map<String, String> headers = new HashMap<>();
        headers.put(PSU_ID, " ");

        Mockito.when(httpClient.send(Mockito.any(), Mockito.any()))
            .thenReturn(new Response<>(-1, new ConsentsResponse201(), ResponseHeaders.emptyResponseHeaders()));

        service.createConsent(RequestHeaders.fromMap(headers), RequestParams.empty(), new Consents());

        Mockito.verify(httpClient, Mockito.times(1))
            .send(builderCaptor.capture(), Mockito.any());

        Map<String, String> actualHeaders = builderCaptor.getValue().headers();
        assertThat(actualHeaders)
            .isNotEmpty()
            .containsEntry(PSU_ID, "");
    }

    @Test
    void createConsent_notEmptyPsuId() {
        Map<String, String> headers = new HashMap<>();
        headers.put(PSU_ID, "foo");

        Mockito.when(httpClient.send(Mockito.any(), Mockito.any()))
            .thenReturn(new Response<>(-1, new ConsentsResponse201(), ResponseHeaders.emptyResponseHeaders()));

        service.createConsent(RequestHeaders.fromMap(headers), RequestParams.empty(), new Consents());

        Mockito.verify(httpClient, Mockito.times(1))
            .send(builderCaptor.capture(), Mockito.any());

        Map<String, String> actualHeaders = builderCaptor.getValue().headers();
        assertThat(actualHeaders)
            .isNotEmpty()
            .containsEntry(PSU_ID, "");
    }

    @Test
    void getTransactionSetsAcceptApplicationJson() {
        Mockito.when(httpClient.send(Mockito.any(), Mockito.any()))
            .thenReturn(new Response<>(200, new TransactionsResponse200Json(), ResponseHeaders.emptyResponseHeaders()));

        service.getTransactionListAsString(null, RequestHeaders.empty(), RequestParams.empty());

        Mockito.verify(httpClient, Mockito.times(1))
            .send(builderCaptor.capture(), Mockito.any());
        assertThat(builderCaptor.getValue().headers())
            .containsEntry(RequestHeaders.ACCEPT, ContentType.APPLICATION_JSON);
    }
}
