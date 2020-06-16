package de.adorsys.xs2a.adapter.rest.api;

import com.fasterxml.jackson.databind.node.ObjectNode;
import de.adorsys.xs2a.adapter.api.model.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Generated;
import java.util.Map;

@Generated("xs2a-adapter-codegen")
public interface PaymentApi {
    @RequestMapping(
        value = "/v1/{payment-service}/{payment-product}",
        method = RequestMethod.POST,
        consumes = "application/json"
    )
    ResponseEntity<PaymentInitationRequestResponse201> initiatePayment(
        @PathVariable("payment-service") PaymentService paymentService,
        @PathVariable("payment-product") PaymentProduct paymentProduct,
        @RequestParam Map<String, String> parameters, @RequestHeader Map<String, String> headers,
        @RequestBody ObjectNode body);

    @RequestMapping(
        value = "/v1/{payment-service}/{payment-product}",
        method = RequestMethod.POST,
        consumes = "application/xml"
    )
    ResponseEntity<PaymentInitationRequestResponse201> initiatePayment(
        @PathVariable("payment-service") PaymentService paymentService,
        @PathVariable("payment-product") PaymentProduct paymentProduct,
        @RequestParam Map<String, String> parameters, @RequestHeader Map<String, String> headers,
        @RequestBody String body);

    @RequestMapping(
        value = "/v1/{payment-service}/{payment-product}/{paymentId}",
        method = RequestMethod.GET
    )
    ResponseEntity<Object> getPaymentInformation(
        @PathVariable("payment-service") PaymentService paymentService,
        @PathVariable("payment-product") PaymentProduct paymentProduct,
        @PathVariable("paymentId") String paymentId, @RequestParam Map<String, String> parameters,
        @RequestHeader Map<String, String> headers);

    @RequestMapping(
        value = "/v1/{payment-service}/{payment-product}/{paymentId}/status",
        method = RequestMethod.GET
    )
    ResponseEntity<Object> getPaymentInitiationStatus(
        @PathVariable("payment-service") PaymentService paymentService,
        @PathVariable("payment-product") PaymentProduct paymentProduct,
        @PathVariable("paymentId") String paymentId, @RequestParam Map<String, String> parameters,
        @RequestHeader Map<String, String> headers);

    @RequestMapping(
        value = "/v1/{payment-service}/{payment-product}/{paymentId}/authorisations",
        method = RequestMethod.GET
    )
    ResponseEntity<Authorisations> getPaymentInitiationAuthorisation(
        @PathVariable("payment-service") PaymentService paymentService,
        @PathVariable("payment-product") PaymentProduct paymentProduct,
        @PathVariable("paymentId") String paymentId, @RequestParam Map<String, String> parameters,
        @RequestHeader Map<String, String> headers);

    @RequestMapping(
        value = "/v1/{payment-service}/{payment-product}/{paymentId}/authorisations",
        method = RequestMethod.POST,
        consumes = "application/json"
    )
    ResponseEntity<StartScaprocessResponse> startPaymentAuthorisation(
        @PathVariable("payment-service") PaymentService paymentService,
        @PathVariable("payment-product") PaymentProduct paymentProduct,
        @PathVariable("paymentId") String paymentId, @RequestParam Map<String, String> parameters,
        @RequestHeader Map<String, String> headers, @RequestBody ObjectNode body);

    @RequestMapping(
        value = "/v1/{payment-service}/{payment-product}/{paymentId}/authorisations/{authorisationId}",
        method = RequestMethod.GET
    )
    ResponseEntity<ScaStatusResponse> getPaymentInitiationScaStatus(
        @PathVariable("payment-service") PaymentService paymentService,
        @PathVariable("payment-product") PaymentProduct paymentProduct,
        @PathVariable("paymentId") String paymentId,
        @PathVariable("authorisationId") String authorisationId,
        @RequestParam Map<String, String> parameters, @RequestHeader Map<String, String> headers);

    @RequestMapping(
        value = "/v1/{payment-service}/{payment-product}/{paymentId}/authorisations/{authorisationId}",
        method = RequestMethod.PUT,
        consumes = "application/json"
    )
    ResponseEntity<Object> updatePaymentPsuData(
        @PathVariable("payment-service") PaymentService paymentService,
        @PathVariable("payment-product") PaymentProduct paymentProduct,
        @PathVariable("paymentId") String paymentId,
        @PathVariable("authorisationId") String authorisationId,
        @RequestParam Map<String, String> parameters, @RequestHeader Map<String, String> headers,
        @RequestBody ObjectNode body);
}
