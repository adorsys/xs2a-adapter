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

package de.adorsys.xs2a.adapter.rest.impl.controller;

import de.adorsys.xs2a.adapter.api.EmbeddedPreAuthorisationService;
import de.adorsys.xs2a.adapter.api.RequestHeaders;
import de.adorsys.xs2a.adapter.api.model.TokenResponse;
import de.adorsys.xs2a.adapter.mapper.EmbeddedPreAuthorisationMapper;
import de.adorsys.xs2a.adapter.mapper.Oauth2Mapper;
import de.adorsys.xs2a.adapter.rest.api.EmbeddedPreAuthorisationApi;
import de.adorsys.xs2a.adapter.rest.api.model.EmbeddedPreAuthorisationRequestTO;
import de.adorsys.xs2a.adapter.rest.api.model.TokenResponseTO;
import org.mapstruct.factory.Mappers;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class EmbeddedPreAuthorisationController implements EmbeddedPreAuthorisationApi {

    private final EmbeddedPreAuthorisationService preAuthorisationService;
    private final Oauth2Mapper mapper = Mappers.getMapper(Oauth2Mapper.class);
    private final EmbeddedPreAuthorisationMapper preAuthorisationMapper = Mappers.getMapper(EmbeddedPreAuthorisationMapper.class);

    public EmbeddedPreAuthorisationController(EmbeddedPreAuthorisationService preAuthorisationService) {
        this.preAuthorisationService = preAuthorisationService;
    }

    @Override
    public TokenResponseTO getToken(Map<String, String> headers, EmbeddedPreAuthorisationRequestTO request) {
        RequestHeaders requestHeaders = RequestHeaders.fromMap(headers);
        TokenResponse tokenResponse
            = preAuthorisationService.getToken(preAuthorisationMapper.toEmbeddedPreAuthorisationRequest(request), requestHeaders);
        return mapper.map(tokenResponse);
    }
}
