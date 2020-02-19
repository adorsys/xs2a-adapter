package de.adorsys.xs2a.adapter.service.impl;

import de.adorsys.xs2a.adapter.service.AccountInformationService;
import de.adorsys.xs2a.adapter.service.RequestHeaders;
import de.adorsys.xs2a.adapter.service.RequestParams;
import de.adorsys.xs2a.adapter.service.Response;
import de.adorsys.xs2a.adapter.service.model.*;

public class TestAccountInformationService implements AccountInformationService {
    @Override
    public Response<ConsentCreationResponse> createConsent(RequestHeaders requestHeaders,
                                                           RequestParams requestParams,
                                                           Consents body) {
        return null;
    }

    @Override
    public Response<ConsentInformation> getConsentInformation(String consentId,
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
    public Response<ConsentStatusResponse> getConsentStatus(String consentId, RequestHeaders requestHeaders) {
        return null;
    }

    @Override
    public Response<StartScaProcessResponse> startConsentAuthorisation(String consentId,
                                                                       RequestHeaders requestHeaders) {
        return null;
    }

    @Override
    public Response<StartScaProcessResponse> startConsentAuthorisation(String consentId,
                                                                       RequestHeaders requestHeaders,
                                                                       UpdatePsuAuthentication updatePsuAuthentication) {
        return null;
    }

    @Override
    public Response<SelectPsuAuthenticationMethodResponse> updateConsentsPsuData(String consentId,
                                                                                 String authorisationId,
                                                                                 RequestHeaders requestHeaders,
                                                                                 SelectPsuAuthenticationMethod selectPsuAuthenticationMethod) {
        return null;
    }

    @Override
    public Response<ScaStatusResponse> updateConsentsPsuData(String consentId,
                                                             String authorisationId,
                                                             RequestHeaders requestHeaders,
                                                             TransactionAuthorisation transactionAuthorisation) {
        return null;
    }

    @Override
    public Response<UpdatePsuAuthenticationResponse> updateConsentsPsuData(String consentId,
                                                                           String authorisationId,
                                                                           RequestHeaders requestHeaders,
                                                                           UpdatePsuAuthentication updatePsuAuthentication) {
        return null;
    }

    @Override
    public Response<AccountListHolder> getAccountList(RequestHeaders requestHeaders,
                                                      RequestParams requestParams) {
        return null;
    }

    @Override
    public Response<TransactionsReport> getTransactionList(String accountId,
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
                                                           RequestHeaders requestHeaders) {
        return null;
    }

    @Override
    public Response<BalanceReport> getBalances(String accountId, RequestHeaders requestHeaders) {
        return null;
    }

    @Override
    public Response<TransactionDetails> getTransactionDetails(String accountId,
                                                              String transactionId,
                                                              RequestHeaders requestHeaders) {
        return null;
    }
}
