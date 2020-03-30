package de.adorsys.xs2a.adapter.rest.psd2;

import de.adorsys.xs2a.adapter.rest.psd2.model.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequestMapping("/{payment-service:payments|bulk-payments|periodic-payments}/{payment-product}")
public interface Psd2PaymentApi {
    @RequestMapping(
        method = RequestMethod.POST,
        consumes = "application/json"
    )
    ResponseEntity<PaymentInitiationRequestResponseTO> initiatePayment(
        @PathVariable("payment-service") PaymentServiceTO paymentService,
        @PathVariable("payment-product") PaymentProductTO paymentProduct,
        @RequestParam Map<String, String> queryParameters, @RequestHeader Map<String, String> headers,
        @RequestBody PaymentInitiationTO body);

    @RequestMapping(
        method = RequestMethod.POST,
        consumes = "application/xml"
    )
    ResponseEntity<PaymentInitiationRequestResponseTO> initiatePayment(
        @PathVariable("payment-service") PaymentServiceTO paymentService,
        @PathVariable("payment-product") PaymentProductTO paymentProduct,
        @RequestParam Map<String, String> queryParameters, @RequestHeader Map<String, String> headers,
        @RequestBody String body);

    @RequestMapping(
        value = "/{paymentId}",
        method = RequestMethod.GET
    )
    ResponseEntity<Object> getPaymentInformation(
        @PathVariable("payment-service") PaymentServiceTO paymentService,
        @PathVariable("payment-product") PaymentProductTO paymentProduct,
        @PathVariable("paymentId") String paymentId, @RequestParam Map<String, String> queryParameters,
        @RequestHeader Map<String, String> headers);

    @RequestMapping(
        value = "/{paymentId}/status",
        method = RequestMethod.GET
    )
    ResponseEntity<Object> getPaymentInitiationStatus(
        @PathVariable("payment-service") PaymentServiceTO paymentService,
        @PathVariable("payment-product") PaymentProductTO paymentProduct,
        @PathVariable("paymentId") String paymentId, @RequestParam Map<String, String> queryParameters,
        @RequestHeader Map<String, String> headers);

    @RequestMapping(
        value = "/{paymentId}/authorisations",
        method = RequestMethod.GET
    )
    ResponseEntity<AuthorisationsTO> getPaymentInitiationAuthorisation(
        @PathVariable("payment-service") PaymentServiceTO paymentService,
        @PathVariable("payment-product") PaymentProductTO paymentProduct,
        @PathVariable("paymentId") String paymentId, @RequestParam Map<String, String> queryParameters,
        @RequestHeader Map<String, String> headers);

    @RequestMapping(
        value = "/{paymentId}/authorisations",
        method = RequestMethod.POST,
        consumes = "application/json"
    )
    ResponseEntity<StartScaProcessResponseTO> startPaymentAuthorisation(
        @PathVariable("payment-service") PaymentServiceTO paymentService,
        @PathVariable("payment-product") PaymentProductTO paymentProduct,
        @PathVariable("paymentId") String paymentId, @RequestParam Map<String, String> queryParameters,
        @RequestHeader Map<String, String> headers, @RequestBody UpdateAuthorisationTO body);

    @RequestMapping(
        value = "/{paymentId}/authorisations/{authorisationId}",
        method = RequestMethod.GET
    )
    ResponseEntity<ScaStatusResponseTO> getPaymentInitiationScaStatus(
        @PathVariable("payment-service") PaymentServiceTO paymentService,
        @PathVariable("payment-product") PaymentProductTO paymentProduct,
        @PathVariable("paymentId") String paymentId,
        @PathVariable("authorisationId") String authorisationId,
        @RequestParam Map<String, String> queryParameters, @RequestHeader Map<String, String> headers);

    @RequestMapping(
        value = "/{paymentId}/authorisations/{authorisationId}",
        method = RequestMethod.PUT,
        consumes = "application/json"
    )
    ResponseEntity<UpdateAuthorisationResponseTO> updatePaymentPsuData(
        @PathVariable("payment-service") PaymentServiceTO paymentService,
        @PathVariable("payment-product") PaymentProductTO paymentProduct,
        @PathVariable("paymentId") String paymentId,
        @PathVariable("authorisationId") String authorisationId,
        @RequestParam Map<String, String> queryParameters, @RequestHeader Map<String, String> headers,
        @RequestBody UpdateAuthorisationTO body);
}
