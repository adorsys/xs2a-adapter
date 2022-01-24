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

import de.adorsys.xs2a.adapter.api.Oauth2Service;
import de.adorsys.xs2a.adapter.api.model.HrefType;
import de.adorsys.xs2a.adapter.api.model.TokenResponse;
import de.adorsys.xs2a.adapter.remote.client.Oauth2Client;
import de.adorsys.xs2a.adapter.rest.api.model.TokenResponseTO;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.doReturn;

class RemoteOauth2ServiceTest {

    private Oauth2Client oauth2Client = Mockito.mock(Oauth2Client.class);
    private final RemoteOauth2Service service = new RemoteOauth2Service(oauth2Client);


    @Test
    void getAuthorizationRequestUri() throws IOException {
        Oauth2Service.Parameters parameters = new Oauth2Service.Parameters();
        String authUrl = "http://localhost:8080/auth";

        doReturn(buildHref(authUrl)).when(oauth2Client).getAuthorizationUrl(anyMap(), any());

        URI uri = service.getAuthorizationRequestUri(new HashMap<>(), parameters);

        assertThat(uri).isEqualTo(URI.create(authUrl));
    }

    @Test
    void getAuthorizationRequestUriWithException() throws IOException {
        HashMap<String, String> headers = new HashMap<>();
        Oauth2Service.Parameters parameters = new Oauth2Service.Parameters();

        doReturn(buildHref("blablabla_unexisting_uri@2#^"))
            .when(oauth2Client)
            .getAuthorizationUrl(anyMap(), any());


        assertThrows(IOException.class, () -> service.getAuthorizationRequestUri(headers, parameters));
    }

    private HrefType buildHref(String authUrl) {
        HrefType hrefType = new HrefType();
        hrefType.setHref(authUrl);
        return hrefType;
    }

    @Test
    void getToken() throws IOException {

        Oauth2Service.Parameters parameters = new Oauth2Service.Parameters();
        String accessToken = "access-token";
        String tokenType = "token-type";
        long expires = 60L;
        String scope = "test-scope";
        String refreshToken = "refresh-token";

        TokenResponseTO tokenTO = new TokenResponseTO();
        tokenTO.setAccessToken(accessToken);
        tokenTO.setTokenType(tokenType);
        tokenTO.setExpiresInSeconds(expires);
        tokenTO.setRefreshToken(refreshToken);
        tokenTO.setScope(scope);

        doReturn(tokenTO).when(oauth2Client).getToken(anyMap(), any());

        TokenResponse token = service.getToken(new HashMap<>(), parameters);

        assertThat(token.getAccessToken()).isEqualTo(accessToken);
        assertThat(token.getRefreshToken()).isEqualTo(refreshToken);
        assertThat(token.getTokenType()).isEqualTo(tokenType);
        assertThat(token.getExpiresInSeconds()).isEqualTo(expires);
        assertThat(token.getScope()).isEqualTo(scope);
    }
}
