/*
 * Copyright 2018-2022 adorsys GmbH & Co KG
 *
 * This program is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or (at
 * your option) any later version. This program is distributed in the hope that
 * it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see https://www.gnu.org/licenses/.
 *
 * This project is also available under a separate commercial license. You can
 * contact us at psd2@adorsys.com.
 */

package de.adorsys.xs2a.adapter.verlag;

import de.adorsys.xs2a.adapter.api.AccountInformationService;
import de.adorsys.xs2a.adapter.api.RequestParams;
import de.adorsys.xs2a.adapter.api.Response;
import de.adorsys.xs2a.adapter.api.model.*;
import de.adorsys.xs2a.adapter.test.ServiceWireMockTest;
import de.adorsys.xs2a.adapter.test.TestRequestResponse;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@ServiceWireMockTest(TestVerlagServiceProvider.class)
class VerlagAccountInformationServiceWireMockTest {
    private static final String CONSENT_ID = "cWpVQSW1X4peDA49LYWH3BnqXgDPkAdbTUf4flwrBcBGfRJPXJzpBFL-ZNGkNtYmeTllIL2aPmJr5erBhdvaj_SdMWF3876hAweK_n7HJlg=_=_psGLvQpt9Q";
    private static final String AUTHORISATION_ID = "356e6d7d-9242-4264-8cb5-892e0758d69c";
    private static final String ACCOUNT_ID = "3a4c851658957bc30d767425679769fc380120315d6da67faa52f016723ec9a2";

    private final AccountInformationService service;

    VerlagAccountInformationServiceWireMockTest(AccountInformationService service) {
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

        Response<String> response = service.getTransactionListAsString(ACCOUNT_ID,
            requestResponse.requestHeaders(),
            requestResponse.requestParams());

        assertThat(response.getBody()).isEqualTo(requestResponse.responseBody());
    }

    @Test
    void getBalances() throws IOException {
        TestRequestResponse requestResponse = new TestRequestResponse("ais/get-balances.json");

        Response<ReadAccountBalanceResponse200> response = service.getBalances(ACCOUNT_ID,
            requestResponse.requestHeaders(),
            RequestParams.empty());

        assertThat(response.getBody()).isEqualTo(requestResponse.responseBody(ReadAccountBalanceResponse200.class));
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
