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

package de.adorsys.xs2a.adapter.validation;

import de.adorsys.xs2a.adapter.service.RequestHeaders;
import de.adorsys.xs2a.adapter.service.RequestParams;
import de.adorsys.xs2a.adapter.service.model.Consents;
import de.adorsys.xs2a.adapter.service.model.SelectPsuAuthenticationMethod;
import de.adorsys.xs2a.adapter.service.model.TransactionAuthorisation;
import de.adorsys.xs2a.adapter.service.model.UpdatePsuAuthentication;

import java.util.Collections;
import java.util.List;

public interface AccountInformationValidationService {

    default List<ValidationError> validateCreateConsent(RequestHeaders requestHeaders,
                                                        RequestParams requestParams,
                                                        Consents body) {
        return Collections.emptyList();
    }

    default List<ValidationError> validateGetConsentInformation(String consentId,
                                                                RequestHeaders requestHeaders,
                                                                RequestParams requestParams) {
        return Collections.emptyList();
    }

    default List<ValidationError> validateDeleteConsent(String consentId,
                                                        RequestHeaders requestHeaders,
                                                        RequestParams requestParams) {
        return Collections.emptyList();
    }

    default List<ValidationError> validateGetConsentStatus(String consentId,
                                                           RequestHeaders requestHeaders,
                                                           RequestParams requestParams) {
        return Collections.emptyList();
    }

    default List<ValidationError> validateStartConsentAuthorisation(String consentId,
                                                                    RequestHeaders requestHeaders,
                                                                    RequestParams requestParams) {
        return Collections.emptyList();
    }

    default List<ValidationError> validateStartConsentAuthorisation(String consentId,
                                                                    RequestHeaders requestHeaders,
                                                                    RequestParams requestParams,
                                                                    UpdatePsuAuthentication updatePsuAuthentication) {
        return Collections.emptyList();
    }

    default List<ValidationError> validateUpdateConsentsPsuData(String consentId,
                                                                String authorisationId,
                                                                RequestHeaders requestHeaders,
                                                                RequestParams requestParams,
                                                                SelectPsuAuthenticationMethod selectPsuAuthenticationMethod) {
        return Collections.emptyList();
    }

    default List<ValidationError> validateUpdateConsentsPsuData(String consentId,
                                                                String authorisationId,
                                                                RequestHeaders requestHeaders,
                                                                RequestParams requestParams,
                                                                TransactionAuthorisation transactionAuthorisation) {
        return Collections.emptyList();
    }

    default List<ValidationError> validateUpdateConsentsPsuData(String consentId,
                                                                String authorisationId,
                                                                RequestHeaders requestHeaders,
                                                                RequestParams requestParams,
                                                                UpdatePsuAuthentication updatePsuAuthentication) {
        return Collections.emptyList();
    }

    default List<ValidationError> validateGetAccountList(RequestHeaders requestHeaders,
                                                         RequestParams requestParams) {
        return Collections.emptyList();
    }

    default List<ValidationError> validateGetTransactionList(String accountId,
                                                             RequestHeaders requestHeaders,
                                                             RequestParams requestParams) {
        return Collections.emptyList();
    }

    default List<ValidationError> validateGetTransactionDetails(String accountId,
                                                                String transactionId,
                                                                RequestHeaders requestHeaders,
                                                                RequestParams requestParams) {
        return Collections.emptyList();
    }

    default List<ValidationError> validateGetTransactionListAsString(String accountId,
                                                                     RequestHeaders requestHeaders,
                                                                     RequestParams requestParams) {
        return Collections.emptyList();
    }

    default List<ValidationError> validateGetConsentScaStatus(String consentId,
                                                              String authorisationId,
                                                              RequestHeaders requestHeaders,
                                                              RequestParams requestParams) {
        return Collections.emptyList();
    }

    default List<ValidationError> validateGetBalances(String accountId,
                                                      RequestHeaders requestHeaders,
                                                      RequestParams requestParams) {
        return Collections.emptyList();
    }

    default List<ValidationError> validateGetCardAccountList(RequestHeaders requestHeaders,
                                                             RequestParams requestParams) {
        return Collections.emptyList();
    }

    default List<ValidationError> validateGetCardAccountDetails(String accountId,
                                                                RequestHeaders requestHeaders,
                                                                RequestParams requestParams) {
        return Collections.emptyList();
    }

    default List<ValidationError> validateGetCardAccountBalances(String accountId,
                                                                 RequestHeaders requestHeaders,
                                                                 RequestParams requestParams) {
        return Collections.emptyList();
    }

    default List<ValidationError> validateGetCardAccountTransactionList(String accountId,
                                                                        RequestHeaders requestHeaders,
                                                                        RequestParams requestParams) {
        return Collections.emptyList();
    }
}
