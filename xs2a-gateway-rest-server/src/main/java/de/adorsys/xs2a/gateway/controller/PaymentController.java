package de.adorsys.xs2a.gateway.controller;

import de.adorsys.xs2a.gateway.api.PaymentApi;
import de.adorsys.xs2a.gateway.mapper.PaymentInitiationScaStatusResponseMapper;
import de.adorsys.xs2a.gateway.model.shared.ScaStatusResponse;
import de.adorsys.xs2a.gateway.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class PaymentController implements PaymentApi {
    private final PaymentService paymentService;
    private final PaymentInitiationScaStatusResponseMapper paymentInitiationScaStatusResponseMapper;

    public PaymentController(PaymentService paymentService, PaymentInitiationScaStatusResponseMapper paymentInitiationScaStatusResponseMapper) {
        this.paymentService = paymentService;
        this.paymentInitiationScaStatusResponseMapper = paymentInitiationScaStatusResponseMapper;
    }

    @Override
    public ResponseEntity<Object> initiatePayment(Object body, UUID xRequestID, String psUIPAddress, String paymentService, String paymentProduct, String digest, String signature, byte[] tpPSignatureCertificate, String PSU_ID, String psUIDType, String psUCorporateID, String psUCorporateIDType, String consentID, Boolean tppRedirectPreferred, String tpPRedirectURI, String tpPNokRedirectURI, Boolean tpPExplicitAuthorisationPreferred, String psUIPPort, String psUAccept, String psUAcceptCharset, String psUAcceptEncoding, String psUAcceptLanguage, String psUUserAgent, String psUHttpMethod, UUID psUDeviceID, String psUGeoLocation) {
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

    @Override
    public ResponseEntity<Object> getPaymentInformation(String paymentService, String paymentProduct, String paymentId, UUID xRequestID, String digest, String signature, byte[] tpPSignatureCertificate, String psUIPAddress, String psUIPPort, String psUAccept, String psUAcceptCharset, String psUAcceptEncoding, String psUAcceptLanguage, String psUUserAgent, String psUHttpMethod, UUID psUDeviceID, String psUGeoLocation) {
        PaymentInformationHeaders headers = PaymentInformationHeaders.builder()
                                                  .xRequestID(xRequestID)
                                                  .digest(digest)
                                                  .signature(signature)
                                                  .tpPSignatureCertificate(tpPSignatureCertificate)
                                                  .psUIPAddress(psUIPAddress)
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

        return ResponseEntity.status(HttpStatus.OK)
                .body(this.paymentService.getPaymentInformation(paymentService, paymentProduct, paymentId, headers));
    }

    @Override
    public ResponseEntity<ScaStatusResponse> getPaymentInitiationScaStatus(String paymentService, String paymentProduct, String paymentId, String authorisationId, UUID xRequestID, String digest, String signature, byte[] tpPSignatureCertificate, String psUIPAddress, String psUIPPort, String psUAccept, String psUAcceptCharset, String psUAcceptEncoding, String psUAcceptLanguage, String psUUserAgent, String psUHttpMethod, UUID psUDeviceID, String psUGeoLocation) {
        PaymentInitiationScaStatusHeaders headers = PaymentInitiationScaStatusHeaders.builder()
                                                            .xRequestID(xRequestID)
                                                            .digest(digest)
                                                            .signature(signature)
                                                            .tpPSignatureCertificate(tpPSignatureCertificate)
                                                            .psUIPAddress(psUIPAddress)
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

        PaymentInitiationScaStatusResponse paymentInitiationScaStatusResponse = this.paymentService.getPaymentInitiationScaStatus(paymentService, paymentProduct, paymentId, authorisationId, headers);

        return ResponseEntity.status(HttpStatus.OK)
                       .body(paymentInitiationScaStatusResponseMapper.mapToScaStatusResponse(paymentInitiationScaStatusResponse));
    }
}

