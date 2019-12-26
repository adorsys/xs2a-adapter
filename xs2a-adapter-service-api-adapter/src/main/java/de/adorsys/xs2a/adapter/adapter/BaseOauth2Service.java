package de.adorsys.xs2a.adapter.adapter;

import de.adorsys.xs2a.adapter.adapter.mapper.TokenResponseMapper;
import de.adorsys.xs2a.adapter.adapter.model.OauthToken;
import de.adorsys.xs2a.adapter.adapter.oauth2.api.model.AuthorisationServerMetaData;
import de.adorsys.xs2a.adapter.http.HttpClient;
import de.adorsys.xs2a.adapter.http.ResponseHandlers;
import de.adorsys.xs2a.adapter.http.UriBuilder;
import de.adorsys.xs2a.adapter.service.Oauth2Service;
import de.adorsys.xs2a.adapter.service.Response;
import de.adorsys.xs2a.adapter.service.model.Aspsp;
import de.adorsys.xs2a.adapter.service.model.TokenResponse;
import org.mapstruct.factory.Mappers;

import java.io.IOException;
import java.net.URI;
import java.util.Map;

import static de.adorsys.xs2a.adapter.http.ResponseHandlers.jsonResponseHandler;

public class BaseOauth2Service implements Oauth2Service {

    private final HttpClient httpClient;
    private final Aspsp aspsp;
    private final TokenResponseMapper tokenResponseMapper = Mappers.getMapper(TokenResponseMapper.class);
    private final String authorizationEndpoint;
    private final String tokenEndpoint;

    public BaseOauth2Service(Aspsp aspsp, HttpClient httpClient, String authorizationEndpoint, String tokenEndpoint) {
        this.httpClient = httpClient;
        this.aspsp = aspsp;
        this.authorizationEndpoint = authorizationEndpoint;
        this.tokenEndpoint = tokenEndpoint;
    }

    public BaseOauth2Service(Aspsp aspsp, HttpClient httpClient) {
        this(aspsp, httpClient, null, null);
    }

    @Override
    public URI getAuthorizationRequestUri(Map<String, String> headers, Parameters parameters) throws IOException {

        return UriBuilder.fromUri(getAuthorizationEndpoint(parameters))
            .queryParam(Parameters.RESPONSE_TYPE, ResponseType.CODE.toString())
            .queryParam(Parameters.STATE, parameters.getState())
            .queryParam(Parameters.REDIRECT_URI, parameters.getRedirectUri())
            .build();
    }

    private String getAuthorizationEndpoint(Parameters parameters) {
        if (authorizationEndpoint != null) {
            return authorizationEndpoint;
        }
        AuthorisationServerMetaData metadata = getAuthorizationServerMetadata(parameters);
        return metadata.getAuthorisationEndpoint();
    }

    private AuthorisationServerMetaData getAuthorizationServerMetadata(Parameters parameters) {
        String scaOAuthLink = parameters.removeScaOAuthLink();
        String metadataUri = scaOAuthLink != null ? scaOAuthLink : aspsp.getIdpUrl();
        return httpClient.get(metadataUri)
            .send(ResponseHandlers.jsonResponseHandler(AuthorisationServerMetaData.class))
            .getBody();
    }

    @Override
    public TokenResponse getToken(Map<String, String> headers, Parameters parameters) throws IOException {

        Response<OauthToken> response = httpClient.post(getTokenEndpoint(parameters))
            .urlEncodedBody(parameters.asMap())
            .send(jsonResponseHandler(OauthToken.class));
        return tokenResponseMapper.map(response.getBody());
    }

    private String getTokenEndpoint(Parameters parameters) {
        if (tokenEndpoint != null) {
            return tokenEndpoint;
        }
        return getAuthorizationServerMetadata(parameters).getTokenEndpoint();
    }
}
