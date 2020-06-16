package de.adorsys.xs2a.adapter.service.ing.internal.api;

import de.adorsys.xs2a.adapter.api.model.CardAccountsTransactionsResponse200;
import de.adorsys.xs2a.adapter.http.HttpClient;
import de.adorsys.xs2a.adapter.http.Request;
import de.adorsys.xs2a.adapter.http.StringUri;
import de.adorsys.xs2a.adapter.service.RequestHeaders;
import de.adorsys.xs2a.adapter.service.Response;
import de.adorsys.xs2a.adapter.service.ing.internal.api.model.AccountsResponse;
import de.adorsys.xs2a.adapter.service.ing.internal.api.model.BalancesResponse;
import de.adorsys.xs2a.adapter.service.ing.internal.api.model.TransactionsResponse;

import java.time.LocalDate;
import java.util.*;

import static de.adorsys.xs2a.adapter.http.ResponseHandlers.jsonResponseHandler;

public class AccountInformationApi {
    private static final String ACCOUNTS_ENDPOINT = "/v3/accounts";
    private static final String TRANSACTIONS_ENDPOINT = "/v2/accounts/{{accountId}}/transactions";
    private static final String BALANCES_ENDPOINT = "/v3/accounts/{{accountId}}/balances";
    private static final String CARD_ACCOUNT_TRANSACTIONS_ENDPOINT = "/v1/card-accounts/{{accountId}}/transactions";

    private final String baseUri;
    private final HttpClient httpClient;

    public AccountInformationApi(String baseUri, HttpClient httpClient) {
        this.baseUri = baseUri;
        this.httpClient = httpClient;
    }

    public Response<AccountsResponse> getAccounts(String requestId,
                                                  Request.Builder.Interceptor clientAuthentication) {
        return httpClient.get(baseUri + ACCOUNTS_ENDPOINT)
            .header(RequestHeaders.X_REQUEST_ID, requestId)
            .send(clientAuthentication, jsonResponseHandler(AccountsResponse.class));
    }

    public Response<TransactionsResponse> getTransactions(String resourceId,
                                                          LocalDate dateFrom,
                                                          LocalDate dateTo,
                                                          Currency currency,
                                                          Integer limit,
                                                          String requestId,
                                                          Request.Builder.Interceptor clientAuthentication) {
        // fixme
        Map<String, Object> queryParams = new LinkedHashMap<>();
        queryParams.put("dateFrom", dateFrom);
        queryParams.put("dateTo", dateTo);
        queryParams.put("currency", currency);
        queryParams.put("limit", limit);
        String uri = StringUri.withQuery(
            baseUri + TRANSACTIONS_ENDPOINT.replace("{{accountId}}", Objects.requireNonNull(resourceId)),
            queryParams
        );

        return httpClient.get(uri)
            .header(RequestHeaders.X_REQUEST_ID, requestId)
            .send(clientAuthentication, jsonResponseHandler(TransactionsResponse.class));
    }

    /**
     * @param balanceTypes (optional) A comma separated list of ISO20022 balance type(s)
     * @param currency (optional/conditional) 3 Letter ISO Currency Code (ISO 4217)
     *                 for which transactions are requested. Required in case
     *                 transactions are requested for a multi-currency account.
     */
    public Response<BalancesResponse> getBalances(String resourceId,
                                                  List<String> balanceTypes,
                                                  Currency currency,
                                                  String requestId,
                                                  Request.Builder.Interceptor clientAuthentication) {
        Map<String, Object> queryParams = new LinkedHashMap<>();
        queryParams.put("balanceTypes", balanceTypes == null ? null : String.join(",", balanceTypes));
        queryParams.put("currency", currency);
        String uri = StringUri.withQuery(
            baseUri + BALANCES_ENDPOINT.replace("{{accountId}}", Objects.requireNonNull(resourceId)),
            queryParams
        );

        return httpClient.get(uri)
            .header(RequestHeaders.X_REQUEST_ID, requestId)
            .send(clientAuthentication, jsonResponseHandler(BalancesResponse.class));
    }

    public Response<CardAccountsTransactionsResponse200> getCardAccountTransactions(
        String accountId,
        LocalDate dateFrom,
        LocalDate dateTo,
        Integer limit,
        String requestId,
        Request.Builder.Interceptor clientAuthentication) {

        Map<String, Object> queryParams = new LinkedHashMap<>();
        queryParams.put("dateFrom", dateFrom);
        queryParams.put("dateTo", dateTo);
        queryParams.put("limit", limit);
        String uri = StringUri.withQuery(
            baseUri + CARD_ACCOUNT_TRANSACTIONS_ENDPOINT.replace("{{accountId}}", Objects.requireNonNull(accountId)),
            queryParams
        );

        return httpClient.get(uri)
            .header(RequestHeaders.X_REQUEST_ID, requestId)
            .send(clientAuthentication, jsonResponseHandler(CardAccountsTransactionsResponse200.class));
    }
}
