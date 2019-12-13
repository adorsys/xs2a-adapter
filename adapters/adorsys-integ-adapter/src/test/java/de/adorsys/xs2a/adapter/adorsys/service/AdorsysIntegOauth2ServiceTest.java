package de.adorsys.xs2a.adapter.adorsys.service;

import de.adorsys.xs2a.adapter.http.StringUri;
import de.adorsys.xs2a.adapter.service.Oauth2Service.Parameters;
import de.adorsys.xs2a.adapter.service.exception.BadRequestException;
import de.adorsys.xs2a.adapter.service.model.Aspsp;
import de.adorsys.xs2a.adapter.service.oauth.Oauth2Api;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.URI;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AdorsysIntegOauth2ServiceTest {
    private static final String IDP_URL = "https://example.com";
    private static final String AUTH_URL = "https://example.com/auth";
    private static final String REDIRECT_URI = "https://example.com/redirect";
    private static final String AUTH_URL_WITH_REDIRECT_URI_PARAM
        = StringUri.withQuery(AUTH_URL, "redirect_uri", REDIRECT_URI);
    private static final URI AUTH_REQUEST_URI = buildAuthRequestURI();

    @InjectMocks
    private AdorsysIntegOauth2Service oauth2Service;

    @Mock
    private Aspsp aspsp;
    @Mock
    private Oauth2Api oauth2Api;

    @Test
    public void getAuthorizationRequestUri_Failure_ScaOauthUrlIsNotProvided() {
        Parameters parameters = new Parameters(new HashMap<>());

        when(aspsp.getIdpUrl()).thenReturn(null);

        Assertions.assertThrows(
            BadRequestException.class,
            () -> oauth2Service.getAuthorizationRequestUri(new HashMap<>(), parameters)
        );
    }

    @Test
    public void getAuthorizationRequestUri_Failure_ScaOauthUrlIsEmptyParam() {
        Parameters parameters = new Parameters(new HashMap<>());
        parameters.setScaOAuthLink("  ");

        when(aspsp.getIdpUrl()).thenReturn(null);

        Assertions.assertThrows(
            BadRequestException.class,
            () -> oauth2Service.getAuthorizationRequestUri(new HashMap<>(), parameters)
        );
    }

    @Test
    public void getAuthorizationRequestUri_Failure_ScaOauthUrlIsEmpty() {
        Parameters parameters = new Parameters(new HashMap<>());

        when(aspsp.getIdpUrl()).thenReturn("    ");

        Assertions.assertThrows(
            BadRequestException.class,
            () -> oauth2Service.getAuthorizationRequestUri(new HashMap<>(), parameters)
        );
    }

    @Test
    public void getAuthorizationRequestUri_Success() {
        Parameters parameters = new Parameters(new HashMap<>());
        parameters.setRedirectUri(REDIRECT_URI);

        when(aspsp.getIdpUrl()).thenReturn(IDP_URL);
        when(oauth2Api.getAuthorisationUri(IDP_URL)).thenReturn(AUTH_URL);

        URI actual = oauth2Service.getAuthorizationRequestUri(new HashMap<>(), parameters);

        assertThat(actual).isEqualTo(AUTH_REQUEST_URI);
    }

    @Test
    public void getToken_Failure_ScaOauthUrlIsNotProvided() {
        Parameters parameters = new Parameters(new HashMap<>());

        when(aspsp.getIdpUrl()).thenReturn(null);

        Assertions.assertThrows(
            BadRequestException.class,
            () -> oauth2Service.getToken(new HashMap<>(), parameters)
        );
    }

    @Test
    public void getToken_Failure_ScaOauthUrlIsEmptyParam() {
        Parameters parameters = new Parameters(new HashMap<>());
        parameters.setScaOAuthLink("  ");

        when(aspsp.getIdpUrl()).thenReturn(null);

        Assertions.assertThrows(
            BadRequestException.class,
            () -> oauth2Service.getToken(new HashMap<>(), parameters)
        );
    }

    @Test
    public void getToken_Failure_ScaOauthUrlIsEmpty() {
        Parameters parameters = new Parameters(new HashMap<>());

        when(aspsp.getIdpUrl()).thenReturn("    ");

        Assertions.assertThrows(
            BadRequestException.class,
            () -> oauth2Service.getToken(new HashMap<>(), parameters)
        );
    }

    @Test
    public void getToken_Failure_ScaOauthUrlHasWrongFormat() {
        Parameters parameters = new Parameters(new HashMap<>());

        when(aspsp.getIdpUrl()).thenReturn("wrong-idp-url");

        Assertions.assertThrows(
            BadRequestException.class,
            () -> oauth2Service.getToken(new HashMap<>(), parameters)
        );
    }

    private static URI buildAuthRequestURI() {
        return URI.create(AUTH_URL_WITH_REDIRECT_URI_PARAM);
    }
}
