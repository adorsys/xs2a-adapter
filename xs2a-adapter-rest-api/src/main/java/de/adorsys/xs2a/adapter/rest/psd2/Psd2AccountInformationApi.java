package de.adorsys.xs2a.adapter.rest.psd2;

import de.adorsys.xs2a.adapter.rest.psd2.model.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

/**
 * @deprecated
 * This api is no longer acceptable and will be removed in future releases.
 * <p>Use {@link de.adorsys.xs2a.adapter.api.AccountApi} instead.</p>
 */
@Deprecated
public interface Psd2AccountInformationApi {
    @RequestMapping(
        value = "/accounts",
        method = RequestMethod.GET
    )
    ResponseEntity<AccountListTO> getAccountList(@RequestParam Map<String, String> queryParameters,
                                                 @RequestHeader Map<String, String> headers) throws IOException;

    @RequestMapping(
        value = "/accounts/{account-id}/balances",
        method = RequestMethod.GET
    )
    ResponseEntity<ReadAccountBalanceResponseTO> getBalances(@PathVariable("account-id") String accountId,
                                                             @RequestParam Map<String, String> queryParameters,
                                                             @RequestHeader
                                                                 Map<String, String> headers) throws IOException;

    @RequestMapping(
        value = "/accounts/{account-id}/transactions",
        method = RequestMethod.GET
    )
    ResponseEntity getTransactionList(@PathVariable("account-id") String accountId,
                                         @RequestParam Map<String, String> queryParameters,
                                         @RequestHeader Map<String, String> headers) throws IOException;

    @RequestMapping(
        value = "/consents",
        method = RequestMethod.POST,
        consumes = "application/json"
    )
    ResponseEntity<ConsentsResponseTO> createConsent(@RequestParam Map<String, String> queryParameters,
                                                     @RequestHeader Map<String, String> headers,
                                                     @RequestBody ConsentsTO body);

    @RequestMapping(
        value = "/consents/{consentId}",
        method = RequestMethod.GET
    )
    ResponseEntity<ConsentInformationResponseTO> getConsentInformation(
        @PathVariable("consentId") String consentId,
        @RequestParam Map<String, String> queryParameters,
        @RequestHeader Map<String, String> headers);

    @RequestMapping(
        value = "/consents/{consentId}",
        method = RequestMethod.DELETE
    )
    ResponseEntity<Void> deleteConsent(@PathVariable("consentId") String consentId,
                                       @RequestParam Map<String, String> queryParameters,
                                       @RequestHeader Map<String, String> headers);

    @RequestMapping(
        value = "/consents/{consentId}/status",
        method = RequestMethod.GET
    )
    ResponseEntity<ConsentStatusResponseTO> getConsentStatus(
        @PathVariable("consentId") String consentId, @RequestHeader Map<String, String> headers);

    @RequestMapping(
        value = "/consents/{consentId}/authorisations",
        method = RequestMethod.POST,
        consumes = "application/json"
    )
    ResponseEntity<StartScaProcessResponseTO> startConsentAuthorisation(
        @PathVariable("consentId") String consentId, @RequestHeader Map<String, String> headers,
        @RequestBody UpdateAuthorisationTO body);

    @RequestMapping(
        value = "/consents/{consentId}/authorisations/{authorisationId}",
        method = RequestMethod.GET
    )
    ResponseEntity<ScaStatusResponseTO> getConsentScaStatus(
        @PathVariable("consentId") String consentId,
        @PathVariable("authorisationId") String authorisationId,
        @RequestHeader Map<String, String> headers);

    @RequestMapping(
        value = "/consents/{consentId}/authorisations/{authorisationId}",
        method = RequestMethod.PUT,
        consumes = "application/json"
    )
    ResponseEntity<UpdateAuthorisationResponseTO> updateConsentsPsuData(@PathVariable("consentId") String consentId,
                                                                        @PathVariable("authorisationId")
                                                                            String authorisationId,
                                                                        @RequestHeader Map<String, String> headers,
                                                                        @RequestBody UpdateAuthorisationTO body);
}
