package de.adorsys.xs2a.adapter.service;

import de.adorsys.xs2a.adapter.adapter.BaseAccountInformationService;
import de.adorsys.xs2a.adapter.http.HttpClient;
import de.adorsys.xs2a.adapter.http.StringUri;
import de.adorsys.xs2a.adapter.service.link.LinksRewriter;
import de.adorsys.xs2a.adapter.service.model.Aspsp;
import de.adorsys.xs2a.adapter.service.model.ConsentCreationResponse;
import de.adorsys.xs2a.adapter.service.model.Consents;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

import static de.adorsys.xs2a.adapter.http.ResponseHandlers.consentCreationResponseHandler;
import static java.util.function.Function.identity;

public class SpardaAccountInformationService extends BaseAccountInformationService {
    private static final String AIS_SCOPE = "ais";
    private static final String BEARER_TOKEN_TYPE_PREFIX = "Bearer ";

    private final SpardaJwtService spardaJwtService;

    public SpardaAccountInformationService(Aspsp aspsp,
                                           HttpClient httpClient,
                                           LinksRewriter linksRewriter,
                                           SpardaJwtService spardaJwtService) {
        super(aspsp, httpClient, linksRewriter);
        this.spardaJwtService = spardaJwtService;
    }

    @Override
    public Response<ConsentCreationResponse> createConsent(RequestHeaders requestHeaders,
                                                           RequestParams requestParams,
                                                           Consents body) {
        if (isOauthPreStep(requestHeaders)) {
            requestHeaders = modifyPsuId(requestHeaders);
        }
        String idpUri = StringUri.appendQueryParam(getIdpUri(), Oauth2Service.Parameters.SCOPE, AIS_SCOPE);

        return createConsent(requestHeaders,
            requestParams,
            body,
            identity(),
            consentCreationResponseHandler(idpUri, ConsentCreationResponse.class));
    }

    private boolean isOauthPreStep(RequestHeaders requestHeaders) {
        return requestHeaders.get(RequestHeaders.AUTHORIZATION)
                   .filter(StringUtils::isNotBlank)
                   .map(authHeader -> authHeader.startsWith(BEARER_TOKEN_TYPE_PREFIX))
                   .orElse(false);
    }

    private RequestHeaders modifyPsuId(RequestHeaders requestHeaders) {
        // .orElse(null) should never be the case, as this method is invoked for OAuth pre-step only
        String token = requestHeaders.getAuthorization()
                           .map(this::getBearerToken)
                           .orElse(null);
        Map<String, String> headersMap = requestHeaders.toMap();
        headersMap.put(RequestHeaders.PSU_ID.toLowerCase(), spardaJwtService.getPsuId(token));
        return RequestHeaders.fromMap(headersMap);
    }

    private String getBearerToken(String authorizationHeader) {
        return authorizationHeader.substring(BEARER_TOKEN_TYPE_PREFIX.length());
    }
}
