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

package de.adorsys.xs2a.adapter.commerzbank;

import de.adorsys.xs2a.adapter.api.Oauth2Service;
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
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

class CommerzbankOauth2ServiceTest {
    private static final String ORG_ID = "PSDDE-BAFIN-999999";
    private static final String BASE_URL = "https://psd2.api-sandbox.commerzbank.com/berlingroup";
    private static final String CONSENT_ID = "consent-id";
    private static final String STATE = "test";
    private static final String REDIRECT_URI = "https://example.com/cb";
    private static final String SCA_OAUTH_LINK = "https://psd2.api.commerzbank.com/berlingroup/.well-known";
    private static final String AUTHORIZATION_ENDPOINT =
        "https://psd2.redirect.commerzbank.com/public/berlingroup/authorize/authorisation-id";
    private static final String TOKEN_ENDPOINT = BASE_URL + "/v1/token";
    private static final String AUTHORIZATION_CODE = "authorization-code";


    private HttpClient httpClient;
    private CommerzbankOauth2Service oauth2Service;

    @BeforeEach
    void setUp() throws Exception {
        Aspsp aspsp = new Aspsp();
        aspsp.setUrl(BASE_URL);

        httpClient = spy(new ApacheHttpClient(null, null));
        doReturn(authorizationServerMetadata())
            .when(httpClient).send(argThat(req -> req.uri().equals(SCA_OAUTH_LINK)), any());
        doReturn(new Response<>(200, new TokenResponse(), ResponseHeaders.emptyResponseHeaders()))
            .when(httpClient).send(argThat(req -> req.uri().equals(TOKEN_ENDPOINT)), any());

        Pkcs12KeyStore keyStore = mock(Pkcs12KeyStore.class);
        when(keyStore.getOrganizationIdentifier())
            .thenReturn(ORG_ID);

        HttpClientFactory httpClientFactory = mock(HttpClientFactory.class);
        HttpClientConfig httpClientConfig = mock(HttpClientConfig.class);
        when(httpClientFactory.getHttpClient(any())).thenReturn(httpClient);
        when(httpClientFactory.getHttpClientConfig()).thenReturn(httpClientConfig);
        when(httpClientConfig.getKeyStore()).thenReturn(keyStore);

        oauth2Service = CommerzbankOauth2Service.create(aspsp, httpClientFactory);
    }

    private Response<AuthorisationServerMetaData> authorizationServerMetadata() {
        AuthorisationServerMetaData metadata = new AuthorisationServerMetaData();
        metadata.setAuthorisationEndpoint(AUTHORIZATION_ENDPOINT);
        metadata.setTokenEndpoint(TOKEN_ENDPOINT);
        return new Response<>(200, metadata, ResponseHeaders.emptyResponseHeaders());
    }

    @Test
    void getAuthorizationRequestUri() throws IOException {
        Parameters parameters = new Parameters();
        parameters.setScaOAuthLink(SCA_OAUTH_LINK);
        parameters.setState(STATE);
        parameters.setRedirectUri(REDIRECT_URI);
        parameters.setConsentId(CONSENT_ID);

        URI uri = oauth2Service.getAuthorizationRequestUri(null, parameters);

        assertEquals(AUTHORIZATION_ENDPOINT + "?" +
            "response_type=code&" +
            "state=" + STATE + "&" +
            "redirect_uri=" + URLEncoder.encode(REDIRECT_URI, StandardCharsets.UTF_8.name()) + "&" +
            "client_id=" + ORG_ID + "&" +
            "code_challenge_method=S256&" +
            "code_challenge=" + oauth2Service.codeChallenge() + "&" +
            "scope=AIS%3A" + CONSENT_ID, uri.toString());
    }

    @Test
    void getAuthorizationRequestUriForPayment() throws IOException {
        Parameters parameters = new Parameters();
        parameters.setScaOAuthLink(SCA_OAUTH_LINK);
        parameters.setState(STATE);
        parameters.setRedirectUri(REDIRECT_URI);
        parameters.setPaymentId("payment-id");

        URI uri = oauth2Service.getAuthorizationRequestUri(null, parameters);

        assertEquals(AUTHORIZATION_ENDPOINT + "?" +
            "response_type=code&" +
            "state=" + STATE + "&" +
            "redirect_uri=" + URLEncoder.encode(REDIRECT_URI, StandardCharsets.UTF_8.name()) + "&" +
            "client_id=" + ORG_ID + "&" +
            "code_challenge_method=S256&" +
            "code_challenge=" + oauth2Service.codeChallenge() + "&" +
            "scope=PIS%3Apayment-id", uri.toString());
    }

    @Test
    void getToken() throws IOException {
        Parameters parameters = new Parameters();
        parameters.setGrantType(Oauth2Service.GrantType.AUTHORIZATION_CODE.toString());
        parameters.setAuthorizationCode(AUTHORIZATION_CODE);
        parameters.setRedirectUri(REDIRECT_URI);

        oauth2Service.getToken(null, parameters);

        Map<String, String> expectedBody = new HashMap<>();
        expectedBody.put(Parameters.GRANT_TYPE, Oauth2Service.GrantType.AUTHORIZATION_CODE.toString());
        expectedBody.put(Parameters.CODE, AUTHORIZATION_CODE);
        expectedBody.put(Parameters.CLIENT_ID, ORG_ID);
        expectedBody.put(Parameters.REDIRECT_URI, REDIRECT_URI);
        expectedBody.put(Parameters.CODE_VERIFIER, oauth2Service.codeVerifier());
        Mockito.verify(httpClient, Mockito.times(1)).send(argThat(req -> {
            boolean tokenExchange = req.uri().equals(TOKEN_ENDPOINT);
            if (tokenExchange) {
                assertEquals(expectedBody, req.urlEncodedBody());
            }
            return tokenExchange;
        }), any());
    }
}
