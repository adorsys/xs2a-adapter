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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
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
    }

    @Override
    public GeneralResponse<ConsentCreationResponse> createConsent(RequestHeaders requestHeaders, Consents consents) {
        ConsentsTO consentsTO = consentMapper.toConsentsTO(consents);
        ResponseEntity<ConsentsResponse201TO> responseEntity = client.createConsent(requestHeaders.toMap(), consentsTO);
        ConsentCreationResponse consentCreationResponse = creationResponseMapper.toConsentCreationResponse(responseEntity.getBody());
        return new GeneralResponse<>(responseEntity.getStatusCodeValue(), consentCreationResponse, getHeaders(responseEntity.getHeaders()));
    }

    @Override
    public GeneralResponse<ConsentInformation> getConsentInformation(String s, RequestHeaders requestHeaders) {
        ResponseEntity<ConsentInformationResponse200JsonTO> responseEntity = client.getConsentInformation(s, requestHeaders.toMap());
        ConsentInformation information = consentInformationMapper.toConsentInformation(responseEntity.getBody());
        return new GeneralResponse<>(responseEntity.getStatusCodeValue(), information, getHeaders(responseEntity.getHeaders()));
    }

    @Override
    public GeneralResponse<ConsentStatusResponse> getConsentStatus(String s, RequestHeaders requestHeaders) {
        ResponseEntity<ConsentStatusResponse200TO> responseEntity = client.getConsentStatus(s, requestHeaders.toMap());
        ConsentStatusResponse statusResponse = statusResponseMapper.toConsentStatusResponse(responseEntity.getBody());
        return new GeneralResponse<>(responseEntity.getStatusCodeValue(), statusResponse, getHeaders(responseEntity.getHeaders()));
    }

    @Override
    public GeneralResponse<StartScaProcessResponse> startConsentAuthorisation(String s, RequestHeaders requestHeaders) {
        ResponseEntity<StartScaprocessResponseTO> responseEntity = client.startConsentAuthorisation(s, requestHeaders.toMap(), createEmptyBody());
        StartScaProcessResponse scaProcessResponse = scaProcessResponseMapper.toStartScaProcessResponse(responseEntity.getBody());
        return new GeneralResponse<>(responseEntity.getStatusCodeValue(), scaProcessResponse, getHeaders(responseEntity.getHeaders()));
    }

    private ObjectNode createEmptyBody() {
        return new ObjectNode(JsonNodeFactory.instance);
    }

    @Override
    public GeneralResponse<StartScaProcessResponse> startConsentAuthorisation(String s, RequestHeaders requestHeaders, UpdatePsuAuthentication updatePsuAuthentication) {
        ResponseEntity<StartScaprocessResponseTO> responseEntity = client.startConsentAuthorisation(s, requestHeaders.toMap(), objectMapper.valueToTree(updatePsuAuthentication));

        StartScaProcessResponse scaProcessResponse = scaProcessResponseMapper.toStartScaProcessResponse(responseEntity.getBody());
        return new GeneralResponse<>(responseEntity.getStatusCodeValue(), scaProcessResponse, getHeaders(responseEntity.getHeaders()));
    }

    @Override
    public GeneralResponse<SelectPsuAuthenticationMethodResponse> updateConsentsPsuData(String s, String s1, RequestHeaders requestHeaders, SelectPsuAuthenticationMethod selectPsuAuthenticationMethod) {
        ResponseEntity<Object> responseEntity = client.updateConsentsPsuData(s, s1, requestHeaders.toMap(), objectMapper.valueToTree(selectPsuAuthenticationMethod));
        return new GeneralResponse<>(responseEntity.getStatusCodeValue(), (SelectPsuAuthenticationMethodResponse) responseEntity.getBody(), getHeaders(responseEntity.getHeaders()));
    }

    @Override
    public GeneralResponse<ScaStatusResponse> updateConsentsPsuData(String s, String s1, RequestHeaders requestHeaders, TransactionAuthorisation transactionAuthorisation) {
        ResponseEntity<Object> responseEntity = client.updateConsentsPsuData(s, s1, requestHeaders.toMap(), objectMapper.valueToTree(transactionAuthorisation));
        return new GeneralResponse<>(responseEntity.getStatusCodeValue(), (ScaStatusResponse) responseEntity.getBody(), getHeaders(responseEntity.getHeaders()));
    }

    @Override
    public GeneralResponse<UpdatePsuAuthenticationResponse> updateConsentsPsuData(String s, String s1, RequestHeaders requestHeaders, UpdatePsuAuthentication updatePsuAuthentication) {
        ResponseEntity<Object> responseEntity = client.updateConsentsPsuData(s, s1, requestHeaders.toMap(), objectMapper.valueToTree(updatePsuAuthentication));
        return new GeneralResponse<>(responseEntity.getStatusCodeValue(), (UpdatePsuAuthenticationResponse) responseEntity.getBody(), getHeaders(responseEntity.getHeaders()));
    }

    @Override
    public GeneralResponse<AccountListHolder> getAccountList(RequestHeaders requestHeaders, RequestParams requestParams) {
        String withBalance = requestParams.toMap().getOrDefault(RequestParams.WITH_BALANCE, Boolean.FALSE.toString());
        ResponseEntity<AccountListTO> responseEntity = client.getAccountList(Boolean.valueOf(withBalance), requestHeaders.toMap());
        AccountListHolder accountListHolder = accountListHolderMapper.toAccountListHolder(responseEntity.getBody());
        return new GeneralResponse<>(responseEntity.getStatusCodeValue(), accountListHolder, getHeaders(responseEntity.getHeaders()));
    }

    @Override
    public GeneralResponse<TransactionsReport> getTransactionList(String s, RequestHeaders requestHeaders, RequestParams requestParams) {
        Map<String, String> headersMap = requestHeaders.toMap();
        if (!requestHeaders.isAcceptJson()) {
            throw new NotAcceptableException("Unsupported accept type: " + headersMap.get(RequestHeaders.ACCEPT));
        }
        ResponseEntity<Object> responseEntity = getTransactionListFromClient(s, requestParams, headersMap);
        TransactionsReport transactionsReport = transactionsReportMapper.toTransactionsReport((TransactionsResponse200JsonTO) responseEntity.getBody());
        return new GeneralResponse<>(responseEntity.getStatusCodeValue(), transactionsReport, getHeaders(responseEntity.getHeaders()));
    }

    private ResponseEntity<Object> getTransactionListFromClient(String s, RequestParams requestParams, Map<String, String> headersMap) {
        Map<String, String> requestParamMap = requestParams.toMap();
        LocalDate dateFrom = strToLocalDate(requestParamMap.get(RequestParams.DATE_FROM));
        LocalDate dateTo = strToLocalDate(requestParamMap.get(RequestParams.DATE_TO));
        String entryReferenceFrom = requestParamMap.get(RequestParams.ENTRY_REFERENCE_FROM);
        BookingStatusTO bookingStatus = BookingStatusTO.fromValue(requestParamMap.get(RequestParams.BOOKING_STATUS));
        Boolean deltaList = Boolean.valueOf(requestParamMap.get(RequestParams.DELTA_LIST));
        Boolean withBalance = Boolean.valueOf(requestParamMap.get(RequestParams.WITH_BALANCE));
        return client.getTransactionList(s, dateFrom, dateTo, entryReferenceFrom, bookingStatus, deltaList, withBalance, headersMap);
    }

    @Override
    public GeneralResponse<String> getTransactionListAsString(String s, RequestHeaders requestHeaders, RequestParams requestParams) {
        Map<String, String> headersMap = requestHeaders.toMap();
        ResponseEntity<Object> responseEntity = getTransactionListFromClient(s, requestParams, headersMap);

        return new GeneralResponse<>(responseEntity.getStatusCodeValue(), (String) responseEntity.getBody(), getHeaders(responseEntity.getHeaders()));
    }

    @Override
    public GeneralResponse<ScaStatusResponse> getConsentScaStatus(String s, String s1, RequestHeaders requestHeaders) {
        ResponseEntity<ScaStatusResponseTO> responseEntity = client.getConsentScaStatus(s, s1, requestHeaders.toMap());
        ScaStatusResponse scaStatusResponse = scaStatusResponseMapper.toScaStatusResponse(responseEntity.getBody());
        return new GeneralResponse<>(responseEntity.getStatusCodeValue(), scaStatusResponse, getHeaders(responseEntity.getHeaders()));
    }

    @Override
    public GeneralResponse<BalanceReport> getBalances(String s, RequestHeaders requestHeaders) {
        ResponseEntity<ReadAccountBalanceResponse200TO> responseEntity = client.getBalances(s, requestHeaders.toMap());
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
