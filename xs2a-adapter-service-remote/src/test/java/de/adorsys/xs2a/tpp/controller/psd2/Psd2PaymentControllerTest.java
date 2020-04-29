package de.adorsys.xs2a.tpp.controller.psd2;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.adorsys.xs2a.adapter.http.ContentType;
import de.adorsys.xs2a.adapter.rest.psd2.model.PaymentProductTO;
import de.adorsys.xs2a.adapter.rest.psd2.model.PaymentServiceTO;
import de.adorsys.xs2a.adapter.service.RequestHeaders;
import de.adorsys.xs2a.adapter.service.Response;
import de.adorsys.xs2a.adapter.service.ResponseHeaders;
import de.adorsys.xs2a.adapter.service.psd2.Psd2PaymentInitiationService;
import de.adorsys.xs2a.adapter.service.psd2.model.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
class Psd2PaymentControllerTest {

    private static final String FLAWED_PARAMETER = "/garbage";
    private static final String PAYMENTS = "/" + PaymentServiceTO.PAYMENTS;
    private static final String BULK_PAYMENTS = "/" + PaymentServiceTO.BULK_PAYMENTS;
    private static final String PERIODIC_PAYMENTS = "/" + PaymentServiceTO.PERIODIC_PAYMENTS;
    private static final String SEPA_CREDIT_TRANSFERS = "/" + PaymentProductTO.SEPA_CREDIT_TRANSFERS;
    private static final String INSTANT_SEPA_CREDIT_TRANSFERS = "/" + PaymentProductTO.INSTANT_SEPA_CREDIT_TRANSFERS;
    private static final String TARGET_2_PAYMENTS = "/" + PaymentProductTO.TARGET_2_PAYMENTS;
    private static final String CROSS_BORDER_CREDIT_TRANSFERS = "/" + PaymentProductTO.CROSS_BORDER_CREDIT_TRANSFERS;
    private static final String PAIN_001_SEPA_CREDIT_TRANSFERS = "/" + PaymentProductTO.PAIN_001_SEPA_CREDIT_TRANSFERS;
    private static final String PAIN_001_INSTANT_SEPA_CREDIT_TRANSFERS = "/" + PaymentProductTO.PAIN_001_INSTANT_SEPA_CREDIT_TRANSFERS;
    private static final String PAIN_001_TARGET_2_PAYMENTS = "/" + PaymentProductTO.PAIN_001_TARGET_2_PAYMENTS;
    private static final String PAYMENT_ID = "paymentId";
    private static final String AUTHORISATIONS = "authorisations";
    private static final String AUTHORISATION_ID = "authorisationId";

    private final ObjectMapper mapper = new ObjectMapper()
        .setSerializationInclusion(JsonInclude.Include.NON_NULL);

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private Psd2PaymentInitiationService service;

    @Test
    void initiatePayment() throws Exception {
        PaymentInitiationRequestResponse response = createPaymentInitiationRequestResponse();

        when(service.initiatePayment(any(PaymentService.class), any(PaymentProduct.class), anyMap(), anyMap(), any(PaymentInitiation.class)))
            .thenReturn(new Response<>(201, response, ResponseHeaders.emptyResponseHeaders()));

        mockMvc.perform(post(PAYMENTS + SEPA_CREDIT_TRANSFERS)
            .header(RequestHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON)
            .content("{}"))
            .andExpect(status().isCreated())
            .andExpect(content().string(mapper.writeValueAsString(response)));

        verify(service, times(1)).initiatePayment(any(PaymentService.class), any(PaymentProduct.class), anyMap(), anyMap(), any(PaymentInitiation.class));
    }

    @Test
    void initiatePayment_wrongParameterPaymentService() throws Exception {
        mockMvc.perform(post(FLAWED_PARAMETER + SEPA_CREDIT_TRANSFERS)
            .header(RequestHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON)
            .content("{}"))
            .andExpect(status().isNotFound());
    }

    @Test
    void initiatePayment_wrongParameterPaymentProduct() throws Exception {
        mockMvc.perform(post(PAYMENTS + FLAWED_PARAMETER)
            .header(RequestHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON)
            .content("{}"))
            .andExpect(status().isBadRequest());
    }

    @Test
    void getPaymentInformation() throws Exception {
        when(service.getPaymentInformation(any(PaymentService.class), any(PaymentProduct.class), anyString(), anyMap(), anyMap()))
            .thenReturn(new Response<>(200, "response", ResponseHeaders.emptyResponseHeaders()));

        mockMvc.perform(get(BULK_PAYMENTS + INSTANT_SEPA_CREDIT_TRANSFERS + "/" + PAYMENT_ID)
            .header(RequestHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().string("response"));

        verify(service, times(1)).getPaymentInformation(any(PaymentService.class), any(PaymentProduct.class), anyString(), anyMap(), anyMap());
    }

    @Test
    void getPaymentInitiationStatus() throws Exception {
        when(service.getPaymentInitiationStatus(any(PaymentService.class), any(PaymentProduct.class), anyString(), anyMap(), anyMap()))
            .thenReturn(new Response<>(200, "response", ResponseHeaders.emptyResponseHeaders()));

        mockMvc.perform(get(PERIODIC_PAYMENTS + TARGET_2_PAYMENTS + "/" + PAYMENT_ID + "/status")
            .header(RequestHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().string("response"));

        verify(service, times(1)).getPaymentInitiationStatus(any(PaymentService.class), any(PaymentProduct.class), anyString(), anyMap(), anyMap());
    }

    @Test
    void getPaymentInitiationAuthorisation() throws Exception {
        when(service.getPaymentInitiationAuthorisation(any(PaymentService.class), any(PaymentProduct.class), anyString(), anyMap(), anyMap()))
            .thenReturn(new Response<>(200, new Authorisations(), ResponseHeaders.emptyResponseHeaders()));

        mockMvc.perform(get(PERIODIC_PAYMENTS + CROSS_BORDER_CREDIT_TRANSFERS + "/" + PAYMENT_ID + "/" + AUTHORISATIONS)
            .header(RequestHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON))
            .andExpect(status().isOk());

        verify(service, times(1)).getPaymentInitiationAuthorisation(any(PaymentService.class), any(PaymentProduct.class), anyString(), anyMap(), anyMap());
    }

    @Test
    void startPaymentAuthorisation() throws Exception {
        StartScaProcessResponse response = createStartScaProcessResponse();

        when(service.startPaymentAuthorisation(any(PaymentService.class), any(PaymentProduct.class), anyString(), anyMap(), anyMap(), any(UpdateAuthorisation.class)))
            .thenReturn(new Response<>(201, response, ResponseHeaders.emptyResponseHeaders()));

        mockMvc.perform(post(PERIODIC_PAYMENTS + PAIN_001_SEPA_CREDIT_TRANSFERS + "/" + PAYMENT_ID + "/" + AUTHORISATIONS)
            .header(RequestHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON)
            .content("{}"))
            .andExpect(status().isCreated())
            .andExpect(content().string(mapper.writeValueAsString(response)));

        verify(service, times(1)).startPaymentAuthorisation(any(PaymentService.class), any(PaymentProduct.class), anyString(), anyMap(), anyMap(), any(UpdateAuthorisation.class));
    }

    @Test
    void getPaymentInitiationScaStatus() throws Exception {
        ScaStatusResponse response = createScaStatusResponse();

        when(service.getPaymentInitiationScaStatus(any(PaymentService.class), any(PaymentProduct.class), anyString(), anyString(), anyMap(), anyMap()))
            .thenReturn(new Response<>(200, response, ResponseHeaders.emptyResponseHeaders()));

        mockMvc.perform(get(PERIODIC_PAYMENTS + PAIN_001_INSTANT_SEPA_CREDIT_TRANSFERS + "/" + PAYMENT_ID + "/" + AUTHORISATIONS + "/" + AUTHORISATION_ID)
            .header(RequestHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().string(mapper.writeValueAsString(response)));

        verify(service, times(1)).getPaymentInitiationScaStatus(any(PaymentService.class), any(PaymentProduct.class), anyString(), anyString(), anyMap(), anyMap());
    }

    @Test
    void updatePaymentPsuData() throws Exception {
        UpdateAuthorisationResponse response = createUpdateAuthorisationResponse();

        when(service.updatePaymentPsuData(any(PaymentService.class), any(PaymentProduct.class), anyString(), anyString(), anyMap(), anyMap(), any(UpdateAuthorisation.class)))
            .thenReturn(new Response<>(200, response, ResponseHeaders.emptyResponseHeaders()));

        mockMvc.perform(put(PERIODIC_PAYMENTS + PAIN_001_TARGET_2_PAYMENTS + "/" + PAYMENT_ID + "/" + AUTHORISATIONS + "/" + AUTHORISATION_ID)
            .header(RequestHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON)
            .content("{}"))
            .andExpect(status().isOk())
            .andExpect(content().string(mapper.writeValueAsString(response)));

        verify(service, times(1)).updatePaymentPsuData(any(PaymentService.class), any(PaymentProduct.class), anyString(), anyString(), anyMap(), anyMap(), any(UpdateAuthorisation.class));
    }

    private PaymentInitiationRequestResponse createPaymentInitiationRequestResponse() {
        PaymentInitiationRequestResponse response = new PaymentInitiationRequestResponse();
        response.setTransactionStatus("status");

        return response;
    }

    private StartScaProcessResponse createStartScaProcessResponse() {
        StartScaProcessResponse response = new StartScaProcessResponse();
        response.setScaStatus("scaStatus");

        return response;
    }

    private ScaStatusResponse createScaStatusResponse() {
        ScaStatusResponse response = new ScaStatusResponse();
        response.setScaStatus("scaStatus");

        return response;
    }

    private UpdateAuthorisationResponse createUpdateAuthorisationResponse() {
        UpdateAuthorisationResponse response = new UpdateAuthorisationResponse();
        response.setScaStatus("scaStatus");

        return response;
    }
}
