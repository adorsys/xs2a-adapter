package de.adorsys.xs2a.adapter.service.impl;

import de.adorsys.xs2a.adapter.http.HttpClient;
import de.adorsys.xs2a.adapter.security.AccessTokenService;
import de.adorsys.xs2a.adapter.service.Response;
import de.adorsys.xs2a.adapter.service.ResponseHeaders;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class DkbAccessTokenServiceTest {

    private AccessTokenService tokenService;
    private HttpClient httpClient;

    @Before
    public void setUp() {
        String file = getClass().getResource("/external.adapter.config.properties").getFile();
        System.setProperty("adapter.config.file.path", file);

        tokenService = DkbAccessTokenService.getInstance();
        httpClient = Mockito.mock(HttpClient.class);
        ((DkbAccessTokenService) tokenService).setHttpClient(httpClient);
    }

    @Test
    public void retrieveToken() {
        String accessToken = "accessToken";
        DkbAccessTokenService.TokenResponse tokenResponse = new DkbAccessTokenService.TokenResponse(accessToken, 3600);
        when(httpClient.post(anyString(), anyString(), anyMap(), any())).thenReturn(new Response<>(200, tokenResponse, ResponseHeaders.fromMap(Collections.emptyMap())));

        String token = tokenService.retrieveToken();

        verify(httpClient, times(1)).post(anyString(), anyString(), anyMap(), any());

        assertThat(token).isEqualTo(accessToken);
    }

}
