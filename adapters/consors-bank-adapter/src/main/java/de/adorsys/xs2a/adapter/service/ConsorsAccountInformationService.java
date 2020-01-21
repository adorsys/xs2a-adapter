package de.adorsys.xs2a.adapter.service;

import de.adorsys.xs2a.adapter.adapter.BaseAccountInformationService;
import de.adorsys.xs2a.adapter.http.HttpClient;
import de.adorsys.xs2a.adapter.service.model.Aspsp;
import de.adorsys.xs2a.adapter.service.model.ConsentCreationResponse;
import de.adorsys.xs2a.adapter.service.model.Consents;

import java.util.Map;
import java.util.function.Function;

public class ConsorsAccountInformationService extends BaseAccountInformationService {

    public ConsorsAccountInformationService(Aspsp aspsp, HttpClient httpClient) {
        super(aspsp, httpClient);
    }

    @Override
    protected <T> Response<ConsentCreationResponse> createConsent(RequestHeaders requestHeaders, Consents body, Function<T, ConsentCreationResponse> mapper, HttpClient.ResponseHandler<T> responseHandler) {
        Map<String, String> headersMap = populatePostHeaders(requestHeaders.toMap());
        headersMap = managePsuIdHeader(headersMap);

        String bodyString = jsonMapper.writeValueAsString(jsonMapper.convertValue(body, Consents.class));

        Response<T> response = httpClient.post(getConsentBaseUri())
            .jsonBody(bodyString)
            .headers(headersMap)
            .send(requestBuilderInterceptor, responseHandler);
        ConsentCreationResponse creationResponse = mapper.apply(response.getBody());
        return new Response<>(response.getStatusCode(), creationResponse, response.getHeaders());
    }

    private Map<String, String> managePsuIdHeader(Map<String, String> headers) {
        if (headers.containsKey(RequestHeaders.PSU_ID) && headers.get(RequestHeaders.PSU_ID) != null && headers.get(RequestHeaders.PSU_ID).isEmpty()) {
            headers.replace(RequestHeaders.PSU_ID, null);
        }

        return headers;
    }
}
