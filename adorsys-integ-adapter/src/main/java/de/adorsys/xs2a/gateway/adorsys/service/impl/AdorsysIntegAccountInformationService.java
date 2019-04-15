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

import de.adorsys.xs2a.gateway.http.HttpClient;
import de.adorsys.xs2a.gateway.http.JsonMapper;
import de.adorsys.xs2a.gateway.service.ErrorResponse;
import de.adorsys.xs2a.gateway.service.Headers;
import de.adorsys.xs2a.gateway.service.ais.*;
import de.adorsys.xs2a.gateway.service.exception.ErrorResponseException;

import java.util.Map;

public class AdorsysIntegAccountInformationService implements AccountInformationService {
    private static final String AIS_URI = "https://dev-xs2a.cloud.adorsys.de/v1/consents";
    private static final String SLASH_SEPARATOR = "/";
    private static final String CONTENT_TYPE_HEADER = "Content-Type";
    private static final String APPLICATION_JSON = "application/json";
    private static final String ACCEPT_HEADER = "Accept";
    private final JsonMapper jsonMapper = new JsonMapper();
    private HttpClient httpClient = HttpClient.newHttpClient();

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
}
