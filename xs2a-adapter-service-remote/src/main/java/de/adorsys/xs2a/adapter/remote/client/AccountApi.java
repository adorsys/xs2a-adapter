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

package de.adorsys.xs2a.adapter.remote.client;

import com.fasterxml.jackson.databind.node.ObjectNode;
import de.adorsys.xs2a.adapter.api.model.*;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

//todo: remove it after the task https://git.adorsys.de/xs2a-gateway/xs2a-gateway/issues/325 will be completed
interface AccountApi {
    @RequestMapping(
        value = "/v1/consents",
        method = RequestMethod.POST,
        consumes = "application/json"
    )
    ResponseEntity<ConsentsResponse201> createConsent(@RequestParam Map<String, String> parameters,
                                                        @RequestHeader Map<String, String> headers,
                                                        @RequestBody Consents body);

    @RequestMapping(
        value = "/v1/consents/{consentId}",
        method = RequestMethod.GET
    )
    ResponseEntity<ConsentInformationResponse200Json> getConsentInformation(
        @PathVariable("consentId") String consentId,
        @RequestParam Map<String, String> parameters,
        @RequestHeader Map<String, String> headers);

    @RequestMapping(
        value = "/v1/consents/{consentId}",
        method = RequestMethod.DELETE
    )
    ResponseEntity<Void> deleteConsent(@PathVariable("consentId") String consentId,
                                       @RequestParam Map<String, String> parameters,
                                       @RequestHeader Map<String, String> headers);

    @RequestMapping(
        value = "/v1/consents/{consentId}/status",
        method = RequestMethod.GET
    )
    ResponseEntity<ConsentStatusResponse200> getConsentStatus(@PathVariable("consentId") String consentId,
                                                              @RequestParam Map<String, String> parameters,
                                                              @RequestHeader Map<String, String> headers);


    @RequestMapping(
        value = "/v1/consents/{consentId}/authorisations",
        method = RequestMethod.GET
    )
    ResponseEntity<Authorisations> getConsentAuthorisation(
        @PathVariable("consentId") String consentId, @RequestParam Map<String, String> parameters,
        @RequestHeader Map<String, String> headers);

    @RequestMapping(
        value = "/v1/consents/{consentId}/authorisations",
        method = RequestMethod.POST,
        consumes = "application/json"
    )
    ResponseEntity<StartScaprocessResponse> startConsentAuthorisation(@PathVariable("consentId") String consentId,
                                                                      @RequestParam Map<String, String> parameters,
                                                                      @RequestHeader Map<String, String> headers,
                                                                      @RequestBody ObjectNode body);

    @RequestMapping(
        value = "/v1/consents/{consentId}/authorisations/{authorisationId}",
        method = RequestMethod.GET
    )
    ResponseEntity<ScaStatusResponse> getConsentScaStatus(
        @PathVariable("consentId") String consentId,
        @PathVariable("authorisationId") String authorisationId,
        @RequestParam Map<String, String> parameters,
        @RequestHeader Map<String, String> headers);

    @RequestMapping(
        value = "/v1/consents/{consentId}/authorisations/{authorisationId}",
        method = RequestMethod.PUT,
        consumes = "application/json"
    )
    ResponseEntity<Object> updateConsentsPsuData(@PathVariable("consentId") String consentId,
                                                 @PathVariable("authorisationId") String authorisationId,
                                                 @RequestParam Map<String, String> parameters,
                                                 @RequestHeader Map<String, String> headers,
                                                 @RequestBody ObjectNode body);

    @RequestMapping(
        value = "/v1/accounts",
        method = RequestMethod.GET
    )
    ResponseEntity<AccountList> getAccountList(
        @RequestParam(value = "withBalance", required = false) Boolean withBalance,
        @RequestHeader Map<String, String> headers);

    @RequestMapping(
        value = "/v1/accounts/{account-id}",
        method = RequestMethod.GET
    )
    ResponseEntity<OK200AccountDetails> getAccountDetails(
        @PathVariable("account-id") String accountId,
        @RequestParam(value = "withBalance", required = false) Boolean withBalance,
        @RequestHeader Map<String, String> headers);

    @RequestMapping(
        value = "/v1/accounts/{account-id}/balances",
        method = RequestMethod.GET
    )
    ResponseEntity<ReadAccountBalanceResponse200> getBalances(
        @PathVariable("account-id") String accountId,
        @RequestParam Map<String, String> parameters,
        @RequestHeader Map<String, String> headers);

    @RequestMapping(
        value = "/v1/accounts/{account-id}/transactions",
        method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    ResponseEntity<TransactionsResponse200Json> getTransactionList(@PathVariable("account-id") String accountId,
                                                          @RequestParam(value = "dateFrom", required = false) LocalDate dateFrom,
                                                          @RequestParam(value = "dateTo", required = false) LocalDate dateTo,
                                                          @RequestParam(value = "entryReferenceFrom", required = false)
                                                  String entryReferenceFrom,
                                                          @RequestParam(value = "bookingStatus", required = true)
                                                  BookingStatus bookingStatus,
                                                          @RequestParam(value = "deltaList", required = false) Boolean deltaList,
                                                          @RequestParam(value = "withBalance", required = false) Boolean withBalance,
                                                          @RequestHeader Map<String, String> headers);

    @RequestMapping(
        value = "/v1/accounts/{account-id}/transactions",
        method = RequestMethod.GET
    )
    ResponseEntity<String> getTransactionListAsString(@PathVariable("account-id") String accountId,
                                                      @RequestParam(value = "dateFrom", required = false) LocalDate dateFrom,
                                                      @RequestParam(value = "dateTo", required = false) LocalDate dateTo,
                                                      @RequestParam(value = "entryReferenceFrom", required = false)
                                                          String entryReferenceFrom,
                                                      @RequestParam(value = "bookingStatus", required = true)
                                                          BookingStatus bookingStatus,
                                                      @RequestParam(value = "deltaList", required = false) Boolean deltaList,
                                                      @RequestParam(value = "withBalance", required = false) Boolean withBalance,
                                                      @RequestHeader Map<String, String> headers);

    @RequestMapping(
        value = "/v1/accounts/{account-id}/transactions/{transactionId}",
        method = RequestMethod.GET
    )
    ResponseEntity<OK200TransactionDetails> getTransactionDetails(
        @PathVariable("account-id") String accountId,
        @PathVariable("transactionId") String transactionId,
        @RequestParam Map<String, String> parameters,
        @RequestHeader Map<String, String> headers);

    @RequestMapping(
        value = "/v1/card-accounts",
        method = RequestMethod.GET
    )
    ResponseEntity<CardAccountList> getCardAccount(@RequestParam Map<String, String> parameters,
                                                     @RequestHeader Map<String, String> headers);

    @RequestMapping(
        value = "/v1/card-accounts/{account-id}",
        method = RequestMethod.GET
    )
    ResponseEntity<OK200CardAccountDetails> ReadCardAccount(
        @PathVariable("account-id") String accountId, @RequestParam Map<String, String> parameters,
        @RequestHeader Map<String, String> headers);

    @RequestMapping(
        value = "/v1/card-accounts/{account-id}/balances",
        method = RequestMethod.GET
    )
    ResponseEntity<ReadCardAccountBalanceResponse200> getCardAccountBalances(
        @PathVariable("account-id") String accountId, @RequestParam Map<String, String> parameters,
        @RequestHeader Map<String, String> headers);

    @RequestMapping(
        value = "/v1/card-accounts/{account-id}/transactions",
        method = RequestMethod.GET
    )
    ResponseEntity<CardAccountsTransactionsResponse200> getCardAccountTransactionList(
        @PathVariable("account-id") String accountId,
        @RequestParam(value = "dateFrom", required = false) LocalDate dateFrom,
        @RequestParam(value = "dateTo", required = false) LocalDate dateTo,
        @RequestParam(value = "entryReferenceFrom", required = false) String entryReferenceFrom,
        @RequestParam(value = "bookingStatus", required = true) BookingStatus bookingStatus,
        @RequestParam(value = "deltaList", required = false) Boolean deltaList,
        @RequestParam(value = "withBalance", required = false) Boolean withBalance,
        @RequestParam Map<String, String> parameters, @RequestHeader Map<String, String> headers);
}
