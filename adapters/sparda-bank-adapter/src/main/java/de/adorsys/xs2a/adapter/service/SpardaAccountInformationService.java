package de.adorsys.xs2a.adapter.service;

import de.adorsys.xs2a.adapter.adapter.BaseAccountInformationService;
import de.adorsys.xs2a.adapter.http.HttpClient;
import de.adorsys.xs2a.adapter.service.link.LinksRewriter;
import de.adorsys.xs2a.adapter.service.model.Aspsp;
import de.adorsys.xs2a.adapter.service.model.ConsentCreationResponse;
import de.adorsys.xs2a.adapter.service.model.Consents;

import static de.adorsys.xs2a.adapter.http.ResponseHandlers.consentCreationResponseHandler;
import static java.util.function.Function.identity;

public class SpardaAccountInformationService extends BaseAccountInformationService {

    public SpardaAccountInformationService(Aspsp aspsp,
                                           HttpClient httpClient,
                                           LinksRewriter linksRewriter) {
        super(aspsp, httpClient, linksRewriter);
    }

    @Override
    public Response<ConsentCreationResponse> createConsent(RequestHeaders requestHeaders,
                                                           RequestParams requestParams,
                                                           Consents body) {
        return createConsent(requestHeaders,
            requestParams,
            body,
            identity(),
            consentCreationResponseHandler(getIdpUri(), ConsentCreationResponse.class));
    }
}
