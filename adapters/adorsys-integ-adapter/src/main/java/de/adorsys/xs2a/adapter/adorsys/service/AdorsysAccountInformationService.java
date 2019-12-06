package de.adorsys.xs2a.adapter.adorsys.service;

import de.adorsys.xs2a.adapter.adapter.BaseAccountInformationService;
import de.adorsys.xs2a.adapter.http.HttpClient;
import de.adorsys.xs2a.adapter.http.Request;
import de.adorsys.xs2a.adapter.service.RequestHeaders;
import de.adorsys.xs2a.adapter.service.Response;
import de.adorsys.xs2a.adapter.service.model.Aspsp;
import de.adorsys.xs2a.adapter.service.model.ConsentCreationResponse;
import de.adorsys.xs2a.adapter.service.model.Consents;

import static de.adorsys.xs2a.adapter.http.ResponseHandlers.consentCreationResponseHandler;
import static java.util.function.Function.identity;

public class AdorsysAccountInformationService extends BaseAccountInformationService {

    public AdorsysAccountInformationService(Aspsp aspsp, HttpClient httpClient, Request.Builder.Interceptor requestBuilderInterceptor) {
        super(aspsp, httpClient, requestBuilderInterceptor);
    }

    @Override
    public Response<ConsentCreationResponse> createConsent(RequestHeaders requestHeaders, Consents body) {
        return createConsent(
            requestHeaders, body, identity(),
            consentCreationResponseHandler(getIdpUri(), ConsentCreationResponse.class)
        );
    }
}
