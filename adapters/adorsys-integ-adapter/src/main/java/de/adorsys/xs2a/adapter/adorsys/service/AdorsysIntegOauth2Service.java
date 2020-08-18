package de.adorsys.xs2a.adapter.adorsys.service;

import de.adorsys.xs2a.adapter.impl.mapper.TokenResponseMapper;
import de.adorsys.xs2a.adapter.impl.model.OauthToken;
import de.adorsys.xs2a.adapter.http.HttpClient;
import de.adorsys.xs2a.adapter.impl.http.StringUri;
import de.adorsys.xs2a.adapter.service.Oauth2Service;
import de.adorsys.xs2a.adapter.service.Response;
import de.adorsys.xs2a.adapter.service.model.Aspsp;
import de.adorsys.xs2a.adapter.service.model.TokenResponse;
import de.adorsys.xs2a.adapter.service.oauth.Oauth2Api;
import de.adorsys.xs2a.adapter.validation.ValidationError;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.factory.Mappers;

import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static de.adorsys.xs2a.adapter.impl.http.ResponseHandlers.jsonResponseHandler;
import static de.adorsys.xs2a.adapter.validation.Validation.requireValid;

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
        requireValid(validateGetAuthorizationRequestUri(headers, parameters));

        String scaOAuthUrl = getScaOAuthUrl(parameters);
        String authorisationUri = oauth2Api.getAuthorisationUri(scaOAuthUrl);

        authorisationUri = StringUri.appendQueryParam(
            authorisationUri,
            "redirect_uri", parameters.getRedirectUri()
        );

        return URI.create(authorisationUri);
    }

    @Override
    public List<ValidationError> validateGetAuthorizationRequestUri(Map<String, String> headers,
                                                                    Parameters parameters) {
        if (StringUtils.isBlank(getScaOAuthUrl(parameters))) {
            return Collections.singletonList(new ValidationError(ValidationError.Code.REQUIRED,
                Parameters.SCA_OAUTH_LINK,
                SCA_OAUTH_LINK_MISSING_ERROR_MESSAGE));
        }

        return Collections.emptyList();
    }

    @Override
    public TokenResponse getToken(Map<String, String> headers, Parameters parameters) {
        requireValid(validateGetToken(headers, parameters));

        String scaOAuthUrl = getScaOAuthUrl(parameters);
        String url = StringUri.withQuery(
            oauth2Api.getTokenUri(scaOAuthUrl),
            "code", parameters.getAuthorizationCode()
        );

        Response<OauthToken> response = httpClient.post(url)
                                            .send(jsonResponseHandler(OauthToken.class));
        return tokenResponseMapper.map(response.getBody());
    }

    @Override
    public List<ValidationError> validateGetToken(Map<String, String> headers, Parameters parameters) {
        String scaOAuthUrl = getScaOAuthUrl(parameters);
        if (StringUtils.isBlank(scaOAuthUrl) || !StringUri.containsProtocol(scaOAuthUrl)) {
            return Collections.singletonList(new ValidationError(ValidationError.Code.REQUIRED,
                Parameters.SCA_OAUTH_LINK,
                SCA_OAUTH_LINK_MISSING_ERROR_MESSAGE));
        }

        return Collections.emptyList();
    }

    private String getScaOAuthUrl(Parameters parameters) {
        String scaOAuthLinkParam = parameters.getScaOAuthLink();

        if (StringUtils.isNotBlank(scaOAuthLinkParam)) {
            return StringUri.decode(scaOAuthLinkParam);
        }

        return aspsp.getIdpUrl();
    }
}
