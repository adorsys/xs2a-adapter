package de.adorsys.xs2a.adapter.service;

import de.adorsys.xs2a.adapter.adapter.BaseAccountInformationService;
import de.adorsys.xs2a.adapter.http.HttpClient;
import de.adorsys.xs2a.adapter.http.StringUri;
import de.adorsys.xs2a.adapter.service.link.LinksRewriter;
import de.adorsys.xs2a.adapter.service.model.Aspsp;
import de.adorsys.xs2a.adapter.service.model.ConsentCreationResponse;
import de.adorsys.xs2a.adapter.service.model.Consents;

import static de.adorsys.xs2a.adapter.http.ResponseHandlers.consentCreationResponseHandler;
import static java.util.function.Function.identity;

public class SpardaAccountInformationService extends BaseAccountInformationService {
    private static final String AIS_SCOPE = "ais";

    public SpardaAccountInformationService(Aspsp aspsp,
                                           HttpClient httpClient,
                                           LinksRewriter linksRewriter) {
        super(aspsp, httpClient, linksRewriter);
    }

    @Override
    public Response<ConsentCreationResponse> createConsent(RequestHeaders requestHeaders,
                                                           RequestParams requestParams,
                                                           Consents body) {
        String idpUri = StringUri.appendQueryParam(getIdpUri(), Oauth2Service.Parameters.SCOPE, AIS_SCOPE);
        return createConsent(requestHeaders,
            requestParams,
            body,
            identity(),
            consentCreationResponseHandler(idpUri, ConsentCreationResponse.class));
    }
}
