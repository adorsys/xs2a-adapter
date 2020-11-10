package de.adorsys.xs2a.adapter.serviceloader;

import de.adorsys.xs2a.adapter.api.EmbeddedPreAuthorisationService;
import de.adorsys.xs2a.adapter.api.RequestHeaders;
import de.adorsys.xs2a.adapter.api.model.EmbeddedPreAuthorisationRequest;
import de.adorsys.xs2a.adapter.api.model.TokenResponse;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class EmbeddedPreAuthorisationServiceImplTest {

    @Test
    void getToken() {
        AdapterServiceLoader serviceLoader = mock(AdapterServiceLoader.class);
        EmbeddedPreAuthorisationService service = mock(EmbeddedPreAuthorisationService.class);
        EmbeddedPreAuthorisationServiceImpl serviceImpl = new EmbeddedPreAuthorisationServiceImpl(serviceLoader);
        RequestHeaders requestHeaders = RequestHeaders.fromMap(Collections.emptyMap());
        EmbeddedPreAuthorisationRequest authorisationRequest = new EmbeddedPreAuthorisationRequest();
        TokenResponse expected = new TokenResponse();

        doReturn(service).when(serviceLoader).getEmbeddedPreAuthorisationService(eq(requestHeaders));
        doReturn(expected).when(service).getToken(authorisationRequest, requestHeaders);

        TokenResponse actual = serviceImpl.getToken(authorisationRequest, requestHeaders);

        assertThat(actual).isEqualTo(expected);

        verify(serviceLoader, times(1)).getEmbeddedPreAuthorisationService(any());
        verify(service, times(1)).getToken(any(), any());
    }
}
