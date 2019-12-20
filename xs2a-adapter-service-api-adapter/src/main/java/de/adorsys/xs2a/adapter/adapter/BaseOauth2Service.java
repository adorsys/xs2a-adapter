package de.adorsys.xs2a.adapter.adapter;

import de.adorsys.xs2a.adapter.adapter.oauth2.api.model.AuthorisationServerMetaData;
import de.adorsys.xs2a.adapter.http.HttpClient;
import de.adorsys.xs2a.adapter.http.ResponseHandlers;
import de.adorsys.xs2a.adapter.http.UriBuilder;
import de.adorsys.xs2a.adapter.service.Oauth2Service;
import de.adorsys.xs2a.adapter.service.model.Aspsp;
import de.adorsys.xs2a.adapter.service.model.TokenResponse;

import java.io.IOException;
import java.net.URI;
import java.util.Map;

public class BaseOauth2Service implements Oauth2Service {

    private final HttpClient httpClient;
    private final Aspsp aspsp;

    public BaseOauth2Service(Aspsp aspsp, HttpClient httpClient) {
        this.httpClient = httpClient;
        this.aspsp = aspsp;
    }

    @Override
    public URI getAuthorizationRequestUri(Map<String, String> headers, Parameters parameters) throws IOException {

        return UriBuilder.fromUri(getAuthorizationEndpoint(parameters.getScaOAuthLink()))
            .queryParam(Parameters.STATE, parameters.getState())
            .queryParam(Parameters.REDIRECT_URI, parameters.getRedirectUri())
            .build();
    }

    private String getAuthorizationEndpoint(String scaOAuthLink) {
        String metadataUri = scaOAuthLink != null ? scaOAuthLink : aspsp.getIdpUrl();
        AuthorisationServerMetaData metadata = getAuthorizationServerMetadata(metadataUri);
        return metadata.getAuthorisationEndpoint();
    }

    @Override
    public TokenResponse getToken(Map<String, String> headers, Parameters parameters) throws IOException {
        throw new UnsupportedOperationException();
    }

    private AuthorisationServerMetaData getAuthorizationServerMetadata(String uri) {
        return httpClient.get(uri)
            .send(ResponseHandlers.jsonResponseHandler(AuthorisationServerMetaData.class))
            .getBody();
    }
}
