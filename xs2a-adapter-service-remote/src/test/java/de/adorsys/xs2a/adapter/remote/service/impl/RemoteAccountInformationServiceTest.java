package de.adorsys.xs2a.adapter.remote.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import de.adorsys.xs2a.adapter.mapper.TransactionsReportMapper;
import de.adorsys.xs2a.adapter.model.BookingStatusTO;
import de.adorsys.xs2a.adapter.model.TransactionsResponse200JsonTO;
import de.adorsys.xs2a.adapter.remote.api.AccountInformationClient;
import de.adorsys.xs2a.adapter.service.RequestHeaders;
import de.adorsys.xs2a.adapter.service.RequestParams;
import de.adorsys.xs2a.adapter.service.Response;
import de.adorsys.xs2a.adapter.service.model.AccountReference;
import de.adorsys.xs2a.adapter.service.model.TransactionsReport;
import org.junit.Before;
import org.junit.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RemoteAccountInformationServiceTest {

    private RemoteAccountInformationService service;
    private AccountInformationClient client;

    @Before
    public void setUp() {
        client = mock(AccountInformationClient.class);
        service = new RemoteAccountInformationService(client);
    }

    @Test
    public void getTransactionListAsString() throws JsonProcessingException {
        TransactionsReport report = buildTransactionReport();
        when(client.getTransactionListAsString(
            any(), any(), any(), anyString(), any(), anyBoolean(), anyBoolean(), anyMap())
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

        TransactionsReport report = buildTransactionReport();
        ResponseEntity<String> responseEntity = buildResponseEntity(report);

        when(client.getTransactionListAsString(
            any(), any(), any(), anyString(), any(), anyBoolean(), anyBoolean(), anyMap())
        ).thenReturn(responseEntity);

        Response<TransactionsReport> response = service.getTransactionList(
            "accountId",
            RequestHeaders.fromMap(buildHeaders()),
            RequestParams.fromMap(buildRequestParams())
        );

        assertThat(response.getBody(), is(report));
    }

    private ResponseEntity<String> buildResponseEntity(TransactionsReport report) {
        TransactionsResponse200JsonTO transactionsResponse200JsonTO =
            Mappers.getMapper(TransactionsReportMapper.class).toTransactionsResponse200Json(report);
        try {
            String body = service.objectMapper.writeValueAsString(transactionsResponse200JsonTO);
            return new ResponseEntity<>(body, HttpStatus.OK);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private TransactionsReport buildTransactionReport() {
        TransactionsReport report = new TransactionsReport();
        AccountReference accountReference = new AccountReference();
        accountReference.setIban("iban");
        accountReference.setCurrency("EUR");
        report.setAccountReference(accountReference);
        return report;
    }

    private HashMap<String, String> buildRequestParams() {
        HashMap<String, String> params = new HashMap<>();
        params.put(RequestParams.BOOKING_STATUS, BookingStatusTO.BOOKED.toString());
        return params;
    }

    private Map<String, String> buildHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/json");
        return headers;
    }
}
