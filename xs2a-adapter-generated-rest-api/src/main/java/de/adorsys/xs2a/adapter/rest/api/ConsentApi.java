package de.adorsys.xs2a.adapter.rest.api;

import com.fasterxml.jackson.databind.node.ObjectNode;
import de.adorsys.xs2a.adapter.api.model.Authorisations;
import de.adorsys.xs2a.adapter.api.model.ConsentInformationResponse200Json;
import de.adorsys.xs2a.adapter.api.model.ConsentStatusResponse200;
import de.adorsys.xs2a.adapter.api.model.Consents;
import de.adorsys.xs2a.adapter.api.model.ConsentsResponse201;
import de.adorsys.xs2a.adapter.api.model.ScaStatusResponse;
import de.adorsys.xs2a.adapter.api.model.StartScaprocessResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Generated;
import java.util.Map;

@Generated("xs2a-adapter-codegen")
public interface ConsentApi {
    @RequestMapping(
        value = "/v1/consents",
        method = RequestMethod.POST,
        consumes = "application/json"
    )
    ResponseEntity<ConsentsResponse201> createConsent(@RequestParam Map<String, String> parameters,
                                                      @RequestHeader Map<String, String> headers, @RequestBody Consents body);

    @RequestMapping(
        value = "/v1/consents/{consentId}",
        method = RequestMethod.GET
    )
    ResponseEntity<ConsentInformationResponse200Json> getConsentInformation(
        @PathVariable("consentId") String consentId, @RequestParam Map<String, String> parameters,
        @RequestHeader Map<String, String> headers);

    @RequestMapping(
        value = "/v1/consents/{consentId}",
        method = RequestMethod.DELETE
    )
    ResponseEntity<Void> deleteConsent(@PathVariable("consentId") String consentId,
                                       @RequestParam Map<String, String> parameters, @RequestHeader Map<String, String> headers);

    @RequestMapping(
        value = "/v1/consents/{consentId}/status",
        method = RequestMethod.GET
    )
    ResponseEntity<ConsentStatusResponse200> getConsentStatus(
        @PathVariable("consentId") String consentId, @RequestParam Map<String, String> parameters,
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
    ResponseEntity<StartScaprocessResponse> startConsentAuthorisation(
        @PathVariable("consentId") String consentId, @RequestParam Map<String, String> parameters,
        @RequestHeader Map<String, String> headers, @RequestBody ObjectNode body);

    @RequestMapping(
        value = "/v1/consents/{consentId}/authorisations/{authorisationId}",
        method = RequestMethod.GET
    )
    ResponseEntity<ScaStatusResponse> getConsentScaStatus(@PathVariable("consentId") String consentId,
                                                          @PathVariable("authorisationId") String authorisationId,
                                                          @RequestParam Map<String, String> parameters, @RequestHeader Map<String, String> headers);

    @RequestMapping(
        value = "/v1/consents/{consentId}/authorisations/{authorisationId}",
        method = RequestMethod.PUT,
        consumes = "application/json"
    )
    ResponseEntity<Object> updateConsentsPsuData(@PathVariable("consentId") String consentId,
                                                 @PathVariable("authorisationId") String authorisationId,
                                                 @RequestParam Map<String, String> parameters, @RequestHeader Map<String, String> headers,
                                                 @RequestBody ObjectNode body);
}
