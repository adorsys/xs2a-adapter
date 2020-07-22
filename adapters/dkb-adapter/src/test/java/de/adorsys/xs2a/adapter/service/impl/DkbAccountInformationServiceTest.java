package de.adorsys.xs2a.adapter.service.impl;

import de.adorsys.xs2a.adapter.security.AccessTokenService;
import de.adorsys.xs2a.adapter.service.model.Aspsp;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class DkbAccountInformationServiceTest {
    private static final Aspsp ASPSP = buildAspspWithUrl();
    private final AccessTokenService accessService = Mockito.mock(AccessTokenService.class);
    private DkbAccountInformationService service = new DkbAccountInformationService(ASPSP, accessService, null, null);

    @Test
    void addBearerHeader() {
        when(accessService.retrieveToken()).thenReturn("token");

        Map<String, String> headers = service.addBearerHeader(new HashMap<>());

        verify(accessService, times(1)).retrieveToken();

        assertThat(headers)
            .hasSize(1)
            .containsKey("Authorization");
        assertThat(headers.get("Authorization")).isNotNull();
    }

    private static Aspsp buildAspspWithUrl() {
        Aspsp aspsp = new Aspsp();
        aspsp.setUrl("url");
        return aspsp;
    }
}
