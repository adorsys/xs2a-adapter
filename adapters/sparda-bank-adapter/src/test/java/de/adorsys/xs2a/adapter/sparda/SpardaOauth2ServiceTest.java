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

package de.adorsys.xs2a.adapter.sparda;

import de.adorsys.xs2a.adapter.api.Oauth2Service.Parameters;
import de.adorsys.xs2a.adapter.api.Pkcs12KeyStore;
import de.adorsys.xs2a.adapter.api.Response;
import de.adorsys.xs2a.adapter.api.ResponseHeaders;
import de.adorsys.xs2a.adapter.api.http.HttpClient;
import de.adorsys.xs2a.adapter.api.http.HttpClientConfig;
import de.adorsys.xs2a.adapter.api.http.HttpClientFactory;
import de.adorsys.xs2a.adapter.api.model.Aspsp;
import de.adorsys.xs2a.adapter.api.model.Scope;
import de.adorsys.xs2a.adapter.api.validation.ValidationError;
import de.adorsys.xs2a.adapter.impl.http.ApacheHttpClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static de.adorsys.xs2a.adapter.api.Oauth2Service.GrantType.AUTHORIZATION_CODE;
import static de.adorsys.xs2a.adapter.api.Oauth2Service.GrantType.REFRESH_TOKEN;
import static de.adorsys.xs2a.adapter.sparda.SpardaOauth2Service.UNSUPPORTED_SCOPE_VALUE_ERROR_MESSAGE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class SpardaOauth2ServiceTest {
    public static final String BIC = "GENODEF1S08";
    public static final String AUTH_HOST = "https://idp.sparda-n.de";
    public static final String TOKEN_HOST = "https://idp.sparda.de";
    public static final String TOKEN_ENDPOINT = TOKEN_HOST + "/oauth2/token";
    public static final String CLIENT_ID = "client-id";
    public static final String REDIRECT_URI = "https://tpp.com/cb";
    public static final String SCOPE = Scope.AIS.getValue();
    public static final String UNSUPPORTED_SCOPE = "unsupportedScope";

    private SpardaOauth2Service oauth2Service;
    private HttpClient httpClient;
    private Aspsp aspsp;

    private final HttpClientFactory httpClientFactory = mock(HttpClientFactory.class);
    private final HttpClientConfig httpClientConfig = mock(HttpClientConfig.class);

    @BeforeEach
    void setUp() {
        aspsp = new Aspsp();
        aspsp.setBic(BIC);
        aspsp.setIdpUrl(AUTH_HOST + " " + TOKEN_HOST);
        httpClient = spy(new ApacheHttpClient(null, null));
        Mockito.lenient()
            .doReturn(new Response<>(200, null, ResponseHeaders.emptyResponseHeaders()))
            .when(httpClient).send(any(), any());

        when(httpClientFactory.getHttpClient(any())).thenReturn(httpClient);
        when(httpClientFactory.getHttpClientConfig()).thenReturn(httpClientConfig);

        oauth2Service = SpardaOauth2Service.create(aspsp, httpClientFactory, CLIENT_ID);
    }

    @Test
    void getAuthorizationRequestUri() throws IOException {
        Parameters parameters = new Parameters();
        parameters.setRedirectUri(REDIRECT_URI);
        parameters.setScope(SCOPE);

        URI uri = oauth2Service.getAuthorizationRequestUri(Collections.emptyMap(), parameters);

        assertEquals(AUTH_HOST + "/oauth2/authorize"
            + "?response_type=code"
            + "&redirect_uri=" + URLEncoder.encode(REDIRECT_URI, StandardCharsets.UTF_8.name())
            + "&scope=" + SCOPE
            + "&client_id=" + CLIENT_ID
            + "&code_challenge_method=S256"
            + "&code_challenge=" + oauth2Service.codeChallenge()
            + "&bic=" + BIC, uri.toString());
        Mockito.verifyNoInteractions(httpClient);
    }

    @Test
    void getAuthorizationRequestUriWithAisScopeMapping() throws IOException {
        Parameters parameters = new Parameters();
        parameters.setRedirectUri(REDIRECT_URI);
        parameters.setScope(Scope.AIS_TRANSACTIONS.getValue());

        URI uri = oauth2Service.getAuthorizationRequestUri(Collections.emptyMap(), parameters);

        assertEquals(AUTH_HOST + "/oauth2/authorize"
            + "?response_type=code"
            + "&redirect_uri=" + URLEncoder.encode(REDIRECT_URI, StandardCharsets.UTF_8.name())
            + "&scope=" + SCOPE
            + "&client_id=" + CLIENT_ID
            + "&code_challenge_method=S256"
            + "&code_challenge=" + oauth2Service.codeChallenge()
            + "&bic=" + BIC, uri.toString());
        Mockito.verifyNoInteractions(httpClient);
    }

    @Test
    void validateGetAuthorizationRequestUri() {
        List<ValidationError> validationErrors =
            oauth2Service.validateGetAuthorizationRequestUri(null, new Parameters());

        assertThat(validationErrors)
            .extracting(ValidationError::getPath)
            .contains(Parameters.REDIRECT_URI, Parameters.SCOPE);
    }

    @Test
    void validateGetAuthorizationRequestUriWithUnsupportedScope() {
        Parameters parameters = new Parameters();
        parameters.setRedirectUri(REDIRECT_URI);
        parameters.setScope(UNSUPPORTED_SCOPE);

        List<ValidationError> validationErrors =
            oauth2Service.validateGetAuthorizationRequestUri(null, parameters);

        assertThat(validationErrors).hasSize(1);

        ValidationError validationError = validationErrors.get(0);
        assertThat(validationError).isNotNull();
        assertThat(validationError.getCode()).isEqualTo(ValidationError.Code.NOT_SUPPORTED);
        assertThat(validationError.getPath()).isEqualTo(Parameters.SCOPE);
        assertThat(validationError.getMessage())
            .isEqualTo(String.format(UNSUPPORTED_SCOPE_VALUE_ERROR_MESSAGE, UNSUPPORTED_SCOPE));
    }

    @Test
    void getToken_authorizationCode() throws IOException {
        String code = "test-code";
        Parameters parameters = new Parameters();
        parameters.setRedirectUri(REDIRECT_URI);
        parameters.setGrantType(AUTHORIZATION_CODE.toString());
        parameters.setAuthorizationCode(code);

        oauth2Service.getToken(Collections.emptyMap(), parameters);

        HashMap<String, String> expectedBody = new HashMap<>();
        expectedBody.put(Parameters.GRANT_TYPE, AUTHORIZATION_CODE.toString());
        expectedBody.put(Parameters.CLIENT_ID, CLIENT_ID);
        expectedBody.put(Parameters.REDIRECT_URI, REDIRECT_URI);
        expectedBody.put(Parameters.CODE, code);
        expectedBody.put(Parameters.CODE_VERIFIER, oauth2Service.codeVerifier());
        Mockito.verify(httpClient, Mockito.times(1))
            .send(ArgumentMatchers.argThat(req -> {
                    boolean tokenExchange = req.method().equalsIgnoreCase("POST") && req.uri().equals(TOKEN_ENDPOINT);
                    if (tokenExchange) {
                        assertEquals(expectedBody, req.urlEncodedBody());
                    }
                    return tokenExchange;
                }),
                any());
    }

    @Test
    void getToken_refreshToken() throws IOException {
        String refreshToken = "refresh-token";
        Parameters parameters = new Parameters();
        parameters.setGrantType(REFRESH_TOKEN.toString());
        parameters.setRefreshToken(refreshToken);

        oauth2Service.getToken(Collections.emptyMap(), parameters);

        HashMap<String, String> expectedBody = new HashMap<>();
        expectedBody.put(Parameters.GRANT_TYPE, REFRESH_TOKEN.toString());
        expectedBody.put(Parameters.CLIENT_ID, CLIENT_ID);
        expectedBody.put(Parameters.REFRESH_TOKEN, refreshToken);
        Mockito.verify(httpClient, Mockito.times(1))
            .send(ArgumentMatchers.argThat(req -> {
                    boolean tokenExchange = req.method().equalsIgnoreCase("POST") && req.uri().equals(TOKEN_ENDPOINT);
                    if (tokenExchange) {
                        assertEquals(expectedBody, req.urlEncodedBody());
                    }
                    return tokenExchange;
                }),
                any());
    }

    @Test
    void validateGetToken() {
        List<ValidationError> validationErrors = oauth2Service.validateGetToken(null, new Parameters());
        assertThat(validationErrors).isEmpty();
    }

    @Test
    void redirectUriIsRequiredForAuthorizationCodeExchange() {
        Parameters parameters = new Parameters();
        parameters.setAuthorizationCode("asdf");
        List<ValidationError> validationErrors = oauth2Service.validateGetToken(null, parameters);
        assertThat(validationErrors)
            .extracting(ValidationError::getPath)
            .containsExactly(Parameters.REDIRECT_URI);
    }

    @Test
    void clientIdFromCertificateIsUsedWhenNullIsPassedIn() throws Exception {
        Pkcs12KeyStore keyStore = Mockito.mock(Pkcs12KeyStore.class);
        when(httpClientConfig.getKeyStore()).thenReturn(keyStore);

        oauth2Service = SpardaOauth2Service.create(aspsp, httpClientFactory, null);
        Parameters parameters = new Parameters();
        parameters.setRedirectUri(REDIRECT_URI);
        parameters.setScope(SCOPE);

        oauth2Service.getAuthorizationRequestUri(null, parameters);

        Mockito.verify(keyStore, Mockito.times(1)).getOrganizationIdentifier();
    }
}
