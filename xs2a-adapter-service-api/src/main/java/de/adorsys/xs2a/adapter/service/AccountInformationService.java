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

public interface AccountInformationService {
    @Deprecated
    default Response<ConsentCreationResponse> createConsent(RequestHeaders requestHeaders,
                                                            Consents body) {
        return createConsent(requestHeaders, RequestParams.empty(), body);
    }

    Response<ConsentCreationResponse> createConsent(RequestHeaders requestHeaders,
                                                    RequestParams requestParams,
                                                    Consents body);

    @Deprecated
    default Response<ConsentInformation> getConsentInformation(String consentId,
                                                               RequestHeaders requestHeaders) {
        return getConsentInformation(consentId, requestHeaders, RequestParams.empty());
    }

    Response<ConsentInformation> getConsentInformation(String consentId,
                                                       RequestHeaders requestHeaders,
                                                       RequestParams requestParams);

    @Deprecated
    default Response<Void> deleteConsent(String consentId,
                                 RequestHeaders requestHeaders) {
        return deleteConsent(consentId, requestHeaders, RequestParams.empty());
    }

    Response<Void> deleteConsent(String consentId,
                                 RequestHeaders requestHeaders,
                                 RequestParams requestParams);

    @Deprecated
    default Response<ConsentStatusResponse> getConsentStatus(String consentId,
                                                     RequestHeaders requestHeaders) {
        return getConsentStatus(consentId, requestHeaders, RequestParams.empty());
    }

    Response<ConsentStatusResponse> getConsentStatus(String consentId,
                                                     RequestHeaders requestHeaders,
                                                     RequestParams requestParams);

    @Deprecated
    default Response<StartScaProcessResponse> startConsentAuthorisation(String consentId,
                                                                RequestHeaders requestHeaders) {
        return startConsentAuthorisation(consentId, requestHeaders, RequestParams.empty());
    }

    Response<StartScaProcessResponse> startConsentAuthorisation(String consentId,
                                                                RequestHeaders requestHeaders,
                                                                RequestParams requestParams);

    @Deprecated
    default Response<StartScaProcessResponse> startConsentAuthorisation(String consentId,
                                                                RequestHeaders requestHeaders,
                                                                UpdatePsuAuthentication updatePsuAuthentication) {
        return startConsentAuthorisation(consentId, requestHeaders, RequestParams.empty(), updatePsuAuthentication);
    }

    Response<StartScaProcessResponse> startConsentAuthorisation(String consentId,
                                                                RequestHeaders requestHeaders,
                                                                RequestParams requestParams,
                                                                UpdatePsuAuthentication updatePsuAuthentication);

    @Deprecated
    default Response<SelectPsuAuthenticationMethodResponse> updateConsentsPsuData(String consentId,
                                                                                  String authorisationId,
                                                                                  RequestHeaders requestHeaders,
                                                                                  SelectPsuAuthenticationMethod selectPsuAuthenticationMethod) {
        return updateConsentsPsuData(consentId, authorisationId, requestHeaders, RequestParams.empty(), selectPsuAuthenticationMethod);
    }

    Response<SelectPsuAuthenticationMethodResponse> updateConsentsPsuData(String consentId,
                                                                          String authorisationId,
                                                                          RequestHeaders requestHeaders,
                                                                          RequestParams requestParams,
                                                                          SelectPsuAuthenticationMethod selectPsuAuthenticationMethod);

    @Deprecated
    default Response<ScaStatusResponse> updateConsentsPsuData(String consentId,
                                                      String authorisationId,
                                                      RequestHeaders requestHeaders,
                                                      TransactionAuthorisation transactionAuthorisation) {
        return updateConsentsPsuData(consentId,
            authorisationId,
            requestHeaders,
            RequestParams.empty(),
            transactionAuthorisation);
    }

    Response<ScaStatusResponse> updateConsentsPsuData(String consentId,
                                                      String authorisationId,
                                                      RequestHeaders requestHeaders,
                                                      RequestParams requestParams,
                                                      TransactionAuthorisation transactionAuthorisation);

    @Deprecated
    default Response<UpdatePsuAuthenticationResponse> updateConsentsPsuData(String consentId,
                                                                    String authorisationId,
                                                                    RequestHeaders requestHeaders,
                                                                    UpdatePsuAuthentication updatePsuAuthentication) {
        return updateConsentsPsuData(consentId,
            authorisationId,
            requestHeaders,
            RequestParams.empty(),
            updatePsuAuthentication);
    }

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

    @Deprecated
    default Response<TransactionDetails> getTransactionDetails(String accountId,
                                                       String transactionId,
                                                       RequestHeaders requestHeaders) {
        return getTransactionDetails(accountId, transactionId, requestHeaders, RequestParams.empty());
    }

    Response<TransactionDetails> getTransactionDetails(String accountId,
                                                       String transactionId,
                                                       RequestHeaders requestHeaders,
                                                       RequestParams requestParams);

    Response<String> getTransactionListAsString(String accountId,
                                                RequestHeaders requestHeaders,
                                                RequestParams requestParams);

    @Deprecated
    default Response<ScaStatusResponse> getConsentScaStatus(String consentId,
                                                            String authorisationId,
                                                            RequestHeaders requestHeaders) {
        return getConsentScaStatus(consentId, authorisationId, requestHeaders, RequestParams.empty());
    }

    Response<ScaStatusResponse> getConsentScaStatus(String consentId,
                                                    String authorisationId,
                                                    RequestHeaders requestHeaders,
                                                    RequestParams requestParams);

    @Deprecated
    default Response<BalanceReport> getBalances(String accountId,
                                        RequestHeaders requestHeaders) {
        return getBalances(accountId, requestHeaders, RequestParams.empty());
    }

    Response<BalanceReport> getBalances(String accountId,
                                        RequestHeaders requestHeaders,
                                        RequestParams requestParams);
}
