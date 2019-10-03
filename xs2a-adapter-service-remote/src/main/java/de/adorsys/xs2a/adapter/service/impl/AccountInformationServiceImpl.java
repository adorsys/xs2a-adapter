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

package de.adorsys.xs2a.adapter.service.impl;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import de.adorsys.xs2a.adapter.api.remote.AccountInformationClient;
import de.adorsys.xs2a.adapter.api.remote.Xs2aAdapterClientParseException;
import de.adorsys.xs2a.adapter.mapper.*;
import de.adorsys.xs2a.adapter.model.*;
import de.adorsys.xs2a.adapter.service.AccountInformationService;
import de.adorsys.xs2a.adapter.service.RequestHeaders;
import de.adorsys.xs2a.adapter.service.RequestParams;
import de.adorsys.xs2a.adapter.service.Response;
import de.adorsys.xs2a.adapter.service.exception.NotAcceptableException;
import de.adorsys.xs2a.adapter.service.mapper.ResponseHeadersMapper;
import de.adorsys.xs2a.adapter.service.model.*;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Map;

public class AccountInformationServiceImpl implements AccountInformationService {
    private static final Logger log = LoggerFactory.getLogger(AccountInformationServiceImpl.class);

    private final AccountInformationClient client;
    private final ConsentMapper consentMapper = Mappers.getMapper(ConsentMapper.class);
    private final ConsentCreationResponseMapper creationResponseMapper = Mappers.getMapper(ConsentCreationResponseMapper.class);
    private final ConsentInformationMapper consentInformationMapper = Mappers.getMapper(ConsentInformationMapper.class);
    private final ConsentStatusResponseMapper statusResponseMapper = Mappers.getMapper(ConsentStatusResponseMapper.class);
    private final ScaStatusResponseMapper scaStatusResponseMapper = Mappers.getMapper(ScaStatusResponseMapper.class);
    private final BalanceReportMapper balanceReportMapper = Mappers.getMapper(BalanceReportMapper.class);
    private final AccountListHolderMapper accountListHolderMapper = Mappers.getMapper(AccountListHolderMapper.class);
    private final TransactionsReportMapper transactionsReportMapper = Mappers.getMapper(TransactionsReportMapper.class);
    private final StartScaProcessResponseMapper scaProcessResponseMapper = Mappers.getMapper(StartScaProcessResponseMapper.class);
    private final ResponseHeadersMapper responseHeadersMapper = Mappers.getMapper(ResponseHeadersMapper.class);
    private final ObjectMapper objectMapper;

    public AccountInformationServiceImpl(AccountInformationClient client) {
        this.client = client;
        this.objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    public AccountInformationServiceImpl(AccountInformationClient client, ObjectMapper objectMapper) {
        this.client = client;
        this.objectMapper = objectMapper;
    }

    @Override
    public Response<ConsentCreationResponse> createConsent(RequestHeaders requestHeaders, Consents consents) {
        ConsentsTO consentsTO = consentMapper.toConsentsTO(consents);
        ResponseEntity<ConsentsResponse201TO> responseEntity = client.createConsent(requestHeaders.toMap(), consentsTO);
        ConsentCreationResponse consentCreationResponse = creationResponseMapper.toConsentCreationResponse(responseEntity.getBody());
        return new Response<>(
            responseEntity.getStatusCodeValue(),
            consentCreationResponse,
            responseHeadersMapper.getHeaders(responseEntity.getHeaders())
        );
    }

    @Override
    public Response<ConsentInformation> getConsentInformation(String consentId, RequestHeaders requestHeaders) {
        ResponseEntity<ConsentInformationResponse200JsonTO> responseEntity = client.getConsentInformation(consentId, requestHeaders.toMap());
        ConsentInformation information = consentInformationMapper.toConsentInformation(responseEntity.getBody());
        return new Response<>(
            responseEntity.getStatusCodeValue(),
            information,
            responseHeadersMapper.getHeaders(responseEntity.getHeaders())
        );
    }

    @Override
    public Response<Void> deleteConsent(String consentId, RequestHeaders requestHeaders) {
        ResponseEntity<Void> responseEntity = client.deleteConsent(consentId, requestHeaders.toMap());
        return new Response<>(
            responseEntity.getStatusCodeValue(),
            null,
            responseHeadersMapper.getHeaders(responseEntity.getHeaders())
        );
    }

    @Override
    public Response<ConsentStatusResponse> getConsentStatus(String consentId, RequestHeaders requestHeaders) {
        ResponseEntity<ConsentStatusResponse200TO> responseEntity = client.getConsentStatus(consentId, requestHeaders.toMap());
        ConsentStatusResponse statusResponse = statusResponseMapper.toConsentStatusResponse(responseEntity.getBody());
        return new Response<>(
            responseEntity.getStatusCodeValue(),
            statusResponse,
            responseHeadersMapper.getHeaders(responseEntity.getHeaders())
        );
    }

    @Override
    public Response<StartScaProcessResponse> startConsentAuthorisation(String consentId, RequestHeaders requestHeaders) {
        ResponseEntity<StartScaprocessResponseTO> responseEntity = client.startConsentAuthorisation(consentId, requestHeaders.toMap(), createEmptyBody());
        StartScaProcessResponse scaProcessResponse = scaProcessResponseMapper.toStartScaProcessResponse(responseEntity.getBody());
        return new Response<>(
            responseEntity.getStatusCodeValue(),
            scaProcessResponse,
            responseHeadersMapper.getHeaders(responseEntity.getHeaders())
        );
    }

    private ObjectNode createEmptyBody() {
        return new ObjectNode(JsonNodeFactory.instance);
    }

    @Override
    public Response<StartScaProcessResponse> startConsentAuthorisation(String consentId, RequestHeaders requestHeaders, UpdatePsuAuthentication updatePsuAuthentication) {
        ResponseEntity<StartScaprocessResponseTO> responseEntity = client.startConsentAuthorisation(consentId, requestHeaders.toMap(), objectMapper.valueToTree(updatePsuAuthentication));

        StartScaProcessResponse scaProcessResponse = scaProcessResponseMapper.toStartScaProcessResponse(responseEntity.getBody());
        return new Response<>(
            responseEntity.getStatusCodeValue(),
            scaProcessResponse,
            responseHeadersMapper.getHeaders(responseEntity.getHeaders())
        );
    }

    @Override
    public Response<SelectPsuAuthenticationMethodResponse> updateConsentsPsuData(String consentId, String authorisationId, RequestHeaders requestHeaders, SelectPsuAuthenticationMethod selectPsuAuthenticationMethod) {
        ResponseEntity<Object> responseEntity = client.updateConsentsPsuData(consentId, authorisationId, requestHeaders.toMap(), objectMapper.valueToTree(selectPsuAuthenticationMethod));
        SelectPsuAuthenticationMethodResponse selectPsuAuthenticationMethodResponse = objectMapper.convertValue(responseEntity.getBody(), SelectPsuAuthenticationMethodResponse.class);
        return new Response<>(
            responseEntity.getStatusCodeValue(),
            selectPsuAuthenticationMethodResponse,
            responseHeadersMapper.getHeaders(responseEntity.getHeaders())
        );
    }

    @Override
    public Response<ScaStatusResponse> updateConsentsPsuData(String consentId, String authorisationId, RequestHeaders requestHeaders, TransactionAuthorisation transactionAuthorisation) {
        ResponseEntity<Object> responseEntity = client.updateConsentsPsuData(consentId, authorisationId, requestHeaders.toMap(), objectMapper.valueToTree(transactionAuthorisation));
        ScaStatusResponse scaStatusResponse = objectMapper.convertValue(responseEntity.getBody(), ScaStatusResponse.class);
        return new Response<>(
            responseEntity.getStatusCodeValue(),
            scaStatusResponse,
            responseHeadersMapper.getHeaders(responseEntity.getHeaders())
        );
    }

    @Override
    public Response<UpdatePsuAuthenticationResponse> updateConsentsPsuData(String consentId, String authorisationId, RequestHeaders requestHeaders, UpdatePsuAuthentication updatePsuAuthentication) {
        ResponseEntity<Object> responseEntity = client.updateConsentsPsuData(consentId, authorisationId, requestHeaders.toMap(), objectMapper.valueToTree(updatePsuAuthentication));
        UpdatePsuAuthenticationResponse updatePsuAuthenticationResponse = objectMapper.convertValue(responseEntity.getBody(), UpdatePsuAuthenticationResponse.class);
        return new Response<>(
            responseEntity.getStatusCodeValue(),
            updatePsuAuthenticationResponse,
            responseHeadersMapper.getHeaders(responseEntity.getHeaders())
        );
    }

    @Override
    public Response<AccountListHolder> getAccountList(RequestHeaders requestHeaders, RequestParams requestParams) {
        String withBalance = requestParams.toMap().getOrDefault(RequestParams.WITH_BALANCE, Boolean.FALSE.toString());
        ResponseEntity<AccountListTO> responseEntity = client.getAccountList(Boolean.valueOf(withBalance), requestHeaders.toMap());
        AccountListHolder accountListHolder = accountListHolderMapper.toAccountListHolder(responseEntity.getBody());
        return new Response<>(
            responseEntity.getStatusCodeValue(),
            accountListHolder,
            responseHeadersMapper.getHeaders(responseEntity.getHeaders())
        );
    }

    @Override
    public Response<TransactionsReport> getTransactionList(String accountId, RequestHeaders requestHeaders, RequestParams requestParams) {
        if (!requestHeaders.isAcceptJson()) {
            throw new NotAcceptableException("Unsupported accept type: " + requestHeaders.get(RequestHeaders.ACCEPT));
        }
        ResponseEntity<Object> responseEntity = getTransactionListFromClient(accountId, requestParams, requestHeaders);
        TransactionsResponse200JsonTO transactionsResponse200JsonTO = objectMapper.convertValue(responseEntity.getBody(), TransactionsResponse200JsonTO.class);
        TransactionsReport transactionsReport = transactionsReportMapper.toTransactionsReport(transactionsResponse200JsonTO);
        return new Response<>(
            responseEntity.getStatusCodeValue(),
            transactionsReport,
            responseHeadersMapper.getHeaders(responseEntity.getHeaders())
        );
    }

    private <T> ResponseEntity<T> getTransactionListFromClient(String accountId, RequestParams requestParams, RequestHeaders requestHeaders) {
        Map<String, String> requestParamMap = requestParams.toMap();
        LocalDate dateFrom = strToLocalDate(requestParamMap.get(RequestParams.DATE_FROM));
        LocalDate dateTo = strToLocalDate(requestParamMap.get(RequestParams.DATE_TO));
        String entryReferenceFrom = requestParamMap.get(RequestParams.ENTRY_REFERENCE_FROM);
        BookingStatusTO bookingStatus = BookingStatusTO.fromValue(requestParamMap.get(RequestParams.BOOKING_STATUS));
        Boolean deltaList = Boolean.valueOf(requestParamMap.get(RequestParams.DELTA_LIST));
        Boolean withBalance = Boolean.valueOf(requestParamMap.get(RequestParams.WITH_BALANCE));
        if (requestHeaders.isAcceptJson()) {
            return (ResponseEntity<T>) client.getTransactionList(accountId, dateFrom, dateTo, entryReferenceFrom, bookingStatus, deltaList, withBalance, requestHeaders.toMap());

        }
        return (ResponseEntity<T>) client.getTransactionListAsString(accountId, dateFrom, dateTo, entryReferenceFrom, bookingStatus, deltaList, withBalance, requestHeaders.toMap());
    }

    @Override
    public Response<String> getTransactionListAsString(String accountId, RequestHeaders requestHeaders, RequestParams requestParams) {
        ResponseEntity<String> responseEntity = getTransactionListFromClient(accountId, requestParams, requestHeaders);

        return new Response<>(
            responseEntity.getStatusCodeValue(),
            responseEntity.getBody(),
            responseHeadersMapper.getHeaders(responseEntity.getHeaders())
        );
    }

    @Override
    public Response<ScaStatusResponse> getConsentScaStatus(String consentId, String authorisationId, RequestHeaders requestHeaders) {
        ResponseEntity<ScaStatusResponseTO> responseEntity = client.getConsentScaStatus(consentId, authorisationId, requestHeaders.toMap());
        ScaStatusResponse scaStatusResponse = scaStatusResponseMapper.toScaStatusResponse(responseEntity.getBody());
        return new Response<>(
            responseEntity.getStatusCodeValue(),
            scaStatusResponse,
            responseHeadersMapper.getHeaders(responseEntity.getHeaders())
        );
    }

    @Override
    public Response<BalanceReport> getBalances(String accountId, RequestHeaders requestHeaders) {
        ResponseEntity<ReadAccountBalanceResponse200TO> responseEntity = client.getBalances(accountId, requestHeaders.toMap());
        BalanceReport balanceReport = balanceReportMapper.toBalanceReport(responseEntity.getBody());
        return new Response<>(
            responseEntity.getStatusCodeValue(),
            balanceReport,
            responseHeadersMapper.getHeaders(responseEntity.getHeaders())
        );
    }

    private LocalDate strToLocalDate(String date) {
        if (StringUtils.isNotBlank(date)) {
            try {
                return LocalDate.parse(date);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                throw new Xs2aAdapterClientParseException(e.getMessage());
            }
        }
        return null;
    }
}
