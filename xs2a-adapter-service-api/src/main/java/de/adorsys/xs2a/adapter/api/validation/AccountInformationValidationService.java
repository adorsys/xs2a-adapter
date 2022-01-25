/*
 * Copyright 2018-2022 adorsys GmbH & Co KG
 *
 * This program is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or (at
 * your option) any later version. This program is distributed in the hope that
 * it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see https://www.gnu.org/licenses/.
 *
 * This project is also available under a separate commercial license. You can
 * contact us at psd2@adorsys.com.
 */

package de.adorsys.xs2a.adapter.api.validation;

import de.adorsys.xs2a.adapter.api.RequestHeaders;
import de.adorsys.xs2a.adapter.api.RequestParams;
import de.adorsys.xs2a.adapter.api.model.Consents;
import de.adorsys.xs2a.adapter.api.model.SelectPsuAuthenticationMethod;
import de.adorsys.xs2a.adapter.api.model.TransactionAuthorisation;
import de.adorsys.xs2a.adapter.api.model.UpdatePsuAuthentication;

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

    default List<ValidationError> validateGetConsentAuthorisation(String consentId,
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

    default List<ValidationError> validateReadAccountDetails(RequestHeaders requestHeaders,
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
