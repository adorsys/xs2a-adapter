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

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static de.adorsys.xs2a.adapter.http.ResponseHandlers.jsonResponseHandler;

public class UnicreditAccountInformationService extends BaseAccountInformationService {
    private static final String AUTHENTICATION_CURRENT_NUMBER_QUERY_PARAM = "authenticationCurrentNumber";
    private static final String DEFAULT_PSU_IP_ADDRESS = "0.0.0.0";
    private static final String DEFAULT_PSU_ID_TYPE = "HVB_ONLINEBANKING";
    private static final Set<String> POSSIBLE_PSU_ID_TYPE_VALUES = new HashSet<>(Arrays.asList(DEFAULT_PSU_ID_TYPE, "UCEBANKINGGLOBAL"));

    private final UnicreditCreateConsentResponseMapper createConsentResponseMapper = new UnicreditCreateConsentResponseMapper();
    private final UnicreditStartAuthorisationResponseMapper startAuthorisationResponseMapper = new UnicreditStartAuthorisationResponseMapper();
    private final ScaStatusResponseMapper scaStatusResponseMapper = new ScaStatusResponseMapper();

    public UnicreditAccountInformationService(Aspsp aspsp, HttpClient httpClient) {
        super(aspsp, httpClient);
    }

    @Override
    public Response<ConsentCreationResponse> createConsent(RequestHeaders requestHeaders, Consents body) {
        return createConsent(requestHeaders, body, ConsentCreationResponse.class, createConsentResponseMapper::modifyResponse);
    }

    @Override
    public Response<StartScaProcessResponse> startConsentAuthorisation(String consentId, RequestHeaders requestHeaders, UpdatePsuAuthentication updatePsuAuthentication) {
        String uri = StringUri.fromElements(getConsentBaseUri(), consentId);
        Map<String, String> headersMap = populatePutHeaders(requestHeaders.toMap());
        headersMap = addPsuIdTypeHeader(headersMap);
        String body = jsonMapper.writeValueAsString(updatePsuAuthentication);

        Response<UnicreditStartScaProcessResponse> response = httpClient.put(uri)
            .jsonBody(body)
            .headers(headersMap)
            .send(jsonResponseHandler(UnicreditStartScaProcessResponse.class));

        return new Response<>(response.getStatusCode(), startAuthorisationResponseMapper.modifyResponse(response.getBody()), response.getHeaders());
    }

    @Override
    public Response<ScaStatusResponse> updateConsentsPsuData(String consentId, String authorisationId, RequestHeaders requestHeaders, TransactionAuthorisation transactionAuthorisation) {
        return updateConsentsPsuData(consentId, authorisationId, requestHeaders, transactionAuthorisation, UnicreditAccountScaStatusResponse.class, scaStatusResponseMapper::toScaStatusResponse);
    }

    @Override
    public Response<ScaStatusResponse> getConsentScaStatus(String consentId, String authorisationId, RequestHeaders requestHeaders) {
        Response<ConsentStatusResponse> response = this.getConsentStatus(consentId, requestHeaders);
        return new Response<>(response.getStatusCode(), scaStatusResponseMapper.toScaStatusResponse(response.getBody()), response.getHeaders());
    }

    @Override
    protected String getUpdateConsentPsuDataUri(String consentId, String authorisationId) {
        String uri = StringUri.fromElements(getConsentBaseUri(), consentId);
        return StringUri.withQuery(uri, AUTHENTICATION_CURRENT_NUMBER_QUERY_PARAM, authorisationId);
    }

    @Override
    protected Map<String, String> addPsuIdTypeHeader(Map<String, String> headers) {
        if (!POSSIBLE_PSU_ID_TYPE_VALUES.contains(headers.get(RequestHeaders.PSU_ID_TYPE))) {
            headers.put(RequestHeaders.PSU_ID_TYPE, DEFAULT_PSU_ID_TYPE);
        }

        return headers;
    }

    @Override
    protected Map<String, String> populateGetHeaders(Map<String, String> map) {
        Map<String, String> headers = super.populateGetHeaders(map);
        headers.put(ACCEPT_HEADER, ContentType.APPLICATION_JSON);
        headers.putIfAbsent(RequestHeaders.PSU_IP_ADDRESS, DEFAULT_PSU_IP_ADDRESS);

        return headers;
    }

    @Override
    protected Map<String, String> populatePostHeaders(Map<String, String> map) {
        Map<String, String> headers = super.populatePostHeaders(map);
        headers.put(RequestHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON);
        return headers;
    }

    @Override
    protected Map<String, String> populatePutHeaders(Map<String, String> headers) {
        headers.put(RequestHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON);
        return headers;
    }
}
