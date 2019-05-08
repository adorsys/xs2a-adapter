package de.adorsys.xs2a.gateway.service;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RequestHeaders {
    public static final String X_GTW_BANK_CODE = "X-GTW-Bank-Code";
    public static final String X_REQUEST_ID = "X-Request-ID";
    public static final String CONSENT_ID = "Consent-ID";
    public static final String DIGEST = "Digest";
    public static final String PSU_ID = "PSU-ID";
    public static final String PSU_CORPORATE_ID = "PSU-Corporate-ID";
    public static final String TPP_REDIRECT_URI = "TPP-Redirect-URI";
    public static final String DATE = "Date";
    public static final String SIGNATURE = "Signature";
    public static final String TPP_SIGNATURE_CERTIFICATE = "TPP-Signature-Certificate";
    private static final String PSU_IP_ADDRESS = "PSU-IP-Address";
    private static final String PSU_ID_TYPE = "PSU-ID-Type";
    private static final String PSU_CORPORATE_ID_TYPE = "PSU-Corporate-ID-Type";
    private static final String TPP_REDIRECT_PREFERRED = "TPP-Redirect-Preferred";
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
    // technical
    private static final String ACCEPT = "Accept";

    private static Map<String, String> headerNamesLowerCased = new HashMap<>();

    static {
        headerNamesLowerCased.put(X_GTW_BANK_CODE.toLowerCase(), X_GTW_BANK_CODE);
        headerNamesLowerCased.put(X_REQUEST_ID.toLowerCase(), X_REQUEST_ID);
        headerNamesLowerCased.put(PSU_IP_ADDRESS.toLowerCase(), PSU_IP_ADDRESS);
        headerNamesLowerCased.put(DIGEST.toLowerCase(), DIGEST);
        headerNamesLowerCased.put(SIGNATURE.toLowerCase(), SIGNATURE);
        headerNamesLowerCased.put(TPP_SIGNATURE_CERTIFICATE.toLowerCase(), TPP_SIGNATURE_CERTIFICATE);
        headerNamesLowerCased.put(PSU_ID.toLowerCase(), PSU_ID);
        headerNamesLowerCased.put(PSU_ID_TYPE.toLowerCase(), PSU_ID_TYPE);
        headerNamesLowerCased.put(PSU_CORPORATE_ID.toLowerCase(), PSU_CORPORATE_ID);
        headerNamesLowerCased.put(PSU_CORPORATE_ID_TYPE.toLowerCase(), PSU_CORPORATE_ID_TYPE);
        headerNamesLowerCased.put(CONSENT_ID.toLowerCase(), CONSENT_ID);
        headerNamesLowerCased.put(TPP_REDIRECT_PREFERRED.toLowerCase(), TPP_REDIRECT_PREFERRED);
        headerNamesLowerCased.put(TPP_REDIRECT_URI.toLowerCase(), TPP_REDIRECT_URI);
        headerNamesLowerCased.put(TPP_NOK_REDIRECT_URI.toLowerCase(), TPP_NOK_REDIRECT_URI);
        headerNamesLowerCased.put(TPP_EXPLICIT_AUTHORISATION_PREFERRED.toLowerCase(), TPP_EXPLICIT_AUTHORISATION_PREFERRED);
        headerNamesLowerCased.put(PSU_IP_PORT.toLowerCase(), PSU_IP_PORT);
        headerNamesLowerCased.put(PSU_ACCEPT.toLowerCase(), PSU_ACCEPT);
        headerNamesLowerCased.put(PSU_ACCEPT_CHARSET.toLowerCase(), PSU_ACCEPT_CHARSET);
        headerNamesLowerCased.put(PSU_ACCEPT_ENCODING.toLowerCase(), PSU_ACCEPT_ENCODING);
        headerNamesLowerCased.put(PSU_ACCEPT_LANGUAGE.toLowerCase(), PSU_ACCEPT_LANGUAGE);
        headerNamesLowerCased.put(PSU_USER_AGENT.toLowerCase(), PSU_USER_AGENT);
        headerNamesLowerCased.put(PSU_HTTP_METHOD.toLowerCase(), PSU_HTTP_METHOD);
        headerNamesLowerCased.put(PSU_DEVICE_ID.toLowerCase(), PSU_DEVICE_ID);
        headerNamesLowerCased.put(PSU_GEO_LOCATION.toLowerCase(), PSU_GEO_LOCATION);
        headerNamesLowerCased.put(ACCEPT.toLowerCase(), ACCEPT);
    }

    private Map<String, String> headers;

    private RequestHeaders() {
    }

    private RequestHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public static HeadersBuilder builder() {
        return new HeadersBuilder();
    }

    public static RequestHeaders fromMap(Map<String, String> headersMap) {
        Map<String, String> headers = new HashMap<>();
        headersMap.forEach((name, value) -> {
            String headerNameInLowerCase = name.toLowerCase();
            if (headerNamesLowerCased.keySet().contains(headerNameInLowerCase)) {
                headers.put(headerNamesLowerCased.get(headerNameInLowerCase), value);
            }
        });
        return new RequestHeaders(headers);
    }

    public Map<String, String> toMap() {
        if (headers == null) {
            headers = new HashMap<>();
        }
        return new HashMap<>(headers);
    }

    public boolean isAcceptJson() {
        return "application/json".equalsIgnoreCase(headers.get(ACCEPT));
    }

    public String removeBankCode() {
        if (headers != null) {
            return headers.remove(X_GTW_BANK_CODE);
        }

        return "";
    }

    public static final class HeadersBuilder {
        private Map<String, String> headers;

        private HeadersBuilder() {
            headers = new HashMap<>();
        }

        public HeadersBuilder bankCode(String bankCode) {
            putIntoMap(X_GTW_BANK_CODE, bankCode);
            return this;
        }

        public HeadersBuilder xRequestId(UUID xRequestId) {
            putIntoMap(X_REQUEST_ID, xRequestId);
            return this;
        }

        public HeadersBuilder psuIpAddress(String psuIpAddress) {
            putIntoMap(PSU_IP_ADDRESS, psuIpAddress);
            return this;
        }

        public HeadersBuilder digest(String digest) {
            putIntoMap(DIGEST, digest);
            return this;
        }

        public HeadersBuilder signature(String signature) {
            putIntoMap(SIGNATURE, signature);
            return this;
        }

        public HeadersBuilder tppSignatureCertificate(byte[] tppSignatureCertificate) {
            if (tppSignatureCertificate != null) {
                putIntoMap(TPP_SIGNATURE_CERTIFICATE, Base64.getEncoder().encodeToString(tppSignatureCertificate));
            }
            return this;
        }

        public HeadersBuilder psuId(String psuId) {
            putIntoMap(PSU_ID, psuId);
            return this;
        }

        public HeadersBuilder psuIdType(String psuIdType) {
            putIntoMap(PSU_ID_TYPE, psuIdType);
            return this;
        }

        public HeadersBuilder psuCorporateId(String psuCorporateId) {
            putIntoMap(PSU_CORPORATE_ID, psuCorporateId);
            return this;
        }

        public HeadersBuilder psuCorporateIdType(String psuCorporateIdType) {
            putIntoMap(PSU_CORPORATE_ID_TYPE, psuCorporateIdType);
            return this;
        }

        public HeadersBuilder consentId(String consentId) {
            putIntoMap(CONSENT_ID, consentId);
            return this;
        }

        public HeadersBuilder tppRedirectPreferred(Boolean tppRedirectPreferred) {
            putIntoMap(TPP_REDIRECT_PREFERRED, tppRedirectPreferred);
            return this;
        }

        public HeadersBuilder tppRedirectUri(String tppRedirectUri) {
            putIntoMap(TPP_REDIRECT_URI, tppRedirectUri);
            return this;
        }

        public HeadersBuilder tppNokRedirectUri(String tppNokRedirectUri) {
            putIntoMap(TPP_NOK_REDIRECT_URI, tppNokRedirectUri);
            return this;
        }

        public HeadersBuilder tppExplicitAuthorisationPreferred(Boolean tppExplicitAuthorisationPreferred) {
            putIntoMap(TPP_EXPLICIT_AUTHORISATION_PREFERRED, tppExplicitAuthorisationPreferred);
            return this;
        }

        public HeadersBuilder psuIpPort(String psuIpPort) {
            putIntoMap(PSU_IP_PORT, psuIpPort);
            return this;
        }

        public HeadersBuilder psuAccept(String psuAccept) {
            putIntoMap(PSU_ACCEPT, psuAccept);
            return this;
        }

        public HeadersBuilder psuAcceptCharset(String psuAcceptCharset) {
            putIntoMap(PSU_ACCEPT_CHARSET, psuAcceptCharset);
            return this;
        }

        public HeadersBuilder psuAcceptEncoding(String psuAcceptEncoding) {
            putIntoMap(PSU_ACCEPT_ENCODING, psuAcceptEncoding);
            return this;
        }

        public HeadersBuilder psuAcceptLanguage(String psuAcceptLanguage) {
            putIntoMap(PSU_ACCEPT_LANGUAGE, psuAcceptLanguage);
            return this;
        }

        public HeadersBuilder psuUserAgent(String psuUserAgent) {
            putIntoMap(PSU_USER_AGENT, psuUserAgent);
            return this;
        }

        public HeadersBuilder psuHttpMethod(String psuHttpMethod) {
            putIntoMap(PSU_HTTP_METHOD, psuHttpMethod);
            return this;
        }

        public HeadersBuilder psuDeviceId(UUID psuDeviceId) {
            putIntoMap(PSU_DEVICE_ID, psuDeviceId);
            return this;
        }

        public HeadersBuilder psuGeoLocation(String psuGeoLocation) {
            putIntoMap(PSU_GEO_LOCATION, psuGeoLocation);
            return this;
        }

        public HeadersBuilder accept(String accept) {
            putIntoMap(ACCEPT, accept);
            return this;
        }

        public RequestHeaders build() {
            return new RequestHeaders(headers);
        }

        private void putIntoMap(String headerName, Object headerValue) {
            if (headerValue != null) {
                headers.put(headerName, headerValue.toString());
            }
        }
    }
}
