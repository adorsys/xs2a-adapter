package de.adorsys.xs2a.adapter.service.impl;

import de.adorsys.xs2a.adapter.service.DownloadService;
import de.adorsys.xs2a.adapter.service.RequestHeaders;
import de.adorsys.xs2a.adapter.service.Response;
import de.adorsys.xs2a.adapter.service.ResponseHeaders;
import de.adorsys.xs2a.adapter.service.loader.AdapterServiceLoader;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DownloadServiceImplTest {
    private static final String DOWNLOAD_URL = "https://www.example.com";
    private static final RequestHeaders REQUEST_HEADERS = RequestHeaders.fromMap(Collections.emptyMap());
    private static final ResponseHeaders RESPONSE_HEADERS = ResponseHeaders.fromMap(Collections.emptyMap());
    private static final int HTTP_CODE_200 = 200;
    private static final byte[] RESPONSE_BODY = "response body".getBytes();
    private static final Response<byte[]> RESPONSE = new Response<>(HTTP_CODE_200, RESPONSE_BODY, RESPONSE_HEADERS);

    @Mock
    private AdapterServiceLoader adapterServiceLoader;

    @Mock
    private DownloadService downloadService;

    @InjectMocks
    private DownloadServiceImpl service;

    @Test
    void download() {
        when(adapterServiceLoader.getDownloadService(REQUEST_HEADERS))
            .thenReturn(downloadService);

        when(downloadService.download(DOWNLOAD_URL, REQUEST_HEADERS))
            .thenReturn(RESPONSE);

        Response<byte[]> actualResponse = service.download(DOWNLOAD_URL, REQUEST_HEADERS);

        assertThat(actualResponse).isEqualTo(RESPONSE);
    }
}
