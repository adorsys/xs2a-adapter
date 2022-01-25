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

package de.adorsys.xs2a.adapter.serviceloader;

import de.adorsys.xs2a.adapter.api.DownloadService;
import de.adorsys.xs2a.adapter.api.RequestHeaders;
import de.adorsys.xs2a.adapter.api.Response;
import de.adorsys.xs2a.adapter.api.ResponseHeaders;
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
