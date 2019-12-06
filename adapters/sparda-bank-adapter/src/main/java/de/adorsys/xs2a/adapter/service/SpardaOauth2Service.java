package de.adorsys.xs2a.adapter.service;

import de.adorsys.xs2a.adapter.adapter.mapper.TokenResponseMapper;
import de.adorsys.xs2a.adapter.adapter.model.OauthToken;
import de.adorsys.xs2a.adapter.http.HttpClient;
import de.adorsys.xs2a.adapter.http.StringUri;
import de.adorsys.xs2a.adapter.service.exception.BadRequestException;
import de.adorsys.xs2a.adapter.service.model.Aspsp;
import de.adorsys.xs2a.adapter.service.model.TokenResponse;
import de.adorsys.xs2a.adapter.service.util.SpardaOauthParamsAdjustingService;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.factory.Mappers;

import java.net.URI;
import java.util.Map;

import static de.adorsys.xs2a.adapter.http.ResponseHandlers.jsonResponseHandler;

public class SpardaOauth2Service implements Oauth2Service {
    private static final String AUTHORISATION_REQUEST_URI_SUFFIX = "/authorise";
    private static final String TOKEN_REQUEST_URI_SUFFIX = "/token";
    private static final String SCA_OAUTH_LINK_MISSING_ERROR_MESSAGE
        = "SCA OAuth link is missing or has a wrong format: " +
              "it has to be either provided as a request parameter or preconfigured for the current ASPSP";
    private static final String REFRESH_TOKEN_GRANT_TYPE = "refresh_token";

    private final Aspsp aspsp;
    private final HttpClient httpClient;
    private final SpardaOauthParamsAdjustingService paramsAdjustingService;
    private final TokenResponseMapper tokenResponseMapper = Mappers.getMapper(TokenResponseMapper.class);

    public SpardaOauth2Service(Aspsp aspsp,
                               HttpClient httpClient,
                               SpardaOauthParamsAdjustingService paramsAdjustingService) {
        this.aspsp = aspsp;
        this.httpClient = httpClient;
        this.paramsAdjustingService = paramsAdjustingService;
    }

    @Override
    public URI getAuthorizationRequestUri(Map<String, String> headers, Parameters parameters) {
        String scaOAuthUrl = getScaOAuthUrl(parameters);

        if (StringUtils.isBlank(scaOAuthUrl)) {
            throw new BadRequestException(SCA_OAUTH_LINK_MISSING_ERROR_MESSAGE);
        }

        String authorisationRequestUri = StringUri.fromElements(scaOAuthUrl, AUTHORISATION_REQUEST_URI_SUFFIX);
        Parameters parametersForGetAuthorizationRequest = paramsAdjustingService.adjustForGetAuthorizationRequest(parameters);

        return URI.create(StringUri.withQuery(authorisationRequestUri, parametersForGetAuthorizationRequest.asMap()));
    }

    @Override
    public TokenResponse getToken(Map<String, String> headers, Parameters parameters) {
        String scaOAuthUrl = getScaOAuthUrl(parameters);

        if (StringUtils.isBlank(scaOAuthUrl) || !StringUri.containsProtocol(scaOAuthUrl)) {
            throw new BadRequestException(SCA_OAUTH_LINK_MISSING_ERROR_MESSAGE);
        }

        String tokenRequestUri = StringUri.fromElements(scaOAuthUrl, TOKEN_REQUEST_URI_SUFFIX);

        Parameters tokenRequestParams;

        if (isRefreshTokenRequest(parameters)) {
            tokenRequestParams = paramsAdjustingService.adjustForRefreshTokenRequest(parameters);
        } else {
            tokenRequestParams = paramsAdjustingService.adjustForGetTokenRequest(parameters);
        }

        Response<OauthToken> response
            = httpClient.post(StringUri.withQuery(tokenRequestUri, tokenRequestParams.asMap()))
                  .send(jsonResponseHandler(OauthToken.class));

        return tokenResponseMapper.map(response.getBody());
    }

    private String getScaOAuthUrl(Parameters parameters) {
        String baseScaOAuthUrl = parameters.getScaOAuthLink();

        if (StringUtils.isBlank(baseScaOAuthUrl)) {
            baseScaOAuthUrl = aspsp.getIdpUrl();
        }

        return baseScaOAuthUrl;
    }

    private boolean isRefreshTokenRequest(Parameters parameters) {
        return REFRESH_TOKEN_GRANT_TYPE.equals(parameters.getGrantType());
    }
}
