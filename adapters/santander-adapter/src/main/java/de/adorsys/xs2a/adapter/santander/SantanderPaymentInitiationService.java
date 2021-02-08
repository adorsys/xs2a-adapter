package de.adorsys.xs2a.adapter.santander;

import de.adorsys.xs2a.adapter.api.http.HttpClientFactory;
import de.adorsys.xs2a.adapter.api.link.LinksRewriter;
import de.adorsys.xs2a.adapter.api.model.Aspsp;
import de.adorsys.xs2a.adapter.impl.BasePaymentInitiationService;
import de.adorsys.xs2a.adapter.impl.security.AccessTokenService;

import java.util.Map;

public class SantanderPaymentInitiationService extends BasePaymentInitiationService {
    private final AccessTokenService accessTokenService;
    private final String clientId;

    public SantanderPaymentInitiationService(Aspsp aspsp,
                                             HttpClientFactory httpClientFactory,
                                             LinksRewriter linksRewriter,
                                             AccessTokenService accessTokenService) {
        super(aspsp,
            httpClientFactory.getHttpClient(aspsp.getAdapterId()),
            linksRewriter,
            httpClientFactory.getHttpClientConfig().getLogSanitizer());
        this.accessTokenService = accessTokenService;
        clientId = SantanderAccessTokenService.getClientId();
    }

    @Override
    protected Map<String, String> populatePostHeaders(Map<String, String> headers) {
        return updateHeaders(headers);
    }

    private Map<String, String> updateHeaders(Map<String, String> headers) {
        headers.put("Authorization", "Bearer " + accessTokenService.retrieveToken());
        headers.put("x-ibm-client-id", clientId);
        return headers;
    }

    @Override
    protected Map<String, String> populatePutHeaders(Map<String, String> headers) {
        return updateHeaders(headers);
    }

    @Override
    protected Map<String, String> populateGetHeaders(Map<String, String> headers) {
        return updateHeaders(headers);
    }

    @Override
    protected Map<String, String> populateDeleteHeaders(Map<String, String> headers) {
        return updateHeaders(headers);
    }
}
