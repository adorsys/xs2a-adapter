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

package de.adorsys.xs2a.adapter.api;

import de.adorsys.xs2a.adapter.api.exception.NotAcceptableException;
import de.adorsys.xs2a.adapter.api.model.*;
import de.adorsys.xs2a.adapter.api.validation.AccountInformationValidationService;

public interface AccountInformationService extends AccountInformationValidationService {

    Response<ConsentsResponse201> createConsent(RequestHeaders requestHeaders,
                                                RequestParams requestParams,
                                                Consents body);

    Response<ConsentInformationResponse200Json> getConsentInformation(String consentId,
                                                                      RequestHeaders requestHeaders,
                                                                      RequestParams requestParams);

    Response<Void> deleteConsent(String consentId,
                                 RequestHeaders requestHeaders,
                                 RequestParams requestParams);

    Response<ConsentStatusResponse200> getConsentStatus(String consentId,
                                                        RequestHeaders requestHeaders,
                                                        RequestParams requestParams);

    Response<Authorisations> getConsentAuthorisation(String consentId,
                                                     RequestHeaders requestHeaders,
                                                     RequestParams requestParams);

    Response<StartScaprocessResponse> startConsentAuthorisation(String consentId,
                                                                RequestHeaders requestHeaders,
                                                                RequestParams requestParams);

    Response<StartScaprocessResponse> startConsentAuthorisation(String consentId,
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

    Response<AccountList> getAccountList(RequestHeaders requestHeaders,
                                         RequestParams requestParams);

    Response<OK200AccountDetails> readAccountDetails(String accountId,
                                                    RequestHeaders requestHeaders,
                                                    RequestParams requestParams);

    /**
     * @throws NotAcceptableException if response content type is not json
     * @return
     */
    Response<TransactionsResponse200Json> getTransactionList(String accountId,
                                                             RequestHeaders requestHeaders,
                                                             RequestParams requestParams);

    Response<OK200TransactionDetails> getTransactionDetails(String accountId,
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

    Response<ReadAccountBalanceResponse200> getBalances(String accountId,
                                                        RequestHeaders requestHeaders,
                                                        RequestParams requestParams);

    Response<CardAccountList> getCardAccountList(RequestHeaders requestHeaders, RequestParams requestParams);

    Response<OK200CardAccountDetails> getCardAccountDetails(String accountId,
                                                            RequestHeaders requestHeaders,
                                                            RequestParams requestParams);

    Response<ReadCardAccountBalanceResponse200> getCardAccountBalances(String accountId,
                                                                       RequestHeaders requestHeaders,
                                                                       RequestParams requestParams);

    Response<CardAccountsTransactionsResponse200> getCardAccountTransactionList(String accountId,
                                                                                RequestHeaders requestHeaders,
                                                                                RequestParams requestParams);
}
