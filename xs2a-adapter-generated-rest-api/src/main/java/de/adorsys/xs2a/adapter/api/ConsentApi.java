package de.adorsys.xs2a.adapter.api;

import com.fasterxml.jackson.databind.node.ObjectNode;
import de.adorsys.xs2a.adapter.model.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Generated;
import java.util.Map;

@Generated("xs2a-codegen")
public interface ConsentApi {
    @RequestMapping(
        value = "/v1/consents",
        method = RequestMethod.POST,
        consumes = "application/json"
    )
    ResponseEntity<ConsentsResponse201TO> createConsent(@RequestHeader Map<String, String> headers,
                                                        @RequestBody ConsentsTO body);

    @RequestMapping(
        value = "/v1/consents/{consentId}",
        method = RequestMethod.GET
    )
    ResponseEntity<ConsentInformationResponse200JsonTO> getConsentInformation(
        @PathVariable("consentId") String consentId, @RequestHeader Map<String, String> headers);

    @RequestMapping(
        value = "/v1/consents/{consentId}",
        method = RequestMethod.DELETE
    )
    ResponseEntity<Void> deleteConsent(@PathVariable("consentId") String consentId,
                                       @RequestHeader Map<String, String> headers);

    @RequestMapping(
        value = "/v1/consents/{consentId}/status",
        method = RequestMethod.GET
    )
    ResponseEntity<ConsentStatusResponse200TO> getConsentStatus(
        @PathVariable("consentId") String consentId, @RequestHeader Map<String, String> headers);

    @RequestMapping(
        value = "/v1/consents/{consentId}/authorisations",
        method = RequestMethod.POST,
        consumes = "application/json"
    )
    ResponseEntity<StartScaprocessResponseTO> startConsentAuthorisation(
        @PathVariable("consentId") String consentId, @RequestHeader Map<String, String> headers,
        @RequestBody ObjectNode body);

    @RequestMapping(
        value = "/v1/consents/{consentId}/authorisations/{authorisationId}",
        method = RequestMethod.GET
    )
    ResponseEntity<ScaStatusResponseTO> getConsentScaStatus(
        @PathVariable("consentId") String consentId,
        @PathVariable("authorisationId") String authorisationId,
        @RequestHeader Map<String, String> headers);

    @RequestMapping(
        value = "/v1/consents/{consentId}/authorisations/{authorisationId}",
        method = RequestMethod.PUT,
        consumes = "application/json"
    )
    ResponseEntity<Object> updateConsentsPsuData(@PathVariable("consentId") String consentId,
                                                 @PathVariable("authorisationId") String authorisationId,
                                                 @RequestHeader Map<String, String> headers, @RequestBody ObjectNode body,
                                                 @RequestParam(required = false) final Map<String, String> params);
}
