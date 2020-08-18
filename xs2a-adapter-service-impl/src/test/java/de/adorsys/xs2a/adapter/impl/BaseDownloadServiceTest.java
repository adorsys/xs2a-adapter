package de.adorsys.xs2a.adapter.impl;

import de.adorsys.xs2a.adapter.http.HttpClient;
import de.adorsys.xs2a.adapter.http.Request;
import de.adorsys.xs2a.adapter.impl.http.RequestBuilderImpl;
import de.adorsys.xs2a.adapter.service.RequestHeaders;
import de.adorsys.xs2a.adapter.service.Response;
import de.adorsys.xs2a.adapter.service.ResponseHeaders;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class BaseDownloadServiceTest {

    public static final String BASE_URL = "https://base.url";
    public static final String DOWNLOAD_URL = "https://base.url/download";
    public static final String OCTET_STREAM = "application/octet-stream";

    private BaseDownloadService service;
    private RequestHeaders headers = RequestHeaders.fromMap(new HashMap<>());

    @Mock
    private HttpClient httpClient;

    @Spy
    private Request.Builder requestBuilder = new RequestBuilderImpl(httpClient, null, null);

    @Captor
    private ArgumentCaptor<String> uriCaptor;

    @Captor
    private ArgumentCaptor<Map<String, String>> headersCaptor;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        service = new BaseDownloadService(BASE_URL, httpClient);
    }

    @Test
    void download_bestCase() {
        when(httpClient.get(any())).thenReturn(requestBuilder);
        doReturn(dummyResponse()).when(requestBuilder).send(any(), any());

        Response<byte[]> response = service.download(DOWNLOAD_URL, headers);

        verify(httpClient, times(1)).get(uriCaptor.capture());
        verify(requestBuilder, times(1)).headers(headersCaptor.capture());

        assertThat(uriCaptor.getValue()).isEqualTo(DOWNLOAD_URL);
        assertThat(headersCaptor.getValue()).isEqualTo(headers.toMap());
        assertThat(response.getHeaders().getHeadersMap()).containsEntry(ResponseHeaders.CONTENT_TYPE, OCTET_STREAM);
    }

    @Test
    void download_partialDownloadLink() {
        when(httpClient.get(any())).thenReturn(requestBuilder);
        doReturn(dummyResponse()).when(requestBuilder).send(any(), any());

        Response<byte[]> response = service.download("/download", headers);

        verify(httpClient, times(1)).get(uriCaptor.capture());
        verify(requestBuilder, times(1)).headers(headersCaptor.capture());

        assertThat(uriCaptor.getValue()).isEqualTo(DOWNLOAD_URL);
        assertThat(headersCaptor.getValue()).isEqualTo(headers.toMap());
        assertThat(response.getHeaders().getHeadersMap()).containsEntry(ResponseHeaders.CONTENT_TYPE, OCTET_STREAM);
    }

    @Test
    void download_noProtocolLink() {
        when(httpClient.get(any())).thenReturn(requestBuilder);
        doReturn(dummyResponse()).when(requestBuilder).send(any(), any());

        Response<byte[]> response = service.download("base.url/download", headers);

        verify(httpClient, times(1)).get(uriCaptor.capture());
        verify(requestBuilder, times(1)).headers(headersCaptor.capture());

        assertThat(uriCaptor.getValue()).isEqualTo(DOWNLOAD_URL);
        assertThat(headersCaptor.getValue()).isEqualTo(headers.toMap());
        assertThat(response.getHeaders().getHeadersMap()).containsEntry(ResponseHeaders.CONTENT_TYPE, OCTET_STREAM);
    }

    private Response<byte[]> dummyResponse() {
        return new Response<>(0, new byte[]{}, ResponseHeaders.fromMap(new HashMap<>()));
    }
}
