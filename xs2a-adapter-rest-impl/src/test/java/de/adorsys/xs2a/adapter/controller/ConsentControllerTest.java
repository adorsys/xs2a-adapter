package de.adorsys.xs2a.adapter.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.adorsys.xs2a.adapter.TestModelBuilder;
import de.adorsys.xs2a.adapter.api.model.*;
import de.adorsys.xs2a.adapter.config.RestExceptionHandler;
import de.adorsys.xs2a.adapter.mapper.HeadersMapper;
import de.adorsys.xs2a.adapter.service.AccountInformationService;
import de.adorsys.xs2a.adapter.service.RequestHeaders;
import de.adorsys.xs2a.adapter.service.Response;
import de.adorsys.xs2a.adapter.service.ResponseHeaders;
import de.adorsys.xs2a.adapter.service.exception.AdapterNotFoundException;
import de.adorsys.xs2a.adapter.service.exception.AspspRegistrationNotFoundException;
import de.adorsys.xs2a.adapter.service.exception.ErrorResponseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pro.javatar.commons.reader.JsonReader;

import java.util.Collections;
import java.util.UUID;

import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ConsentControllerTest {
    private static final int HTTP_CODE_200 = 200;
    private static final String ACCOUNTS = "/v1/accounts";
    private static final String CARD_ACCOUNTS = "/v1/card-accounts";
    private static final FormattingConversionService conversionService = buildFormattingConversionService();

    private MockMvc mockMvc;

    @InjectMocks
    private ConsentController controller;

    @Mock
    private AccountInformationService accountInformationService;

    @Mock
    private HeadersMapper headersMapper;

    @Spy
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                          .setMessageConverters(new MappingJackson2HttpMessageConverter())
                          .setControllerAdvice(new RestExceptionHandler(new HeadersMapper()))
                          .setConversionService(conversionService)
                          .build();
    }

    @Test
    public void createConsent() throws Exception {
        ConsentsResponse201 response = TestModelBuilder.buildConsentCreationResponse();

        when(accountInformationService.createConsent(any(), any(), any()))
                .thenReturn(buildResponse(response));
        when(headersMapper.toHttpHeaders(any()))
                .thenReturn(new HttpHeaders());
        MvcResult mvcResult = mockMvc.perform(post(ConsentController.CONSENTS)
                                                      .header(RequestHeaders.X_GTW_ASPSP_ID, "db")
                                                      .header(RequestHeaders.X_REQUEST_ID, UUID.randomUUID())
                                                      .contentType(APPLICATION_JSON_UTF8_VALUE)
                                                      .content(writeValueAsString(response)))
                                      .andExpect(status().is(HttpStatus.CREATED.value()))
                                      .andReturn();

        ConsentsResponse201 response201 = JsonReader.getInstance()
            .getObjectFromString(mvcResult.getResponse().getContentAsString(), ConsentsResponse201.class);

        assertThat(response201.getConsentId()).isEqualTo(TestModelBuilder.CONSTENT_ID);
        assertThat(response201.getPsuMessage()).isEqualTo(TestModelBuilder.MESSAGE);
        assertThat(response201.getConsentStatus()).isEqualTo(ConsentStatus.RECEIVED);
        assertThat(response201.getLinks()).hasSize(1);
        assertThat(response201.getChosenScaMethod()).isNotNull();
        assertThat(response201.getScaMethods()).isNotNull();
        assertThat(response201.getChallengeData()).isNotNull();
    }

    @Test
    public void createConsentRequiredFieldIsMissing() throws Exception {
        when(accountInformationService.createConsent(any(), any(), any()))
                .thenThrow(new AspspRegistrationNotFoundException(""));

        mockMvc.perform(post(ConsentController.CONSENTS)
                                .contentType(APPLICATION_JSON_UTF8_VALUE)
                                .content("{}"))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
                .andReturn();
    }

    @Test
    public void getTransactionWithoutBookingStatusParamRespondsWithBadRequestAndClearErrorMessage() throws Exception {
        mockMvc.perform(get("/v1/accounts/resource-id/transactions"))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.tppMessages[0].text").value("Required parameter 'bookingStatus' is missing"));
    }

    @Test
    @SuppressWarnings("squid:S2699")
    public void createConsentsRespondsWithBadRequestIfAdapterNotFound() throws Exception {
        String adpaterId = "test-psd2-adapter";

        when(accountInformationService.createConsent(any(), any(), any()))
            .thenThrow(new AdapterNotFoundException(adpaterId));

        mockMvc.perform(post(ConsentController.CONSENTS)
                .header(RequestHeaders.X_GTW_ASPSP_ID, adpaterId)
                .header(RequestHeaders.X_REQUEST_ID, UUID.randomUUID())
                .contentType(APPLICATION_JSON_UTF8_VALUE)
                .content("{}"))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.tppMessages[0].text", containsString(adpaterId)));
    }

    @Test
    void createConsent_preOauth() throws Exception {
        when(accountInformationService.createConsent(any(), any(), any()))
            .thenThrow(new ErrorResponseException(403, ResponseHeaders.emptyResponseHeaders(), new ErrorResponse(), "TOKEN_INVALID"));

        mockMvc.perform(post(ConsentController.CONSENTS)
            .header(RequestHeaders.X_GTW_ASPSP_ID, "adapterId")
            .header(RequestHeaders.X_REQUEST_ID, UUID.randomUUID())
            .contentType(APPLICATION_JSON_UTF8_VALUE)
            .content("{}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$._links.preOauth.href", containsString(Oauth2Controller.AUTHORIZATION_REQUEST_URI)));
    }

    @Test
    void createConsent_oauthConsent() throws Exception {
        ConsentsResponse201 response = TestModelBuilder.buildConsentCreationResponse();
        response.setConsentId(null);
        response.setLinks(null);

        when(accountInformationService.createConsent(any(), any(), any()))
            .thenReturn(buildResponse(response));

        mockMvc.perform(post(ConsentController.CONSENTS)
            .header(RequestHeaders.X_GTW_ASPSP_ID, "adapterId")
            .header(RequestHeaders.X_REQUEST_ID, UUID.randomUUID())
            .contentType(APPLICATION_JSON_UTF8_VALUE)
            .content("{}"))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$._links.oauthConsent.href", containsString(Oauth2Controller.AUTHORIZATION_REQUEST_URI)));
    }

    @Test
    void getConsentInformation() throws Exception {
        when(accountInformationService.getConsentInformation(anyString(), any(), any()))
            .thenReturn(buildResponse(TestModelBuilder.buildConsentInformationResponse()));

        mockMvc.perform(get(ConsentController.CONSENTS + "/asd"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.consentStatus", containsString(ConsentStatus.RECEIVED.toString())))
            .andExpect(jsonPath("$.frequencyPerDay", equalTo(4)))
            .andExpect(jsonPath("$.recurringIndicator", is(true)));

        verify(accountInformationService, times(1))
            .getConsentInformation(anyString(), any(), any());
    }

    @Test
    void deleteConsent() throws Exception {
        when(accountInformationService.deleteConsent(anyString(), any(), any()))
            .thenReturn(buildResponse(null));

        mockMvc.perform(delete(ConsentController.CONSENTS + "/1"))
            .andExpect(status().isNoContent());
    }

    @Test
    void getConsentStatus() throws Exception {
        when(accountInformationService.getConsentStatus(anyString(), any(), any()))
            .thenReturn(buildResponse(TestModelBuilder.buildConsentStatusResponse()));

        mockMvc.perform(get(ConsentController.CONSENTS + "/asd/status"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.consentStatus", containsString(ConsentStatus.RECEIVED.toString())))
            .andExpect(jsonPath("$.psuMessage", containsString(TestModelBuilder.MESSAGE)));

        verify(accountInformationService, times(1))
            .getConsentStatus(anyString(), any(), any());
    }

    @Test
    void startConsentAuthorisation_updatePsuAuthentication() throws Exception {
        UpdatePsuAuthentication requestBody = TestModelBuilder.buildUpdatePsuAuthentication();

        when(accountInformationService.startConsentAuthorisation(anyString(), any(), any(), any(UpdatePsuAuthentication.class)))
            .thenReturn(buildResponse(TestModelBuilder.buildStartScaprocessResponse()));

        mockMvc.perform(post(ConsentController.CONSENTS + "/asd/authorisations")
            .contentType(APPLICATION_JSON_UTF8_VALUE)
            .content(writeValueAsString(requestBody)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.scaStatus", containsString(ScaStatus.STARTED.toString())))
            .andExpect(jsonPath("$.authorisationId", containsString(TestModelBuilder.AUTHORISATION_ID)))
            .andExpect(jsonPath("$.psuMessage", containsString(TestModelBuilder.MESSAGE)));

        verify(accountInformationService, times(1))
            .startConsentAuthorisation(anyString(), any(), any(), any());
    }

    @Test
    void startConsentAuthorisation_startAuthorisation() throws Exception {
        when(accountInformationService.startConsentAuthorisation(anyString(), any(), any()))
            .thenReturn(buildResponse(TestModelBuilder.buildStartScaprocessResponse()));

        mockMvc.perform(post(ConsentController.CONSENTS + "/asd/authorisations")
            .contentType(APPLICATION_JSON_UTF8_VALUE)
            .content("{}"))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.scaStatus", containsString(ScaStatus.STARTED.toString())))
            .andExpect(jsonPath("$.authorisationId", containsString(TestModelBuilder.AUTHORISATION_ID)))
            .andExpect(jsonPath("$.psuMessage", containsString(TestModelBuilder.MESSAGE)));

        verify(accountInformationService, times(1))
            .startConsentAuthorisation(anyString(), any(), any());
    }

    @Test
    void updateConsentsPsuData_updatePsuAuthentication() throws Exception {
        UpdatePsuAuthentication requestBody = TestModelBuilder.buildUpdatePsuAuthentication();

        when(accountInformationService.updateConsentsPsuData(anyString(), anyString(), any(), any(), any(UpdatePsuAuthentication.class)))
            .thenReturn(buildResponse(TestModelBuilder.buildUpdatePsuAuthenticationResponse()));

        mockMvc.perform(put(ConsentController.CONSENTS + "/asd/authorisations/qwq")
            .contentType(APPLICATION_JSON_UTF8_VALUE)
            .content(writeValueAsString(requestBody)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.scaStatus", containsString(ScaStatus.STARTED.toString())))
            .andExpect(jsonPath("$.authorisationId", containsString(TestModelBuilder.AUTHORISATION_ID)))
            .andExpect(jsonPath("$.psuMessage", containsString(TestModelBuilder.MESSAGE)));

        verify(accountInformationService, times(1))
            .updateConsentsPsuData(anyString(), anyString(), any(), any(), any(UpdatePsuAuthentication.class));
    }

    @Test
    void updateConsentsPsuData_selectPsuAuthenticationMethod() throws Exception {
        SelectPsuAuthenticationMethod requestBody = TestModelBuilder.buildSelectPsuAuthenticationMethod();

        when(accountInformationService.updateConsentsPsuData(anyString(), anyString(), any(), any(), any(SelectPsuAuthenticationMethod.class)))
            .thenReturn(buildResponse(TestModelBuilder.buildSelectPsuAuthenticationMethodResponse()));

        mockMvc.perform(put(ConsentController.CONSENTS + "/asd/authorisations/qwq")
            .contentType(APPLICATION_JSON_UTF8_VALUE)
            .content(writeValueAsString(requestBody)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.scaStatus", containsString(ScaStatus.SCAMETHODSELECTED.toString())))
            .andExpect(jsonPath("$.psuMessage", containsString(TestModelBuilder.MESSAGE)));

        verify(accountInformationService, times(1))
            .updateConsentsPsuData(anyString(), anyString(), any(), any(), any(SelectPsuAuthenticationMethod.class));
    }

    @Test
    void updateConsentsPsuData_transactionAuthorisation() throws Exception {
        TransactionAuthorisation requestBody = TestModelBuilder.buildTransactionAuthorisation();

        when(accountInformationService.updateConsentsPsuData(anyString(), anyString(), any(), any(), any(TransactionAuthorisation.class)))
            .thenReturn(buildResponse(TestModelBuilder.buildScaStatusResponse()));

        mockMvc.perform(put(ConsentController.CONSENTS + "/asd/authorisations/qwq")
            .contentType(APPLICATION_JSON_UTF8_VALUE)
            .content(writeValueAsString(requestBody)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.scaStatus", containsString(ScaStatus.FINALISED.toString())));

        verify(accountInformationService, times(1))
            .updateConsentsPsuData(anyString(), anyString(), any(), any(), any(TransactionAuthorisation.class));
    }

    @Test
    void getAccountList() throws Exception {
        when(accountInformationService.getAccountList(any(), any()))
            .thenReturn(buildResponse(TestModelBuilder.buildAccountList()));

        mockMvc.perform(get(ACCOUNTS))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.accounts", hasSize(2)));

        verify(accountInformationService, times(1))
            .getAccountList(any(), any());
    }

    @Test
    void getTransactionList_json() throws Exception {
        when(accountInformationService.getTransactionList(anyString(), any(), any()))
            .thenReturn(buildResponse(TestModelBuilder.buildTransactionsResponse()));

        mockMvc.perform(get(ACCOUNTS + "/asd/transactions")
            .queryParam("bookingStatus", "booked")
            .accept(APPLICATION_JSON_UTF8_VALUE))
            .andExpect(status().isOk())
            .andExpect(jsonPath(format("$._links.%s.href", TestModelBuilder.CONSTENT_ID),
                containsString(TestModelBuilder.MESSAGE)));

        verify(accountInformationService, times(1))
            .getTransactionList(anyString(), any(), any());
    }

    @Test
    void getTransactionList_string() throws Exception {
        String response = "<test></test>";

        when(accountInformationService.getTransactionListAsString(anyString(), any(), any()))
            .thenReturn(buildResponse(response));

        MvcResult mvcResult = mockMvc.perform(get(ACCOUNTS + "/asd/transactions")
            .queryParam("bookingStatus", "booked"))
            .andExpect(status().isOk())
            .andReturn();

        String actualResponse = JsonReader.getInstance()
            .getObjectFromString(mvcResult.getResponse().getContentAsString(), String.class);

        assertThat(actualResponse).contains(response);
    }

    @Test
    void getTransactionDetails() throws Exception {
        when(accountInformationService.getTransactionDetails(anyString(), anyString(), any(), any()))
            .thenReturn(buildResponse(new OK200TransactionDetails()));

        mockMvc.perform(get(ACCOUNTS + "/asds/transactions/asd"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.transactionsDetails", nullValue()));

        verify(accountInformationService, times(1))
            .getTransactionDetails(anyString(), anyString(), any(), any());
    }

    @Test
    void getCardAccount() throws Exception {
        when(accountInformationService.getCardAccountList(any(), any()))
            .thenReturn(buildResponse(new CardAccountList()));

        mockMvc.perform(get(CARD_ACCOUNTS))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.cardAccounts", nullValue()));

        verify(accountInformationService, times(1)).getCardAccountList(any(), any());
    }

    @Test
    void readCardAccount() throws Exception {
        when(accountInformationService.getCardAccountDetails(anyString(), any(), any()))
            .thenReturn(buildResponse(new OK200CardAccountDetails()));

        mockMvc.perform(get(CARD_ACCOUNTS + "/asd"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.cardAccount", nullValue()));

        verify(accountInformationService, times(1)).getCardAccountDetails(anyString(), any(), any());
    }

    @Test
    void getCardAccountBalances() throws Exception {
        when(accountInformationService.getCardAccountBalances(anyString(), any(), any()))
            .thenReturn(buildResponse(TestModelBuilder.buildReadCardAccountBalanceResponse()));

        mockMvc.perform(get(CARD_ACCOUNTS + "/asd/balances"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.balances", hasSize(2)));

        verify(accountInformationService, times(1))
            .getCardAccountBalances(anyString(), any(), any());
    }

    @Test
    void getCardAccountTransactionList() throws Exception {
        when(accountInformationService.getCardAccountTransactionList(anyString(), any(), any()))
            .thenReturn(buildResponse(TestModelBuilder.buildCardAccountsTransactionsResponse()));

        mockMvc.perform(get(CARD_ACCOUNTS + "/asd/transactions")
        .param("bookingStatus", BookingStatus.BOOKED.toString()))
            .andExpect(status().isOk())
            .andExpect(jsonPath(format("$._links.%s.href", TestModelBuilder.CONSTENT_ID),
                containsString(TestModelBuilder.MESSAGE)));

        verify(accountInformationService, times(1))
            .getCardAccountTransactionList(anyString(), any(), any());
    }

    @Test
    void getConsentScaStatus() throws Exception {
        when(accountInformationService.getConsentScaStatus(anyString(), anyString(), any(), any()))
            .thenReturn(buildResponse(TestModelBuilder.buildScaStatusResponse()));

        mockMvc.perform(get(ConsentController.CONSENTS + "/asd/authorisations/asd"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.scaStatus", containsString(ScaStatus.FINALISED.toString())));

        verify(accountInformationService, times(1))
            .getConsentScaStatus(anyString(), anyString(), any(), any());
    }

    @Test
    void getBalances() throws Exception {
        when(accountInformationService.getBalances(anyString(), any(), any()))
            .thenReturn(buildResponse(TestModelBuilder.buildReadAccountBalanceResponse()));

        mockMvc.perform(get(ACCOUNTS + "/asd/balances"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.balances", hasSize(2)));

        verify(accountInformationService, times(1))
            .getBalances(anyString(), any(), any());
    }

    private <T> Response<T> buildResponse(T response) {
        return new Response<>(HTTP_CODE_200, response, ResponseHeaders.fromMap(Collections.emptyMap()));
    }

    private <T> String writeValueAsString(T value) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(value);
    }

    private static FormattingConversionService buildFormattingConversionService() {
        FormattingConversionService service = new FormattingConversionService();
        service.addConverter(new Converter<String, BookingStatus>() {

            @Override
            public BookingStatus convert(String source) {
                return BookingStatus.fromValue(source);
            }
        });

        return service;
    }
}
