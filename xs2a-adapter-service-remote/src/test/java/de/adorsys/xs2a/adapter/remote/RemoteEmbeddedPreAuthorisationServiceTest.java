package de.adorsys.xs2a.adapter.remote;

import de.adorsys.xs2a.adapter.api.RequestHeaders;
import de.adorsys.xs2a.adapter.api.model.EmbeddedPreAuthorisationRequest;
import de.adorsys.xs2a.adapter.api.model.TokenResponse;
import de.adorsys.xs2a.adapter.remote.client.EmbeddedPreAuthorisationClient;
import de.adorsys.xs2a.adapter.rest.api.model.EmbeddedPreAuthorisationRequestTO;
import de.adorsys.xs2a.adapter.rest.api.model.TokenResponseTO;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class RemoteEmbeddedPreAuthorisationServiceTest {

    private static final String ACCESS_TOKEN = "94A2776E7BD6F611462BC4344E17773C65FC4C486401643B724D102A8936DFF4";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";

    private final EmbeddedPreAuthorisationClient client = mock(EmbeddedPreAuthorisationClient.class);
    private final RemoteEmbeddedPreAuthorisationService service = new RemoteEmbeddedPreAuthorisationService(client);
    private final ArgumentCaptor<EmbeddedPreAuthorisationRequestTO> argumentCaptor
        = ArgumentCaptor.forClass(EmbeddedPreAuthorisationRequestTO.class);

    @Test
    void getToken() {
        when(client.getToken(any(), any())).thenReturn(getTokenTO());

        TokenResponse actualResponse = service.getToken(getAuthorisationRequest(), RequestHeaders.empty());

        verify(client, times(1)).getToken(anyMap(), argumentCaptor.capture());

        assertThat(actualResponse)
            .isNotNull()
            .extracting("accessToken")
            .isEqualTo(ACCESS_TOKEN);

        assertThat(argumentCaptor.getValue())
            .isNotNull()
            .matches(request -> request.getPassword().equals(PASSWORD))
            .matches(request -> request.getUsername().equals(USERNAME));
    }

    private TokenResponseTO getTokenTO() {
        TokenResponseTO token = new TokenResponseTO();
        token.setAccessToken(ACCESS_TOKEN);
        return token;
    }

    private EmbeddedPreAuthorisationRequest getAuthorisationRequest() {
        EmbeddedPreAuthorisationRequest request = new EmbeddedPreAuthorisationRequest();
        request.setPassword(PASSWORD);
        request.setUsername(USERNAME);
        return request;
    }
}
