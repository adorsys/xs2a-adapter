package de.adorsys.xs2a.adapter.api;

import java.util.*;

import static java.lang.String.CASE_INSENSITIVE_ORDER;

public class RequestHeaders {
    private static final String BEARER_SPACE = "Bearer ";

    public static final String X_REQUEST_ID = "X-Request-ID";
    public static final String CONSENT_ID = "Consent-ID";
    public static final String DIGEST = "Digest";
    public static final String PSU_ID = "PSU-ID";
    public static final String PSU_CORPORATE_ID = "PSU-Corporate-ID";
    public static final String TPP_REDIRECT_URI = "TPP-Redirect-URI";
    public static final String DATE = "Date";
    public static final String SIGNATURE = "Signature";
    public static final String TPP_SIGNATURE_CERTIFICATE = "TPP-Signature-Certificate";
    public static final String PSU_IP_ADDRESS = "PSU-IP-Address";
    public static final String PSU_ID_TYPE = "PSU-ID-Type";
    public static final String PSU_CORPORATE_ID_TYPE = "PSU-Corporate-ID-Type";
    public static final String TPP_REDIRECT_PREFERRED = "TPP-Redirect-Preferred";
    public static final String TPP_NOK_REDIRECT_URI = "TPP-Nok-Redirect-URI";
    public static final String TPP_EXPLICIT_AUTHORISATION_PREFERRED = "TPP-Explicit-Authorisation-Preferred";
    public static final String PSU_IP_PORT = "PSU-IP-Port";
    public static final String PSU_ACCEPT = "PSU-Accept";
    public static final String PSU_ACCEPT_CHARSET = "PSU-Accept-Charset";
    public static final String PSU_ACCEPT_ENCODING = "PSU-Accept-Encoding";
    public static final String PSU_ACCEPT_LANGUAGE = "PSU-Accept-Language";
    public static final String PSU_USER_AGENT = "PSU-User-Agent";
    public static final String PSU_HTTP_METHOD = "PSU-Http-Method";
    public static final String PSU_DEVICE_ID = "PSU-Device-ID";
    public static final String PSU_GEO_LOCATION = "PSU-Geo-Location";
    // technical
    public static final String ACCEPT = "Accept";
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String AUTHORIZATION = "Authorization";
    public static final String CORRELATION_ID = "Correlation-ID";
    // gateway
    public static final String X_GTW_ASPSP_ID = "X-GTW-ASPSP-ID";
    public static final String X_GTW_BANK_CODE = "X-GTW-Bank-Code";
    public static final String X_GTW_BIC = "X-GTW-BIC";
    public static final String X_GTW_IBAN = "X-GTW-IBAN";

    private static final RequestHeaders EMPTY = new RequestHeaders(Collections.emptyMap());

    private final Map<String, String> headers;

    private RequestHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public static RequestHeaders fromMap(Map<String, String> headersMap) {
        Map<String, String> headers = new TreeMap<>(CASE_INSENSITIVE_ORDER);
        for (Map.Entry<String, String> entry : headersMap.entrySet()) {
            String name = entry.getKey();
            if (CONTENT_TYPE.equalsIgnoreCase(name)) {
                // exclude the content-type header for backward compatibility
                // if set the value will be used in the outgoing request
                // potentially breaking existing integrations
                // that require exact match of the content-type
                continue;
            }
            headers.put(name, entry.getValue());
        }
        return new RequestHeaders(headers);
    }

    public static RequestHeaders empty() {
        return EMPTY;
    }

    public Map<String, String> toMap() {
        return new HashMap<>(headers);
    }

    public boolean isAcceptJson() {
        return Optional.ofNullable(headers.get(ACCEPT))
                   .map(a -> a.toLowerCase().startsWith("application/json"))
                   .orElse(false);
    }

    public Optional<String> get(String headerName) {
        return Optional.ofNullable(headers.get(headerName));
    }

    public Optional<String> getAspspId() {
        return get(X_GTW_ASPSP_ID);
    }

    public Optional<String> getBankCode() {
        return get(X_GTW_BANK_CODE);
    }

    public Optional<String> getBic() {
        return get(X_GTW_BIC);
    }

    public Optional<String> getIban() {
        return get(X_GTW_IBAN);
    }

    public Optional<String> getAuthorization() {
        return get(AUTHORIZATION);
    }

    public Optional<String> getAccessToken() {
        return getAuthorization().flatMap(authorization -> {
            if (!authorization.startsWith(BEARER_SPACE)) {
                return Optional.empty();
            }
            return Optional.of(authorization.substring(BEARER_SPACE.length()));
        });
    }
}
