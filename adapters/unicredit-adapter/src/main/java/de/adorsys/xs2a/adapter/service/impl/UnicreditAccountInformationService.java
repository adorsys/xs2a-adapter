package de.adorsys.xs2a.adapter.service.impl;

import de.adorsys.xs2a.adapter.adapter.BaseAccountInformationService;
import de.adorsys.xs2a.adapter.http.ContentType;
import de.adorsys.xs2a.adapter.http.HttpClient;
import de.adorsys.xs2a.adapter.http.StringUri;
import de.adorsys.xs2a.adapter.service.RequestHeaders;
import de.adorsys.xs2a.adapter.service.RequestParams;
import de.adorsys.xs2a.adapter.service.Response;
import de.adorsys.xs2a.adapter.service.impl.mapper.UpdatePsuAuthenticationResponseUnicreditMapper;
import de.adorsys.xs2a.adapter.service.impl.model.UnicreditStartScaProcessResponse;
import de.adorsys.xs2a.adapter.service.impl.service.UnicreditCreateConsentResponseLinkModifierService;
import de.adorsys.xs2a.adapter.service.model.*;

import java.util.Map;
import java.util.function.Function;

import static de.adorsys.xs2a.adapter.http.ResponseHandlers.jsonResponseHandler;

public class UnicreditAccountInformationService extends BaseAccountInformationService {

    private static final String REDIRECT_URI = "https://example.com";
    private static final String AUTHENTICATION_CURRENT_NUMBER = "authenticationCurrentNumber";
    private static final String VALUE = "400";

    private UpdatePsuAuthenticationResponseUnicreditMapper unicreditMapper = new UpdatePsuAuthenticationResponseUnicreditMapper();
    private UnicreditCreateConsentResponseLinkModifierService unicreditCreateConsentResponseLinkModifierService = new UnicreditCreateConsentResponseLinkModifierService();

    public UnicreditAccountInformationService(Aspsp aspsp, HttpClient httpClient) {
        super(aspsp, httpClient);
    }

    @Override
    protected <T> Response<ConsentCreationResponse> createConsent(RequestHeaders requestHeaders, Consents body, Function<T, ConsentCreationResponse> mapper, HttpClient.ResponseHandler<T> responseHandler) {
        Map<String, String> headersMap = populatePostHeaders(requestHeaders.toMap());
        headersMap = addPsuIdHeader(headersMap);

        String bodyString = jsonMapper.writeValueAsString(jsonMapper.convertValue(body, Consents.class));

        Response<T> response = httpClient.post(getConsentBaseUri())
            .jsonBody(bodyString)
            .headers(headersMap)
            .send(requestBuilderInterceptor, responseHandler);
        ConsentCreationResponse creationResponse = mapper.apply(response.getBody());
        return new Response<>(response.getStatusCode(), unicreditCreateConsentResponseLinkModifierService.modifyResponse(creationResponse), response.getHeaders());
    }

    @Override
    public Response<UpdatePsuAuthenticationResponse> updateConsentsPsuData(String consentId, String authorisationId, RequestHeaders requestHeaders, UpdatePsuAuthentication updatePsuAuthentication) {
        String uri = StringUri.fromElements(getConsentBaseUri(), consentId, AUTHORISATIONS, authorisationId);
        Map<String, String> headersMap = populatePutHeaders(requestHeaders.toMap());
        String body = jsonMapper.writeValueAsString(updatePsuAuthentication);

        Response<UnicreditStartScaProcessResponse> response = httpClient.put(uri)
            .jsonBody(body)
            .headers(headersMap)
            .send(jsonResponseHandler(UnicreditStartScaProcessResponse.class));

        return new Response<>(response.getStatusCode(), unicreditMapper.modifyResponse(response.getBody()), response.getHeaders());
    }

    @Override
    protected <T> Response<ScaStatusResponse> updateConsentsPsuData(String consentId, String authorisationId, RequestHeaders requestHeaders, TransactionAuthorisation transactionAuthorisation, Class<T> klass, Function<T, ScaStatusResponse> mapper) {
        String uri = StringUri.appendQueryParam(getUpdateConsentPsuDataUri(consentId, authorisationId), AUTHENTICATION_CURRENT_NUMBER, VALUE);
        Map<String, String> headersMap = populatePutHeaders(requestHeaders.toMap());
        String body = jsonMapper.writeValueAsString(transactionAuthorisation);

        Response<T> response = httpClient.put(uri)
            .jsonBody(body)
            .headers(headersMap)
            .send(requestBuilderInterceptor, jsonResponseHandler(klass));

        ScaStatusResponse scaStatusResponse = mapper.apply(response.getBody());
        return new Response<>(response.getStatusCode(), scaStatusResponse, response.getHeaders());
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
}
