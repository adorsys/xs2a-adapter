package de.adorsys.xs2a.adapter.service.impl;

import de.adorsys.xs2a.adapter.adapter.BaseAccountInformationService;
import de.adorsys.xs2a.adapter.http.StringUri;
import de.adorsys.xs2a.adapter.service.GeneralResponse;
import de.adorsys.xs2a.adapter.service.RequestHeaders;
import de.adorsys.xs2a.adapter.service.StartScaProcessResponse;
import de.adorsys.xs2a.adapter.service.ais.ConsentCreationResponse;
import de.adorsys.xs2a.adapter.service.ais.Consents;
import de.adorsys.xs2a.adapter.service.impl.mapper.ScaStatusResponseMapper;
import de.adorsys.xs2a.adapter.service.impl.mapper.UnicreditCreateConsentLinkMapper;
import de.adorsys.xs2a.adapter.service.impl.mapper.UnicreditStartAuthorisationLinkMapper;
import de.adorsys.xs2a.adapter.service.impl.model.UnicreditAccountScaStatusResponse;
import de.adorsys.xs2a.adapter.service.model.ScaStatusResponse;
import de.adorsys.xs2a.adapter.service.model.TransactionAuthorisation;
import de.adorsys.xs2a.adapter.service.model.UpdatePsuAuthentication;

import java.util.Map;
import java.util.function.Function;

public class UnicreditAccountInformationService extends BaseAccountInformationService {
    private static final String AUTHENTICATION_CURRENT_NUMBER_QUERY_PARAM = "authenticationCurrentNumber";
    private static final String PSU_IP_ADDRESS = "PSU-IP-Address";
    private static final String MOCK_PSU_IP_ADDRESS = "0.0.0.0";    // TODO should be changed to a real data

    private final UnicreditCreateConsentLinkMapper createConsentLinkMapper = new UnicreditCreateConsentLinkMapper();
    private final UnicreditStartAuthorisationLinkMapper startAuthorisationLinkMapper = new UnicreditStartAuthorisationLinkMapper();
    private final ScaStatusResponseMapper scaStatusResponseMapper = new ScaStatusResponseMapper();

    public UnicreditAccountInformationService(String baseUri) {
        super(baseUri);
    }

    @Override
    public GeneralResponse<ConsentCreationResponse> createConsent(RequestHeaders requestHeaders, Consents body) {
        return createConsent(requestHeaders, body, ConsentCreationResponse.class, createConsentLinkMapper::modifyLinks);
    }

    @Override
    protected <T> GeneralResponse<StartScaProcessResponse> startConsentAuthorisation(String consentId, RequestHeaders requestHeaders, UpdatePsuAuthentication updatePsuAuthentication, Class<T> klass, Function<T, StartScaProcessResponse> mapper) {
        String uri = StringUri.fromElements(getConsentBaseUri(), consentId);
        Map<String, String> headersMap = populatePutHeaders(requestHeaders.toMap());
        String body = jsonMapper.writeValueAsString(updatePsuAuthentication);

        GeneralResponse<T> response = httpClient.put(uri, body, headersMap, jsonResponseHandler(klass));
        StartScaProcessResponse startScaProcessResponse = mapper.apply(response.getResponseBody());

        return new GeneralResponse<>(response.getStatusCode(), startAuthorisationLinkMapper.modifyLinks(startScaProcessResponse), response.getResponseHeaders());
    }

    @Override
    public GeneralResponse<ScaStatusResponse> updateConsentsPsuData(String consentId, String authorisationId, RequestHeaders requestHeaders, TransactionAuthorisation transactionAuthorisation) {
        return updateConsentsPsuData(consentId, authorisationId, requestHeaders, transactionAuthorisation, UnicreditAccountScaStatusResponse.class, scaStatusResponseMapper::toScaStatusResponse);
    }

    @Override
    protected String getUpdateConsentPsuDataUri(String consentId, String authorisationId) {
        String uri = StringUri.fromElements(getConsentBaseUri(), consentId);
        return StringUri.withQuery(uri, AUTHENTICATION_CURRENT_NUMBER_QUERY_PARAM, authorisationId);
    }

    @Override
    protected Map<String, String> populateGetHeaders(Map<String, String> map) {
        Map<String, String> headers = super.populateGetHeaders(map);
        headers.put(ACCEPT_HEADER, APPLICATION_JSON);
        headers.put(PSU_IP_ADDRESS, MOCK_PSU_IP_ADDRESS);

        return headers;
    }

    @Override
    protected Map<String, String> populatePostHeaders(Map<String, String> map) {
        Map<String, String> headers = super.populatePostHeaders(map);
        headers.put(CONTENT_TYPE_HEADER, APPLICATION_JSON);
        return headers;
    }

    @Override
    protected Map<String, String> populatePutHeaders(Map<String, String> headers) {
        headers.put(CONTENT_TYPE_HEADER, APPLICATION_JSON);
        return headers;
    }
}
