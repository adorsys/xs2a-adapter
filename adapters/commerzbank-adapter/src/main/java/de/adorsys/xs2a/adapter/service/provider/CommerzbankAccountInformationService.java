package de.adorsys.xs2a.adapter.service.provider;

import com.google.api.client.auth.oauth2.AuthorizationCodeTokenRequest;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import de.adorsys.xs2a.adapter.adapter.BaseAccountInformationService;
import de.adorsys.xs2a.adapter.service.GeneralResponse;
import de.adorsys.xs2a.adapter.service.RequestHeaders;
import de.adorsys.xs2a.adapter.service.RequestParams;
import de.adorsys.xs2a.adapter.service.account.AccountListHolder;
import de.adorsys.xs2a.adapter.service.account.BalanceReport;
import de.adorsys.xs2a.adapter.service.account.TransactionsReport;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Map;

public class CommerzbankAccountInformationService extends BaseAccountInformationService {
    public CommerzbankAccountInformationService(String baseUrl) {
        super(baseUrl);
    }

    @Override
    public GeneralResponse<AccountListHolder> getAccountList(RequestHeaders requestHeaders,
                                                             RequestParams requestParams) {
        requestHeaders = withAuthorizationBearer(requestHeaders);
        return super.getAccountList(requestHeaders, requestParams);
    }

    private RequestHeaders withAuthorizationBearer(RequestHeaders requestHeaders) {
        Map<String, String> headers = requestHeaders.toMap();
        String accessToken = getAccessToken();
        headers.put("Authorization", "Bearer " + accessToken);
        return RequestHeaders.fromMap(headers);
    }

    private String getAccessToken() {
        try {
            TokenResponse tokenResponse = new AuthorizationCodeTokenRequest(new NetHttpTransport(), new JacksonFactory(),
                    new GenericUrl("https://psd2.api-sandbox.commerzbank.com/berlingroup/v1/token"), "AIS_VALID_CODE")
                .setRedirectUri("http://localhost")
                .set("client_id", "VALID_CLIENT_ID")
                .set("code_verifier", "sha256")
                .execute();
            return tokenResponse.getAccessToken();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public GeneralResponse<TransactionsReport> getTransactionList(String accountId,
                                                                  RequestHeaders requestHeaders,
                                                                  RequestParams requestParams) {
        requestHeaders = withAuthorizationBearer(requestHeaders);
        return super.getTransactionList(accountId, requestHeaders, requestParams);
    }

    @Override
    public GeneralResponse<String> getTransactionListAsString(String accountId,
                                                              RequestHeaders requestHeaders,
                                                              RequestParams requestParams) {
        requestHeaders = withAuthorizationBearer(requestHeaders);
        return super.getTransactionListAsString(accountId, requestHeaders, requestParams);
    }

    @Override
    public GeneralResponse<BalanceReport> getBalances(String accountId, RequestHeaders requestHeaders) {
        requestHeaders = withAuthorizationBearer(requestHeaders);
        return super.getBalances(accountId, requestHeaders);
    }
}
