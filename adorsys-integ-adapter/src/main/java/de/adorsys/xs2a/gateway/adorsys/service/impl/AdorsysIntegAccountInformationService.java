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

package de.adorsys.xs2a.gateway.adorsys.service.impl;

import de.adorsys.xs2a.gateway.adapter.AbstractService;
import de.adorsys.xs2a.gateway.service.Headers;
import de.adorsys.xs2a.gateway.service.RequestParams;
import de.adorsys.xs2a.gateway.service.StartScaProcessResponse;
import de.adorsys.xs2a.gateway.service.account.AccountListHolder;
import de.adorsys.xs2a.gateway.service.ais.*;
import de.adorsys.xs2a.gateway.service.model.*;

import java.util.Map;
import java.util.stream.Collectors;

public class AdorsysIntegAccountInformationService extends AbstractService implements AccountInformationService {
    private static final String AIS_URI = "https://dev-xs2a.cloud.adorsys.de/v1/consents";
    private static final String ACCOUNTS_URI = "https://dev-xs2a.cloud.adorsys.de/v1/accounts";

    @Override
    public ConsentCreationResponse createConsent(Consents body, Headers headers) {

        Map<String, String> headersMap = headers.toMap();
        headersMap.put(CONTENT_TYPE_HEADER, APPLICATION_JSON);

        String bodyString = jsonMapper.writeValueAsString(jsonMapper.convertValue(body, Consents.class));

        return httpClient.post(AIS_URI, bodyString, headersMap,
                               postResponseHandler(ConsentCreationResponse.class));

    }

    @Override
    public ConsentInformation getConsentInformation(String consentId, Headers headers) {
        String uri = AIS_URI + SLASH_SEPARATOR + consentId;
        Map<String, String> headersMap = headers.toMap();
        headersMap.put(ACCEPT_HEADER, APPLICATION_JSON);
        return httpClient.get(uri, headersMap, getResponseHandlerAis(ConsentInformation.class));
    }

    @Override
    public ConsentStatusResponse getConsentStatus(String consentId, Headers headers) {
        String uri = AIS_URI + SLASH_SEPARATOR + consentId + "/status";
        Map<String, String> headersMap = headers.toMap();
        headersMap.put(ACCEPT_HEADER, APPLICATION_JSON);
        return httpClient.get(uri, headersMap, getResponseHandlerAis(ConsentStatusResponse.class));
    }

    @Override
    public StartScaProcessResponse startConsentAuthorisation(String consentId, Headers headers) {
        String uri = AIS_URI + SLASH_SEPARATOR + consentId + SLASH_AUTHORISATIONS;

        return httpClient.post(uri, headers.toMap(), responseHandler(StartScaProcessResponse.class));
    }

    @Override
    public StartScaProcessResponse startConsentAuthorisation(String consentId, Headers headers, UpdatePsuAuthentication updatePsuAuthentication) {
        String uri = AIS_URI + SLASH_SEPARATOR + consentId + SLASH_AUTHORISATIONS;
        String body = jsonMapper.writeValueAsString(updatePsuAuthentication);

        return httpClient.post(uri, body, headers.toMap(), responseHandler(StartScaProcessResponse.class));
    }

    @Override
    public SelectPsuAuthenticationMethodResponse updateConsentsPsuData(
            String consentId,
            String authorisationId,
            Headers headers,
            SelectPsuAuthenticationMethod selectPsuAuthenticationMethod) {

        String uri = AIS_URI + SLASH_SEPARATOR + consentId + SLASH_AUTHORISATIONS_SLASH + authorisationId;
        String body = jsonMapper.writeValueAsString(selectPsuAuthenticationMethod);

        return httpClient.put(uri, body, headers.toMap(), responseHandler(SelectPsuAuthenticationMethodResponse.class));
    }

    @Override
    public ScaStatusResponse updateConsentsPsuData(String consentId, String authorisationId, Headers headers,
                                                   TransactionAuthorisation transactionAuthorisation) {
        String uri = AIS_URI + SLASH_SEPARATOR + consentId + SLASH_AUTHORISATIONS_SLASH + authorisationId;
        String body = jsonMapper.writeValueAsString(transactionAuthorisation);

        return httpClient.put(uri, body, headers.toMap(), responseHandler(ScaStatusResponse.class));
    }

    @Override
    public AccountListHolder getAccountList(Headers headers, RequestParams requestParams) {
        Map<String, String> headersMap = headers.toMap();
        headersMap.put(ACCEPT_HEADER, APPLICATION_JSON);

        String uri = buildUri(ACCOUNTS_URI, requestParams);

        return httpClient.get(uri, headersMap, getResponseHandler(AccountListHolder.class));
    }

    private static String buildUri(String uri, RequestParams requestParams) {
        if (requestParams == null) {
            return uri;
        }

        Map<String, String> requestParamsMap = requestParams.toMap();

        if (requestParamsMap.isEmpty()) {
            return uri;
        }

        String requestParamsString = requestParamsMap.entrySet().stream()
                                             .map(entry -> entry.getKey() + "=" + entry.getValue())
                                             .collect(Collectors.joining("&", "?", ""));

        return uri + requestParamsString;
    }
}