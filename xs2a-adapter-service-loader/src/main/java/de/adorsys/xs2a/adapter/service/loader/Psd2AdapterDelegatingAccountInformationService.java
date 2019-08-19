package de.adorsys.xs2a.adapter.service.loader;

import de.adorsys.xs2a.adapter.service.RequestHeaders;
import de.adorsys.xs2a.adapter.service.model.TokenResponse;
import de.adorsys.xs2a.adapter.service.psd2.Psd2AccountInformationService;
import de.adorsys.xs2a.adapter.service.psd2.model.*;

import java.io.IOException;
import java.net.URI;
import java.util.Map;

public class Psd2AdapterDelegatingAccountInformationService implements Psd2AccountInformationService {

    private final Psd2AdapterServiceLoader adapterServiceLoader;

    public Psd2AdapterDelegatingAccountInformationService(Psd2AdapterServiceLoader adapterServiceLoader) {
        this.adapterServiceLoader = adapterServiceLoader;
    }

    @Override
    public ConsentsResponse createConsent(Map<String, String> headers, Consents consents) {
        return getAccountInformationService(headers)
            .createConsent(headers, consents);
    }

    private Psd2AccountInformationService getAccountInformationService(Map<String, String> headers) {
        return adapterServiceLoader.getPsd2AccountInformationService(RequestHeaders.fromMap(headers));
    }

    @Override
    public URI getAuthorizationRequestUri(Map<String, String> headers, String state, URI redirectUri) throws IOException {
        // fixme headers parameter is needed only for service loading
        return getAccountInformationService(headers)
            .getAuthorizationRequestUri(headers, state, redirectUri);
    }

    @Override
    public TokenResponse getToken(Map<String, String> headers, String authorizationCode) throws IOException {
        return getAccountInformationService(headers)
            .getToken(headers, authorizationCode);
    }

    @Override
    public AccountList getAccounts(Map<String, String> queryParameters,
                                   Map<String, String> headers) throws IOException {
        return getAccountInformationService(headers)
            .getAccounts(queryParameters, headers);
    }

    @Override
    public ReadAccountBalanceResponse getBalances(String accountId,
                                                  Map<String, String> queryParameters,
                                                  Map<String, String> headers) throws IOException {
        return getAccountInformationService(headers)
            .getBalances(accountId, queryParameters, headers);
    }

    @Override
    public TransactionsResponse getTransactions(String accountId,
                                                Map<String, String> queryParameters,
                                                Map<String, String> headers) throws IOException {
        return getAccountInformationService(headers)
            .getTransactions(accountId, queryParameters, headers);
    }
}
