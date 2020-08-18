package de.adorsys.xs2a.adapter.serviceloader;

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
    public URI getAuthorizationRequestUri(Map<String, String> headers, Parameters parameters) throws IOException {
        return getOauth2Service(headers)
            .getAuthorizationRequestUri(headers, parameters);
    }

    private Oauth2Service getOauth2Service(Map<String, String> headers) {
        return adapterServiceLoader.getOauth2Service(RequestHeaders.fromMap(headers));
    }

    @Override
    public TokenResponse getToken(Map<String, String> headers, Parameters parameters) throws IOException {
        return getOauth2Service(headers)
            .getToken(headers, parameters);
    }
}
