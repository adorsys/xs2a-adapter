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

package de.adorsys.xs2a.adapter.rest.impl.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import de.adorsys.xs2a.adapter.api.AccountInformationService;
import de.adorsys.xs2a.adapter.api.RequestHeaders;
import de.adorsys.xs2a.adapter.api.RequestParams;
import de.adorsys.xs2a.adapter.api.Response;
import de.adorsys.xs2a.adapter.api.exception.ErrorResponseException;
import de.adorsys.xs2a.adapter.api.model.*;
import de.adorsys.xs2a.adapter.mapper.HeadersMapper;
import de.adorsys.xs2a.adapter.rest.api.AccountApi;
import de.adorsys.xs2a.adapter.rest.api.ConsentApi;
import de.adorsys.xs2a.adapter.rest.api.Oauth2Api;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Map;

import static java.util.Collections.singletonMap;

@RestController
public class ConsentController extends AbstractController implements ConsentApi, AccountApi {
    public static final String CONSENTS = "/v1/consents";

    private final AccountInformationService accountInformationService;
    private final HeadersMapper headersMapper;

    public ConsentController(AccountInformationService accountInformationService, ObjectMapper objectMapper, HeadersMapper headersMapper) {
        super(objectMapper);
        this.accountInformationService = accountInformationService;
        this.headersMapper = headersMapper;
    }

    @Override
    public ResponseEntity<ConsentsResponse201> createConsent(Map<String, String> parameters,
                                                               Map<String, String> headers,
                                                               Consents body) {
        RequestHeaders requestHeaders = RequestHeaders.fromMap(headers);
        RequestParams requestParams = RequestParams.fromMap(parameters);

        Response<ConsentsResponse201> response;
        try {
            response = accountInformationService.createConsent(requestHeaders,
                requestParams,
                body);
        } catch (ErrorResponseException e) {
            if (e.getStatusCode() == 403 && e.getMessage() != null && e.getMessage().contains("TOKEN_INVALID")) {
                ConsentsResponse201 consentsResponse = new ConsentsResponse201();
                HrefType preOauthHref = new HrefType();
                preOauthHref.setHref(Oauth2Api.AUTHORIZATION_REQUEST_URI);
                Map<String, HrefType> preOauth = singletonMap("preOauth", preOauthHref);
                consentsResponse.setLinks(preOauth);
                return ResponseEntity.ok(consentsResponse);
            }
            throw e;
        }

        ConsentsResponse201 consentsResponse = response.getBody();
        if (consentsResponse.getConsentId() == null && consentsResponse.getLinks() == null) {
            HrefType oauthConsentHref = new HrefType();
            oauthConsentHref.setHref(Oauth2Api.AUTHORIZATION_REQUEST_URI);
            consentsResponse.setLinks(
                singletonMap("oauthConsent", oauthConsentHref)
            );
        }

        return ResponseEntity.status(HttpStatus.CREATED)
            .headers(headersMapper.toHttpHeaders(response.getHeaders()))
            .body(consentsResponse);
    }

    @Override
    public ResponseEntity<ConsentInformationResponse200Json> getConsentInformation(String consentId,
                                                                                   Map<String, String> parameters,
                                                                                   Map<String, String> headers) {
        RequestParams requestParams = RequestParams.fromMap(parameters);
        RequestHeaders requestHeaders = RequestHeaders.fromMap(headers);

        Response<ConsentInformationResponse200Json> response =
            accountInformationService.getConsentInformation(consentId, requestHeaders, requestParams);

        return ResponseEntity
                       .status(HttpStatus.OK)
                       .headers(headersMapper.toHttpHeaders(response.getHeaders()))
                       .body(response.getBody());
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
    public ResponseEntity<ConsentStatusResponse200> getConsentStatus(String consentId,
                                                                     Map<String, String> parameters,
                                                                     Map<String, String> headers) {
        RequestHeaders requestHeaders = RequestHeaders.fromMap(headers);
        RequestParams requestParams = RequestParams.fromMap(parameters);

        Response<ConsentStatusResponse200> response =
            accountInformationService.getConsentStatus(consentId, requestHeaders, requestParams);

        return ResponseEntity
                       .status(HttpStatus.OK)
                       .headers(headersMapper.toHttpHeaders(response.getHeaders()))
                       .body(response.getBody());
    }

    @Override
    public ResponseEntity<Authorisations> getConsentAuthorisation(String consentId,
                                                                  Map<String, String> parameters,
                                                                  Map<String, String> headers) {
        RequestHeaders requestHeaders = RequestHeaders.fromMap(headers);
        RequestParams requestParams = RequestParams.fromMap(parameters);

        Response<Authorisations> response =
            accountInformationService.getConsentAuthorisation(consentId, requestHeaders, requestParams);

        return ResponseEntity
            .status(HttpStatus.OK)
            .headers(headersMapper.toHttpHeaders(response.getHeaders()))
            .body(response.getBody());
    }

    @Override
    public ResponseEntity<StartScaprocessResponse> startConsentAuthorisation(String consentId,
                                                                             Map<String, String> parameters,
                                                                             Map<String, String> headers,
                                                                             ObjectNode body) {
        RequestHeaders requestHeaders = RequestHeaders.fromMap(headers);
        RequestParams requestParams = RequestParams.fromMap(parameters);

        Response<StartScaprocessResponse> response = handleAuthorisationBody(body,
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
                       .body(response.getBody());
    }

    @Override
    public ResponseEntity<Object> updateConsentsPsuData(String consentId,
                                                        String authorisationId,
                                                        Map<String, String> parameters,
                                                        Map<String, String> headers,
                                                        ObjectNode body) {
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
    public ResponseEntity<AccountList> getAccountList(Boolean withBalance,
                                                      Map<String, String> parameters,
                                                      Map<String, String> headers) {
        RequestHeaders requestHeaders = RequestHeaders.fromMap(headers);

        RequestParams requestParams = RequestParams.builder()
                                              .withBalance(withBalance)
                                              .build();

        Response<AccountList> response = accountInformationService.getAccountList(requestHeaders, requestParams);

        return ResponseEntity.status(HttpStatus.OK)
                       .headers(headersMapper.toHttpHeaders(response.getHeaders()))
                       .body(response.getBody());
    }

    @Override
    public ResponseEntity<OK200AccountDetails> readAccountDetails(String accountId,
                                                                 Boolean withBalance,
                                                                 Map<String, String> parameters,
                                                                 Map<String, String> headers) {
        RequestHeaders requestHeaders = RequestHeaders.fromMap(headers);

        RequestParams requestParams = RequestParams.builder()
                                              .withBalance(withBalance)
                                              .build();

        Response<OK200AccountDetails> response = accountInformationService.readAccountDetails(
            accountId, requestHeaders, requestParams);

        return ResponseEntity.status(HttpStatus.OK)
            .headers(headersMapper.toHttpHeaders(response.getHeaders()))
            .body(response.getBody());
    }

    @Override
    public ResponseEntity<Object> getTransactionList(String accountId,
                                                     LocalDate dateFrom,
                                                     LocalDate dateTo, String entryReferenceFrom,
                                                     BookingStatus bookingStatus,
                                                     Boolean deltaList,
                                                     Boolean withBalance,
                                                     Map<String, String> parameters,
                                                     Map<String, String> headers) {
        RequestHeaders requestHeaders = RequestHeaders.fromMap(headers);

        RequestParams requestParams = RequestParams.fromMap(parameters);

        if (requestHeaders.isAcceptJson()) {
            Response<TransactionsResponse200Json> transactionList =
                accountInformationService.getTransactionList(accountId, requestHeaders, requestParams);
            return ResponseEntity.status(HttpStatus.OK)
                    .headers(headersMapper.toHttpHeaders(transactionList.getHeaders()))
                    .body(transactionList.getBody());
        }

        Response<String> response = accountInformationService.getTransactionListAsString(accountId, requestHeaders, requestParams);

        return ResponseEntity
                       .status(HttpStatus.OK)
                       .headers(headersMapper.toHttpHeaders(response.getHeaders()))
                       .body(response.getBody());
    }

    @Override
    public ResponseEntity<OK200TransactionDetails> getTransactionDetails(String accountId,
                                                                         String transactionId,
                                                                         Map<String, String> parameters,
                                                                         Map<String, String> headers) {
        Response<OK200TransactionDetails> response = accountInformationService.getTransactionDetails(accountId,
            transactionId,
            RequestHeaders.fromMap(headers),
            RequestParams.fromMap(parameters));
        return ResponseEntity.status(HttpStatus.OK)
            .headers(headersMapper.toHttpHeaders(response.getHeaders()))
            .body(response.getBody());
    }

    @Override
    public ResponseEntity<CardAccountList> getCardAccount(Map<String, String> parameters, Map<String, String> headers) {
        Response<CardAccountList> response =
            accountInformationService.getCardAccountList(RequestHeaders.fromMap(headers),
                RequestParams.fromMap(parameters));
        return ResponseEntity.status(HttpStatus.OK)
            .headers(headersMapper.toHttpHeaders(response.getHeaders()))
            .body(response.getBody());
    }

    @Override
    public ResponseEntity<OK200CardAccountDetails> ReadCardAccount(String accountId,
                                                                     Map<String, String> parameters,
                                                                     Map<String, String> headers) {
        Response<OK200CardAccountDetails> response =
            accountInformationService.getCardAccountDetails(accountId,
                RequestHeaders.fromMap(headers),
                RequestParams.fromMap(parameters));
        return ResponseEntity.status(HttpStatus.OK)
            .headers(headersMapper.toHttpHeaders(response.getHeaders()))
            .body(response.getBody());
    }

    @Override
    public ResponseEntity<ReadCardAccountBalanceResponse200> getCardAccountBalances(String accountId,
                                                                                    Map<String, String> parameters,
                                                                                    Map<String, String> headers) {
        Response<ReadCardAccountBalanceResponse200> response =
            accountInformationService.getCardAccountBalances(accountId,
                RequestHeaders.fromMap(headers),
                RequestParams.fromMap(parameters));
        return ResponseEntity.status(HttpStatus.OK)
            .headers(headersMapper.toHttpHeaders(response.getHeaders()))
            .body(response.getBody());
    }

    @Override
    public ResponseEntity<CardAccountsTransactionsResponse200> getCardAccountTransactionList(String accountId,
                                                                                               LocalDate dateFrom,
                                                                                               LocalDate dateTo,
                                                                                               String entryReferenceFrom,
                                                                                               BookingStatus bookingStatus,
                                                                                               Boolean deltaList,
                                                                                               Boolean withBalance,
                                                                                               Map<String, String> parameters,
                                                                                               Map<String, String> headers) {
        Response<CardAccountsTransactionsResponse200> response =
            accountInformationService.getCardAccountTransactionList(accountId,
                RequestHeaders.fromMap(headers),
                RequestParams.fromMap(parameters));
        return ResponseEntity.status(HttpStatus.OK)
            .headers(headersMapper.toHttpHeaders(response.getHeaders()))
            .body(response.getBody());
    }

    @Override
    public ResponseEntity<ScaStatusResponse> getConsentScaStatus(String consentId,
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
                .body(response.getBody());
    }

    @Override
    public ResponseEntity<ReadAccountBalanceResponse200> getBalances(String accountId,
                                                                       Map<String, String> parameters,
                                                                       Map<String, String> headers) {
        RequestHeaders requestHeaders = RequestHeaders.fromMap(headers);
        RequestParams requestParams = RequestParams.fromMap(parameters);

        Response<ReadAccountBalanceResponse200> response =
            accountInformationService.getBalances(accountId, requestHeaders, requestParams);

        return ResponseEntity
                       .status(HttpStatus.OK)
                       .headers(headersMapper.toHttpHeaders(response.getHeaders()))
                       .body(response.getBody());
    }
}
