package de.adorsys.xs2a.adapter.service;

import de.adorsys.xs2a.adapter.service.model.TokenResponse;

import java.io.IOException;
import java.net.URI;
import java.util.Map;

public interface Oauth2Service {
    // https://tools.ietf.org/html/rfc6749#section-4.1.1
    URI getAuthorizationRequestUri(Map<String, String> headers, String state, URI redirectUri) throws IOException;

    TokenResponse getToken(Map<String, String> headers,
                           String authorizationCode,
                           URI redirectUri,
                           String clientId) throws IOException;
}
