package de.adorsys.xs2a.adapter.api;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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
    public static final String TPP_DECOUPLED_PREFERRED = "TPP-Decoupled-Preferred";
    public static final String TPP_BRAND_LOGGING_INFORMATION = "TPP-Brand-Logging-Information";
    public static final String TPP_NOTIFICATION_URI = "TPP-Notification-URI";
    public static final String TPP_NOTIFICATION_CONTENT_PREFERRED = "TPP-Notification-Content-Preferred";
    public static final String PSU_IP_ADDRESS_MANDATORY = "PSU-IP-Address_mandatory";

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
    // Open Banking Gateway
    public static final String X_OAUTH_PREFERRED = "X-OAUTH-PREFERRED";
    // Crealogix
    public static final String PSD2_AUTHORIZATION = "PSD2-AUTHORIZATION";

    private static final RequestHeaders EMPTY = new RequestHeaders(Collections.emptyMap());

    private static final Map<String, String> headerNamesLowerCased = new HashMap<>();

    static {
        headerNamesLowerCased.put(X_GTW_ASPSP_ID.toLowerCase(), X_GTW_ASPSP_ID);
        headerNamesLowerCased.put(X_GTW_BANK_CODE.toLowerCase(), X_GTW_BANK_CODE);
        headerNamesLowerCased.put(X_GTW_BIC.toLowerCase(), X_GTW_BIC);
        headerNamesLowerCased.put(X_GTW_IBAN.toLowerCase(), X_GTW_IBAN);
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
        headerNamesLowerCased.put(AUTHORIZATION.toLowerCase(), AUTHORIZATION);
        headerNamesLowerCased.put(CORRELATION_ID.toLowerCase(), CORRELATION_ID);
        headerNamesLowerCased.put(X_OAUTH_PREFERRED.toLowerCase(), X_OAUTH_PREFERRED);
        headerNamesLowerCased.put(PSD2_AUTHORIZATION.toLowerCase(), PSD2_AUTHORIZATION);
        headerNamesLowerCased.put(TPP_DECOUPLED_PREFERRED.toLowerCase(), TPP_DECOUPLED_PREFERRED);
        headerNamesLowerCased.put(TPP_BRAND_LOGGING_INFORMATION.toLowerCase(), TPP_BRAND_LOGGING_INFORMATION);
        headerNamesLowerCased.put(TPP_NOTIFICATION_URI.toLowerCase(), TPP_NOTIFICATION_URI);
        headerNamesLowerCased.put(TPP_NOTIFICATION_CONTENT_PREFERRED.toLowerCase(), TPP_NOTIFICATION_CONTENT_PREFERRED);
        headerNamesLowerCased.put(PSU_IP_ADDRESS_MANDATORY.toLowerCase(), PSU_IP_ADDRESS_MANDATORY);
    }

    private final Map<String, String> headers;

    private RequestHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public static RequestHeaders fromMap(Map<String, String> headersMap) {
        Map<String, String> headers = new HashMap<>();
        headersMap.forEach((name, value) -> {
            String headerNameInLowerCase = name.toLowerCase();
            if (headerNamesLowerCased.containsKey(headerNameInLowerCase)) {
                headers.put(headerNamesLowerCased.get(headerNameInLowerCase), value);
            }
        });
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
        return Optional.ofNullable(headers.get(headerNamesLowerCased.get(headerName.toLowerCase())));
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
