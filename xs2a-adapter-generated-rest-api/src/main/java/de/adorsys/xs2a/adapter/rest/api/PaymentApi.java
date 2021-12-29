package de.adorsys.xs2a.adapter.rest.api;

import com.fasterxml.jackson.databind.node.ObjectNode;
import de.adorsys.xs2a.adapter.api.model.Authorisations;
import de.adorsys.xs2a.adapter.api.model.PaymentInitationRequestResponse201;
import de.adorsys.xs2a.adapter.api.model.PaymentProduct;
import de.adorsys.xs2a.adapter.api.model.PaymentService;
import de.adorsys.xs2a.adapter.api.model.PeriodicPaymentInitiationMultipartBody;
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
public interface PaymentApi {
    @RequestMapping(
        value = "/v1/{payment-service}/{payment-product}",
        method = RequestMethod.POST,
        consumes = "application/json"
    )
    ResponseEntity<PaymentInitationRequestResponse201> initiatePayment(
        @PathVariable("payment-service") PaymentService paymentService,
        @PathVariable("payment-product") PaymentProduct paymentProduct,
        @RequestParam Map<String, String> parameters,
        @RequestHeader Map<String, String> headers,
        @RequestBody ObjectNode body);

    @RequestMapping(
        value = "/v1/{payment-service}/{payment-product}",
        method = RequestMethod.POST,
        consumes = { "application/xml", "text/plain" }
    )
    ResponseEntity<PaymentInitationRequestResponse201> initiatePayment(
        @PathVariable("payment-service") PaymentService paymentService,
        @PathVariable("payment-product") PaymentProduct paymentProduct,
        @RequestParam Map<String, String> parameters,
        @RequestHeader Map<String, String> headers,
        @RequestBody String body);

    @RequestMapping(
        value = "/v1/{payment-service}/{payment-product}",
        method = RequestMethod.POST,
        consumes = "multipart/form-data"
    )
    ResponseEntity<PaymentInitationRequestResponse201> initiatePayment(
        @PathVariable("payment-service") PaymentService paymentService,
        @PathVariable("payment-product") PaymentProduct paymentProduct,
        @RequestParam Map<String, String> parameters,
        @RequestHeader Map<String, String> headers,
        PeriodicPaymentInitiationMultipartBody body);

    @RequestMapping(
        value = "/v1/{payment-service}/{payment-product}/{paymentId}",
        method = RequestMethod.GET
    )
    ResponseEntity<Object> getPaymentInformation(
        @PathVariable("payment-service") PaymentService paymentService,
        @PathVariable("payment-product") PaymentProduct paymentProduct,
        @PathVariable("paymentId") String paymentId,
        @RequestParam Map<String, String> parameters,
        @RequestHeader Map<String, String> headers);

    @RequestMapping(
        value = "/v1/{payment-service}/{payment-product}/{paymentId}/status",
        method = RequestMethod.GET
    )
    ResponseEntity<Object> getPaymentInitiationStatus(
        @PathVariable("payment-service") PaymentService paymentService,
        @PathVariable("payment-product") PaymentProduct paymentProduct,
        @PathVariable("paymentId") String paymentId,
        @RequestParam Map<String, String> parameters,
        @RequestHeader Map<String, String> headers);

    @RequestMapping(
        value = "/v1/{payment-service}/{payment-product}/{paymentId}/authorisations",
        method = RequestMethod.GET
    )
    ResponseEntity<Authorisations> getPaymentInitiationAuthorisation(
        @PathVariable("payment-service") PaymentService paymentService,
        @PathVariable("payment-product") PaymentProduct paymentProduct,
        @PathVariable("paymentId") String paymentId,
        @RequestParam Map<String, String> parameters,
        @RequestHeader Map<String, String> headers);

    @RequestMapping(
        value = "/v1/{payment-service}/{payment-product}/{paymentId}/authorisations",
        method = RequestMethod.POST,
        consumes = "application/json"
    )
    ResponseEntity<StartScaprocessResponse> startPaymentAuthorisation(
        @PathVariable("payment-service") PaymentService paymentService,
        @PathVariable("payment-product") PaymentProduct paymentProduct,
        @PathVariable("paymentId") String paymentId,
        @RequestParam Map<String, String> parameters,
        @RequestHeader Map<String, String> headers,
        @RequestBody ObjectNode body);

    @RequestMapping(
        value = "/v1/{payment-service}/{payment-product}/{paymentId}/authorisations/{authorisationId}",
        method = RequestMethod.GET
    )
    ResponseEntity<ScaStatusResponse> getPaymentInitiationScaStatus(
        @PathVariable("payment-service") PaymentService paymentService,
        @PathVariable("payment-product") PaymentProduct paymentProduct,
        @PathVariable("paymentId") String paymentId,
        @PathVariable("authorisationId") String authorisationId,
        @RequestParam Map<String, String> parameters,
        @RequestHeader Map<String, String> headers);

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
        @RequestParam Map<String, String> parameters,
        @RequestHeader Map<String, String> headers,
        @RequestBody ObjectNode body);
}
