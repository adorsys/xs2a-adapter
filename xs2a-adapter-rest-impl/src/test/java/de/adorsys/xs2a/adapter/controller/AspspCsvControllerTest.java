package de.adorsys.xs2a.adapter.controller;

import de.adorsys.xs2a.adapter.config.RestExceptionHandler;
import de.adorsys.xs2a.adapter.mapper.HeadersMapper;
import de.adorsys.xs2a.adapter.service.AspspCsvService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AspspCsvControllerTest {
    private static final String BASE_URI = "/v1/aspsps/csv";

    private MockMvc mockMvc;

    @InjectMocks
    private AspspCsvController controller;

    @Mock
    private AspspCsvService aspspCsvService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                      .setMessageConverters(new MappingJackson2HttpMessageConverter())
                      .setControllerAdvice(new RestExceptionHandler(new HeadersMapper()))
                      .build();
    }

    @Test
    public void export() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).setMessageConverters().build();

        byte[] bytes = ("81cecc67-6d1b-4169-b67c-2de52b99a0cc,\"BNP Paribas Germany, Consorsbank\"," +
                            "CSDBDE71XXX,https://xs2a-sndbx.consorsbank.de,consors-bank-adapter,76030080").getBytes();

        when(aspspCsvService.exportCsv()).thenReturn(bytes);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                                                  .get(BASE_URI + "/export"))
                                  .andExpect(status().is(HttpStatus.OK.value()))
                                  .andReturn();

        byte[] results = mvcResult.getResponse().getContentAsByteArray();

        assertThat(Arrays.equals(results, bytes)).isTrue();
    }

    @Test
    public void importCsv() throws Exception {
        doNothing().when(aspspCsvService).importCsv(any());

        mockMvc.perform(multipart(BASE_URI + "/import")
                            .file("file", "content".getBytes()))
            .andExpect(status().is(HttpStatus.OK.value()));

        verify(aspspCsvService, times(1)).importCsv(any());
    }

    @Test
    public void persist() throws Exception {
        doNothing().when(aspspCsvService).saveCsv();

        mockMvc.perform(MockMvcRequestBuilders.post(BASE_URI + "/persist"))
            .andExpect(status().is(HttpStatus.NO_CONTENT.value()));

        verify(aspspCsvService, times(1)).saveCsv();
    }
}
