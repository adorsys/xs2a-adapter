package de.adorsys.xs2a.gateway.api;

import de.adorsys.xs2a.gateway.model.AccountListTO;
import de.adorsys.xs2a.gateway.model.BookingStatusTO;
import de.adorsys.xs2a.gateway.model.ReadAccountBalanceResponse200TO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Generated;
import java.time.LocalDate;
import java.util.Map;

@Generated("xs2a-gateway-codegen")
public interface AccountApi {
  @RequestMapping(
      value = "/v1/accounts",
      method = RequestMethod.GET
  )
  ResponseEntity<AccountListTO> getAccountList(
          @RequestParam(value = "withBalance", required = false) Boolean withBalance,
          @RequestHeader Map<String, String> headers);

  @RequestMapping(
      value = "/v1/accounts/{account-id}/balances",
      method = RequestMethod.GET
  )
  ResponseEntity<ReadAccountBalanceResponse200TO> getBalances(
          @PathVariable("account-id") String accountId, @RequestHeader Map<String, String> headers);

  @RequestMapping(
      value = "/v1/accounts/{account-id}/transactions",
      method = RequestMethod.GET
  )
  ResponseEntity<Object> getTransactionList(@PathVariable("account-id") String accountId,
                                            @RequestParam(value = "dateFrom", required = false) LocalDate dateFrom,
                                            @RequestParam(value = "dateTo", required = false) LocalDate dateTo,
                                            @RequestParam(value = "entryReferenceFrom", required = false)
                                                    String entryReferenceFrom,
                                            @RequestParam(value = "bookingStatus", required = true)
                                                    BookingStatusTO bookingStatus,
                                            @RequestParam(value = "deltaList", required = false) Boolean deltaList,
                                            @RequestParam(value = "withBalance", required = false) Boolean withBalance,
                                            @RequestHeader Map<String, String> headers);
}
