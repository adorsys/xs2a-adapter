package de.adorsys.xs2a.adapter.commerzbank.service;

import de.adorsys.xs2a.adapter.adapter.model.OauthToken;
import de.adorsys.xs2a.adapter.http.ApacheHttpClient;
import de.adorsys.xs2a.adapter.http.HttpClient;
import de.adorsys.xs2a.adapter.service.Oauth2Service;
import de.adorsys.xs2a.adapter.service.Oauth2Service.Parameters;
import de.adorsys.xs2a.adapter.service.Pkcs12KeyStore;
import de.adorsys.xs2a.adapter.service.Response;
import de.adorsys.xs2a.adapter.service.ResponseHeaders;
import de.adorsys.xs2a.adapter.service.model.Aspsp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CommerzbankOauth2ServiceTest {
    private static final String ORG_ID = "PSDDE-BAFIN-999999";
    private static final String BASE_URL = "https://psd2.api-sandbox.commerzbank.com/berlingroup";
    private static final String CONSENT_ID = "consent-id";
    private static final String STATE = "test";
    private static final String REDIRECT_URI = "https://example.com/cb";
    private static final String AUTHORIZATION_ENDPOINT =
        "https://psd2.api-sandbox.commerzbank.com/public/berlingroup/authorize/authorisation-id";
    private static final String TOKEN_ENDPOINT = BASE_URL + "/v1/token";
    private static final String AUTHORIZATION_CODE = "authorization-code";


    private Aspsp aspsp;
    private HttpClient httpClient;
    private Pkcs12KeyStore keyStore;
    private CommerzbankOauth2Service oauth2Service;

    @BeforeEach
    void setUp() throws Exception {
        aspsp = new Aspsp();
        aspsp.setUrl(BASE_URL);

        httpClient = Mockito.spy(new ApacheHttpClient(null));
        Mockito.doReturn(new Response<>(200, new OauthToken(), ResponseHeaders.emptyResponseHeaders()))
            .when(httpClient).send(Mockito.argThat(req -> req.uri().equals(TOKEN_ENDPOINT)), Mockito.any());

        keyStore = Mockito.mock(Pkcs12KeyStore.class);
        Mockito.when(keyStore.getOrganizationIdentifier())
            .thenReturn(ORG_ID);

        oauth2Service = CommerzbankOauth2Service.create(aspsp, httpClient, keyStore);
    }

    @Test
    void getAuthorizationRequestUri() throws IOException {
        Parameters parameters = new Parameters();
        parameters.setScaOAuthLink(AUTHORIZATION_ENDPOINT);
        parameters.setState(STATE);
        parameters.setRedirectUri(REDIRECT_URI);
        parameters.setConsentId(CONSENT_ID);

        URI uri = oauth2Service.getAuthorizationRequestUri(null, parameters);

        assertEquals(AUTHORIZATION_ENDPOINT + "?" +
            "response_type=code&" +
            "state=" + STATE + "&" +
            "redirect_uri=" + URLEncoder.encode(REDIRECT_URI, StandardCharsets.UTF_8.name()) + "&" +
            "client_id=" + ORG_ID + "&" +
            "code_challenge_method=S256&" +
            "code_challenge=" + oauth2Service.codeChallenge() + "&" +
            "scope=AIS%3A" + CONSENT_ID, uri.toString());
    }

    @Test
    void getAuthorizationRequestUriForPayment() throws IOException {
        Parameters parameters = new Parameters();
        parameters.setScaOAuthLink(AUTHORIZATION_ENDPOINT);
        parameters.setState(STATE);
        parameters.setRedirectUri(REDIRECT_URI);
        parameters.setPaymentId("payment-id");

        URI uri = oauth2Service.getAuthorizationRequestUri(null, parameters);

        assertEquals(AUTHORIZATION_ENDPOINT + "?" +
            "response_type=code&" +
            "state=" + STATE + "&" +
            "redirect_uri=" + URLEncoder.encode(REDIRECT_URI, StandardCharsets.UTF_8.name()) + "&" +
            "client_id=" + ORG_ID + "&" +
            "code_challenge_method=S256&" +
            "code_challenge=" + oauth2Service.codeChallenge() + "&" +
            "scope=PIS%3Apayment-id", uri.toString());
    }

    @Test
    void getToken() throws IOException {
        Parameters parameters = new Parameters();
        parameters.setGrantType(Oauth2Service.GrantType.AUTHORIZATION_CODE.toString());
        parameters.setAuthorizationCode(AUTHORIZATION_CODE);
        parameters.setRedirectUri(REDIRECT_URI);

        oauth2Service.getToken(null, parameters);

        Map<String, String> expectedBody = new HashMap<>();
        expectedBody.put(Parameters.GRANT_TYPE, Oauth2Service.GrantType.AUTHORIZATION_CODE.toString());
        expectedBody.put(Parameters.CODE, AUTHORIZATION_CODE);
        expectedBody.put(Parameters.CLIENT_ID, ORG_ID);
        expectedBody.put(Parameters.REDIRECT_URI, REDIRECT_URI);
        expectedBody.put(Parameters.CODE_VERIFIER, oauth2Service.codeVerifier());
        Mockito.verify(httpClient, Mockito.times(1)).send(ArgumentMatchers.argThat(req -> {
            boolean tokenExchange = req.uri().equals(TOKEN_ENDPOINT);
            if (tokenExchange) {
                assertEquals(expectedBody, req.urlEncodedBody());
            }
            return tokenExchange;
        }), Mockito.any());
    }
}
