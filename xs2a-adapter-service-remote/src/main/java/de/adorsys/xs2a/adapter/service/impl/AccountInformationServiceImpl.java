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
import de.adorsys.xs2a.adapter.mapper.*;
import de.adorsys.xs2a.adapter.model.*;
import de.adorsys.xs2a.adapter.service.*;
import de.adorsys.xs2a.adapter.service.account.AccountListHolder;
import de.adorsys.xs2a.adapter.service.account.BalanceReport;
import de.adorsys.xs2a.adapter.service.account.TransactionsReport;
import de.adorsys.xs2a.adapter.service.ais.*;
import de.adorsys.xs2a.adapter.service.exception.NotAcceptableException;
import de.adorsys.xs2a.adapter.service.model.*;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.factory.Mappers;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AccountInformationServiceImpl implements AccountInformationService {

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
    private final ObjectMapper objectMapper = new ObjectMapper();

    public AccountInformationServiceImpl(AccountInformationClient client) {
        this.client = client;
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    }

    @Override
    public GeneralResponse<ConsentCreationResponse> createConsent(RequestHeaders requestHeaders, Consents consents) {
        ConsentsTO consentsTO = consentMapper.toConsentsTO(consents);
        ResponseEntity<ConsentsResponse201TO> responseEntity = client.createConsent(requestHeaders.toMap(), consentsTO);
        ConsentCreationResponse consentCreationResponse = creationResponseMapper.toConsentCreationResponse(responseEntity.getBody());
        return new GeneralResponse<>(responseEntity.getStatusCodeValue(), consentCreationResponse, getHeaders(responseEntity.getHeaders()));
    }

    @Override
    public GeneralResponse<ConsentInformation> getConsentInformation(String consentId, RequestHeaders requestHeaders) {
        ResponseEntity<ConsentInformationResponse200JsonTO> responseEntity = client.getConsentInformation(consentId, requestHeaders.toMap());
        ConsentInformation information = consentInformationMapper.toConsentInformation(responseEntity.getBody());
        return new GeneralResponse<>(responseEntity.getStatusCodeValue(), information, getHeaders(responseEntity.getHeaders()));
    }

    @Override
    public GeneralResponse<ConsentStatusResponse> getConsentStatus(String consentId, RequestHeaders requestHeaders) {
        ResponseEntity<ConsentStatusResponse200TO> responseEntity = client.getConsentStatus(consentId, requestHeaders.toMap());
        ConsentStatusResponse statusResponse = statusResponseMapper.toConsentStatusResponse(responseEntity.getBody());
        return new GeneralResponse<>(responseEntity.getStatusCodeValue(), statusResponse, getHeaders(responseEntity.getHeaders()));
    }

    @Override
    public GeneralResponse<StartScaProcessResponse> startConsentAuthorisation(String consentId, RequestHeaders requestHeaders) {
        ResponseEntity<StartScaprocessResponseTO> responseEntity = client.startConsentAuthorisation(consentId, requestHeaders.toMap(), createEmptyBody());
        StartScaProcessResponse scaProcessResponse = scaProcessResponseMapper.toStartScaProcessResponse(responseEntity.getBody());
        return new GeneralResponse<>(responseEntity.getStatusCodeValue(), scaProcessResponse, getHeaders(responseEntity.getHeaders()));
    }

    private ObjectNode createEmptyBody() {
        return new ObjectNode(JsonNodeFactory.instance);
    }

    @Override
    public GeneralResponse<StartScaProcessResponse> startConsentAuthorisation(String consentId, RequestHeaders requestHeaders, UpdatePsuAuthentication updatePsuAuthentication) {
        ResponseEntity<StartScaprocessResponseTO> responseEntity = client.startConsentAuthorisation(consentId, requestHeaders.toMap(), objectMapper.valueToTree(updatePsuAuthentication));

        StartScaProcessResponse scaProcessResponse = scaProcessResponseMapper.toStartScaProcessResponse(responseEntity.getBody());
        return new GeneralResponse<>(responseEntity.getStatusCodeValue(), scaProcessResponse, getHeaders(responseEntity.getHeaders()));
    }

    @Override
    public GeneralResponse<SelectPsuAuthenticationMethodResponse> updateConsentsPsuData(String consentId, String authorisationId, RequestHeaders requestHeaders, SelectPsuAuthenticationMethod selectPsuAuthenticationMethod) {
        ResponseEntity<Object> responseEntity = client.updateConsentsPsuData(consentId, authorisationId, requestHeaders.toMap(), objectMapper.valueToTree(selectPsuAuthenticationMethod));
        SelectPsuAuthenticationMethodResponse selectPsuAuthenticationMethodResponse = objectMapper.convertValue(responseEntity.getBody(), SelectPsuAuthenticationMethodResponse.class);
        return new GeneralResponse<>(responseEntity.getStatusCodeValue(), selectPsuAuthenticationMethodResponse, getHeaders(responseEntity.getHeaders()));
    }

    @Override
    public GeneralResponse<ScaStatusResponse> updateConsentsPsuData(String consentId, String authorisationId, RequestHeaders requestHeaders, TransactionAuthorisation transactionAuthorisation) {
        ResponseEntity<Object> responseEntity = client.updateConsentsPsuData(consentId, authorisationId, requestHeaders.toMap(), objectMapper.valueToTree(transactionAuthorisation));
        ScaStatusResponse scaStatusResponse = objectMapper.convertValue(responseEntity.getBody(), ScaStatusResponse.class);
        return new GeneralResponse<>(responseEntity.getStatusCodeValue(), scaStatusResponse, getHeaders(responseEntity.getHeaders()));
    }

    @Override
    public GeneralResponse<UpdatePsuAuthenticationResponse> updateConsentsPsuData(String consentId, String authorisationId, RequestHeaders requestHeaders, UpdatePsuAuthentication updatePsuAuthentication) {
        ResponseEntity<Object> responseEntity = client.updateConsentsPsuData(consentId, authorisationId, requestHeaders.toMap(), objectMapper.valueToTree(updatePsuAuthentication));
        UpdatePsuAuthenticationResponse updatePsuAuthenticationResponse = objectMapper.convertValue(responseEntity.getBody(), UpdatePsuAuthenticationResponse.class);
        return new GeneralResponse<>(responseEntity.getStatusCodeValue(), updatePsuAuthenticationResponse, getHeaders(responseEntity.getHeaders()));
    }

    @Override
    public GeneralResponse<AccountListHolder> getAccountList(RequestHeaders requestHeaders, RequestParams requestParams) {
        String withBalance = requestParams.toMap().getOrDefault(RequestParams.WITH_BALANCE, Boolean.FALSE.toString());
        ResponseEntity<AccountListTO> responseEntity = client.getAccountList(Boolean.valueOf(withBalance), requestHeaders.toMap());
        AccountListHolder accountListHolder = accountListHolderMapper.toAccountListHolder(responseEntity.getBody());
        return new GeneralResponse<>(responseEntity.getStatusCodeValue(), accountListHolder, getHeaders(responseEntity.getHeaders()));
    }

    @Override
    public GeneralResponse<TransactionsReport> getTransactionList(String accountId, RequestHeaders requestHeaders, RequestParams requestParams) {
        Map<String, String> headersMap = requestHeaders.toMap();
        if (!requestHeaders.isAcceptJson()) {
            throw new NotAcceptableException("Unsupported accept type: " + headersMap.get(RequestHeaders.ACCEPT));
        }
        ResponseEntity<Object> responseEntity = getTransactionListFromClient(accountId, requestParams, headersMap);
        TransactionsResponse200JsonTO transactionsResponse200JsonTO = objectMapper.convertValue(responseEntity.getBody(), TransactionsResponse200JsonTO.class);
        TransactionsReport transactionsReport = transactionsReportMapper.toTransactionsReport(transactionsResponse200JsonTO);
        return new GeneralResponse<>(responseEntity.getStatusCodeValue(), transactionsReport, getHeaders(responseEntity.getHeaders()));
    }

    private ResponseEntity<Object> getTransactionListFromClient(String accountId, RequestParams requestParams, Map<String, String> headersMap) {
        Map<String, String> requestParamMap = requestParams.toMap();
        LocalDate dateFrom = strToLocalDate(requestParamMap.get(RequestParams.DATE_FROM));
        LocalDate dateTo = strToLocalDate(requestParamMap.get(RequestParams.DATE_TO));
        String entryReferenceFrom = requestParamMap.get(RequestParams.ENTRY_REFERENCE_FROM);
        BookingStatusTO bookingStatus = BookingStatusTO.fromValue(requestParamMap.get(RequestParams.BOOKING_STATUS));
        Boolean deltaList = Boolean.valueOf(requestParamMap.get(RequestParams.DELTA_LIST));
        Boolean withBalance = Boolean.valueOf(requestParamMap.get(RequestParams.WITH_BALANCE));
        return client.getTransactionList(accountId, dateFrom, dateTo, entryReferenceFrom, bookingStatus, deltaList, withBalance, headersMap);
    }

    @Override
    public GeneralResponse<String> getTransactionListAsString(String accountId, RequestHeaders requestHeaders, RequestParams requestParams) {
        Map<String, String> headersMap = requestHeaders.toMap();
        ResponseEntity<Object> responseEntity = getTransactionListFromClient(accountId, requestParams, headersMap);

        return new GeneralResponse<>(responseEntity.getStatusCodeValue(), (String) responseEntity.getBody(), getHeaders(responseEntity.getHeaders()));
    }

    @Override
    public GeneralResponse<ScaStatusResponse> getConsentScaStatus(String consentId, String authorisationId, RequestHeaders requestHeaders) {
        ResponseEntity<ScaStatusResponseTO> responseEntity = client.getConsentScaStatus(consentId, authorisationId, requestHeaders.toMap());
        ScaStatusResponse scaStatusResponse = scaStatusResponseMapper.toScaStatusResponse(responseEntity.getBody());
        return new GeneralResponse<>(responseEntity.getStatusCodeValue(), scaStatusResponse, getHeaders(responseEntity.getHeaders()));
    }

    @Override
    public GeneralResponse<BalanceReport> getBalances(String accountId, RequestHeaders requestHeaders) {
        ResponseEntity<ReadAccountBalanceResponse200TO> responseEntity = client.getBalances(accountId, requestHeaders.toMap());
        BalanceReport balanceReport = balanceReportMapper.toBalanceReport(responseEntity.getBody());
        return new GeneralResponse<>(responseEntity.getStatusCodeValue(), balanceReport, getHeaders(responseEntity.getHeaders()));
    }

    private ResponseHeaders getHeaders(HttpHeaders httpHeaders) {
        Set<Map.Entry<String, List<String>>> entrySet = httpHeaders.entrySet();
        Map<String, String> headers = new HashMap<>(entrySet.size());
        for (Map.Entry<String, List<String>> entry : entrySet) {
            List<String> value = entry.getValue();
            if (value != null && !value.isEmpty()) {
                headers.put(entry.getKey(), value.get(0));
            }
        }
        return ResponseHeaders.fromMap(headers);
    }

    private LocalDate strToLocalDate(String date) {
        if (StringUtils.isNotBlank(date)) {
            return LocalDate.parse(date);
        }
        return null;
    }
}
