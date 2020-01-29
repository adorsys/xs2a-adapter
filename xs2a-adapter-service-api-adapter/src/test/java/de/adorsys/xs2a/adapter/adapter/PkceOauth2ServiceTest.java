package de.adorsys.xs2a.adapter.adapter;

import de.adorsys.xs2a.adapter.service.Oauth2Service;
import de.adorsys.xs2a.adapter.service.Oauth2Service.GrantType;
import de.adorsys.xs2a.adapter.service.Oauth2Service.Parameters;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.net.URI;

class PkceOauth2ServiceTest {

    Oauth2Service oauth2Service = Mockito.mock(Oauth2Service.class);
    PkceOauth2Service pkceOauth2Service = new PkceOauth2Service(oauth2Service);
    Parameters parameters = new Parameters();

    @Test
    void codeVerifierParameterAddedForAuthorizationCodeExchange() throws IOException {
        parameters.setGrantType(GrantType.AUTHORIZATION_CODE.toString());

        pkceOauth2Service.getToken(null, parameters);

        Mockito.verify(oauth2Service, Mockito.times(1))
            .getToken(Mockito.any(), Mockito.argThat(p -> p.getCodeVerifier() != null &&
                p.getCodeVerifier().equals(pkceOauth2Service.codeVerifier())));
    }

    @Test
    void codeVerifierParameterIsNotAddedForTokenRefresh() throws IOException {
        parameters.setGrantType(GrantType.REFRESH_TOKEN.toString());

        pkceOauth2Service.getToken(null, parameters);

        Mockito.verify(oauth2Service, Mockito.times(1))
            .getToken(Mockito.any(), Mockito.argThat(p -> p.getCodeVerifier() == null));
    }

    @Test
    void getAuthorizationRequestUri() throws IOException {
        String authorisationRequestUri = "https://authorisation.endpoint?" +
            "response_type=code&state=state&redirect_uri=https://redirect.uri";
        Mockito.when(oauth2Service.getAuthorizationRequestUri(Mockito.any(), Mockito.any()))
            .thenReturn(URI.create(authorisationRequestUri));

        URI actual = pkceOauth2Service.getAuthorizationRequestUri(null, parameters);

        Mockito.verify(oauth2Service, Mockito.times(1))
            .getAuthorizationRequestUri(Mockito.any(), Mockito.any());

        // no way to match whole URI because codeChallenge() will always produce unique output
        Assertions.assertTrue(actual.toString().contains(authorisationRequestUri));
    }
}
