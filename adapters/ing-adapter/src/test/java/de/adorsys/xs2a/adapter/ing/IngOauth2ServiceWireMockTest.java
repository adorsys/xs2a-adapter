package de.adorsys.xs2a.adapter.ing;

import de.adorsys.xs2a.adapter.service.Oauth2Service;
import de.adorsys.xs2a.adapter.service.model.TokenResponse;
import de.adorsys.xs2a.adapter.test.ServiceWireMockTest;
import de.adorsys.xs2a.adapter.test.TestRequestResponse;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.util.HashMap;
import java.util.LinkedHashMap;

import static org.assertj.core.api.Assertions.assertThat;

@ServiceWireMockTest(IngServiceProvider.class)
class IngOauth2ServiceWireMockTest {

    private final Oauth2Service service;

    IngOauth2ServiceWireMockTest(Oauth2Service service) {
        this.service = service;
    }

    @Test
    void getAuthorizationUriJson() throws Exception {
        TestRequestResponse requestResponse = new TestRequestResponse("oauth2/get-authorization-uri.json");

        URI authorizationRequestUri = service.getAuthorizationRequestUri(requestResponse.requestHeaders().toMap(),
            new Oauth2Service.Parameters(new HashMap<>(requestResponse.requestParams().toMap())));

        URI expectedUri = requestResponse.responseBody(URI.class);
        // not comparing two URIs directly because of the dynamic wiremock port
        assertThat(authorizationRequestUri.getPath()).isEqualTo(expectedUri.getPath());
        assertThat(authorizationRequestUri.getQuery()).isEqualTo(expectedUri.getQuery());
    }

    @Test
    void getToken() throws Exception {
        TestRequestResponse requestResponse = new TestRequestResponse("oauth2/get-token.json");

        TokenResponse tokenResponse = service.getToken(requestResponse.requestHeaders().toMap(),
            new Oauth2Service.Parameters(new LinkedHashMap<>(requestResponse.requestParams().toMap())));

        assertThat(tokenResponse).isEqualTo(requestResponse.responseBody(TokenResponse.class));
    }
}
