package de.adorsys.xs2a.adapter.consors;

import de.adorsys.xs2a.adapter.api.RequestHeaders;
import de.adorsys.xs2a.adapter.api.RequestParams;
import de.adorsys.xs2a.adapter.api.Response;
import de.adorsys.xs2a.adapter.api.http.HttpClient;
import de.adorsys.xs2a.adapter.api.http.HttpLogSanitizer;
import de.adorsys.xs2a.adapter.api.link.LinksRewriter;
import de.adorsys.xs2a.adapter.api.model.Aspsp;
import de.adorsys.xs2a.adapter.api.model.Consents;
import de.adorsys.xs2a.adapter.api.model.ConsentsResponse201;
import de.adorsys.xs2a.adapter.impl.BaseAccountInformationService;

import java.util.Map;

import static de.adorsys.xs2a.adapter.api.RequestHeaders.PSU_ID;

public class ConsorsAccountInformationService extends BaseAccountInformationService {

    public ConsorsAccountInformationService(Aspsp aspsp,
                                            HttpClient httpClient,
                                            PsuIdHeaderInterceptor interceptor,
                                            LinksRewriter linksRewriter,
                                            HttpLogSanitizer logSanitizer) {
        super(aspsp, httpClient, interceptor, linksRewriter, logSanitizer);
    }

    @Override
    public Response<String> getTransactionListAsString(String accountId,
                                                       RequestHeaders requestHeaders,
                                                       RequestParams requestParams) {
        return getTransactionList(accountId, requestHeaders, requestParams)
            .map(jsonMapper::writeValueAsString);
    }

    @Override
    public Response<ConsentsResponse201> createConsent(RequestHeaders requestHeaders,
                                                       RequestParams requestParams,
                                                       Consents body) {
        Map<String, String> checkedHeader = removePsuIdHeader(requestHeaders.toMap());

        return super.createConsent(RequestHeaders.fromMap(checkedHeader), requestParams, body);
    }

    private Map<String, String> removePsuIdHeader(Map<String, String> headers) {
        headers.remove(PSU_ID);
        return headers;
    }
}
