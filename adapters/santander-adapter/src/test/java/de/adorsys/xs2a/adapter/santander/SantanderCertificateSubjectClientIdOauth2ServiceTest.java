package de.adorsys.xs2a.adapter.santander;

import de.adorsys.xs2a.adapter.api.Oauth2Service;
import de.adorsys.xs2a.adapter.api.Pkcs12KeyStore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.net.URI;
import java.security.KeyStoreException;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SantanderCertificateSubjectClientIdOauth2ServiceTest {

    private static final String BOGUS_URI = "http://foo.boo";
    private static final String CLIENT_ID = "PSDDE-BAFIN-123456";
    @InjectMocks
    SantanderCertificateSubjectClientIdOauth2Service clientIdOauth2Service;
    @Mock
    Oauth2Service oauth2Service;
    @Mock
    Pkcs12KeyStore keyStore;

    @BeforeEach
    void setUp() throws KeyStoreException {
        when(keyStore.getOrganizationIdentifier(any())).thenReturn(CLIENT_ID);
    }

    @Test
    void getAuthorizationRequestUri() throws IOException, KeyStoreException {
        when(oauth2Service.getAuthorizationRequestUri(anyMap(), any()))
            .thenReturn(URI.create(BOGUS_URI));

        URI actualUri = clientIdOauth2Service.getAuthorizationRequestUri(Map.of(), new Oauth2Service.Parameters());

        verify(oauth2Service, times(1)).getAuthorizationRequestUri(anyMap(), any());
        verify(keyStore, times(1)).getQsealCertificate(any());

        assertThat(actualUri)
            .isEqualTo(URI.create(BOGUS_URI + "?client_id=" + CLIENT_ID));
    }

    @Test
    void getToken() throws IOException, KeyStoreException {
        ArgumentCaptor<Oauth2Service.Parameters> paramsCaptor = ArgumentCaptor.forClass(Oauth2Service.Parameters.class);

        clientIdOauth2Service.getToken(Map.of(), new Oauth2Service.Parameters());

        verify(oauth2Service, times(1)).getToken(anyMap(), paramsCaptor.capture());
        verify(keyStore, times(1)).getQsealCertificate(any());
        verify(keyStore, times(1)).getOrganizationIdentifier(any());

        Oauth2Service.Parameters actualParams = paramsCaptor.getValue();

        assertThat(actualParams)
            .isNotNull()
            .extracting(Oauth2Service.Parameters::getClientId)
            .isEqualTo(CLIENT_ID);
    }
}
