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

package de.adorsys.xs2a.adapter.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import de.adorsys.xs2a.adapter.api.AccountApi;
import de.adorsys.xs2a.adapter.api.ConsentApi;
import de.adorsys.xs2a.adapter.mapper.*;
import de.adorsys.xs2a.adapter.model.*;
import de.adorsys.xs2a.adapter.service.GeneralResponse;
import de.adorsys.xs2a.adapter.service.RequestHeaders;
import de.adorsys.xs2a.adapter.service.RequestParams;
import de.adorsys.xs2a.adapter.service.StartScaProcessResponse;
import de.adorsys.xs2a.adapter.service.account.AccountListHolder;
import de.adorsys.xs2a.adapter.service.account.BalanceReport;
import de.adorsys.xs2a.adapter.service.account.TransactionsReport;
import de.adorsys.xs2a.adapter.service.ais.*;
import de.adorsys.xs2a.adapter.service.model.ScaStatusResponse;
import org.mapstruct.factory.Mappers;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Map;

@RestController
public class ConsentController extends AbstractController implements ConsentApi, AccountApi {
    public static String CONSENTS = "/v1/consents";

    private final AccountInformationService accountInformationService;
    private final HeadersMapper headersMapper;

    private final ConsentMapper consentMapper = Mappers.getMapper(ConsentMapper.class);
    private final ConsentCreationResponseMapper creationResponseMapper = Mappers.getMapper(ConsentCreationResponseMapper.class);
    private final ConsentInformationMapper consentInformationMapper = Mappers.getMapper(ConsentInformationMapper.class);
    private final ConsentStatusResponseMapper consentStatusResponseMapper = Mappers.getMapper(ConsentStatusResponseMapper.class);
    private final AccountListHolderMapper accountListHolderMapper = Mappers.getMapper(AccountListHolderMapper.class);
    private final ScaStatusResponseMapper scaStatusResponseMapper = Mappers.getMapper(ScaStatusResponseMapper.class);
    private final BalanceReportMapper balanceReportMapper = Mappers.getMapper(BalanceReportMapper.class);

    public ConsentController(AccountInformationService accountInformationService, ObjectMapper objectMapper, HeadersMapper headersMapper) {
        super(objectMapper);
        this.accountInformationService = accountInformationService;
        this.headersMapper = headersMapper;
    }

    @Override
    public ResponseEntity<ConsentsResponse201TO> createConsent(Map<String, String> headers, ConsentsTO body) {
        RequestHeaders requestHeaders = RequestHeaders.fromMap(headers);
        Consents consents = consentMapper.toConsents(body);

        GeneralResponse<ConsentCreationResponse> response = accountInformationService.createConsent(requestHeaders, consents);

        return ResponseEntity
                       .status(HttpStatus.CREATED)
                       .headers(headersMapper.toHttpHeaders(response.getResponseHeaders()))
                       .body(creationResponseMapper.toConsentResponse201(response.getResponseBody()));
    }

    @Override
    public ResponseEntity<ConsentInformationResponse200JsonTO> getConsentInformation(String consentId, Map<String, String> headers) {
        RequestHeaders requestHeaders = RequestHeaders.fromMap(headers);

        GeneralResponse<ConsentInformation> response = accountInformationService.getConsentInformation(consentId, requestHeaders);

        return ResponseEntity
                       .status(HttpStatus.OK)
                       .headers(headersMapper.toHttpHeaders(response.getResponseHeaders()))
                       .body(consentInformationMapper.toConsentInformationResponse200Json(response.getResponseBody()));
    }

    @Override
    public ResponseEntity<ConsentStatusResponse200TO> getConsentStatus(String consentId, Map<String, String> headers) {
        RequestHeaders requestHeaders = RequestHeaders.fromMap(headers);

        GeneralResponse<ConsentStatusResponse> response = accountInformationService.getConsentStatus(consentId, requestHeaders);

        return ResponseEntity
                       .status(HttpStatus.OK)
                       .headers(headersMapper.toHttpHeaders(response.getResponseHeaders()))
                       .body(consentStatusResponseMapper.toConsentStatusResponse200(response.getResponseBody()));
    }

    @Override
    public ResponseEntity<StartScaprocessResponseTO> startConsentAuthorisation(String consentId, Map<String, String> headers, ObjectNode body) {
        RequestHeaders requestHeaders = RequestHeaders.fromMap(headers);

        GeneralResponse<?> response = handleAuthorisationBody(body,
                (UpdatePsuAuthenticationHandler) updatePsuAuthentication -> accountInformationService.startConsentAuthorisation(consentId, requestHeaders, updatePsuAuthentication),
                (StartAuthorisationHandler) emptyAuthorisationBody -> accountInformationService.startConsentAuthorisation(consentId, requestHeaders));

        return ResponseEntity
                       .status(HttpStatus.CREATED)
                       .headers(headersMapper.toHttpHeaders(response.getResponseHeaders()))
                       .body(startScaProcessResponseMapper.toStartScaprocessResponseTO((StartScaProcessResponse) response.getResponseBody()));
    }

    @Override
    public ResponseEntity<Object> updateConsentsPsuData(String consentId, String authorisationId, Map<String, String> headers, ObjectNode body) {
//        oneOf: #Different Authorisation Bodies
//                - {}
//                - $ref: "#/components/schemas/updatePsuAuthentication"
//                - $ref: "#/components/schemas/selectPsuAuthenticationMethod"
//                - $ref: "#/components/schemas/transactionAuthorisation"
//        oneOf: #Different Authorisation Bodies
//              - $ref: "#/components/schemas/updatePsuIdenticationResponse" #Update PSU Identification
//              - $ref: "#/components/schemas/updatePsuAuthenticationResponse" #Update PSU Authentication
//              - $ref: "#/components/schemas/selectPsuAuthenticationMethodResponse" #Select Authentication Method
//              - $ref: "#/components/schemas/scaStatusResponse" #Transaction Authorisation
        RequestHeaders requestHeaders = RequestHeaders.fromMap(headers);

        GeneralResponse<?> response = handleAuthorisationBody(body,
                (UpdatePsuAuthenticationHandler) updatePsuAuthentication -> accountInformationService.updateConsentsPsuData(consentId, authorisationId, requestHeaders, updatePsuAuthentication),
                (SelectPsuAuthenticationMethodHandler) selectPsuAuthenticationMethod -> accountInformationService.updateConsentsPsuData(consentId, authorisationId, requestHeaders, selectPsuAuthenticationMethod),
                (TransactionAuthorisationHandler) transactionAuthorisation -> accountInformationService.updateConsentsPsuData(consentId, authorisationId, requestHeaders, transactionAuthorisation)
        );

        return ResponseEntity
                .status(HttpStatus.OK)
                .headers(headersMapper.toHttpHeaders(response.getResponseHeaders()))
                .body(response.getResponseBody());
    }

    @Override
    public ResponseEntity<AccountListTO> getAccountList(Boolean withBalance, Map<String, String> headers) {
        RequestHeaders requestHeaders = RequestHeaders.fromMap(headers);

        RequestParams requestParams = RequestParams.builder()
                                              .withBalance(withBalance)
                                              .build();

        GeneralResponse<AccountListHolder> response = accountInformationService.getAccountList(requestHeaders, requestParams);

        return ResponseEntity.status(HttpStatus.OK)
                       .headers(headersMapper.toHttpHeaders(response.getResponseHeaders()))
                       .body(accountListHolderMapper.toAccountListTO(response.getResponseBody()));
    }

    @Override
    public ResponseEntity<Object> getTransactionList(String accountId,
                                                     LocalDate dateFrom,
                                                     LocalDate dateTo, String entryReferenceFrom,
                                                     BookingStatusTO bookingStatus,
                                                     Boolean deltaList,
                                                     Boolean withBalance,
                                                     Map<String, String> headers) {
        RequestHeaders requestHeaders = RequestHeaders.fromMap(headers);

        RequestParams requestParams = RequestParams.builder()
                                              .bookingStatus(bookingStatus.toString())
                                              .dateFrom(dateFrom)
                                              .dateTo(dateTo)
                                              .entryReferenceFrom(entryReferenceFrom)
                                              .deltaList(deltaList)
                                              .withBalance(withBalance)
                                              .build();

        if (requestHeaders.isAcceptJson()) {
            GeneralResponse<TransactionsReport> transactionList = accountInformationService.getTransactionList(accountId, requestHeaders, requestParams);
            return ResponseEntity.status(HttpStatus.OK)
                    .headers(headersMapper.toHttpHeaders(transactionList.getResponseHeaders()))
                    .body(transactionList.getResponseBody());
        }

        GeneralResponse<String> response = accountInformationService.getTransactionListAsString(accountId, requestHeaders, requestParams);

        return ResponseEntity
                       .status(HttpStatus.OK)
                       .headers(headersMapper.toHttpHeaders(response.getResponseHeaders()))
                       .body(response.getResponseBody());
    }

    @Override
    public ResponseEntity<ScaStatusResponseTO> getConsentScaStatus(String consentId, String authorisationId, Map<String, String> headers) {
        RequestHeaders requestHeaders = RequestHeaders.fromMap(headers);

        GeneralResponse<ScaStatusResponse> response = accountInformationService.getConsentScaStatus(consentId, authorisationId, requestHeaders);

        return ResponseEntity
                .status(HttpStatus.OK)
                .headers(headersMapper.toHttpHeaders(response.getResponseHeaders()))
                .body(scaStatusResponseMapper.toScaStatusResponseTO(response.getResponseBody()));
    }

    @Override
    public ResponseEntity<ReadAccountBalanceResponse200TO> getBalances(String accountId, Map<String, String> headers) {
        RequestHeaders requestHeaders = RequestHeaders.fromMap(headers);

        GeneralResponse<BalanceReport> response = accountInformationService.getBalances(accountId, requestHeaders);

        return ResponseEntity
                       .status(HttpStatus.OK)
                       .headers(headersMapper.toHttpHeaders(response.getResponseHeaders()))
                       .body(balanceReportMapper.toReadAccountBalanceResponse200TO(response.getResponseBody()));
    }
}
