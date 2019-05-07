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

import de.adorsys.xs2a.gateway.adapter.BaseAccountInformationService;
import de.adorsys.xs2a.gateway.service.GeneralResponse;
import de.adorsys.xs2a.gateway.service.RequestHeaders;
import de.adorsys.xs2a.gateway.service.ais.ConsentInformation;
import de.adorsys.xs2a.gateway.service.impl.mapper.DeutscheBankConsentInformationMapper;
import de.adorsys.xs2a.gateway.service.impl.model.DeutscheBankConsentInformation;
import org.mapstruct.factory.Mappers;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import static de.adorsys.xs2a.gateway.service.RequestHeaders.CONSENT_ID;

public class DeutscheBankAccountInformationService extends BaseAccountInformationService {
    private static final String DATE_HEADER = "Date";
    private static final String RESOURCE_ID_HEADER = "Resource-ID";
    private static final String BASE_URI = "https://simulator-xs2a.db.com/ais/DE/SB-DB/v1/";

    private final DeutscheBankConsentInformationMapper deutscheBankConsentInformationMapper =
            Mappers.getMapper(DeutscheBankConsentInformationMapper.class);

    @Override
    public GeneralResponse<ConsentInformation> getConsentInformation(String consentId, RequestHeaders requestHeaders) {
        return getConsentInformation(consentId, requestHeaders, DeutscheBankConsentInformation.class, deutscheBankConsentInformationMapper::toConsentInformation);
    }
    @Override
    protected String getBaseUri() {
        return BASE_URI;
    }

    @Override
    protected Map<String, String> addConsentIdHeader(Map<String, String> map) {
        Map<String, String> headers = super.addConsentIdHeader(map);
        // needed, as DB passes consent id value through "Resource-ID" header instead of "Consent-ID" one
        headers.put(RESOURCE_ID_HEADER, headers.get(CONSENT_ID));
        headers.remove(CONSENT_ID);
        return headers;
    }


    @Override
    protected Map<String, String> populateGetHeaders(Map<String, String> map) {
        Map<String, String> headers = super.populateGetHeaders(map);
        headers.put(DATE_HEADER, DateTimeFormatter.RFC_1123_DATE_TIME.format(ZonedDateTime.now()));
        headers.put(ACCEPT_HEADER, APPLICATION_JSON);

        return headers;
    }

    @Override
    protected Map<String, String> populatePostHeaders(Map<String, String> map) {
        Map<String, String> headers = super.populateGetHeaders(map);
        headers.put(DATE_HEADER, DateTimeFormatter.RFC_1123_DATE_TIME.format(ZonedDateTime.now()));
        headers.put(CONTENT_TYPE_HEADER, APPLICATION_JSON);

        return headers;
    }
}