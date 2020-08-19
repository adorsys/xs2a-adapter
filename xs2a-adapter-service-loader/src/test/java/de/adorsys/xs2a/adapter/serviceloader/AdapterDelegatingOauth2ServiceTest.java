package de.adorsys.xs2a.adapter.serviceloader;

import de.adorsys.xs2a.adapter.api.Oauth2Service;
import de.adorsys.xs2a.adapter.api.RequestHeaders;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.HashMap;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdapterDelegatingOauth2ServiceTest {

    @Spy
    private final Oauth2Service testService = new TestOauth2Service();

    @Mock
    private AdapterServiceLoader serviceLoader;

    @InjectMocks
    private AdapterDelegatingOauth2Service oauth2Service;

    @Test
    void getAuthorizationRequestUri() throws IOException {
        when(serviceLoader.getOauth2Service(any(RequestHeaders.class))).thenReturn(testService);

        oauth2Service.getAuthorizationRequestUri(new HashMap<>(), new Oauth2Service.Parameters());

        verify(serviceLoader, times(1)).getOauth2Service(any(RequestHeaders.class));
        verify(testService, times(1)).getAuthorizationRequestUri(anyMap(), any(Oauth2Service.Parameters.class));
    }

    @Test
    void getToken() throws IOException {
        when(serviceLoader.getOauth2Service(any(RequestHeaders.class))).thenReturn(testService);

        oauth2Service.getToken(new HashMap<>(), new Oauth2Service.Parameters());

        verify(serviceLoader, times(1)).getOauth2Service(any(RequestHeaders.class));
        verify(testService, times(1)).getToken(anyMap(), any(Oauth2Service.Parameters.class));
    }
}
