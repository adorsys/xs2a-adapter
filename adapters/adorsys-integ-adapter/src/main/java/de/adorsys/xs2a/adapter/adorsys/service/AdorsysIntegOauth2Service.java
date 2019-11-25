package de.adorsys.xs2a.adapter.adorsys.service;

import de.adorsys.xs2a.adapter.adapter.mapper.TokenResponseMapper;
import de.adorsys.xs2a.adapter.adapter.model.OauthToken;
import de.adorsys.xs2a.adapter.adorsys.service.api.Oauth2Api;
import de.adorsys.xs2a.adapter.http.HttpClient;
import de.adorsys.xs2a.adapter.http.StringUri;
import de.adorsys.xs2a.adapter.service.Oauth2Service;
import de.adorsys.xs2a.adapter.service.Response;
import de.adorsys.xs2a.adapter.service.exception.BadRequestException;
import de.adorsys.xs2a.adapter.service.model.TokenResponse;
import org.mapstruct.factory.Mappers;

import java.net.URI;
import java.util.Map;

import static de.adorsys.xs2a.adapter.http.ResponseHandlers.jsonResponseHandler;

public class AdorsysIntegOauth2Service implements Oauth2Service {
    private static final String SCA_OAUTH_LINK_MISSING_ERROR_MESSAGE
        = "SCA OAuth link is missing: it has to be either provided as a request parameter or preconfigured for the current ASPSP";

    private final String authorizationRequestBaseUrl;
    private final String tokenRequestBaseUrl;
    private final HttpClient httpClient;
    private final Oauth2Api oauth2Api;
    private final TokenResponseMapper tokenResponseMapper = Mappers.getMapper(TokenResponseMapper.class);

    public AdorsysIntegOauth2Service(String authorizationRequestBaseUrl,
                                     String tokenRequestBaseUrl,
                                     HttpClient httpClient) {
        this.authorizationRequestBaseUrl = authorizationRequestBaseUrl;
        this.tokenRequestBaseUrl = tokenRequestBaseUrl;
        this.httpClient = httpClient;
        this.oauth2Api = new Oauth2Api(this.httpClient);
    }

    @Override
    public URI getAuthorizationRequestUri(Map<String, String> headers, Parameters parameters) {
        String scaOAuthUrl = getScaOAuthUrl(parameters);

        if (scaOAuthUrl == null || scaOAuthUrl.trim().isEmpty()) {
            if (authorizationRequestBaseUrl != null && !authorizationRequestBaseUrl.trim().isEmpty()) {
                String url = authorizationRequestBaseUrl + "/auth/authorize";
                return URI.create(StringUri.withQuery(url, parameters.asMap()));
            }
            throw new BadRequestException(SCA_OAUTH_LINK_MISSING_ERROR_MESSAGE);
        }

        return URI.create(oauth2Api.getAuthorisationUri(scaOAuthUrl));
    }

    @Override
    public TokenResponse getToken(Map<String, String> headers, Parameters parameters) {
        String scaOAuthUrl = getScaOAuthUrl(parameters);

        String url;
        if (scaOAuthUrl == null || scaOAuthUrl.trim().isEmpty()) {
            if (tokenRequestBaseUrl == null || tokenRequestBaseUrl.trim().isEmpty()) {
                throw new BadRequestException(SCA_OAUTH_LINK_MISSING_ERROR_MESSAGE);
            }
            url = tokenRequestBaseUrl + "/oauth/token";
        } else {
            url = StringUri.withQuery(oauth2Api.getTokenUri(scaOAuthUrl), "code", parameters.getAuthorizationCode());
        }

        Response<OauthToken> response = httpClient.post(url)
            .send(jsonResponseHandler(OauthToken.class));
        return tokenResponseMapper.map(response.getBody());
    }

    private String getScaOAuthUrl(Parameters parameters) {
        String scaOAuthLink = parameters.getScaOAuthLink();

        if (scaOAuthLink != null && !scaOAuthLink.trim().isEmpty()) {
            return StringUri.decodeUrl(scaOAuthLink);
        }

        return null;
    }
}
