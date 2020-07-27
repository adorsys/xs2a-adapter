package de.adorsys.xs2a.adapter.sparkasse;

import de.adorsys.xs2a.adapter.adapter.model.OauthToken;
import de.adorsys.xs2a.adapter.adapter.oauth2.api.model.AuthorisationServerMetaData;
import de.adorsys.xs2a.adapter.http.ApacheHttpClient;
import de.adorsys.xs2a.adapter.http.HttpClient;
import de.adorsys.xs2a.adapter.service.Oauth2Service.GrantType;
import de.adorsys.xs2a.adapter.service.Oauth2Service.Parameters;
import de.adorsys.xs2a.adapter.service.Pkcs12KeyStore;
import de.adorsys.xs2a.adapter.service.Response;
import de.adorsys.xs2a.adapter.service.ResponseHeaders;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SparkasseOauth2ServiceTest {
    private static final String ORG_ID = "PSDDE-BAFIN-999999";
    private static final String STATE = "xyz";
    private static final String AUTHORIZATION_ENDPOINT = "https://www.sparkasse.de/oauth/authorize";
    private static final String TOKEN_ENDPOINT = "https://www.sparkasse.de/oauth/token";
    private static final String SCA_OAUTH_LINK = "https://sparkasse.de/oauth/.well-known";
    private static final String CONSENT_ID = "consent-id";
    private static final String AUTHORIZATION_CODE = "authorization-code";
    private static final String REFRESH_TOKEN = "refresh-token";

    private Pkcs12KeyStore keyStore;
    private SparkasseOauth2Service oauth2Service;
    private HttpClient httpClient;

    @BeforeEach
    void setUp() throws Exception {
        keyStore = Mockito.mock(Pkcs12KeyStore.class);
        Mockito.when(keyStore.getOrganizationIdentifier())
            .thenReturn(ORG_ID);

        httpClient = Mockito.spy(new ApacheHttpClient(null));
        Mockito.doReturn(authorizationServerMetadata())
            .when(httpClient).send(Mockito.argThat(req -> req.uri().equals(SCA_OAUTH_LINK)), Mockito.any());
        Mockito.doReturn(tokenResponse())
            .when(httpClient).send(Mockito.argThat(req -> req.uri().equals(TOKEN_ENDPOINT)), Mockito.any());

        oauth2Service = SparkasseOauth2Service.create(null, httpClient, keyStore);
    }

    private Response<AuthorisationServerMetaData> authorizationServerMetadata() {
        AuthorisationServerMetaData metadata = new AuthorisationServerMetaData();
        metadata.setAuthorisationEndpoint(AUTHORIZATION_ENDPOINT);
        metadata.setTokenEndpoint(TOKEN_ENDPOINT);
        return new Response<>(200, metadata, ResponseHeaders.emptyResponseHeaders());
    }

    private Response<OauthToken> tokenResponse() {
        return new Response<>(200, new OauthToken(), ResponseHeaders.emptyResponseHeaders());
    }

    @Test
    void getAuthorizationRequestUri() throws IOException {

        Parameters parameters = new Parameters();
        parameters.setScaOAuthLink(SCA_OAUTH_LINK);
        parameters.setState(STATE);
        parameters.setConsentId(CONSENT_ID);

        URI uri = oauth2Service.getAuthorizationRequestUri(null, parameters);

        assertEquals(AUTHORIZATION_ENDPOINT + "?" +
            "responseType=code&" +
            "state=xyz&" +
            "clientId=" + ORG_ID + "&" +
            "code_challenge_method=S256&" +
            "code_challenge=" + oauth2Service.codeChallenge() + "&" +
            "scope=AIS%3A+" + CONSENT_ID, uri.toString());
    }

    @Test
    void getAuthorizationRequestUriForPayment() throws IOException {

        Parameters parameters = new Parameters();
        parameters.setScaOAuthLink(SCA_OAUTH_LINK);
        parameters.setState(STATE);
        parameters.setPaymentId("payment-id");

        URI uri = oauth2Service.getAuthorizationRequestUri(null, parameters);

        assertEquals(AUTHORIZATION_ENDPOINT + "?" +
            "responseType=code&" +
            "state=xyz&" +
            "clientId=" + ORG_ID + "&" +
            "code_challenge_method=S256&" +
            "code_challenge=" + oauth2Service.codeChallenge() + "&" +
            "scope=PIS%3A+payment-id", uri.toString());
    }

    @Test
    void getToken_authorizationCodeExchange() throws IOException {

        Parameters parameters = new Parameters();
        parameters.setScaOAuthLink(SCA_OAUTH_LINK);
        parameters.setGrantType(GrantType.AUTHORIZATION_CODE.toString());
        parameters.setAuthorizationCode(AUTHORIZATION_CODE);

        oauth2Service.getToken(null, parameters);

        Map<String, String> expectedBody = new HashMap<>();
        expectedBody.put(Parameters.GRANT_TYPE, GrantType.AUTHORIZATION_CODE.toString());
        expectedBody.put(Parameters.CODE, AUTHORIZATION_CODE);
        expectedBody.put(Parameters.CLIENT_ID, ORG_ID);
        expectedBody.put(Parameters.CODE_VERIFIER, oauth2Service.codeVerifier());
        Mockito.verify(httpClient, Mockito.times(1)).send(ArgumentMatchers.argThat(req -> {
            boolean tokenExchange = req.uri().equals(TOKEN_ENDPOINT);
            if (tokenExchange) {
                assertEquals(expectedBody, req.urlEncodedBody());
            }
            return tokenExchange;
        }), Mockito.any());
    }

    @Test
    void getToken_refresh() throws IOException {

        Parameters parameters = new Parameters();
        parameters.setScaOAuthLink(SCA_OAUTH_LINK);
        parameters.setGrantType(GrantType.REFRESH_TOKEN.toString());
        parameters.setRefreshToken(REFRESH_TOKEN);

        oauth2Service.getToken(null, parameters);

        Map<String, String> expectedBody = new HashMap<>();
        expectedBody.put(Parameters.GRANT_TYPE, GrantType.REFRESH_TOKEN.toString());
        expectedBody.put(Parameters.REFRESH_TOKEN, REFRESH_TOKEN);
        expectedBody.put(Parameters.CLIENT_ID, ORG_ID);
        Mockito.verify(httpClient, Mockito.times(1)).send(ArgumentMatchers.argThat(req -> {
            boolean tokenExchange = req.uri().equals(TOKEN_ENDPOINT);
            if (tokenExchange) {
                assertEquals(expectedBody, req.urlEncodedBody());
            }
            return tokenExchange;
        }), Mockito.any());
    }
}
