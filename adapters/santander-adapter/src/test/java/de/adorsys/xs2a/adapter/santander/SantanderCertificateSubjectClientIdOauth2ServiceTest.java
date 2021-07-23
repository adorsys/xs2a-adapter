package de.adorsys.xs2a.adapter.santander;

import de.adorsys.xs2a.adapter.api.Oauth2Service;
import de.adorsys.xs2a.adapter.api.Pkcs12KeyStore;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.net.URI;
import java.security.KeyStoreException;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SantanderCertificateSubjectClientIdOauth2ServiceTest {

    @InjectMocks
    SantanderCertificateSubjectClientIdOauth2Service clientIdOauth2Service;
    @Mock
    Oauth2Service oauth2Service;
    @Mock
    Pkcs12KeyStore keyStore;

    @Test
    void getAuthorizationRequestUri() throws IOException, KeyStoreException {
        when(oauth2Service.getAuthorizationRequestUri(anyMap(), any()))
            .thenReturn(URI.create("http://foo.boo"));

        clientIdOauth2Service.getAuthorizationRequestUri(Map.of(), new Oauth2Service.Parameters());

        verify(oauth2Service, times(1)).getAuthorizationRequestUri(anyMap(), any());
        verify(keyStore, times(1)).getQsealCertificate(any());
    }

    @Test
    void getToken() throws IOException, KeyStoreException {
        clientIdOauth2Service.getToken(Map.of(), new Oauth2Service.Parameters());

        verify(oauth2Service, times(1)).getToken(anyMap(), any());
        verify(keyStore, times(1)).getQsealCertificate(any());
        verify(keyStore, times(1)).getOrganizationIdentifier(any());
    }
}