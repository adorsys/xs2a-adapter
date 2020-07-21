package de.adorsys.xs2a.adapter.adorsys.service;

import de.adorsys.xs2a.adapter.adapter.model.OauthToken;
import de.adorsys.xs2a.adapter.http.HttpClient;
import de.adorsys.xs2a.adapter.http.Request;
import de.adorsys.xs2a.adapter.http.StringUri;
import de.adorsys.xs2a.adapter.service.Oauth2Service.Parameters;
import de.adorsys.xs2a.adapter.service.Response;
import de.adorsys.xs2a.adapter.service.ResponseHeaders;
import de.adorsys.xs2a.adapter.service.model.Aspsp;
import de.adorsys.xs2a.adapter.service.model.TokenResponse;
import de.adorsys.xs2a.adapter.service.oauth.Oauth2Api;
import de.adorsys.xs2a.adapter.validation.RequestValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
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
    private static final OauthToken OAUTH_TOKEN = buildOauthToken();
    private static final int HTTP_CODE_OK = 200;
    private static final Response<OauthToken> OAUTH_TOKEN_RESPONSE
        = new Response<>(HTTP_CODE_OK, OAUTH_TOKEN, ResponseHeaders.emptyResponseHeaders());

    @InjectMocks
    private AdorsysIntegOauth2Service oauth2Service;

    @Mock
    private Aspsp aspsp;
    @Mock
    private Oauth2Api oauth2Api;
    @Mock
    private HttpClient httpClient;

    @Mock
    private Request.Builder requestBuilder;

    @Test
    void getAuthorizationRequestUri_Failure_ScaOauthUrlIsNotProvided() {
        Parameters parameters = new Parameters(new HashMap<>());

        when(aspsp.getIdpUrl()).thenReturn(null);

        Assertions.assertThrows(
            RequestValidationException.class,
            () -> oauth2Service.getAuthorizationRequestUri(new HashMap<>(), parameters)
        );
    }

    @Test
    void getAuthorizationRequestUri_Failure_ScaOauthUrlIsEmptyParam() {
        Parameters parameters = new Parameters(new HashMap<>());
        parameters.setScaOAuthLink("  ");

        when(aspsp.getIdpUrl()).thenReturn(null);

        Assertions.assertThrows(
            RequestValidationException.class,
            () -> oauth2Service.getAuthorizationRequestUri(new HashMap<>(), parameters)
        );
    }

    @Test
    void getAuthorizationRequestUri_Failure_ScaOauthUrlIsEmpty() {
        Parameters parameters = new Parameters(new HashMap<>());

        when(aspsp.getIdpUrl()).thenReturn("    ");

        Assertions.assertThrows(
            RequestValidationException.class,
            () -> oauth2Service.getAuthorizationRequestUri(new HashMap<>(), parameters)
        );
    }

    @Test
    void getAuthorizationRequestUri_Success() {
        Parameters parameters = new Parameters(new HashMap<>());
        parameters.setRedirectUri(REDIRECT_URI);

        when(aspsp.getIdpUrl()).thenReturn(IDP_URL);
        when(oauth2Api.getAuthorisationUri(IDP_URL)).thenReturn(AUTH_URL);

        URI actual = oauth2Service.getAuthorizationRequestUri(new HashMap<>(), parameters);

        assertThat(actual).isEqualTo(AUTH_REQUEST_URI);
    }

    @Test
    void getToken_Failure_ScaOauthUrlIsNotProvided() {
        Parameters parameters = new Parameters(new HashMap<>());

        when(aspsp.getIdpUrl()).thenReturn(null);

        Assertions.assertThrows(
            RequestValidationException.class,
            () -> oauth2Service.getToken(new HashMap<>(), parameters)
        );
    }

    @Test
    void getToken_Failure_ScaOauthUrlIsEmptyParam() {
        Parameters parameters = new Parameters(new HashMap<>());
        parameters.setScaOAuthLink("  ");

        when(aspsp.getIdpUrl()).thenReturn(null);

        Assertions.assertThrows(
            RequestValidationException.class,
            () -> oauth2Service.getToken(new HashMap<>(), parameters)
        );
    }

    @Test
    void getToken_Failure_ScaOauthUrlIsEmpty() {
        Parameters parameters = new Parameters(new HashMap<>());

        when(aspsp.getIdpUrl()).thenReturn("    ");

        Assertions.assertThrows(
            RequestValidationException.class,
            () -> oauth2Service.getToken(new HashMap<>(), parameters)
        );
    }

    @Test
    void getToken_Failure_ScaOauthUrlHasWrongFormat() {
        Parameters parameters = new Parameters(new HashMap<>());

        when(aspsp.getIdpUrl()).thenReturn("wrong-idp-url");

        Assertions.assertThrows(
            RequestValidationException.class,
            () -> oauth2Service.getToken(new HashMap<>(), parameters)
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

        TokenResponse tokenResponse = oauth2Service.getToken(new HashMap<>(), parameters);

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

    private static OauthToken buildOauthToken() {
        OauthToken oauthToken = new OauthToken();

        oauthToken.setAccessToken(ACCESS_TOKEN);
        oauthToken.setTokenType(TOKEN_TYPE);
        oauthToken.setExpiresInSeconds(EXPIRES_IN_SECONDS);
        oauthToken.setRefreshToken(REFRESH_TOKEN);
        oauthToken.setScope(SCOPE);

        return oauthToken;
    }
}
