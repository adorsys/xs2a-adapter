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

import de.adorsys.xs2a.gateway.http.StringUri;
import de.adorsys.xs2a.gateway.service.GeneralResponse;
import de.adorsys.xs2a.gateway.service.RequestHeaders;
import de.adorsys.xs2a.gateway.service.RequestParams;
import de.adorsys.xs2a.gateway.service.StartScaProcessResponse;
import de.adorsys.xs2a.gateway.service.account.AccountListHolder;
import de.adorsys.xs2a.gateway.service.account.TransactionsReport;
import de.adorsys.xs2a.gateway.service.ais.*;
import de.adorsys.xs2a.gateway.service.model.*;

import java.util.Map;
import java.util.function.Function;

public class BaseAccountInformationService extends AbstractService implements AccountInformationService {

    private static final String CONSENTS = "consents";
    private static final String ACCOUNTS = "accounts";
    private static final String TRANSACTIONS = "transactions";

    private final String baseUri;

    public BaseAccountInformationService(String baseUri) {
        this.baseUri = baseUri;
    }

    @Override
    public GeneralResponse<ConsentCreationResponse> createConsent(Consents body, RequestHeaders requestHeaders) {
        Map<String, String> headersMap = populatePostHeaders(requestHeaders.toMap());

        String bodyString = jsonMapper.writeValueAsString(jsonMapper.convertValue(body, Consents.class));

        return httpClient.post(getConsentBaseUri(), bodyString, headersMap,
                               jsonResponseHandler(ConsentCreationResponse.class));
    }

    protected <T> GeneralResponse<ConsentCreationResponse> createConsent(Consents body, RequestHeaders requestHeaders, Class<T> klass, Function<T, ConsentCreationResponse> mapper){
        Map<String, String> headersMap = populatePostHeaders(requestHeaders.toMap());

        String bodyString = jsonMapper.writeValueAsString(jsonMapper.convertValue(body, Consents.class));

        GeneralResponse<T> response = httpClient.post(getConsentBaseUri(), bodyString, headersMap, jsonResponseHandler(klass));
        ConsentCreationResponse creationResponse = mapper.apply(response.getResponseBody());
        return new GeneralResponse<>(response.getStatusCode(), creationResponse, response.getResponseHeaders());
    }

    @Override
    public GeneralResponse<ConsentInformation> getConsentInformation(String consentId, RequestHeaders requestHeaders) {
        String uri = StringUri.fromElements(getConsentBaseUri(), consentId);
        Map<String, String> headersMap = populateGetHeaders(requestHeaders.toMap());
        return httpClient.get(uri, headersMap, jsonResponseHandler(ConsentInformation.class));
    }

    protected <T> GeneralResponse<ConsentInformation> getConsentInformation(String consentId, RequestHeaders requestHeaders, Class<T> klass, Function<T, ConsentInformation> mapper) {
        String uri = StringUri.fromElements(getConsentBaseUri(), consentId);
        Map<String, String> headersMap = populateGetHeaders(requestHeaders.toMap());
        GeneralResponse<T> response = httpClient.get(uri, headersMap, jsonResponseHandler(klass));
        ConsentInformation consentInformation = mapper.apply(response.getResponseBody());

        return new GeneralResponse<>(response.getStatusCode(), consentInformation, response.getResponseHeaders());
    }

    @Override
    public GeneralResponse<ConsentStatusResponse> getConsentStatus(String consentId, RequestHeaders requestHeaders) {
        String uri = StringUri.fromElements(getConsentBaseUri(), consentId, STATUS);
        Map<String, String> headersMap = populateGetHeaders(requestHeaders.toMap());

        return httpClient.get(uri, headersMap, jsonResponseHandler(ConsentStatusResponse.class));
    }

    @Override
    public GeneralResponse<StartScaProcessResponse> startConsentAuthorisation(String consentId, RequestHeaders requestHeaders) {
        String uri = StringUri.fromElements(getConsentBaseUri(), consentId, AUTHORISATIONS);
        Map<String, String> headersMap = populatePostHeaders(requestHeaders.toMap());

        return httpClient.post(uri, headersMap, jsonResponseHandler(StartScaProcessResponse.class));
    }

    protected <T> GeneralResponse<StartScaProcessResponse> startConsentAuthorisation(String consentId, RequestHeaders requestHeaders, Class<T> klass, Function<T, StartScaProcessResponse> mapper) {
        String uri = StringUri.fromElements(getConsentBaseUri(), consentId, AUTHORISATIONS);
        Map<String, String> headersMap = populatePostHeaders(requestHeaders.toMap());

        GeneralResponse<T> response = httpClient.post(uri, headersMap, jsonResponseHandler(klass));
        StartScaProcessResponse startScaProcessResponse = mapper.apply(response.getResponseBody());
        return new GeneralResponse<>(response.getStatusCode(), startScaProcessResponse, response.getResponseHeaders());
    }

    @Override
    public GeneralResponse<StartScaProcessResponse> startConsentAuthorisation(String consentId, RequestHeaders requestHeaders, UpdatePsuAuthentication updatePsuAuthentication) {
        String uri = StringUri.fromElements(getConsentBaseUri(), consentId, AUTHORISATIONS);
        Map<String, String> headersMap = populatePostHeaders(requestHeaders.toMap());
        String body = jsonMapper.writeValueAsString(updatePsuAuthentication);

        return httpClient.post(uri, body, headersMap, jsonResponseHandler(StartScaProcessResponse.class));
    }

    protected <T> GeneralResponse<StartScaProcessResponse> startConsentAuthorisation(String consentId, RequestHeaders requestHeaders, UpdatePsuAuthentication updatePsuAuthentication, Class<T> klass, Function<T, StartScaProcessResponse> mapper) {
        String uri = StringUri.fromElements(getConsentBaseUri(), consentId, AUTHORISATIONS);
        Map<String, String> headersMap = populatePostHeaders(requestHeaders.toMap());
        String body = jsonMapper.writeValueAsString(updatePsuAuthentication);

        GeneralResponse<T> response = httpClient.post(uri, body, headersMap, jsonResponseHandler(klass));
        StartScaProcessResponse startScaProcessResponse = mapper.apply(response.getResponseBody());
        return new GeneralResponse<>(response.getStatusCode(), startScaProcessResponse, response.getResponseHeaders());
    }

    @Override
    public GeneralResponse<UpdatePsuAuthenticationResponse> updateConsentsPsuData(String consentId, String authorisationId, RequestHeaders requestHeaders,
                                                                                  UpdatePsuAuthentication updatePsuAuthentication) {
        String uri = StringUri.fromElements(getConsentBaseUri(), consentId, AUTHORISATIONS, authorisationId);
        Map<String, String> headersMap = populatePutHeaders(requestHeaders.toMap());
        String body = jsonMapper.writeValueAsString(updatePsuAuthentication);

        return httpClient.put(uri, body, headersMap, jsonResponseHandler(UpdatePsuAuthenticationResponse.class));
    }

    @Override
    public GeneralResponse<SelectPsuAuthenticationMethodResponse> updateConsentsPsuData(String consentId, String authorisationId, RequestHeaders requestHeaders, SelectPsuAuthenticationMethod selectPsuAuthenticationMethod) {
        String uri = StringUri.fromElements(getConsentBaseUri(), consentId, AUTHORISATIONS, authorisationId);
        Map<String, String> headersMap = populatePutHeaders(requestHeaders.toMap());
        String body = jsonMapper.writeValueAsString(selectPsuAuthenticationMethod);

        return httpClient.put(uri, body, headersMap, jsonResponseHandler(SelectPsuAuthenticationMethodResponse.class));

    }

    @Override
    public GeneralResponse<ScaStatusResponse> updateConsentsPsuData(String consentId, String authorisationId, RequestHeaders requestHeaders, TransactionAuthorisation transactionAuthorisation) {
        String uri = StringUri.fromElements(getConsentBaseUri(), consentId, AUTHORISATIONS, authorisationId);
        Map<String, String> headersMap = populatePutHeaders(requestHeaders.toMap());
        String body = jsonMapper.writeValueAsString(transactionAuthorisation);

        return httpClient.put(uri, body, headersMap, jsonResponseHandler(ScaStatusResponse.class));
    }

    @Override
    public GeneralResponse<AccountListHolder> getAccountList(RequestHeaders requestHeaders, RequestParams requestParams) {
        Map<String, String> headersMap = populateGetHeaders(requestHeaders.toMap());
        headersMap = addConsentIdHeader(headersMap);

        String uri = buildUri(getAccountsBaseUri(), requestParams);

        return httpClient.get(uri, headersMap, jsonResponseHandler(AccountListHolder.class));
    }

    @Override
    public GeneralResponse<TransactionsReport> getTransactionList(String accountId, RequestHeaders requestHeaders, RequestParams requestParams) {
        Map<String, String> headersMap = populateGetHeaders(requestHeaders.toMap());
        headersMap.put(ACCEPT_HEADER, APPLICATION_JSON);

        String uri = getTransactionListUri(accountId, requestParams);

        return httpClient.get(uri, headersMap, jsonResponseHandler(TransactionsReport.class));
    }

    private String getTransactionListUri(String accountId, RequestParams requestParams) {
        String uri = StringUri.fromElements(getAccountsBaseUri(), accountId, TRANSACTIONS);
        uri = buildUri(uri, requestParams);
        return uri;
    }

    @Override
    public GeneralResponse<String> getTransactionListAsString(String accountId, RequestHeaders requestHeaders, RequestParams requestParams) {
        String uri = getTransactionListUri(accountId, requestParams);
        Map<String, String> headers = populateGetHeaders(requestHeaders.toMap());
        return httpClient.get(uri, headers, xmlResponseHandler());
    }

    protected String getConsentBaseUri() {
        return StringUri.fromElements(baseUri, CONSENTS);
    }

    protected String getAccountsBaseUri() {
        return StringUri.fromElements(baseUri, ACCOUNTS);
    }
}