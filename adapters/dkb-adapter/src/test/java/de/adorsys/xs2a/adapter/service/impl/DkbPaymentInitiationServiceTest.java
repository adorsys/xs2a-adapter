package de.adorsys.xs2a.adapter.service.impl;

import de.adorsys.xs2a.adapter.security.AccessTokenService;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class DkbPaymentInitiationServiceTest {

    private final AccessTokenService accessService = Mockito.mock(AccessTokenService.class);
    private DkbPaymentInitiationService service = new DkbPaymentInitiationService("url", accessService, null);

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
