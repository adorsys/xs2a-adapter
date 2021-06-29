package de.adorsys.xs2a.adapter.remote;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.adorsys.xs2a.adapter.api.RequestHeaders;
import de.adorsys.xs2a.adapter.api.RequestParams;
import de.adorsys.xs2a.adapter.api.Response;
import de.adorsys.xs2a.adapter.api.ResponseHeaders;
import de.adorsys.xs2a.adapter.api.exception.NotAcceptableException;
import de.adorsys.xs2a.adapter.api.model.*;
import de.adorsys.xs2a.adapter.remote.client.AccountInformationClient;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class RemoteAccountInformationServiceTest {

    public static final String CONSENT_ID = "consentId";
    public static final String CONTENT_TYPE = "application/json";
    public static final String X_REQUEST_ID = "1234567";
    public static final int HTTP_STATUS_OK = 200;
    public static final String AUTHORIZATION_ID = "AUTHORIZATION_ID";
    public static final String ACCOUNT_ID = "ACCOUNT_ID";
    public static final String TRANSACTION_ID = "TRX_ID";
    private final ObjectMapper objectMapper = new ObjectMapper();
    private RemoteAccountInformationService service;
    private AccountInformationClient client;
    private final ResponseEntity entity = mock(ResponseEntity.class);

    @BeforeEach
    public void setUp() {
        client = mock(AccountInformationClient.class);
        service = new RemoteAccountInformationService(client);
    }

    @Test
    void createConsent() {
        ConsentsResponse201 responseBody = new ConsentsResponse201();

        doReturn(entity).when(client).createConsent(anyMap(), anyMap(), any());
        doReturn(HTTP_STATUS_OK).when(entity).getStatusCodeValue();
        doReturn(responseBody).when(entity).getBody();
        doReturn(buildHttpHeaders()).when(entity).getHeaders();

        Response<ConsentsResponse201> response = service.createConsent(RequestHeaders.empty(),
                                                                       RequestParams.empty(),
                                                                       new Consents());

        Assertions.assertThat(response.getBody()).isEqualTo(responseBody);
        assertResponseHeaders(response.getHeaders());
    }

    private static void assertResponseHeaders(ResponseHeaders headers) {
        Assertions.assertThat(headers.getHeadersMap()).hasSize(2);
        Assertions.assertThat(headers.getHeader(ResponseHeaders.CONTENT_TYPE)).isEqualTo(CONTENT_TYPE);
        Assertions.assertThat(headers.getHeader(ResponseHeaders.X_REQUEST_ID)).isEqualTo(X_REQUEST_ID);
    }

    private HttpHeaders buildHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.put(ResponseHeaders.CONTENT_TYPE, Collections.singletonList(CONTENT_TYPE));
        headers.put(ResponseHeaders.X_REQUEST_ID, Collections.singletonList(X_REQUEST_ID));
        return headers;
    }

    @Test
    void getConsentInformation() {
        ConsentInformationResponse200Json responseBody = new ConsentInformationResponse200Json();

        doReturn(entity).when(client).getConsentInformation(CONSENT_ID, Collections.emptyMap(), Collections.emptyMap());
        doReturn(HTTP_STATUS_OK).when(entity).getStatusCodeValue();
        doReturn(responseBody).when(entity).getBody();
        doReturn(buildHttpHeaders()).when(entity).getHeaders();

        Response<ConsentInformationResponse200Json> response = service.getConsentInformation(CONSENT_ID,
                                                                                             RequestHeaders.empty(),
                                                                                             RequestParams.empty());

        Assertions.assertThat(response.getBody()).isEqualTo(responseBody);
        assertResponseHeaders(response.getHeaders());
    }

    @Test
    void getConsentStatus() {
        ConsentStatusResponse200 responseBody = new ConsentStatusResponse200();

        doReturn(entity).when(client).getConsentStatus(CONSENT_ID, Collections.emptyMap(), Collections.emptyMap());
        doReturn(HTTP_STATUS_OK).when(entity).getStatusCodeValue();
        doReturn(responseBody).when(entity).getBody();
        doReturn(buildHttpHeaders()).when(entity).getHeaders();

        Response<ConsentStatusResponse200> response = service.getConsentStatus(CONSENT_ID,
                                                                               RequestHeaders.empty(),
                                                                               RequestParams.empty());

        Assertions.assertThat(response.getBody()).isEqualTo(responseBody);
        assertResponseHeaders(response.getHeaders());
    }

    @Test
    void deleteConsent() {

        doReturn(entity).when(client).deleteConsent(CONSENT_ID, Collections.emptyMap(), Collections.emptyMap());
        doReturn(202).when(entity).getStatusCodeValue();
        doReturn(buildHttpHeaders()).when(entity).getHeaders();

        Response<Void> response = service.deleteConsent(CONSENT_ID,
                                                        RequestHeaders.empty(),
                                                        RequestParams.empty());

        assertResponseHeaders(response.getHeaders());
    }

    @Test
    void getConsentAuthorisation() {
        Authorisations responseBody = new Authorisations();

        doReturn(entity).when(client).getConsentAuthorisation(CONSENT_ID, Collections.emptyMap(), Collections.emptyMap());
        doReturn(HTTP_STATUS_OK).when(entity).getStatusCodeValue();
        doReturn(responseBody).when(entity).getBody();
        doReturn(buildHttpHeaders()).when(entity).getHeaders();

        Response<Authorisations> response = service.getConsentAuthorisation(CONSENT_ID,
            RequestHeaders.empty(),
            RequestParams.empty());

        Assertions.assertThat(response.getBody()).isEqualTo(responseBody);
        assertResponseHeaders(response.getHeaders());
    }

    @Test
    void startConsentAuthorisation() {
        StartScaprocessResponse responseBody = new StartScaprocessResponse();

        doReturn(entity).when(client).startConsentAuthorisation(CONSENT_ID,
                                                                Collections.emptyMap(),
                                                                Collections.emptyMap(),
                                                                objectMapper.createObjectNode());
        doReturn(HTTP_STATUS_OK).when(entity).getStatusCodeValue();
        doReturn(responseBody).when(entity).getBody();
        doReturn(buildHttpHeaders()).when(entity).getHeaders();

        Response<StartScaprocessResponse> response = service.startConsentAuthorisation(CONSENT_ID,
                                                                                       RequestHeaders.empty(),
                                                                                       RequestParams.empty());
        assertResponseHeaders(response.getHeaders());
        Assertions.assertThat(response.getBody()).isEqualTo(responseBody);
    }

    @Test
    void startConsentAuthorisation_PsuAuthentication() {
        StartScaprocessResponse responseBody = new StartScaprocessResponse();

        doReturn(entity).when(client).startConsentAuthorisation(eq(CONSENT_ID),
                                                                anyMap(),
                                                                anyMap(),
                                                                any());
        doReturn(HTTP_STATUS_OK).when(entity).getStatusCodeValue();
        doReturn(responseBody).when(entity).getBody();
        doReturn(buildHttpHeaders()).when(entity).getHeaders();

        Response<StartScaprocessResponse> response = service.startConsentAuthorisation(CONSENT_ID,
                                                                                       RequestHeaders.empty(),
                                                                                       RequestParams.empty(),
                                                                                       new UpdatePsuAuthentication());
        assertResponseHeaders(response.getHeaders());
        Assertions.assertThat(response.getBody()).isEqualTo(responseBody);
    }

    @Test
    void updateConsentsPsuData_SelectMethod() {
        SelectPsuAuthenticationMethodResponse responseBody = new SelectPsuAuthenticationMethodResponse();
        SelectPsuAuthenticationMethod requestBody = new SelectPsuAuthenticationMethod();

        doReturn(entity).when(client).updateConsentsPsuData(eq(CONSENT_ID),
                                                            eq(AUTHORIZATION_ID),
                                                            anyMap(),
                                                            anyMap(),
                                                            any());
        doReturn(HTTP_STATUS_OK).when(entity).getStatusCodeValue();
        doReturn(responseBody).when(entity).getBody();
        doReturn(buildHttpHeaders()).when(entity).getHeaders();

        Response<SelectPsuAuthenticationMethodResponse> response = service.updateConsentsPsuData(CONSENT_ID,
                                                                                                 AUTHORIZATION_ID,
                                                                                                 RequestHeaders.empty(),
                                                                                                 RequestParams.empty(),
                                                                                                 requestBody);
        assertResponseHeaders(response.getHeaders());
        Assertions.assertThat(response.getBody()).isEqualTo(responseBody);
    }

    @Test
    void updateConsentsPsuData_SendOTP() {
        ScaStatusResponse responseBody = new ScaStatusResponse();
        TransactionAuthorisation requestBody = new TransactionAuthorisation();

        doReturn(entity).when(client).updateConsentsPsuData(eq(CONSENT_ID),
                                                            eq(AUTHORIZATION_ID),
                                                            anyMap(),
                                                            anyMap(),
                                                            any());
        doReturn(HTTP_STATUS_OK).when(entity).getStatusCodeValue();
        doReturn(responseBody).when(entity).getBody();
        doReturn(buildHttpHeaders()).when(entity).getHeaders();

        Response<ScaStatusResponse> response = service.updateConsentsPsuData(CONSENT_ID,
                                                                             AUTHORIZATION_ID,
                                                                             RequestHeaders.empty(),
                                                                             RequestParams.empty(),
                                                                             requestBody);
        assertResponseHeaders(response.getHeaders());
        Assertions.assertThat(response.getBody()).isEqualTo(responseBody);
    }

    @Test
    void updateConsentsPsuData_PsuAuthentication() {
        UpdatePsuAuthenticationResponse responseBody = new UpdatePsuAuthenticationResponse();
        UpdatePsuAuthentication requestBody = new UpdatePsuAuthentication();

        doReturn(entity).when(client).updateConsentsPsuData(eq(CONSENT_ID),
                                                            eq(AUTHORIZATION_ID),
                                                            anyMap(),
                                                            anyMap(),
                                                            any());
        doReturn(HTTP_STATUS_OK).when(entity).getStatusCodeValue();
        doReturn(responseBody).when(entity).getBody();
        doReturn(buildHttpHeaders()).when(entity).getHeaders();

        Response<UpdatePsuAuthenticationResponse> response = service.updateConsentsPsuData(CONSENT_ID,
                                                                                           AUTHORIZATION_ID,
                                                                                           RequestHeaders.empty(),
                                                                                           RequestParams.empty(),
                                                                                           requestBody);
        assertResponseHeaders(response.getHeaders());
        Assertions.assertThat(response.getBody()).isEqualTo(responseBody);
    }

    @Test
    void getAccountList() {
        AccountList responseBody = new AccountList();

        doReturn(responseBody).when(entity).getBody();
        doReturn(entity).when(client).getAccountList(false, Collections.emptyMap());
        doReturn(HTTP_STATUS_OK).when(entity).getStatusCodeValue();
        doReturn(responseBody).when(entity).getBody();
        doReturn(buildHttpHeaders()).when(entity).getHeaders();


        Response<AccountList> response = service.getAccountList(RequestHeaders.empty(), RequestParams.empty());

        assertResponseHeaders(response.getHeaders());
        Assertions.assertThat(response.getBody()).isEqualTo(responseBody);
    }

    @Test
    void readAccountDetails() {
        OK200AccountDetails responseBody = new OK200AccountDetails();
        String accountId = "accountId";

        doReturn(responseBody).when(entity).getBody();
        doReturn(entity).when(client).readAccountDetails(accountId, false, Collections.emptyMap());
        doReturn(HTTP_STATUS_OK).when(entity).getStatusCodeValue();
        doReturn(responseBody).when(entity).getBody();
        doReturn(buildHttpHeaders()).when(entity).getHeaders();


        Response<OK200AccountDetails> response = service.readAccountDetails(accountId, RequestHeaders.empty(),
            RequestParams.empty());

        assertResponseHeaders(response.getHeaders());
        Assertions.assertThat(response.getBody()).isEqualTo(responseBody);
    }

    @Test
    void getTransactionList_NotAJsonContentType() {
        Map<String, String> headersMap = new HashMap<>();
        headersMap.put(RequestHeaders.CONTENT_TYPE, "application/xml");
        RequestHeaders headers = RequestHeaders.fromMap(headersMap);
        assertThrows(NotAcceptableException.class, () -> service.getTransactionList(ACCOUNT_ID,
                                                                                    headers,
                                                                                    RequestParams.empty()));
    }

    @Test
    void getTransactionDetails() {
        OK200TransactionDetails responseBody = new OK200TransactionDetails();

        doReturn(responseBody).when(entity).getBody();
        doReturn(entity).when(client).getTransactionDetails(eq(ACCOUNT_ID), eq(TRANSACTION_ID), anyMap(), anyMap());
        doReturn(HTTP_STATUS_OK).when(entity).getStatusCodeValue();
        doReturn(responseBody).when(entity).getBody();
        doReturn(buildHttpHeaders()).when(entity).getHeaders();


        Response<OK200TransactionDetails> response = service.getTransactionDetails(ACCOUNT_ID,
                                                                                   TRANSACTION_ID,
                                                                                   RequestHeaders.empty(),
                                                                                   RequestParams.empty());

        assertResponseHeaders(response.getHeaders());
        Assertions.assertThat(response.getBody()).isEqualTo(responseBody);
    }

    @Test
    void getConsentScaStatus() {
        ScaStatusResponse responseBody = new ScaStatusResponse();

        doReturn(responseBody).when(entity).getBody();
        doReturn(entity).when(client).getConsentScaStatus(eq(CONSENT_ID), eq(AUTHORIZATION_ID), anyMap(), anyMap());
        doReturn(HTTP_STATUS_OK).when(entity).getStatusCodeValue();
        doReturn(responseBody).when(entity).getBody();
        doReturn(buildHttpHeaders()).when(entity).getHeaders();


        Response<ScaStatusResponse> response = service.getConsentScaStatus(CONSENT_ID,
                                                                           AUTHORIZATION_ID,
                                                                           RequestHeaders.empty(),
                                                                           RequestParams.empty());

        assertResponseHeaders(response.getHeaders());
        Assertions.assertThat(response.getBody()).isEqualTo(responseBody);
    }

    @Test
    void getBalances() {
        ReadAccountBalanceResponse200 responseBody = new ReadAccountBalanceResponse200();

        doReturn(responseBody).when(entity).getBody();
        doReturn(entity).when(client).getBalances(eq(ACCOUNT_ID), anyMap(), anyMap());
        doReturn(HTTP_STATUS_OK).when(entity).getStatusCodeValue();
        doReturn(responseBody).when(entity).getBody();
        doReturn(buildHttpHeaders()).when(entity).getHeaders();


        Response<ReadAccountBalanceResponse200> response = service.getBalances(ACCOUNT_ID,
                                                                               RequestHeaders.empty(),
                                                                               RequestParams.empty());

        assertResponseHeaders(response.getHeaders());
        Assertions.assertThat(response.getBody()).isEqualTo(responseBody);
    }

    @Test
    void getCardAccountList() {
        CardAccountList responseBody = new CardAccountList();

        doReturn(responseBody).when(entity).getBody();
        doReturn(entity).when(client).getCardAccount(anyMap(), anyMap());
        doReturn(HTTP_STATUS_OK).when(entity).getStatusCodeValue();
        doReturn(responseBody).when(entity).getBody();
        doReturn(buildHttpHeaders()).when(entity).getHeaders();


        Response<CardAccountList> response = service.getCardAccountList(RequestHeaders.empty(),
                                                                        RequestParams.empty());

        assertResponseHeaders(response.getHeaders());
        Assertions.assertThat(response.getBody()).isEqualTo(responseBody);
    }

    @Test
    void getCardAccountDetails() {
        OK200CardAccountDetails responseBody = new OK200CardAccountDetails();

        doReturn(responseBody).when(entity).getBody();
        doReturn(entity).when(client).ReadCardAccount(eq(ACCOUNT_ID), anyMap(), anyMap());
        doReturn(HTTP_STATUS_OK).when(entity).getStatusCodeValue();
        doReturn(responseBody).when(entity).getBody();
        doReturn(buildHttpHeaders()).when(entity).getHeaders();


        Response<OK200CardAccountDetails> response = service.getCardAccountDetails(ACCOUNT_ID,
                                                                                   RequestHeaders.empty(),
                                                                                   RequestParams.empty());

        assertResponseHeaders(response.getHeaders());
        Assertions.assertThat(response.getBody()).isEqualTo(responseBody);
    }

    @Test
    void getCardAccountBalances() {
        ReadCardAccountBalanceResponse200 responseBody = new ReadCardAccountBalanceResponse200();

        doReturn(responseBody).when(entity).getBody();
        doReturn(entity).when(client).getCardAccountBalances(eq(ACCOUNT_ID), anyMap(), anyMap());
        doReturn(HTTP_STATUS_OK).when(entity).getStatusCodeValue();
        doReturn(responseBody).when(entity).getBody();
        doReturn(buildHttpHeaders()).when(entity).getHeaders();


        Response<ReadCardAccountBalanceResponse200> response = service.getCardAccountBalances(ACCOUNT_ID,
                                                                                              RequestHeaders.empty(),
                                                                                              RequestParams.empty());

        assertResponseHeaders(response.getHeaders());
        Assertions.assertThat(response.getBody()).isEqualTo(responseBody);
    }

    @Test
    void getCardAccountTransactionList() {
        String dateFrom = "2020-07-20";
        String dateTo = "2020-07-21";
        String entryReferenceFrom = "entryReferenceFrom";
        String bookingStatus = "both";
        CardAccountsTransactionsResponse200 responseBody = new CardAccountsTransactionsResponse200();
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put(RequestParams.DATE_FROM, dateFrom);
        paramsMap.put(RequestParams.DATE_TO, dateTo);
        paramsMap.put(RequestParams.ENTRY_REFERENCE_FROM, entryReferenceFrom);
        paramsMap.put(RequestParams.BOOKING_STATUS, bookingStatus);
        paramsMap.put(RequestParams.DELTA_LIST, "true");
        paramsMap.put(RequestParams.WITH_BALANCE, "false");
        RequestParams requestParams = RequestParams.fromMap(paramsMap);

        doReturn(responseBody).when(entity).getBody();
        doReturn(entity).when(client).getCardAccountTransactionList(eq(ACCOUNT_ID),
                                                                    eq(requestParams.dateFrom()),
                                                                    eq(requestParams.dateTo()),
                                                                    eq(entryReferenceFrom),
                                                                    eq(BookingStatus.BOTH),
                                                                    eq(requestParams.deltaList()),
                                                                    eq(requestParams.withBalance()),
                                                                    anyMap(),
                                                                    anyMap());
        doReturn(HTTP_STATUS_OK).when(entity).getStatusCodeValue();
        doReturn(responseBody).when(entity).getBody();
        doReturn(buildHttpHeaders()).when(entity).getHeaders();


        Response<CardAccountsTransactionsResponse200> response = service.getCardAccountTransactionList(ACCOUNT_ID,
                                                                                                       RequestHeaders.empty(),
                                                                                                       requestParams);

        assertResponseHeaders(response.getHeaders());
        Assertions.assertThat(response.getBody()).isEqualTo(responseBody);
    }

    @Test
    void getTransactionListAsString() throws JsonProcessingException {
        TransactionsResponse200Json report = buildTransactionReport();
        when(client.getTransactionListAsString(
            any(), any(), any(), any(), any(), any(), any(), any())
        ).thenReturn(buildResponseEntity(report));

        Response<String> response = service.getTransactionListAsString(
            "accountId",
            RequestHeaders.fromMap(buildHeaders()),
            RequestParams.fromMap(buildRequestParams())
        );

        assertThat(service.objectMapper.writeValueAsString(report)).isEqualTo(response.getBody());
    }

    @Test
    void getTransactionList() {
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

        assertThat(response.getBody()).isEqualTo(report);
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
