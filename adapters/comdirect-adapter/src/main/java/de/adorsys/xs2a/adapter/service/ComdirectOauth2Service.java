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
import de.adorsys.xs2a.adapter.http.HttpClient;
import de.adorsys.xs2a.adapter.http.StringUri;
import de.adorsys.xs2a.adapter.service.model.Aspsp;
import de.adorsys.xs2a.adapter.service.model.TokenResponse;
import org.mapstruct.factory.Mappers;

import java.net.URI;
import java.util.Map;

import static de.adorsys.xs2a.adapter.http.ResponseHandlers.jsonResponseHandler;

public class ComdirectOauth2Service extends AbstractService implements Oauth2Service {
    private final TokenResponseMapper tokenResponseMapper = Mappers.getMapper(TokenResponseMapper.class);
    private final Aspsp aspsp;

    public ComdirectOauth2Service(Aspsp aspsp, HttpClient httpClient) {
        super(httpClient);
        this.aspsp = aspsp;
    }

    @Override
    public URI getAuthorizationRequestUri(Map<String, String> headers, Parameters parameters) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TokenResponse getToken(Map<String, String> headers, Parameters parameters) {
        String url = StringUri.fromElements(getBaseUrl(), "/v1/token");

        parameters.setCodeVerifier("dBjftJeZ4CVP-mB92K27uhbUJU1p1r_wW1gFWFOEjXk");

        Response<OauthToken> response = httpClient.post(url)
            .urlEncodedBody(parameters.asMap())
            .send(jsonResponseHandler(OauthToken.class));
        return tokenResponseMapper.map(response.getBody());
    }

    private String getBaseUrl() {
        return aspsp.getIdpUrl() != null ? aspsp.getIdpUrl() : aspsp.getUrl();
    }
}
