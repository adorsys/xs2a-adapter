package de.adorsys.xs2a.adapter.consors;

import de.adorsys.xs2a.adapter.api.*;
import de.adorsys.xs2a.adapter.api.http.HttpClient;
import de.adorsys.xs2a.adapter.api.http.Request;
import de.adorsys.xs2a.adapter.api.model.*;
import de.adorsys.xs2a.adapter.impl.http.AbstractHttpClient;
import de.adorsys.xs2a.adapter.impl.link.identity.IdentityLinksRewriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Map;

import static de.adorsys.xs2a.adapter.api.RequestHeaders.PSU_ID;
import static java.util.Collections.singletonMap;
import static org.assertj.core.api.Assertions.assertThat;

class ConsorsAccountInformationServiceTest {

    private static final String EMPTY_STRING = "";

    private final HttpClient httpClient = Mockito.spy(AbstractHttpClient.class);
    private final AccountInformationService service = new ConsorsServiceProvider()
        .getAccountInformationService(new Aspsp(), (x, y, z) -> httpClient, null, new IdentityLinksRewriter());
    private ArgumentCaptor<Request.Builder> builderCaptor;

    @BeforeEach
    void setUp() {
        builderCaptor = ArgumentCaptor.forClass(Request.Builder.class);
    }

    @Test
    void getTransactionListCanDeserializeRemittanceInformationUnstructuredOfTypeString() {
        Mockito.doAnswer(invocationOnMock -> {
            HttpClient.ResponseHandler responseHandler = invocationOnMock.getArgument(1, HttpClient.ResponseHandler.class);
            //language=JSON
            String rawResponse = "{\n" +
                "  \"transactions\": {\n" +
                "    \"booked\": [\n" +
                "      {\n" +
                "        \"remittanceInformationStructured\": \"remittanceInformationStructuredStringValue\"\n" +
                "      }\n" +
                "    ]\n" +
                "  }\n" +
                "}";
            return new Response<>(200,
                responseHandler.apply(200,
                    new ByteArrayInputStream(rawResponse.getBytes()),
                    ResponseHeaders.fromMap(singletonMap("Content-Type", "application/json"))),
                null);
        }).when(httpClient).send(Mockito.any(), Mockito.any());

        Response<TransactionsResponse200Json> response = service.getTransactionList(null,
            RequestHeaders.empty(),
            RequestParams.empty());

        RemittanceInformationStructured remittanceInformationStructured = response.getBody()
            .getTransactions()
            .getBooked()
            .get(0)
            .getRemittanceInformationStructured();
        assertThat(remittanceInformationStructured.getReference())
            .isEqualTo("remittanceInformationStructuredStringValue");
    }
    @Test
    void getTransactionDetailsCanDeserializeRemittanceInformationUnstructuredOfTypeString() {
        Mockito.doAnswer(invocationOnMock -> {
            HttpClient.ResponseHandler responseHandler = invocationOnMock.getArgument(1, HttpClient.ResponseHandler.class);
            //language=JSON
            String rawResponse = "{\n" +
                "  \"transactionsDetails\": {\n" +
                    "    \"remittanceInformationStructured\": \"remittanceInformationStructuredStringValue\"\n" +
                    "  }\n" +
                "}";
            return new Response<>(200,
                responseHandler.apply(200,
                    new ByteArrayInputStream(rawResponse.getBytes()),
                    ResponseHeaders.fromMap(singletonMap("Content-Type", "application/json"))),
                null);
        }).when(httpClient).send(Mockito.any(), Mockito.any());

        Response<OK200TransactionDetails> response = service.getTransactionDetails(null,
            null,
            RequestHeaders.empty(),
            RequestParams.empty());

        RemittanceInformationStructured remittanceInformationStructured = response.getBody()
            .getTransactionsDetails()
            .getRemittanceInformationStructured();
        assertThat(remittanceInformationStructured.getReference())
            .isEqualTo("remittanceInformationStructuredStringValue");
    }

    @Test
    void addPsuIdHeader_noPsuId() {
        Mockito.when(httpClient.send(Mockito.any(), Mockito.any()))
            .thenReturn(new Response<>(-1, new ConsentsResponse201(), ResponseHeaders.emptyResponseHeaders()));

        service.createConsent(RequestHeaders.empty(), RequestParams.empty(), new Consents());

        Mockito.verify(httpClient, Mockito.times(1))
            .send(builderCaptor.capture(), Mockito.any());

        Map<String, String> actualHeaders = builderCaptor.getValue().headers();
        assertThat(actualHeaders)
            .isNotEmpty()
            .containsEntry(PSU_ID, EMPTY_STRING);
    }

    @Test
    void addPsuIdHeader_blankPsuId() {
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
            .containsEntry(PSU_ID, EMPTY_STRING);
    }

    @Test
    void addPsuIdHeader_notEmptyPsuId() {
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
            .containsEntry(PSU_ID, EMPTY_STRING);
    }
}
