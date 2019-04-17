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
import de.adorsys.xs2a.gateway.service.RequestParams;
import de.adorsys.xs2a.gateway.service.StartScaProcessResponse;
import de.adorsys.xs2a.gateway.service.account.AccountListHolder;
import de.adorsys.xs2a.gateway.service.account.TransactionsReport;
import de.adorsys.xs2a.gateway.service.ais.*;
import de.adorsys.xs2a.gateway.service.exception.ErrorResponseException;
import de.adorsys.xs2a.gateway.service.model.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.Map;
import java.util.stream.Collectors;

public class AdorsysIntegAccountInformationService implements AccountInformationService {
    private static final String AIS_URI = "https://dev-xs2a.cloud.adorsys.de/v1/consents";
    private static final String ACCOUNTS_URI = "https://dev-xs2a.cloud.adorsys.de/v1/accounts";
    private static final String SLASH_SEPARATOR = "/";
    private static final String SLASH_AUTHORISATIONS = "/authorisations";
    private static final String SLASH_AUTHORISATIONS_SLASH = "/authorisations/";
    private static final String SLASH_TRANSACTIONS = "/transactions";
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
            if (isEmpty(responseBody)) {
                throw new ErrorResponseException(statusCode);
            }
            throw new ErrorResponseException(statusCode, jsonMapper.readValue(responseBody, ErrorResponse.class));
        };
    }

    private boolean isEmpty(InputStream responseBody) {
        try {
            return responseBody.available() == 0;
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public TransactionsReport getTransactionList(String accountId, Headers headers, RequestParams requestParams) {
        Map<String, String> headersMap = headers.toMap();
        headersMap.put(ACCEPT_HEADER, APPLICATION_JSON);

        String uri = ACCOUNTS_URI + SLASH_SEPARATOR + accountId + SLASH_TRANSACTIONS;
        uri = buildUri(uri, requestParams);

        return httpClient.get(uri, headersMap, getResponseHandlerAis(TransactionsReport.class));
    }
}