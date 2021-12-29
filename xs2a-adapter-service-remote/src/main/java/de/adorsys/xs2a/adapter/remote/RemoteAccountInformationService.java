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

package de.adorsys.xs2a.adapter.remote;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import de.adorsys.xs2a.adapter.api.AccountInformationService;
import de.adorsys.xs2a.adapter.api.RequestHeaders;
import de.adorsys.xs2a.adapter.api.RequestParams;
import de.adorsys.xs2a.adapter.api.Response;
import de.adorsys.xs2a.adapter.api.exception.NotAcceptableException;
import de.adorsys.xs2a.adapter.api.model.*;
import de.adorsys.xs2a.adapter.remote.client.AccountInformationClient;
import de.adorsys.xs2a.adapter.remote.mapper.ResponseHeadersMapper;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.factory.Mappers;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;

public class RemoteAccountInformationService implements AccountInformationService {

    private final AccountInformationClient client;
    private final ResponseHeadersMapper responseHeadersMapper =
        Mappers.getMapper(ResponseHeadersMapper.class);
    final ObjectMapper objectMapper;

    public RemoteAccountInformationService(
        AccountInformationClient client, ObjectMapper objectMapper
    ) {
        this.client = client;
        this.objectMapper = objectMapper;
    }

    public RemoteAccountInformationService(AccountInformationClient client) {
        this.client = client;
        this.objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @Override
    public Response<ConsentsResponse201> createConsent(RequestHeaders requestHeaders,
                                                           RequestParams requestParams,
                                                           Consents consents) {
        ResponseEntity<ConsentsResponse201> responseEntity = client.createConsent(requestParams.toMap(),
            requestHeaders.toMap(),
            consents);
        return new Response<>(
            responseEntity.getStatusCodeValue(),
            responseEntity.getBody(),
            responseHeadersMapper.getHeaders(responseEntity.getHeaders())
        );
    }

    @Override
    public Response<ConsentInformationResponse200Json> getConsentInformation(String consentId,
                                                                             RequestHeaders requestHeaders,
                                                                             RequestParams requestParams) {
        ResponseEntity<ConsentInformationResponse200Json> responseEntity =
            client.getConsentInformation(consentId, requestParams.toMap(), requestHeaders.toMap());
        return new Response<>(
            responseEntity.getStatusCodeValue(),
            responseEntity.getBody(),
            responseHeadersMapper.getHeaders(responseEntity.getHeaders())
        );
    }

    @Override
    public Response<Void> deleteConsent(String consentId,
                                        RequestHeaders requestHeaders,
                                        RequestParams requestParams) {
        ResponseEntity<Void> responseEntity =
            client.deleteConsent(consentId, requestParams.toMap(), requestHeaders.toMap());
        return new Response<>(
            responseEntity.getStatusCodeValue(),
            null,
            responseHeadersMapper.getHeaders(responseEntity.getHeaders())
        );
    }

    @Override
    public Response<ConsentStatusResponse200> getConsentStatus(String consentId,
                                                               RequestHeaders requestHeaders,
                                                               RequestParams requestParams) {
        ResponseEntity<ConsentStatusResponse200> responseEntity =
            client.getConsentStatus(consentId, requestParams.toMap(), requestHeaders.toMap());
        return new Response<>(
            responseEntity.getStatusCodeValue(),
            responseEntity.getBody(),
            responseHeadersMapper.getHeaders(responseEntity.getHeaders())
        );
    }

    @Override
    public Response<Authorisations> getConsentAuthorisation(String consentId,
                                                            RequestHeaders requestHeaders,
                                                            RequestParams requestParams) {
        ResponseEntity<Authorisations> responseEntity =
            client.getConsentAuthorisation(consentId,
                requestParams.toMap(),
                requestHeaders.toMap());
        return new Response<>(
            responseEntity.getStatusCodeValue(),
            responseEntity.getBody(),
            responseHeadersMapper.getHeaders(responseEntity.getHeaders())
        );
    }

    @Override
    public Response<StartScaprocessResponse> startConsentAuthorisation(String consentId,
                                                                       RequestHeaders requestHeaders,
                                                                       RequestParams requestParams) {
        ResponseEntity<StartScaprocessResponse> responseEntity =
            client.startConsentAuthorisation(consentId,
                requestParams.toMap(),
                requestHeaders.toMap(),
                createEmptyBody());
        return new Response<>(
            responseEntity.getStatusCodeValue(),
            responseEntity.getBody(),
            responseHeadersMapper.getHeaders(responseEntity.getHeaders())
        );
    }

    private ObjectNode createEmptyBody() {
        return new ObjectNode(JsonNodeFactory.instance);
    }

    @Override
    public Response<StartScaprocessResponse> startConsentAuthorisation(String consentId,
                                                                       RequestHeaders requestHeaders,
                                                                       RequestParams requestParams,
                                                                       UpdatePsuAuthentication updatePsuAuthentication) {
        ResponseEntity<StartScaprocessResponse> responseEntity =
            client.startConsentAuthorisation(consentId,
                requestParams.toMap(),
                requestHeaders.toMap(),
                objectMapper.valueToTree(updatePsuAuthentication));

        return new Response<>(
            responseEntity.getStatusCodeValue(),
            responseEntity.getBody(),
            responseHeadersMapper.getHeaders(responseEntity.getHeaders())
        );
    }

    @Override
    public Response<SelectPsuAuthenticationMethodResponse> updateConsentsPsuData(String consentId,
                                                                                 String authorisationId,
                                                                                 RequestHeaders requestHeaders,
                                                                                 RequestParams requestParams,
                                                                                 SelectPsuAuthenticationMethod selectPsuAuthenticationMethod) {
        ResponseEntity<Object> responseEntity = client.updateConsentsPsuData(
            consentId,
            authorisationId,
            requestParams.toMap(),
            requestHeaders.toMap(),
            objectMapper.valueToTree(selectPsuAuthenticationMethod)
        );
        SelectPsuAuthenticationMethodResponse selectPsuAuthenticationMethodResponse =
            objectMapper.convertValue(
                responseEntity.getBody(),
                SelectPsuAuthenticationMethodResponse.class
            );
        return new Response<>(
            responseEntity.getStatusCodeValue(),
            selectPsuAuthenticationMethodResponse,
            responseHeadersMapper.getHeaders(responseEntity.getHeaders())
        );
    }

    @Override
    public Response<ScaStatusResponse> updateConsentsPsuData(
        String consentId,
        String authorisationId,
        RequestHeaders requestHeaders,
        RequestParams requestParams,
        TransactionAuthorisation transactionAuthorisation
    ) {
        ResponseEntity<Object> responseEntity = client.updateConsentsPsuData(
            consentId,
            authorisationId,
            requestParams.toMap(),
            requestHeaders.toMap(),
            objectMapper.valueToTree(transactionAuthorisation)
        );
        ScaStatusResponse scaStatusResponse =
            objectMapper.convertValue(responseEntity.getBody(), ScaStatusResponse.class);
        return new Response<>(
            responseEntity.getStatusCodeValue(),
            scaStatusResponse,
            responseHeadersMapper.getHeaders(responseEntity.getHeaders())
        );
    }

    @Override
    public Response<UpdatePsuAuthenticationResponse> updateConsentsPsuData(
        String consentId,
        String authorisationId,
        RequestHeaders requestHeaders,
        RequestParams requestParams,
        UpdatePsuAuthentication updatePsuAuthentication
    ) {
        ResponseEntity<Object> responseEntity = client.updateConsentsPsuData(
            consentId,
            authorisationId,
            requestParams.toMap(),
            requestHeaders.toMap(),
            objectMapper.valueToTree(updatePsuAuthentication)
        );
        UpdatePsuAuthenticationResponse updatePsuAuthenticationResponse = objectMapper.convertValue(
            responseEntity.getBody(),
            UpdatePsuAuthenticationResponse.class
        );
        return new Response<>(
            responseEntity.getStatusCodeValue(),
            updatePsuAuthenticationResponse,
            responseHeadersMapper.getHeaders(responseEntity.getHeaders())
        );
    }

    @Override
    public Response<AccountList> getAccountList(
        RequestHeaders requestHeaders,
        RequestParams requestParams
    ) {
        String withBalance = requestParams.toMap().getOrDefault(
            RequestParams.WITH_BALANCE,
            Boolean.FALSE.toString()
        );
        ResponseEntity<AccountList> responseEntity = client.getAccountList(
            Boolean.valueOf(withBalance),
            requestHeaders.toMap()
        );
        return new Response<>(
            responseEntity.getStatusCodeValue(),
            responseEntity.getBody(),
            responseHeadersMapper.getHeaders(responseEntity.getHeaders())
        );
    }

    @Override
    public Response<OK200AccountDetails> readAccountDetails(
        String accountId,
        RequestHeaders requestHeaders,
        RequestParams requestParams
    ) {
        String withBalance = requestParams.toMap().getOrDefault(
            RequestParams.WITH_BALANCE,
            Boolean.FALSE.toString()
        );
        ResponseEntity<OK200AccountDetails> responseEntity = client.readAccountDetails(
            accountId,
            Boolean.valueOf(withBalance),
            requestHeaders.toMap()
        );
        return new Response<>(
            responseEntity.getStatusCodeValue(),
            responseEntity.getBody(),
            responseHeadersMapper.getHeaders(responseEntity.getHeaders())
        );
    }

    @Override
    public Response<TransactionsResponse200Json> getTransactionList(
        String accountId,
        RequestHeaders requestHeaders,
        RequestParams requestParams
    ) {
        if (!requestHeaders.isAcceptJson()) {
            throw new NotAcceptableException(
                "Unsupported accept type: " + requestHeaders.get(RequestHeaders.ACCEPT)
            );
        }
        ResponseEntity<String> responseEntity = getTransactionListFromClient(
            accountId,
            requestParams,
            requestHeaders
        );
        TransactionsResponse200Json transactionsResponse = null;
        try {
            transactionsResponse = objectMapper.readValue(
                responseEntity.getBody(),
                TransactionsResponse200Json.class
            );
        } catch (IOException e) {
            throw new Xs2aAdapterClientParseException(e);
        }
        return new Response<>(
            responseEntity.getStatusCodeValue(),
            transactionsResponse,
            responseHeadersMapper.getHeaders(responseEntity.getHeaders())
        );
    }

    @Override
    public Response<OK200TransactionDetails> getTransactionDetails(String accountId,
                                                                   String transactionId,
                                                                   RequestHeaders requestHeaders,
                                                                   RequestParams requestParams) {
        ResponseEntity<OK200TransactionDetails> response = client.getTransactionDetails(accountId,
            transactionId,
            requestParams.toMap(),
            requestHeaders.toMap());
        return new Response<>(response.getStatusCodeValue(),
            response.getBody(),
            responseHeadersMapper.getHeaders(response.getHeaders()));
    }

    private ResponseEntity<String> getTransactionListFromClient(
        String accountId,
        RequestParams requestParams,
        RequestHeaders requestHeaders
    ) {
        Map<String, String> requestParamMap = requestParams.toMap();
        LocalDate dateFrom = strToLocalDate(requestParamMap.get(RequestParams.DATE_FROM));
        LocalDate dateTo = strToLocalDate(requestParamMap.get(RequestParams.DATE_TO));
        String entryReferenceFrom = requestParamMap.get(RequestParams.ENTRY_REFERENCE_FROM);
        BookingStatusGeneric bookingStatus = BookingStatusGeneric.fromValue(
            requestParamMap.get(RequestParams.BOOKING_STATUS)
        );
        Boolean deltaList = Boolean.valueOf(requestParamMap.get(RequestParams.DELTA_LIST));
        Boolean withBalance = Boolean.valueOf(requestParamMap.get(RequestParams.WITH_BALANCE));

        return client.getTransactionListAsString(
            accountId,
            dateFrom,
            dateTo,
            entryReferenceFrom,
            bookingStatus,
            deltaList,
            withBalance,
            requestHeaders.toMap()
        );
    }

    @Override
    public Response<String> getTransactionListAsString(
        String accountId, RequestHeaders requestHeaders, RequestParams requestParams
    ) {
        ResponseEntity<String> responseEntity = getTransactionListFromClient(
            accountId, requestParams, requestHeaders
        );

        String body = responseEntity.getBody();
        if (requestHeaders.isAcceptJson()) {
            TransactionsResponse200Json json = null;
            try {
                json = objectMapper.readValue(body, TransactionsResponse200Json.class);
                body = objectMapper.writeValueAsString(json);
            } catch (IOException e) {
                throw new Xs2aAdapterClientParseException(e);
            }
        }

        return new Response<>(
            responseEntity.getStatusCodeValue(),
            body,
            responseHeadersMapper.getHeaders(responseEntity.getHeaders())
        );
    }

    @Override
    public Response<ScaStatusResponse> getConsentScaStatus(String consentId,
                                                           String authorisationId,
                                                           RequestHeaders requestHeaders,
                                                           RequestParams requestParams) {
        ResponseEntity<ScaStatusResponse> responseEntity =
            client.getConsentScaStatus(consentId, authorisationId, requestParams.toMap(), requestHeaders.toMap());
        return new Response<>(
            responseEntity.getStatusCodeValue(),
            responseEntity.getBody(),
            responseHeadersMapper.getHeaders(responseEntity.getHeaders())
        );
    }

    @Override
    public Response<ReadAccountBalanceResponse200> getBalances(String accountId,
                                                               RequestHeaders requestHeaders,
                                                               RequestParams requestParams) {
        ResponseEntity<ReadAccountBalanceResponse200> responseEntity =
            client.getBalances(accountId, requestParams.toMap(), requestHeaders.toMap());
        return new Response<>(
            responseEntity.getStatusCodeValue(),
            responseEntity.getBody(),
            responseHeadersMapper.getHeaders(responseEntity.getHeaders())
        );
    }

    @Override
    public Response<CardAccountList> getCardAccountList(RequestHeaders requestHeaders, RequestParams requestParams) {
        ResponseEntity<CardAccountList> responseEntity =
            client.getCardAccount(requestParams.toMap(), requestHeaders.toMap());
        return new Response<>(responseEntity.getStatusCodeValue(),
            responseEntity.getBody(),
            responseHeadersMapper.getHeaders(responseEntity.getHeaders()));
    }

    @Override
    public Response<OK200CardAccountDetails> getCardAccountDetails(String accountId,
                                                                   RequestHeaders requestHeaders,
                                                                   RequestParams requestParams) {
        ResponseEntity<OK200CardAccountDetails> responseEntity =
            client.ReadCardAccount(accountId, requestParams.toMap(), requestHeaders.toMap());
        return new Response<>(responseEntity.getStatusCodeValue(),
            responseEntity.getBody(),
            responseHeadersMapper.getHeaders(responseEntity.getHeaders()));
    }

    @Override
    public Response<ReadCardAccountBalanceResponse200> getCardAccountBalances(String accountId,
                                                                              RequestHeaders requestHeaders,
                                                                              RequestParams requestParams) {
        ResponseEntity<ReadCardAccountBalanceResponse200> responseEntity =
            client.getCardAccountBalances(accountId, requestParams.toMap(), requestHeaders.toMap());
        return new Response<>(responseEntity.getStatusCodeValue(),
            responseEntity.getBody(),
            responseHeadersMapper.getHeaders(responseEntity.getHeaders()));
    }

    @Override
    public Response<CardAccountsTransactionsResponse200> getCardAccountTransactionList(String accountId,
                                                                                       RequestHeaders requestHeaders,
                                                                                       RequestParams requestParams) {
        ResponseEntity<CardAccountsTransactionsResponse200> responseEntity =
            client.getCardAccountTransactionList(accountId,
                requestParams.dateFrom(),
                requestParams.dateTo(),
                requestParams.entryReferenceFrom(),
                requestParams.bookingStatus() != null ? BookingStatusCard.fromValue(requestParams.bookingStatus()) : null,
                requestParams.deltaList(),
                requestParams.withBalance(),
                requestParams.toMap(),
                requestHeaders.toMap());
        return new Response<>(responseEntity.getStatusCodeValue(),
            responseEntity.getBody(),
            responseHeadersMapper.getHeaders(responseEntity.getHeaders()));
    }

    private LocalDate strToLocalDate(String date) {
        if (StringUtils.isNotBlank(date)) {
            try {
                return LocalDate.parse(date);
            } catch (Exception e) {
                throw new Xs2aAdapterClientParseException(e);
            }
        }
        return null;
    }
}
