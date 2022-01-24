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

package de.adorsys.xs2a.adapter.comdirect;

import de.adorsys.xs2a.adapter.api.Oauth2Service;
import de.adorsys.xs2a.adapter.api.model.TokenResponse;
import de.adorsys.xs2a.adapter.test.ServiceWireMockTest;
import de.adorsys.xs2a.adapter.test.TestRequestResponse;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.LinkedHashMap;

import static org.assertj.core.api.Assertions.assertThat;

@ServiceWireMockTest(ComdirectServiceProvider.class)
class ComdirectOauth2ServiceWireMockTest {

    private final Oauth2Service oauth2Service;

    ComdirectOauth2ServiceWireMockTest(Oauth2Service oauth2Service) {
        this.oauth2Service = oauth2Service;
    }

    @Test
    void getAccessToken() throws IOException {
        var requestResponse = new TestRequestResponse("oauth2/get-access-token.json");

        var modifiableParams = new LinkedHashMap<>(requestResponse.requestParams().toMap());

        var actualToken = oauth2Service.getToken(requestResponse.requestHeaders().toMap(),
            new Oauth2Service.Parameters(modifiableParams));

        assertThat(actualToken)
            .isNotNull()
            .isEqualTo(requestResponse.responseBody(TokenResponse.class));
    }
}
