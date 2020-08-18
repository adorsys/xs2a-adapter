package de.adorsys.xs2a.adapter.serviceloader;

import de.adorsys.xs2a.adapter.service.Oauth2Service;
import de.adorsys.xs2a.adapter.service.model.TokenResponse;

import java.io.IOException;
import java.net.URI;
import java.util.Map;

public class TestOauth2Service implements Oauth2Service {
    @Override
    public URI getAuthorizationRequestUri(Map<String, String> headers,
                                          Parameters parameters) throws IOException {
        return null;
    }

    @Override
    public TokenResponse getToken(Map<String, String> headers,
                                  Parameters parameters) throws IOException {
        return null;
    }
}
