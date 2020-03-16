package de.adorsys.xs2a.adapter.service;

import de.adorsys.xs2a.adapter.adapter.model.OauthToken;
import de.adorsys.xs2a.adapter.http.ApacheHttpClient;
import de.adorsys.xs2a.adapter.http.HttpClient;
import de.adorsys.xs2a.adapter.service.Oauth2Service.Parameters;
import de.adorsys.xs2a.adapter.service.model.Aspsp;
import de.adorsys.xs2a.adapter.validation.ValidationError;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import java.io.IOException;
import java.net.URI;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static de.adorsys.xs2a.adapter.service.Oauth2Service.GrantType.AUTHORIZATION_CODE;
import static de.adorsys.xs2a.adapter.service.Oauth2Service.GrantType.REFRESH_TOKEN;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SpardaOauth2ServiceTest {
    public static final String BIC = "GENODEF1S08";
    public static final String IDP_URI = "https://idp.sparda-n.de/oauth2";
    public static final String TOKEN_ENDPOINT = IDP_URI + "/token";
    public static final String CLIENT_ID = "client-id";
    public static final String REDIRECT_URI = "https://tpp.com/cb";

    private SpardaOauth2Service oauth2Service;
    private HttpClient httpClient;

    @BeforeEach
    void setUp() {
        Aspsp aspsp = new Aspsp();
        aspsp.setBic(BIC);
        httpClient = Mockito.spy(new ApacheHttpClient(null));
        Mockito.lenient()
            .doReturn(new Response<>(200, null, ResponseHeaders.emptyResponseHeaders()))
            .when(httpClient).send(Mockito.any(), Mockito.any());
        oauth2Service = SpardaOauth2Service.create(aspsp, httpClient, null, CLIENT_ID);
    }

    private Response<OauthToken> tokenResponse() {
        return new Response<>(200, new OauthToken(), ResponseHeaders.emptyResponseHeaders());
    }

    @Test
    public void getAuthorizationRequestUri() throws IOException {
        Parameters parameters = new Parameters();
        parameters.setScaOAuthLink(IDP_URI);
        parameters.setRedirectUri(REDIRECT_URI);
        parameters.setBic(BIC);

        URI uri = oauth2Service.getAuthorizationRequestUri(Collections.emptyMap(), parameters);

        assertEquals(IDP_URI + "/authorize"
            + "?response_type=code"
            + "&redirect_uri=" + REDIRECT_URI
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
            .containsExactly(Parameters.SCA_OAUTH_LINK, Parameters.REDIRECT_URI);
    }

    @Test
    void getToken_authorizationCode() throws IOException {
        String code = "test-code";
        Parameters parameters = new Parameters();
        parameters.setScaOAuthLink(IDP_URI);
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
                Mockito.any());
    }

    @Test
    void getToken_refreshToken() throws IOException {
        String refreshToken = "refresh-token";
        Parameters parameters = new Parameters();
        parameters.setScaOAuthLink(IDP_URI);
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
                Mockito.any());
    }

    @Test
    void validateGetToken() {
        List<ValidationError> validationErrors = oauth2Service.validateGetToken(null, new Parameters());
        assertThat(validationErrors)
            .extracting(ValidationError::getPath)
            .containsExactly(Parameters.SCA_OAUTH_LINK);
    }

    @Test
    void redirectUriIsRequiredForAuthorizationCodeExchange() {
        Parameters parameters = new Parameters();
        parameters.setAuthorizationCode("asdf");
        List<ValidationError> validationErrors = oauth2Service.validateGetToken(null, parameters);
        assertThat(validationErrors)
            .extracting(ValidationError::getPath)
            .containsExactly(Parameters.SCA_OAUTH_LINK, Parameters.REDIRECT_URI);
    }

    @Test
    void clientIdFromCertificateIsUsedWhenNullIsPassedIn() throws Exception {
        Pkcs12KeyStore keyStore = Mockito.mock(Pkcs12KeyStore.class);
        oauth2Service = SpardaOauth2Service.create(null, null, keyStore, null);
        Parameters parameters = new Parameters();
        parameters.setScaOAuthLink(IDP_URI);
        parameters.setRedirectUri(REDIRECT_URI);

        oauth2Service.getAuthorizationRequestUri(null, parameters);

        Mockito.verify(keyStore, Mockito.times(1)).getOrganizationIdentifier();
    }
}
