package de.adorsys.xs2a.gateway.service;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Headers {
    private static final String X_REQUEST_ID = "X-Request-ID";
    private static final String PSU_IP_ADDRESS = "PSU-IP-Address";
    private static final String DIGEST = "Digest";
    private static final String SIGNATURE = "Signature";
    private static final String TPP_SIGNATURE_CERTIFICATE = "TPP-Signature-Certificate";
    private static final String PSU_ID = "PSU-ID";
    private static final String PSU_ID_TYPE = "PSU-ID-Type";
    private static final String PSU_CORPORATE_ID = "PSU-Corporate-ID";
    private static final String PSU_CORPORATE_ID_TYPE = "PSU-Corporate-ID-Type";
    private static final String CONSENT_ID = "Consent-ID";
    private static final String TPP_REDIRECT_PREFERRED = "TPP-Redirect-Preferred";
    private static final String TPP_REDIRECT_URI = "TPP-Redirect-URI";
    private static final String TPP_NOK_REDIRECT_URI = "TPP-Nok-Redirect-URI";
    private static final String TPP_EXPLICIT_AUTHORISATION_PREFERRED = "TPP-Explicit-Authorisation-Preferred";
    private static final String PSU_IP_PORT = "PSU-IP-Port";
    private static final String PSU_ACCEPT = "PSU-Accept";
    private static final String PSU_ACCEPT_CHARSET = "PSU-Accept-Charset";
    private static final String PSU_ACCEPT_ENCODING = "PSU-Accept-Encoding";
    private static final String PSU_ACCEPT_LANGUAGE = "PSU-Accept-Language";
    private static final String PSU_USER_AGENT = "PSU-User-Agent";
    private static final String PSU_HTTP_METHOD = "PSU-Http-Method";
    private static final String PSU_DEVICE_ID = "PSU-Device-ID";
    private static final String PSU_GEO_LOCATION = "PSU-Geo-Location";

    private Map<String, String> headers;

    private UUID xRequestId;
    private String psuIpAddress;
    private String digest;
    private String signature;
    private byte[] tppSignatureCertificate;
    private String psuId;
    private String psuIdType;
    private String psuCorporateId;
    private String psuCorporateIdType;
    private String consentId;
    private Boolean tppRedirectPreferred;
    private String tppRedirectUri;
    private String tppNokRedirectUri;
    private Boolean tppExplicitAuthorisationPreferred;
    private String psuIpPort;
    private String psuAccept;
    private String psuAcceptCharset;
    private String psuAcceptEncoding;
    private String psuAcceptLanguage;
    private String psuUserAgent;
    private String psuHttpMethod;
    private UUID psuDeviceId;
    private String psuGeoLocation;

    private Headers() {
    }

    public static HeadersBuilder builder() {
        return new HeadersBuilder();
    }

    public Map<String, String> toMap() {
        if (headers == null) {
            headers = new HashMap<>();

            putIntoAs(xRequestId, headers, X_REQUEST_ID);
            putIntoAs(psuIpAddress, headers, PSU_IP_ADDRESS);
            putIntoAs(digest, headers, DIGEST);
            putIntoAs(signature, headers, SIGNATURE);
            if (tppSignatureCertificate != null) {
                headers.put(TPP_SIGNATURE_CERTIFICATE, Base64.getEncoder().encodeToString(tppSignatureCertificate));
            }
            putIntoAs(psuId, headers, PSU_ID);
            putIntoAs(psuIdType, headers, PSU_ID_TYPE);
            putIntoAs(psuCorporateId, headers, PSU_CORPORATE_ID);
            putIntoAs(psuCorporateIdType, headers, PSU_CORPORATE_ID_TYPE);
            putIntoAs(consentId, headers, CONSENT_ID);
            putIntoAs(tppRedirectPreferred, headers, TPP_REDIRECT_PREFERRED);
            putIntoAs(tppRedirectUri, headers, TPP_REDIRECT_URI);
            putIntoAs(tppNokRedirectUri, headers, TPP_NOK_REDIRECT_URI);
            putIntoAs(tppExplicitAuthorisationPreferred, headers, TPP_EXPLICIT_AUTHORISATION_PREFERRED);
            putIntoAs(psuIpPort, headers, PSU_IP_PORT);
            putIntoAs(psuAccept, headers, PSU_ACCEPT);
            putIntoAs(psuAcceptCharset, headers, PSU_ACCEPT_CHARSET);
            putIntoAs(psuAcceptEncoding, headers, PSU_ACCEPT_ENCODING);
            putIntoAs(psuAcceptLanguage, headers, PSU_ACCEPT_LANGUAGE);
            putIntoAs(psuUserAgent, headers, PSU_USER_AGENT);
            putIntoAs(psuHttpMethod, headers, PSU_HTTP_METHOD);
            putIntoAs(psuDeviceId, headers, PSU_DEVICE_ID);
            putIntoAs(psuGeoLocation, headers, PSU_GEO_LOCATION);
        }

        return new HashMap<>(headers);
    }

    private void putIntoAs(Object headerValue, Map<String, String> headers, String headerName) {
        if (headerValue != null) {
            headers.put(headerName, headerValue.toString());
        }
    }

    public static final class HeadersBuilder {
        private UUID xRequestId;
        private String psuIpAddress;
        private String digest;
        private String signature;
        private byte[] tppSignatureCertificate;
        private String psuId;
        private String psuIdType;
        private String psuCorporateId;
        private String psuCorporateIdType;
        private String consentId;
        private Boolean tppRedirectPreferred;
        private String tppRedirectUri;
        private String tppNokRedirectUri;
        private Boolean tppExplicitAuthorisationPreferred;
        private String psuIpPort;
        private String psuAccept;
        private String psuAcceptCharset;
        private String psuAcceptEncoding;
        private String psuAcceptLanguage;
        private String psuUserAgent;
        private String psuHttpMethod;
        private UUID psuDeviceId;
        private String psuGeoLocation;

        private HeadersBuilder() {
        }

        public HeadersBuilder xRequestId(UUID xRequestId) {
            this.xRequestId = xRequestId;
            return this;
        }

        public HeadersBuilder psuIpAddress(String psuIpAddress) {
            this.psuIpAddress = psuIpAddress;
            return this;
        }

        public HeadersBuilder digest(String digest) {
            this.digest = digest;
            return this;
        }

        public HeadersBuilder signature(String signature) {
            this.signature = signature;
            return this;
        }

        public HeadersBuilder tppSignatureCertificate(byte[] tppSignatureCertificate) {
            this.tppSignatureCertificate = tppSignatureCertificate;
            return this;
        }

        public HeadersBuilder psuId(String psuId) {
            this.psuId = psuId;
            return this;
        }

        public HeadersBuilder psuIdType(String psuIdType) {
            this.psuIdType = psuIdType;
            return this;
        }

        public HeadersBuilder psuCorporateId(String psuCorporateId) {
            this.psuCorporateId = psuCorporateId;
            return this;
        }

        public HeadersBuilder psuCorporateIdType(String psuCorporateIdType) {
            this.psuCorporateIdType = psuCorporateIdType;
            return this;
        }

        public HeadersBuilder consentId(String consentId) {
            this.consentId = consentId;
            return this;
        }

        public HeadersBuilder tppRedirectPreferred(Boolean tppRedirectPreferred) {
            this.tppRedirectPreferred = tppRedirectPreferred;
            return this;
        }

        public HeadersBuilder tppRedirectUri(String tppRedirectUri) {
            this.tppRedirectUri = tppRedirectUri;
            return this;
        }

        public HeadersBuilder tppNokRedirectUri(String tppNokRedirectUri) {
            this.tppNokRedirectUri = tppNokRedirectUri;
            return this;
        }

        public HeadersBuilder tppExplicitAuthorisationPreferred(Boolean tppExplicitAuthorisationPreferred) {
            this.tppExplicitAuthorisationPreferred = tppExplicitAuthorisationPreferred;
            return this;
        }

        public HeadersBuilder psuIpPort(String psuIpPort) {
            this.psuIpPort = psuIpPort;
            return this;
        }

        public HeadersBuilder psuAccept(String psuAccept) {
            this.psuAccept = psuAccept;
            return this;
        }

        public HeadersBuilder psuAcceptCharset(String psuAcceptCharset) {
            this.psuAcceptCharset = psuAcceptCharset;
            return this;
        }

        public HeadersBuilder psuAcceptEncoding(String psuAcceptEncoding) {
            this.psuAcceptEncoding = psuAcceptEncoding;
            return this;
        }

        public HeadersBuilder psuAcceptLanguage(String psuAcceptLanguage) {
            this.psuAcceptLanguage = psuAcceptLanguage;
            return this;
        }

        public HeadersBuilder psuUserAgent(String psuUserAgent) {
            this.psuUserAgent = psuUserAgent;
            return this;
        }

        public HeadersBuilder psuHttpMethod(String psuHttpMethod) {
            this.psuHttpMethod = psuHttpMethod;
            return this;
        }

        public HeadersBuilder psuDeviceId(UUID psuDeviceId) {
            this.psuDeviceId = psuDeviceId;
            return this;
        }

        public HeadersBuilder psuGeoLocation(String psuGeoLocation) {
            this.psuGeoLocation = psuGeoLocation;
            return this;
        }

        public Headers build() {
            Headers headers = new Headers();
            headers.psuCorporateId = this.psuCorporateId;
            headers.tppRedirectUri = this.tppRedirectUri;
            headers.psuHttpMethod = this.psuHttpMethod;
            headers.psuGeoLocation = this.psuGeoLocation;
            headers.psuIpPort = this.psuIpPort;
            headers.psuAcceptEncoding = this.psuAcceptEncoding;
            headers.psuCorporateIdType = this.psuCorporateIdType;
            headers.xRequestId = this.xRequestId;
            headers.psuIpAddress = this.psuIpAddress;
            headers.signature = this.signature;
            headers.tppExplicitAuthorisationPreferred = this.tppExplicitAuthorisationPreferred;
            headers.psuDeviceId = this.psuDeviceId;
            headers.tppSignatureCertificate = this.tppSignatureCertificate;
            headers.digest = this.digest;
            headers.tppNokRedirectUri = this.tppNokRedirectUri;
            headers.psuAcceptCharset = this.psuAcceptCharset;
            headers.consentId = this.consentId;
            headers.psuAcceptLanguage = this.psuAcceptLanguage;
            headers.tppRedirectPreferred = this.tppRedirectPreferred;
            headers.psuUserAgent = this.psuUserAgent;
            headers.psuIdType = this.psuIdType;
            headers.psuId = this.psuId;
            headers.psuAccept = this.psuAccept;
            return headers;
        }
    }
}
