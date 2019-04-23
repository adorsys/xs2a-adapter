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

import de.adorsys.xs2a.gateway.http.HttpClient;
import de.adorsys.xs2a.gateway.http.JsonMapper;
import de.adorsys.xs2a.gateway.service.ErrorResponse;
import de.adorsys.xs2a.gateway.service.RequestParams;
import de.adorsys.xs2a.gateway.service.ResponseHeaders;
import de.adorsys.xs2a.gateway.service.exception.ErrorResponseException;

import java.io.IOException;
import java.io.PushbackInputStream;
import java.io.UncheckedIOException;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class AbstractService {
    protected static final String SLASH_SEPARATOR = "/";
    protected static final String SLASH_TRANSACTIONS = "/transactions";
    protected static final String SLASH_AUTHORISATIONS = "/authorisations";
    protected static final String SLASH_AUTHORISATIONS_SLASH = "/authorisations/";
    protected static final String CONTENT_TYPE_HEADER = "Content-Type";
    protected static final String APPLICATION_JSON = "application/json";
    protected static final String ACCEPT_HEADER = "Accept";
    protected final JsonMapper jsonMapper = new JsonMapper();
    protected HttpClient httpClient = HttpClient.newHttpClient();

    public void setHttpClient(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    protected Map<String, String> addConsentIdHeader(Map<String, String> map) {
        return map;
    }

    protected Map<String, String> populatePostHeaders(Map<String, String> map) {
        return map;
    }

    protected Map<String, String> populatePutHeaders(Map<String, String> map) {
        return map;
    }

    protected Map<String, String> populateGetHeaders(Map<String, String> map) {
        return map;
    }

    <T> HttpClient.ResponseHandler<T> responseHandler(Class<T> klass) {
        return (statusCode, responseBody, responseHeaders) -> {
            if (statusCode == 200 || statusCode == 201) {
                return jsonMapper.readValue(responseBody, klass);
            }
            throw responseException(statusCode, new PushbackInputStream(responseBody), responseHeaders);
        };
    }

    private ErrorResponseException responseException(int statusCode, PushbackInputStream responseBody, ResponseHeaders responseHeaders) {
        if (isEmpty(responseBody)) {
            return new ErrorResponseException(statusCode, responseHeaders);
        }
        return new ErrorResponseException(statusCode, responseHeaders, jsonMapper.readValue(responseBody, ErrorResponse.class));
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

    protected static String buildUri(String uri, RequestParams requestParams) {
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