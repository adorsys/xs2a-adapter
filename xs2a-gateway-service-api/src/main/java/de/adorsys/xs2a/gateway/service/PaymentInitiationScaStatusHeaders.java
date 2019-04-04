package de.adorsys.xs2a.gateway.service;

import java.util.UUID;

public class PaymentInitiationScaStatusHeaders {
    private UUID xRequestID;
    private String digest;
    private String signature;
    private byte[] tpPSignatureCertificate;
    private String psUIPAddress;
    private String psUIPPort;
    private String psUAccept;
    private String psUAcceptCharset;
    private String psUAcceptEncoding;
    private String psUAcceptLanguage;
    private String psUUserAgent;
    private String psUHttpMethod;
    private UUID psUDeviceID;
    private String psUGeoLocation;

    private PaymentInitiationScaStatusHeaders() {
    }

    public static PaymentInitiationScaStatusHeadersBuilder builder() {
        return new PaymentInitiationScaStatusHeadersBuilder();
    }

    public static final class PaymentInitiationScaStatusHeadersBuilder {
        private UUID xRequestID;
        private String digest;
        private String signature;
        private byte[] tpPSignatureCertificate;
        private String psUIPAddress;
        private String psUIPPort;
        private String psUAccept;
        private String psUAcceptCharset;
        private String psUAcceptEncoding;
        private String psUAcceptLanguage;
        private String psUUserAgent;
        private String psUHttpMethod;
        private UUID psUDeviceID;
        private String psUGeoLocation;

        private PaymentInitiationScaStatusHeadersBuilder() {
        }

        public PaymentInitiationScaStatusHeadersBuilder xRequestID(UUID xRequestID) {
            this.xRequestID = xRequestID;
            return this;
        }

        public PaymentInitiationScaStatusHeadersBuilder digest(String digest) {
            this.digest = digest;
            return this;
        }

        public PaymentInitiationScaStatusHeadersBuilder signature(String signature) {
            this.signature = signature;
            return this;
        }

        public PaymentInitiationScaStatusHeadersBuilder tpPSignatureCertificate(byte[] tpPSignatureCertificate) {
            this.tpPSignatureCertificate = tpPSignatureCertificate;
            return this;
        }

        public PaymentInitiationScaStatusHeadersBuilder psUIPAddress(String psUIPAddress) {
            this.psUIPAddress = psUIPAddress;
            return this;
        }

        public PaymentInitiationScaStatusHeadersBuilder psUIPPort(String psUIPPort) {
            this.psUIPPort = psUIPPort;
            return this;
        }

        public PaymentInitiationScaStatusHeadersBuilder psUAccept(String psUAccept) {
            this.psUAccept = psUAccept;
            return this;
        }

        public PaymentInitiationScaStatusHeadersBuilder psUAcceptCharset(String psUAcceptCharset) {
            this.psUAcceptCharset = psUAcceptCharset;
            return this;
        }

        public PaymentInitiationScaStatusHeadersBuilder psUAcceptEncoding(String psUAcceptEncoding) {
            this.psUAcceptEncoding = psUAcceptEncoding;
            return this;
        }

        public PaymentInitiationScaStatusHeadersBuilder psUAcceptLanguage(String psUAcceptLanguage) {
            this.psUAcceptLanguage = psUAcceptLanguage;
            return this;
        }

        public PaymentInitiationScaStatusHeadersBuilder psUUserAgent(String psUUserAgent) {
            this.psUUserAgent = psUUserAgent;
            return this;
        }

        public PaymentInitiationScaStatusHeadersBuilder psUHttpMethod(String psUHttpMethod) {
            this.psUHttpMethod = psUHttpMethod;
            return this;
        }

        public PaymentInitiationScaStatusHeadersBuilder psUDeviceID(UUID psUDeviceID) {
            this.psUDeviceID = psUDeviceID;
            return this;
        }

        public PaymentInitiationScaStatusHeadersBuilder psUGeoLocation(String psUGeoLocation) {
            this.psUGeoLocation = psUGeoLocation;
            return this;
        }

        public PaymentInitiationScaStatusHeaders build() {
            PaymentInitiationScaStatusHeaders paymentInitiationScaStatusHeaders = new PaymentInitiationScaStatusHeaders();
            paymentInitiationScaStatusHeaders.tpPSignatureCertificate = this.tpPSignatureCertificate;
            paymentInitiationScaStatusHeaders.psUIPPort = this.psUIPPort;
            paymentInitiationScaStatusHeaders.psUDeviceID = this.psUDeviceID;
            paymentInitiationScaStatusHeaders.xRequestID = this.xRequestID;
            paymentInitiationScaStatusHeaders.digest = this.digest;
            paymentInitiationScaStatusHeaders.signature = this.signature;
            paymentInitiationScaStatusHeaders.psUAccept = this.psUAccept;
            paymentInitiationScaStatusHeaders.psUAcceptEncoding = this.psUAcceptEncoding;
            paymentInitiationScaStatusHeaders.psUAcceptLanguage = this.psUAcceptLanguage;
            paymentInitiationScaStatusHeaders.psUHttpMethod = this.psUHttpMethod;
            paymentInitiationScaStatusHeaders.psUGeoLocation = this.psUGeoLocation;
            paymentInitiationScaStatusHeaders.psUAcceptCharset = this.psUAcceptCharset;
            paymentInitiationScaStatusHeaders.psUUserAgent = this.psUUserAgent;
            paymentInitiationScaStatusHeaders.psUIPAddress = this.psUIPAddress;
            return paymentInitiationScaStatusHeaders;
        }
    }
}
