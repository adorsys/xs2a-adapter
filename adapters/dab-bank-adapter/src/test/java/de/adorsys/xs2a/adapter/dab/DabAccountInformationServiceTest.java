package de.adorsys.xs2a.adapter.dab;

import de.adorsys.xs2a.adapter.impl.adapter.link.identity.IdentityLinksRewriter;
import de.adorsys.xs2a.adapter.api.model.OK200TransactionDetails;
import de.adorsys.xs2a.adapter.api.model.RemittanceInformationStructured;
import de.adorsys.xs2a.adapter.api.model.TransactionsResponse200Json;
import de.adorsys.xs2a.adapter.impl.http.AbstractHttpClient;
import de.adorsys.xs2a.adapter.http.HttpClient;
import de.adorsys.xs2a.adapter.service.*;
import de.adorsys.xs2a.adapter.service.model.Aspsp;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;

import static java.util.Collections.singletonMap;
import static org.assertj.core.api.Assertions.assertThat;

class DabAccountInformationServiceTest {

    private final HttpClient httpClient = Mockito.spy(AbstractHttpClient.class);
    private final AccountInformationService service = new DabServiceProvider()
        .getAccountInformationService(new Aspsp(), (x, y, z) -> httpClient, null, new IdentityLinksRewriter());

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
}
