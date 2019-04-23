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

package de.adorsys.xs2a.gateway.service.ais;

import de.adorsys.xs2a.gateway.service.GeneralResponse;
import de.adorsys.xs2a.gateway.service.RequestHeaders;
import de.adorsys.xs2a.gateway.service.RequestParams;
import de.adorsys.xs2a.gateway.service.StartScaProcessResponse;
import de.adorsys.xs2a.gateway.service.account.AccountListHolder;
import de.adorsys.xs2a.gateway.service.account.TransactionsReport;
import de.adorsys.xs2a.gateway.service.model.*;

public interface AccountInformationService {
    GeneralResponse<ConsentCreationResponse> createConsent(Consents body, RequestHeaders requestHeaders);

    GeneralResponse<ConsentInformation> getConsentInformation(String consentId, RequestHeaders requestHeaders);

    GeneralResponse<ConsentStatusResponse> getConsentStatus(String consentId, RequestHeaders requestHeaders);

    GeneralResponse<StartScaProcessResponse> startConsentAuthorisation(String consentId, RequestHeaders requestHeaders);

    GeneralResponse<StartScaProcessResponse> startConsentAuthorisation(String consentId, RequestHeaders requestHeaders, UpdatePsuAuthentication updatePsuAuthentication);

    GeneralResponse<SelectPsuAuthenticationMethodResponse> updateConsentsPsuData(
            String consentId,
            String authorisationId,
            RequestHeaders requestHeaders,
            SelectPsuAuthenticationMethod selectPsuAuthenticationMethod);

    GeneralResponse<ScaStatusResponse> updateConsentsPsuData(
            String consentId,
            String authorisationId,
            RequestHeaders requestHeaders,
            TransactionAuthorisation transactionAuthorisation);

    GeneralResponse<UpdatePsuAuthenticationResponse> updateConsentsPsuData(
            String consentId,
            String authorisationId,
            RequestHeaders requestHeaders,
            UpdatePsuAuthentication updatePsuAuthentication
    );

    GeneralResponse<AccountListHolder> getAccountList(RequestHeaders requestHeaders, RequestParams requestParams);

    GeneralResponse<TransactionsReport> getTransactionList(String accountId, RequestHeaders requestHeaders, RequestParams requestParams);
}
