package de.adorsys.xs2a.adapter.service;

import de.adorsys.xs2a.adapter.adapter.mapper.TokenResponseMapper;
import de.adorsys.xs2a.adapter.adapter.model.OauthToken;
import de.adorsys.xs2a.adapter.http.HttpClient;
import de.adorsys.xs2a.adapter.http.StringUri;
import de.adorsys.xs2a.adapter.service.exception.BadRequestException;
import de.adorsys.xs2a.adapter.service.model.Aspsp;
import de.adorsys.xs2a.adapter.service.model.TokenResponse;
import de.adorsys.xs2a.adapter.service.oauth.Oauth2Api;
import de.adorsys.xs2a.adapter.service.oauth.OauthParamsAdjustingService;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.factory.Mappers;

import java.net.URI;
import java.util.Map;

import static de.adorsys.xs2a.adapter.http.ResponseHandlers.jsonResponseHandler;

public class SparkasseOauth2Service implements Oauth2Service {
    private static final String SCA_OAUTH_LINK_MISSING_ERROR_MESSAGE
        = "SCA OAuth link is missing or has a wrong format: " +
              "it has to be either provided as a request parameter or preconfigured for the current ASPSP";

    private final Aspsp aspsp;
    private final HttpClient httpClient;
    private final Oauth2Api oauth2Api;
    private final OauthParamsAdjustingService paramsAdjustingService;
    private final TokenResponseMapper tokenResponseMapper = Mappers.getMapper(TokenResponseMapper.class);

    public SparkasseOauth2Service(Aspsp aspsp, HttpClient httpClient, Oauth2Api oauth2Api,
                                  OauthParamsAdjustingService paramsAdjustingService) {
        this.aspsp = aspsp;
        this.httpClient = httpClient;
        this.oauth2Api = oauth2Api;
        this.paramsAdjustingService = paramsAdjustingService;
    }

    @Override
    public URI getAuthorizationRequestUri(Map<String, String> headers, Parameters parameters) {
        String scaOAuthUrl = getScaOAuthUrl(parameters);

        if (StringUtils.isBlank(scaOAuthUrl)) {
            throw new BadRequestException(SCA_OAUTH_LINK_MISSING_ERROR_MESSAGE);
        }

        String authorisationUri = oauth2Api.getAuthorisationUri(scaOAuthUrl);
        Parameters parametersForGetAuthorizationRequest
            = paramsAdjustingService.adjustForGetAuthorizationRequest(parameters);

        return URI.create(StringUri.withQuery(authorisationUri, parametersForGetAuthorizationRequest.asMap()));
    }

    @Override
    public TokenResponse getToken(Map<String, String> headers, Parameters parameters) {
        String scaOAuthUrl = getScaOAuthUrl(parameters);

        if (StringUtils.isBlank(scaOAuthUrl) || !StringUri.containsProtocol(scaOAuthUrl)) {
            throw new BadRequestException(SCA_OAUTH_LINK_MISSING_ERROR_MESSAGE);
        }

        Parameters tokenRequestParams = paramsAdjustingService.adjustForGetTokenRequest(parameters);

        String url = StringUri.withQuery(
            oauth2Api.getTokenUri(scaOAuthUrl),
            tokenRequestParams.asMap()
        );

        Response<OauthToken> response = httpClient.post(url)
                                            .send(jsonResponseHandler(OauthToken.class));
        return tokenResponseMapper.map(response.getBody());
    }

    private String getScaOAuthUrl(Parameters parameters) {
        String scaOAuthLinkParam = parameters.getScaOAuthLink();

        if (StringUtils.isNotBlank(scaOAuthLinkParam)) {
            return StringUri.decode(scaOAuthLinkParam);
        }

        return aspsp.getIdpUrl();
    }
}
