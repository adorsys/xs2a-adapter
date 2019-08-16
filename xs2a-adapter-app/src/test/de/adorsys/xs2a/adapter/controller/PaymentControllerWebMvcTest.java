package de.adorsys.xs2a.adapter.controller;

import de.adorsys.xs2a.adapter.mapper.HeadersMapper;
import de.adorsys.xs2a.adapter.mapper.PaymentInitiationScaStatusResponseMapper;
import de.adorsys.xs2a.adapter.service.PaymentInitiationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.startsWith;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(PaymentController.class)
public class PaymentControllerWebMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PaymentInitiationService paymentService;
    @MockBean
    private PaymentInitiationScaStatusResponseMapper paymentInitiationScaStatusResponseMapper;
    @MockBean
    private HeadersMapper headersMapper;

    @Test
    public void illegalPaymentService() throws Exception {
        mockMvc.perform(get("/v1/PAYMENTS/sepa-credit-transfers/id"))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.tppMessages[0].text").value(
                startsWith("Illegal value 'PAYMENTS' for parameter 'payment-service', allowed values: payments,")));
    }

    @Test
    public void illegalPaymentProduct() throws Exception {
        mockMvc.perform(get("/v1/payments/SEPA-CREDIT-TRANSFERS/id"))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.tppMessages[0].text").value(
                startsWith("Illegal value 'SEPA-CREDIT-TRANSFERS' for parameter 'payment-product', allowed values: sepa-credit-transfers,")));
    }
}
