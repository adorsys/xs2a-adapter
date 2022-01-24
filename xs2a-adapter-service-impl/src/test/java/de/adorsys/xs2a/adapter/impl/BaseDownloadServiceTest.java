/*
 * Copyright 2018-2022 adorsys GmbH & Co KG
 *
 * This program is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or (at
 * your option) any later version. This program is distributed in the hope that
 * it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see https://www.gnu.org/licenses/.
 *
 * This project is also available under a separate commercial license. You can
 * contact us at psd2@adorsys.com.
 */

package de.adorsys.xs2a.adapter.impl;

import de.adorsys.xs2a.adapter.api.RequestHeaders;
import de.adorsys.xs2a.adapter.api.Response;
import de.adorsys.xs2a.adapter.api.ResponseHeaders;
import de.adorsys.xs2a.adapter.api.http.HttpClient;
import de.adorsys.xs2a.adapter.api.http.Interceptor;
import de.adorsys.xs2a.adapter.api.http.Request;
import de.adorsys.xs2a.adapter.impl.http.RequestBuilderImpl;
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
    private final RequestHeaders headers = RequestHeaders.fromMap(new HashMap<>());

    @Mock
    private HttpClient httpClient;

    @Mock
    private Interceptor interceptor;

    @Spy
    private final Request.Builder requestBuilder = new RequestBuilderImpl(httpClient, null, null);

    @Captor
    private ArgumentCaptor<String> uriCaptor;

    @Captor
    private ArgumentCaptor<Map<String, String>> headersCaptor;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        service = new BaseDownloadService(BASE_URL, httpClient, interceptor, null);
    }

    @Test
    void download_bestCase() {
        when(httpClient.get(any())).thenReturn(requestBuilder);
        doReturn(dummyResponse()).when(requestBuilder).send(any(), anyList());

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
        doReturn(dummyResponse()).when(requestBuilder).send(any(), anyList());

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
        doReturn(dummyResponse()).when(requestBuilder).send(any(), anyList());

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
