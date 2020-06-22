package de.adorsys.xs2a.adapter.remote.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import de.adorsys.xs2a.adapter.api.model.AccountReference;
import de.adorsys.xs2a.adapter.api.model.BookingStatus;
import de.adorsys.xs2a.adapter.api.model.TransactionsResponse200Json;
import de.adorsys.xs2a.adapter.remote.api.AccountInformationClient;
import de.adorsys.xs2a.adapter.service.RequestHeaders;
import de.adorsys.xs2a.adapter.service.RequestParams;
import de.adorsys.xs2a.adapter.service.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RemoteAccountInformationServiceTest {

    private RemoteAccountInformationService service;
    private AccountInformationClient client;

    @BeforeEach
    public void setUp() {
        client = mock(AccountInformationClient.class);
        service = new RemoteAccountInformationService(client);
    }

    @Test
    public void getTransactionListAsString() throws JsonProcessingException {
        TransactionsResponse200Json report = buildTransactionReport();
        when(client.getTransactionListAsString(
            any(), any(), any(), any(), any(), any(), any(), any())
        ).thenReturn(buildResponseEntity(report));

        Response<String> response = service.getTransactionListAsString(
            "accountId",
            RequestHeaders.fromMap(buildHeaders()),
            RequestParams.fromMap(buildRequestParams())
        );

        assertThat(service.objectMapper.writeValueAsString(report), is(response.getBody()));
    }

    @Test
    public void getTransactionList() {
        AccountInformationClient client = mock(AccountInformationClient.class);
        RemoteAccountInformationService service = new RemoteAccountInformationService(client);

        TransactionsResponse200Json report = buildTransactionReport();
        ResponseEntity<String> responseEntity = buildResponseEntity(report);

        when(client.getTransactionListAsString(
            any(), any(), any(), any(), any(), any(), any(), any())
        ).thenReturn(responseEntity);

        Response<TransactionsResponse200Json> response = service.getTransactionList(
            "accountId",
            RequestHeaders.fromMap(buildHeaders()),
            RequestParams.fromMap(buildRequestParams())
        );

        assertThat(response.getBody(), is(report));
    }

    private ResponseEntity<String> buildResponseEntity(TransactionsResponse200Json report) {
        try {
            String body = service.objectMapper.writeValueAsString(report);
            return new ResponseEntity<>(body, HttpStatus.OK);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private TransactionsResponse200Json buildTransactionReport() {
        TransactionsResponse200Json report = new TransactionsResponse200Json();
        AccountReference accountReference = new AccountReference();
        accountReference.setIban("iban");
        accountReference.setCurrency("EUR");
        report.setAccount(accountReference);
        return report;
    }

    private HashMap<String, String> buildRequestParams() {
        HashMap<String, String> params = new HashMap<>();
        params.put(RequestParams.BOOKING_STATUS, BookingStatus.BOOKED.toString());
        return params;
    }

    private Map<String, String> buildHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/json");
        return headers;
    }
}
