package de.adorsys.xs2a.gateway.controller;

import de.adorsys.xs2a.gateway.api.PaymentApi;
import de.adorsys.xs2a.gateway.service.PaymentInitiationHeaders;
import de.adorsys.xs2a.gateway.service.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public class PaymentController implements PaymentApi {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @Override
    public ResponseEntity<Object> initiatePayment(Object body, UUID xRequestID, String psUIPAddress, String paymentService, String paymentProduct, String digest, String signature, byte[] tpPSignatureCertificate, String PSU_ID, String psUIDType, String psUCorporateID, String psUCorporateIDType, String consentID, boolean tppRedirectPreferred, String tpPRedirectURI, String tpPNokRedirectURI, boolean tpPExplicitAuthorisationPreferred, String psUIPPort, String psUAccept, String psUAcceptCharset, String psUAcceptEncoding, String psUAcceptLanguage, String psUUserAgent, String psUHttpMethod, UUID psUDeviceID, String psUGeoLocation) {
        PaymentInitiationHeaders headers = PaymentInitiationHeaders.builder()
                .xRequestID(xRequestID)
                .digest(digest)
                .signature(signature)
                .tpPSignatureCertificate(tpPSignatureCertificate)
                .PSU_ID(PSU_ID)
                .psUIDType(psUIDType)
                .psUCorporateID(psUCorporateID)
                .psUCorporateIDType(psUCorporateIDType)
                .consentID(consentID)
                .tppRedirectPreferred(tppRedirectPreferred)
                .tpPRedirectURI(tpPRedirectURI)
                .tpPNokRedirectURI(tpPNokRedirectURI)
                .tpPExplicitAuthorisationPreferred(tpPExplicitAuthorisationPreferred)
                .psUIPPort(psUIPPort)
                .psUAccept(psUAccept)
                .psUAcceptCharset(psUAcceptCharset)
                .psUAcceptEncoding(psUAcceptEncoding)
                .psUAcceptLanguage(psUAcceptLanguage)
                .psUUserAgent(psUUserAgent)
                .psUHttpMethod(psUHttpMethod)
                .psUDeviceID(psUDeviceID)
                .psUGeoLocation(psUGeoLocation)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(this.paymentService.initiatePayment(paymentService, paymentProduct, body, headers));
    }
}

