package de.adorsys.xs2a.adapter.serviceloader;

import de.adorsys.xs2a.adapter.api.AccountInformationService;
import de.adorsys.xs2a.adapter.api.RequestHeaders;
import de.adorsys.xs2a.adapter.api.RequestParams;
import de.adorsys.xs2a.adapter.api.Response;
import de.adorsys.xs2a.adapter.api.model.*;

public class TestAccountInformationService implements AccountInformationService {
    @Override
    public Response<ConsentsResponse201> createConsent(RequestHeaders requestHeaders,
                                                       RequestParams requestParams,
                                                       Consents body) {
        return null;
    }

    @Override
    public Response<ConsentInformationResponse200Json> getConsentInformation(String consentId,
                                                                             RequestHeaders requestHeaders,
                                                                             RequestParams requestParams) {
        return null;
    }

    @Override
    public Response<Void> deleteConsent(String consentId,
                                        RequestHeaders requestHeaders,
                                        RequestParams requestParams) {
        return null;
    }

    @Override
    public Response<ConsentStatusResponse200> getConsentStatus(String consentId,
                                                               RequestHeaders requestHeaders,
                                                               RequestParams requestParams) {
        return null;
    }

    @Override
    public Response<StartScaprocessResponse> startConsentAuthorisation(String consentId,
                                                                       RequestHeaders requestHeaders,
                                                                       RequestParams requestParams) {
        return null;
    }

    @Override
    public Response<StartScaprocessResponse> startConsentAuthorisation(String consentId,
                                                                       RequestHeaders requestHeaders,
                                                                       RequestParams requestParams,
                                                                       UpdatePsuAuthentication updatePsuAuthentication) {
        return null;
    }

    @Override
    public Response<SelectPsuAuthenticationMethodResponse> updateConsentsPsuData(String consentId,
                                                                                 String authorisationId,
                                                                                 RequestHeaders requestHeaders,
                                                                                 RequestParams requestParams,
                                                                                 SelectPsuAuthenticationMethod selectPsuAuthenticationMethod) {
        return null;
    }

    @Override
    public Response<ScaStatusResponse> updateConsentsPsuData(String consentId,
                                                             String authorisationId,
                                                             RequestHeaders requestHeaders,
                                                             RequestParams requestParams,
                                                             TransactionAuthorisation transactionAuthorisation) {
        return null;
    }

    @Override
    public Response<UpdatePsuAuthenticationResponse> updateConsentsPsuData(String consentId,
                                                                           String authorisationId,
                                                                           RequestHeaders requestHeaders,
                                                                           RequestParams requestParams,
                                                                           UpdatePsuAuthentication updatePsuAuthentication) {
        return null;
    }

    @Override
    public Response<AccountList> getAccountList(RequestHeaders requestHeaders,
                                                RequestParams requestParams) {
        return null;
    }

    @Override
    public Response<TransactionsResponse200Json> getTransactionList(String accountId,
                                                                    RequestHeaders requestHeaders,
                                                                    RequestParams requestParams) {
        return null;
    }

    @Override
    public Response<String> getTransactionListAsString(String accountId,
                                                       RequestHeaders requestHeaders,
                                                       RequestParams requestParams) {
        return null;
    }

    @Override
    public Response<ScaStatusResponse> getConsentScaStatus(String consentId,
                                                           String authorisationId,
                                                           RequestHeaders requestHeaders,
                                                           RequestParams requestParams) {
        return null;
    }

    @Override
    public Response<ReadAccountBalanceResponse200> getBalances(String accountId,
                                                               RequestHeaders requestHeaders,
                                                               RequestParams requestParams) {
        return null;
    }

    @Override
    public Response<CardAccountList> getCardAccountList(RequestHeaders requestHeaders, RequestParams requestParams) {
        return null;
    }

    @Override
    public Response<OK200CardAccountDetails> getCardAccountDetails(String accountId, RequestHeaders requestHeaders, RequestParams requestParams) {
        return null;
    }

    @Override
    public Response<ReadCardAccountBalanceResponse200> getCardAccountBalances(String accountId, RequestHeaders requestHeaders, RequestParams requestParams) {
        return null;
    }

    @Override
    public Response<CardAccountsTransactionsResponse200> getCardAccountTransactionList(String accountId, RequestHeaders requestHeaders, RequestParams requestParams) {
        return null;
    }

    @Override
    public Response<OK200TransactionDetails> getTransactionDetails(String accountId,
                                                                   String transactionId,
                                                                   RequestHeaders requestHeaders,
                                                                   RequestParams requestParams) {
        return null;
    }
}
