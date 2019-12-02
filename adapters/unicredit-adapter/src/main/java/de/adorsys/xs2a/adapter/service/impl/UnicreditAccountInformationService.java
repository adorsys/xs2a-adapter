package de.adorsys.xs2a.adapter.service.impl;

import de.adorsys.xs2a.adapter.adapter.BaseAccountInformationService;
import de.adorsys.xs2a.adapter.http.ContentType;
import de.adorsys.xs2a.adapter.http.HttpClient;
import de.adorsys.xs2a.adapter.http.StringUri;
import de.adorsys.xs2a.adapter.service.RequestHeaders;
import de.adorsys.xs2a.adapter.service.Response;
import de.adorsys.xs2a.adapter.service.impl.mapper.ScaStatusResponseMapper;
import de.adorsys.xs2a.adapter.service.impl.mapper.UnicreditCreateConsentResponseMapper;
import de.adorsys.xs2a.adapter.service.impl.mapper.UnicreditStartAuthorisationResponseMapper;
import de.adorsys.xs2a.adapter.service.impl.model.UnicreditAccountScaStatusResponse;
import de.adorsys.xs2a.adapter.service.impl.model.UnicreditStartScaProcessResponse;
import de.adorsys.xs2a.adapter.service.model.*;

import javax.ws.rs.core.HttpHeaders;
import java.util.Map;

import static de.adorsys.xs2a.adapter.http.ResponseHandlers.jsonResponseHandler;

public class UnicreditAccountInformationService extends BaseAccountInformationService {

    public UnicreditAccountInformationService(String baseUri, HttpClient httpClient) {
        super(baseUri, httpClient);
    }

    @Override
    protected Map<String, String> populatePostHeaders(Map<String, String> map) {
        map.put(RequestHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON);
        return map;
    }

    @Override
    protected Map<String, String> populatePutHeaders(Map<String, String> headers) {
        headers.put(RequestHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON);
        return headers;
    }
}
