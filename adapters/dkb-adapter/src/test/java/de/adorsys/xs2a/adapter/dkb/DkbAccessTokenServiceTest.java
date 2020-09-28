package de.adorsys.xs2a.adapter.dkb;

import de.adorsys.xs2a.adapter.api.Response;
import de.adorsys.xs2a.adapter.api.ResponseHeaders;
import de.adorsys.xs2a.adapter.api.config.AdapterConfig;
import de.adorsys.xs2a.adapter.api.http.HttpClient;
import de.adorsys.xs2a.adapter.impl.http.RequestBuilderImpl;
import de.adorsys.xs2a.adapter.impl.security.AccessTokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

class DkbAccessTokenServiceTest {

    private AccessTokenService tokenService;
    private HttpClient httpClient;

    @BeforeEach
    public void setUp() {
        String file = getClass().getResource(File.separator + "dkb.adapter.config.properties").getFile();
        AdapterConfig.setConfigFile(file);

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
