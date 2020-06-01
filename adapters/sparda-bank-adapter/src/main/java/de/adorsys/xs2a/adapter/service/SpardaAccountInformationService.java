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
                   .map(StringUtils::isNotBlank)
                   .orElse(false);
    }

    private RequestHeaders modifyPsuId(RequestHeaders requestHeaders) {
        Map<String, String> headersMap = requestHeaders.toMap();
        String token = headersMap.get(RequestHeaders.AUTHORIZATION);
        headersMap.put(RequestHeaders.PSU_ID, spardaJwtService.getPsuId(token));
        return RequestHeaders.fromMap(headersMap);
    }
}
