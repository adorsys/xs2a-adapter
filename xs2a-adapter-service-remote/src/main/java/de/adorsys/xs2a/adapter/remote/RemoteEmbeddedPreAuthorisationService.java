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

package de.adorsys.xs2a.adapter.remote;

import de.adorsys.xs2a.adapter.api.EmbeddedPreAuthorisationService;
import de.adorsys.xs2a.adapter.api.RequestHeaders;
import de.adorsys.xs2a.adapter.api.model.EmbeddedPreAuthorisationRequest;
import de.adorsys.xs2a.adapter.api.model.TokenResponse;
import de.adorsys.xs2a.adapter.mapper.EmbeddedPreAuthorisationMapper;
import de.adorsys.xs2a.adapter.mapper.Oauth2Mapper;
import de.adorsys.xs2a.adapter.remote.client.EmbeddedPreAuthorisationClient;
import de.adorsys.xs2a.adapter.rest.api.model.TokenResponseTO;
import org.mapstruct.factory.Mappers;

public class RemoteEmbeddedPreAuthorisationService implements EmbeddedPreAuthorisationService {

    private final EmbeddedPreAuthorisationClient client;
    private final Oauth2Mapper oauth2Mapper = Mappers.getMapper(Oauth2Mapper.class);
    private final EmbeddedPreAuthorisationMapper embeddedPreAuthorisationMapper = Mappers.getMapper(EmbeddedPreAuthorisationMapper.class);

    public RemoteEmbeddedPreAuthorisationService(EmbeddedPreAuthorisationClient client) {
        this.client = client;
    }

    @Override
    public TokenResponse getToken(EmbeddedPreAuthorisationRequest request, RequestHeaders requestHeaders) {
        TokenResponseTO responseTO
            = client.getToken(requestHeaders.toMap(), embeddedPreAuthorisationMapper.toEmbeddedPreAuthorisationRequestTO(request));
        return oauth2Mapper.toTokenResponse(responseTO);
    }
}
