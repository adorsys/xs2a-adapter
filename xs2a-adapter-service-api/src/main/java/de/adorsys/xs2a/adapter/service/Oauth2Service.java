package de.adorsys.xs2a.adapter.service;

import de.adorsys.xs2a.adapter.service.model.TokenResponse;

import java.io.IOException;
import java.net.URI;
import java.util.Map;

public interface Oauth2Service {
    // https://tools.ietf.org/html/rfc6749#section-4.1.1
    URI getAuthorizationRequestUri(Map<String, String> headers, Parameters parameters) throws IOException;

    TokenResponse getToken(Map<String, String> headers, Parameters parameters) throws IOException;

    class Parameters {
        public static final String CODE = "code";
        public static final String REDIRECT_URI = "redirect_uri";
        public static final String CLIENT_ID = "client_id";
        public static final String GRANT_TYPE = "grant_type";
        public static final String CODE_VERIFIER = "code_verifier";
        public static final String STATE = "state";
        public static final String SCA_OAUTH_LINK = "sca_oauth_link";
        public static final String BIC = "bic";
        public static final String SCOPE = "scope";
        public static final String RESPONSE_TYPE = "response_type";
        public static final String CODE_CHALLENGE = "code_challenge";
        public static final String CODE_CHALLENGE_METHOD = "code_challenge_method";
        public static final String REFRESH_TOKEN = "refresh_token";

        private final Map<String, String> parameters;

        public Parameters(Map<String, String> parameters) {
            this.parameters = parameters;
        }

        public Map<String, String> asMap() {
            return parameters;
        }

        public String get(String key) {
            return parameters.get(key);
        }

        public void set(String key, String value) {
            parameters.put(key, value);
        }

        public String getAuthorizationCode() {
            return get(CODE);
        }

        public void setAuthorizationCode(String value) {
            set(CODE, value);
        }

        public String getRedirectUri() {
            return get(REDIRECT_URI);
        }

        public void setRedirectUri(String value) {
            set(REDIRECT_URI, value);
        }

        public String getClientId() {
            return get(CLIENT_ID);
        }

        public void setClientId(String value) {
            set(CLIENT_ID, value);
        }

        public String getGrantType() {
            return get(GRANT_TYPE);
        }

        public void setGrantType(String value) {
            set(GRANT_TYPE, value);
        }

        public String getCodeVerifier() {
            return get(CODE_VERIFIER);
        }

        public void setCodeVerifier(String value) {
            set(CODE_VERIFIER, value);
        }

        public String getState() {
            return get(STATE);
        }

        public void setState(String value) {
            set(STATE, value);
        }

        public String getScaOAuthLink() {
            return get(SCA_OAUTH_LINK);
        }

        public void setScaOAuthLink(String value) {
            set(SCA_OAUTH_LINK, value);
        }

        public String getBic() {
            return get(BIC);
        }

        public void setBic(String value) {
            set(BIC, value);
        }

        public String getScope() {
            return get(SCOPE);
        }

        public void setScope(String value) {
            set(SCOPE, value);
        }

        public String getResponseType() {
            return get(RESPONSE_TYPE);
        }

        public void setResponseType(String value) {
            set(RESPONSE_TYPE, value);
        }

        public String getCodeChallenge() {
            return get(CODE_CHALLENGE);
        }

        public void setCodeChallenge(String value) {
            set(CODE_CHALLENGE, value);
        }

        public String getCodeChallengeMethod() {
            return get(CODE_CHALLENGE_METHOD);
        }

        public void setCodeChallengeMethod(String value) {
            set(CODE_CHALLENGE_METHOD, value);
        }

        public String getRefreshToken() {
            return get(REFRESH_TOKEN);
        }

        public void setRefreshToken(String value) {
            set(REFRESH_TOKEN, value);
        }
    }
}
