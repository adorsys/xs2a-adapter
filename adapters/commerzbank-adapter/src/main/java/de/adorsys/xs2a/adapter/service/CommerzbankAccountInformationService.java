package de.adorsys.xs2a.adapter.service;

import com.google.api.client.auth.oauth2.AuthorizationCodeTokenRequest;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import de.adorsys.xs2a.adapter.adapter.BaseAccountInformationService;
import de.adorsys.xs2a.adapter.service.model.*;
import de.adorsys.xs2a.adapter.service.mapper.BalanceReportMapper;
import de.adorsys.xs2a.adapter.service.mapper.TransactionsReportMapper;
import org.mapstruct.factory.Mappers;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Map;

public class CommerzbankAccountInformationService extends BaseAccountInformationService {

    private TransactionsReportMapper transactionsReportMapper = Mappers.getMapper(TransactionsReportMapper.class);
    private BalanceReportMapper balanceReportMapper = Mappers.getMapper(BalanceReportMapper.class);

    public CommerzbankAccountInformationService(String baseUrl) {
        super(baseUrl);
    }

    @Override
    public Response<AccountListHolder> getAccountList(RequestHeaders requestHeaders,
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
    public Response<TransactionsReport> getTransactionList(String accountId,
                                                           RequestHeaders requestHeaders,
                                                           RequestParams requestParams) {
        requestHeaders = withAuthorizationBearer(requestHeaders);
        return getTransactionList(accountId, requestHeaders, requestParams, CommerzbankTransactionsReport.class,
            transactionsReportMapper::toTransactionsReport);
    }

    @Override
    public Response<String> getTransactionListAsString(String accountId,
                                                       RequestHeaders requestHeaders,
                                                       RequestParams requestParams) {
        requestHeaders = withAuthorizationBearer(requestHeaders);
        return super.getTransactionListAsString(accountId, requestHeaders, requestParams);
    }

    @Override
    public Response<BalanceReport> getBalances(String accountId, RequestHeaders requestHeaders) {
        requestHeaders = withAuthorizationBearer(requestHeaders);
        return getBalances(accountId, requestHeaders, CommerzbankBalanceReport.class,
            balanceReportMapper::toBalanceReport);
    }
}
