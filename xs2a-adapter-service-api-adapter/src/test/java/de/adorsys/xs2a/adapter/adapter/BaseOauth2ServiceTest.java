package de.adorsys.xs2a.adapter.adapter;

import de.adorsys.xs2a.adapter.adapter.oauth2.api.model.AuthorisationServerMetaData;
import de.adorsys.xs2a.adapter.http.ApacheHttpClient;
import de.adorsys.xs2a.adapter.http.HttpClient;
import de.adorsys.xs2a.adapter.service.Oauth2Service.Parameters;
import de.adorsys.xs2a.adapter.service.Response;
import de.adorsys.xs2a.adapter.service.ResponseHeaders;
import de.adorsys.xs2a.adapter.service.model.Aspsp;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

class BaseOauth2ServiceTest {

    @Test
    void scaOauthLinkUsedForAuthorizationServerMetadataResolution() throws IOException {
        String scaOauthLink = "https://example.com/.well-known";
        HttpClient httpClient = Mockito.spy(new ApacheHttpClient(null));
        Mockito.doReturn(authorizationServerMetadata())
            .when(httpClient).send(Mockito.argThat(req -> req.uri().equals(scaOauthLink)), Mockito.any());
        BaseOauth2Service oauth2Service = new BaseOauth2Service(null, httpClient);
        Parameters parameters = new Parameters();
        parameters.setScaOAuthLink(scaOauthLink);

        oauth2Service.getAuthorizationRequestUri(null, parameters);

        Mockito.verify(httpClient, Mockito.times(1))
            .send(Mockito.argThat(req -> req.uri().equals(scaOauthLink)), Mockito.any());
    }

    private Response<AuthorisationServerMetaData> authorizationServerMetadata() {
        AuthorisationServerMetaData metadata = new AuthorisationServerMetaData();
        metadata.setAuthorisationEndpoint("https://server.example.com/authorize");
        return new Response<>(200, metadata, ResponseHeaders.emptyResponseHeaders());
    }

    @Test
    void authorizationServerMetadataResolutionFallsBackToIdpUrlWhenScaOauthLinkIsMissing() throws IOException {
        String idpUrl = "https://idp.example.com/.well-known";
        Aspsp aspsp = new Aspsp();
        aspsp.setIdpUrl(idpUrl);
        HttpClient httpClient = Mockito.spy(new ApacheHttpClient(null));
        Mockito.doReturn(authorizationServerMetadata())
            .when(httpClient).send(Mockito.argThat(req -> req.uri().equals(idpUrl)), Mockito.any());
        BaseOauth2Service oauth2Service = new BaseOauth2Service(aspsp, httpClient);

        oauth2Service.getAuthorizationRequestUri(null, new Parameters());

        Mockito.verify(httpClient, Mockito.times(1))
            .send(Mockito.argThat(req -> req.uri().equals(idpUrl)), Mockito.any());
    }
}
