package de.adorsys.xs2a.adapter.service;

import de.adorsys.xs2a.adapter.http.StringUri;
import de.adorsys.xs2a.adapter.service.Oauth2Service.Parameters;
import de.adorsys.xs2a.adapter.service.exception.BadRequestException;
import de.adorsys.xs2a.adapter.service.model.Aspsp;
import de.adorsys.xs2a.adapter.service.oauth.SpardaOauthParamsAdjustingService;
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
public class SpardaOauth2ServiceTest {
    private static final String IDP_URL = "https://example.com";
    private static final String AUTH_URL = "https://example.com/authorize";
    private static final String BIC = "TESTBIC1XXX";
    private static final String CLIENT_ID = "testClientId";
    private static final String REDIRECT_URI = "https://example.com/redirect";
    private static final String SCOPE = "ais";
    private static final String STATE = "testState";
    private static final String RESPONSE_TYPE = "code";
    private static final String CODE_CHALLENGE = "testCodeChallenge";
    private static final String CODE_CHALLENGE_METHOD = "testCodeChallenge";

    private static final Parameters PARAMETERS_FOR_GET_AUTHORISATION_REQUEST = buildParametersForGetAuthorizationRequest();
    private static final URI GET_AUTHORISATION_REQUEST_URI = buildGetAuthorizationRequestURI();

    @InjectMocks
    private SpardaOauth2Service oauth2Service;

    @Mock
    private Aspsp aspsp;
    @Mock
    private SpardaOauthParamsAdjustingService paramsAdjustingService;

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

        when(aspsp.getIdpUrl())
            .thenReturn(IDP_URL);
        when(paramsAdjustingService.adjustForGetAuthorizationRequest(parameters))
            .thenReturn(PARAMETERS_FOR_GET_AUTHORISATION_REQUEST);

        URI actual = oauth2Service.getAuthorizationRequestUri(new HashMap<>(), parameters);

        assertThat(actual).isEqualTo(GET_AUTHORISATION_REQUEST_URI);
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

    private static Parameters buildParametersForGetAuthorizationRequest() {
        Parameters parameters = new Parameters(new HashMap<>());

        parameters.setBic(BIC);
        parameters.setClientId(CLIENT_ID);
        parameters.setRedirectUri(REDIRECT_URI);
        parameters.setScope(SCOPE);
        parameters.setState(STATE);
        parameters.setResponseType(RESPONSE_TYPE);
        parameters.setCodeChallenge(CODE_CHALLENGE);
        parameters.setCodeChallengeMethod(CODE_CHALLENGE_METHOD);

        return parameters;
    }

    private static URI buildGetAuthorizationRequestURI() {
        String url = StringUri.withQuery(AUTH_URL, PARAMETERS_FOR_GET_AUTHORISATION_REQUEST.asMap());
        return URI.create(url);
    }
}
