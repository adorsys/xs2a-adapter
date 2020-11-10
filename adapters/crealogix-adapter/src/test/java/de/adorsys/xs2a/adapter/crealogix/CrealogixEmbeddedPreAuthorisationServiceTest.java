package de.adorsys.xs2a.adapter.crealogix;

import de.adorsys.xs2a.adapter.api.RequestHeaders;
import de.adorsys.xs2a.adapter.api.Response;
import de.adorsys.xs2a.adapter.api.http.HttpClient;
import de.adorsys.xs2a.adapter.api.http.Request;
import de.adorsys.xs2a.adapter.api.model.Aspsp;
import de.adorsys.xs2a.adapter.api.model.EmbeddedPreAuthorisationRequest;
import de.adorsys.xs2a.adapter.api.model.TokenResponse;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class CrealogixEmbeddedPreAuthorisationServiceTest {

    @Test
    void getToken() throws IOException {
        String url = "https://localhost:8443/";
        HttpClient httpClient = mock(HttpClient.class);
        Aspsp aspsp = new Aspsp();
        aspsp.setIdpUrl(url);
        Response tppResponse = mock(Response.class);
        Response psd2Response = mock(Response.class);
        TokenResponse tppTokenResponse = new TokenResponse();
        tppTokenResponse.setAccessToken("tpp");
        TokenResponse psd2TokenResponse = new TokenResponse();
        psd2TokenResponse.setAccessToken("psd2");

        Request.Builder tppBuilder = mock(Request.Builder.class);
        Request.Builder psd2Builder = mock(Request.Builder.class);

        doReturn(tppBuilder).when(httpClient).post(eq("https://localhost:8443/token"));
        doReturn(tppBuilder).when(tppBuilder).urlEncodedBody(anyMap());
        doReturn(tppBuilder).when(tppBuilder).headers(anyMap());
        doReturn(tppResponse).when(tppBuilder).send(any());
        doReturn(tppTokenResponse).when(tppResponse).getBody();

        doReturn(psd2Builder).when(httpClient).post(eq("https://localhost:8443/pre-auth/1.0.5/psd2-auth/v1/auth/token"));
        doReturn(psd2Builder).when(psd2Builder).jsonBody(anyString());
        doReturn(psd2Builder).when(psd2Builder).headers(anyMap());
        doReturn(psd2Response).when(psd2Builder).send(any());
        doReturn(psd2TokenResponse).when(psd2Response).getBody();


        CrealogixEmbeddedPreAuthorisationService authorisationService
            = new CrealogixEmbeddedPreAuthorisationService(CrealogixClient.DKB, aspsp, httpClient);

        TokenResponse token = authorisationService.getToken(new EmbeddedPreAuthorisationRequest(), RequestHeaders.fromMap(Collections.emptyMap()));

        CrealogixAuthorisationToken authorisationToken = CrealogixAuthorisationToken.decode(token.getAccessToken());

        assertThat(token.getAccessToken()).isNotBlank();
        assertThat(authorisationToken.getTppToken()).isEqualTo("tpp");
        assertThat(authorisationToken.getPsd2AuthorisationToken()).isEqualTo("psd2");
    }

}
