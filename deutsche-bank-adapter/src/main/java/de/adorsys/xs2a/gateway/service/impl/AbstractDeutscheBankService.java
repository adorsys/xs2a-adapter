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

import de.adorsys.xs2a.gateway.adapter.AbstractService;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

abstract class AbstractDeutscheBankService extends AbstractService {
    private static final String BASE_URI = "https://simulator-xs2a.db.com/";
    static final String PIS_URI = BASE_URI + "pis/DE/SB-DB/v1/";
    static final String AIS_URI = BASE_URI + "ais/DE/SB-DB/v1/";
    private static final String DATE_HEADER = "Date";

    void addDBSpecificPostHeaders(Map<String, String> headersMap) {
        headersMap.put(DATE_HEADER, DateTimeFormatter.RFC_1123_DATE_TIME.format(ZonedDateTime.now()));
        headersMap.put(CONTENT_TYPE_HEADER, APPLICATION_JSON);
    }

    void addDBSpecificGetHeaders(Map<String, String> headersMap) {
        headersMap.put(DATE_HEADER, DateTimeFormatter.RFC_1123_DATE_TIME.format(ZonedDateTime.now()));
        headersMap.put(ACCEPT_HEADER, APPLICATION_JSON);
    }
}