package de.adorsys.xs2a.adapter.fiducia;

import de.adorsys.xs2a.adapter.adapter.link.identity.IdentityLinksRewriter;
import de.adorsys.xs2a.adapter.api.model.DayOfExecution;
import de.adorsys.xs2a.adapter.api.model.ExecutionRule;
import de.adorsys.xs2a.adapter.api.model.OK200TransactionDetails;
import de.adorsys.xs2a.adapter.api.model.TransactionsResponse200Json;
import de.adorsys.xs2a.adapter.http.AbstractHttpClient;
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

public class FiduciaAccountInformationServiceTest {
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
    public void populatePostHeaders() {
        Map<String, String> postHeaders = service.populatePostHeaders(new HashMap<>());
        assertThat(postHeaders.size()).isEqualTo(1);
        assertThat(postHeaders).containsKeys(DATE_HEADER);
    }

    @Test
    public void populateGetHeaders() {
        Map<String, String> getHeaders = service.populateGetHeaders(new HashMap<>());
        assertThat(getHeaders.size()).isEqualTo(1);
        assertThat(getHeaders).containsKeys(DATE_HEADER);
    }

    @Test
    public void populatePutHeaders() {
        Map<String, String> putHeaders = service.populatePutHeaders(new HashMap<>());
        assertThat(putHeaders.size()).isEqualTo(1);
        assertThat(putHeaders).containsKeys(DATE_HEADER);
    }

    @Test
    public void populateDeleteHeaders() {
        Map<String, String> deleteHeaders = service.populateDeleteHeaders(new HashMap<>());
        assertThat(deleteHeaders.size()).isEqualTo(1);
        assertThat(deleteHeaders).containsKeys(DATE_HEADER);
    }

    @Test
    public void getTransactionList_failure_notSupportedBookingStatus() {
        RequestValidationException ex = Assertions.assertThrows(
            RequestValidationException.class,
            () -> service.getTransactionList(
                ACCOUNT_ID,
                RequestHeaders.fromMap(new HashMap<>()),
                RequestParams.fromMap(buildRequestParamsMapWithBookingStatus(UNSUPPORTED_BOOKING_STATUS))
            )
        );
    }

    @Test
    public void getTransactionListAsString_failure_notSupportedBookingStatus() {
        Assertions.assertThrows(
            RequestValidationException.class,
            () -> service.getTransactionListAsString(
                ACCOUNT_ID,
                RequestHeaders.fromMap(new HashMap<>()),
                RequestParams.fromMap(buildRequestParamsMapWithBookingStatus(UNSUPPORTED_BOOKING_STATUS))
            )
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
}
