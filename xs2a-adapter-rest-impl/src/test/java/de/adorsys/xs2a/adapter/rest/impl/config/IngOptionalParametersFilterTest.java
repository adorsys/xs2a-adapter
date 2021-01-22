package de.adorsys.xs2a.adapter.rest.impl.config;

import de.adorsys.xs2a.adapter.api.AccountInformationService;
import de.adorsys.xs2a.adapter.api.RequestParams;
import de.adorsys.xs2a.adapter.api.ResponseHeaders;
import de.adorsys.xs2a.adapter.api.exception.OAuthException;
import de.adorsys.xs2a.adapter.api.model.ErrorResponse;
import de.adorsys.xs2a.adapter.mapper.HeadersMapper;
import de.adorsys.xs2a.adapter.rest.impl.controller.ConsentController;
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

class IngOptionalParametersFilterTest {

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
            .addFilters(new IngOptionalParametersFilter())
            .build();
    }

    @Test
    void doFilter() throws Exception {
        ArgumentCaptor<RequestParams> paramsCaptor = ArgumentCaptor.forClass(RequestParams.class);
        when(accountInformationService.createConsent(any(), paramsCaptor.capture(), any()))
            .thenThrow(new OAuthException(ResponseHeaders.emptyResponseHeaders(), new ErrorResponse(), ""));

        mockMvc.perform(post(ConsentController.CONSENTS)
            .contentType(APPLICATION_JSON_VALUE)
            .content("{}"))
            .andExpect(status().is(HttpStatus.FORBIDDEN.value()));

        RequestParams params = paramsCaptor.getValue();

        assertThat(params.toMap())
            .containsKey("balanceTypes")
            .containsKey("limit");
    }
}
