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

import de.adorsys.xs2a.gateway.service.GeneralResponse;
import de.adorsys.xs2a.gateway.service.RequestHeaders;
import de.adorsys.xs2a.gateway.service.RequestParams;
import de.adorsys.xs2a.gateway.service.StartScaProcessResponse;
import de.adorsys.xs2a.gateway.service.account.AccountListHolder;
import de.adorsys.xs2a.gateway.service.account.TransactionsReport;
import de.adorsys.xs2a.gateway.service.ais.*;
import de.adorsys.xs2a.gateway.service.model.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PushbackInputStream;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class BaseAccountInformationService extends AbstractService implements AccountInformationService {

    private final Pattern charsetPattern = Pattern.compile("charset=([^;]+)");

    @Override
    public GeneralResponse<ConsentCreationResponse> createConsent(Consents body, RequestHeaders requestHeaders) {
        Map<String, String> headersMap = populatePostHeaders(requestHeaders.toMap());

        String bodyString = jsonMapper.writeValueAsString(jsonMapper.convertValue(body, Consents.class));

        return httpClient.post(getConsentBaseUri(), bodyString, headersMap,
                               responseHandler(ConsentCreationResponse.class));
    }

    @Override
    public GeneralResponse<ConsentInformation> getConsentInformation(String consentId, RequestHeaders requestHeaders) {
        String uri = getConsentBaseUri() + SLASH_SEPARATOR + consentId;
        Map<String, String> headersMap = populateGetHeaders(requestHeaders.toMap());
        return httpClient.get(uri, headersMap, responseHandler(ConsentInformation.class));
    }

    protected <T> GeneralResponse<ConsentInformation> getConsentInformation(String consentId, RequestHeaders requestHeaders, Class<T> klass, Function<T, ConsentInformation> mapper) {
        String uri = getConsentBaseUri() + SLASH_SEPARATOR + consentId;
        Map<String, String> headersMap = populateGetHeaders(requestHeaders.toMap());
        GeneralResponse<T> response = httpClient.get(uri, headersMap, responseHandler(klass));
        ConsentInformation consentInformation = mapper.apply(response.getResponseBody());

        return new GeneralResponse<>(response.getStatusCode(), consentInformation, response.getResponseHeaders());
    }

    @Override
    public GeneralResponse<ConsentStatusResponse> getConsentStatus(String consentId, RequestHeaders requestHeaders) {
        String uri = getConsentBaseUri() + SLASH_SEPARATOR + consentId + "/status";
        Map<String, String> headersMap = populateGetHeaders(requestHeaders.toMap());

        return httpClient.get(uri, headersMap, responseHandler(ConsentStatusResponse.class));
    }

    @Override
    public GeneralResponse<StartScaProcessResponse> startConsentAuthorisation(String consentId, RequestHeaders requestHeaders) {
        String uri = getConsentBaseUri() + SLASH_SEPARATOR + consentId + SLASH_AUTHORISATIONS;
        Map<String, String> headersMap = populatePostHeaders(requestHeaders.toMap());

        return httpClient.post(uri, headersMap, responseHandler(StartScaProcessResponse.class));
    }

    @Override
    public GeneralResponse<StartScaProcessResponse> startConsentAuthorisation(String consentId, RequestHeaders requestHeaders, UpdatePsuAuthentication updatePsuAuthentication) {
        String uri = getConsentBaseUri() + SLASH_SEPARATOR + consentId + SLASH_AUTHORISATIONS;
        Map<String, String> headersMap = populatePostHeaders(requestHeaders.toMap());
        String body = jsonMapper.writeValueAsString(updatePsuAuthentication);

        return httpClient.post(uri, body, headersMap, responseHandler(StartScaProcessResponse.class));
    }

    @Override
    public GeneralResponse<UpdatePsuAuthenticationResponse> updateConsentsPsuData(String consentId, String authorisationId, RequestHeaders requestHeaders,
                                                                 UpdatePsuAuthentication updatePsuAuthentication) {
        String uri = getConsentBaseUri() + SLASH_SEPARATOR + consentId + SLASH_AUTHORISATIONS_SLASH + authorisationId;
        Map<String, String> headersMap = populatePutHeaders(requestHeaders.toMap());
        String body = jsonMapper.writeValueAsString(updatePsuAuthentication);

        return httpClient.put(uri, body, headersMap, responseHandler(UpdatePsuAuthenticationResponse.class));
    }

    @Override
    public GeneralResponse<SelectPsuAuthenticationMethodResponse> updateConsentsPsuData(String consentId, String authorisationId, RequestHeaders requestHeaders, SelectPsuAuthenticationMethod selectPsuAuthenticationMethod) {
        String uri = getConsentBaseUri() + SLASH_SEPARATOR + consentId + SLASH_AUTHORISATIONS_SLASH + authorisationId;
        Map<String, String> headersMap = populatePutHeaders(requestHeaders.toMap());
        String body = jsonMapper.writeValueAsString(selectPsuAuthenticationMethod);

        return httpClient.put(uri, body, headersMap, responseHandler(SelectPsuAuthenticationMethodResponse.class));

    }

    @Override
    public GeneralResponse<ScaStatusResponse> updateConsentsPsuData(String consentId, String authorisationId, RequestHeaders requestHeaders, TransactionAuthorisation transactionAuthorisation) {
        String uri = getConsentBaseUri() + SLASH_SEPARATOR + consentId + SLASH_AUTHORISATIONS_SLASH + authorisationId;
        Map<String, String> headersMap = populatePutHeaders(requestHeaders.toMap());
        String body = jsonMapper.writeValueAsString(transactionAuthorisation);

        return httpClient.put(uri, body, headersMap, responseHandler(ScaStatusResponse.class));
    }

    @Override
    public GeneralResponse<AccountListHolder> getAccountList(RequestHeaders requestHeaders, RequestParams requestParams) {
        Map<String, String> headersMap = populateGetHeaders(requestHeaders.toMap());
        headersMap = addConsentIdHeader(headersMap);

        String uri = buildUri(getAccountsBaseUri(), requestParams);

        return httpClient.get(uri, headersMap, responseHandler(AccountListHolder.class));
    }

    @Override
    public GeneralResponse<TransactionsReport> getTransactionList(String accountId, RequestHeaders requestHeaders, RequestParams requestParams) {
        Map<String, String> headersMap = requestHeaders.toMap();
        headersMap.put(ACCEPT_HEADER, APPLICATION_JSON);

        String uri = getTransactionListUri(accountId, requestParams);

        return httpClient.get(uri, headersMap, responseHandler(TransactionsReport.class));
    }

    private String getTransactionListUri(String accountId, RequestParams requestParams) {
        String uri = getAccountsBaseUri() + SLASH_SEPARATOR + accountId + SLASH_TRANSACTIONS;
        uri = buildUri(uri, requestParams);
        return uri;
    }

    @Override
    public GeneralResponse<String> getTransactionListAsString(String accountId, RequestHeaders requestHeaders, RequestParams requestParams) {
        String uri = getTransactionListUri(accountId, requestParams);
        return httpClient.get(uri, requestHeaders.toMap(), (statusCode, responseBody, responseHeaders) -> {
            if (statusCode == 200) {
                try(ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = responseBody.read(buffer)) != -1) {
                        baos.write(buffer, 0, length);
                    }

                    Matcher matcher = charsetPattern.matcher(responseHeaders.getHeader(CONTENT_TYPE_HEADER));
                    if (matcher.find()) {
                        return baos.toString(matcher.group(1));
                    }

                    return baos.toString(StandardCharsets.UTF_8.name());
                } catch (IOException e) {
                    throw new UncheckedIOException(e);
                }
            }
            throw responseException(statusCode, new PushbackInputStream(responseBody), responseHeaders);
        });
    }

    protected abstract String getConsentBaseUri();

    protected abstract String getAccountsBaseUri();
}
