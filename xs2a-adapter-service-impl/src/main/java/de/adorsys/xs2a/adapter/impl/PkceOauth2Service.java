package de.adorsys.xs2a.adapter.impl;

import de.adorsys.xs2a.adapter.api.Oauth2Service;
import de.adorsys.xs2a.adapter.api.PkceOauth2Extension;
import de.adorsys.xs2a.adapter.api.model.TokenResponse;
import de.adorsys.xs2a.adapter.impl.http.UriBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.Map;

/**
 * @see <a href="https://tools.ietf.org/html/rfc7636">Proof Key for Code Exchange</a>
 */
public class PkceOauth2Service implements Oauth2Service, PkceOauth2Extension {

    private final Oauth2Service oauth2Service;

    public PkceOauth2Service(Oauth2Service oauth2Service) {
        this.oauth2Service = oauth2Service;
    }

    @Override
    public URI getAuthorizationRequestUri(Map<String, String> headers, Parameters parameters) throws IOException {
        return UriBuilder.fromUri(oauth2Service.getAuthorizationRequestUri(headers, parameters))
            .queryParam(Parameters.CODE_CHALLENGE_METHOD, orElse(parameters.getCodeChallengeMethod(), "S256"))
            .queryParam(Parameters.CODE_CHALLENGE, orElse(parameters.getCodeChallenge(), this.codeChallenge()))
            .build();
    }

    @Override
    public TokenResponse getToken(Map<String, String> headers, Parameters parameters) throws IOException {
        if (GrantType.AUTHORIZATION_CODE.toString().equals(parameters.getGrantType())) {
            parameters.setCodeVerifier(orElse(parameters.getCodeVerifier(), this.codeVerifier()));
        }
        return oauth2Service.getToken(headers, parameters);
    }

    private String orElse(String optionalValue, String defaultValue) {
        if (optionalValue != null) {
            return optionalValue;
        }
        return defaultValue;
    }
}
