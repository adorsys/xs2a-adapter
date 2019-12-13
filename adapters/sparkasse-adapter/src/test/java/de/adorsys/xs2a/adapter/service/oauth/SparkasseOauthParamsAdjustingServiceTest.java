package de.adorsys.xs2a.adapter.service.oauth;

import de.adorsys.xs2a.adapter.service.Oauth2Service.Parameters;
import de.adorsys.xs2a.adapter.service.Pkcs12KeyStore;
import de.adorsys.xs2a.adapter.service.exception.BadRequestException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.security.KeyStoreException;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SparkasseOauthParamsAdjustingServiceTest {
    private static final String ORGANIZATION_IDENTIFIER = "Test ID";
    private static final String CLIENT_ID = "TestClientId";
    private static final String SCOPE = "ais";
    private static final String STATE = "TestState";
    private static final String RESPONSE_TYPE = "code";
    private static final String CODE_CHALLENGE = "TestCodeChallenge";
    private static final String CODE_CHALLENGE_METHOD = "S256";
    private static final String GRANT_TYPE_GET_TOKEN = "authorization_code";
    private static final String CODE = "TestCode";
    private static final String CODE_VERIFIER = "wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww";

    private static final String SPARKASSE_RESPONSE_TYPE_PARAM_NAME = "responseType";
    private static final String SPARKASSE_CLIENT_ID_PARAM_NAME = "clientId";

    private static final Parameters PARAMETERS_EMPTY = buildEmptyParameters();
    private static final Parameters PARAMETERS_FOR_GET_AUTHORISATION_REQUEST
        = buildValidParametersForGetAuthorizationRequest();
    private static final Parameters PARAMETERS_FOR_GET_TOKEN_REQUEST
        = buildValidParametersForGetTokenRequest();

    @Mock
    private Pkcs12KeyStore keyStore;

    private SparkasseOauthParamsAdjustingService paramsAdjustingService;

    @Test(expected = BadRequestException.class)
    public void adjustForGetAuthorizationRequest_Failure_emptyParamsFromTpp() throws KeyStoreException {
        when(keyStore.getOrganizationIdentifier()).thenReturn(ORGANIZATION_IDENTIFIER);

        paramsAdjustingService = new SparkasseOauthParamsAdjustingService(keyStore);

        paramsAdjustingService.adjustForGetAuthorizationRequest(PARAMETERS_EMPTY);
    }

    @Test
    public void adjustForGetAuthorizationRequest_Success() {
        paramsAdjustingService = new SparkasseOauthParamsAdjustingService(keyStore);

        Parameters parameters = paramsAdjustingService.adjustForGetAuthorizationRequest(PARAMETERS_FOR_GET_AUTHORISATION_REQUEST);

        assertThat(parameters).isNotNull();
        assertThat(parameters.get(SPARKASSE_CLIENT_ID_PARAM_NAME)).isEqualTo(CLIENT_ID);
        assertThat(parameters.getScope()).isEqualTo(SCOPE);
        assertThat(parameters.getState()).isEqualTo(STATE);
        assertThat(parameters.get(SPARKASSE_RESPONSE_TYPE_PARAM_NAME)).isEqualTo(RESPONSE_TYPE);
        assertThat(parameters.getCodeChallenge()).isEqualTo(CODE_CHALLENGE);
        assertThat(parameters.getCodeChallengeMethod()).isEqualTo(CODE_CHALLENGE_METHOD);
    }

    @Test(expected = BadRequestException.class)
    public void adjustForGetTokenRequest_Failure_emptyParamsFromTpp() throws KeyStoreException {
        when(keyStore.getOrganizationIdentifier()).thenReturn(ORGANIZATION_IDENTIFIER);

        paramsAdjustingService = new SparkasseOauthParamsAdjustingService(keyStore);

        paramsAdjustingService.adjustForGetTokenRequest(PARAMETERS_EMPTY);
    }

    @Test
    public void adjustForGetTokenRequest_Success() {
        paramsAdjustingService = new SparkasseOauthParamsAdjustingService(keyStore);

        Parameters parameters = paramsAdjustingService.adjustForGetTokenRequest(PARAMETERS_FOR_GET_TOKEN_REQUEST);

        assertThat(parameters).isNotNull();
        assertThat(parameters.getGrantType()).isEqualTo(GRANT_TYPE_GET_TOKEN);
        assertThat(parameters.getClientId()).isEqualTo(CLIENT_ID);
        assertThat(parameters.getAuthorizationCode()).isEqualTo(CODE);
        assertThat(parameters.getCodeVerifier()).isEqualTo(CODE_VERIFIER);
    }

    private static Parameters buildEmptyParameters() {
        return new Parameters(new HashMap<>());
    }

    private static Parameters buildValidParametersForGetAuthorizationRequest() {
        Parameters parameters = new Parameters(new HashMap<>());

        parameters.setResponseType(RESPONSE_TYPE);
        parameters.setClientId(CLIENT_ID);
        parameters.setScope(SCOPE);
        parameters.setState(STATE);
        parameters.setCodeChallenge(CODE_CHALLENGE);
        parameters.setCodeChallengeMethod(CODE_CHALLENGE_METHOD);

        return parameters;
    }

    private static Parameters buildValidParametersForGetTokenRequest() {
        Parameters parameters = new Parameters(new HashMap<>());

        parameters.setAuthorizationCode(CODE);
        parameters.setClientId(CLIENT_ID);
        parameters.setCodeVerifier(CODE_VERIFIER);
        parameters.setGrantType(GRANT_TYPE_GET_TOKEN);

        return parameters;
    }
}
