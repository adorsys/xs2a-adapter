package de.adorsys.xs2a.adapter.adorsys.service;

import de.adorsys.xs2a.adapter.adapter.mapper.TokenResponseMapper;
import de.adorsys.xs2a.adapter.adapter.model.OauthToken;
import de.adorsys.xs2a.adapter.adorsys.service.api.Oauth2Api;
import de.adorsys.xs2a.adapter.http.HttpClient;
import de.adorsys.xs2a.adapter.http.StringUri;
import de.adorsys.xs2a.adapter.service.Oauth2Service;
import de.adorsys.xs2a.adapter.service.Response;
import de.adorsys.xs2a.adapter.service.exception.BadRequestException;
import de.adorsys.xs2a.adapter.service.model.Aspsp;
import de.adorsys.xs2a.adapter.service.model.TokenResponse;
import org.mapstruct.factory.Mappers;

import java.net.URI;
import java.util.Map;

import static de.adorsys.xs2a.adapter.http.ResponseHandlers.jsonResponseHandler;

public class AdorsysIntegOauth2Service implements Oauth2Service {
    private static final String SCA_OAUTH_LINK_MISSING_ERROR_MESSAGE
        = "SCA OAuth link is missing or has a wrong format: " +
              "it has to be either provided as a request parameter or preconfigured for the current ASPSP";

    private final Aspsp aspsp;
    private final HttpClient httpClient;
    private final Oauth2Api oauth2Api;
    private final TokenResponseMapper tokenResponseMapper = Mappers.getMapper(TokenResponseMapper.class);

    public AdorsysIntegOauth2Service(Aspsp aspsp,
                                     HttpClient httpClient,
                                     Oauth2Api oauth2Api) {
        this.aspsp = aspsp;
        this.httpClient = httpClient;
        this.oauth2Api = oauth2Api;
    }

    @Override
    public URI getAuthorizationRequestUri(Map<String, String> headers, Parameters parameters) {
        String scaOAuthUrl = getScaOAuthUrl(parameters);

        if (scaOAuthUrl == null || scaOAuthUrl.trim().isEmpty()) {
            throw new BadRequestException(SCA_OAUTH_LINK_MISSING_ERROR_MESSAGE);
        }

        String authorisationUri = oauth2Api.getAuthorisationUri(scaOAuthUrl);

        if (!StringUri.containsQueryParam(authorisationUri, "redirectId")) {
            authorisationUri = StringUri.appendQueryParam(
                authorisationUri,
                "redirect_uri", parameters.getRedirectUri()
            );
        }

        return URI.create(authorisationUri);
    }

    @Override
    public TokenResponse getToken(Map<String, String> headers, Parameters parameters) {
        String scaOAuthUrl = getScaOAuthUrl(parameters);

        if (scaOAuthUrl == null
                || scaOAuthUrl.trim().isEmpty()
                || !StringUri.containsProtocol(scaOAuthUrl)) {
            throw new BadRequestException(SCA_OAUTH_LINK_MISSING_ERROR_MESSAGE);
        }

        String url = StringUri.withQuery(
            oauth2Api.getTokenUri(scaOAuthUrl),
            "code", parameters.getAuthorizationCode()
        );

        Response<OauthToken> response = httpClient.post(url)
            .send(jsonResponseHandler(OauthToken.class));
        return tokenResponseMapper.map(response.getBody());
    }

    private String getScaOAuthUrl(Parameters parameters) {
        String scaOAuthLinkParam = parameters.getScaOAuthLink();

        if (scaOAuthLinkParam != null && !scaOAuthLinkParam.trim().isEmpty()) {
            return StringUri.decodeUrl(scaOAuthLinkParam);
        }

        return aspsp.getIdpUrl();
    }
}
