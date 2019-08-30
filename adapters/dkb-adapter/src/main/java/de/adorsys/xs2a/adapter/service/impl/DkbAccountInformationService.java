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

package de.adorsys.xs2a.adapter.service.impl;

import de.adorsys.xs2a.adapter.adapter.BaseAccountInformationService;
import de.adorsys.xs2a.adapter.security.AccessTokenService;
import de.adorsys.xs2a.adapter.service.GeneralResponse;
import de.adorsys.xs2a.adapter.service.RequestHeaders;
import de.adorsys.xs2a.adapter.service.model.StartScaProcessResponse;
import de.adorsys.xs2a.adapter.service.model.ConsentCreationResponse;
import de.adorsys.xs2a.adapter.service.model.Consents;
import de.adorsys.xs2a.adapter.service.impl.mapper.ConsentCreationResponseMapper;
import de.adorsys.xs2a.adapter.service.impl.mapper.StartScaProcessResponseMapper;
import de.adorsys.xs2a.adapter.service.impl.model.DkbConsentCreationResponse;
import de.adorsys.xs2a.adapter.service.impl.model.DkbStartScaProcessResponse;
import de.adorsys.xs2a.adapter.service.model.UpdatePsuAuthentication;
import org.mapstruct.factory.Mappers;

import java.util.Map;

public class DkbAccountInformationService extends BaseAccountInformationService {
    private final StartScaProcessResponseMapper startScaProcessResponseMapper = Mappers.getMapper(StartScaProcessResponseMapper.class);
    private final ConsentCreationResponseMapper creationResponseMapper = Mappers.getMapper(ConsentCreationResponseMapper.class);
    private AccessTokenService accessService;

    public DkbAccountInformationService(String baseUri, AccessTokenService accessService) {
        super(baseUri);
        this.accessService = accessService;
    }

    @Override
    public GeneralResponse<ConsentCreationResponse> createConsent(RequestHeaders requestHeaders, Consents body) {
        return createConsent(requestHeaders, body, DkbConsentCreationResponse.class, creationResponseMapper::toConsentCreationResponse);
    }

    @Override
    public GeneralResponse<StartScaProcessResponse> startConsentAuthorisation(String consentId, RequestHeaders requestHeaders) {
        return startConsentAuthorisation(consentId, requestHeaders, DkbStartScaProcessResponse.class, startScaProcessResponseMapper::toStartScaProcessResponse);
    }

    @Override
    public GeneralResponse<StartScaProcessResponse> startConsentAuthorisation(String consentId, RequestHeaders requestHeaders, UpdatePsuAuthentication updatePsuAuthentication) {
        return startConsentAuthorisation(consentId, requestHeaders, updatePsuAuthentication, DkbStartScaProcessResponse.class, startScaProcessResponseMapper::toStartScaProcessResponse);
    }

    @Override
    protected Map<String, String> populatePostHeaders(Map<String, String> map) {
        return addBearerHeader(map);
    }

    @Override
    protected Map<String, String> populatePutHeaders(Map<String, String> headers) {
        return addBearerHeader(headers);
    }

    @Override
    protected Map<String, String> populateGetHeaders(Map<String, String> headers) {
        return addBearerHeader(headers);
    }

    Map<String, String> addBearerHeader(Map<String, String> map) {
        map.put("Authorization", "Bearer " + accessService.retrieveToken());
        return map;
    }
}
