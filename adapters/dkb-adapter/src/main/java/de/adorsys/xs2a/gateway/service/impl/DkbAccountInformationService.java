/*
 * Copyright 2018-2018 adorsys GmbH & Co KG
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.adorsys.xs2a.gateway.service.impl;

import de.adorsys.xs2a.gateway.adapter.BaseAccountInformationService;
import de.adorsys.xs2a.gateway.http.StringUri;
import de.adorsys.xs2a.gateway.security.AccessTokenService;
import de.adorsys.xs2a.gateway.service.GeneralResponse;
import de.adorsys.xs2a.gateway.service.RequestHeaders;
import de.adorsys.xs2a.gateway.service.StartScaProcessResponse;
import de.adorsys.xs2a.gateway.service.ais.ConsentCreationResponse;
import de.adorsys.xs2a.gateway.service.ais.Consents;
import de.adorsys.xs2a.gateway.service.impl.mapper.ConsentCreationResponseMapper;
import de.adorsys.xs2a.gateway.service.impl.mapper.StartScaProcessResponseMapper;
import de.adorsys.xs2a.gateway.service.impl.model.DkbConsentCreationResponse;
import de.adorsys.xs2a.gateway.service.impl.model.DkbStartScaProcessResponse;
import de.adorsys.xs2a.gateway.service.model.UpdatePsuAuthentication;

import java.util.Map;

public class DkbAccountInformationService extends BaseAccountInformationService {
    private AccessTokenService accessService;

    public DkbAccountInformationService(String baseUri, AccessTokenService accessService) {
        super(baseUri);
        this.accessService = accessService;
    }

    @Override
    public GeneralResponse<ConsentCreationResponse> createConsent(Consents body, RequestHeaders requestHeaders) {
        Map<String, String> headersMap = populatePostHeaders(requestHeaders.toMap());

        String bodyString = jsonMapper.writeValueAsString(jsonMapper.convertValue(body, Consents.class));

        GeneralResponse<DkbConsentCreationResponse> response = httpClient.post(getConsentBaseUri(), bodyString, headersMap,
                                                                               responseHandler(DkbConsentCreationResponse.class));
        ConsentCreationResponse entity = ConsentCreationResponseMapper.INSTANCE.toBGEntity(response.getResponseBody());
        return new GeneralResponse<>(response.getStatusCode(), entity, response.getResponseHeaders());
    }

    @Override
    public GeneralResponse<StartScaProcessResponse> startConsentAuthorisation(String consentId, RequestHeaders requestHeaders) {
        String uri = StringUri.fromElements(getConsentBaseUri(), consentId, AUTHORISATIONS);
        Map<String, String> headersMap = populatePostHeaders(requestHeaders.toMap());

        GeneralResponse<DkbStartScaProcessResponse> response = httpClient.post(uri, headersMap, responseHandler(DkbStartScaProcessResponse.class));
        StartScaProcessResponse entity = StartScaProcessResponseMapper.INSTANCE.toBGEntity(response.getResponseBody());
        return new GeneralResponse<>(response.getStatusCode(), entity, response.getResponseHeaders());
    }

    @Override
    public GeneralResponse<StartScaProcessResponse> startConsentAuthorisation(String consentId, RequestHeaders requestHeaders, UpdatePsuAuthentication updatePsuAuthentication) {
        String uri = StringUri.fromElements(getConsentBaseUri(), consentId, AUTHORISATIONS);
        Map<String, String> headersMap = populatePostHeaders(requestHeaders.toMap());
        String body = jsonMapper.writeValueAsString(updatePsuAuthentication);

        GeneralResponse<DkbStartScaProcessResponse> response = httpClient.post(uri, body, headersMap, responseHandler(DkbStartScaProcessResponse.class));
        StartScaProcessResponse entity = StartScaProcessResponseMapper.INSTANCE.toBGEntity(response.getResponseBody());
        return new GeneralResponse<>(response.getStatusCode(), entity, response.getResponseHeaders());
    }

    @Override
    protected Map<String, String> populatePostHeaders(Map<String, String> map) {
        return addBearerHeader(map);
    }

    @Override
    protected Map<String, String> populatePutHeaders(Map<String, String> map) {
        return addBearerHeader(map);
    }

    @Override
    protected Map<String, String> populateGetHeaders(Map<String, String> map) {
        return addBearerHeader(map);
    }

    Map<String, String> addBearerHeader(Map<String, String> map) {
        map.put("Authorization", "Bearer " + accessService.retrieveToken());
        return map;
    }
}