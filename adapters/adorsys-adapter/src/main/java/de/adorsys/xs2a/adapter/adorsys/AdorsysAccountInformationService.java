package de.adorsys.xs2a.adapter.adorsys;

import de.adorsys.xs2a.adapter.api.RequestHeaders;
import de.adorsys.xs2a.adapter.api.RequestParams;
import de.adorsys.xs2a.adapter.api.Response;
import de.adorsys.xs2a.adapter.api.http.HttpClient;
import de.adorsys.xs2a.adapter.api.http.Request;
import de.adorsys.xs2a.adapter.api.link.LinksRewriter;
import de.adorsys.xs2a.adapter.api.model.Aspsp;
import de.adorsys.xs2a.adapter.api.model.Consents;
import de.adorsys.xs2a.adapter.api.model.ConsentsResponse201;
import de.adorsys.xs2a.adapter.impl.BaseAccountInformationService;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

import static de.adorsys.xs2a.adapter.impl.http.ResponseHandlers.consentCreationResponseHandler;
import static java.util.function.Function.identity;

public class AdorsysAccountInformationService extends BaseAccountInformationService {
    private static final String ACCEPT_ALL = "*/*";
    private static final String ACCEPT_JSON = "application/json";

    public AdorsysAccountInformationService(Aspsp aspsp,
                                            HttpClient httpClient,
                                            Request.Builder.Interceptor requestBuilderInterceptor,
                                            LinksRewriter linksRewriter) {
        super(aspsp, httpClient, requestBuilderInterceptor, linksRewriter);
    }

    @Override
    public Response<ConsentsResponse201> createConsent(RequestHeaders requestHeaders,
                                                       RequestParams requestParams,
                                                       Consents body) {
        return createConsent(requestHeaders,
            requestParams,
            body,
            identity(),
            consentCreationResponseHandler(getIdpUri(), ConsentsResponse201.class));
    }

    @Override
    protected Map<String, String> populatePostHeaders(Map<String, String> headers) {
        return super.populatePostHeaders(modifyAcceptHeader(headers));
    }

    @Override
    protected Map<String, String> populatePutHeaders(Map<String, String> headers) {
        return super.populatePutHeaders(modifyAcceptHeader(headers));
    }

    @Override
    protected Map<String, String> populateGetHeaders(Map<String, String> headers) {
        return super.populateGetHeaders(modifyAcceptHeader(headers));
    }

    @Override
    protected Map<String, String> populateDeleteHeaders(Map<String, String> headers) {
        return super.populateDeleteHeaders(modifyAcceptHeader(headers));
    }

    private Map<String, String> modifyAcceptHeader(Map<String, String> headers) {
        String acceptValue = headers.get(RequestHeaders.ACCEPT);

        if (StringUtils.isBlank(acceptValue) || acceptValue.equals(ACCEPT_ALL)) {
            headers.put(RequestHeaders.ACCEPT, ACCEPT_JSON);
        }

        return headers;
    }
}
