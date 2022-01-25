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

package de.adorsys.xs2a.adapter.adorsys;

import de.adorsys.xs2a.adapter.api.Oauth2Service.Parameters;
import de.adorsys.xs2a.adapter.api.Response;
import de.adorsys.xs2a.adapter.api.ResponseHeaders;
import de.adorsys.xs2a.adapter.api.http.HttpClient;
import de.adorsys.xs2a.adapter.api.http.HttpClientConfig;
import de.adorsys.xs2a.adapter.api.http.HttpClientFactory;
import de.adorsys.xs2a.adapter.api.http.Request;
import de.adorsys.xs2a.adapter.api.model.Aspsp;
import de.adorsys.xs2a.adapter.api.model.TokenResponse;
import de.adorsys.xs2a.adapter.api.oauth.Oauth2Api;
import de.adorsys.xs2a.adapter.api.validation.RequestValidationException;
import de.adorsys.xs2a.adapter.impl.http.StringUri;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.URI;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AdorsysIntegOauth2ServiceTest {
    private static final String IDP_URL = "https://example.com";
    private static final String AUTH_URL = "https://example.com/auth";
    private static final String TOKEN_URL = "https://example.com/token";
    private static final String REDIRECT_URI = "https://example.com/redirect";
    private static final String AUTHORISATION_CODE = "qwerty";
    private static final String AUTH_URL_WITH_REDIRECT_URI_PARAM
        = StringUri.withQuery(AUTH_URL, "redirect_uri", REDIRECT_URI);
    private static final String TOKEN_URL_WITH_AUTHORISATION_CODE
        = StringUri.withQuery(TOKEN_URL, "code", AUTHORISATION_CODE);
    private static final URI AUTH_REQUEST_URI = buildAuthRequestURI();
    private static final String ACCESS_TOKEN = "testAccessToken";
    private static final String TOKEN_TYPE = "testTokenType";
    private static final Long EXPIRES_IN_SECONDS = 300L;
    private static final String REFRESH_TOKEN = "testRefreshToken";
    private static final String SCOPE = "testScope";
    private static final int HTTP_CODE_OK = 200;
    private static final Response<TokenResponse> OAUTH_TOKEN_RESPONSE
        = new Response<>(HTTP_CODE_OK, buildTokenResponse(), ResponseHeaders.emptyResponseHeaders());

    private AdorsysIntegOauth2Service oauth2Service;

    @Mock
    private Aspsp aspsp;
    @Mock
    private Oauth2Api oauth2Api;
    @Mock
    private HttpClient httpClient;
    @Mock
    private Request.Builder requestBuilder;
    @Mock
    private HttpClientFactory httpClientFactory;
    @Mock
    private HttpClientConfig httpClientConfig;

    @BeforeEach
    void setUp() {
        when(httpClientFactory.getHttpClient(any())).thenReturn(httpClient);
        when(httpClientFactory.getHttpClientConfig()).thenReturn(httpClientConfig);

        oauth2Service = new AdorsysIntegOauth2Service(aspsp, httpClientFactory, oauth2Api);
    }

    @Test
    void getAuthorizationRequestUri_Failure_ScaOauthUrlIsNotProvided() {
        Parameters parameters = new Parameters(new HashMap<>());

        when(aspsp.getIdpUrl()).thenReturn(null);

        Assertions.assertThrows(
            RequestValidationException.class,
            () -> {
                oauth2Service.getAuthorizationRequestUri(null, parameters);
            }
        );
    }

    @Test
    void getAuthorizationRequestUri_Failure_ScaOauthUrlIsEmptyParam() {
        Parameters parameters = new Parameters(new HashMap<>());
        parameters.setScaOAuthLink("  ");

        when(aspsp.getIdpUrl()).thenReturn(null);

        Assertions.assertThrows(
            RequestValidationException.class,
            () -> oauth2Service.getAuthorizationRequestUri(null, parameters)
        );
    }

    @Test
    void getAuthorizationRequestUri_Failure_ScaOauthUrlIsEmpty() {
        Parameters parameters = new Parameters(new HashMap<>());

        when(aspsp.getIdpUrl()).thenReturn("    ");

        Assertions.assertThrows(
            RequestValidationException.class,
            () -> oauth2Service.getAuthorizationRequestUri(null, parameters)
        );
    }

    @Test
    void getAuthorizationRequestUri_Success() {
        Parameters parameters = new Parameters(new HashMap<>());
        parameters.setRedirectUri(REDIRECT_URI);

        when(aspsp.getIdpUrl()).thenReturn(IDP_URL);
        when(oauth2Api.getAuthorisationUri(IDP_URL)).thenReturn(AUTH_URL);

        URI actual = oauth2Service.getAuthorizationRequestUri(null, parameters);

        assertThat(actual).isEqualTo(AUTH_REQUEST_URI);
    }

    @Test
    void getToken_Failure_ScaOauthUrlIsNotProvided() {
        Parameters parameters = new Parameters(new HashMap<>());

        when(aspsp.getIdpUrl()).thenReturn(null);

        Assertions.assertThrows(
            RequestValidationException.class,
            () -> oauth2Service.getToken(null, parameters)
        );
    }

    @Test
    void getToken_Failure_ScaOauthUrlIsEmptyParam() {
        Parameters parameters = new Parameters(new HashMap<>());
        parameters.setScaOAuthLink("  ");

        when(aspsp.getIdpUrl()).thenReturn(null);

        Assertions.assertThrows(
            RequestValidationException.class,
            () -> oauth2Service.getToken(null, parameters)
        );
    }

    @Test
    void getToken_Failure_ScaOauthUrlIsEmpty() {
        Parameters parameters = new Parameters(new HashMap<>());

        when(aspsp.getIdpUrl()).thenReturn("    ");

        Assertions.assertThrows(
            RequestValidationException.class,
            () -> oauth2Service.getToken(null, parameters)
        );
    }

    @Test
    void getToken_Failure_ScaOauthUrlHasWrongFormat() {
        Parameters parameters = new Parameters(new HashMap<>());

        when(aspsp.getIdpUrl()).thenReturn("wrong-idp-url");

        Assertions.assertThrows(
            RequestValidationException.class,
            () -> oauth2Service.getToken(null, parameters)
        );
    }

    @Test
    void getToken_Success() {
        Parameters parameters = new Parameters(new HashMap<>());
        parameters.setScaOAuthLink(IDP_URL);
        parameters.setAuthorizationCode(AUTHORISATION_CODE);

        when(oauth2Api.getTokenUri(IDP_URL))
            .thenReturn(TOKEN_URL);
        when(httpClient.post(TOKEN_URL_WITH_AUTHORISATION_CODE))
            .thenReturn(requestBuilder);
        when(requestBuilder.send(any(HttpClient.ResponseHandler.class)))
            .thenReturn(OAUTH_TOKEN_RESPONSE);

        TokenResponse tokenResponse = oauth2Service.getToken(null, parameters);

        assertThat(tokenResponse).isNotNull();
        assertThat(tokenResponse.getAccessToken()).isEqualTo(ACCESS_TOKEN);
        assertThat(tokenResponse.getTokenType()).isEqualTo(TOKEN_TYPE);
        assertThat(tokenResponse.getExpiresInSeconds()).isEqualTo(EXPIRES_IN_SECONDS);
        assertThat(tokenResponse.getRefreshToken()).isEqualTo(REFRESH_TOKEN);
        assertThat(tokenResponse.getScope()).isEqualTo(SCOPE);
    }

    private static URI buildAuthRequestURI() {
        return URI.create(AUTH_URL_WITH_REDIRECT_URI_PARAM);
    }

    private static TokenResponse buildTokenResponse() {
        TokenResponse tokenResponse = new TokenResponse();

        tokenResponse.setAccessToken(ACCESS_TOKEN);
        tokenResponse.setTokenType(TOKEN_TYPE);
        tokenResponse.setExpiresInSeconds(EXPIRES_IN_SECONDS);
        tokenResponse.setRefreshToken(REFRESH_TOKEN);
        tokenResponse.setScope(SCOPE);

        return tokenResponse;
    }
}
