package de.adorsys.xs2a.adapter.api;

import com.fasterxml.jackson.databind.node.ObjectNode;
import de.adorsys.xs2a.adapter.model.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Generated;
import java.util.Map;

@Generated("xs2a-gateway-codegen")
public interface PaymentApi {
  @RequestMapping(
      value = "/v1/{payment-service}/{payment-product}",
      method = RequestMethod.POST,
      consumes = "application/json"
  )
  ResponseEntity<PaymentInitationRequestResponse201TO> initiatePayment(
          @PathVariable("payment-service") PaymentServiceTO paymentService,
          @PathVariable("payment-product") PaymentProductTO paymentProduct,
          @RequestHeader Map<String, String> headers, @RequestBody ObjectNode body);

  @RequestMapping(
      value = "/v1/{payment-service}/{payment-product}",
      method = RequestMethod.POST,
      consumes = "application/xml"
  )
  ResponseEntity<PaymentInitationRequestResponse201TO> initiatePayment(
          @PathVariable("payment-service") PaymentServiceTO paymentService,
          @PathVariable("payment-product") PaymentProductTO paymentProduct,
          @RequestHeader Map<String, String> headers, @RequestBody String body);

  @RequestMapping(
      value = "/v1/{payment-service}/{payment-product}/{paymentId}",
      method = RequestMethod.GET
  )
  ResponseEntity<Object> getPaymentInformation(
          @PathVariable("payment-service") PaymentServiceTO paymentService,
          @PathVariable("payment-product") PaymentProductTO paymentProduct,
          @PathVariable("paymentId") String paymentId, @RequestHeader Map<String, String> headers);

  @RequestMapping(
      value = "/v1/{payment-service}/{payment-product}/{paymentId}/status",
      method = RequestMethod.GET
  )
  ResponseEntity<Object> getPaymentInitiationStatus(
          @PathVariable("payment-service") PaymentServiceTO paymentService,
          @PathVariable("payment-product") PaymentProductTO paymentProduct,
          @PathVariable("paymentId") String paymentId, @RequestHeader Map<String, String> headers);

  @RequestMapping(
      value = "/v1/{payment-service}/{payment-product}/{paymentId}/authorisations",
      method = RequestMethod.GET
  )
  ResponseEntity<AuthorisationsTO> getPaymentInitiationAuthorisation(
          @PathVariable("payment-service") PaymentServiceTO paymentService,
          @PathVariable("payment-product") PaymentProductTO paymentProduct,
          @PathVariable("paymentId") String paymentId, @RequestHeader Map<String, String> headers);

  @RequestMapping(
      value = "/v1/{payment-service}/{payment-product}/{paymentId}/authorisations",
      method = RequestMethod.POST,
      consumes = "application/json"
  )
  ResponseEntity<StartScaprocessResponseTO> startPaymentAuthorisation(
          @PathVariable("payment-service") PaymentServiceTO paymentService,
          @PathVariable("payment-product") PaymentProductTO paymentProduct,
          @PathVariable("paymentId") String paymentId, @RequestHeader Map<String, String> headers,
          @RequestBody ObjectNode body);

  @RequestMapping(
      value = "/v1/{payment-service}/{payment-product}/{paymentId}/authorisations/{authorisationId}",
      method = RequestMethod.GET
  )
  ResponseEntity<ScaStatusResponseTO> getPaymentInitiationScaStatus(
          @PathVariable("payment-service") PaymentServiceTO paymentService,
          @PathVariable("payment-product") PaymentProductTO paymentProduct,
          @PathVariable("paymentId") String paymentId,
          @PathVariable("authorisationId") String authorisationId,
          @RequestHeader Map<String, String> headers);

  @RequestMapping(
      value = "/v1/{payment-service}/{payment-product}/{paymentId}/authorisations/{authorisationId}",
      method = RequestMethod.PUT,
      consumes = "application/json"
  )
  ResponseEntity<Object> updatePaymentPsuData(
          @PathVariable("payment-service") PaymentServiceTO paymentService,
          @PathVariable("payment-product") PaymentProductTO paymentProduct,
          @PathVariable("paymentId") String paymentId,
          @PathVariable("authorisationId") String authorisationId,
          @RequestHeader Map<String, String> headers, @RequestBody ObjectNode body);
}
