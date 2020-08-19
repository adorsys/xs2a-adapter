package de.adorsys.xs2a.adapter.adorsys;

import de.adorsys.xs2a.adapter.api.Oauth2Service;
import de.adorsys.xs2a.adapter.api.Response;
import de.adorsys.xs2a.adapter.api.http.HttpClient;
import de.adorsys.xs2a.adapter.api.model.Aspsp;
import de.adorsys.xs2a.adapter.api.model.TokenResponse;
import de.adorsys.xs2a.adapter.api.oauth.Oauth2Api;
import de.adorsys.xs2a.adapter.api.validation.ValidationError;
import de.adorsys.xs2a.adapter.impl.http.StringUri;
import org.apache.commons.lang3.StringUtils;

import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static de.adorsys.xs2a.adapter.api.validation.Validation.requireValid;
import static de.adorsys.xs2a.adapter.impl.http.ResponseHandlers.jsonResponseHandler;

public class AdorsysIntegOauth2Service implements Oauth2Service {
    private static final String SCA_OAUTH_LINK_MISSING_ERROR_MESSAGE
        = "SCA OAuth link is missing or has a wrong format: " +
              "it has to be either provided as a request parameter or preconfigured for the current ASPSP";

    private final Aspsp aspsp;
    private final HttpClient httpClient;
    private final Oauth2Api oauth2Api;

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

        Response<TokenResponse> response = httpClient.post(url)
            .send(jsonResponseHandler(TokenResponse.class));
        return response.getBody();
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
