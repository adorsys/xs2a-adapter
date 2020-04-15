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

package de.adorsys.xs2a.adapter.service;

import de.adorsys.xs2a.adapter.service.exception.NotAcceptableException;
import de.adorsys.xs2a.adapter.service.model.*;
import de.adorsys.xs2a.adapter.validation.AccountInformationValidationService;

public interface AccountInformationService extends AccountInformationValidationService {

    Response<ConsentCreationResponse> createConsent(RequestHeaders requestHeaders,
                                                    RequestParams requestParams,
                                                    Consents body);

    Response<ConsentInformation> getConsentInformation(String consentId,
                                                       RequestHeaders requestHeaders,
                                                       RequestParams requestParams);

    Response<Void> deleteConsent(String consentId,
                                 RequestHeaders requestHeaders,
                                 RequestParams requestParams);

    Response<ConsentStatusResponse> getConsentStatus(String consentId,
                                                     RequestHeaders requestHeaders,
                                                     RequestParams requestParams);

    Response<StartScaProcessResponse> startConsentAuthorisation(String consentId,
                                                                RequestHeaders requestHeaders,
                                                                RequestParams requestParams);

    Response<StartScaProcessResponse> startConsentAuthorisation(String consentId,
                                                                RequestHeaders requestHeaders,
                                                                RequestParams requestParams,
                                                                UpdatePsuAuthentication updatePsuAuthentication);

    Response<SelectPsuAuthenticationMethodResponse> updateConsentsPsuData(String consentId,
                                                                          String authorisationId,
                                                                          RequestHeaders requestHeaders,
                                                                          RequestParams requestParams,
                                                                          SelectPsuAuthenticationMethod selectPsuAuthenticationMethod);

    Response<ScaStatusResponse> updateConsentsPsuData(String consentId,
                                                      String authorisationId,
                                                      RequestHeaders requestHeaders,
                                                      RequestParams requestParams,
                                                      TransactionAuthorisation transactionAuthorisation);

    Response<UpdatePsuAuthenticationResponse> updateConsentsPsuData(String consentId,
                                                                    String authorisationId,
                                                                    RequestHeaders requestHeaders,
                                                                    RequestParams requestParams,
                                                                    UpdatePsuAuthentication updatePsuAuthentication);

    Response<AccountListHolder> getAccountList(RequestHeaders requestHeaders,
                                               RequestParams requestParams);

    /**
     * @throws NotAcceptableException if response content type is not json
     */
    Response<TransactionsReport> getTransactionList(String accountId,
                                                    RequestHeaders requestHeaders,
                                                    RequestParams requestParams);

    Response<TransactionDetails> getTransactionDetails(String accountId,
                                                       String transactionId,
                                                       RequestHeaders requestHeaders,
                                                       RequestParams requestParams);

    Response<String> getTransactionListAsString(String accountId,
                                                RequestHeaders requestHeaders,
                                                RequestParams requestParams);

    Response<ScaStatusResponse> getConsentScaStatus(String consentId,
                                                    String authorisationId,
                                                    RequestHeaders requestHeaders,
                                                    RequestParams requestParams);

    Response<BalanceReport> getBalances(String accountId,
                                        RequestHeaders requestHeaders,
                                        RequestParams requestParams);

    Response<CardAccountList> getCardAccountList(RequestHeaders requestHeaders, RequestParams requestParams);

    Response<CardAccountDetailsHolder> getCardAccountDetails(String accountId,
                                                             RequestHeaders requestHeaders,
                                                             RequestParams requestParams);

    Response<CardAccountBalanceReport> getCardAccountBalances(String accountId,
                                                              RequestHeaders requestHeaders,
                                                              RequestParams requestParams);

    Response<CardAccountsTransactions> getCardAccountTransactionList(String accountId,
                                                                     RequestHeaders requestHeaders,
                                                                     RequestParams requestParams);
}
