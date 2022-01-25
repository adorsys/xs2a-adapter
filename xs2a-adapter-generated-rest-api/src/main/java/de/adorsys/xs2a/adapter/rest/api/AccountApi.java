/*
 * Copyright 2018-2022 adorsys GmbH & Co KG
 *
 * This program is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or (at
 * your option) any later version. This program is distributed in the hope that
 * it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see https://www.gnu.org/licenses/.
 *
 * This project is also available under a separate commercial license. You can
 * contact us at psd2@adorsys.com.
 */

package de.adorsys.xs2a.adapter.rest.api;

import de.adorsys.xs2a.adapter.api.model.AccountList;
import de.adorsys.xs2a.adapter.api.model.BookingStatusCard;
import de.adorsys.xs2a.adapter.api.model.BookingStatusGeneric;
import de.adorsys.xs2a.adapter.api.model.CardAccountList;
import de.adorsys.xs2a.adapter.api.model.CardAccountsTransactionsResponse200;
import de.adorsys.xs2a.adapter.api.model.OK200AccountDetails;
import de.adorsys.xs2a.adapter.api.model.OK200CardAccountDetails;
import de.adorsys.xs2a.adapter.api.model.OK200TransactionDetails;
import de.adorsys.xs2a.adapter.api.model.ReadAccountBalanceResponse200;
import de.adorsys.xs2a.adapter.api.model.ReadCardAccountBalanceResponse200;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Generated;
import java.time.LocalDate;
import java.util.Map;

@Generated("xs2a-adapter-codegen")
public interface AccountApi {
    @RequestMapping(
        value = "/v1/accounts",
        method = RequestMethod.GET
    )
    ResponseEntity<AccountList> getAccountList(
        @RequestParam(value = "withBalance", required = false) Boolean withBalance,
        @RequestParam Map<String, String> parameters,
        @RequestHeader Map<String, String> headers);

    @RequestMapping(
        value = "/v1/accounts/{account-id}",
        method = RequestMethod.GET
    )
    ResponseEntity<OK200AccountDetails> readAccountDetails(
        @PathVariable("account-id") String accountId,
        @RequestParam(value = "withBalance", required = false) Boolean withBalance,
        @RequestParam Map<String, String> parameters,
        @RequestHeader Map<String, String> headers);

    @RequestMapping(
        value = "/v1/accounts/{account-id}/balances",
        method = RequestMethod.GET
    )
    ResponseEntity<ReadAccountBalanceResponse200> getBalances(
        @PathVariable("account-id") String accountId,
        @RequestParam Map<String, String> parameters,
        @RequestHeader Map<String, String> headers);

    @SuppressWarnings("parameternumber")
    @RequestMapping(
        value = "/v1/accounts/{account-id}/transactions",
        method = RequestMethod.GET
    )
    ResponseEntity<Object> getTransactionList(
        @PathVariable("account-id") String accountId,
        @RequestParam(value = "dateFrom", required = false) LocalDate dateFrom,
        @RequestParam(value = "dateTo", required = false) LocalDate dateTo,
        @RequestParam(value = "entryReferenceFrom", required = false) String entryReferenceFrom,
        @RequestParam(value = "bookingStatus", required = true) BookingStatusGeneric bookingStatus,
        @RequestParam(value = "deltaList", required = false) Boolean deltaList,
        @RequestParam(value = "withBalance", required = false) Boolean withBalance,
        @RequestParam(value = "pageIndex", required = false) Integer pageIndex,
        @RequestParam(value = "itemsPerPage", required = false) Integer itemsPerPage,
        @RequestParam Map<String, String> parameters,
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
    ResponseEntity<CardAccountList> getCardAccountList(
        @RequestParam Map<String, String> parameters,
        @RequestHeader Map<String, String> headers);

    @RequestMapping(
        value = "/v1/card-accounts/{account-id}",
        method = RequestMethod.GET
    )
    ResponseEntity<OK200CardAccountDetails> readCardAccountDetails(
        @PathVariable("account-id") String accountId,
        @RequestParam Map<String, String> parameters,
        @RequestHeader Map<String, String> headers);

    @RequestMapping(
        value = "/v1/card-accounts/{account-id}/balances",
        method = RequestMethod.GET
    )
    ResponseEntity<ReadCardAccountBalanceResponse200> getCardAccountBalances(
        @PathVariable("account-id") String accountId,
        @RequestParam Map<String, String> parameters,
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
        @RequestParam(value = "bookingStatus", required = true) BookingStatusCard bookingStatus,
        @RequestParam(value = "deltaList", required = false) Boolean deltaList,
        @RequestParam(value = "withBalance", required = false) Boolean withBalance,
        @RequestParam Map<String, String> parameters,
        @RequestHeader Map<String, String> headers);
}
