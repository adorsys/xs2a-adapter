package de.adorsys.xs2a.adapter.fiducia;

import de.adorsys.xs2a.adapter.impl.link.identity.IdentityLinksRewriter;
import de.adorsys.xs2a.adapter.api.model.*;
import de.adorsys.xs2a.adapter.impl.http.AbstractHttpClient;
import de.adorsys.xs2a.adapter.http.HttpClient;
import de.adorsys.xs2a.adapter.service.RequestHeaders;
import de.adorsys.xs2a.adapter.service.RequestParams;
import de.adorsys.xs2a.adapter.service.Response;
import de.adorsys.xs2a.adapter.service.ResponseHeaders;
import de.adorsys.xs2a.adapter.service.model.Aspsp;
import de.adorsys.xs2a.adapter.validation.RequestValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Map;

import static java.util.Collections.singletonMap;
import static org.apache.http.protocol.HTTP.DATE_HEADER;
import static org.assertj.core.api.Assertions.assertThat;

class FiduciaAccountInformationServiceTest {
    private static final String ACCOUNT_ID = "1234567890";
    private static final String UNSUPPORTED_BOOKING_STATUS = "unsupported";
    private static final String SUPPORTED_BOOKING_STATUS = "booked";

    private HttpClient httpClient;
    private FiduciaAccountInformationService service;

    @BeforeEach
    void setUp() {
        httpClient = Mockito.spy(AbstractHttpClient.class);
        service = new FiduciaAccountInformationService(new Aspsp(), httpClient, null, new IdentityLinksRewriter());
    }

    @Test
    void populatePostHeaders() {
        Map<String, String> postHeaders = service.populatePostHeaders(new HashMap<>());
        assertThat(postHeaders)
            .hasSize(1)
            .containsKeys(DATE_HEADER);
    }

    @Test
    void populateGetHeaders() {
        Map<String, String> getHeaders = service.populateGetHeaders(new HashMap<>());
        assertThat(getHeaders)
            .hasSize(1)
            .containsKeys(DATE_HEADER);
    }

    @Test
    void populatePutHeaders() {
        Map<String, String> putHeaders = service.populatePutHeaders(new HashMap<>());
        assertThat(putHeaders)
            .hasSize(1)
            .containsKeys(DATE_HEADER);
    }

    @Test
    void populateDeleteHeaders() {
        Map<String, String> deleteHeaders = service.populateDeleteHeaders(new HashMap<>());
        assertThat(deleteHeaders)
            .hasSize(1)
            .containsKeys(DATE_HEADER);
    }

    @Test
    void getTransactionList_failure_notSupportedBookingStatus() {
        RequestHeaders requestHeaders = RequestHeaders.fromMap(new HashMap<>());
        RequestParams requestParams = RequestParams.fromMap(buildRequestParamsMapWithBookingStatus(UNSUPPORTED_BOOKING_STATUS));

        Assertions.assertThrows(
            RequestValidationException.class,
            () -> service.getTransactionList(ACCOUNT_ID, requestHeaders, requestParams)
        );
    }

    @Test
    void getTransactionListAsString_failure_notSupportedBookingStatus() {
        RequestHeaders requestHeaders = RequestHeaders.fromMap(new HashMap<>());
        RequestParams requestParams = RequestParams.fromMap(buildRequestParamsMapWithBookingStatus(UNSUPPORTED_BOOKING_STATUS));

        Assertions.assertThrows(
            RequestValidationException.class,
            () -> service.getTransactionListAsString(ACCOUNT_ID, requestHeaders, requestParams)
        );
    }

    private Map<String, String> buildRequestParamsMapWithBookingStatus(String bookingStatus) {
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put(RequestParams.BOOKING_STATUS, bookingStatus);
        return requestParams;
    }

    @Test
    void getTransactionListUsesFiduciaExecutionRule() {
        Mockito.doAnswer(invocationOnMock -> {
            HttpClient.ResponseHandler responseHandler = invocationOnMock.getArgument(1, HttpClient.ResponseHandler.class);
            //language=JSON
            String rawResponse = "{\n" +
                "  \"transactions\": {\n" +
                "    \"booked\": [\n" +
                "      {\n" +
                "        \"additionalInformationStructured\": {\n" +
                "          \"standingOrderDetails\": {\n" +
                "            \"executionRule\": \"preceeding\"\n" +
                "          }\n" +
                "        }\n" +
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

        ExecutionRule executionRule = response.getBody()
            .getTransactions()
            .getBooked()
            .get(0)
            .getAdditionalInformationStructured()
            .getStandingOrderDetails()
            .getExecutionRule();
        assertThat(executionRule).isEqualTo(ExecutionRule.PRECEDING);
    }

    @Test
    void getTransactionDetailsUsesFiduciaExecutionRule() {
        Mockito.doAnswer(invocationOnMock -> {
            HttpClient.ResponseHandler responseHandler = invocationOnMock.getArgument(1, HttpClient.ResponseHandler.class);
            //language=JSON
            String rawResponse = "{\n" +
                "  \"transactionsDetails\": {\n" +
                "    \"additionalInformationStructured\": {\n" +
                "      \"standingOrderDetails\": {\n" +
                "        \"executionRule\": \"preceeding\"\n" +
                "      }\n" +
                "    }\n" +
                "  }\n" +
                "}";
            return new Response<>(200,
                responseHandler.apply(200,
                    new ByteArrayInputStream(rawResponse.getBytes()),
                    ResponseHeaders.fromMap(singletonMap("Content-Type", "application/json"))),
                null);
        }).when(httpClient).send(Mockito.any(), Mockito.any());

        Response<OK200TransactionDetails> response = service.getTransactionDetails((String) null,
            (String) null,
            RequestHeaders.empty(),
            RequestParams.empty());

        ExecutionRule executionRule = response.getBody()
            .getTransactionsDetails()
            .getAdditionalInformationStructured()
            .getStandingOrderDetails()
            .getExecutionRule();
        assertThat(executionRule).isEqualTo(ExecutionRule.PRECEDING);
    }

    @Test
    void getTransactionListUsesFiduciaDayOfExecution() {
        Mockito.doAnswer(invocationOnMock -> {
            HttpClient.ResponseHandler responseHandler = invocationOnMock.getArgument(1, HttpClient.ResponseHandler.class);
            //language=JSON
            String rawResponse = "{\n" +
                "  \"transactions\": {\n" +
                "    \"booked\": [\n" +
                "      {\n" +
                "        \"additionalInformationStructured\": {\n" +
                "          \"standingOrderDetails\": {\n" +
                "            \"dayOfExecution\": \"01\"\n" +
                "          }\n" +
                "        }\n" +
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

        DayOfExecution dayOfExecution = response.getBody()
            .getTransactions()
            .getBooked()
            .get(0)
            .getAdditionalInformationStructured()
            .getStandingOrderDetails()
            .getDayOfExecution();
        assertThat(dayOfExecution).isEqualTo(DayOfExecution._1);
    }

    @Test
    void getTransactionDetailsUsesFiduciaDayOfExecution() {
        Mockito.doAnswer(invocationOnMock -> {
            HttpClient.ResponseHandler responseHandler = invocationOnMock.getArgument(1, HttpClient.ResponseHandler.class);
            //language=JSON
            String rawResponse = "{\n" +
                "  \"transactionsDetails\": {\n" +
                "    \"additionalInformationStructured\": {\n" +
                "      \"standingOrderDetails\": {\n" +
                "        \"dayOfExecution\": \"01\"\n" +
                "      }\n" +
                "    }\n" +
                "  }\n" +
                "}";
            return new Response<>(200,
                responseHandler.apply(200,
                    new ByteArrayInputStream(rawResponse.getBytes()),
                    ResponseHeaders.fromMap(singletonMap("Content-Type", "application/json"))),
                null);
        }).when(httpClient).send(Mockito.any(), Mockito.any());

        Response<OK200TransactionDetails> response = service.getTransactionDetails((String) null,
            (String) null,
            RequestHeaders.empty(),
            RequestParams.empty());

        DayOfExecution dayOfExecution = response.getBody()
            .getTransactionsDetails()
            .getAdditionalInformationStructured()
            .getStandingOrderDetails()
            .getDayOfExecution();
        assertThat(dayOfExecution).isEqualTo(DayOfExecution._1);
    }

    @Test
    void getTransactionListUsesFiduciaMonthsOfExecution() {
        Mockito.doAnswer(invocationOnMock -> {
            HttpClient.ResponseHandler responseHandler = invocationOnMock.getArgument(1, HttpClient.ResponseHandler.class);
            //language=JSON
            String rawResponse = "{\n" +
                "  \"transactions\": {\n" +
                "    \"booked\": [\n" +
                "      {\n" +
                "        \"additionalInformationStructured\": {\n" +
                "          \"standingOrderDetails\": {\n" +
                "            \"monthsOfExecution\": [\"01\"]\n" +
                "          }\n" +
                "        }\n" +
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

        StandingOrderDetails.MonthsOfExecution monthsOfExecution = response.getBody()
            .getTransactions()
            .getBooked()
            .get(0)
            .getAdditionalInformationStructured()
            .getStandingOrderDetails()
            .getMonthsOfExecution()
            .get(0);
        assertThat(monthsOfExecution).isEqualTo(StandingOrderDetails.MonthsOfExecution._1);
    }

    @Test
    void getTransactionDetailsUsesFiduciaMonthsOfExecution() {
        Mockito.doAnswer(invocationOnMock -> {
            HttpClient.ResponseHandler responseHandler = invocationOnMock.getArgument(1, HttpClient.ResponseHandler.class);
            //language=JSON
            String rawResponse = "{\n" +
                "  \"transactionsDetails\": {\n" +
                "    \"additionalInformationStructured\": {\n" +
                "      \"standingOrderDetails\": {\n" +
                "        \"monthsOfExecution\": [\"01\"]\n" +
                "      }\n" +
                "    }\n" +
                "  }\n" +
                "}";
            return new Response<>(200,
                responseHandler.apply(200,
                    new ByteArrayInputStream(rawResponse.getBytes()),
                    ResponseHeaders.fromMap(singletonMap("Content-Type", "application/json"))),
                null);
        }).when(httpClient).send(Mockito.any(), Mockito.any());

        Response<OK200TransactionDetails> response = service.getTransactionDetails((String) null,
            (String) null,
            RequestHeaders.empty(),
            RequestParams.empty());

        StandingOrderDetails.MonthsOfExecution monthsOfExecution = response.getBody()
            .getTransactionsDetails()
            .getAdditionalInformationStructured()
            .getStandingOrderDetails()
            .getMonthsOfExecution()
            .get(0);
        assertThat(monthsOfExecution).isEqualTo(StandingOrderDetails.MonthsOfExecution._1);
    }

    @Test
    void getTransactionListHandlesStringRemittanceInformationStructured() {
        Mockito.doAnswer(invocationOnMock -> {
            HttpClient.ResponseHandler responseHandler = invocationOnMock.getArgument(1, HttpClient.ResponseHandler.class);
            //language=JSON
            String rawResponse = "{\n" +
                "  \"transactions\": {\n" +
                "    \"booked\": [\n" +
                "      {\n" +
                "        \"remittanceInformationStructured\": \"reference\"\n" +
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
        assertThat(remittanceInformationStructured.getReference()).isEqualTo("reference");
    }

    @Test
    void getTransactionDetailsHandlesStringRemittanceInformationStructured() {
        Mockito.doAnswer(invocationOnMock -> {
            HttpClient.ResponseHandler responseHandler = invocationOnMock.getArgument(1, HttpClient.ResponseHandler.class);

            //language=JSON
            String rawResponse = "{\n" +
                "  \"transactionsDetails\": {\n" +
                "    \"remittanceInformationStructured\": \"reference\"\n" +
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

        String reference = response.getBody()
            .getTransactionsDetails()
            .getRemittanceInformationStructured()
            .getReference();
        assertThat(reference).isEqualTo("reference");
    }
}
