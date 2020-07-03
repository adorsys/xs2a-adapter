package de.adorsys.xs2a.adapter.config;

import de.adorsys.xs2a.adapter.api.model.BookingStatus;
import de.adorsys.xs2a.adapter.controller.ConsentController;
import de.adorsys.xs2a.adapter.mapper.HeadersMapper;
import de.adorsys.xs2a.adapter.service.AccountInformationService;
import de.adorsys.xs2a.adapter.service.RequestHeaders;
import de.adorsys.xs2a.adapter.service.RequestParams;
import de.adorsys.xs2a.adapter.service.exception.NotAcceptableException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.convert.ApplicationConversionService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class PropertyEditorControllerAdviceTest {

    MockMvc mockMvc;

    @InjectMocks
    private ConsentController consentController;
    @Mock
    private AccountInformationService accountInformationService;

    @Captor
    private ArgumentCaptor<RequestParams> paramsCaptor;

    @Spy
    PropertyEditorControllerAdvice propertyEditorControllerAdvice
        = new PropertyEditorControllerAdvice(new ApplicationConversionService());

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(consentController)
            .setControllerAdvice(propertyEditorControllerAdvice,
                new RestExceptionHandler(new HeadersMapper()))
            .build();
    }

    @Test
    void setAsText_bookingStatus() throws Exception {
        when(accountInformationService.getTransactionList(anyString(), any(RequestHeaders.class), paramsCaptor.capture()))
            .thenThrow(new NotAcceptableException("error message"));

        mockMvc.perform(get("/v1/accounts/1/transactions")
            .accept(APPLICATION_JSON_VALUE)
            .param("bookingStatus", "booked"))
            .andExpect(status().isNotAcceptable());

        verify(propertyEditorControllerAdvice, atLeastOnce()).initBinder(any());

        RequestParams actualParams = paramsCaptor.getValue();
        assertNotNull(actualParams);
        assertEquals(BookingStatus.BOOKED.toString(), actualParams.bookingStatus());
    }
}
