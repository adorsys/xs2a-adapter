package de.adorsys.xs2a.adapter.service.impl;

import de.adorsys.xs2a.adapter.service.GeneralResponse;
import de.adorsys.xs2a.adapter.service.RequestHeaders;
import de.adorsys.xs2a.adapter.service.RequestParams;
import de.adorsys.xs2a.adapter.service.StartScaProcessResponse;
import de.adorsys.xs2a.adapter.service.account.AccountListHolder;
import de.adorsys.xs2a.adapter.service.account.BalanceReport;
import de.adorsys.xs2a.adapter.service.account.TransactionsReport;
import de.adorsys.xs2a.adapter.service.ais.*;
import de.adorsys.xs2a.adapter.service.model.*;

public class TestAccountInformationService implements AccountInformationService {
    @Override
    public GeneralResponse<ConsentCreationResponse> createConsent(RequestHeaders requestHeaders, Consents body) {
        return null;
    }

    @Override
    public GeneralResponse<ConsentInformation> getConsentInformation(String consentId, RequestHeaders requestHeaders) {
        return null;
    }

    @Override
    public GeneralResponse<Void> deleteConsent(String consentId, RequestHeaders requestHeaders) {
        return null;
    }

    @Override
    public GeneralResponse<ConsentStatusResponse> getConsentStatus(String consentId, RequestHeaders requestHeaders) {
        return null;
    }

    @Override
    public GeneralResponse<StartScaProcessResponse> startConsentAuthorisation(String consentId,
                                                                              RequestHeaders requestHeaders) {
        return null;
    }

    @Override
    public GeneralResponse<StartScaProcessResponse> startConsentAuthorisation(String consentId,
                                                                              RequestHeaders requestHeaders,
                                                                              UpdatePsuAuthentication updatePsuAuthentication) {
        return null;
    }

    @Override
    public GeneralResponse<SelectPsuAuthenticationMethodResponse> updateConsentsPsuData(String consentId,
                                                                                        String authorisationId,
                                                                                        RequestHeaders requestHeaders,
                                                                                        SelectPsuAuthenticationMethod selectPsuAuthenticationMethod) {
        return null;
    }

    @Override
    public GeneralResponse<ScaStatusResponse> updateConsentsPsuData(String consentId,
                                                                    String authorisationId,
                                                                    RequestHeaders requestHeaders,
                                                                    TransactionAuthorisation transactionAuthorisation) {
        return null;
    }

    @Override
    public GeneralResponse<UpdatePsuAuthenticationResponse> updateConsentsPsuData(String consentId,
                                                                                  String authorisationId,
                                                                                  RequestHeaders requestHeaders,
                                                                                  UpdatePsuAuthentication updatePsuAuthentication) {
        return null;
    }

    @Override
    public GeneralResponse<AccountListHolder> getAccountList(RequestHeaders requestHeaders,
                                                             RequestParams requestParams) {
        return null;
    }

    @Override
    public GeneralResponse<TransactionsReport> getTransactionList(String accountId,
                                                                  RequestHeaders requestHeaders,
                                                                  RequestParams requestParams) {
        return null;
    }

    @Override
    public GeneralResponse<String> getTransactionListAsString(String accountId,
                                                              RequestHeaders requestHeaders,
                                                              RequestParams requestParams) {
        return null;
    }

    @Override
    public GeneralResponse<ScaStatusResponse> getConsentScaStatus(String consentId,
                                                                  String authorisationId,
                                                                  RequestHeaders requestHeaders) {
        return null;
    }

    @Override
    public GeneralResponse<BalanceReport> getBalances(String accountId, RequestHeaders requestHeaders) {
        return null;
    }
}
