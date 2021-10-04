package de.adorsys.xs2a.adapter.santander;

import de.adorsys.xs2a.adapter.api.Oauth2Service;
import de.adorsys.xs2a.adapter.api.model.TokenResponse;
import de.adorsys.xs2a.adapter.test.ServiceWireMockTest;
import de.adorsys.xs2a.adapter.test.TestRequestResponse;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@ServiceWireMockTest(SantanderServiceProvider.class)
class SantanderOauth2ServiceWireMockTest {

    private final Oauth2Service oauth2Service;

    SantanderOauth2ServiceWireMockTest(Oauth2Service oauth2Service) {
        this.oauth2Service = oauth2Service;
    }

    @Test
    void getAuthorizationEndpoint() throws IOException {
        TestRequestResponse requestResponse = new TestRequestResponse("oauth2/get-authorization-endpoint.json");

        // Needed a mutable map to put into Parameters, otherwise requestParams().toMap() will return unmodifiable collection
        // which will throw UnsupportedOperationException downstream.
        Map<String, String> modifiableParams = new LinkedHashMap<>(requestResponse.requestParams().toMap());

        URI actualUri = oauth2Service.getAuthorizationRequestUri(requestResponse.requestHeaders().toMap(),
            new Oauth2Service.Parameters(modifiableParams));

        // It's onerous to get a WireMock port in running test environment, thus checking determined parts of URL only
        assertThat(actualUri)
            .isNotNull()
            .asString()
            .contains("http://localhost", "response_type=code", "scope=AIS%3A3087d8e2-2eb0-4e54-9af9-32ea8c6eef02");
    }

    @Test
    void getClientCredentialsToken() throws IOException {
        TestRequestResponse requestResponse = new TestRequestResponse("oauth2/get-client-credentials-token.json");

        Map<String, String> modifiableParams = new LinkedHashMap<>(requestResponse.requestParams().toMap());

        TokenResponse actualToken = oauth2Service.getToken(requestResponse.requestHeaders().toMap(),
            new Oauth2Service.Parameters(modifiableParams));

        assertThat(actualToken)
            .isNotNull()
            .isEqualTo(requestResponse.responseBody(TokenResponse.class));
    }

    @Test
    void getAccessToken() throws IOException {
        TestRequestResponse requestResponse = new TestRequestResponse("oauth2/get-access-token.json");

        Map<String, String> modifiableParams = new LinkedHashMap<>(requestResponse.requestParams().toMap());

        TokenResponse actualToken = oauth2Service.getToken(requestResponse.requestHeaders().toMap(),
            new Oauth2Service.Parameters(modifiableParams));

        assertThat(actualToken)
            .isNotNull()
            .isEqualTo(requestResponse.responseBody(TokenResponse.class));
    }
}
