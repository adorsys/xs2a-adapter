package de.adorsys.xs2a.adapter.crealogix;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.adorsys.xs2a.adapter.api.RequestHeaders;
import de.adorsys.xs2a.adapter.api.Response;
import de.adorsys.xs2a.adapter.api.ResponseHeaders;
import de.adorsys.xs2a.adapter.api.http.HttpClient;
import de.adorsys.xs2a.adapter.api.http.HttpClientConfig;
import de.adorsys.xs2a.adapter.api.http.HttpClientFactory;
import de.adorsys.xs2a.adapter.api.http.Request;
import de.adorsys.xs2a.adapter.api.model.Aspsp;
import de.adorsys.xs2a.adapter.api.model.EmbeddedPreAuthorisationRequest;
import de.adorsys.xs2a.adapter.api.model.TokenResponse;
import de.adorsys.xs2a.adapter.crealogix.model.CrealogixValidationResponse;
import de.adorsys.xs2a.adapter.impl.http.BaseHttpClientConfig;
import de.adorsys.xs2a.adapter.impl.http.Xs2aHttpLogSanitizer;
import de.adorsys.xs2a.adapter.impl.security.AccessTokenException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class CrealogixEmbeddedPreAuthorisationServiceTest {

    private final HttpClient httpClient = mock(HttpClient.class);
    private final HttpClientFactory clientFactory = mock(HttpClientFactory.class);
    private final Aspsp aspsp = getAspsp();
    private CrealogixEmbeddedPreAuthorisationService authorisationService;

    private final HttpClientConfig clientConfig = new BaseHttpClientConfig(new Xs2aHttpLogSanitizer(), null);

    @BeforeEach
    void setUp() {
        when(clientFactory.getHttpClient(any())).thenReturn(httpClient);
        when(clientFactory.getHttpClientConfig()).thenReturn(clientConfig);

        authorisationService
            = new CrealogixEmbeddedPreAuthorisationService(CrealogixClient.DKB, aspsp, clientFactory);
    }

    @Test
    void getToken() throws IOException {
        Response<TokenResponse> tppResponse = mock(Response.class);
        Response<CrealogixValidationResponse> psd2Response = mock(Response.class);
        TokenResponse tppTokenResponse = new TokenResponse();
        tppTokenResponse.setAccessToken("tpp");
        TokenResponse psd2TokenResponse = new TokenResponse();
        psd2TokenResponse.setAccessToken("psd2");

        Request.Builder tppBuilder = mock(Request.Builder.class);
        Request.Builder psd2Builder = mock(Request.Builder.class);

        doReturn(tppBuilder).when(httpClient).post("https://localhost:8443/token");
        doReturn(tppBuilder).when(tppBuilder).urlEncodedBody(anyMap());
        doReturn(tppBuilder).when(tppBuilder).headers(anyMap());
        doReturn(tppResponse).when(tppBuilder).send(any());
        doReturn(tppTokenResponse).when(tppResponse).getBody();

        doReturn(psd2Builder).when(httpClient).post("https://localhost:8443/foo/boo/token");
        doReturn(psd2Builder).when(psd2Builder).jsonBody(anyString());
        doReturn(psd2Builder).when(psd2Builder).headers(anyMap());
        doReturn(psd2Response).when(psd2Builder).send(any());
        doReturn(psd2Response).when(psd2Response).map(any());
        doReturn(psd2TokenResponse).when(psd2Response).getBody();

        TokenResponse token = authorisationService.getToken(new EmbeddedPreAuthorisationRequest(), RequestHeaders.fromMap(Collections.emptyMap()));

        CrealogixAuthorisationToken authorisationToken = CrealogixAuthorisationToken.decode(token.getAccessToken());

        assertThat(token.getAccessToken()).isNotBlank();
        assertThat(authorisationToken.getTppToken()).isEqualTo("tpp");
        assertThat(authorisationToken.getPsd2AuthorisationToken()).isEqualTo("psd2");
    }

    @ParameterizedTest
    @ValueSource(ints = {200, 201, 202})
    void responseHandler_successfulStatuses(int statusCode) throws JsonProcessingException {
        String stringBody = new ObjectMapper().writeValueAsString(new TokenResponse());
        InputStream inputStream = new ByteArrayInputStream(stringBody.getBytes());

        TokenResponse actualResponse
            = authorisationService.responseHandler(TokenResponse.class).apply(statusCode, inputStream, ResponseHeaders.emptyResponseHeaders());

        assertThat(actualResponse)
            .isNotNull()
            .isInstanceOf(TokenResponse.class);
    }

    @ParameterizedTest
    @ValueSource(ints = {302, 403, 500})
    void responseHandler_notSuccessfulStatuses(int statusCode) {
        HttpClient.ResponseHandler<TokenResponse> responseHandler = authorisationService.responseHandler(TokenResponse.class);
        ResponseHeaders responseHeaders = ResponseHeaders.emptyResponseHeaders();
        InputStream body = new ByteArrayInputStream("body".getBytes());

        assertThatThrownBy(() -> responseHandler.apply(statusCode, body, responseHeaders))
            .isInstanceOf(AccessTokenException.class);
    }

    private Aspsp getAspsp() {
        String url = "https://localhost:8443/";
        Aspsp aspsp = new Aspsp();
        aspsp.setIdpUrl(url);
        return aspsp;
    }
}
