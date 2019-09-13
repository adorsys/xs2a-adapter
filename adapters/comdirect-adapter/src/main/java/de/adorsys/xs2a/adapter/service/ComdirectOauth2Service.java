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

package de.adorsys.xs2a.adapter.service;

import de.adorsys.xs2a.adapter.adapter.AbstractService;
import de.adorsys.xs2a.adapter.adapter.mapper.TokenResponseMapper;
import de.adorsys.xs2a.adapter.adapter.model.OauthToken;
import de.adorsys.xs2a.adapter.http.StringUri;
import de.adorsys.xs2a.adapter.service.model.TokenResponse;
import org.mapstruct.factory.Mappers;

import java.net.URI;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ComdirectOauth2Service extends AbstractService implements Oauth2Service {
    private final TokenResponseMapper tokenResponseMapper = Mappers.getMapper(TokenResponseMapper.class);
    private final String baseUrl;

    public ComdirectOauth2Service(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Override
    public URI getAuthorizationRequestUri(Map<String, String> headers, String state,
                                          URI redirectUri) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TokenResponse getToken(Map<String, String> headers, String authorizationCode, URI redirectUri,
                                  String clientId) {

        String url = StringUri.fromElements(baseUrl, "/v1/token");

        Map<String, String> params = new HashMap<>();
        params.put("redirect_uri", redirectUri.toString());
        params.put("client_id", clientId);
        params.put("grant_type", "authorization_code");
        params.put("code", authorizationCode);
        params.put("code_verifier", "dBjftJeZ4CVP-mB92K27uhbUJU1p1r_wW1gFWFOEjXk");

        Response<OauthToken> response = httpClient.postForm(url, Collections.emptyMap(), params,
                                                            jsonResponseHandler(OauthToken.class));
        return tokenResponseMapper.map(response.getBody());
    }
}
