package de.adorsys.xs2a.adapter.crealogix;

import de.adorsys.xs2a.adapter.api.AccountInformationService;
import de.adorsys.xs2a.adapter.api.RequestParams;
import de.adorsys.xs2a.adapter.api.Response;
import de.adorsys.xs2a.adapter.api.ResponseHeaders;
import de.adorsys.xs2a.adapter.api.http.HttpClient;
import de.adorsys.xs2a.adapter.api.model.*;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class CrealogixAccountInformationServiceTest extends CrealogixTestHelper {

    public static final String ACCOUNT_ID = "accountId";
    public static final String CONSENT_ID = "consentId";
    public static final String AUTHORISATION_ID = "authorisationId";
    private static final String REMITTANCE_INFORMATION_STRUCTURED = "remittanceInformationStructuredStringValue";
    private AccountInformationService service;

    @BeforeEach
    void setUp() {
        when(httpClientFactory.getHttpClient(any())).thenReturn(httpClient);
        when(httpClientFactory.getHttpClientConfig()).thenReturn(httpClientConfig);

        service = new CrealogixAccountInformationService(aspsp, httpClientFactory, linksRewriter);
    }

    @Test
    void createConsent() {
        when(httpClient.post(anyString())).thenReturn(getRequestBuilder("POST"));
        when(httpClient.send(any(), any()))
            .thenReturn(new Response<>(-1,
                                       new ConsentsResponse201(),
                                       ResponseHeaders.emptyResponseHeaders()));

        Response<ConsentsResponse201> actualResponse
            = service.createConsent(getHeadersWithAuthorization(),
                                    RequestParams.empty(),
                                    new Consents());

        verify(httpClient, times(1)).post(anyString());
        verify(httpClient, times(1)).send(any(), any());

        assertThat(actualResponse)
            .isNotNull()
            .extracting(Response::getBody)
            .isInstanceOf(ConsentsResponse201.class);
    }

    @Test
    void getTransactionList() {
        String rawResponse = "{\n" +
            "  \"transactions\": {\n" +
            "    \"booked\": [\n" +
            "      {\n" +
            "        \"remittanceInformationStructured\": {" +
            "           \"reference\": \"" + REMITTANCE_INFORMATION_STRUCTURED + "\"\n" +
            "         }\n" +
            "      }\n" +
            "    ]\n" +
            "  }\n" +
            "}";

        when(httpClient.get(anyString()))
            .thenReturn(getRequestBuilder("GET"));
        when(httpClient.send(any(), any()))
            .thenAnswer(invocationOnMock -> {
                HttpClient.ResponseHandler responseHandler = invocationOnMock.getArgument(1, HttpClient.ResponseHandler.class);
                return new Response<>(-1,
                                      responseHandler.apply(200,
                                          new ByteArrayInputStream(rawResponse.getBytes()),
                                          ResponseHeaders.emptyResponseHeaders()),
                                      null);
            });

        Response<?> actualResponse
            = service.getTransactionList(ACCOUNT_ID,
                                         getHeadersWithAuthorization(),
                                         RequestParams.empty());

        verify(httpClient, times(1)).get(anyString());
        verify(httpClient, times(1)).send(any(), any());

        assertThat(actualResponse)
            .isNotNull()
            .extracting(Response::getBody)
            .asInstanceOf(InstanceOfAssertFactories.type(TransactionsResponse200Json.class))
            .matches(body ->
                body.getTransactions()
                    .getBooked()
                    .get(0)
                    .getRemittanceInformationStructured()
                    .equals(REMITTANCE_INFORMATION_STRUCTURED));
    }

    @Test
    void getTransactionDetails() {
        String rawResponse = "{\n" +
            "  \"transactionsDetails\": {\n" +
            "    \"remittanceInformationStructured\": {" +
            "       \"reference\": \"" + REMITTANCE_INFORMATION_STRUCTURED + "\"\n" +
            "       }\n" +
            "  }\n" +
            "}";

        when(httpClient.get(anyString()))
            .thenReturn(getRequestBuilder("GET"));
        when(httpClient.send(any(), any()))
            .thenAnswer(invocationOnMock -> {
                HttpClient.ResponseHandler responseHandler = invocationOnMock.getArgument(1, HttpClient.ResponseHandler.class);
                return new Response<>(-1,
                    responseHandler.apply(200,
                        new ByteArrayInputStream(rawResponse.getBytes()),
                        ResponseHeaders.emptyResponseHeaders()),
                    null);
            });

        Response<?> actualResponse = service.getTransactionDetails(ACCOUNT_ID,
                                                                   "transactionId",
                                                                   getHeadersWithAuthorization(),
                                                                   RequestParams.empty());

        verify(httpClient, times(1)).get(anyString());
        verify(httpClient, times(1)).send(any(), any());

        assertThat(actualResponse)
            .isNotNull()
            .extracting(Response::getBody)
            .asInstanceOf(InstanceOfAssertFactories.type(OK200TransactionDetails.class))
            .matches(body ->
                body.getTransactionsDetails()
                    .getRemittanceInformationStructured()
                    .equals(REMITTANCE_INFORMATION_STRUCTURED));
    }

    @Test
    void getConsentInformation() {
        when(httpClient.get(anyString()))
            .thenReturn(getRequestBuilder("GET"));
        when(httpClient.send(any(), any()))
            .thenReturn(getResponse(new ConsentInformationResponse200Json()));

        Response<ConsentInformationResponse200Json> actualResponse = service.getConsentInformation(CONSENT_ID,
            getHeadersWithAuthorization(),
            RequestParams.empty());

        assertThat(actualResponse)
            .isNotNull();
    }

    @Test
    void deleteConsent() {
        when(httpClient.delete(anyString()))
            .thenReturn(getRequestBuilder("DELETE"));
        when(httpClient.send(any(), any()))
            .thenReturn(getResponse(Void.TYPE));

        Response<Void> actualResponse = service.deleteConsent(CONSENT_ID,
            getHeadersWithAuthorization(),
            RequestParams.empty());

        assertThat(actualResponse)
            .isNotNull();
    }

    @Test
    void getConsentStatus() {
        when(httpClient.get(anyString()))
            .thenReturn(getRequestBuilder("GET"));
        when(httpClient.send(any(), any()))
            .thenReturn(getResponse(new ConsentStatusResponse200()));

        Response<ConsentStatusResponse200> actualResponse = service.getConsentStatus(CONSENT_ID,
            getHeadersWithAuthorization(),
            RequestParams.empty());

        assertThat(actualResponse)
            .isNotNull();
    }

    @Test
    void startConsentAuthorisation() {
        when(httpClient.post(anyString()))
            .thenReturn(getRequestBuilder("POST"));
        when(httpClient.send(any(), any()))
            .thenReturn(getResponse(new StartScaprocessResponse()));

        Response<StartScaprocessResponse> actualResponse = service.startConsentAuthorisation(CONSENT_ID,
            getHeadersWithAuthorization(),
            RequestParams.empty());

        assertThat(actualResponse)
            .isNotNull();
    }

    @Test
    void startConsentAuthorisationWithPsuAuthentication() {
        when(httpClient.post(anyString()))
            .thenReturn(getRequestBuilder("POST"));
        when(httpClient.send(any(), any()))
            .thenReturn(getResponse(new StartScaprocessResponse()));

        Response<StartScaprocessResponse> actualResponse = service.startConsentAuthorisation(CONSENT_ID,
            getHeadersWithAuthorization(),
            RequestParams.empty(),
            new UpdatePsuAuthentication());

        assertThat(actualResponse)
            .isNotNull();
    }

    @Test
    void updateConsentsPsuData_authenticatePsu() {
        when(httpClient.put(anyString()))
            .thenReturn(getRequestBuilder("PUT"));
        when(httpClient.send(any(), any()))
            .thenReturn(getResponse(new UpdatePsuAuthenticationResponse()));

        Response<UpdatePsuAuthenticationResponse> actualResponse = service.updateConsentsPsuData(CONSENT_ID,
            AUTHORISATION_ID,
            getHeadersWithAuthorization(),
            RequestParams.empty(),
            new UpdatePsuAuthentication());

        assertThat(actualResponse)
            .isNotNull();
    }

    @Test
    void updateConsentsPsuData_selectScaMethod() {
        when(httpClient.put(anyString()))
            .thenReturn(getRequestBuilder("PUT"));
        when(httpClient.send(any(), any()))
            .thenReturn(getResponse(new SelectPsuAuthenticationMethodResponse()));

        Response<SelectPsuAuthenticationMethodResponse> actualResponse = service.updateConsentsPsuData(CONSENT_ID,
            AUTHORISATION_ID,
            getHeadersWithAuthorization(),
            RequestParams.empty(),
            new SelectPsuAuthenticationMethod());

        assertThat(actualResponse)
            .isNotNull();
    }

    @Test
    void updateConsentsPsuData_authorizeTransaction() {
        when(httpClient.put(anyString()))
            .thenReturn(getRequestBuilder("PUT"));
        when(httpClient.send(any(), any()))
            .thenReturn(getResponse(new ScaStatusResponse()));

        Response<ScaStatusResponse> actualResponse = service.updateConsentsPsuData(CONSENT_ID,
            AUTHORISATION_ID,
            getHeadersWithAuthorization(),
            RequestParams.empty(),
            new TransactionAuthorisation());

        assertThat(actualResponse)
            .isNotNull();
    }

    @Test
    void getAccountList() {
        when(httpClient.get(anyString()))
            .thenReturn(getRequestBuilder("GET"));
        when(httpClient.send(any(), any()))
            .thenReturn(getResponse(new AccountList()));

        Response<AccountList> actualResponse = service.getAccountList(
            getHeadersWithAuthorization(),
            RequestParams.empty());

        assertThat(actualResponse)
            .isNotNull();
    }

    @Test
    void readAccountDetails() {
        when(httpClient.get(anyString()))
            .thenReturn(getRequestBuilder("GET"));
        when(httpClient.send(any(), any()))
            .thenReturn(getResponse(new OK200AccountDetails()));

        Response<OK200AccountDetails> actualResponse = service.readAccountDetails(
            "accountId",
            getHeadersWithAuthorization(),
            RequestParams.empty());

        assertThat(actualResponse)
            .isNotNull();
    }

    @Test
    void getTransactionListAsString() {
        when(httpClient.get(anyString()))
            .thenReturn(getRequestBuilder("GET"));
        when(httpClient.send(any(), any()))
            .thenReturn(getResponse("transactions"));

        Response<String> actualResponse = service.getTransactionListAsString(ACCOUNT_ID,
            getHeadersWithAuthorization(),
            RequestParams.empty());

        assertThat(actualResponse)
            .isNotNull();
    }

    @Test
    void getConsentScaStatus() {
        when(httpClient.get(anyString()))
            .thenReturn(getRequestBuilder("GET"));
        when(httpClient.send(any(), any()))
            .thenReturn(getResponse(new ScaStatusResponse()));

        Response<String> actualResponse = service.getTransactionListAsString(ACCOUNT_ID,
            getHeadersWithAuthorization(),
            RequestParams.empty());

        assertThat(actualResponse)
            .isNotNull();
    }

    @Test
    void getBalances() {
        when(httpClient.get(anyString()))
            .thenReturn(getRequestBuilder("GET"));
        when(httpClient.send(any(), any()))
            .thenReturn(getResponse(new ReadAccountBalanceResponse200()));

        Response<ReadAccountBalanceResponse200> actualResponse = service.getBalances(ACCOUNT_ID,
            getHeadersWithAuthorization(),
            RequestParams.empty());

        assertThat(actualResponse)
            .isNotNull();
    }

    @Test
    void getCardAccountList() {
        when(httpClient.get(anyString()))
            .thenReturn(getRequestBuilder("GET"));
        when(httpClient.send(any(), any()))
            .thenReturn(getResponse(new CardAccountList()));

        Response<CardAccountList> actualResponse = service.getCardAccountList(
            getHeadersWithAuthorization(),
            RequestParams.empty());

        assertThat(actualResponse)
            .isNotNull();
    }

    @Test
    void getCardAccountDetails() {
        when(httpClient.get(anyString()))
            .thenReturn(getRequestBuilder("GET"));
        when(httpClient.send(any(), any()))
            .thenReturn(getResponse(new OK200CardAccountDetails()));

        Response<OK200CardAccountDetails> actualResponse = service.getCardAccountDetails(ACCOUNT_ID,
            getHeadersWithAuthorization(),
            RequestParams.empty());

        assertThat(actualResponse)
            .isNotNull();
    }

    @Test
    void getCardAccountBalances() {
        when(httpClient.get(anyString()))
            .thenReturn(getRequestBuilder("GET"));
        when(httpClient.send(any(), any()))
            .thenReturn(getResponse(new ReadCardAccountBalanceResponse200()));

        Response<ReadCardAccountBalanceResponse200> actualResponse = service.getCardAccountBalances(ACCOUNT_ID,
            getHeadersWithAuthorization(),
            RequestParams.empty());

        assertThat(actualResponse)
            .isNotNull();
    }

    @Test
    void getCardAccountTransactionList() {
        when(httpClient.get(anyString()))
            .thenReturn(getRequestBuilder("GET"));
        when(httpClient.send(any(), any()))
            .thenReturn(getResponse(new CardAccountsTransactionsResponse200()));

        Response<CardAccountsTransactionsResponse200> actualResponse = service.getCardAccountTransactionList(ACCOUNT_ID,
            getHeadersWithAuthorization(),
            RequestParams.empty());

        assertThat(actualResponse)
            .isNotNull();
    }
}
