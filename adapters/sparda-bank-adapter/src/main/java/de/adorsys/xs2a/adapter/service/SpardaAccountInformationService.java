package de.adorsys.xs2a.adapter.service;

import de.adorsys.xs2a.adapter.impl.adapter.BaseAccountInformationService;
import de.adorsys.xs2a.adapter.api.model.Consents;
import de.adorsys.xs2a.adapter.api.model.ConsentsResponse201;
import de.adorsys.xs2a.adapter.http.HttpClient;
import de.adorsys.xs2a.adapter.impl.http.StringUri;
import de.adorsys.xs2a.adapter.service.link.LinksRewriter;
import de.adorsys.xs2a.adapter.service.model.Aspsp;

import java.util.Map;

import static de.adorsys.xs2a.adapter.impl.http.ResponseHandlers.consentCreationResponseHandler;
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
    public Response<ConsentsResponse201> createConsent(RequestHeaders requestHeaders,
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
            consentCreationResponseHandler(idpUri, ConsentsResponse201.class));
    }

    private boolean isOauthPreStep(RequestHeaders requestHeaders) {
        return requestHeaders.get(RequestHeaders.AUTHORIZATION)
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
