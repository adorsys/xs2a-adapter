package de.adorsys.xs2a.adapter.service.impl;

import de.adorsys.xs2a.adapter.adapter.BaseAccountInformationService;
import de.adorsys.xs2a.adapter.http.ContentType;
import de.adorsys.xs2a.adapter.http.HttpClient;
import de.adorsys.xs2a.adapter.service.RequestHeaders;
import de.adorsys.xs2a.adapter.service.RequestParams;
import de.adorsys.xs2a.adapter.service.Response;
import de.adorsys.xs2a.adapter.service.model.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class UnicreditAccountInformationService extends BaseAccountInformationService {

    private static final String DEFAULT_PSU_ID_TYPE = "HVB_ONLINEBANKING";
    private static final Set<String> POSSIBLE_PSU_ID_TYPE_VALUES = new HashSet<>(Arrays.asList(DEFAULT_PSU_ID_TYPE, "UCEBANKINGGLOBAL"));
    private static final String REDIRECT_URI = "example.com";

    public UnicreditAccountInformationService(Aspsp aspsp, HttpClient httpClient) {
        super(aspsp, httpClient);
    }

    @Override
    public Response<AccountListHolder> getAccountList(RequestHeaders requestHeaders, RequestParams requestParams) {
        return super.getAccountList(requestHeaders, RequestParams.builder().build());
    }

    @Override
    protected Map<String, String> populatePostHeaders(Map<String, String> map) {
        map.put(RequestHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON);
        map.put(RequestHeaders.TPP_REDIRECT_URI, REDIRECT_URI);
        return map;
    }

    @Override
    protected Map<String, String> populatePutHeaders(Map<String, String> headers) {
        headers.put(RequestHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON);
        return headers;
    }

    @Override
    protected Map<String, String> addPsuIdTypeHeader(Map<String, String> headers) {
        if (!POSSIBLE_PSU_ID_TYPE_VALUES.contains(headers.get(RequestHeaders.PSU_ID_TYPE))) {
            headers.put(RequestHeaders.PSU_ID_TYPE, DEFAULT_PSU_ID_TYPE);
        }

        return headers;
    }
}
