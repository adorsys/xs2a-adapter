package de.adorsys.xs2a.adapter.sparkasse;

import de.adorsys.xs2a.adapter.api.AccountInformationService;
import de.adorsys.xs2a.adapter.api.RequestParams;
import de.adorsys.xs2a.adapter.api.Response;
import de.adorsys.xs2a.adapter.api.model.*;
import de.adorsys.xs2a.adapter.test.ServiceWireMockTest;
import de.adorsys.xs2a.adapter.test.TestRequestResponse;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@ServiceWireMockTest(SparkasseServiceProvider.class)
class SparkasseAccountInformationServiceWireMockTest {
    private static final String CONSENT_ID = "e3d6fd32-8e41-498b-a20a-c643215e420c";
    private static final String AUTHORISATION_ID = "a7129418-87e2-43c3-ba57-38aa2e23093b";
    private static final String ACCOUNT_ID = "3217d050-f5b5-4318-a799-413bce784ef6";
    private final AccountInformationService service;

    SparkasseAccountInformationServiceWireMockTest(AccountInformationService service) {
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

        Response<StartScaprocessResponse> response = service.startConsentAuthorisation(CONSENT_ID,
            requestResponse.requestHeaders(),
            RequestParams.empty(),
            requestResponse.requestBody(UpdatePsuAuthentication.class));

        assertThat(response.getBody()).isEqualTo(requestResponse.responseBody(StartScaprocessResponse.class));
    }

    @Test
    void selectScaMethod() throws Exception {
        TestRequestResponse requestResponse = new TestRequestResponse("ais/select-sca-method.json");
        SelectPsuAuthenticationMethodResponse expected = requestResponse.responseBody(SelectPsuAuthenticationMethodResponse.class);

        Response<SelectPsuAuthenticationMethodResponse> response = service.updateConsentsPsuData(CONSENT_ID,
            AUTHORISATION_ID,
            requestResponse.requestHeaders(),
            RequestParams.empty(),
            requestResponse.requestBody(SelectPsuAuthenticationMethod.class));

        SelectPsuAuthenticationMethodResponse actual = response.getBody();

        assertThat(actual).isEqualToIgnoringGivenFields(expected, "chosenScaMethod");
        assertThat(actual.getChosenScaMethod()).isEqualToComparingFieldByField(expected.getChosenScaMethod());
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

        Response<String> response = service.getTransactionListAsString(ACCOUNT_ID,
            requestResponse.requestHeaders(),
            requestResponse.requestParams());

        assertThat(response.getBody()).isEqualTo(requestResponse.responseBody());
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

        assertThat(response.getStatusCode()).isEqualTo(202);
    }
}
