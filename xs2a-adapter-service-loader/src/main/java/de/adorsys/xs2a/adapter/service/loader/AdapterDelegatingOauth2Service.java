package de.adorsys.xs2a.adapter.service.loader;

import de.adorsys.xs2a.adapter.service.Oauth2Service;
import de.adorsys.xs2a.adapter.service.RequestHeaders;
import de.adorsys.xs2a.adapter.service.model.TokenResponse;

import java.io.IOException;
import java.net.URI;
import java.util.Map;

public class AdapterDelegatingOauth2Service implements Oauth2Service {

    private final AdapterServiceLoader adapterServiceLoader;

    public AdapterDelegatingOauth2Service(AdapterServiceLoader adapterServiceLoader) {
        this.adapterServiceLoader = adapterServiceLoader;
    }

    @Override
    public URI getAuthorizationRequestUri(Map<String, String> headers,
                                          String state,
                                          URI redirectUri) throws IOException {
        // fixme headers parameter is needed only for service loading
        return getOauth2Service(headers)
            .getAuthorizationRequestUri(headers, state, redirectUri);
    }

    private Oauth2Service getOauth2Service(Map<String, String> headers) {
        return adapterServiceLoader.getOauth2Service(RequestHeaders.fromMap(headers));
    }

    @Override
    public TokenResponse getToken(Map<String, String> headers,
                                  String authorizationCode,
                                  URI redirectUri,
                                  String clientId) throws IOException {
        return getOauth2Service(headers)
            .getToken(headers, authorizationCode, redirectUri, clientId);
    }
}
