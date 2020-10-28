package de.adorsys.xs2a.adapter.crealogix;

import de.adorsys.xs2a.adapter.api.RequestHeaders;
import de.adorsys.xs2a.adapter.api.RequestParams;
import de.adorsys.xs2a.adapter.api.Response;
import de.adorsys.xs2a.adapter.api.http.HttpClient;
import de.adorsys.xs2a.adapter.api.link.LinksRewriter;
import de.adorsys.xs2a.adapter.api.model.Aspsp;
import de.adorsys.xs2a.adapter.api.model.Consents;
import de.adorsys.xs2a.adapter.api.model.ConsentsResponse201;
import de.adorsys.xs2a.adapter.impl.BaseAccountInformationService;

import static de.adorsys.xs2a.adapter.crealogix.CrealogixRequestResponseHandlers.crealogixRequestHandler;
import static de.adorsys.xs2a.adapter.crealogix.CrealogixRequestResponseHandlers.crealogixResponseHandler;
import static java.util.function.Function.identity;

public class CrealogixAccountInformationService extends BaseAccountInformationService {

    public CrealogixAccountInformationService(Aspsp aspsp,
                                              HttpClient httpClient,
                                              LinksRewriter linksRewriter) {
        super(aspsp, httpClient, linksRewriter);
    }

    @Override
    public Response<ConsentsResponse201> createConsent(RequestHeaders requestHeaders,
                                                       RequestParams requestParams,
                                                       Consents body) {
        crealogixRequestHandler(requestHeaders);

        return super.createConsent(requestHeaders, requestParams, body, identity(), crealogixResponseHandler(ConsentsResponse201.class));
    }
}
