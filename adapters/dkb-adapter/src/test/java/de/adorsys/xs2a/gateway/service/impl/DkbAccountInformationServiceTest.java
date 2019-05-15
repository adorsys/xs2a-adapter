package de.adorsys.xs2a.gateway.service.impl;

import de.adorsys.xs2a.gateway.security.AccessTokenService;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class DkbAccountInformationServiceTest {
    private final AccessTokenService accessService = Mockito.mock(AccessTokenService.class);
    private DkbAccountInformationService service = new DkbAccountInformationService("url", accessService);

    @Test
    public void addBearerHeader() {
        when(accessService.retrieveToken()).thenReturn("token");

        Map<String, String> headers = service.addBearerHeader(new HashMap<>());

        verify(accessService, times(1)).retrieveToken();

        assertThat(headers).hasSize(1);
        assertThat(headers.containsKey("Authorization")).isTrue();
        assertThat(headers.get("Authorization")).isNotNull();
    }
}