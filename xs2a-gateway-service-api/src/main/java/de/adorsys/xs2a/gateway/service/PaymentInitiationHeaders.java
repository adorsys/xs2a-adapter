package de.adorsys.xs2a.gateway.service;

import java.util.UUID;

public class PaymentInitiationHeaders {
    private UUID xRequestID;
    private String psUIPAddress;
    private String digest;
    private String signature;
    private byte[] tpPSignatureCertificate;
    private String PSU_ID;
    private String psUIDType;
    private String psUCorporateID;
    private String psUCorporateIDType;
    private String consentID;
    private boolean tppRedirectPreferred;
    private String tpPRedirectURI;
    private String tpPNokRedirectURI;
    private boolean tpPExplicitAuthorisationPreferred;
    private String psUIPPort;
    private String psUAccept;
    private String psUAcceptCharset;
    private String psUAcceptEncoding;
    private String psUAcceptLanguage;
    private String psUUserAgent;
    private String psUHttpMethod;
    private UUID psUDeviceID;
    private String psUGeoLocation;

    public static PaymentInitiationHeadersBuilder builder() {
        return new PaymentInitiationHeadersBuilder();
    }

    public static final class PaymentInitiationHeadersBuilder {
        private UUID xRequestID;
        private String psUIPAddress;
        private String digest;
        private String signature;
        private byte[] tpPSignatureCertificate;
        private String PSU_ID;
        private String psUIDType;
        private String psUCorporateID;
        private String psUCorporateIDType;
        private String consentID;
        private boolean tppRedirectPreferred;
        private String tpPRedirectURI;
        private String tpPNokRedirectURI;
        private boolean tpPExplicitAuthorisationPreferred;
        private String psUIPPort;
        private String psUAccept;
        private String psUAcceptCharset;
        private String psUAcceptEncoding;
        private String psUAcceptLanguage;
        private String psUUserAgent;
        private String psUHttpMethod;
        private UUID psUDeviceID;
        private String psUGeoLocation;

        private PaymentInitiationHeadersBuilder() {
        }

        public PaymentInitiationHeadersBuilder xRequestID(UUID xRequestID) {
            this.xRequestID = xRequestID;
            return this;
        }

        public PaymentInitiationHeadersBuilder psUIPAddress(String psUIPAddress) {
            this.psUIPAddress = psUIPAddress;
            return this;
        }

        public PaymentInitiationHeadersBuilder digest(String digest) {
            this.digest = digest;
            return this;
        }

        public PaymentInitiationHeadersBuilder signature(String signature) {
            this.signature = signature;
            return this;
        }

        public PaymentInitiationHeadersBuilder tpPSignatureCertificate(byte[] tpPSignatureCertificate) {
            this.tpPSignatureCertificate = tpPSignatureCertificate;
            return this;
        }

        public PaymentInitiationHeadersBuilder PSU_ID(String PSU_ID) {
            this.PSU_ID = PSU_ID;
            return this;
        }

        public PaymentInitiationHeadersBuilder psUIDType(String psUIDType) {
            this.psUIDType = psUIDType;
            return this;
        }

        public PaymentInitiationHeadersBuilder psUCorporateID(String psUCorporateID) {
            this.psUCorporateID = psUCorporateID;
            return this;
        }

        public PaymentInitiationHeadersBuilder psUCorporateIDType(String psUCorporateIDType) {
            this.psUCorporateIDType = psUCorporateIDType;
            return this;
        }

        public PaymentInitiationHeadersBuilder consentID(String consentID) {
            this.consentID = consentID;
            return this;
        }

        public PaymentInitiationHeadersBuilder tppRedirectPreferred(boolean tppRedirectPreferred) {
            this.tppRedirectPreferred = tppRedirectPreferred;
            return this;
        }

        public PaymentInitiationHeadersBuilder tpPRedirectURI(String tpPRedirectURI) {
            this.tpPRedirectURI = tpPRedirectURI;
            return this;
        }

        public PaymentInitiationHeadersBuilder tpPNokRedirectURI(String tpPNokRedirectURI) {
            this.tpPNokRedirectURI = tpPNokRedirectURI;
            return this;
        }

        public PaymentInitiationHeadersBuilder tpPExplicitAuthorisationPreferred(boolean tpPExplicitAuthorisationPreferred) {
            this.tpPExplicitAuthorisationPreferred = tpPExplicitAuthorisationPreferred;
            return this;
        }

        public PaymentInitiationHeadersBuilder psUIPPort(String psUIPPort) {
            this.psUIPPort = psUIPPort;
            return this;
        }

        public PaymentInitiationHeadersBuilder psUAccept(String psUAccept) {
            this.psUAccept = psUAccept;
            return this;
        }

        public PaymentInitiationHeadersBuilder psUAcceptCharset(String psUAcceptCharset) {
            this.psUAcceptCharset = psUAcceptCharset;
            return this;
        }

        public PaymentInitiationHeadersBuilder psUAcceptEncoding(String psUAcceptEncoding) {
            this.psUAcceptEncoding = psUAcceptEncoding;
            return this;
        }

        public PaymentInitiationHeadersBuilder psUAcceptLanguage(String psUAcceptLanguage) {
            this.psUAcceptLanguage = psUAcceptLanguage;
            return this;
        }

        public PaymentInitiationHeadersBuilder psUUserAgent(String psUUserAgent) {
            this.psUUserAgent = psUUserAgent;
            return this;
        }

        public PaymentInitiationHeadersBuilder psUHttpMethod(String psUHttpMethod) {
            this.psUHttpMethod = psUHttpMethod;
            return this;
        }

        public PaymentInitiationHeadersBuilder psUDeviceID(UUID psUDeviceID) {
            this.psUDeviceID = psUDeviceID;
            return this;
        }

        public PaymentInitiationHeadersBuilder psUGeoLocation(String psUGeoLocation) {
            this.psUGeoLocation = psUGeoLocation;
            return this;
        }

        public PaymentInitiationHeaders build() {
            PaymentInitiationHeaders paymentInitiationHeaders = new PaymentInitiationHeaders();
            paymentInitiationHeaders.xRequestID = this.xRequestID;
            paymentInitiationHeaders.psUIPPort = this.psUIPPort;
            paymentInitiationHeaders.psUAcceptCharset = this.psUAcceptCharset;
            paymentInitiationHeaders.psUCorporateID = this.psUCorporateID;
            paymentInitiationHeaders.psUIPAddress = this.psUIPAddress;
            paymentInitiationHeaders.digest = this.digest;
            paymentInitiationHeaders.tpPExplicitAuthorisationPreferred = this.tpPExplicitAuthorisationPreferred;
            paymentInitiationHeaders.psUGeoLocation = this.psUGeoLocation;
            paymentInitiationHeaders.tpPNokRedirectURI = this.tpPNokRedirectURI;
            paymentInitiationHeaders.tppRedirectPreferred = this.tppRedirectPreferred;
            paymentInitiationHeaders.tpPSignatureCertificate = this.tpPSignatureCertificate;
            paymentInitiationHeaders.psUAcceptLanguage = this.psUAcceptLanguage;
            paymentInitiationHeaders.psUHttpMethod = this.psUHttpMethod;
            paymentInitiationHeaders.psUDeviceID = this.psUDeviceID;
            paymentInitiationHeaders.psUAcceptEncoding = this.psUAcceptEncoding;
            paymentInitiationHeaders.psUUserAgent = this.psUUserAgent;
            paymentInitiationHeaders.signature = this.signature;
            paymentInitiationHeaders.psUIDType = this.psUIDType;
            paymentInitiationHeaders.psUCorporateIDType = this.psUCorporateIDType;
            paymentInitiationHeaders.PSU_ID = this.PSU_ID;
            paymentInitiationHeaders.consentID = this.consentID;
            paymentInitiationHeaders.tpPRedirectURI = this.tpPRedirectURI;
            paymentInitiationHeaders.psUAccept = this.psUAccept;
            return paymentInitiationHeaders;
        }
    }
}
