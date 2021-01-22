package de.adorsys.xs2a.adapter.impl.oauth2.api;

import de.adorsys.xs2a.adapter.api.Response;
import de.adorsys.xs2a.adapter.api.http.HttpClient;
import de.adorsys.xs2a.adapter.api.http.Request;
import de.adorsys.xs2a.adapter.impl.http.RequestBuilderImpl;
import de.adorsys.xs2a.adapter.impl.oauth2.api.model.AuthorisationServerMetaData;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class BaseOauth2ApiTest<T extends AuthorisationServerMetaData> {

    public static final String SCA_OAUTH_URL = "https://sca.oauth.url";
    public static final String AUTHORISATION_ENDPOINT = "https://authorisation.endpoint";
    public static final String TOKEN_ENDPOINT = "https://token.endpoint";

    private HttpClient httpClient = mock(HttpClient.class);
    private Request.Builder requestBuilder = spy(new RequestBuilderImpl(httpClient, "GET", null));
    private ArgumentCaptor<String> urlCaptor = ArgumentCaptor.forClass(String.class);

    private BaseOauth2Api<AuthorisationServerMetaData> api = new BaseOauth2Api<>(httpClient, AuthorisationServerMetaData.class);



    @Test
    void getAuthorisationUri() {
        when(httpClient.get(any())).thenReturn(requestBuilder);
        doReturn(dummyResponse()).when(requestBuilder).send(any());

        String actual = api.getAuthorisationUri(SCA_OAUTH_URL);

        verify(httpClient, times(1)).get(urlCaptor.capture());
        verify(requestBuilder, times(1)).send(any());

        assertThat(actual).isEqualTo(AUTHORISATION_ENDPOINT);
        assertThat(urlCaptor.getValue()).isEqualTo(SCA_OAUTH_URL);
    }

    @Test
    void getTokenUri() {
        when(httpClient.get(any())).thenReturn(requestBuilder);
        doReturn(dummyResponse()).when(requestBuilder).send(any());

        String actual = api.getTokenUri(SCA_OAUTH_URL);

        verify(httpClient, times(1)).get(urlCaptor.capture());
        verify(requestBuilder, times(1)).send(any());

        assertThat(actual).isEqualTo(TOKEN_ENDPOINT);
        assertThat(urlCaptor.getValue()).isEqualTo(SCA_OAUTH_URL);
    }

    private Response<AuthorisationServerMetaData> dummyResponse() {
        AuthorisationServerMetaData metaData = new AuthorisationServerMetaData();
        metaData.setAuthorisationEndpoint(AUTHORISATION_ENDPOINT);
        metaData.setTokenEndpoint(TOKEN_ENDPOINT);

        return new Response<>(200, metaData, null);
    }
}
