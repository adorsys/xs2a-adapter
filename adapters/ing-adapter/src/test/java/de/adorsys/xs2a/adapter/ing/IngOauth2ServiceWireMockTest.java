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

package de.adorsys.xs2a.adapter.ing;

import de.adorsys.xs2a.adapter.api.Oauth2Service;
import de.adorsys.xs2a.adapter.api.model.TokenResponse;
import de.adorsys.xs2a.adapter.test.ServiceWireMockTest;
import de.adorsys.xs2a.adapter.test.TestRequestResponse;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.util.HashMap;
import java.util.LinkedHashMap;

import static org.assertj.core.api.Assertions.assertThat;

@ServiceWireMockTest(IngServiceProvider.class)
class IngOauth2ServiceWireMockTest {

    private final Oauth2Service service;

    IngOauth2ServiceWireMockTest(Oauth2Service service) {
        this.service = service;
    }

    @Test
    void getAuthorizationUriJson() throws Exception {
        TestRequestResponse requestResponse = new TestRequestResponse("oauth2/get-authorization-uri.json");

        URI authorizationRequestUri = service.getAuthorizationRequestUri(requestResponse.requestHeaders().toMap(),
            new Oauth2Service.Parameters(new HashMap<>(requestResponse.requestParams().toMap())));

        URI expectedUri = requestResponse.responseBody(URI.class);
        // not comparing two URIs directly because of the dynamic wiremock port
        assertThat(authorizationRequestUri.getPath()).isEqualTo(expectedUri.getPath());
        assertThat(authorizationRequestUri.getQuery()).isEqualTo(expectedUri.getQuery());
    }

    @Test
    void getToken() throws Exception {
        TestRequestResponse requestResponse = new TestRequestResponse("oauth2/get-token.json");

        TokenResponse tokenResponse = service.getToken(requestResponse.requestHeaders().toMap(),
            new Oauth2Service.Parameters(new LinkedHashMap<>(requestResponse.requestParams().toMap())));

        assertThat(tokenResponse).isEqualTo(requestResponse.responseBody(TokenResponse.class));
    }
}
