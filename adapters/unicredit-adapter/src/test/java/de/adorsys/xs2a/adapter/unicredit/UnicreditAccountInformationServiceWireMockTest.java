package de.adorsys.xs2a.adapter.unicredit;

import de.adorsys.xs2a.adapter.api.AccountInformationService;
import de.adorsys.xs2a.adapter.api.RequestParams;
import de.adorsys.xs2a.adapter.api.exception.ErrorResponseException;
import de.adorsys.xs2a.adapter.api.model.*;
import de.adorsys.xs2a.adapter.test.ServiceWireMockTest;
import de.adorsys.xs2a.adapter.test.TestRequestResponse;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.InstanceOfAssertFactories.optional;
import static org.assertj.core.api.InstanceOfAssertFactories.type;

@ServiceWireMockTest(UnicreditServiceProvider.class)
class UnicreditAccountInformationServiceWireMockTest {

    private static final String CONSENT_ID = "12f6ed9d-fd09-4aea-97bc-aeb61052cf3e";
    private static final String ACCOUNT_ID = "3dc3d5b37023";
    private static final String AUTHORISATION_ID = "3dc3d5b37023";

    private final AccountInformationService accountInformationService;

    UnicreditAccountInformationServiceWireMockTest(AccountInformationService accountInformationService) {
        this.accountInformationService = accountInformationService;
    }

    @Test
    void createConsent() throws Exception {
        var requestResponse = new TestRequestResponse("ais/create-consent.json");

        var response = accountInformationService.createConsent(requestResponse.requestHeaders(),
            RequestParams.empty(),
            requestResponse.requestBody(Consents.class));

        assertThat(response.getBody()).isEqualTo(requestResponse.responseBody(ConsentsResponse201.class));
    }

    @Test
    void deleteConsent() throws Exception {
        var requestResponse = new TestRequestResponse("ais/delete-consent.json");

        var response = accountInformationService.deleteConsent(CONSENT_ID,
            requestResponse.requestHeaders(),
            RequestParams.empty());

        assertThat(response.getStatusCode()).isEqualTo(204);
    }

    @Test
    void getAccounts() throws Exception {
        var requestResponse = new TestRequestResponse("ais/get-accounts.json");

        var response = accountInformationService.getAccountList(requestResponse.requestHeaders(),
            requestResponse.requestParams());

        assertThat(response.getBody()).isEqualTo(requestResponse.responseBody(AccountList.class));
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
        var requestHeaders = requestResponse.requestHeaders();
        var params = RequestParams.empty();

        assertThatThrownBy(() -> accountInformationService.getConsentScaStatus(CONSENT_ID,
            AUTHORISATION_ID,
            requestHeaders,
            params))
            .asInstanceOf(type(ErrorResponseException.class))
            .matches(er -> 404 == er.getStatusCode())
            .extracting(ErrorResponseException::getErrorResponse, optional(ErrorResponse.class))
            .contains(requestResponse.responseBody(ErrorResponse.class));
    }

    @Test
    void getTransactions() throws Exception {
        var requestResponse = new TestRequestResponse("ais/get-transactions.json");

        var response = accountInformationService.getTransactionList(ACCOUNT_ID,
            requestResponse.requestHeaders(),
            requestResponse.requestParams());

        assertThat(response.getBody())
            .isEqualTo(requestResponse.responseBody(TransactionsResponse200Json.class));
    }

    @Test
    void authoriseTransaction() throws Exception {
        var requestResponse = new TestRequestResponse("ais/authorise-transaction.json");

        var response = accountInformationService.updateConsentsPsuData(CONSENT_ID,
            AUTHORISATION_ID,
            requestResponse.requestHeaders(),
            requestResponse.requestParams(),
            requestResponse.requestBody(TransactionAuthorisation.class));

        assertThat(response.getBody()).isEqualTo(requestResponse.responseBody(ScaStatusResponse.class));
    }

    @Test
    void authenticatePsu() throws Exception {
        var requestResponse = new TestRequestResponse("ais/authenticate-psu.json");

        var response = accountInformationService.startConsentAuthorisation(CONSENT_ID,
            requestResponse.requestHeaders(),
            RequestParams.empty());

        assertThat(response.getBody()).isEqualTo(requestResponse.responseBody(StartScaprocessResponse.class));
    }

    @Test
    void updatePsuAuthentication() throws IOException {
        var requestResponse = new TestRequestResponse("ais/update-psu-authentication.json");

        var response = accountInformationService.updateConsentsPsuData(CONSENT_ID,
            AUTHORISATION_ID,
            requestResponse.requestHeaders(),
            requestResponse.requestParams(),
            requestResponse.requestBody(UpdatePsuAuthentication.class));

        assertThat(response.getBody())
            .isEqualTo(requestResponse.responseBody(UpdatePsuAuthenticationResponse.class));
    }
}
