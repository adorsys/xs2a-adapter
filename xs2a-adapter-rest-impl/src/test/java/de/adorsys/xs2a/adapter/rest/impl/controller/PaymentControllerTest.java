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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.adorsys.xs2a.adapter.api.PaymentInitiationService;
import de.adorsys.xs2a.adapter.api.Response;
import de.adorsys.xs2a.adapter.api.ResponseHeaders;
import de.adorsys.xs2a.adapter.api.model.*;
import de.adorsys.xs2a.adapter.mapper.HeadersMapper;
import de.adorsys.xs2a.adapter.rest.impl.TestModelBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static java.lang.String.format;
import static org.hamcrest.CoreMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PaymentControllerTest {

    protected static final PaymentService PAYMENT_SERVICE = PaymentService.PAYMENTS;
    protected static final PaymentProduct PAYMENT_PRODUCT = PaymentProduct.SEPA_CREDIT_TRANSFERS;
    private static final String PAYMENT_URL = "/v1/" + PAYMENT_SERVICE + "/" + PAYMENT_PRODUCT;
    private static final FormattingConversionService conversionService = buildFormattingConversionService();

    @InjectMocks
    private PaymentController controller;

    @Mock
    private PaymentInitiationService paymentInitiationService;

    @Mock
    private HeadersMapper headersMapper;

    @Spy
    private ObjectMapper objectMapper;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(controller)
            .setMessageConverters(new MappingJackson2HttpMessageConverter())
            .setConversionService(conversionService)
            .build();
    }

    @Test
    void initiatePayment_multipart() throws Exception {
        PeriodicPaymentInitiationMultipartBody body = new PeriodicPaymentInitiationMultipartBody();

        when(paymentInitiationService.initiatePayment(eq(PAYMENT_SERVICE), eq(PAYMENT_PRODUCT), any(), any(), any()))
            .thenReturn(buildResponse(TestModelBuilder.buildPaymentInitationRequestResponse()));

        mockMvc.perform(post(PAYMENT_URL)
            .content(writeValueAsString(body))
            .contentType(MediaType.MULTIPART_FORM_DATA))
            .andExpect(status().isCreated())
            .andExpect(jsonPath(format("$._links.%s.href", TestModelBuilder.PAYMENT_ID),
                containsString(TestModelBuilder.MESSAGE)))
            .andExpect(jsonPath("$.paymentId", containsString(TestModelBuilder.PAYMENT_ID)))
            .andExpect(jsonPath("$.psuMessage", containsString(TestModelBuilder.MESSAGE)))
            .andExpect(jsonPath("$.transactionFeeIndicator", is(true)));

        verify(paymentInitiationService, times(1))
            .initiatePayment(eq(PAYMENT_SERVICE), eq(PAYMENT_PRODUCT), any(), any(), any());
    }

    @Test
    void initiatePayment_json() throws Exception {

        when(paymentInitiationService.initiatePayment(eq(PAYMENT_SERVICE), eq(PAYMENT_PRODUCT), any(), any(), any()))
            .thenReturn(buildResponse(TestModelBuilder.buildPaymentInitationRequestResponse()));

        mockMvc.perform(post(PAYMENT_URL)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content("{}"))
            .andExpect(status().isCreated())
            .andExpect(jsonPath(format("$._links.%s.href", TestModelBuilder.PAYMENT_ID),
                containsString(TestModelBuilder.MESSAGE)))
            .andExpect(jsonPath("$.paymentId", containsString(TestModelBuilder.PAYMENT_ID)))
            .andExpect(jsonPath("$.psuMessage", containsString(TestModelBuilder.MESSAGE)))
            .andExpect(jsonPath("$.transactionFeeIndicator", is(true)));

        verify(paymentInitiationService, times(1))
            .initiatePayment(eq(PAYMENT_SERVICE), eq(PAYMENT_PRODUCT), any(), any(), any());
    }

    @Test
    void getPaymentInformation() throws Exception {
        String response = "response-message";

        when(paymentInitiationService.getPaymentInformationAsString(eq(PAYMENT_SERVICE),
            eq(PAYMENT_PRODUCT),
            anyString(),
            any(),
            any()))
            .thenReturn(buildResponse(response));

        mockMvc.perform(get(PAYMENT_URL + "/foo"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", containsString(response)));

        verify(paymentInitiationService, times(1))
            .getPaymentInformationAsString(eq(PAYMENT_SERVICE), eq(PAYMENT_PRODUCT), anyString(), any(), any());
    }

    @Test
    void getPaymentInitiationScaStatus() throws Exception {
        when(paymentInitiationService.getPaymentInitiationScaStatus(eq(PAYMENT_SERVICE),
            eq(PAYMENT_PRODUCT),
            anyString(),
            anyString(),
            any(),
            any()))
            .thenReturn(buildResponse(TestModelBuilder.buildScaStatusResponse()));

        mockMvc.perform(get(PAYMENT_URL + "/foo/authorisations/boo"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.scaStatus", containsString(ScaStatus.FINALISED.toString())));

        verify(paymentInitiationService, times(1))
            .getPaymentInitiationScaStatus(eq(PAYMENT_SERVICE), eq(PAYMENT_PRODUCT), anyString(), anyString(), any(), any());
    }

    @Test
    void getPaymentInitiationStatus_json() throws Exception {
        when(paymentInitiationService.getPaymentInitiationStatus(eq(PAYMENT_SERVICE), eq(PAYMENT_PRODUCT), anyString(), any(), any()))
            .thenReturn(buildResponse(TestModelBuilder.buildPaymentInitiationStatusResponse()));

        mockMvc.perform(get(PAYMENT_URL + "/foo/status")
            .accept(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.transactionStatus", containsString(TransactionStatus.ACCC.toString())))
            .andExpect(jsonPath("$.psuMessage", containsString(TestModelBuilder.MESSAGE)))
            .andExpect(jsonPath("$.fundsAvailable", is(true)));

        verify(paymentInitiationService, times(1))
            .getPaymentInitiationStatus(eq(PAYMENT_SERVICE), eq(PAYMENT_PRODUCT), anyString(), any(), any());
    }

    @Test
    void getPaymentInitiationStatus_xml() throws Exception {
        String response = "<Test>";

        when(paymentInitiationService.getPaymentInitiationStatusAsString(eq(PAYMENT_SERVICE),
            eq(PAYMENT_PRODUCT),
            anyString(),
            any(),
            any()))
            .thenReturn(buildResponse(response));

        mockMvc.perform(get(PAYMENT_URL + "/foo/status"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", containsString(response)));

        verify(paymentInitiationService, times(1))
            .getPaymentInitiationStatusAsString(eq(PAYMENT_SERVICE), eq(PAYMENT_PRODUCT), anyString(), any(), any());
    }

    @Test
    void getPaymentInitiationAuthorisation() throws Exception {
        when(paymentInitiationService.getPaymentInitiationAuthorisation(eq(PAYMENT_SERVICE),
            eq(PAYMENT_PRODUCT),
            anyString(),
            any(),
            any()))
            .thenReturn(buildResponse(new Authorisations()));

        mockMvc.perform(get(PAYMENT_URL + "/foo/authorisations"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.authorisationIds", nullValue()));

        verify(paymentInitiationService, times(1))
            .getPaymentInitiationAuthorisation(eq(PAYMENT_SERVICE), eq(PAYMENT_PRODUCT), anyString(), any(), any());
    }

    @Test
    void startPaymentAuthorisation_updatePsuAuthentication() throws Exception {
        when(paymentInitiationService
            .startPaymentAuthorisation(eq(PAYMENT_SERVICE),
                eq(PAYMENT_PRODUCT),
                anyString(),
                any(),
                any(),
                any(UpdatePsuAuthentication.class)))
            .thenReturn(buildResponse(TestModelBuilder.buildStartScaprocessResponse()));

        mockMvc.perform(post(PAYMENT_URL + "/foo/authorisations")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(writeValueAsString(TestModelBuilder.buildUpdatePsuAuthentication())))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.authorisationId", containsString(TestModelBuilder.AUTHORISATION_ID)))
            .andExpect(jsonPath("$.psuMessage", containsString(TestModelBuilder.MESSAGE)))
            .andExpect(jsonPath("$.scaStatus", containsString(ScaStatus.STARTED.toString())));

        verify(paymentInitiationService, times(1))
            .startPaymentAuthorisation(eq(PAYMENT_SERVICE),
                eq(PAYMENT_PRODUCT),
                anyString(),
                any(),
                any(),
                any(UpdatePsuAuthentication.class));
    }

    @Test
    void startPaymentAuthorisation_emptyAuthorisationBody() throws Exception {
        when(paymentInitiationService
            .startPaymentAuthorisation(eq(PAYMENT_SERVICE), eq(PAYMENT_PRODUCT), anyString(), any(), any()))
            .thenReturn(buildResponse(TestModelBuilder.buildStartScaprocessResponse()));

        mockMvc.perform(post(PAYMENT_URL + "/foo/authorisations")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content("{}"))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.authorisationId", containsString(TestModelBuilder.AUTHORISATION_ID)))
            .andExpect(jsonPath("$.psuMessage", containsString(TestModelBuilder.MESSAGE)))
            .andExpect(jsonPath("$.scaStatus", containsString(ScaStatus.STARTED.toString())));

        verify(paymentInitiationService, times(1))
            .startPaymentAuthorisation(eq(PAYMENT_SERVICE), eq(PAYMENT_PRODUCT), anyString(), any(), any());
    }

    @Test
    void updatePaymentPsuData_updatePsuAuthentication() throws Exception {
        when(paymentInitiationService
            .updatePaymentPsuData(eq(PAYMENT_SERVICE),
                eq(PAYMENT_PRODUCT),
                anyString(),
                anyString(),
                any(),
                any(),
                any(UpdatePsuAuthentication.class)))
            .thenReturn(buildResponse(TestModelBuilder.buildUpdatePsuAuthenticationResponse()));

        mockMvc.perform(put(PAYMENT_URL + "/foo/authorisations/boo")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(writeValueAsString(TestModelBuilder.buildUpdatePsuAuthentication())))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.authorisationId", containsString(TestModelBuilder.AUTHORISATION_ID)))
            .andExpect(jsonPath("$.psuMessage", containsString(TestModelBuilder.MESSAGE)))
            .andExpect(jsonPath("$.scaStatus", containsString(ScaStatus.STARTED.toString())));

        verify(paymentInitiationService, times(1))
            .updatePaymentPsuData(eq(PAYMENT_SERVICE),
                eq(PAYMENT_PRODUCT),
                anyString(),
                anyString(),
                any(),
                any(),
                any(UpdatePsuAuthentication.class));
    }

    @Test
    void updatePaymentPsuData_selectPsuAuthenticationMethod() throws Exception {
        when(paymentInitiationService
            .updatePaymentPsuData(eq(PAYMENT_SERVICE),
                eq(PAYMENT_PRODUCT),
                anyString(),
                anyString(),
                any(),
                any(),
                any(SelectPsuAuthenticationMethod.class)))
            .thenReturn(buildResponse(TestModelBuilder.buildSelectPsuAuthenticationMethodResponse()));

        mockMvc.perform(put(PAYMENT_URL + "/foo/authorisations/boo")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(writeValueAsString(TestModelBuilder.buildSelectPsuAuthenticationMethod())))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.psuMessage", containsString(TestModelBuilder.MESSAGE)))
            .andExpect(jsonPath("$.scaStatus", containsString(ScaStatus.SCAMETHODSELECTED.toString())));

        verify(paymentInitiationService, times(1))
            .updatePaymentPsuData(eq(PAYMENT_SERVICE),
                eq(PAYMENT_PRODUCT),
                anyString(),
                anyString(),
                any(),
                any(),
                any(SelectPsuAuthenticationMethod.class));
    }

    @Test
    void updatePaymentPsuData_transactionAuthorisation() throws Exception {
        when(paymentInitiationService
            .updatePaymentPsuData(eq(PAYMENT_SERVICE),
                eq(PAYMENT_PRODUCT),
                anyString(),
                anyString(),
                any(),
                any(),
                any(TransactionAuthorisation.class)))
            .thenReturn(buildResponse(TestModelBuilder.buildScaStatusResponse()));

        mockMvc.perform(put(PAYMENT_URL + "/foo/authorisations/boo")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(writeValueAsString(TestModelBuilder.buildTransactionAuthorisation())))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.scaStatus", containsString(ScaStatus.FINALISED.toString())));

        verify(paymentInitiationService, times(1))
            .updatePaymentPsuData(eq(PAYMENT_SERVICE),
                eq(PAYMENT_PRODUCT),
                anyString(),
                anyString(),
                any(),
                any(),
                any(TransactionAuthorisation.class));
    }

    private <T> Response<T> buildResponse(T response) {
        return new Response<>(200, response, ResponseHeaders.fromMap(Collections.emptyMap()));
    }

    private <T> String writeValueAsString(T value) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(value);
    }

    private static FormattingConversionService buildFormattingConversionService() {
        FormattingConversionService service = new FormattingConversionService();

        service.addConverter(new Converter<String, PaymentProduct>() {

            @Override
            public PaymentProduct convert(String source) {
                return PaymentProduct.fromValue(source);
            }
        });

        service.addConverter(new Converter<String, PaymentService>() {

            @Override
            public PaymentService convert(String source) {
                return PaymentService.fromValue(source);
            }
        });

        return service;
    }
}
