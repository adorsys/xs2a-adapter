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
import de.adorsys.xs2a.gateway.service.RequestParams;
import de.adorsys.xs2a.gateway.service.StartScaProcessResponse;
import de.adorsys.xs2a.gateway.service.account.AccountListHolder;
import de.adorsys.xs2a.gateway.service.ais.*;
import de.adorsys.xs2a.gateway.service.impl.mapper.DeutscheBankConsentInformationMapper;
import de.adorsys.xs2a.gateway.service.impl.model.DeutscheBankConsentInformation;
import de.adorsys.xs2a.gateway.service.model.UpdatePsuAuthentication;
import org.mapstruct.factory.Mappers;

import java.util.Map;
import java.util.stream.Collectors;

import static de.adorsys.xs2a.gateway.service.Headers.CONSENT_ID;
import static de.adorsys.xs2a.gateway.service.Headers.RESOURCE_ID;

public class DeutscheBankAccountInformationService extends AbstractDeutscheBankService implements AccountInformationService {
    private static final String CONSENTS_URI = AIS_URI + "consents";
    private static final String ACCOUNTS_URI = AIS_URI + "accounts";

    private final DeutscheBankConsentInformationMapper deutscheBankConsentInformationMapper =
            Mappers.getMapper(DeutscheBankConsentInformationMapper.class);

    @Override
    public ConsentCreationResponse createConsent(Consents body, Headers headers) {

        Map<String, String> headersMap = headers.toMap();
        addDBSpecificPostHeaders(headersMap);

        String bodyString = jsonMapper.writeValueAsString(jsonMapper.convertValue(body, Consents.class));

        return httpClient.post(CONSENTS_URI, bodyString, headersMap,
                               postResponseHandler(ConsentCreationResponse.class));
    }

    @Override
    public ConsentInformation getConsentInformation(String consentId, Headers headers) {
        String uri = CONSENTS_URI + SLASH_SEPARATOR + consentId;
        Map<String, String> headersMap = headers.toMap();
        addDBSpecificGetHeaders(headersMap);
        DeutscheBankConsentInformation deutscheBankConsentInformation =
                httpClient.get(uri, headersMap, getResponseHandlerAis(DeutscheBankConsentInformation.class));
        return deutscheBankConsentInformationMapper.toConsentInformation(deutscheBankConsentInformation);
    }

    @Override
    public ConsentStatusResponse getConsentStatus(String consentId, Headers headers) {
        String uri = CONSENTS_URI + SLASH_SEPARATOR + consentId + "/status";
        Map<String, String> headersMap = headers.toMap();
        addDBSpecificGetHeaders(headersMap);

        return httpClient.get(uri, headersMap, getResponseHandlerAis(ConsentStatusResponse.class));
    }

    @Override
    public StartScaProcessResponse startConsentAuthorisation(String consentId, Headers headers) {
        String uri = CONSENTS_URI + SLASH_SEPARATOR + consentId + SLASH_AUTHORISATIONS;

        return httpClient.post(uri, headers.toMap(), responseHandler(StartScaProcessResponse.class));
    }

    @Override
    public StartScaProcessResponse startConsentAuthorisation(
            String consentId,
            Headers headers,
            UpdatePsuAuthentication updatePsuAuthentication) {
        String uri = CONSENTS_URI + SLASH_SEPARATOR + consentId + SLASH_AUTHORISATIONS;
        String body = jsonMapper.writeValueAsString(updatePsuAuthentication);

        return httpClient.post(uri, body, headers.toMap(), responseHandler(StartScaProcessResponse.class));
    }

    @Override
    public AccountListHolder getAccountList(Headers headers, RequestParams requestParams) {
        Map<String, String> headersMap = headers.toMap();
        addDBSpecificGetHeaders(headersMap);
        adaptConsentIdHeaderForDB(headersMap);

        String uri = buildUri(ACCOUNTS_URI, requestParams);

        return httpClient.get(uri, headersMap, getResponseHandler(AccountListHolder.class));
    }

    private void adaptConsentIdHeaderForDB(Map<String, String> headersMap) {
        // needed, as DB passes consent id value through "Resource-ID" header instead of "Consent-ID" one
        headersMap.put(RESOURCE_ID, headersMap.get(CONSENT_ID));
        headersMap.remove(CONSENT_ID);
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
