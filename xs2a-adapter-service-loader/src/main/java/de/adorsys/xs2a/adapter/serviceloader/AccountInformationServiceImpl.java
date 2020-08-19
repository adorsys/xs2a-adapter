/*
 * Copyright 2018-2018 adorsys GmbH & Co KG
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.adorsys.xs2a.adapter.serviceloader;

import de.adorsys.xs2a.adapter.api.AccountInformationService;
import de.adorsys.xs2a.adapter.api.RequestHeaders;
import de.adorsys.xs2a.adapter.api.RequestParams;
import de.adorsys.xs2a.adapter.api.Response;
import de.adorsys.xs2a.adapter.api.model.*;

public class AccountInformationServiceImpl implements AccountInformationService {
    private final AdapterServiceLoader adapterServiceLoader;

    public AccountInformationServiceImpl(AdapterServiceLoader adapterServiceLoader) {
        this.adapterServiceLoader = adapterServiceLoader;
    }

    @Override
    public Response<ConsentsResponse201> createConsent(RequestHeaders requestHeaders,
                                                       RequestParams requestParams,
                                                       Consents consents) {
        return getAccountInformationService(requestHeaders)
                   .createConsent(requestHeaders, requestParams, consents);
    }

    private AccountInformationService getAccountInformationService(RequestHeaders requestHeaders) {
        return adapterServiceLoader.getAccountInformationService(requestHeaders);
    }

    @Override
    public Response<ConsentInformationResponse200Json> getConsentInformation(String consentId,
                                                                             RequestHeaders requestHeaders,
                                                                             RequestParams requestParams) {
        return getAccountInformationService(requestHeaders)
                   .getConsentInformation(consentId, requestHeaders, requestParams);
    }

    @Override
    public Response<Void> deleteConsent(String consentId,
                                        RequestHeaders requestHeaders,
                                        RequestParams requestParams) {
        return getAccountInformationService(requestHeaders)
            .deleteConsent(consentId, requestHeaders, requestParams);
    }

    @Override
    public Response<ConsentStatusResponse200> getConsentStatus(String consentId,
                                                               RequestHeaders requestHeaders,
                                                               RequestParams requestParams) {
        return getAccountInformationService(requestHeaders)
                   .getConsentStatus(consentId, requestHeaders, requestParams);
    }

    @Override
    public Response<StartScaprocessResponse> startConsentAuthorisation(String consentId,
                                                                       RequestHeaders requestHeaders,
                                                                       RequestParams requestParams) {
        return getAccountInformationService(requestHeaders)
                   .startConsentAuthorisation(consentId, requestHeaders, requestParams);
    }

    @Override
    public Response<StartScaprocessResponse> startConsentAuthorisation(String consentId,
                                                                       RequestHeaders requestHeaders,
                                                                       RequestParams requestParams,
                                                                       UpdatePsuAuthentication updatePsuAuthentication) {
        return getAccountInformationService(requestHeaders)
                   .startConsentAuthorisation(consentId, requestHeaders, requestParams, updatePsuAuthentication);
    }

    @Override
    public Response<SelectPsuAuthenticationMethodResponse> updateConsentsPsuData(String consentId,
                                                                                 String authorisationId,
                                                                                 RequestHeaders requestHeaders,
                                                                                 RequestParams requestParams,
                                                                                 SelectPsuAuthenticationMethod selectPsuAuthenticationMethod) {
        return getAccountInformationService(requestHeaders)
            .updateConsentsPsuData(consentId,
                authorisationId,
                requestHeaders,
                requestParams,
                selectPsuAuthenticationMethod);
    }

    @Override
    public Response<ScaStatusResponse> updateConsentsPsuData(String consentId,
                                                             String authorisationId,
                                                             RequestHeaders requestHeaders,
                                                             RequestParams requestParams,
                                                             TransactionAuthorisation transactionAuthorisation) {
        return getAccountInformationService(requestHeaders)
            .updateConsentsPsuData(consentId,
                authorisationId,
                requestHeaders,
                requestParams,
                transactionAuthorisation);
    }

    @Override
    public Response<UpdatePsuAuthenticationResponse> updateConsentsPsuData(String consentId,
                                                                           String authorisationId,
                                                                           RequestHeaders requestHeaders,
                                                                           RequestParams requestParams,
                                                                           UpdatePsuAuthentication updatePsuAuthentication) {
        return getAccountInformationService(requestHeaders)
            .updateConsentsPsuData(consentId,
                authorisationId,
                requestHeaders,
                requestParams,
                updatePsuAuthentication);
    }

    @Override
    public Response<AccountList> getAccountList(RequestHeaders requestHeaders,
                                                RequestParams requestParams) {
        return getAccountInformationService(requestHeaders)
                   .getAccountList(requestHeaders, requestParams);
    }

    @Override
    public Response<TransactionsResponse200Json> getTransactionList(String accountId, RequestHeaders requestHeaders,
                                                                    RequestParams requestParams) {
        return getAccountInformationService(requestHeaders)
                   .getTransactionList(accountId, requestHeaders, requestParams);
    }

    @Override
    public Response<OK200TransactionDetails> getTransactionDetails(String accountId,
                                                                   String transactionId,
                                                                   RequestHeaders requestHeaders,
                                                                   RequestParams requestParams) {
        return getAccountInformationService(requestHeaders)
            .getTransactionDetails(accountId, transactionId, requestHeaders, requestParams);
    }

    @Override
    public Response<String> getTransactionListAsString(String accountId, RequestHeaders requestHeaders,
                                                       RequestParams requestParams) {
        return getAccountInformationService(requestHeaders)
                   .getTransactionListAsString(accountId, requestHeaders, requestParams);
    }

    @Override
    public Response<ScaStatusResponse> getConsentScaStatus(String consentId,
                                                           String authorisationId,
                                                           RequestHeaders requestHeaders,
                                                           RequestParams requestParams) {
        return getAccountInformationService(requestHeaders)
                   .getConsentScaStatus(consentId, authorisationId, requestHeaders, requestParams);
    }

    @Override
    public Response<ReadAccountBalanceResponse200> getBalances(String accountId,
                                                               RequestHeaders requestHeaders,
                                                               RequestParams requestParams) {
        return getAccountInformationService(requestHeaders)
                   .getBalances(accountId, requestHeaders, requestParams);
    }

    @Override
    public Response<CardAccountList> getCardAccountList(RequestHeaders requestHeaders, RequestParams requestParams) {
        return getAccountInformationService(requestHeaders)
            .getCardAccountList(requestHeaders, requestParams);
    }

    @Override
    public Response<OK200CardAccountDetails> getCardAccountDetails(String accountId,
                                                                   RequestHeaders requestHeaders,
                                                                   RequestParams requestParams) {
        return getAccountInformationService(requestHeaders)
            .getCardAccountDetails(accountId, requestHeaders, requestParams);
    }

    @Override
    public Response<ReadCardAccountBalanceResponse200> getCardAccountBalances(String accountId,
                                                                              RequestHeaders requestHeaders,
                                                                              RequestParams requestParams) {
        return getAccountInformationService(requestHeaders)
            .getCardAccountBalances(accountId, requestHeaders, requestParams);
    }

    @Override
    public Response<CardAccountsTransactionsResponse200> getCardAccountTransactionList(String accountId,
                                                                                       RequestHeaders requestHeaders,
                                                                                       RequestParams requestParams) {
        return getAccountInformationService(requestHeaders)
            .getCardAccountTransactionList(accountId, requestHeaders, requestParams);
    }
}
