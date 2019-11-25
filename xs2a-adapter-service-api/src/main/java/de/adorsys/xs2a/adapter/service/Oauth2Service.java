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
            return get("code");
        }

        public void setAuthorizationCode(String value) {
            set("code", value);
        }

        public String getRedirectUri() {
            return get("redirect_uri");
        }

        public void setRedirectUri(String value) {
            set("redirect_uri", value);
        }

        public String getClientId() {
            return get("client_id");
        }

        public void setClientId(String value) {
            set("client_id", value);
        }

        public String getGrantType() {
            return get("grant_type");
        }

        public void setGrantType(String value) {
            set("grant_type", value);
        }

        public String getCodeVerifier() {
            return get("code_verifier");
        }

        public void setCodeVerifier(String value) {
            set("code_verifier", value);
        }

        public String getState() {
            return get("state");
        }

        public void setState(String value) {
            set("state", value);
        }

        public String getScaOAuthLink() {
            return get("sca_oauth_link");
        }

        public void setScaOAuthLink(String value) {
            set("sca_oauth_link", value);
        }
    }
}
