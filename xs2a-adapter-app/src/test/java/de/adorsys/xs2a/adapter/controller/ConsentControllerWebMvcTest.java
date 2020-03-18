package de.adorsys.xs2a.adapter.controller;

import de.adorsys.xs2a.adapter.mapper.HeadersMapper;
import de.adorsys.xs2a.adapter.service.AccountInformationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ConsentController.class)
public class ConsentControllerWebMvcTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountInformationService accountInformationService;
    @MockBean
    private HeadersMapper headersMapper;

    @Test
    public void illegalBookingStatus() throws Exception {
        mockMvc.perform(get("/v1/accounts/resource-id/transactions").param("bookingStatus", "BOOKED"))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.tppMessages[0].text")
                .value("Illegal value 'BOOKED' for parameter 'bookingStatus', allowed values: " +
                    "information, booked, pending, both"));
    }
}
