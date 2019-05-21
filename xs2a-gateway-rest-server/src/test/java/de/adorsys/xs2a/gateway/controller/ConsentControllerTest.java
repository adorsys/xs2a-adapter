package de.adorsys.xs2a.gateway.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.adorsys.xs2a.gateway.TestModelBuilder;
import de.adorsys.xs2a.gateway.config.RestExceptionHandler;
import de.adorsys.xs2a.gateway.mapper.HeadersMapper;
import de.adorsys.xs2a.gateway.model.ais.ConsentStatusTO;
import de.adorsys.xs2a.gateway.model.ais.ConsentsResponse201;
import de.adorsys.xs2a.gateway.service.GeneralResponse;
import de.adorsys.xs2a.gateway.service.RequestHeaders;
import de.adorsys.xs2a.gateway.service.ResponseHeaders;
import de.adorsys.xs2a.gateway.service.ais.ConsentCreationResponse;
import de.adorsys.xs2a.gateway.service.ais.AccountInformationService;
import de.adorsys.xs2a.gateway.service.exception.BankCodeNotProvidedException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
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
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
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
                .thenReturn(new GeneralResponse<>(HTTP_CODE_200, response, ResponseHeaders.fromMap(Collections.emptyMap())));
        when(headersMapper.toHttpHeaders(any()))
                .thenReturn(new HttpHeaders());
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                                                      .post(ConsentController.CONSENTS)
                                                      .header(RequestHeaders.X_GTW_BANK_CODE, "db")
                                                      .header(RequestHeaders.X_REQUEST_ID, UUID.randomUUID())
                                                      .contentType(APPLICATION_JSON_UTF8_VALUE)
                                                      .content(body))
                                      .andExpect(status().is(HttpStatus.CREATED.value()))
                                      .andReturn();

        ConsentsResponse201 response201 = JsonReader.getInstance().getObjectFromString(mvcResult.getResponse().getContentAsString()
                , ConsentsResponse201.class);

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
                .thenThrow(new BankCodeNotProvidedException());

        mockMvc.perform(MockMvcRequestBuilders
                                .post(ConsentController.CONSENTS)
                                .contentType(APPLICATION_JSON_UTF8_VALUE)
                                .content("{}"))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
                .andReturn();
    }
}