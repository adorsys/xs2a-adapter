package de.adorsys.xs2a.adapter.ing;

import de.adorsys.xs2a.adapter.api.model.AccountList;
import de.adorsys.xs2a.adapter.api.model.TransactionsResponse200Json;
import de.adorsys.xs2a.adapter.service.AccountInformationService;
import de.adorsys.xs2a.adapter.service.RequestParams;
import de.adorsys.xs2a.adapter.service.Response;
import de.adorsys.xs2a.adapter.test.ServiceWireMockTest;
import de.adorsys.xs2a.adapter.test.TestRequestResponse;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@ServiceWireMockTest(IngServiceProvider.class)
class IngAccountInformationServiceWireMockTest {
    protected static final String ACCOUNT_ID = "a217d676-7559-4f2a-83dc-5da0c2279223";
    private final AccountInformationService service;

    IngAccountInformationServiceWireMockTest(AccountInformationService service) {
        this.service = service;
    }

    @Test
    void getAccounts() throws Exception {
        TestRequestResponse requestResponse = new TestRequestResponse("ais/get-accounts.json");

        Response<AccountList> response = service.getAccountList(requestResponse.requestHeaders(), RequestParams.empty());

        assertThat(response.getBody()).isEqualTo(requestResponse.responseBody(AccountList.class));
    }

    @Test
    void getTransactions() throws Exception {
        TestRequestResponse requestResponse = new TestRequestResponse("ais/get-transactions.json");

        Response<TransactionsResponse200Json> response =
            service.getTransactionList(ACCOUNT_ID, requestResponse.requestHeaders(), requestResponse.requestParams());

        assertThat(response.getBody()).isEqualTo(requestResponse.responseBody(TransactionsResponse200Json.class));
    }
}
