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

package de.adorsys.xs2a.gateway.service.impl;

import de.adorsys.xs2a.gateway.service.GeneralResponse;
import de.adorsys.xs2a.gateway.service.RequestHeaders;
import de.adorsys.xs2a.gateway.service.RequestParams;
import de.adorsys.xs2a.gateway.service.StartScaProcessResponse;
import de.adorsys.xs2a.gateway.service.account.AccountListHolder;
import de.adorsys.xs2a.gateway.service.account.TransactionsReport;
import de.adorsys.xs2a.gateway.service.ais.*;
import de.adorsys.xs2a.gateway.service.model.*;
import de.adorsys.xs2a.gateway.service.provider.AccountInformationServiceProvider;
import de.adorsys.xs2a.gateway.service.provider.BankNotSupportedException;

import java.util.ServiceLoader;
import java.util.stream.StreamSupport;

public class AccountInformationServiceImpl implements AccountInformationService {

    @Override
    public GeneralResponse<ConsentCreationResponse> createConsent(Consents consents, RequestHeaders requestHeaders) {
        return getConsentService(requestHeaders).createConsent(consents, requestHeaders);
    }

    @Override
    public GeneralResponse<ConsentInformation> getConsentInformation(String consentId, RequestHeaders requestHeaders) {
        return getConsentService(requestHeaders).getConsentInformation(consentId, requestHeaders);
    }

    @Override
    public GeneralResponse<ConsentStatusResponse> getConsentStatus(String consentId, RequestHeaders requestHeaders) {
        return getConsentService(requestHeaders).getConsentStatus(consentId, requestHeaders);
    }

    @Override
    public GeneralResponse<StartScaProcessResponse> startConsentAuthorisation(String consentId, RequestHeaders requestHeaders) {
        return getConsentService(requestHeaders).startConsentAuthorisation(consentId, requestHeaders);
    }

    @Override
    public GeneralResponse<StartScaProcessResponse> startConsentAuthorisation(
            String consentId,
            RequestHeaders requestHeaders,
            UpdatePsuAuthentication updatePsuAuthentication) {
        return getConsentService(requestHeaders).startConsentAuthorisation(consentId, requestHeaders, updatePsuAuthentication);
    }

    @Override
    public GeneralResponse<SelectPsuAuthenticationMethodResponse> updateConsentsPsuData(
            String consentId,
            String authorisationId,
            RequestHeaders requestHeaders,
            SelectPsuAuthenticationMethod selectPsuAuthenticationMethod) {
        return getConsentService(requestHeaders).updateConsentsPsuData(consentId, authorisationId, requestHeaders, selectPsuAuthenticationMethod);
    }

    @Override
    public GeneralResponse<ScaStatusResponse> updateConsentsPsuData(
            String consentId,
            String authorisationId,
            RequestHeaders requestHeaders,
            TransactionAuthorisation transactionAuthorisation) {
        return getConsentService(requestHeaders).updateConsentsPsuData(consentId, authorisationId, requestHeaders, transactionAuthorisation);
    }

    @Override
    public GeneralResponse<UpdatePsuAuthenticationResponse> updateConsentsPsuData(
            String consentId,
            String authorisationId,
            RequestHeaders requestHeaders,
            UpdatePsuAuthentication updatePsuAuthentication) {
        return getConsentService(requestHeaders).updateConsentsPsuData(consentId, authorisationId, requestHeaders, updatePsuAuthentication);
    }

    AccountInformationService getConsentService(RequestHeaders requestHeaders) {
        String bankCode = requestHeaders.toMap().get(RequestHeaders.X_GTW_BANK_CODE);
        ServiceLoader<AccountInformationServiceProvider> loader =
                ServiceLoader.load(AccountInformationServiceProvider.class);
        return StreamSupport.stream(loader.spliterator(), false)
                       .filter(pis -> pis.getBankCodes().contains(bankCode))
                       .findFirst().orElseThrow(() -> new BankNotSupportedException(bankCode))
                       .getAccountInformationService();
    }

    @Override
    public GeneralResponse<AccountListHolder> getAccountList(RequestHeaders requestHeaders, RequestParams requestParams) {
        return getConsentService(requestHeaders).getAccountList(requestHeaders, requestParams);
    }

    @Override
    public GeneralResponse<TransactionsReport> getTransactionList(String accountId, RequestHeaders requestHeaders, RequestParams requestParams) {
        return getConsentService(requestHeaders).getTransactionList(accountId, requestHeaders, requestParams);
    }
}
