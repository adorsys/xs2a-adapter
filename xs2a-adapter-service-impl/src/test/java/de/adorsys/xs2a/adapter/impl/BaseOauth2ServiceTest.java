package de.adorsys.xs2a.adapter.impl;

import de.adorsys.xs2a.adapter.api.Oauth2Service;
import de.adorsys.xs2a.adapter.api.Oauth2Service.Parameters;
import de.adorsys.xs2a.adapter.api.Response;
import de.adorsys.xs2a.adapter.api.ResponseHeaders;
import de.adorsys.xs2a.adapter.api.http.HttpClient;
import de.adorsys.xs2a.adapter.api.model.Aspsp;
import de.adorsys.xs2a.adapter.api.model.TokenResponse;
import de.adorsys.xs2a.adapter.impl.http.ApacheHttpClient;
import de.adorsys.xs2a.adapter.impl.oauth2.api.model.AuthorisationServerMetaData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BaseOauth2ServiceTest {

    private static final String SCA_OAUTH_LINK = "https://example.com/.well-known";
    private static final String TOKEN_ENDPOINT = "https://server.example.com/token";
    private static final String ACCESS_TOKEN = "access token";
    private static final String IDP_URL = "https://server.example.com/idp";
    private static final String AUTHORISATION_ENDPOINT = "https://authorisation.endpoint";
    private static final String STATE = "state";
    private static final String REDIRECT_URI = "https://redirect.uri?param1=a&param2=b&param3=c";

    private Parameters parameters = buildParametersWithScaOauthLink();
    private BaseOauth2Service oauth2Service;

    @Spy
    private HttpClient httpClient = new ApacheHttpClient(null, null);

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        oauth2Service = new BaseOauth2Service(buildAspspWithIdpUrl(), httpClient);
    }

    @Test
    void scaOauthLinkUsedForAuthorizationServerMetadataResolution() throws IOException {
        Mockito.doReturn(authorizationServerMetadata())
            .when(httpClient).send(Mockito.argThat(req -> req.uri().equals(SCA_OAUTH_LINK)), Mockito.any());

        oauth2Service.getAuthorizationRequestUri(null, parameters);

        Mockito.verify(httpClient, Mockito.times(1))
            .send(Mockito.argThat(req -> req.uri().equals(SCA_OAUTH_LINK)), Mockito.any());
    }

    @Test
    void authorizationServerMetadataResolutionFallsBackToIdpUrlWhenScaOauthLinkIsMissing() throws IOException {
        Mockito.doReturn(authorizationServerMetadata())
            .when(httpClient).send(Mockito.argThat(req -> req.uri().equals(IDP_URL)), Mockito.any());

        oauth2Service.getAuthorizationRequestUri(null, new Parameters());

        Mockito.verify(httpClient, Mockito.times(1))
            .send(Mockito.argThat(req -> req.uri().equals(IDP_URL)), Mockito.any());
    }

    @Test
    void getAuthorizationEndpoint_withAuthorisationEndpoint() throws IOException {
        parameters.setAuthorizationEndpoint(AUTHORISATION_ENDPOINT);
        parameters.setState(STATE);
        parameters.setRedirectUri(REDIRECT_URI);

        String expectedAuthorisationRequestUri = AUTHORISATION_ENDPOINT + "?" +
            Parameters.RESPONSE_TYPE + "=" + Oauth2Service.ResponseType.CODE.toString() +
            "&" + Parameters.STATE + "=" + STATE +
            "&" + Parameters.REDIRECT_URI + "=" + URLEncoder.encode(REDIRECT_URI, StandardCharsets.UTF_8.name());

        URI actual = oauth2Service.getAuthorizationRequestUri(null, parameters);

        assertEquals(expectedAuthorisationRequestUri, actual.toString());
    }

    @Test
    void getToken_withAvailableScaOauthLink() throws IOException {
        Mockito.doReturn(authorizationServerMetadata())
            .when(httpClient).send(Mockito.argThat(req -> req.uri().equals(SCA_OAUTH_LINK)), Mockito.any());
        Mockito.doReturn(tokenResponse())
            .when(httpClient).send(Mockito.argThat(req -> req.uri().equals(TOKEN_ENDPOINT)), Mockito.any());

        TokenResponse actual = oauth2Service.getToken(null, parameters);

        Mockito.verify(httpClient, Mockito.times(2))
            .send(Mockito.any(), Mockito.any());

        assertEquals(ACCESS_TOKEN, actual.getAccessToken());
    }

    @Test
    void getToken_withAvailableTokenEndpoint() throws IOException {
        parameters.setTokenEndpoint(TOKEN_ENDPOINT);
        Mockito.doReturn(tokenResponse()).when(httpClient).send(Mockito.any(), Mockito.any());

        TokenResponse actual = oauth2Service.getToken(null, parameters);

        Mockito.verify(httpClient, Mockito.times(1))
            .send(Mockito.any(), Mockito.any());

        assertEquals(ACCESS_TOKEN, actual.getAccessToken());
    }

    private Response<AuthorisationServerMetaData> authorizationServerMetadata() {
        AuthorisationServerMetaData metadata = new AuthorisationServerMetaData();
        metadata.setAuthorisationEndpoint("https://server.example.com/authorize");
        metadata.setTokenEndpoint(TOKEN_ENDPOINT);
        return new Response<>(200, metadata, ResponseHeaders.emptyResponseHeaders());
    }

    private Response<TokenResponse> tokenResponse() {
        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setAccessToken(ACCESS_TOKEN);
        return new Response<>(200, tokenResponse, ResponseHeaders.emptyResponseHeaders());
    }

    private Aspsp buildAspspWithIdpUrl() {
        Aspsp aspsp = new Aspsp();
        aspsp.setIdpUrl(IDP_URL);
        return aspsp;
    }

    private Parameters buildParametersWithScaOauthLink() {
        Parameters params = new Parameters();
        params.setScaOAuthLink(SCA_OAUTH_LINK);
        return params;
    }
}
