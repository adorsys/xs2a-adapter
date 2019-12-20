package de.adorsys.xs2a.adapter.service;

import de.adorsys.xs2a.adapter.adapter.oauth2.api.model.AuthorisationServerMetaData;
import de.adorsys.xs2a.adapter.http.ApacheHttpClient;
import de.adorsys.xs2a.adapter.http.HttpClient;
import de.adorsys.xs2a.adapter.service.Oauth2Service.Parameters;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SparkasseOauth2ServiceTest {
    private static final String ORG_ID = "PSDDE-BAFIN-999999";
    private static final String STATE = "xyz";
    private static final String AUTHORIZATION_ENDPOINT = "https://www.sparkasse.de/oauth/authorize";
    public static final String SCA_OAUTH_LINK = "https://sparkasse.de/oauth/.well-known";
    public static final String CONSENT_ID = "consent-id";

    private Pkcs12KeyStore keyStore;
    private SparkasseOauth2Service oauth2Service;

    @BeforeEach
    void setUp() throws Exception {
        keyStore = Mockito.mock(Pkcs12KeyStore.class);
        Mockito.when(keyStore.getOrganizationIdentifier())
            .thenReturn(ORG_ID);

        HttpClient httpClient = Mockito.spy(new ApacheHttpClient(null));
        Mockito.doReturn(authorizationServerMetadata())
            .when(httpClient).send(Mockito.argThat(req -> req.uri().equals(SCA_OAUTH_LINK)), Mockito.any());

        oauth2Service = SparkasseOauth2Service.create(null, httpClient, keyStore);
    }

    @Test
    void getAuthorizationRequestUri() throws IOException {

        Parameters parameters = new Parameters();
        parameters.setScaOAuthLink(SCA_OAUTH_LINK);
        parameters.setState(STATE);
        parameters.setConsentId(CONSENT_ID);

        URI uri = oauth2Service.getAuthorizationRequestUri(null, parameters);

        assertEquals(AUTHORIZATION_ENDPOINT + "?" +
            "state=xyz&" +
            "code_challenge_method=S256&" +
            "code_challenge=" + oauth2Service.codeChallenge() + "&" +
            "responseType=code&" +
            "clientId=" + ORG_ID + "&" +
            "scope=AIS:%20" + CONSENT_ID, uri.toString());
    }

    private Response<AuthorisationServerMetaData> authorizationServerMetadata() {
        AuthorisationServerMetaData metadata = new AuthorisationServerMetaData();
        metadata.setAuthorisationEndpoint(AUTHORIZATION_ENDPOINT);
        return new Response<>(200, metadata, ResponseHeaders.emptyResponseHeaders());
    }
}
