package de.adorsys.xs2a.adapter.service.ing;

import de.adorsys.xs2a.adapter.http.HttpClient;
import de.adorsys.xs2a.adapter.service.Oauth2Service;
import de.adorsys.xs2a.adapter.service.Pkcs12KeyStore;
import de.adorsys.xs2a.adapter.service.Response;
import de.adorsys.xs2a.adapter.service.ResponseHeaders;
import de.adorsys.xs2a.adapter.service.config.AdapterConfig;
import de.adorsys.xs2a.adapter.service.ing.internal.api.AccountInformationApi;
import de.adorsys.xs2a.adapter.service.ing.internal.api.ClientAuthentication;
import de.adorsys.xs2a.adapter.service.ing.internal.api.ClientAuthenticationFactory;
import de.adorsys.xs2a.adapter.service.ing.internal.api.Oauth2Api;
import de.adorsys.xs2a.adapter.service.ing.internal.service.IngOauth2Service;
import de.adorsys.xs2a.adapter.service.link.LinksRewriter;
import de.adorsys.xs2a.adapter.service.model.Link;
import de.adorsys.xs2a.adapter.service.model.TokenResponse;
import de.adorsys.xs2a.adapter.service.psd2.Psd2AccountInformationService;
import de.adorsys.xs2a.adapter.service.psd2.model.*;
import org.mapstruct.factory.Mappers;

import java.net.URI;
import java.security.GeneralSecurityException;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Collections.emptyMap;

public class IngPsd2AccountInformationService implements Psd2AccountInformationService, Oauth2Service {

    private final IngOauth2Service oauth2Service;
    private final AccountInformationApi accountInformationApi;
    private final LinksRewriter linksRewriter;
    private final IngMapper mapper = Mappers.getMapper(IngMapper.class);

    public IngPsd2AccountInformationService(String baseUri,
                                            HttpClient httpClient,
                                            Pkcs12KeyStore keyStore,
                                            LinksRewriter linksRewriter)
        throws GeneralSecurityException {
        this.linksRewriter = linksRewriter;

        accountInformationApi = new AccountInformationApi(baseUri, httpClient);
        Oauth2Api oauth2Api = new Oauth2Api(baseUri, httpClient);

        String qsealAlias = AdapterConfig.readProperty("ing.qseal.alias");
        X509Certificate qsealCertificate = keyStore.getQsealCertificate(qsealAlias);
        PrivateKey qsealPrivateKey = keyStore.getQsealPrivateKey(qsealAlias);
        ClientAuthenticationFactory clientAuthenticationFactory =
            new ClientAuthenticationFactory(qsealCertificate, qsealPrivateKey);
        oauth2Service = new IngOauth2Service(oauth2Api, clientAuthenticationFactory);
    }

    @Override
    public Response<ConsentsResponse> createConsent(Map<String, String> queryParameters,
                                                    Map<String, String> headers,
                                                    Consents consents) {
        return toResponse(new ConsentsResponse());
    }

    private <T> Response<T> toResponse(T body) {
        return new Response<>(200, body, ResponseHeaders.fromMap(emptyMap()));
    }

    @Override
    public Response<ConsentInformationResponse> getConsentInformation(String consentId,
                                                                      Map<String, String> queryParameters,
                                                                      Map<String, String> headers) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Response<Void> deleteConsent(String consentId,
                                        Map<String, String> queryParameters,
                                        Map<String, String> headers) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Response<ConsentStatusResponse> getConsentStatus(String consentId,
                                                            Map<String, String> queryParameters,
                                                            Map<String, String> headers) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Response<ScaStatusResponse> getConsentScaStatus(String consentId,
                                                           String authorisationId,
                                                           Map<String, String> queryParameters,
                                                           Map<String, String> headers) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Response<CardAccountList> getCardAccountList(Map<String, String> queryParameters,
                                                        Map<String, String> headers) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Response<CardAccountDetailsResponse> getCardAccountDetails(String accountId,
                                                                      Map<String, String> queryParameters,
                                                                      Map<String, String> headers) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Response<ReadCardAccountBalanceResponse> getCardAccountBalances(String accountId,
                                                                           Map<String, String> queryParameters,
                                                                           Map<String, String> headers) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Response<CardAccountsTransactionsResponse> getCardAccountTransactionList(String accountId,
                                                                                    Map<String, String> queryParameters,
                                                                                    Map<String, String> headers) {
        return getCardAccountTransactionList(accountId, new QueryParameters(queryParameters), new Headers(headers));
    }

    private Response<CardAccountsTransactionsResponse> getCardAccountTransactionList(String accountId,
                                                                                     QueryParameters queryParameters,
                                                                                     Headers headers) {
        String accessToken = headers.getAccessToken();
        ClientAuthentication clientAuthentication = oauth2Service.getClientAuthentication(accessToken);
        LocalDate dateFrom = queryParameters.getDateFrom();
        LocalDate dateTo = queryParameters.getDateTo();
        Integer limit = queryParameters.getLimit();
        Response<CardAccountsTransactionsResponse> response = accountInformationApi.getCardAccountTransactions(
            accountId,
            dateFrom,
            dateTo,
            limit,
            headers.getRequestId(),
            clientAuthentication);

        // rewrite links
        CardAccountsTransactionsResponse body = response.getBody();
        if (body != null) {
            body.setLinks(rewriteLinks(body.getLinks()));
            CardAccountReport cardTransactions = body.getCardTransactions();
            if (cardTransactions != null) {
                cardTransactions.setLinks(rewriteLinks(cardTransactions.getLinks()));
            }
        }
        return response;
    }

    @Override
    public Response<StartScaProcessResponse> startConsentAuthorisation(String consentId,
                                                                       Map<String, String> queryParameters,
                                                                       Map<String, String> headers,
                                                                       UpdateAuthorisation updateAuthentication) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Response<UpdateAuthorisationResponse> updateConsentsPsuData(String consentId,
                                                                       String authorisationId,
                                                                       Map<String, String> queryParameters,
                                                                       Map<String, String> headers,
                                                                       UpdateAuthorisation updateAuthentication) {
        throw new UnsupportedOperationException();
    }

    @Override
    public URI getAuthorizationRequestUri(Map<String, String> headers, Parameters parameters) {
        return oauth2Service.getAuthorizationRequestUri(parameters);
    }

    @Override
    public TokenResponse getToken(Map<String, String> headers, Parameters parameters) {
        return mapper.map(oauth2Service.getToken(parameters));
    }

    @Override
    public Response<AccountList> getAccounts(Map<String, String> queryParameters, Map<String, String> headers) {
        return getAccounts(new Headers(headers));
    }

    private Response<AccountList> getAccounts(Headers headers) {
        ClientAuthentication clientAuthentication = oauth2Service.getClientAuthentication(headers.getAccessToken());
        Response<AccountList> response = accountInformationApi.getAccounts(headers.getRequestId(), clientAuthentication)
            .map(mapper::map);

        rewriteLinks(response.getBody());
        return response;
    }

    private void rewriteLinks(AccountList accountList) {
        Optional.ofNullable(accountList)
            .map(AccountList::getAccounts)
            .ifPresent(accounts -> accounts.forEach(acc -> {
                    acc.setLinks(rewriteLinks(acc.getLinks()));
                }
            ));
    }

    private Map<String, HrefType> rewriteLinks(Map<String, HrefType> links) {
        Map<String, Link> xs2aLinks = mapper.mapToXs2aLinks(links);
        Map<String, Link> rewrittenLinks = linksRewriter.rewrite(xs2aLinks);
        return mapper.mapToPsd2Links(rewrittenLinks);
    }

    @Override
    public Response<ReadAccountBalanceResponse> getBalances(String accountId,
                                                            Map<String, String> queryParameters,
                                                            Map<String, String> headers) {
        return getBalances(accountId, new QueryParameters(queryParameters), new Headers(headers));
    }

    private Response<ReadAccountBalanceResponse> getBalances(String accountId,
                                                             QueryParameters queryParameters,
                                                             Headers headers) {
        String accessToken = headers.getAccessToken();
        ClientAuthentication clientAuthentication = oauth2Service.getClientAuthentication(accessToken);
        Currency currency = queryParameters.getCurrency();
        List<String> balanceTypes = queryParameters.get("balanceTypes", this::parseBalanceTypes);
        return accountInformationApi.getBalances(accountId,
            balanceTypes,
            currency,
            headers.getRequestId(),
            clientAuthentication)
            .map(mapper::map);
    }

    private List<String> parseBalanceTypes(String value) {
        if (value == null) {
            return null;
        }
        return Arrays.stream(value.split(","))
            .collect(Collectors.toList());
    }

    @Override
    public Response<TransactionsResponse> getTransactions(String accountId,
                                                          Map<String, String> queryParameters,
                                                          Map<String, String> headers) {
        return getTransactions(accountId, new QueryParameters(queryParameters), new Headers(headers));
    }

    @Override
    public Response<TransactionDetailsResponse> getTransactionDetails(String accountId,
                                                                      String transactionId,
                                                                      Map<String, String> queryParameters,
                                                                      Map<String, String> headers) {
        throw new UnsupportedOperationException();
    }

    private Response<TransactionsResponse> getTransactions(String accountId,
                                                           QueryParameters queryParameters,
                                                           Headers headers) {
        String accessToken = headers.getAccessToken();
        ClientAuthentication clientAuthentication = oauth2Service.getClientAuthentication(accessToken);
        LocalDate dateFrom = queryParameters.getDateFrom();
        LocalDate dateTo = queryParameters.getDateTo();
        Currency currency = queryParameters.getCurrency();
        Integer limit = queryParameters.getLimit();
        Response<TransactionsResponse> response = accountInformationApi.getTransactions(accountId,
            dateFrom,
            dateTo,
            currency,
            limit,
            headers.getRequestId(),
            clientAuthentication)
            .map(mapper::map);

        rewriteLinks(response.getBody());
        return response;
    }

    private void rewriteLinks(TransactionsResponse transactionsResponse) {
        Optional.ofNullable(transactionsResponse)
            .ifPresent(body -> {
                body.setLinks(rewriteLinks(body.getLinks()));

                Optional.ofNullable(body.getTransactions())
                    .ifPresent(ts -> {
                        ts.setLinks(rewriteLinks(ts.getLinks()));

                        rewriteLinks(ts.getBooked());
                        rewriteLinks(ts.getPending());
                    });
            });
    }

    private void rewriteLinks(List<TransactionDetails> transactionDetails) {
        Optional.ofNullable(transactionDetails)
            .ifPresent(td -> td.forEach(t -> t.setLinks(rewriteLinks(t.getLinks()))));
    }
}
