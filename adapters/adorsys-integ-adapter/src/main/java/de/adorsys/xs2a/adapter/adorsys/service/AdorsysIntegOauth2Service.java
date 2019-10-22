package de.adorsys.xs2a.adapter.adorsys.service;

import de.adorsys.xs2a.adapter.adapter.mapper.TokenResponseMapper;
import de.adorsys.xs2a.adapter.adapter.model.OauthToken;
import de.adorsys.xs2a.adapter.http.HttpClient;
import de.adorsys.xs2a.adapter.http.StringUri;
import de.adorsys.xs2a.adapter.service.Oauth2Service;
import de.adorsys.xs2a.adapter.service.Response;
import de.adorsys.xs2a.adapter.service.model.TokenResponse;
import org.mapstruct.factory.Mappers;

import java.net.URI;
import java.util.Map;

import static de.adorsys.xs2a.adapter.http.ResponseHandlers.jsonResponseHandler;

public class AdorsysIntegOauth2Service implements Oauth2Service {
    private final String authorizationRequestBaseUrl;
    private final String tokenRequestBaseUrl;
    private final HttpClient httpClient;
    private final TokenResponseMapper tokenResponseMapper = Mappers.getMapper(TokenResponseMapper.class);

    public AdorsysIntegOauth2Service(String authorizationRequestBaseUrl,
                                     String tokenRequestBaseUrl,
                                     HttpClient httpClient) {
        this.authorizationRequestBaseUrl = authorizationRequestBaseUrl;
        this.tokenRequestBaseUrl = tokenRequestBaseUrl;
        this.httpClient = httpClient;
    }

    @Override
    public URI getAuthorizationRequestUri(Map<String, String> headers, Parameters parameters) {
        String url = authorizationRequestBaseUrl + "/auth/authorize";

        return URI.create(StringUri.withQuery(url, parameters.asMap()));
    }

    @Override
    public TokenResponse getToken(Map<String, String> headers, Parameters parameters) {
        String url = tokenRequestBaseUrl + "/oauth/token";

        Response<OauthToken> response = httpClient.post(url)
            .header("code", parameters.getAuthorizationCode())
            .send(jsonResponseHandler(OauthToken.class));
        return tokenResponseMapper.map(response.getBody());
    }
}
