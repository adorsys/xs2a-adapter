package de.adorsys.xs2a.adapter.santander;

import de.adorsys.xs2a.adapter.adapter.oauth2.api.model.AuthorisationServerMetaData;
import de.adorsys.xs2a.adapter.http.ApacheHttpClient;
import de.adorsys.xs2a.adapter.http.HttpClient;
import de.adorsys.xs2a.adapter.service.Oauth2Service;
import de.adorsys.xs2a.adapter.service.Oauth2Service.Parameters;
import de.adorsys.xs2a.adapter.service.Pkcs12KeyStore;
import de.adorsys.xs2a.adapter.service.Response;
import de.adorsys.xs2a.adapter.service.ResponseHeaders;
import de.adorsys.xs2a.adapter.service.model.Aspsp;
import de.adorsys.xs2a.adapter.validation.ValidationError;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class SantanderOauth2ServiceTest {

    private static final String SCA_OAUTH_LINK = "https://santander.de/.well-known/oauth-authorization-server";
    private static final String AUTHORIZATION_ENDPOINT = "https://api.santander.de/oauthsos/password/authorize";
    private static final String TOKEN_ENDPOINT = "https://api.santander.de/v1/oauth_matls/token";
    private static final String CLIENT_ID = "PSDDE-BAFIN-123456";
    private static final String REDIRECT_URI = "https://tpp-redirect.com/cb";
    private static final String STATE = "S8NJ7uqk5fY4EjNvP_G_FtyJu6pUsvH9jsYni9dMAJw";
    private static final String PAYMENT_ID = "6ee7548f-3fd7-45c1-a6e8-a378e31d8726";
    private static final String CONSENT_ID = "consent_id";
    private static final String AUTHORIZATION_CODE = "authorization_code";
    private static final String REFRESH_TOKEN = "refresh_token";

    private HttpClient httpClient;
    private SantanderOauth2Service oauth2Service;

    @BeforeEach
    void setUp() throws Exception {
        Aspsp aspsp = new Aspsp();
        httpClient = Mockito.spy(new ApacheHttpClient(null));
        Mockito.doReturn(authorizationServerMetadata())
            .when(httpClient).send(Mockito.argThat(req -> req.uri().equals(SCA_OAUTH_LINK)), Mockito.any());
        Mockito.doReturn(new Response<>(200, null, null))
            .when(httpClient).send(Mockito.argThat(req -> req.uri().equals(TOKEN_ENDPOINT)), Mockito.any());
        Pkcs12KeyStore keyStore = Mockito.mock(Pkcs12KeyStore.class);
        Mockito.when(keyStore.getOrganizationIdentifier())
            .thenReturn(CLIENT_ID);
        oauth2Service = SantanderOauth2Service.create(aspsp, httpClient, keyStore);
    }

    private Response<AuthorisationServerMetaData> authorizationServerMetadata() {
        AuthorisationServerMetaData metadata = new AuthorisationServerMetaData();
        metadata.setAuthorisationEndpoint(AUTHORIZATION_ENDPOINT);
        metadata.setTokenEndpoint(TOKEN_ENDPOINT);
        return new Response<>(200, metadata, ResponseHeaders.emptyResponseHeaders());
    }

    @Test
    void validateGetAuthorizationRequestUri() {
        List<ValidationError> validationErrors =
            oauth2Service.validateGetAuthorizationRequestUri(Collections.emptyMap(), new Parameters());

        assertThat(validationErrors).containsExactly(ValidationError.required(Parameters.SCA_OAUTH_LINK),
            ValidationError.required(Parameters.CONSENT_ID + " or " + Parameters.PAYMENT_ID));
    }

    @Test
    void getAuthorizationRequestUriForPayment() throws Exception {

        Parameters parameters = new Parameters();
        parameters.setScaOAuthLink(SCA_OAUTH_LINK);
        parameters.setRedirectUri(REDIRECT_URI);
        parameters.setState(STATE);
        parameters.setPaymentId(PAYMENT_ID);

        URI authorizationRequestUri = oauth2Service.getAuthorizationRequestUri(null, parameters);

        assertThat(authorizationRequestUri)
            .hasToString("https://api.santander.de/oauthsos/password/authorize?" +
                "response_type=code" +
                "&state=" + STATE +
                "&redirect_uri=" + URLEncoder.encode(REDIRECT_URI, StandardCharsets.UTF_8.name()) +
                "&code_challenge_method=S256" +
                "&code_challenge=" + oauth2Service.codeChallenge() +
                "&client_id=" + CLIENT_ID +
                "&scope=PIS%3A" + PAYMENT_ID);
    }


    @Test
    void getAuthorizationRequestUriForConsent() throws Exception {

        Parameters parameters = new Parameters();
        parameters.setScaOAuthLink(SCA_OAUTH_LINK);
        parameters.setRedirectUri(REDIRECT_URI);
        parameters.setState(STATE);
        parameters.setConsentId(CONSENT_ID);

        URI authorizationRequestUri = oauth2Service.getAuthorizationRequestUri(null, parameters);

        assertThat(authorizationRequestUri)
            .hasToString("https://api.santander.de/oauthsos/password/authorize?" +
                "response_type=code" +
                "&state=" + STATE +
                "&redirect_uri=" + URLEncoder.encode(REDIRECT_URI, StandardCharsets.UTF_8.name()) +
                "&code_challenge_method=S256" +
                "&code_challenge=" + oauth2Service.codeChallenge() +
                "&client_id=" + CLIENT_ID +
                "&scope=AIS%3A" + CONSENT_ID);
    }

    @Test
    void getToken() throws Exception {
        Parameters parameters = new Parameters();
        parameters.setScaOAuthLink(SCA_OAUTH_LINK);
        parameters.setGrantType(Oauth2Service.GrantType.AUTHORIZATION_CODE.toString());
        parameters.setAuthorizationCode(AUTHORIZATION_CODE);
        parameters.setRedirectUri(REDIRECT_URI);

        oauth2Service.getToken(null, parameters);

        Map<String, String> expectedBody = new HashMap<>();
        expectedBody.put(Parameters.GRANT_TYPE, Oauth2Service.GrantType.AUTHORIZATION_CODE.toString());
        expectedBody.put(Parameters.CODE, AUTHORIZATION_CODE);
        expectedBody.put(Parameters.CLIENT_ID, CLIENT_ID);
        expectedBody.put(Parameters.CODE_VERIFIER, oauth2Service.codeVerifier());
        expectedBody.put(Parameters.REDIRECT_URI, REDIRECT_URI);
        Mockito.verify(httpClient, Mockito.times(1)).send(ArgumentMatchers.argThat(req -> {
            boolean tokenExchange = req.uri().equals(TOKEN_ENDPOINT);
            if (tokenExchange) {
                assertEquals(expectedBody, req.urlEncodedBody());
            }
            return tokenExchange;
        }), Mockito.any());
    }

    @Test
    void getTokenRefresh() throws Exception {
        Parameters parameters = new Parameters();
        parameters.setScaOAuthLink(SCA_OAUTH_LINK);
        parameters.setGrantType(Oauth2Service.GrantType.REFRESH_TOKEN.toString());
        parameters.setRefreshToken(REFRESH_TOKEN);

        oauth2Service.getToken(null, parameters);

        Map<String, String> expectedBody = new HashMap<>();
        expectedBody.put(Parameters.GRANT_TYPE, Oauth2Service.GrantType.REFRESH_TOKEN.toString());
        expectedBody.put(Parameters.REFRESH_TOKEN, REFRESH_TOKEN);
        expectedBody.put(Parameters.CLIENT_ID, CLIENT_ID);
        Mockito.verify(httpClient, Mockito.times(1)).send(ArgumentMatchers.argThat(req -> {
            boolean tokenExchange = req.uri().equals(TOKEN_ENDPOINT);
            if (tokenExchange) {
                assertEquals(expectedBody, req.urlEncodedBody());
            }
            return tokenExchange;
        }), Mockito.any());
    }
}
