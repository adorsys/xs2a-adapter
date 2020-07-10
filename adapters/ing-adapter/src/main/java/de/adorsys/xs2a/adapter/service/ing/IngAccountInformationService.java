package de.adorsys.xs2a.adapter.service.ing;

import de.adorsys.xs2a.adapter.api.model.*;
import de.adorsys.xs2a.adapter.http.JacksonObjectMapper;
import de.adorsys.xs2a.adapter.http.JsonMapper;
import de.adorsys.xs2a.adapter.service.*;
import de.adorsys.xs2a.adapter.service.ing.internal.api.AccountInformationApi;
import de.adorsys.xs2a.adapter.service.ing.internal.api.ClientAuthentication;
import de.adorsys.xs2a.adapter.service.ing.internal.service.IngOauth2Service;
import de.adorsys.xs2a.adapter.service.link.LinksRewriter;
import de.adorsys.xs2a.adapter.service.model.TokenResponse;
import org.mapstruct.factory.Mappers;

import java.net.URI;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Collections.emptyMap;

public class IngAccountInformationService implements AccountInformationService, Oauth2Service {

    private final IngOauth2Service oauth2Service;
    private final AccountInformationApi accountInformationApi;
    private final LinksRewriter linksRewriter;
    private final IngMapper mapper = Mappers.getMapper(IngMapper.class);
    private final JsonMapper jsonMapper = new JacksonObjectMapper();

    public IngAccountInformationService(AccountInformationApi accountInformationApi,
                                        IngOauth2Service oauth2Service,
                                        LinksRewriter linksRewriter) {
        this.accountInformationApi = accountInformationApi;
        this.oauth2Service = oauth2Service;
        this.linksRewriter = linksRewriter;
    }

    @Override
    public Response<ConsentsResponse201> createConsent(RequestHeaders requestHeaders,
                                                       RequestParams requestParams,
                                                       Consents body) {
        return toResponse(new ConsentsResponse201());
    }

    private <T> Response<T> toResponse(T body) {
        return new Response<>(200, body, ResponseHeaders.fromMap(emptyMap()));
    }

    @Override
    public Response<ConsentInformationResponse200Json> getConsentInformation(String consentId,
                                                                             RequestHeaders requestHeaders,
                                                                             RequestParams requestParams) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Response<Void> deleteConsent(String consentId,
                                        RequestHeaders requestHeaders,
                                        RequestParams requestParams) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Response<ConsentStatusResponse200> getConsentStatus(String consentId,
                                                               RequestHeaders requestHeaders,
                                                               RequestParams requestParams) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Response<StartScaprocessResponse> startConsentAuthorisation(String consentId,
                                                                       RequestHeaders requestHeaders,
                                                                       RequestParams requestParams) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Response<StartScaprocessResponse> startConsentAuthorisation(String consentId,
                                                                       RequestHeaders requestHeaders,
                                                                       RequestParams requestParams,
                                                                       UpdatePsuAuthentication updatePsuAuthentication) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Response<SelectPsuAuthenticationMethodResponse> updateConsentsPsuData(String consentId,
                                                                                 String authorisationId,
                                                                                 RequestHeaders requestHeaders,
                                                                                 RequestParams requestParams,
                                                                                 SelectPsuAuthenticationMethod selectPsuAuthenticationMethod) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Response<ScaStatusResponse> updateConsentsPsuData(String consentId,
                                                             String authorisationId,
                                                             RequestHeaders requestHeaders,
                                                             RequestParams requestParams,
                                                             TransactionAuthorisation transactionAuthorisation) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Response<UpdatePsuAuthenticationResponse> updateConsentsPsuData(String consentId,
                                                                           String authorisationId,
                                                                           RequestHeaders requestHeaders,
                                                                           RequestParams requestParams,
                                                                           UpdatePsuAuthentication updatePsuAuthentication) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Response<ScaStatusResponse> getConsentScaStatus(String consentId,
                                                           String authorisationId,
                                                           RequestHeaders requestHeaders,
                                                           RequestParams requestParams) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Response<CardAccountList> getCardAccountList(RequestHeaders requestHeaders, RequestParams requestParams) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Response<OK200CardAccountDetails> getCardAccountDetails(String accountId,
                                                                   RequestHeaders requestHeaders,
                                                                   RequestParams requestParams) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Response<ReadCardAccountBalanceResponse200> getCardAccountBalances(String accountId,
                                                                              RequestHeaders requestHeaders,
                                                                              RequestParams requestParams) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Response<CardAccountsTransactionsResponse200> getCardAccountTransactionList(String accountId,
                                                                                       RequestHeaders requestHeaders,
                                                                                       RequestParams requestParams) {
        String accessToken = requestHeaders.getAccessToken().orElse(null);
        ClientAuthentication clientAuthentication = oauth2Service.getClientAuthentication(accessToken);
        LocalDate dateFrom = requestParams.dateFrom();
        LocalDate dateTo = requestParams.dateTo();
        Integer limit = requestParams.get("limit", Integer::valueOf);
        Response<CardAccountsTransactionsResponse200> response = accountInformationApi.getCardAccountTransactions(
            accountId,
            dateFrom,
            dateTo,
            limit,
            requestHeaders.get(RequestHeaders.X_REQUEST_ID).orElse(null),
            clientAuthentication);

        // rewrite links
        CardAccountsTransactionsResponse200 body = response.getBody();
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
    public URI getAuthorizationRequestUri(Map<String, String> headers, Parameters parameters) {
        return oauth2Service.getAuthorizationRequestUri(parameters);
    }

    @Override
    public TokenResponse getToken(Map<String, String> headers, Parameters parameters) {
        return mapper.map(oauth2Service.getToken(parameters));
    }

    @Override
    public Response<AccountList> getAccountList(RequestHeaders requestHeaders,
                                                RequestParams requestParams) {
        String accessToken = requestHeaders.getAccessToken().orElse(null);
        ClientAuthentication clientAuthentication = oauth2Service.getClientAuthentication(accessToken);
        String requestId = requestHeaders.get(RequestHeaders.X_REQUEST_ID).orElse(null);
        Response<AccountList> response = accountInformationApi.getAccounts(requestId, clientAuthentication)
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
        return linksRewriter.rewrite(links);
    }

    @Override
    public Response<ReadAccountBalanceResponse200> getBalances(String accountId,
                                                               RequestHeaders requestHeaders,
                                                               RequestParams requestParams) {
        String accessToken = requestHeaders.getAccessToken().orElse(null);
        ClientAuthentication clientAuthentication = oauth2Service.getClientAuthentication(accessToken);
        Currency currency = requestParams.get("currency", Currency::getInstance);
        String requestId = requestHeaders.get(RequestHeaders.X_REQUEST_ID).orElse(null);
        List<String> balanceTypes = requestParams.get("balanceTypes", this::parseBalanceTypes);
        return accountInformationApi.getBalances(accountId,
            balanceTypes,
            currency,
            requestId,
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
    public Response<TransactionsResponse200Json> getTransactionList(String accountId,
                                                                    RequestHeaders requestHeaders,
                                                                    RequestParams requestParams) {
        String accessToken = requestHeaders.getAccessToken().orElse(null);
        ClientAuthentication clientAuthentication = oauth2Service.getClientAuthentication(accessToken);
        LocalDate dateFrom = requestParams.dateFrom();
        LocalDate dateTo = requestParams.dateTo();
        Currency currency = requestParams.get("currency", Currency::getInstance);
        Integer limit = requestParams.get("limit", Integer::valueOf);
        String requestId = requestHeaders.get(RequestHeaders.X_REQUEST_ID).orElse(null);
        Response<TransactionsResponse200Json> response = accountInformationApi.getTransactions(accountId,
            dateFrom,
            dateTo,
            currency,
            limit,
            requestId,
            clientAuthentication)
            .map(mapper::map);

        rewriteLinks(response.getBody());
        return response;
    }

    @Override
    public Response<OK200TransactionDetails> getTransactionDetails(String accountId,
                                                                   String transactionId,
                                                                   RequestHeaders requestHeaders,
                                                                   RequestParams requestParams) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Response<String> getTransactionListAsString(String accountId,
                                                       RequestHeaders requestHeaders,
                                                       RequestParams requestParams) {
        return getTransactionList(accountId, requestHeaders, requestParams)
            .map(jsonMapper::writeValueAsString);
    }

    private void rewriteLinks(TransactionsResponse200Json transactionsResponse) {
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
