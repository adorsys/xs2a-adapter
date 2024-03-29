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

package de.adorsys.xs2a.adapter.ing;

import de.adorsys.xs2a.adapter.api.Oauth2Service;
import de.adorsys.xs2a.adapter.api.Oauth2Service.Parameters;
import de.adorsys.xs2a.adapter.api.Response;
import de.adorsys.xs2a.adapter.api.ResponseHeaders;
import de.adorsys.xs2a.adapter.api.model.Scope;
import de.adorsys.xs2a.adapter.api.validation.RequestValidationException;
import de.adorsys.xs2a.adapter.api.validation.ValidationError;
import de.adorsys.xs2a.adapter.ing.model.IngApplicationTokenResponse;
import de.adorsys.xs2a.adapter.ing.model.IngAuthorizationURLResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.URI;
import java.util.Collections;
import java.util.List;

import static de.adorsys.xs2a.adapter.ing.IngOauth2Service.UNSUPPORTED_SCOPE_VALUE_ERROR_MESSAGE;
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
    private static final IngApplicationTokenResponse APPLICATION_TOKEN_RESPONSE = buildApplicationTokenResponse();

    @Mock
    private IngOauth2Api oauth2Api;
    @Mock
    private IngClientAuthenticationFactory clientAuthenticationFactory;

    @Mock
    IngClientAuthentication clientAuthentication;

    @InjectMocks
    private IngOauth2Service oauth2Service;

    @Test
    void getAuthorizationRequestUri_Failure_ScopeIsNotProvided() {
        Parameters parameters = new Parameters();
        try {
            oauth2Service.getAuthorizationRequestUri(parameters);
            fail("Should not be reached as RequestValidationException is expected to be thrown before");
        } catch (RequestValidationException e) {
            List<ValidationError> validationErrors = e.getValidationErrors();
            assertNotNull(validationErrors);
            assertEquals(1, validationErrors.size());

            ValidationError validationError = validationErrors.get(0);
            assertEquals(validationErrors.get(0), ValidationError.required(Oauth2Service.Parameters.SCOPE));
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

        when(oauth2Api.getApplicationToken(Collections.singletonList(clientAuthentication)))
            .thenReturn(new Response<>(200, APPLICATION_TOKEN_RESPONSE, ResponseHeaders.emptyResponseHeaders()));

        when(clientAuthenticationFactory.newClientAuthentication(APPLICATION_TOKEN_RESPONSE))
            .thenReturn(clientAuthentication);

        when(oauth2Api.getAuthorizationUrl(Collections.singletonList(clientAuthentication), MAPPED_SCOPE, REDIRECT_URI))
            .thenReturn(new Response<>(200, buildAuthorizationURLResponse(), ResponseHeaders.emptyResponseHeaders()));

        URI uri = oauth2Service.getAuthorizationRequestUri(parameters);

        assertNotNull(uri);
    }

    private IngAuthorizationURLResponse buildAuthorizationURLResponse() {
        IngAuthorizationURLResponse authorizationURLResponse = new IngAuthorizationURLResponse();
        authorizationURLResponse.setLocation(LOCATION);
        return authorizationURLResponse;
    }

    private static IngApplicationTokenResponse buildApplicationTokenResponse() {
        IngApplicationTokenResponse tokenResponse = new IngApplicationTokenResponse();
        tokenResponse.setClientId(CLIENT_ID);
        return tokenResponse;
    }
}
