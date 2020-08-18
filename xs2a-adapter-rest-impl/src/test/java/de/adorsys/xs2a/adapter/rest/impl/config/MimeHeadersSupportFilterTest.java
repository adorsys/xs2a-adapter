package de.adorsys.xs2a.adapter.rest.impl.config;

import de.adorsys.xs2a.adapter.mapper.HeadersMapper;
import de.adorsys.xs2a.adapter.rest.impl.controller.ConsentController;
import de.adorsys.xs2a.adapter.service.AccountInformationService;
import de.adorsys.xs2a.adapter.service.RequestHeaders;
import de.adorsys.xs2a.adapter.service.exception.AspspRegistrationNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MimeHeadersSupportFilterTest {

    private MockMvc mockMvc;

    @InjectMocks
    private ConsentController controller;

    @Mock
    private AccountInformationService accountInformationService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                      .setMessageConverters(new MappingJackson2HttpMessageConverter())
                      .setControllerAdvice(new RestExceptionHandler(new HeadersMapper()))
                      .addFilters(new MimeHeadersSupportFilter())
                      .build();
    }

    @Test
    void doFilter() throws Exception {
        String encodedUmlauts = "=?UTF-8?B?w6Qgw7Ygw7w=?="; // ä ö ü
        ArgumentCaptor<RequestHeaders> headersCaptor = ArgumentCaptor.forClass(RequestHeaders.class);
        when(accountInformationService.createConsent(headersCaptor.capture(), any(), any()))
            .thenThrow(new AspspRegistrationNotFoundException(""));

        mockMvc.perform(post(ConsentController.CONSENTS)
                            .header(RequestHeaders.PSU_ID, encodedUmlauts)
                            .contentType(APPLICATION_JSON_VALUE)
                            .content("{}"))
            .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
            .andReturn();

        RequestHeaders headers = headersCaptor.getValue();

        assertThat(headers.get(RequestHeaders.PSU_ID)).contains("ä ö ü");
    }
}
