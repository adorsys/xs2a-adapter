package de.adorsys.xs2a.adapter.deutschebank;

import de.adorsys.xs2a.adapter.api.AccountInformationService;
import de.adorsys.xs2a.adapter.api.RequestParams;
import de.adorsys.xs2a.adapter.api.Response;
import de.adorsys.xs2a.adapter.api.model.*;
import de.adorsys.xs2a.adapter.test.ServiceWireMockTest;
import de.adorsys.xs2a.adapter.test.TestRequestResponse;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@ServiceWireMockTest(DeutscheBankServiceProvider.class)
class DeutscheBankAccountInformationServiceWireMockTest {

    private static final String CONSENT_ID = "17501be7-1dcd-4ad5-ab30-6d02b170e31a";
    private static final String AUTHORISATION_ID = "9cd38df2-1315-41d4-9602-5f63149aacaf";
    private static final String ACCOUNT_ID = "8724B81FA1BDDF1775B8C8354221849E";

    private final AccountInformationService service;

    DeutscheBankAccountInformationServiceWireMockTest(AccountInformationService service) {
        this.service = service;
    }

    @Test
    void createConsent() throws Exception {
        TestRequestResponse requestResponse = new TestRequestResponse("ais/create-consent.json");

        Response<ConsentsResponse201> response = service.createConsent(requestResponse.requestHeaders(),
            RequestParams.empty(),
            requestResponse.requestBody(Consents.class));

        assertThat(response.getBody()).isEqualTo(requestResponse.responseBody(ConsentsResponse201.class));
    }

    @Test
    void authenticatePsu() throws Exception {
        TestRequestResponse requestResponse = new TestRequestResponse("ais/authenticate-psu.json");

        Response<UpdatePsuAuthenticationResponse> response = service.updateConsentsPsuData(CONSENT_ID,
            AUTHORISATION_ID,
            requestResponse.requestHeaders(),
            RequestParams.empty(),
            requestResponse.requestBody(UpdatePsuAuthentication.class));

        assertThat(response.getBody()).isEqualTo(requestResponse.responseBody(UpdatePsuAuthenticationResponse.class));
    }

    @Test
    void selectScaMethod() throws Exception {
        TestRequestResponse requestResponse = new TestRequestResponse("ais/select-sca-method.json");

        Response<SelectPsuAuthenticationMethodResponse> response = service.updateConsentsPsuData(CONSENT_ID,
            AUTHORISATION_ID,
            requestResponse.requestHeaders(),
            RequestParams.empty(),
            requestResponse.requestBody(SelectPsuAuthenticationMethod.class));

        assertThat(response.getBody())
            .isEqualTo(requestResponse.responseBody(SelectPsuAuthenticationMethodResponse.class));
    }

    @Test
    void authoriseTransaction() throws Exception {
        TestRequestResponse requestResponse = new TestRequestResponse("ais/authorise-transaction.json");

        Response<ScaStatusResponse> response = service.updateConsentsPsuData(CONSENT_ID,
            AUTHORISATION_ID,
            requestResponse.requestHeaders(),
            RequestParams.empty(),
            requestResponse.requestBody(TransactionAuthorisation.class));

        assertThat(response.getBody()).isEqualTo(requestResponse.responseBody(ScaStatusResponse.class));
    }

    @Test
    void getAccounts() throws Exception {
        TestRequestResponse requestResponse = new TestRequestResponse("ais/get-accounts.json");

        Response<AccountList> response = service.getAccountList(requestResponse.requestHeaders(),
            requestResponse.requestParams());

        assertThat(response.getBody()).isEqualTo(requestResponse.responseBody(AccountList.class));
    }

    @Test
    void getTransactions() throws Exception {
        TestRequestResponse requestResponse = new TestRequestResponse("ais/get-transactions.json");

        Response<TransactionsResponse200Json> response = service.getTransactionList(ACCOUNT_ID,
            requestResponse.requestHeaders(),
            requestResponse.requestParams());

        assertThat(response.getBody()).isEqualTo(requestResponse.responseBody(TransactionsResponse200Json.class));
    }

    @Test
    void getScaStatus() throws Exception {
        TestRequestResponse requestResponse = new TestRequestResponse("ais/get-sca-status.json");

        Response<ScaStatusResponse> response = service.getConsentScaStatus(CONSENT_ID,
            AUTHORISATION_ID,
            requestResponse.requestHeaders(),
            RequestParams.empty());

        assertThat(response.getBody()).isEqualTo(requestResponse.responseBody(ScaStatusResponse.class));
    }

    @Test
    void getConsentStatus() throws Exception {
        TestRequestResponse requestResponse = new TestRequestResponse("ais/get-consent-status.json");

        Response<ConsentStatusResponse200> response = service.getConsentStatus(CONSENT_ID,
            requestResponse.requestHeaders(),
            RequestParams.empty());

        assertThat(response.getBody()).isEqualTo(requestResponse.responseBody(ConsentStatusResponse200.class));
    }

    @Test
    void deleteConsent() throws Exception {
        TestRequestResponse requestResponse = new TestRequestResponse("ais/delete-consent.json");

        Response<Void> response = service.deleteConsent(CONSENT_ID,
            requestResponse.requestHeaders(),
            RequestParams.empty());

        assertThat(response.getStatusCode()).isEqualTo(204);
    }
}
