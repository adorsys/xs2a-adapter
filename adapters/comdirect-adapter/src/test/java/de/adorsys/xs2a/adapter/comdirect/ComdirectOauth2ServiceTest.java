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
import de.adorsys.xs2a.adapter.api.Pkcs12KeyStore;
import de.adorsys.xs2a.adapter.api.Response;
import de.adorsys.xs2a.adapter.api.ResponseHeaders;
import de.adorsys.xs2a.adapter.api.http.HttpClient;
import de.adorsys.xs2a.adapter.api.http.HttpClientConfig;
import de.adorsys.xs2a.adapter.api.http.HttpClientFactory;
import de.adorsys.xs2a.adapter.api.http.Request;
import de.adorsys.xs2a.adapter.api.model.Aspsp;
import de.adorsys.xs2a.adapter.api.model.TokenResponse;
import de.adorsys.xs2a.adapter.api.validation.RequestValidationException;
import de.adorsys.xs2a.adapter.impl.oauth2.api.model.AuthorisationServerMetaData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static de.adorsys.xs2a.adapter.api.Oauth2Service.Parameters;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class ComdirectOauth2ServiceTest {

    private static final String SCA_OAUTH_LINK =
        "https://xs2a-api.comdirect.de/berlingroup/.well-known/openid-configuration?authorizationId=31f68ab6-1ce6-4131-a324-3f37d2ca4b02";
    private static final String IDP_URL =
        "https://psd.comdirect.de/public/berlingroup/idp";
    private static final String AUTHORIZATION_ENDPOINT_LINK
        = "https://psd.comdirect.de/berlingroup/authorize/31f68ab6-1ce6-4131-a324-3f37d2ca4b02";
    private static final String ORG_ID = "PSDDE-BAFIN-999999";
    private static final String STATE = "test";
    private static final String REDIRECT_URI = "https://example.com/cb";
    private static final String CONSENT_ID = "consent-id";
    private static final String AUTHORIZATION_CODE = "authorization-code";
    private static final String BASE_URI = "https://psd.comdirect.de/berlingroup";
    private static final String TOKEN_ENDPOINT = BASE_URI + "/v1/token";

    private ComdirectOauth2Service oauth2Service;
    private final HttpClientFactory httpClientFactory = mock(HttpClientFactory.class);
    private final HttpClient httpClient = mock(HttpClient.class);
    private final Request.Builder builder = mock(Request.Builder.class);

    @BeforeEach
    void setUp() throws Exception {
        Pkcs12KeyStore keyStore = mock(Pkcs12KeyStore.class);
        when(keyStore.getOrganizationIdentifier())
            .thenReturn(ORG_ID);

        HttpClientConfig httpClientConfig = mock(HttpClientConfig.class);
        when(httpClientFactory.getHttpClient(any())).thenReturn(httpClient);
        when(httpClientFactory.getHttpClientConfig()).thenReturn(httpClientConfig);
        when(httpClientConfig.getKeyStore()).thenReturn(keyStore);

        when(httpClient.get(anyString())).thenReturn(builder);

        doReturn(authorizationServerMetadata()).when(builder).send(any());
    }

    private Response<AuthorisationServerMetaData> authorizationServerMetadata() {
        AuthorisationServerMetaData metadata = new AuthorisationServerMetaData();
        metadata.setAuthorisationEndpoint(AUTHORIZATION_ENDPOINT_LINK);
        return new Response<>(200, metadata, ResponseHeaders.emptyResponseHeaders());
    }

    @Test
    void getAuthorizationRequestUri() throws IOException {
        oauth2Service = ComdirectOauth2Service.create(new Aspsp(), httpClientFactory);
        Parameters parameters = new Parameters();
        parameters.setScaOAuthLink(SCA_OAUTH_LINK);
        parameters.setState(STATE);
        parameters.setConsentId(CONSENT_ID);
        parameters.setRedirectUri(REDIRECT_URI);

        URI uri = oauth2Service.getAuthorizationRequestUri(null, parameters);

        assertEquals(AUTHORIZATION_ENDPOINT_LINK + "?" +
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
        oauth2Service = ComdirectOauth2Service.create(new Aspsp(), httpClientFactory);
        Parameters parameters = new Parameters();
        parameters.setScaOAuthLink(SCA_OAUTH_LINK);
        parameters.setState(STATE);
        parameters.setPaymentId("payment-id");
        parameters.setRedirectUri(REDIRECT_URI);

        URI uri = oauth2Service.getAuthorizationRequestUri(null, parameters);

        assertEquals(AUTHORIZATION_ENDPOINT_LINK + "?" +
            "response_type=code&" +
            "state=" + STATE + "&" +
            "redirect_uri=" + URLEncoder.encode(REDIRECT_URI, StandardCharsets.UTF_8.name()) + "&" +
            "client_id=" + ORG_ID + "&" +
            "code_challenge_method=S256&" +
            "code_challenge=" + oauth2Service.codeChallenge() + "&" +
            "scope=PIS%3Apayment-id", uri.toString());
    }

    @Test
    void getAuthorizationRequestUri_noScaOAuthLink() throws IOException {
        Aspsp aspsp = new Aspsp();
        aspsp.setIdpUrl(IDP_URL);
        AuthorisationServerMetaData metaData = new AuthorisationServerMetaData();
        metaData.setAuthorisationEndpoint(AUTHORIZATION_ENDPOINT_LINK);

        oauth2Service = ComdirectOauth2Service.create(aspsp, httpClientFactory);

        Parameters parameters = new Parameters();
        parameters.setState(STATE);
        parameters.setConsentId(CONSENT_ID);
        parameters.setRedirectUri(REDIRECT_URI);

        doReturn(new Response<>(200, metaData, null)).when(builder).send(any());

        URI uri = oauth2Service.getAuthorizationRequestUri(null, parameters);

        verify(httpClient, times(1)).get(anyString());
        verify(builder, times(1)).send(any());

        assertEquals(AUTHORIZATION_ENDPOINT_LINK + "?" +
            "response_type=code&" +
            "state=" + STATE + "&" +
            "redirect_uri=" + URLEncoder.encode(REDIRECT_URI, StandardCharsets.UTF_8.name()) + "&" +
            "client_id=" + ORG_ID + "&" +
            "code_challenge_method=S256&" +
            "code_challenge=" + oauth2Service.codeChallenge() + "&" +
            "scope=AIS%3A" + CONSENT_ID, uri.toString());
    }

    @Test
    void getAuthorizationRequestUri_noScaOAuthLinkNoIdpUrl() {
        oauth2Service = ComdirectOauth2Service.create(new Aspsp(), httpClientFactory);
        Parameters parameters = new Parameters();

        assertThrows(RequestValidationException.class,
            () -> oauth2Service.getAuthorizationRequestUri(null, parameters));
    }

    @Test
    @SuppressWarnings("unchecked")
    void getToken() throws IOException {
        Aspsp aspsp = new Aspsp();
        aspsp.setUrl(BASE_URI);

        ArgumentCaptor<Map<String, String>> mapCaptor = ArgumentCaptor.forClass(Map.class);

        when(httpClient.post(anyString())).thenReturn(builder);
        when(builder.urlEncodedBody(any())).thenReturn(builder);
        doReturn(new Response<>(200, new TokenResponse(), ResponseHeaders.emptyResponseHeaders()))
            .when(builder).send(any());

        oauth2Service = ComdirectOauth2Service.create(aspsp, httpClientFactory);
        Parameters parameters = new Parameters();
        parameters.setGrantType(Oauth2Service.GrantType.AUTHORIZATION_CODE.toString());
        parameters.setAuthorizationCode(AUTHORIZATION_CODE);
        parameters.setRedirectUri(REDIRECT_URI);

        oauth2Service.getToken(null, parameters);

        Map<String, String> expectedBody = new HashMap<>();
        expectedBody.put(Parameters.GRANT_TYPE, Oauth2Service.GrantType.AUTHORIZATION_CODE.toString());
        expectedBody.put(Parameters.CLIENT_ID, ORG_ID);
        expectedBody.put(Parameters.CODE, AUTHORIZATION_CODE);
        expectedBody.put(Parameters.REDIRECT_URI, REDIRECT_URI);
        expectedBody.put(Parameters.CODE_VERIFIER, oauth2Service.codeVerifier());
        verify(httpClient, times(1))
            .post(argThat(argument -> argument.equals(TOKEN_ENDPOINT)));
        verify(builder, times(1)).urlEncodedBody(mapCaptor.capture());

        Map<String, String> actualBody = mapCaptor.getValue();
        assertNotNull(actualBody);
        assertEquals(expectedBody, actualBody);
    }
}
