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

import de.adorsys.xs2a.gateway.http.HttpClient;
import de.adorsys.xs2a.gateway.http.JsonMapper;
import de.adorsys.xs2a.gateway.service.ErrorResponse;
import de.adorsys.xs2a.gateway.service.exception.ErrorResponseException;

import java.io.IOException;
import java.io.PushbackInputStream;
import java.io.UncheckedIOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

abstract class AbstractDeutscheBankService {
    private static final String BASE_URI = "https://simulator-xs2a.db.com/";
    static final String PIS_URI = BASE_URI + "pis/DE/SB-DB/v1/";
    static final String AIS_URI = BASE_URI + "ais/DE/SB-DB/v1/";
    static final String SLASH_SEPARATOR = "/";
    static final String SLASH_AUTHORISATIONS = "/authorisations";
    static final String SLASH_AUTHORISATIONS_SLASH = "/authorisations/";
    private static final String DATE_HEADER = "Date";
    private static final String CONTENT_TYPE_HEADER = "Content-Type";
    private static final String APPLICATION_JSON = "application/json";
    private static final String ACCEPT_HEADER = "Accept";
    final JsonMapper jsonMapper = new JsonMapper();
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
                    return jsonMapper.readValue(responseBody, klass);
                case 400:
                case 401:
                case 403:
                case 404:
                case 405:
                    throw new ErrorResponseException(statusCode, jsonMapper.readValue(responseBody, ErrorResponse.class));
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
                    return jsonMapper.readValue(responseBody, klass);
                case 400:
                case 401:
                case 403:
                case 404:
                case 405:
                    throw new ErrorResponseException(statusCode, jsonMapper.readValue(responseBody, ErrorResponse.class));
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

    <T> HttpClient.ResponseHandler<T> getResponseHandlerAis(Class<T> klass) {
        return (statusCode, responseBody) -> {
            switch (statusCode) {
                case 200:
                    return jsonMapper.readValue(responseBody, klass);
                case 400:
                case 401:
                case 403:
                case 404:
                case 405:
                case 406:
                case 409:
                case 429:
                    throw new ErrorResponseException(statusCode, jsonMapper.readValue(responseBody, ErrorResponse.class));
                case 408:
                case 415:
                case 500:
                case 503:
                    throw new ErrorResponseException(statusCode);
                default:
                    throw new UnexpectedResponseStatusCodeException();
            }
        };
    }

    <T> HttpClient.ResponseHandler<T> responseHandler(Class<T> klass) {
        return (statusCode, responseBody) -> {
            if (statusCode == 200 || statusCode == 201) {
                return jsonMapper.readValue(responseBody, klass);
            }
            throw responseException(statusCode, new PushbackInputStream(responseBody));
        };
    }

    private ErrorResponseException responseException(int statusCode, PushbackInputStream responseBody) {
        if (isEmpty(responseBody)) {
            return new ErrorResponseException(statusCode);
        }
        return new ErrorResponseException(statusCode, jsonMapper.readValue(responseBody, ErrorResponse.class));
    }

    private boolean isEmpty(PushbackInputStream responseBody) {
        try {
            int nextByte = responseBody.read();
            if (nextByte == -1) {
                return true;
            }
            responseBody.unread(nextByte);
            return false;
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
