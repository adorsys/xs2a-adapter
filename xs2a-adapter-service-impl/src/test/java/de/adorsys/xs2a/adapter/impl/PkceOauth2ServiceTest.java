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

package de.adorsys.xs2a.adapter.impl;

import de.adorsys.xs2a.adapter.api.Oauth2Service;
import de.adorsys.xs2a.adapter.api.Oauth2Service.GrantType;
import de.adorsys.xs2a.adapter.api.Oauth2Service.Parameters;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.net.URI;

class PkceOauth2ServiceTest {

    Oauth2Service oauth2Service = Mockito.mock(Oauth2Service.class);
    PkceOauth2Service pkceOauth2Service = new PkceOauth2Service(oauth2Service) {
        @Override
        public String codeChallenge() {
            return "1234";
        }
    };
    Parameters parameters = new Parameters();

    @Test
    void codeVerifierParameterAddedForAuthorizationCodeExchange() throws IOException {
        parameters.setGrantType(GrantType.AUTHORIZATION_CODE.toString());

        pkceOauth2Service.getToken(null, parameters);

        Mockito.verify(oauth2Service, Mockito.times(1))
            .getToken(Mockito.any(), Mockito.argThat(p -> p.getCodeVerifier() != null &&
                p.getCodeVerifier().equals(pkceOauth2Service.codeVerifier())));
    }

    @Test
    void codeVerifierParameterCanBeOverridden() throws IOException {
        parameters.setGrantType(GrantType.AUTHORIZATION_CODE.toString());
        String codeVerifier = "code-verifier";
        parameters.setCodeVerifier(codeVerifier);

        pkceOauth2Service.getToken(null, parameters);

        Mockito.verify(oauth2Service, Mockito.times(1))
            .getToken(Mockito.any(), Mockito.argThat(p -> p.getCodeVerifier() != null &&
                p.getCodeVerifier().equals(codeVerifier)));
    }

    @Test
    void codeVerifierParameterIsNotAddedForTokenRefresh() throws IOException {
        parameters.setGrantType(GrantType.REFRESH_TOKEN.toString());

        pkceOauth2Service.getToken(null, parameters);

        Mockito.verify(oauth2Service, Mockito.times(1))
            .getToken(Mockito.any(), Mockito.argThat(p -> p.getCodeVerifier() == null));
    }

    @Test
    void getAuthorizationRequestUri() throws IOException {
        String authorisationRequestUri = "https://authorisation.endpoint?" +
            "response_type=code&state=state&redirect_uri=https%3A%2F%2Fredirect.uri";
        String expectedOutput = authorisationRequestUri + "&" +
            Parameters.CODE_CHALLENGE_METHOD + "=S256&" +
            Parameters.CODE_CHALLENGE + "=" + pkceOauth2Service.codeChallenge();

        Mockito.when(oauth2Service.getAuthorizationRequestUri(Mockito.any(), Mockito.any()))
            .thenReturn(URI.create(authorisationRequestUri));

        URI actual = pkceOauth2Service.getAuthorizationRequestUri(null, parameters);

        Mockito.verify(oauth2Service, Mockito.times(1))
            .getAuthorizationRequestUri(Mockito.any(), Mockito.any());

        Assertions.assertEquals(actual.toString(), expectedOutput);
    }

    @Test
    void getAuthorizationRequestUriWithOverriddenPkceParameters() throws IOException {
        String authorisationRequestUri = "https://authorisation.endpoint?" +
            "response_type=code&state=state&redirect_uri=https%3A%2F%2Fredirect.uri";
        String codeChallengeMethod = "plain";
        String codeChallenge = "code-challenge";
        String expectedOutput = authorisationRequestUri + "&" +
            Parameters.CODE_CHALLENGE_METHOD + "=" + codeChallengeMethod + "&" +
            Parameters.CODE_CHALLENGE + "=" + codeChallenge;
        parameters.setCodeChallengeMethod(codeChallengeMethod);
        parameters.setCodeChallenge(codeChallenge);

        Mockito.when(oauth2Service.getAuthorizationRequestUri(Mockito.any(), Mockito.any()))
            .thenReturn(URI.create(authorisationRequestUri));

        URI actual = pkceOauth2Service.getAuthorizationRequestUri(null, parameters);

        Mockito.verify(oauth2Service, Mockito.times(1))
            .getAuthorizationRequestUri(Mockito.any(), Mockito.any());

        Assertions.assertEquals(actual.toString(), expectedOutput);
    }
}
