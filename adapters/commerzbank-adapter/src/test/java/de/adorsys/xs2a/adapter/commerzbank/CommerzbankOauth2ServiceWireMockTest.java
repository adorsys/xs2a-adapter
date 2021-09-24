package de.adorsys.xs2a.adapter.commerzbank;

import de.adorsys.xs2a.adapter.api.Oauth2Service;
import de.adorsys.xs2a.adapter.api.model.TokenResponse;
import de.adorsys.xs2a.adapter.test.ServiceWireMockTest;
import de.adorsys.xs2a.adapter.test.TestRequestResponse;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.LinkedHashMap;

import static org.assertj.core.api.Assertions.assertThat;

@ServiceWireMockTest(CommerzbankServiceProvider.class)
class CommerzbankOauth2ServiceWireMockTest {

    private final Oauth2Service oauth2Service;

    CommerzbankOauth2ServiceWireMockTest(Oauth2Service oauth2Service) {
        this.oauth2Service = oauth2Service;
    }

    @Test
    void getAccessToken() throws IOException {
        var requestResponse = new TestRequestResponse("oauth2/get-access-token.json");

        var modifiableParams = new LinkedHashMap<>(requestResponse.requestParams().toMap());

        var actualToken = oauth2Service.getToken(requestResponse.requestHeaders().toMap(),
            new Oauth2Service.Parameters(modifiableParams));

        assertThat(actualToken)
            .isNotNull()
            .isEqualTo(requestResponse.responseBody(TokenResponse.class));
    }
}
