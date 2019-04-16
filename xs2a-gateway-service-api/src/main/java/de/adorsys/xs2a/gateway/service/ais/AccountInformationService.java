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

import de.adorsys.xs2a.gateway.service.Headers;
import de.adorsys.xs2a.gateway.service.RequestParams;
import de.adorsys.xs2a.gateway.service.StartScaProcessResponse;
import de.adorsys.xs2a.gateway.service.account.AccountListHolder;
import de.adorsys.xs2a.gateway.service.model.*;

public interface AccountInformationService {
    ConsentCreationResponse createConsent(Consents body, Headers headers);

    ConsentInformation getConsentInformation(String consentId, Headers headers);

    ConsentStatusResponse getConsentStatus(String consentId, Headers headers);

    StartScaProcessResponse startConsentAuthorisation(String consentId, Headers headers);

    StartScaProcessResponse startConsentAuthorisation(String consentId, Headers headers, UpdatePsuAuthentication updatePsuAuthentication);

    SelectPsuAuthenticationMethodResponse updateConsentsPsuData(
            String consentId,
            String authorisationId,
            Headers headers,
            SelectPsuAuthenticationMethod selectPsuAuthenticationMethod);

    ScaStatusResponse updateConsentsPsuData(
            String consentId,
            String authorisationId,
            Headers headers,
            TransactionAuthorisation transactionAuthorisation);

    AccountListHolder getAccountList(Headers headers, RequestParams requestParams);
}
