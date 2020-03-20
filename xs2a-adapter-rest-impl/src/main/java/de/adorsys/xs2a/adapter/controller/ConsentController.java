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
import de.adorsys.xs2a.adapter.service.AccountInformationService;
import de.adorsys.xs2a.adapter.service.RequestHeaders;
import de.adorsys.xs2a.adapter.service.RequestParams;
import de.adorsys.xs2a.adapter.service.Response;
import de.adorsys.xs2a.adapter.service.model.*;
import org.mapstruct.factory.Mappers;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Map;

@RestController
public class ConsentController extends AbstractController implements ConsentApi, AccountApi {
    public static final String CONSENTS = "/v1/consents";

    private final AccountInformationService accountInformationService;
    private final HeadersMapper headersMapper;

    private final ConsentMapper consentMapper = Mappers.getMapper(ConsentMapper.class);
    private final ConsentCreationResponseMapper creationResponseMapper = Mappers.getMapper(ConsentCreationResponseMapper.class);
    private final ConsentInformationMapper consentInformationMapper = Mappers.getMapper(ConsentInformationMapper.class);
    private final ConsentStatusResponseMapper consentStatusResponseMapper = Mappers.getMapper(ConsentStatusResponseMapper.class);
    private final AccountListHolderMapper accountListHolderMapper = Mappers.getMapper(AccountListHolderMapper.class);
    private final ScaStatusResponseMapper scaStatusResponseMapper = Mappers.getMapper(ScaStatusResponseMapper.class);
    private final BalanceReportMapper balanceReportMapper = Mappers.getMapper(BalanceReportMapper.class);
    private final TransactionsReportMapper transactionsReportMapper = Mappers.getMapper(TransactionsReportMapper.class);
    private final TransactionDetailsMapper transactionDetailsMapper = Mappers.getMapper(TransactionDetailsMapper.class);
    private final CardAccountListMapper cardAccountListMapper = Mappers.getMapper(CardAccountListMapper.class);
    private final CardAccountDetailsHolderMapper cardAccountDetailsHolderMapper =
        Mappers.getMapper(CardAccountDetailsHolderMapper.class);
    private final CardAccountBalanceReportMapper cardAccountBalanceReportMapper =
        Mappers.getMapper(CardAccountBalanceReportMapper.class);
    private CardAccountsTransactionsMapper cardAccountsTransactionsMapper =
        Mappers.getMapper(CardAccountsTransactionsMapper.class);

    public ConsentController(AccountInformationService accountInformationService, ObjectMapper objectMapper, HeadersMapper headersMapper) {
        super(objectMapper);
        this.accountInformationService = accountInformationService;
        this.headersMapper = headersMapper;
    }

    @Override
    public ResponseEntity<ConsentsResponse201TO> createConsent(Map<String, String> parameters,
                                                               Map<String, String> headers,
                                                               ConsentsTO body) {
        RequestHeaders requestHeaders = RequestHeaders.fromMap(headers);
        RequestParams requestParams = RequestParams.fromMap(parameters);
        Consents consents = consentMapper.toConsents(body);

        Response<ConsentCreationResponse> response = accountInformationService.createConsent(requestHeaders,
            requestParams,
            consents);

        return ResponseEntity
                       .status(HttpStatus.CREATED)
                       .headers(headersMapper.toHttpHeaders(response.getHeaders()))
                       .body(creationResponseMapper.toConsentResponse201(response.getBody()));
    }

    @Override
    public ResponseEntity<ConsentInformationResponse200JsonTO> getConsentInformation(String consentId,
                                                                                     Map<String, String> parameters,
                                                                                     Map<String, String> headers) {
        RequestParams requestParams = RequestParams.fromMap(parameters);
        RequestHeaders requestHeaders = RequestHeaders.fromMap(headers);

        Response<ConsentInformation> response =
            accountInformationService.getConsentInformation(consentId, requestHeaders, requestParams);

        return ResponseEntity
                       .status(HttpStatus.OK)
                       .headers(headersMapper.toHttpHeaders(response.getHeaders()))
                       .body(consentInformationMapper.toConsentInformationResponse200Json(response.getBody()));
    }

    @Override
    public ResponseEntity<Void> deleteConsent(String consentId,
                                              Map<String, String> parameters,
                                              Map<String, String> headers) {
        RequestHeaders requestHeaders = RequestHeaders.fromMap(headers);
        RequestParams requestParams = RequestParams.fromMap(parameters);

        Response<Void> response = accountInformationService.deleteConsent(consentId, requestHeaders, requestParams);

        return new ResponseEntity<>(headersMapper.toHttpHeaders(response.getHeaders()), HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<ConsentStatusResponse200TO> getConsentStatus(String consentId,
                                                                       Map<String, String> parameters,
                                                                       Map<String, String> headers) {
        RequestHeaders requestHeaders = RequestHeaders.fromMap(headers);
        RequestParams requestParams = RequestParams.fromMap(parameters);

        Response<ConsentStatusResponse> response =
            accountInformationService.getConsentStatus(consentId, requestHeaders, requestParams);

        return ResponseEntity
                       .status(HttpStatus.OK)
                       .headers(headersMapper.toHttpHeaders(response.getHeaders()))
                       .body(consentStatusResponseMapper.toConsentStatusResponse200(response.getBody()));
    }

    @Override
    public ResponseEntity<StartScaprocessResponseTO> startConsentAuthorisation(String consentId,
                                                                               Map<String, String> parameters,
                                                                               Map<String, String> headers,
                                                                               ObjectNode body) {
        RequestHeaders requestHeaders = RequestHeaders.fromMap(headers);
        RequestParams requestParams = RequestParams.fromMap(parameters);

        Response<?> response = handleAuthorisationBody(body,
                (UpdatePsuAuthenticationHandler) updatePsuAuthentication ->
                    accountInformationService.startConsentAuthorisation(consentId,
                        requestHeaders,
                        requestParams,
                        updatePsuAuthentication),
                (StartAuthorisationHandler) emptyAuthorisationBody ->
                    accountInformationService.startConsentAuthorisation(consentId, requestHeaders, requestParams));

        return ResponseEntity
                       .status(HttpStatus.CREATED)
                       .headers(headersMapper.toHttpHeaders(response.getHeaders()))
                       .body(startScaProcessResponseMapper.toStartScaprocessResponseTO((StartScaProcessResponse) response.getBody()));
    }

    @Override
    public ResponseEntity<Object> updateConsentsPsuData(String consentId,
                                                        String authorisationId,
                                                        Map<String, String> parameters,
                                                        Map<String, String> headers,
                                                        ObjectNode body) {
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
        RequestParams requestParams = RequestParams.fromMap(parameters);

        Response<?> response = handleAuthorisationBody(body,
                (UpdatePsuAuthenticationHandler) updatePsuAuthentication ->
                    accountInformationService.updateConsentsPsuData(consentId,
                        authorisationId,
                        requestHeaders,
                        requestParams,
                        updatePsuAuthentication),
                (SelectPsuAuthenticationMethodHandler) selectPsuAuthenticationMethod ->
                    accountInformationService.updateConsentsPsuData(consentId,
                        authorisationId,
                        requestHeaders,
                        requestParams,
                        selectPsuAuthenticationMethod),
                (TransactionAuthorisationHandler) transactionAuthorisation ->
                    accountInformationService.updateConsentsPsuData(consentId,
                        authorisationId,
                        requestHeaders,
                        requestParams,
                        transactionAuthorisation)
        );

        return ResponseEntity
                .status(HttpStatus.OK)
                .headers(headersMapper.toHttpHeaders(response.getHeaders()))
                .body(response.getBody());
    }

    @Override
    public ResponseEntity<AccountListTO> getAccountList(Boolean withBalance,
                                                        Map<String, String> parameters,
                                                        Map<String, String> headers) {
        RequestHeaders requestHeaders = RequestHeaders.fromMap(headers);

        RequestParams requestParams = RequestParams.builder()
                                              .withBalance(withBalance)
                                              .build();

        Response<AccountListHolder> response = accountInformationService.getAccountList(requestHeaders, requestParams);

        return ResponseEntity.status(HttpStatus.OK)
                       .headers(headersMapper.toHttpHeaders(response.getHeaders()))
                       .body(accountListHolderMapper.toAccountListTO(response.getBody()));
    }

    @Override
    public ResponseEntity<Object> getTransactionList(String accountId,
                                                     LocalDate dateFrom,
                                                     LocalDate dateTo, String entryReferenceFrom,
                                                     BookingStatusTO bookingStatus,
                                                     Boolean deltaList,
                                                     Boolean withBalance,
                                                     Map<String, String> parameters,
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
            Response<TransactionsReport> transactionList = accountInformationService.getTransactionList(accountId, requestHeaders, requestParams);
            return ResponseEntity.status(HttpStatus.OK)
                    .headers(headersMapper.toHttpHeaders(transactionList.getHeaders()))
                    .body(transactionsReportMapper.toTransactionsResponse200Json(transactionList.getBody()));
        }

        Response<String> response = accountInformationService.getTransactionListAsString(accountId, requestHeaders, requestParams);

        return ResponseEntity
                       .status(HttpStatus.OK)
                       .headers(headersMapper.toHttpHeaders(response.getHeaders()))
                       .body(response.getBody());
    }

    @Override
    public ResponseEntity<OK200TransactionDetailsTO> getTransactionDetails(String accountId,
                                                                           String transactionId,
                                                                           Map<String, String> parameters,
                                                                           Map<String, String> headers) {
        Response<TransactionDetails> response = accountInformationService.getTransactionDetails(accountId,
            transactionId,
            RequestHeaders.fromMap(headers),
            RequestParams.fromMap(parameters));
        return ResponseEntity.status(HttpStatus.OK)
            .headers(headersMapper.toHttpHeaders(response.getHeaders()))
            .body(transactionDetailsMapper.map(response.getBody()));
    }

    @Override
    public ResponseEntity<CardAccountListTO> getCardAccount(Map<String, String> parameters, Map<String, String> headers) {
        Response<CardAccountList> response =
            accountInformationService.getCardAccountList(RequestHeaders.fromMap(headers),
                RequestParams.fromMap(parameters));
        return ResponseEntity.status(HttpStatus.OK)
            .headers(headersMapper.toHttpHeaders(response.getHeaders()))
            .body(cardAccountListMapper.map(response.getBody()));
    }

    @Override
    public ResponseEntity<OK200CardAccountDetailsTO> ReadCardAccount(String accountId,
                                                                     Map<String, String> parameters,
                                                                     Map<String, String> headers) {
        Response<CardAccountDetailsHolder> response =
            accountInformationService.getCardAccountDetails(accountId,
                RequestHeaders.fromMap(headers),
                RequestParams.fromMap(parameters));
        return ResponseEntity.status(HttpStatus.OK)
            .headers(headersMapper.toHttpHeaders(response.getHeaders()))
            .body(cardAccountDetailsHolderMapper.map(response.getBody()));
    }

    @Override
    public ResponseEntity<ReadCardAccountBalanceResponse200TO> getCardAccountBalances(String accountId,
                                                                                      Map<String, String> parameters,
                                                                                      Map<String, String> headers) {
        Response<CardAccountBalanceReport> response =
            accountInformationService.getCardAccountBalances(accountId,
                RequestHeaders.fromMap(headers),
                RequestParams.fromMap(parameters));
        return ResponseEntity.status(HttpStatus.OK)
            .headers(headersMapper.toHttpHeaders(response.getHeaders()))
            .body(cardAccountBalanceReportMapper.map(response.getBody()));
    }

    @Override
    public ResponseEntity<CardAccountsTransactionsResponse200TO> getCardAccountTransactionList(String accountId,
                                                                                               LocalDate dateFrom,
                                                                                               LocalDate dateTo,
                                                                                               String entryReferenceFrom,
                                                                                               BookingStatusTO bookingStatus,
                                                                                               Boolean deltaList,
                                                                                               Boolean withBalance,
                                                                                               Map<String, String> parameters,
                                                                                               Map<String, String> headers) {
        Response<CardAccountsTransactions> response =
            accountInformationService.getCardAccountTransactionList(accountId,
                RequestHeaders.fromMap(headers),
                RequestParams.fromMap(parameters));
        return ResponseEntity.status(HttpStatus.OK)
            .headers(headersMapper.toHttpHeaders(response.getHeaders()))
            .body(cardAccountsTransactionsMapper.map(response.getBody()));
    }

    @Override
    public ResponseEntity<ScaStatusResponseTO> getConsentScaStatus(String consentId,
                                                                   String authorisationId,
                                                                   Map<String, String> parameters,
                                                                   Map<String, String> headers) {
        RequestHeaders requestHeaders = RequestHeaders.fromMap(headers);
        RequestParams requestParams = RequestParams.fromMap(parameters);

        Response<ScaStatusResponse> response = accountInformationService.getConsentScaStatus(consentId,
            authorisationId,
            requestHeaders,
            requestParams);

        return ResponseEntity
                .status(HttpStatus.OK)
                .headers(headersMapper.toHttpHeaders(response.getHeaders()))
                .body(scaStatusResponseMapper.toScaStatusResponseTO(response.getBody()));
    }

    @Override
    public ResponseEntity<ReadAccountBalanceResponse200TO> getBalances(String accountId,
                                                                       Map<String, String> parameters,
                                                                       Map<String, String> headers) {
        RequestHeaders requestHeaders = RequestHeaders.fromMap(headers);
        RequestParams requestParams = RequestParams.fromMap(parameters);

        Response<BalanceReport> response =
            accountInformationService.getBalances(accountId, requestHeaders, requestParams);

        return ResponseEntity
                       .status(HttpStatus.OK)
                       .headers(headersMapper.toHttpHeaders(response.getHeaders()))
                       .body(balanceReportMapper.toReadAccountBalanceResponse200TO(response.getBody()));
    }
}
