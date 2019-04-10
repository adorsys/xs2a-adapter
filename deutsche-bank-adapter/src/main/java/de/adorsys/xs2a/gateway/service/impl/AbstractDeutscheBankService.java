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

import de.adorsys.xs2a.gateway.service.ErrorResponse;
import de.adorsys.xs2a.gateway.service.exception.ErrorResponseException;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

abstract class AbstractDeutscheBankService {
    private static final String HOST = "https://simulator-xs2a.db.com/";
    static final String BASE_PIS_URI = HOST + "pis/DE/SB-DB/v1/";
    static final String BASE_AIS_URI = HOST + "ais/DE/SB-DB/v1/";
    static final String SLASH_SEPARATOR = "/";
    private static final String DATE_HEADER = "Date";
    private static final String CONTENT_TYPE_HEADER = "Content-Type";
    private static final String APPLICATION_JSON = "application/json";
    private static final String ACCEPT_HEADER = "Accept";
    final DeutscheBankObjectMapper objectMapper = new DeutscheBankObjectMapper();
    HttpClient httpClient = HttpClient.newHttpClient();

    void setHttpClient(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    void addDBSpecificPostHeaders(Map<String, String> headersMap) {
        headersMap.put(DATE_HEADER, DateTimeFormatter.RFC_1123_DATE_TIME.format(ZonedDateTime.now()));
        headersMap.put(CONTENT_TYPE_HEADER, APPLICATION_JSON);
    }

    void addDBSpecificGetHeaders(Map<String, String> headersMap) {
        headersMap.put(DATE_HEADER, DateTimeFormatter.RFC_1123_DATE_TIME.format(ZonedDateTime.now()));
        headersMap.put(ACCEPT_HEADER, APPLICATION_JSON);
    }

    <T> HttpClient.ResponseHandler<T> getResponseHandler(Class<T> klass) {
        return (statusCode, responseBody) -> {
            switch (statusCode) {
                case 200:
                    return objectMapper.toObject(responseBody, klass);
                case 400:
                case 401:
                case 403:
                case 404:
                case 405:
                    throw new ErrorResponseException(statusCode, objectMapper.toObject(responseBody, ErrorResponse.class));
                case 406:
                case 408:
                case 415:
                case 429:
                case 500:
                case 503:
                    throw new ErrorResponseException(statusCode);
                default:
                    throw new UnexpectedResponseStatusCodeException();
            }
        };
    }

    <T> HttpClient.ResponseHandler<T> postResponseHandler(Class<T> klass) {
        return (statusCode, responseBody) -> {
            switch (statusCode) {
                case 201:
                    return objectMapper.toObject(responseBody, klass);
                case 400:
                case 401:
                case 403:
                case 404:
                case 405:
                    throw new ErrorResponseException(statusCode, objectMapper.toObject(responseBody, ErrorResponse.class));
                case 406:
                case 408:
                case 415:
                case 429:
                case 500:
                case 503:
                    throw new ErrorResponseException(statusCode);
                default:
                    throw new UnexpectedResponseStatusCodeException();
            }
        };
    }
}
