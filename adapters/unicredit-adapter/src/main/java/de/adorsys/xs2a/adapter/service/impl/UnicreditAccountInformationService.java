package de.adorsys.xs2a.adapter.service.impl;

import de.adorsys.xs2a.adapter.adapter.BaseAccountInformationService;
import de.adorsys.xs2a.adapter.http.StringUri;
import de.adorsys.xs2a.adapter.service.GeneralResponse;
import de.adorsys.xs2a.adapter.service.RequestHeaders;
import de.adorsys.xs2a.adapter.service.StartScaProcessResponse;
import de.adorsys.xs2a.adapter.service.ais.ConsentCreationResponse;
import de.adorsys.xs2a.adapter.service.ais.Consents;
import de.adorsys.xs2a.adapter.service.mapper.ScaStatusResponseMapper;
import de.adorsys.xs2a.adapter.service.model.*;
import org.mapstruct.factory.Mappers;

import java.util.Map;
import java.util.function.Function;

public class UnicreditAccountInformationService extends BaseAccountInformationService {
    private static final String AUTHORISE_TRANSACTION_LINK = "authoriseTransaction";
    private static final String START_AUTHORISATION_WITH_PSU_AUTHENTICATION_LINK = "startAuthorisationWithPsuAuthentication";
    private static final String SELECT_AUTHENTICATION_METHOD_LINK = "selectAuthenticationMethod";
    private static final String NEXT_LINK = "next";
    private static final String AUTHENTICATION_CURRENT_NUMBER_QUERY_PARAM = "authenticationCurrentNumber";
    private static final String PSU_IP_ADDRESS = "PSU-IP-Address";
    private static final String MOCK_PSU_IP_ADDRESS = "0.0.0.0";    // TODO should be changed to a real data

    private final ScaStatusResponseMapper scaStatusResponseMapper = Mappers.getMapper(ScaStatusResponseMapper.class);

    public UnicreditAccountInformationService(String baseUri) {
        super(baseUri);
    }

    @Override
    protected <T> GeneralResponse<ConsentCreationResponse> createConsent(RequestHeaders requestHeaders, Consents body, Class<T> klass, Function<T, ConsentCreationResponse> mapper) {
        Map<String, String> headersMap = populatePostHeaders(requestHeaders.toMap());

        String bodyString = jsonMapper.writeValueAsString(jsonMapper.convertValue(body, Consents.class));

        GeneralResponse<T> response = httpClient.post(getConsentBaseUri(), bodyString, headersMap, jsonResponseHandler(klass));
        ConsentCreationResponse creationResponse = mapper.apply(response.getResponseBody());

        Map<String, Link> links = creationResponse.getLinks();
        // 1.1 is the obsolete version for now
        if (isBerlinGroupVersionObsolete(links, AUTHORISE_TRANSACTION_LINK)) {
            modifyLinksToActualVersion(links, AUTHORISE_TRANSACTION_LINK, START_AUTHORISATION_WITH_PSU_AUTHENTICATION_LINK, this::buildStartAuthorisationUri);
        }

        return new GeneralResponse<>(response.getStatusCode(), creationResponse, response.getResponseHeaders());
    }

    @Override
    protected <T> GeneralResponse<StartScaProcessResponse> startConsentAuthorisation(String consentId, RequestHeaders requestHeaders, UpdatePsuAuthentication updatePsuAuthentication, Class<T> klass, Function<T, StartScaProcessResponse> mapper) {
        String uri = StringUri.fromElements(getConsentBaseUri(), consentId);
        Map<String, String> headersMap = populatePutHeaders(requestHeaders.toMap());
        String body = jsonMapper.writeValueAsString(updatePsuAuthentication);

        GeneralResponse<T> response = httpClient.put(uri, body, headersMap, jsonResponseHandler(klass));
        StartScaProcessResponse startScaProcessResponse = mapper.apply(response.getResponseBody());

        Map<String, Link> links = startScaProcessResponse.getLinks();
        // 1.1 is the obsolete version for now
        if (isBerlinGroupVersionObsolete(links, NEXT_LINK)) {
            if (startScaProcessResponse.isSelectScaMethodStage()) {
                modifyLinksToActualVersion(links, NEXT_LINK, SELECT_AUTHENTICATION_METHOD_LINK, this::buildUpdatePsuDataUri);
            } else if (startScaProcessResponse.isChosenScaMethodStage()) {
                modifyLinksToActualVersion(links, NEXT_LINK, AUTHORISE_TRANSACTION_LINK, this::buildUpdatePsuDataUri);
            } else {
                // TODO come up with better solution
                throw new RuntimeException();
            }
        }

        return new GeneralResponse<>(response.getStatusCode(), startScaProcessResponse, response.getResponseHeaders());
    }

    @Override
    public GeneralResponse<ScaStatusResponse> updateConsentsPsuData(String consentId, String authorisationId, RequestHeaders requestHeaders, TransactionAuthorisation transactionAuthorisation) {
        String uri = StringUri.fromElements(getConsentBaseUri(), consentId);
        String uriWithQueryParam = StringUri.withQuery(uri, AUTHENTICATION_CURRENT_NUMBER_QUERY_PARAM, authorisationId);
        Map<String, String> headersMap = populatePutHeaders(requestHeaders.toMap());
        String body = jsonMapper.writeValueAsString(transactionAuthorisation);

        GeneralResponse<UnicreditScaStatusResponse> response = httpClient.put(uriWithQueryParam, body, headersMap, jsonResponseHandler(UnicreditScaStatusResponse.class));
        ScaStatusResponse scaStatusResponse = scaStatusResponseMapper.toScaStatusResponse(response.getResponseBody());
        return new GeneralResponse<>(response.getStatusCode(), scaStatusResponse, response.getResponseHeaders());
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

    private boolean isBerlinGroupVersionObsolete(Map<String, Link> links, String linkName) {
        return links.containsKey(linkName);
    }

    private void modifyLinksToActualVersion(Map<String, Link> links, String linkToRemove, String linkToAdd, Function<String, String> linkModifier) {
        Link authoriseTransactionLink = links.get(linkToRemove);
        Link startAuthorisationLink = new Link(linkModifier.apply(authoriseTransactionLink.getHref()));

        links.remove(linkToRemove);
        links.put(linkToAdd, startAuthorisationLink);
    }

    private String buildStartAuthorisationUri(String baseUri) {
        return StringUri.fromElements(baseUri, AUTHORISATIONS);
    }

    private String buildUpdatePsuDataUri(String baseUri) {
        Map<String, String> queryParams = StringUri.getQueryParamsFromUri(baseUri);

        String authenticationCurrentNumber = queryParams.get(AUTHENTICATION_CURRENT_NUMBER_QUERY_PARAM);

        if (authenticationCurrentNumber == null) {
            // TODO come up with better solution
            throw new RuntimeException();
        }

        return StringUri.fromElements(baseUri, AUTHORISATIONS, authenticationCurrentNumber);
    }
}
