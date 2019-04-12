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
import de.adorsys.xs2a.gateway.service.consent.*;
import de.adorsys.xs2a.gateway.service.impl.mapper.DeutscheBankConsentInformationMapper;
import de.adorsys.xs2a.gateway.service.impl.model.DeutscheBankConsentInformation;
import org.mapstruct.factory.Mappers;

import java.util.Map;

public class DeutscheBankAccountInformationService extends AbstractDeutscheBankService implements ConsentService {
    private static final String CONSENTS_URI = AIS_URI + "consents";

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
        String uri = CONSENTS_URI + "/" + consentId;
        Map<String, String> headersMap = headers.toMap();
        addDBSpecificGetHeaders(headersMap);
        DeutscheBankConsentInformation deutscheBankConsentInformation =
                httpClient.get(uri, headersMap, getResponseHandlerAis(DeutscheBankConsentInformation.class));
        return deutscheBankConsentInformationMapper.toConsentInformation(deutscheBankConsentInformation);
    }

    @Override
    public ConsentStatusResponse getConsentStatus(String consentId, Headers headers) {
        String uri = CONSENTS_URI + "/" + consentId + "/status";
        Map<String, String> headersMap = headers.toMap();
        addDBSpecificGetHeaders(headersMap);

        return httpClient.get(uri, headersMap, getResponseHandlerAis(ConsentStatusResponse.class));
    }
}
