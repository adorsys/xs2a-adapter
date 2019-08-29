package de.adorsys.xs2a.adapter.service.ing.internal.api;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpExecuteInterceptor;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.util.ObjectParser;
import de.adorsys.xs2a.adapter.service.ing.internal.api.model.AccountsResponse;
import de.adorsys.xs2a.adapter.service.ing.internal.api.model.BalancesResponse;
import de.adorsys.xs2a.adapter.service.ing.internal.api.model.TransactionsResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Currency;
import java.util.List;
import java.util.Objects;

public class AccountInformationApi {
    private static final String ACCOUNTS_ENDPOINT = "/v2/accounts";
    private static final String TRANSACTIONS_ENDPOINT = "/v2/accounts/{{accountId}}/transactions";
    private static final String BALANCES_ENDPOINT = "/v2/accounts/{{accountId}}/balances";

    private final Host host;
    private final HttpTransport transport;
    private final ObjectParser parser;

    public AccountInformationApi(Host host, HttpTransport transport, ObjectParser parser) {
        this.host = host;
        this.transport = transport;
        this.parser = parser;
    }

    public AccountsResponse getAccounts(HttpExecuteInterceptor clientAuthentication) throws IOException {
        return transport.createRequestFactory()
            .buildGetRequest(new GenericUrl(host.toString() + ACCOUNTS_ENDPOINT))
            .setInterceptor(clientAuthentication)
            .setParser(parser)
            .execute()
            .parseAs(AccountsResponse.class);
    }

    public TransactionsResponse getTransactions(String resourceId,
                                                LocalDate dateFrom,
                                                LocalDate dateTo,
                                                Currency currency,
                                                Integer limit,
                                                HttpExecuteInterceptor clientAuthentication) throws IOException {
        GenericUrl url = new GenericUrl(host.toString() + TRANSACTIONS_ENDPOINT.replace("{{accountId}}", Objects.requireNonNull(resourceId)));
        url.set("dateFrom", dateFrom);
        url.set("dateTo", dateTo);
        url.set("currency", currency);
        url.set("limit", limit);

        return transport.createRequestFactory()
            .buildGetRequest(url)
            .setInterceptor(clientAuthentication)
            .setParser(parser)
            .execute()
            .parseAs(TransactionsResponse.class);
    }

    /**
     * @param balanceTypes (optional) A comma separated list of ISO20022 balance type(s)
     * @param currency (optional/conditional) 3 Letter ISO Currency Code (ISO 4217) for which transactions are requested. Required in case transactions are requested for a multi-currency account.
     */
    public BalancesResponse getBalances(String resourceId,
                                        List<String> balanceTypes,
                                        Currency currency,
                                        HttpExecuteInterceptor clientAuthentication) throws IOException {
        GenericUrl url = new GenericUrl(host.toString() + BALANCES_ENDPOINT.replace("{{accountId}}", Objects.requireNonNull(resourceId)));
        url.set("balanceTypes", balanceTypes == null ? null : String.join(",", balanceTypes));
        url.set("currency", currency);
        return transport.createRequestFactory()
            .buildGetRequest(url)
            .setInterceptor(clientAuthentication)
            .setParser(parser)
            .execute()
            .parseAs(BalancesResponse.class);
    }
}
