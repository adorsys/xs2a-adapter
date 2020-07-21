package de.adorsys.xs2a.adapter.service.impl;

import de.adorsys.xs2a.adapter.http.HttpClient;
import de.adorsys.xs2a.adapter.http.RequestBuilderImpl;
import de.adorsys.xs2a.adapter.security.AccessTokenService;
import de.adorsys.xs2a.adapter.service.Response;
import de.adorsys.xs2a.adapter.service.ResponseHeaders;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

class DkbAccessTokenServiceTest {

    private AccessTokenService tokenService;
    private HttpClient httpClient;

    @BeforeEach
    public void setUp() {
        String file = getClass().getResource("/external.adapter.config.properties").getFile();
        System.setProperty("adapter.config.file.path", file);

        tokenService = DkbAccessTokenService.getInstance();
        httpClient = mock(HttpClient.class);
        ((DkbAccessTokenService) tokenService).setHttpClient(httpClient);

    }

    @Test
    void retrieveToken() {
        String accessToken = "accessToken";
        DkbAccessTokenService.TokenResponse tokenResponse = new DkbAccessTokenService.TokenResponse(accessToken, 3600);
        when(httpClient.send(any(), any())).thenReturn(new Response<>(200, tokenResponse, ResponseHeaders.fromMap(Collections.emptyMap())));
        when(httpClient.post(any())).thenReturn(new RequestBuilderImpl(httpClient, "POST", ""));

        String token = tokenService.retrieveToken();

        verify(httpClient, times(1)).send(any(), any());
        assertThat(token).isEqualTo(accessToken);
    }

}
