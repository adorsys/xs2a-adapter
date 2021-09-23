package de.adorsys.xs2a.adapter.commerzbank;

import de.adorsys.xs2a.adapter.api.AccountInformationService;
import de.adorsys.xs2a.adapter.api.RequestParams;
import de.adorsys.xs2a.adapter.api.model.*;
import de.adorsys.xs2a.adapter.test.ServiceWireMockTest;
import de.adorsys.xs2a.adapter.test.TestRequestResponse;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@ServiceWireMockTest(CommerzbankServiceProvider.class)
class CommerzbankAccountInformationServiceWireMockTest {

    private static final String CONSENT_ID = "VALID_CONSENT_ID";
    private static final String ACCOUNT_ID = "ACCOUNT_ID";
    private static final String AUTHORISATION_ID = "11111111-1111-1111-1111-111111111111";

    private final AccountInformationService accountInformationService;

    CommerzbankAccountInformationServiceWireMockTest(AccountInformationService accountInformationService) {
        this.accountInformationService = accountInformationService;
    }

    @Test
    void createConsent() throws IOException {
        var requestResponse = new TestRequestResponse("ais/create-consent.json");

        var response = accountInformationService.createConsent(requestResponse.requestHeaders(),
            RequestParams.empty(),
            requestResponse.requestBody(Consents.class));

        assertThat(response.getBody())
            .isEqualTo(requestResponse.responseBody(ConsentsResponse201.class));
    }

    @Test
    void deleteConsent() throws IOException {
        var requestResponse = new TestRequestResponse("ais/delete-consent.json");

        var response = accountInformationService.deleteConsent(CONSENT_ID,
            requestResponse.requestHeaders(),
            RequestParams.empty());

        assertThat(response.getStatusCode())
            .isEqualTo(204);
    }

    @Test
    void getAccounts() throws IOException {
        var requestResponse = new TestRequestResponse("ais/get-accounts.json");

        var response = accountInformationService.getAccountList(requestResponse.requestHeaders(),
            requestResponse.requestParams());

        assertThat(response.getBody())
            .isEqualTo(requestResponse.responseBody(AccountList.class));
    }

    @Test
    void getBalances() throws IOException {
        var requestResponse = new TestRequestResponse("ais/get-balances.json");

        var response = accountInformationService.getBalances(ACCOUNT_ID,
            requestResponse.requestHeaders(),
            RequestParams.empty());

        assertThat(response.getBody())
            .isEqualTo(requestResponse.responseBody(ReadAccountBalanceResponse200.class));
    }

    @Test
    void getConsentAuthorisation() throws IOException {
        var requestResponse = new TestRequestResponse("ais/get-consent-authorisation.json");

        var response = accountInformationService.getConsentAuthorisation(CONSENT_ID,
            requestResponse.requestHeaders(),
            RequestParams.empty());

        assertThat(response.getBody())
            .isEqualTo(requestResponse.responseBody(Authorisations.class));
    }

    @Test
    void getConsentStatus() throws IOException {
        var requestResponse = new TestRequestResponse("ais/get-consent-status.json");

        var response = accountInformationService.getConsentStatus(CONSENT_ID,
            requestResponse.requestHeaders(),
            RequestParams.empty());

        assertThat(response.getBody())
            .isEqualTo(requestResponse.responseBody(ConsentStatusResponse200.class));
    }

    @Test
    void getScaStatus() throws IOException {
        var requestResponse = new TestRequestResponse("ais/get-sca-status.json");

        var response = accountInformationService.getConsentScaStatus(CONSENT_ID,
            AUTHORISATION_ID,
            requestResponse.requestHeaders(),
            RequestParams.empty());

        assertThat(response.getBody())
            .isEqualTo(requestResponse.responseBody(ScaStatusResponse.class));
    }

    @Test
    void getTransactions() throws IOException {
        var requestResponse = new TestRequestResponse("ais/get-transactions.json");

        var response = accountInformationService.getTransactionList(ACCOUNT_ID,
            requestResponse.requestHeaders(),
            requestResponse.requestParams());

        assertThat(response.getBody())
            .isEqualTo(requestResponse.responseBody(TransactionsResponse200Json.class));
    }
}
