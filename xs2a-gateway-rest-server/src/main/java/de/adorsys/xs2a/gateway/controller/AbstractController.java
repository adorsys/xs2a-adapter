package de.adorsys.xs2a.gateway.controller;

import de.adorsys.xs2a.gateway.service.Headers;

import java.util.UUID;

public abstract class AbstractController {
    Headers buildHeaders(String bankCode, UUID xRequestID, String digest, String signature, byte[] tpPSignatureCertificate, String psUIPAddress, String psUIPPort, String psUAccept, String psUAcceptCharset, String psUAcceptEncoding, String psUAcceptLanguage, String psUUserAgent, String psUHttpMethod, UUID psUDeviceID, String psUGeoLocation) {
        return Headers.builder()
                .bankCode(bankCode)
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
}
