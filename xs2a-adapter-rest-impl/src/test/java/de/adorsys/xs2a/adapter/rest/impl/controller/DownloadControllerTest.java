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

package de.adorsys.xs2a.adapter.rest.impl.controller;

import de.adorsys.xs2a.adapter.api.DownloadService;
import de.adorsys.xs2a.adapter.api.RequestHeaders;
import de.adorsys.xs2a.adapter.api.Response;
import de.adorsys.xs2a.adapter.api.ResponseHeaders;
import de.adorsys.xs2a.adapter.mapper.HeadersMapper;
import de.adorsys.xs2a.adapter.rest.impl.config.RestExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class DownloadControllerTest {
    private static final String DOWNLOAD_URL = "https://www.example.com";
    private static final int HTTP_CODE_200 = 200;
    private static final byte[] RESPONSE_BODY = "response body".getBytes();
    private static final ResponseHeaders RESPONSE_HEADERS = ResponseHeaders.emptyResponseHeaders();
    private static final Response<byte[]> RESPONSE = new Response<>(HTTP_CODE_200, RESPONSE_BODY, RESPONSE_HEADERS);

    private MockMvc mockMvc;

    @Mock
    private DownloadService downloadService;
    @Mock
    private HeadersMapper headersMapper;

    @InjectMocks
    private DownloadController controller;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(controller)
            .setMessageConverters(new ByteArrayHttpMessageConverter())
            .setControllerAdvice(new RestExceptionHandler(new HeadersMapper()))
            .build();
    }

    @Test
    void download() throws Exception {
        when(downloadService.download(eq(DOWNLOAD_URL), any()))
            .thenReturn(RESPONSE);
        when(headersMapper.toHttpHeaders(any()))
            .thenReturn(new HttpHeaders());

        MvcResult mvcResult = mockMvc.perform(get("/v1/download")
            .param("url", DOWNLOAD_URL)
            .header(RequestHeaders.X_GTW_ASPSP_ID, "db")
            .header(RequestHeaders.X_REQUEST_ID, UUID.randomUUID()))
            .andExpect(status().is(HttpStatus.OK.value()))
            .andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();

        assertThat(response.getStatus()).isEqualTo(HTTP_CODE_200);
        assertThat(response.getContentAsByteArray()).isEqualTo(RESPONSE_BODY);
    }
}
