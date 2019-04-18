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

package de.adorsys.xs2a.gateway.adapter;

import de.adorsys.xs2a.gateway.service.Headers;
import de.adorsys.xs2a.gateway.service.RequestParams;
import de.adorsys.xs2a.gateway.service.StartScaProcessResponse;
import de.adorsys.xs2a.gateway.service.account.AccountListHolder;
import de.adorsys.xs2a.gateway.service.ais.*;
import de.adorsys.xs2a.gateway.service.model.*;

import java.util.Map;
import java.util.function.Function;

public abstract class BaseAccountInformationService extends AbstractService implements AccountInformationService {

    @Override
    public ConsentCreationResponse createConsent(Consents body, Headers headers) {
        Map<String, String> headersMap = populatePostHeaders(headers.toMap());

        String bodyString = jsonMapper.writeValueAsString(jsonMapper.convertValue(body, Consents.class));

        return httpClient.post(getConsentBaseUri(), bodyString, headersMap,
                               responseHandler(ConsentCreationResponse.class));
    }

    @Override
    public ConsentInformation getConsentInformation(String consentId, Headers headers) {
        String uri = getConsentBaseUri() + SLASH_SEPARATOR + consentId;
        Map<String, String> headersMap = populateGetHeaders(headers.toMap());
        return httpClient.get(uri, headersMap, responseHandler(ConsentInformation.class));
    }

    protected <T> ConsentInformation getConsentInformation(String consentId, Headers headers, Class<T> klass, Function<T, ConsentInformation> mapper) {
        String uri = getConsentBaseUri() + SLASH_SEPARATOR + consentId;
        Map<String, String> headersMap = populateGetHeaders(headers.toMap());
        T response = httpClient.get(uri, headersMap, responseHandler(klass));
        return mapper.apply(response);
    }

    @Override
    public ConsentStatusResponse getConsentStatus(String consentId, Headers headers) {
        String uri = getConsentBaseUri() + SLASH_SEPARATOR + consentId + "/status";
        Map<String, String> headersMap = populateGetHeaders(headers.toMap());

        return httpClient.get(uri, headersMap, responseHandler(ConsentStatusResponse.class));
    }

    @Override
    public StartScaProcessResponse startConsentAuthorisation(String consentId, Headers headers) {
        String uri = getConsentBaseUri() + SLASH_SEPARATOR + consentId + SLASH_AUTHORISATIONS;
        Map<String, String> headersMap = populatePostHeaders(headers.toMap());

        return httpClient.post(uri, headersMap, responseHandler(StartScaProcessResponse.class));
    }

    @Override
    public StartScaProcessResponse startConsentAuthorisation(String consentId, Headers headers, UpdatePsuAuthentication updatePsuAuthentication) {
        String uri = getConsentBaseUri() + SLASH_SEPARATOR + consentId + SLASH_AUTHORISATIONS;
        Map<String, String> headersMap = populatePostHeaders(headers.toMap());
        String body = jsonMapper.writeValueAsString(updatePsuAuthentication);

        return httpClient.post(uri, body, headersMap, responseHandler(StartScaProcessResponse.class));
    }

    @Override
    public SelectPsuAuthenticationMethodResponse updateConsentsPsuData(String consentId, String authorisationId, Headers headers, SelectPsuAuthenticationMethod selectPsuAuthenticationMethod) {
        String uri = getConsentBaseUri() + SLASH_SEPARATOR + consentId + SLASH_AUTHORISATIONS_SLASH + authorisationId;
        Map<String, String> headersMap = populatePutHeaders(headers.toMap());
        String body = jsonMapper.writeValueAsString(selectPsuAuthenticationMethod);

        return httpClient.put(uri, body, headersMap, responseHandler(SelectPsuAuthenticationMethodResponse.class));

    }

    @Override
    public ScaStatusResponse updateConsentsPsuData(String consentId, String authorisationId, Headers headers, TransactionAuthorisation transactionAuthorisation) {
        String uri = getConsentBaseUri() + SLASH_SEPARATOR + consentId + SLASH_AUTHORISATIONS_SLASH + authorisationId;
        Map<String, String> headersMap = populatePutHeaders(headers.toMap());
        String body = jsonMapper.writeValueAsString(transactionAuthorisation);

        return httpClient.put(uri, body, headersMap, responseHandler(ScaStatusResponse.class));
    }

    @Override
    public AccountListHolder getAccountList(Headers headers, RequestParams requestParams) {
        Map<String, String> headersMap = populateGetHeaders(headers.toMap());
        headersMap = addConsentIdHeader(headersMap);

        String uri = buildUri(getAccountsBaseUri(), requestParams);

        return httpClient.get(uri, headersMap, responseHandler(AccountListHolder.class));
    }

    protected abstract String getConsentBaseUri();

    protected abstract String getAccountsBaseUri();
}
