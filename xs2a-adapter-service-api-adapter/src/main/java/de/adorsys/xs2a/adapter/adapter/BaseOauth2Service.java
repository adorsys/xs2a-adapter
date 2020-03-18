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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.util.Map;

import static de.adorsys.xs2a.adapter.http.ResponseHandlers.jsonResponseHandler;

public class BaseOauth2Service implements Oauth2Service {
    private static final Logger logger = LoggerFactory.getLogger(BaseOauth2Service.class);

    private final HttpClient httpClient;
    private final Aspsp aspsp;
    private final TokenResponseMapper tokenResponseMapper = Mappers.getMapper(TokenResponseMapper.class);

    public BaseOauth2Service(Aspsp aspsp, HttpClient httpClient) {
        this.httpClient = httpClient;
        this.aspsp = aspsp;
    }

    @Override
    public URI getAuthorizationRequestUri(Map<String, String> headers, Parameters parameters) throws IOException {

        return UriBuilder.fromUri(getAuthorizationEndpoint(parameters))
            .queryParam(Parameters.RESPONSE_TYPE, ResponseType.CODE.toString())
            .queryParam(Parameters.STATE, parameters.getState())
            .queryParam(Parameters.REDIRECT_URI, parameters.getRedirectUri())
            .queryParam(Parameters.SCOPE, parameters.getScope())
            .build();
    }

    private String getAuthorizationEndpoint(Parameters parameters) {
        String authorizationEndpoint = parameters.getAuthorizationEndpoint();
        if (authorizationEndpoint != null) {
            logger.debug("Get Authorisation Request URI: resolved on the adapter side");
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
        String tokenEndpoint = parameters.removeTokenEndpoint();
        if (tokenEndpoint != null) {
            return tokenEndpoint;
        }
        return getAuthorizationServerMetadata(parameters).getTokenEndpoint();
    }
}
