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

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import static org.apache.http.protocol.HTTP.DATE_HEADER;

public class FiduciaAccountInformationService extends BaseAccountInformationService {
    private static final String BASE_URI = "https://xs2a-test.fiduciagad.de/xs2a/v1";
    private static final String CONSENTS_URI = BASE_URI + "/consents";
    private static final String ACCOUNTS_URI = BASE_URI + "/accounts";

    @Override
    protected String getConsentBaseUri() {
        return CONSENTS_URI;
    }

    @Override
    protected String getAccountsBaseUri() {
        return ACCOUNTS_URI;
    }

    @Override
    protected Map<String, String> populatePostHeaders(Map<String, String> map) {
        Map<String, String> headers = super.populateGetHeaders(map);
        headers.put(DATE_HEADER, DateTimeFormatter.RFC_1123_DATE_TIME.format(ZonedDateTime.now()));

        return headers;
    }

    @Override
    protected Map<String, String> populateGetHeaders(Map<String, String> map) {
        Map<String, String> headers = super.populateGetHeaders(map);
        headers.put(DATE_HEADER, DateTimeFormatter.RFC_1123_DATE_TIME.format(ZonedDateTime.now()));

        return headers;
    }

    @Override
    protected Map<String, String> populatePutHeaders(Map<String, String> map) {
        Map<String, String> headers = super.populateGetHeaders(map);
        headers.put(DATE_HEADER, DateTimeFormatter.RFC_1123_DATE_TIME.format(ZonedDateTime.now()));

        return headers;
    }
}
