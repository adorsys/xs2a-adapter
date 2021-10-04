package de.adorsys.xs2a.adapter.santander;

import de.adorsys.xs2a.adapter.api.AccountInformationService;
import de.adorsys.xs2a.adapter.api.RequestParams;
import de.adorsys.xs2a.adapter.api.Response;
import de.adorsys.xs2a.adapter.api.model.*;
import de.adorsys.xs2a.adapter.test.ServiceWireMockTest;
import de.adorsys.xs2a.adapter.test.TestRequestResponse;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@ServiceWireMockTest(SantanderServiceProvider.class)
class SantanderAccountInformationServiceWireMockTest {

    private static final String ACCOUNT_ID = "3b5a3b70-ceec-4518-b23e-ee5d1302f532";
    private static final String CONSENT_ID = "3087d8e2-2eb0-4e54-9af9-32ea8c6eef02";

    private final AccountInformationService accountInformationService;

    SantanderAccountInformationServiceWireMockTest(AccountInformationService accountInformationService) {
        this.accountInformationService = accountInformationService;
    }

    @Test
    void createConsent() throws IOException {
        TestRequestResponse requestResponse = new TestRequestResponse("ais/create-consent.json");

        Response<ConsentsResponse201> response = accountInformationService.createConsent(requestResponse.requestHeaders(),
            RequestParams.empty(),
            requestResponse.requestBody(Consents.class));

        assertThat(response.getBody())
            .isEqualTo(requestResponse.responseBody(ConsentsResponse201.class));
    }

    @Test
    void getAccounts() throws IOException {
        TestRequestResponse requestResponse = new TestRequestResponse("ais/get-accounts.json");

        Response<AccountList> response = accountInformationService.getAccountList(requestResponse.requestHeaders(),
           requestResponse.requestParams());

        assertThat(response.getBody())
            .isEqualTo(requestResponse.responseBody(AccountList.class));
    }

    @Test
    void getTransactions() throws IOException {
        TestRequestResponse requestResponse = new TestRequestResponse("ais/get-transactions.json");

        Response<TransactionsResponse200Json> response = accountInformationService.getTransactionList(ACCOUNT_ID,
            requestResponse.requestHeaders(),
            requestResponse.requestParams());

        assertThat(response.getBody())
            .isEqualTo(requestResponse.responseBody(TransactionsResponse200Json.class));
    }

    @Test
    void getBalances() throws IOException {
        TestRequestResponse requestResponse = new TestRequestResponse("ais/get-balances.json");

        Response<ReadAccountBalanceResponse200> response = accountInformationService.getBalances(ACCOUNT_ID,
            requestResponse.requestHeaders(),
            RequestParams.empty());

        assertThat(response.getBody())
            .isEqualTo(requestResponse.responseBody(ReadAccountBalanceResponse200.class));
    }

    @Test
    void getConsentStatus() throws IOException {
        TestRequestResponse requestResponse = new TestRequestResponse("ais/get-consent-status.json");

        Response<ConsentStatusResponse200> response = accountInformationService.getConsentStatus(CONSENT_ID,
            requestResponse.requestHeaders(),
            RequestParams.empty());

        assertThat(response.getBody())
            .isEqualTo(requestResponse.responseBody(ConsentStatusResponse200.class));
    }

    @Test
    void deleteConsent() throws IOException {
        TestRequestResponse requestResponse = new TestRequestResponse("ais/delete-consent.json");

        Response<Void> response = accountInformationService.deleteConsent(CONSENT_ID,
            requestResponse.requestHeaders(),
            RequestParams.empty());

        assertThat(response.getStatusCode())
            .isEqualTo(204);
    }
}
