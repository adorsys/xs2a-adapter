package de.adorsys.xs2a.adapter.adapter;

import de.adorsys.xs2a.adapter.service.Oauth2Service.Parameters;
import de.adorsys.xs2a.adapter.service.Oauth2Service;
import de.adorsys.xs2a.adapter.service.Pkcs12KeyStore;
import de.adorsys.xs2a.adapter.service.model.TokenResponse;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.io.IOException;
import java.net.URI;
import java.security.KeyStoreException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CertificateSubjectClientIdOauth2ServiceTest {

    private static final String organisationIdentifier = "organisationIdentifier";

    private Pkcs12KeyStore pkcs12KeyStore = mock(Pkcs12KeyStore.class);
    private Oauth2Service oauth2Service = mock(Oauth2Service.class);
    private CertificateSubjectClientIdOauth2Service service = new CertificateSubjectClientIdOauth2Service(oauth2Service, pkcs12KeyStore);
    private ArgumentCaptor<Parameters> captor = ArgumentCaptor.forClass(Parameters.class);

    private Parameters parameters = new Parameters();

    @Test
    void getAuthorizationRequestUri() throws IOException, KeyStoreException {
        String authorisationRequestUri = "https://authorisation.endpoint?" +
            "response_type=code&state=state&redirect_uri=https://redirect.uri";

        URI expectedOutput = URI.create(authorisationRequestUri + "&" + Parameters.CLIENT_ID + "=" + organisationIdentifier);

        when(oauth2Service.getAuthorizationRequestUri(any(), any())).thenReturn(URI.create(authorisationRequestUri));
        when(pkcs12KeyStore.getOrganizationIdentifier()).thenReturn(organisationIdentifier);

        URI actual = service.getAuthorizationRequestUri(null, parameters);

        verify(oauth2Service, times(1)).getAuthorizationRequestUri(any(), any());
        verify(pkcs12KeyStore, times(1)).getOrganizationIdentifier();

        assertEquals(actual, expectedOutput);
    }

    @Test
    void getAuthorizationRequestUri_throwingException() throws IOException, KeyStoreException {
        when(oauth2Service.getAuthorizationRequestUri(any(), any())).thenReturn(URI.create(""));
        when(pkcs12KeyStore.getOrganizationIdentifier()).thenThrow(KeyStoreException.class);

        assertThrows(RuntimeException.class, () -> service.getAuthorizationRequestUri(null, parameters));
    }

    @Test
    void getToken() throws KeyStoreException, IOException {
        when(pkcs12KeyStore.getOrganizationIdentifier()).thenReturn(organisationIdentifier);
        when(oauth2Service.getToken(any(), eq(parameters))).thenReturn(new TokenResponse());

        service.getToken(null, parameters);

        verify(pkcs12KeyStore, times(1)).getOrganizationIdentifier();
        verify(oauth2Service, times(1)).getToken(any(), captor.capture());

        assertEquals(captor.getValue().getClientId(), organisationIdentifier);
    }
}
