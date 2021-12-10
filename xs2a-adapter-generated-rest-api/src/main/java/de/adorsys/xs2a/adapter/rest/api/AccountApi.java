package de.adorsys.xs2a.adapter.rest.api;

import de.adorsys.xs2a.adapter.api.model.AccountList;
import de.adorsys.xs2a.adapter.api.model.BookingStatus;
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
        @RequestParam Map<String, String> parameters, @RequestHeader Map<String, String> headers);

    @RequestMapping(
        value = "/v1/accounts/{account-id}",
        method = RequestMethod.GET
    )
    ResponseEntity<OK200AccountDetails> readAccountDetails(
        @PathVariable("account-id") String accountId,
        @RequestParam(value = "withBalance", required = false) Boolean withBalance,
        @RequestParam Map<String, String> parameters, @RequestHeader Map<String, String> headers);

    @RequestMapping(
        value = "/v1/accounts/{account-id}/balances",
        method = RequestMethod.GET
    )
    ResponseEntity<ReadAccountBalanceResponse200> getBalances(
        @PathVariable("account-id") String accountId, @RequestParam Map<String, String> parameters,
        @RequestHeader Map<String, String> headers);

    @RequestMapping(
        value = "/v1/accounts/{account-id}/transactions",
        method = RequestMethod.GET
    )
    ResponseEntity<Object> getTransactionList(@PathVariable("account-id") String accountId,
                                              @RequestParam(value = "dateFrom", required = false) LocalDate dateFrom,
                                              @RequestParam(value = "dateTo", required = false) LocalDate dateTo,
                                              @RequestParam(value = "entryReferenceFrom", required = false) String entryReferenceFrom,
                                              @RequestParam(value = "bookingStatus", required = true) BookingStatus bookingStatus,
                                              @RequestParam(value = "deltaList", required = false) Boolean deltaList,
                                              @RequestParam(value = "withBalance", required = false) Boolean withBalance,
                                              @RequestParam Map<String, String> parameters, @RequestHeader Map<String, String> headers);

    @RequestMapping(
        value = "/v1/accounts/{account-id}/transactions/{transactionId}",
        method = RequestMethod.GET
    )
    ResponseEntity<OK200TransactionDetails> getTransactionDetails(
        @PathVariable("account-id") String accountId,
        @PathVariable("transactionId") String transactionId,
        @RequestParam Map<String, String> parameters, @RequestHeader Map<String, String> headers);

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
