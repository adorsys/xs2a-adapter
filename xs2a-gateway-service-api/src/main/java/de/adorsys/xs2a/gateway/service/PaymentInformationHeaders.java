package de.adorsys.xs2a.gateway.service;

import java.util.UUID;

public class PaymentInformationHeaders {
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

    public static PaymentInformationHeadersBuilder builder() {
        return new PaymentInformationHeadersBuilder();
    }

    public static final class PaymentInformationHeadersBuilder {
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

        private PaymentInformationHeadersBuilder() {
        }

        public PaymentInformationHeadersBuilder xRequestID(UUID xRequestID) {
            this.xRequestID = xRequestID;
            return this;
        }

        public PaymentInformationHeadersBuilder digest(String digest) {
            this.digest = digest;
            return this;
        }

        public PaymentInformationHeadersBuilder signature(String signature) {
            this.signature = signature;
            return this;
        }

        public PaymentInformationHeadersBuilder tpPSignatureCertificate(byte[] tpPSignatureCertificate) {
            this.tpPSignatureCertificate = tpPSignatureCertificate;
            return this;
        }

        public PaymentInformationHeadersBuilder psUIPAddress(String psUIPAddress) {
            this.psUIPAddress = psUIPAddress;
            return this;
        }

        public PaymentInformationHeadersBuilder psUIPPort(String psUIPPort) {
            this.psUIPPort = psUIPPort;
            return this;
        }

        public PaymentInformationHeadersBuilder psUAccept(String psUAccept) {
            this.psUAccept = psUAccept;
            return this;
        }

        public PaymentInformationHeadersBuilder psUAcceptCharset(String psUAcceptCharset) {
            this.psUAcceptCharset = psUAcceptCharset;
            return this;
        }

        public PaymentInformationHeadersBuilder psUAcceptEncoding(String psUAcceptEncoding) {
            this.psUAcceptEncoding = psUAcceptEncoding;
            return this;
        }

        public PaymentInformationHeadersBuilder psUAcceptLanguage(String psUAcceptLanguage) {
            this.psUAcceptLanguage = psUAcceptLanguage;
            return this;
        }

        public PaymentInformationHeadersBuilder psUUserAgent(String psUUserAgent) {
            this.psUUserAgent = psUUserAgent;
            return this;
        }

        public PaymentInformationHeadersBuilder psUHttpMethod(String psUHttpMethod) {
            this.psUHttpMethod = psUHttpMethod;
            return this;
        }

        public PaymentInformationHeadersBuilder psUDeviceID(UUID psUDeviceID) {
            this.psUDeviceID = psUDeviceID;
            return this;
        }

        public PaymentInformationHeadersBuilder psUGeoLocation(String psUGeoLocation) {
            this.psUGeoLocation = psUGeoLocation;
            return this;
        }

        public PaymentInformationHeaders build() {
            PaymentInformationHeaders paymentInformationHeaders = new PaymentInformationHeaders();
            paymentInformationHeaders.psUUserAgent = this.psUUserAgent;
            paymentInformationHeaders.digest = this.digest;
            paymentInformationHeaders.psUIPPort = this.psUIPPort;
            paymentInformationHeaders.psUAcceptCharset = this.psUAcceptCharset;
            paymentInformationHeaders.tpPSignatureCertificate = this.tpPSignatureCertificate;
            paymentInformationHeaders.xRequestID = this.xRequestID;
            paymentInformationHeaders.psUHttpMethod = this.psUHttpMethod;
            paymentInformationHeaders.psUAcceptLanguage = this.psUAcceptLanguage;
            paymentInformationHeaders.psUGeoLocation = this.psUGeoLocation;
            paymentInformationHeaders.psUDeviceID = this.psUDeviceID;
            paymentInformationHeaders.psUAccept = this.psUAccept;
            paymentInformationHeaders.signature = this.signature;
            paymentInformationHeaders.psUAcceptEncoding = this.psUAcceptEncoding;
            paymentInformationHeaders.psUIPAddress = this.psUIPAddress;
            return paymentInformationHeaders;
        }
    }
}
