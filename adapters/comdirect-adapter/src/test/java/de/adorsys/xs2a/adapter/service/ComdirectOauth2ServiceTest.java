package de.adorsys.xs2a.adapter.service;

import de.adorsys.xs2a.adapter.adapter.model.OauthToken;
import de.adorsys.xs2a.adapter.adapter.oauth2.api.model.AuthorisationServerMetaData;
import de.adorsys.xs2a.adapter.http.ApacheHttpClient;
import de.adorsys.xs2a.adapter.http.HttpClient;
import de.adorsys.xs2a.adapter.http.Request;
import de.adorsys.xs2a.adapter.http.RequestBuilderImpl;
import de.adorsys.xs2a.adapter.service.model.Aspsp;
import de.adorsys.xs2a.adapter.validation.RequestValidationException;
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

import static de.adorsys.xs2a.adapter.service.Oauth2Service.Parameters;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class ComdirectOauth2ServiceTest {

    private static final String SCA_OAUTH_LINK =
        "https://psd.comdirect.de/public/berlingroup/authorize/a437c154-d926-48c6-ae71-7e52b402bf51";
    private static final String IDP_URL =
        "https://psd.comdirect.de/public/berlingroup/idp";
    private static final String ORG_ID = "PSDDE-BAFIN-999999";
    private static final String STATE = "test";
    private static final String REDIRECT_URI = "https://example.com/cb";
    private static final String CONSENT_ID = "consent-id";
    private static final String AUTHORIZATION_CODE = "authorization-code";
    private static final String BASE_URI = "https://psd.comdirect.de/berlingroup";
    private static final String TOKEN_ENDPOINT = BASE_URI + "/v1/token";
    private ComdirectOauth2Service oauth2Service;
    private Pkcs12KeyStore keyStore;

    @BeforeEach
    void setUp() throws Exception {
        keyStore = Mockito.mock(Pkcs12KeyStore.class);
        when(keyStore.getOrganizationIdentifier())
            .thenReturn(ORG_ID);
    }

    @Test
    void getAuthorizationRequestUri() throws IOException {
        oauth2Service = ComdirectOauth2Service.create(new Aspsp(), null, keyStore);
        Parameters parameters = new Parameters();
        parameters.setScaOAuthLink(SCA_OAUTH_LINK);
        parameters.setState(STATE);
        parameters.setConsentId(CONSENT_ID);
        parameters.setRedirectUri(REDIRECT_URI);

        URI uri = oauth2Service.getAuthorizationRequestUri(null, parameters);

        assertEquals(SCA_OAUTH_LINK + "?" +
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
        oauth2Service = ComdirectOauth2Service.create(new Aspsp(), null, keyStore);
        Parameters parameters = new Parameters();
        parameters.setScaOAuthLink(SCA_OAUTH_LINK);
        parameters.setState(STATE);
        parameters.setPaymentId("payment-id");
        parameters.setRedirectUri(REDIRECT_URI);

        URI uri = oauth2Service.getAuthorizationRequestUri(null, parameters);

        assertEquals(SCA_OAUTH_LINK + "?" +
            "response_type=code&" +
            "state=" + STATE + "&" +
            "redirect_uri=" + URLEncoder.encode(REDIRECT_URI, StandardCharsets.UTF_8.name()) + "&" +
            "client_id=" + ORG_ID + "&" +
            "code_challenge_method=S256&" +
            "code_challenge=" + oauth2Service.codeChallenge() + "&" +
            "scope=PIS%3Apayment-id", uri.toString());
    }

    @Test
    void getAuthorizationRequestUri_noScaOAuthLink() throws IOException {
        Aspsp aspsp = new Aspsp();
        aspsp.setIdpUrl(IDP_URL);
        AuthorisationServerMetaData metaData = new AuthorisationServerMetaData();
        metaData.setAuthorisationEndpoint(IDP_URL);

        HttpClient httpClient = Mockito.mock(HttpClient.class);
        Request.Builder requestBuilder = Mockito.spy(new RequestBuilderImpl(httpClient, null, null));
        oauth2Service = ComdirectOauth2Service.create(aspsp, httpClient, keyStore);

        Parameters parameters = new Parameters();
        parameters.setState(STATE);
        parameters.setConsentId(CONSENT_ID);
        parameters.setRedirectUri(REDIRECT_URI);

        when(httpClient.get(anyString())).thenReturn(requestBuilder);
        doReturn(new Response<>(200, metaData, null)).when(requestBuilder).send(any());

        URI uri = oauth2Service.getAuthorizationRequestUri(null, parameters);

        verify(httpClient, times(1)).get(anyString());
        verify(requestBuilder, times(1)).send(any());

        assertEquals(IDP_URL + "?" +
            "response_type=code&" +
            "state=" + STATE + "&" +
            "redirect_uri=" + URLEncoder.encode(REDIRECT_URI, StandardCharsets.UTF_8.name()) + "&" +
            "client_id=" + ORG_ID + "&" +
            "code_challenge_method=S256&" +
            "code_challenge=" + oauth2Service.codeChallenge() + "&" +
            "scope=AIS%3A" + CONSENT_ID, uri.toString());
    }

    @Test
    void getAuthorizationRequestUri_noScaOAuthLinkNoIdpUrl() {
        oauth2Service = ComdirectOauth2Service.create(new Aspsp(), null, keyStore);

        assertThrows(RequestValidationException.class,
            () -> oauth2Service.getAuthorizationRequestUri(null, new Parameters()));
    }

    @Test
    void getToken() throws IOException {
        Aspsp aspsp = new Aspsp();
        aspsp.setUrl(BASE_URI);
        HttpClient httpClient = Mockito.spy(new ApacheHttpClient(null));
        doReturn(new Response<>(200, new OauthToken(), ResponseHeaders.emptyResponseHeaders()))
            .when(httpClient).send(Mockito.argThat(req -> req.uri().equals(TOKEN_ENDPOINT)), Mockito.any());
        oauth2Service = ComdirectOauth2Service.create(aspsp, httpClient, keyStore);
        Parameters parameters = new Parameters();
        parameters.setGrantType(Oauth2Service.GrantType.AUTHORIZATION_CODE.toString());
        parameters.setAuthorizationCode(AUTHORIZATION_CODE);
        parameters.setRedirectUri(REDIRECT_URI);

        oauth2Service.getToken(null, parameters);

        Map<String, String> expectedBody = new HashMap<>();
        expectedBody.put(Parameters.GRANT_TYPE, Oauth2Service.GrantType.AUTHORIZATION_CODE.toString());
        expectedBody.put(Parameters.CLIENT_ID, ORG_ID);
        expectedBody.put(Parameters.CODE, AUTHORIZATION_CODE);
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
