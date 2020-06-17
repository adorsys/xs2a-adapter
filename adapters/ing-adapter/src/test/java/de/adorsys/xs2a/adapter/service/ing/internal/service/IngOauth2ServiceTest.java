package de.adorsys.xs2a.adapter.service.ing.internal.service;

import de.adorsys.xs2a.adapter.service.Oauth2Service;
import de.adorsys.xs2a.adapter.service.Oauth2Service.Parameters;
import de.adorsys.xs2a.adapter.service.Response;
import de.adorsys.xs2a.adapter.service.ResponseHeaders;
import de.adorsys.xs2a.adapter.service.ing.internal.api.ClientAuthentication;
import de.adorsys.xs2a.adapter.service.ing.internal.api.ClientAuthenticationFactory;
import de.adorsys.xs2a.adapter.service.ing.internal.api.Oauth2Api;
import de.adorsys.xs2a.adapter.service.ing.internal.api.model.ApplicationTokenResponse;
import de.adorsys.xs2a.adapter.service.ing.internal.api.model.AuthorizationURLResponse;
import de.adorsys.xs2a.adapter.service.model.Scope;
import de.adorsys.xs2a.adapter.validation.RequestValidationException;
import de.adorsys.xs2a.adapter.validation.ValidationError;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.URI;
import java.util.List;

import static de.adorsys.xs2a.adapter.service.ing.internal.service.IngOauth2Service.MISSING_REQUIRED_PARAMETER_ERROR_MESSAGE;
import static de.adorsys.xs2a.adapter.service.ing.internal.service.IngOauth2Service.UNSUPPORTED_SCOPE_VALUE_ERROR_MESSAGE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IngOauth2ServiceTest {
    private static final String CLIENT_ID = "client-id";
    private static final String LOCATION = "https://example.com/location";
    private static final String REDIRECT_URI = "https://example.com/redirect";
    private static final String SCOPE = Scope.AIS.getValue();
    private static final String UNSUPPORTED_SCOPE = "unsupportedScope";
    private static final String MAPPED_SCOPE = "payment-accounts:transactions:view payment-accounts:balances:view";
    private static final ApplicationTokenResponse APPLICATION_TOKEN_RESPONSE = buildApplicationTokenResponse();

    @Mock
    private Oauth2Api oauth2Api;
    @Mock
    private ClientAuthenticationFactory clientAuthenticationFactory;

    @Mock
    ClientAuthentication clientAuthentication;

    @InjectMocks
    private IngOauth2Service oauth2Service;

    @Test
    void getAuthorizationRequestUri_Failure_ScopeIsNotProvided() {
        try {
            oauth2Service.getAuthorizationRequestUri(new Parameters());
            fail("Should not be reached as RequestValidationException is expected to be thrown before");
        } catch (RequestValidationException e) {
            List<ValidationError> validationErrors = e.getValidationErrors();
            assertNotNull(validationErrors);
            assertEquals(1, validationErrors.size());

            ValidationError validationError = validationErrors.get(0);
            assertEquals(ValidationError.Code.REQUIRED, validationError.getCode());
            assertEquals(Oauth2Service.Parameters.SCOPE, validationError.getPath());
            assertEquals(MISSING_REQUIRED_PARAMETER_ERROR_MESSAGE, validationError.getMessage());
        }
    }

    @Test
    void getAuthorizationRequestUri_Failure_ScopeIsUnsupported() {
        Parameters parameters = new Parameters();
        parameters.setScope(UNSUPPORTED_SCOPE);

        try {
            oauth2Service.getAuthorizationRequestUri(parameters);
            fail("Should not be reached as RequestValidationException is expected to be thrown before");
        } catch (RequestValidationException e) {
            List<ValidationError> validationErrors = e.getValidationErrors();
            assertNotNull(validationErrors);
            assertEquals(1, validationErrors.size());

            ValidationError validationError = validationErrors.get(0);
            assertEquals(ValidationError.Code.NOT_SUPPORTED, validationError.getCode());
            assertEquals(Oauth2Service.Parameters.SCOPE, validationError.getPath());
            assertEquals(String.format(UNSUPPORTED_SCOPE_VALUE_ERROR_MESSAGE, UNSUPPORTED_SCOPE),
                validationError.getMessage());
        }
    }

    @Test
    void getAuthorizationRequestUri_Success() {
        Parameters parameters = new Parameters();
        parameters.setScope(SCOPE);
        parameters.setRedirectUri(REDIRECT_URI);

        when(clientAuthenticationFactory.newClientAuthenticationForApplicationToken())
            .thenReturn(clientAuthentication);

        when(oauth2Api.getApplicationToken(clientAuthentication))
            .thenReturn(new Response<>(200, APPLICATION_TOKEN_RESPONSE, ResponseHeaders.emptyResponseHeaders()));

        when(clientAuthenticationFactory.newClientAuthentication(APPLICATION_TOKEN_RESPONSE))
            .thenReturn(clientAuthentication);

        when(oauth2Api.getAuthorizationUrl(clientAuthentication, MAPPED_SCOPE, REDIRECT_URI))
            .thenReturn(new Response<>(200, buildAuthorizationURLResponse(), ResponseHeaders.emptyResponseHeaders()));

        URI uri = oauth2Service.getAuthorizationRequestUri(parameters);

        assertNotNull(uri);
    }

    private AuthorizationURLResponse buildAuthorizationURLResponse() {
        AuthorizationURLResponse authorizationURLResponse = new AuthorizationURLResponse();
        authorizationURLResponse.setLocation(LOCATION);
        return authorizationURLResponse;
    }

    private static ApplicationTokenResponse buildApplicationTokenResponse() {
        ApplicationTokenResponse tokenResponse = new ApplicationTokenResponse();
        tokenResponse.setClientId(CLIENT_ID);
        return tokenResponse;
    }
}
