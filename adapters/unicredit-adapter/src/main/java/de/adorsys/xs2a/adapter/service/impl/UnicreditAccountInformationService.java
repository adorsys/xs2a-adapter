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
//    private static final String AUTHENTICATION_CURRENT_NUMBER_QUERY_PARAM = "authenticationCurrentNumber";
    private static final String DEFAULT_PSU_IP_ADDRESS = "0.0.0.0";

//    private final ScaStatusResponseMapper scaStatusResponseMapper = new ScaStatusResponseMapper();

    public UnicreditAccountInformationService(String baseUri, HttpClient httpClient) {
        super(baseUri, httpClient);
    }

//    @Override
//    public Response<ScaStatusResponse> updateConsentsPsuData(String consentId, String authorisationId, RequestHeaders requestHeaders, TransactionAuthorisation transactionAuthorisation) {
//        return updateConsentsPsuData(consentId, authorisationId, requestHeaders, transactionAuthorisation, UnicreditAccountScaStatusResponse.class, scaStatusResponseMapper::toScaStatusResponse);
//    }
//
//    @Override
//    public Response<ScaStatusResponse> getConsentScaStatus(String consentId, String authorisationId, RequestHeaders requestHeaders) {
//        Response<ConsentStatusResponse> response = this.getConsentStatus(consentId, requestHeaders);
//        return new Response<>(response.getStatusCode(), scaStatusResponseMapper.toScaStatusResponse(response.getBody()), response.getHeaders());
//    }
//
//    @Override
//    protected String getUpdateConsentPsuDataUri(String consentId, String authorisationId) {
//        String uri = StringUri.fromElements(getConsentBaseUri(), consentId);
//        return StringUri.withQuery(uri, AUTHENTICATION_CURRENT_NUMBER_QUERY_PARAM, authorisationId);
//    }

    @Override
    protected Map<String, String> populateGetHeaders(Map<String, String> map) {
        Map<String, String> headers = super.populateGetHeaders(map);
        headers.put(ACCEPT_HEADER, ContentType.APPLICATION_JSON);
        headers.putIfAbsent(RequestHeaders.PSU_IP_ADDRESS, DEFAULT_PSU_IP_ADDRESS);

        return headers;
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
