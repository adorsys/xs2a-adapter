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

import de.adorsys.xs2a.gateway.service.Headers;
import de.adorsys.xs2a.gateway.service.StartScaProcessResponse;
import de.adorsys.xs2a.gateway.service.RequestParams;
import de.adorsys.xs2a.gateway.service.account.AccountListHolder;
import de.adorsys.xs2a.gateway.service.ais.*;
import de.adorsys.xs2a.gateway.service.model.UpdatePsuAuthentication;
import de.adorsys.xs2a.gateway.service.provider.AccountInformationServiceProvider;
import de.adorsys.xs2a.gateway.service.provider.BankNotSupportedException;

import java.util.ServiceLoader;
import java.util.stream.StreamSupport;

public class AccountInformationServiceImpl implements AccountInformationService {

    @Override
    public ConsentCreationResponse createConsent(Consents consents, Headers headers) {
        return getConsentService(headers).createConsent(consents, headers);
    }

    @Override
    public ConsentInformation getConsentInformation(String consentId, Headers headers) {
        return getConsentService(headers).getConsentInformation(consentId, headers);
    }

    @Override
    public ConsentStatusResponse getConsentStatus(String consentId, Headers headers) {
        return getConsentService(headers).getConsentStatus(consentId, headers);
    }

    @Override
    public StartScaProcessResponse startConsentAuthorisation(String consentId, Headers headers) {
        return getConsentService(headers).startConsentAuthorisation(consentId, headers);
    }

    @Override
    public StartScaProcessResponse startConsentAuthorisation(
            String consentId,
            Headers headers,
            UpdatePsuAuthentication updatePsuAuthentication) {
        return getConsentService(headers).startConsentAuthorisation(consentId, headers, updatePsuAuthentication);
    }

    AccountInformationService getConsentService(Headers headers) {
        String bankCode = headers.toMap().get(Headers.X_GTW_BANK_CODE);
        ServiceLoader<AccountInformationServiceProvider> loader =
                ServiceLoader.load(AccountInformationServiceProvider.class);
        return StreamSupport.stream(loader.spliterator(), false)
                       .filter(pis -> pis.getBankCode().equalsIgnoreCase(bankCode))
                       .findFirst().orElseThrow(() -> new BankNotSupportedException(bankCode))
                       .getAccountInformationService();
    }

    @Override
    public AccountListHolder getAccountList(Headers headers, RequestParams requestParams) {
        return getConsentService(headers).getAccountList(headers, requestParams);
    }
}
