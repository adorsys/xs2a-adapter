package de.adorsys.xs2a.adapter.remote.api.psd2;

import de.adorsys.xs2a.adapter.rest.psd2.model.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

public interface Psd2PaymentApi {
    @RequestMapping(
        value = "/{payment-service}/{payment-product}",
        method = RequestMethod.POST,
        consumes = "application/json"
    )
    ResponseEntity<PaymentInitiationRequestResponseTO> initiatePayment(
        @PathVariable("payment-service") String paymentService,
        @PathVariable("payment-product") String paymentProduct,
        @RequestParam Map<String, String> queryParameters, @RequestHeader Map<String, String> headers,
        @RequestBody PaymentInitiationTO body) throws IOException;

    @RequestMapping(
        value = "/{payment-service}/{payment-product}",
        method = RequestMethod.POST,
        consumes = "application/xml"
    )
    ResponseEntity<PaymentInitiationRequestResponseTO> initiatePayment(
        @PathVariable("payment-service") String paymentService,
        @PathVariable("payment-product") String paymentProduct,
        @RequestParam Map<String, String> queryParameters, @RequestHeader Map<String, String> headers,
        @RequestBody String body) throws IOException;

    @RequestMapping(
        value = "/{payment-service}/{payment-product}/{paymentId}",
        method = RequestMethod.GET
    )
    ResponseEntity<Object> getPaymentInformation(
        @PathVariable("payment-service") String paymentService,
        @PathVariable("payment-product") String paymentProduct,
        @PathVariable("paymentId") String paymentId,
        @RequestParam Map<String, String> queryParameters, @RequestHeader Map<String, String> headers)
        throws IOException;

    @RequestMapping(
        value = "/{payment-service}/{payment-product}/{paymentId}/status",
        method = RequestMethod.GET
    )
    ResponseEntity<Object> getPaymentInitiationStatus(
        @PathVariable("payment-service") String paymentService,
        @PathVariable("payment-product") String paymentProduct,
        @PathVariable("paymentId") String paymentId,
        @RequestParam Map<String, String> queryParameters, @RequestHeader Map<String, String> headers)
        throws IOException;

    @RequestMapping(
        value = "/{payment-service}/{payment-product}/{paymentId}/authorisations",
        method = RequestMethod.GET
    )
    ResponseEntity<AuthorisationsTO> getPaymentInitiationAuthorisation(
        @PathVariable("payment-service") String paymentService,
        @PathVariable("payment-product") String paymentProduct,
        @PathVariable("paymentId") String paymentId,
        @RequestParam Map<String, String> queryParameters, @RequestHeader Map<String, String> headers)
        throws IOException;

    @RequestMapping(
        value = "/{payment-service}/{payment-product}/{paymentId}/authorisations",
        method = RequestMethod.POST,
        consumes = "application/json"
    )
    ResponseEntity<StartScaProcessResponseTO> startPaymentAuthorisation(
        @PathVariable("payment-service") String paymentService,
        @PathVariable("payment-product") String paymentProduct,
        @PathVariable("paymentId") String paymentId,
        @RequestParam Map<String, String> queryParameters, @RequestHeader Map<String, String> headers,
        @RequestBody UpdateAuthorisationTO body) throws IOException;

    @RequestMapping(
        value = "/{payment-service}/{payment-product}/{paymentId}/authorisations/{authorisationId}",
        method = RequestMethod.GET
    )
    ResponseEntity<ScaStatusResponseTO> getPaymentInitiationScaStatus(
        @PathVariable("payment-service") String paymentService,
        @PathVariable("payment-product") String paymentProduct,
        @PathVariable("paymentId") String paymentId,
        @PathVariable("authorisationId") String authorisationId,
        @RequestParam Map<String, String> queryParameters, @RequestHeader Map<String, String> headers)
        throws IOException;

    @RequestMapping(
        value = "/{payment-service}/{payment-product}/{paymentId}/authorisations/{authorisationId}",
        method = RequestMethod.PUT,
        consumes = "application/json"
    )
    ResponseEntity<UpdateAuthorisationResponseTO> updatePaymentPsuData(
        @PathVariable("payment-service") String paymentService,
        @PathVariable("payment-product") String paymentProduct,
        @PathVariable("paymentId") String paymentId,
        @PathVariable("authorisationId") String authorisationId,
        @RequestParam Map<String, String> queryParameters, @RequestHeader Map<String, String> headers,
        @RequestBody UpdateAuthorisationTO body) throws IOException;
}
