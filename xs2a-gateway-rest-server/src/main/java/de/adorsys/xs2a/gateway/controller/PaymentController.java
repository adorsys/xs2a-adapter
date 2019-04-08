package de.adorsys.xs2a.gateway.controller;

import de.adorsys.xs2a.gateway.api.PaymentApi;
import de.adorsys.xs2a.gateway.mapper.PaymentInitiationScaStatusResponseMapper;
import de.adorsys.xs2a.gateway.mapper.PaymentInitiationStatusMapper;
import de.adorsys.xs2a.gateway.mapper.SinglePaymentInformationMapper;
import de.adorsys.xs2a.gateway.model.pis.PaymentInitiationSctWithStatusResponse;
import de.adorsys.xs2a.gateway.model.pis.PaymentInitiationStatusResponse200Json;
import de.adorsys.xs2a.gateway.model.shared.ScaStatusResponse;
import de.adorsys.xs2a.gateway.service.*;
import org.mapstruct.factory.Mappers;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class PaymentController implements PaymentApi {
    private final PaymentService paymentService;
    private final PaymentInitiationScaStatusResponseMapper paymentInitiationScaStatusResponseMapper;
    private final SinglePaymentInformationMapper singlePaymentInformationMapper = Mappers.getMapper(SinglePaymentInformationMapper.class);
    private final PaymentInitiationStatusMapper paymentInitiationStatusMapper = Mappers.getMapper(PaymentInitiationStatusMapper.class);

    public PaymentController(PaymentService paymentService, PaymentInitiationScaStatusResponseMapper paymentInitiationScaStatusResponseMapper) {
        this.paymentService = paymentService;
        this.paymentInitiationScaStatusResponseMapper = paymentInitiationScaStatusResponseMapper;
    }

    @Override
    public ResponseEntity<Object> initiatePayment(Object body, UUID xRequestID, String psUIPAddress, String paymentProduct, String digest, String signature, byte[] tpPSignatureCertificate, String PSU_ID, String psUIDType, String psUCorporateID, String psUCorporateIDType, String consentID, Boolean tppRedirectPreferred, String tpPRedirectURI, String tpPNokRedirectURI, Boolean tpPExplicitAuthorisationPreferred, String psUIPPort, String psUAccept, String psUAcceptCharset, String psUAcceptEncoding, String psUAcceptLanguage, String psUUserAgent, String psUHttpMethod, UUID psUDeviceID, String psUGeoLocation) {
        Headers headers = Headers.builder()
                                  .xRequestId(xRequestID)
                                  .psuIpAddress(psUIPAddress)
                                  .digest(digest)
                                  .signature(signature)
                                  .tppSignatureCertificate(tpPSignatureCertificate)
                                  .psuId(PSU_ID)
                                  .psuIdType(psUIDType)
                                  .psuCorporateId(psUCorporateID)
                                  .psuCorporateIdType(psUCorporateIDType)
                                  .consentId(consentID)
                                  .tppRedirectPreferred(tppRedirectPreferred)
                                  .tppRedirectUri(tpPRedirectURI)
                                  .tppNokRedirectUri(tpPNokRedirectURI)
                                  .tppExplicitAuthorisationPreferred(tpPExplicitAuthorisationPreferred)
                                  .psuIpPort(psUIPPort)
                                  .psuAccept(psUAccept)
                                  .psuAcceptCharset(psUAcceptCharset)
                                  .psuAcceptEncoding(psUAcceptEncoding)
                                  .psuAcceptLanguage(psUAcceptLanguage)
                                  .psuUserAgent(psUUserAgent)
                                  .psuHttpMethod(psUHttpMethod)
                                  .psuDeviceId(psUDeviceID)
                                  .psuGeoLocation(psUGeoLocation)
                                  .build();

        return ResponseEntity.status(HttpStatus.CREATED)
                       .body(this.paymentService.initiateSinglePayment(paymentProduct, body, headers));
    }

    @Override
    public ResponseEntity<PaymentInitiationSctWithStatusResponse> getSinglePaymentInformation(String paymentProduct, String paymentId, UUID xRequestID, String digest, String signature, byte[] tpPSignatureCertificate, String psUIPAddress, String psUIPPort, String psUAccept, String psUAcceptCharset, String psUAcceptEncoding, String psUAcceptLanguage, String psUUserAgent, String psUHttpMethod, UUID psUDeviceID, String psUGeoLocation) {
        Headers headers = buildHeaders(xRequestID, digest, signature, tpPSignatureCertificate, psUIPAddress, psUIPPort, psUAccept, psUAcceptCharset, psUAcceptEncoding, psUAcceptLanguage, psUUserAgent, psUHttpMethod, psUDeviceID, psUGeoLocation);

        SinglePaymentInitiationInformationWithStatusResponse response = this.paymentService.getSinglePaymentInformation(paymentProduct, paymentId, headers);

        return ResponseEntity.status(HttpStatus.OK)
                       .body(singlePaymentInformationMapper.toPaymentInitiationSctWithStatusResponse(response));
    }

    private Headers buildHeaders(UUID xRequestID, String digest, String signature, byte[] tpPSignatureCertificate, String psUIPAddress, String psUIPPort, String psUAccept, String psUAcceptCharset, String psUAcceptEncoding, String psUAcceptLanguage, String psUUserAgent, String psUHttpMethod, UUID psUDeviceID, String psUGeoLocation) {
        return Headers.builder()
                .xRequestId(xRequestID)
                .digest(digest)
                .signature(signature)
                .tppSignatureCertificate(tpPSignatureCertificate)
                .psuIpAddress(psUIPAddress)
                .psuIpPort(psUIPPort)
                .psuAccept(psUAccept)
                .psuAcceptCharset(psUAcceptCharset)
                .psuAcceptEncoding(psUAcceptEncoding)
                .psuAcceptLanguage(psUAcceptLanguage)
                .psuUserAgent(psUUserAgent)
                .psuHttpMethod(psUHttpMethod)
                .psuDeviceId(psUDeviceID)
                .psuGeoLocation(psUGeoLocation)
                .build();
    }

    @Override
    public ResponseEntity<ScaStatusResponse> getPaymentInitiationScaStatus(String paymentService, String paymentProduct, String paymentId, String authorisationId, UUID xRequestID, String digest, String signature, byte[] tpPSignatureCertificate, String psUIPAddress, String psUIPPort, String psUAccept, String psUAcceptCharset, String psUAcceptEncoding, String psUAcceptLanguage, String psUUserAgent, String psUHttpMethod, UUID psUDeviceID, String psUGeoLocation) {
        Headers headers = buildHeaders(xRequestID, digest, signature, tpPSignatureCertificate, psUIPAddress, psUIPPort, psUAccept, psUAcceptCharset, psUAcceptEncoding, psUAcceptLanguage, psUUserAgent, psUHttpMethod, psUDeviceID, psUGeoLocation);

        PaymentInitiationScaStatusResponse paymentInitiationScaStatusResponse = this.paymentService.getPaymentInitiationScaStatus(paymentService, paymentProduct, paymentId, authorisationId, headers);

        return ResponseEntity.status(HttpStatus.OK)
                       .body(paymentInitiationScaStatusResponseMapper.mapToScaStatusResponse(paymentInitiationScaStatusResponse));
    }

    @Override
    public ResponseEntity<PaymentInitiationStatusResponse200Json> getSinglePaymentInitiationStatus(String paymentProduct, String paymentId, UUID xRequestID, String digest, String signature, byte[] tpPSignatureCertificate, String psUIPAddress, String psUIPPort, String psUAccept, String psUAcceptCharset, String psUAcceptEncoding, String psUAcceptLanguage, String psUUserAgent, String psUHttpMethod, UUID psUDeviceID, String psUGeoLocation) {
        Headers headers = buildHeaders(xRequestID, digest, signature, tpPSignatureCertificate, psUIPAddress, psUIPPort, psUAccept, psUAcceptCharset, psUAcceptEncoding, psUAcceptLanguage, psUUserAgent, psUHttpMethod, psUDeviceID, psUGeoLocation);

        PaymentInitiationStatus status = paymentService.getSinglePaymentInitiationStatus(paymentProduct, paymentId, headers);
        return ResponseEntity.ok(paymentInitiationStatusMapper.toPaymentInitiationStatusResponse200Json(status));
    }
}

