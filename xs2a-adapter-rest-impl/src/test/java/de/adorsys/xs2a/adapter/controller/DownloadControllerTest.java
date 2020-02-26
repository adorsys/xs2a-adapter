package de.adorsys.xs2a.adapter.controller;

import de.adorsys.xs2a.adapter.config.RestExceptionHandler;
import de.adorsys.xs2a.adapter.mapper.HeadersMapper;
import de.adorsys.xs2a.adapter.service.DownloadService;
import de.adorsys.xs2a.adapter.service.RequestHeaders;
import de.adorsys.xs2a.adapter.service.Response;
import de.adorsys.xs2a.adapter.service.ResponseHeaders;
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

public class DownloadControllerTest {
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
    public void download() throws Exception {
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
