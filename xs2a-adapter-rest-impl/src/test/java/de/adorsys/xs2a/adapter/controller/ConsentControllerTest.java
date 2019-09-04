package de.adorsys.xs2a.adapter.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.adorsys.xs2a.adapter.TestModelBuilder;
import de.adorsys.xs2a.adapter.config.RestExceptionHandler;
import de.adorsys.xs2a.adapter.mapper.HeadersMapper;
import de.adorsys.xs2a.adapter.model.ConsentStatusTO;
import de.adorsys.xs2a.adapter.model.ConsentsResponse201TO;
import de.adorsys.xs2a.adapter.service.Response;
import de.adorsys.xs2a.adapter.service.RequestHeaders;
import de.adorsys.xs2a.adapter.service.ResponseHeaders;
import de.adorsys.xs2a.adapter.service.AccountInformationService;
import de.adorsys.xs2a.adapter.service.model.ConsentCreationResponse;
import de.adorsys.xs2a.adapter.service.exception.AspspRegistrationNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pro.javatar.commons.reader.JsonReader;

import java.util.Collections;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ConsentControllerTest {
    private static final int HTTP_CODE_200 = 200;

    private MockMvc mockMvc;

    @InjectMocks
    private ConsentController controller;

    @Mock
    private AccountInformationService accountInformationService;
    @Mock
    private HeadersMapper headersMapper;


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                          .setMessageConverters(new MappingJackson2HttpMessageConverter())
                          .setControllerAdvice(new RestExceptionHandler(new HeadersMapper()))
                          .build();
    }

    @Test
    public void createConsent() throws Exception {
        ConsentCreationResponse response = TestModelBuilder.buildConsentCreationResponse();
        String body = new ObjectMapper().writeValueAsString(response);

        when(accountInformationService.createConsent(any(), any()))
                .thenReturn(new Response<>(HTTP_CODE_200, response, ResponseHeaders.fromMap(Collections.emptyMap())));
        when(headersMapper.toHttpHeaders(any()))
                .thenReturn(new HttpHeaders());
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                                                      .post(ConsentController.CONSENTS)
                                                      .header(RequestHeaders.X_GTW_ASPSP_ID, "db")
                                                      .header(RequestHeaders.X_REQUEST_ID, UUID.randomUUID())
                                                      .contentType(APPLICATION_JSON_UTF8_VALUE)
                                                      .content(body))
                                      .andExpect(status().is(HttpStatus.CREATED.value()))
                                      .andReturn();

        ConsentsResponse201TO response201 = JsonReader.getInstance().getObjectFromString(mvcResult.getResponse().getContentAsString()
                , ConsentsResponse201TO.class);

        assertThat(response201.getConsentId()).isEqualTo(TestModelBuilder.CONSTENT_ID);
        assertThat(response201.getMessage()).isEqualTo(TestModelBuilder.MESSAGE);
        assertThat(response201.getConsentStatus()).isEqualTo(ConsentStatusTO.RECEIVED);
        assertThat(response201.getLinks()).hasSize(1);
        assertThat(response201.getChosenScaMethod()).isNotNull();
        assertThat(response201.getScaMethods()).isNotNull();
        assertThat(response201.getChallengeData()).isNotNull();
    }

    @Test
    public void createConsentRequiredFieldIsMissing() throws Exception {
        when(accountInformationService.createConsent(any(), any()))
                .thenThrow(new AspspRegistrationNotFoundException(""));

        mockMvc.perform(MockMvcRequestBuilders
                                .post(ConsentController.CONSENTS)
                                .contentType(APPLICATION_JSON_UTF8_VALUE)
                                .content("{}"))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
                .andReturn();
    }

    @Test
    public void getTransactionWithoutBookingStatusParamRespondsWithBadRequestAndClearErrorMessage()  throws Exception{
        mockMvc.perform(get("/v1/accounts/resource-id/transactions"))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.tppMessages[0].text").value("Required parameter 'bookingStatus' is missing"));
    }
}
