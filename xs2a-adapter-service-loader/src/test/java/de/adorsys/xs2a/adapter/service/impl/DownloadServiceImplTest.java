package de.adorsys.xs2a.adapter.service.impl;

import de.adorsys.xs2a.adapter.service.DownloadService;
import de.adorsys.xs2a.adapter.service.RequestHeaders;
import de.adorsys.xs2a.adapter.service.Response;
import de.adorsys.xs2a.adapter.service.ResponseHeaders;
import de.adorsys.xs2a.adapter.service.loader.AdapterServiceLoader;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DownloadServiceImplTest {
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
    public void download() {
        when(adapterServiceLoader.getDownloadService(REQUEST_HEADERS))
            .thenReturn(downloadService);

        when(downloadService.download(DOWNLOAD_URL, REQUEST_HEADERS))
            .thenReturn(RESPONSE);

        Response<byte[]> actualResponse = service.download(DOWNLOAD_URL, REQUEST_HEADERS);

        assertThat(actualResponse).isEqualTo(RESPONSE);
    }
}
