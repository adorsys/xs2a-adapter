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

import de.adorsys.xs2a.adapter.service.GeneralResponse;
import de.adorsys.xs2a.adapter.service.RequestHeaders;
import de.adorsys.xs2a.adapter.service.RequestParams;
import de.adorsys.xs2a.adapter.service.StartScaProcessResponse;
import de.adorsys.xs2a.adapter.service.account.AccountListHolder;
import de.adorsys.xs2a.adapter.service.account.BalanceReport;
import de.adorsys.xs2a.adapter.service.account.TransactionsReport;
import de.adorsys.xs2a.adapter.service.ais.*;
import de.adorsys.xs2a.adapter.service.exception.BankCodeNotProvidedException;
import de.adorsys.xs2a.adapter.service.exception.BankNotSupportedException;
import de.adorsys.xs2a.adapter.service.model.*;
import de.adorsys.xs2a.adapter.service.provider.AccountInformationServiceProvider;

import java.util.ServiceLoader;
import java.util.stream.StreamSupport;

public class AccountInformationServiceImpl implements AccountInformationService {
    private final BankServiceLoader bankServiceLoader;

    public AccountInformationServiceImpl(BankServiceLoader bankServiceLoader) {
        this.bankServiceLoader = bankServiceLoader;
    }

    @Override
    public GeneralResponse<ConsentCreationResponse> createConsent(RequestHeaders requestHeaders,
                                                                  Consents consents) {
        return bankServiceLoader
                   .getAccountInformationService(requestHeaders.removeBankCode())
                   .createConsent(requestHeaders, consents);
    }

    @Override
    public GeneralResponse<ConsentInformation> getConsentInformation(String consentId,
                                                                     RequestHeaders requestHeaders) {
        return bankServiceLoader
                   .getAccountInformationService(requestHeaders.removeBankCode())
                   .getConsentInformation(consentId, requestHeaders);
    }

    @Override
    public GeneralResponse<ConsentStatusResponse> getConsentStatus(String consentId,
                                                                   RequestHeaders requestHeaders) {
        return bankServiceLoader
                   .getAccountInformationService(requestHeaders.removeBankCode())
                   .getConsentStatus(consentId, requestHeaders);
    }

    @Override
    public GeneralResponse<StartScaProcessResponse> startConsentAuthorisation(String consentId,
                                                                              RequestHeaders requestHeaders) {
        return bankServiceLoader
                   .getAccountInformationService(requestHeaders.removeBankCode())
                   .startConsentAuthorisation(consentId, requestHeaders);
    }

    @Override
    public GeneralResponse<StartScaProcessResponse> startConsentAuthorisation(String consentId,
                                                                              RequestHeaders requestHeaders,
                                                                              UpdatePsuAuthentication updatePsuAuthentication) {
        return bankServiceLoader
                   .getAccountInformationService(requestHeaders.removeBankCode())
                   .startConsentAuthorisation(consentId, requestHeaders, updatePsuAuthentication);
    }

    @Override
    public GeneralResponse<SelectPsuAuthenticationMethodResponse> updateConsentsPsuData(String consentId,
                                                                                        String authorisationId,
                                                                                        RequestHeaders requestHeaders,
                                                                                        SelectPsuAuthenticationMethod selectPsuAuthenticationMethod) {
        return bankServiceLoader
                   .getAccountInformationService(requestHeaders.removeBankCode())
                   .updateConsentsPsuData(consentId, authorisationId, requestHeaders, selectPsuAuthenticationMethod);
    }

    @Override
    public GeneralResponse<ScaStatusResponse> updateConsentsPsuData(String consentId,
                                                                    String authorisationId,
                                                                    RequestHeaders requestHeaders,
                                                                    TransactionAuthorisation transactionAuthorisation) {
        return bankServiceLoader
                   .getAccountInformationService(requestHeaders.removeBankCode())
                   .updateConsentsPsuData(consentId, authorisationId, requestHeaders, transactionAuthorisation);
    }

    @Override
    public GeneralResponse<UpdatePsuAuthenticationResponse> updateConsentsPsuData(String consentId,
                                                                                  String authorisationId,
                                                                                  RequestHeaders requestHeaders,
                                                                                  UpdatePsuAuthentication updatePsuAuthentication) {
        return bankServiceLoader
                   .getAccountInformationService(requestHeaders.removeBankCode())
                   .updateConsentsPsuData(consentId, authorisationId, requestHeaders, updatePsuAuthentication);
    }

    @Override
    public GeneralResponse<AccountListHolder> getAccountList(RequestHeaders requestHeaders,
                                                             RequestParams requestParams) {
        return bankServiceLoader
                   .getAccountInformationService(requestHeaders.removeBankCode())
                   .getAccountList(requestHeaders, requestParams);
    }

    @Override
    public GeneralResponse<TransactionsReport> getTransactionList(String accountId, RequestHeaders requestHeaders,
                                                                  RequestParams requestParams) {
        return bankServiceLoader
                   .getAccountInformationService(requestHeaders.removeBankCode())
                   .getTransactionList(accountId, requestHeaders, requestParams);
    }

    @Override
    public GeneralResponse<String> getTransactionListAsString(String accountId, RequestHeaders requestHeaders,
                                                              RequestParams requestParams) {
        return bankServiceLoader
                   .getAccountInformationService(requestHeaders.removeBankCode())
                   .getTransactionListAsString(accountId, requestHeaders, requestParams);
    }

    @Override
    public GeneralResponse<ScaStatusResponse> getConsentScaStatus(String consentId,
                                                                  String authorisationId,
                                                                  RequestHeaders requestHeaders) {
        return bankServiceLoader
                   .getAccountInformationService(requestHeaders.removeBankCode())
                   .getConsentScaStatus(consentId, authorisationId, requestHeaders);
    }

    @Override
    public GeneralResponse<BalanceReport> getBalances(String accountId,
                                                      RequestHeaders requestHeaders) {
        return bankServiceLoader
                   .getAccountInformationService(requestHeaders.removeBankCode())
                   .getBalances(accountId, requestHeaders);
    }
}
