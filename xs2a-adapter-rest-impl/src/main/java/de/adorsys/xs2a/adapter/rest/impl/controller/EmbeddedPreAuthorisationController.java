/*
 * Copyright 2018-2022 adorsys GmbH & Co KG
 *
 * This program is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or (at
 * your option) any later version. This program is distributed in the hope that
 * it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see https://www.gnu.org/licenses/.
 *
 * This project is also available under a separate commercial license. You can
 * contact us at psd2@adorsys.com.
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
