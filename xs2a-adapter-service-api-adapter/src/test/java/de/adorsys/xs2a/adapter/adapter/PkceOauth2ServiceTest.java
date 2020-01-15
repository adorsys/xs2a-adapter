package de.adorsys.xs2a.adapter.adapter;

import de.adorsys.xs2a.adapter.service.Oauth2Service;
import de.adorsys.xs2a.adapter.service.Oauth2Service.GrantType;
import de.adorsys.xs2a.adapter.service.Oauth2Service.Parameters;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

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
}
