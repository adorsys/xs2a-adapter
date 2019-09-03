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

package de.adorsys.xs2a.adapter.service.impl;

import de.adorsys.xs2a.adapter.service.AccountInformationService;
import de.adorsys.xs2a.adapter.service.Response;
import de.adorsys.xs2a.adapter.service.RequestHeaders;
import de.adorsys.xs2a.adapter.service.RequestParams;
import de.adorsys.xs2a.adapter.service.model.StartScaProcessResponse;
import de.adorsys.xs2a.adapter.service.model.AccountListHolder;
import de.adorsys.xs2a.adapter.service.model.BalanceReport;
import de.adorsys.xs2a.adapter.service.model.TransactionsReport;
import de.adorsys.xs2a.adapter.service.model.*;

public class AccountInformationServiceImpl implements AccountInformationService {
    private final AdapterServiceLoader adapterServiceLoader;

    public AccountInformationServiceImpl(AdapterServiceLoader adapterServiceLoader) {
        this.adapterServiceLoader = adapterServiceLoader;
    }

    @Override
    public Response<ConsentCreationResponse> createConsent(RequestHeaders requestHeaders,
                                                           Consents consents) {
        return getAccountInformationService(requestHeaders)
                   .createConsent(requestHeaders, consents);
    }

    private AccountInformationService getAccountInformationService(RequestHeaders requestHeaders) {
        return adapterServiceLoader.getAccountInformationService(requestHeaders);
    }

    @Override
    public Response<ConsentInformation> getConsentInformation(String consentId,
                                                              RequestHeaders requestHeaders) {
        return getAccountInformationService(requestHeaders)
                   .getConsentInformation(consentId, requestHeaders);
    }

    @Override
    public Response<Void> deleteConsent(String consentId, RequestHeaders requestHeaders) {
        return getAccountInformationService(requestHeaders)
                   .deleteConsent(consentId, requestHeaders);
    }

    @Override
    public Response<ConsentStatusResponse> getConsentStatus(String consentId,
                                                            RequestHeaders requestHeaders) {
        return getAccountInformationService(requestHeaders)
                   .getConsentStatus(consentId, requestHeaders);
    }

    @Override
    public Response<StartScaProcessResponse> startConsentAuthorisation(String consentId,
                                                                       RequestHeaders requestHeaders) {
        return getAccountInformationService(requestHeaders)
                   .startConsentAuthorisation(consentId, requestHeaders);
    }

    @Override
    public Response<StartScaProcessResponse> startConsentAuthorisation(String consentId,
                                                                       RequestHeaders requestHeaders,
                                                                       UpdatePsuAuthentication updatePsuAuthentication) {
        return getAccountInformationService(requestHeaders)
                   .startConsentAuthorisation(consentId, requestHeaders, updatePsuAuthentication);
    }

    @Override
    public Response<SelectPsuAuthenticationMethodResponse> updateConsentsPsuData(String consentId,
                                                                                 String authorisationId,
                                                                                 RequestHeaders requestHeaders,
                                                                                 SelectPsuAuthenticationMethod selectPsuAuthenticationMethod) {
        return getAccountInformationService(requestHeaders)
                   .updateConsentsPsuData(consentId, authorisationId, requestHeaders, selectPsuAuthenticationMethod);
    }

    @Override
    public Response<ScaStatusResponse> updateConsentsPsuData(String consentId,
                                                             String authorisationId,
                                                             RequestHeaders requestHeaders,
                                                             TransactionAuthorisation transactionAuthorisation) {
        return getAccountInformationService(requestHeaders)
                   .updateConsentsPsuData(consentId, authorisationId, requestHeaders, transactionAuthorisation);
    }

    @Override
    public Response<UpdatePsuAuthenticationResponse> updateConsentsPsuData(String consentId,
                                                                           String authorisationId,
                                                                           RequestHeaders requestHeaders,
                                                                           UpdatePsuAuthentication updatePsuAuthentication) {
        return getAccountInformationService(requestHeaders)
                   .updateConsentsPsuData(consentId, authorisationId, requestHeaders, updatePsuAuthentication);
    }

    @Override
    public Response<AccountListHolder> getAccountList(RequestHeaders requestHeaders,
                                                      RequestParams requestParams) {
        return getAccountInformationService(requestHeaders)
                   .getAccountList(requestHeaders, requestParams);
    }

    @Override
    public Response<TransactionsReport> getTransactionList(String accountId, RequestHeaders requestHeaders,
                                                           RequestParams requestParams) {
        return getAccountInformationService(requestHeaders)
                   .getTransactionList(accountId, requestHeaders, requestParams);
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
                                                           RequestHeaders requestHeaders) {
        return getAccountInformationService(requestHeaders)
                   .getConsentScaStatus(consentId, authorisationId, requestHeaders);
    }

    @Override
    public Response<BalanceReport> getBalances(String accountId,
                                               RequestHeaders requestHeaders) {
        return getAccountInformationService(requestHeaders)
                   .getBalances(accountId, requestHeaders);
    }
}
