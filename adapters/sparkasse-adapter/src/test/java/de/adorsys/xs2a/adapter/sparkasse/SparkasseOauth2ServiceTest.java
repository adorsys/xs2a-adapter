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

package de.adorsys.xs2a.adapter.sparkasse;

import de.adorsys.xs2a.adapter.api.Oauth2Service.GrantType;
import de.adorsys.xs2a.adapter.api.Oauth2Service.Parameters;
import de.adorsys.xs2a.adapter.api.Pkcs12KeyStore;
import de.adorsys.xs2a.adapter.api.Response;
import de.adorsys.xs2a.adapter.api.ResponseHeaders;
import de.adorsys.xs2a.adapter.api.http.HttpClient;
import de.adorsys.xs2a.adapter.api.http.HttpClientConfig;
import de.adorsys.xs2a.adapter.api.http.HttpClientFactory;
import de.adorsys.xs2a.adapter.api.model.Aspsp;
import de.adorsys.xs2a.adapter.api.model.TokenResponse;
import de.adorsys.xs2a.adapter.impl.http.ApacheHttpClient;
import de.adorsys.xs2a.adapter.impl.oauth2.api.model.AuthorisationServerMetaData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

class SparkasseOauth2ServiceTest {
    private static final String ORG_ID = "PSDDE-BAFIN-999999";
    private static final String STATE = "xyz";
    private static final String AUTHORIZATION_ENDPOINT = "https://www.sparkasse.de/oauth/authorize";
    private static final String TOKEN_ENDPOINT = "https://www.sparkasse.de/oauth/token";
    private static final String SCA_OAUTH_LINK = "https://sparkasse.de/oauth/.well-known";
    private static final String CONSENT_ID = "consent-id";
    private static final String AUTHORIZATION_CODE = "authorization-code";
    private static final String REFRESH_TOKEN = "refresh-token";

    private final Pkcs12KeyStore keyStore = mock(Pkcs12KeyStore.class);
    private final HttpClientFactory httpClientFactory = mock(HttpClientFactory.class);
    private final HttpClientConfig httpClientConfig = mock(HttpClientConfig.class);
    private final HttpClient httpClient = spy(new ApacheHttpClient(null, null));
    private final Aspsp aspsp = new Aspsp();
    private SparkasseOauth2Service oauth2Service;

    @BeforeEach
    void setUp() throws Exception {
        when(keyStore.getOrganizationIdentifier())
            .thenReturn(ORG_ID);

        doReturn(authorizationServerMetadata())
            .when(httpClient).send(argThat(req -> req.uri().equals(SCA_OAUTH_LINK)), any());
        doReturn(tokenResponse())
            .when(httpClient).send(argThat(req -> req.uri().equals(TOKEN_ENDPOINT)), any());

        when(httpClientFactory.getHttpClient(any())).thenReturn(httpClient);
        when(httpClientFactory.getHttpClientConfig()).thenReturn(httpClientConfig);
        when(httpClientConfig.getKeyStore()).thenReturn(keyStore);

        oauth2Service = SparkasseOauth2Service.create(aspsp, httpClientFactory);
    }

    private Response<AuthorisationServerMetaData> authorizationServerMetadata() {
        AuthorisationServerMetaData metadata = new AuthorisationServerMetaData();
        metadata.setAuthorisationEndpoint(AUTHORIZATION_ENDPOINT);
        metadata.setTokenEndpoint(TOKEN_ENDPOINT);
        return new Response<>(200, metadata, ResponseHeaders.emptyResponseHeaders());
    }

    private Response<TokenResponse> tokenResponse() {
        return new Response<>(200, new TokenResponse(), ResponseHeaders.emptyResponseHeaders());
    }

    @Test
    void getAuthorizationRequestUri() throws IOException {

        Parameters parameters = new Parameters();
        parameters.setScaOAuthLink(SCA_OAUTH_LINK);
        parameters.setState(STATE);
        parameters.setConsentId(CONSENT_ID);

        URI uri = oauth2Service.getAuthorizationRequestUri(null, parameters);

        assertEquals(AUTHORIZATION_ENDPOINT + "?" +
            "responseType=code&" +
            "state=xyz&" +
            "clientId=" + ORG_ID + "&" +
            "code_challenge_method=S256&" +
            "code_challenge=" + oauth2Service.codeChallenge() + "&" +
            "scope=AIS%3A+" + CONSENT_ID, uri.toString());
    }

    @Test
    void getAuthorizationRequestUriForPayment() throws IOException {

        Parameters parameters = new Parameters();
        parameters.setScaOAuthLink(SCA_OAUTH_LINK);
        parameters.setState(STATE);
        parameters.setPaymentId("payment-id");

        URI uri = oauth2Service.getAuthorizationRequestUri(null, parameters);

        assertEquals(AUTHORIZATION_ENDPOINT + "?" +
            "responseType=code&" +
            "state=xyz&" +
            "clientId=" + ORG_ID + "&" +
            "code_challenge_method=S256&" +
            "code_challenge=" + oauth2Service.codeChallenge() + "&" +
            "scope=PIS%3A+payment-id", uri.toString());
    }

    @Test
    void getToken_authorizationCodeExchange() throws IOException {

        Parameters parameters = new Parameters();
        parameters.setScaOAuthLink(SCA_OAUTH_LINK);
        parameters.setGrantType(GrantType.AUTHORIZATION_CODE.toString());
        parameters.setAuthorizationCode(AUTHORIZATION_CODE);

        oauth2Service.getToken(null, parameters);

        Map<String, String> expectedBody = new HashMap<>();
        expectedBody.put(Parameters.GRANT_TYPE, GrantType.AUTHORIZATION_CODE.toString());
        expectedBody.put(Parameters.CODE, AUTHORIZATION_CODE);
        expectedBody.put(Parameters.CLIENT_ID, ORG_ID);
        expectedBody.put(Parameters.CODE_VERIFIER, oauth2Service.codeVerifier());
        Mockito.verify(httpClient, Mockito.times(1)).send(argThat(req -> {
            boolean tokenExchange = req.uri().equals(TOKEN_ENDPOINT);
            if (tokenExchange) {
                assertEquals(expectedBody, req.urlEncodedBody());
            }
            return tokenExchange;
        }), any());
    }

    @Test
    void getToken_refresh() throws IOException {

        Parameters parameters = new Parameters();
        parameters.setScaOAuthLink(SCA_OAUTH_LINK);
        parameters.setGrantType(GrantType.REFRESH_TOKEN.toString());
        parameters.setRefreshToken(REFRESH_TOKEN);

        oauth2Service.getToken(null, parameters);

        Map<String, String> expectedBody = new HashMap<>();
        expectedBody.put(Parameters.GRANT_TYPE, GrantType.REFRESH_TOKEN.toString());
        expectedBody.put(Parameters.REFRESH_TOKEN, REFRESH_TOKEN);
        expectedBody.put(Parameters.CLIENT_ID, ORG_ID);
        Mockito.verify(httpClient, Mockito.times(1)).send(argThat(req -> {
            boolean tokenExchange = req.uri().equals(TOKEN_ENDPOINT);
            if (tokenExchange) {
                assertEquals(expectedBody, req.urlEncodedBody());
            }
            return tokenExchange;
        }), any());
    }
}
