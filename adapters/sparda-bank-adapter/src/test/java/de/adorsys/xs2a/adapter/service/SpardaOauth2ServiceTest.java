package de.adorsys.xs2a.adapter.service;

import de.adorsys.xs2a.adapter.http.ApacheHttpClient;
import de.adorsys.xs2a.adapter.http.HttpClient;
import de.adorsys.xs2a.adapter.service.Oauth2Service.Parameters;
import de.adorsys.xs2a.adapter.service.model.Aspsp;
import de.adorsys.xs2a.adapter.service.model.Scope;
import de.adorsys.xs2a.adapter.validation.ValidationError;
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

import static de.adorsys.xs2a.adapter.service.Oauth2Service.GrantType.AUTHORIZATION_CODE;
import static de.adorsys.xs2a.adapter.service.Oauth2Service.GrantType.REFRESH_TOKEN;
import static de.adorsys.xs2a.adapter.service.SpardaOauth2Service.UNSUPPORTED_SCOPE_VALUE_ERROR_MESSAGE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SpardaOauth2ServiceTest {
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

    @BeforeEach
    void setUp() {
        aspsp = new Aspsp();
        aspsp.setBic(BIC);
        aspsp.setIdpUrl(AUTH_HOST + " " + TOKEN_HOST);
        httpClient = Mockito.spy(new ApacheHttpClient(null));
        Mockito.lenient()
            .doReturn(new Response<>(200, null, ResponseHeaders.emptyResponseHeaders()))
            .when(httpClient).send(Mockito.any(), Mockito.any());
        oauth2Service = SpardaOauth2Service.create(aspsp, httpClient, null, CLIENT_ID);
    }

    @Test
    public void getAuthorizationRequestUri() throws IOException {
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
    public void getAuthorizationRequestUriWithAisScopeMapping() throws IOException {
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

        assertThat(validationErrors).isNotNull();
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
                Mockito.any());
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
                Mockito.any());
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
        oauth2Service = SpardaOauth2Service.create(aspsp, null, keyStore, null);
        Parameters parameters = new Parameters();
        parameters.setRedirectUri(REDIRECT_URI);
        parameters.setScope(SCOPE);

        oauth2Service.getAuthorizationRequestUri(null, parameters);

        Mockito.verify(keyStore, Mockito.times(1)).getOrganizationIdentifier();
    }
}
