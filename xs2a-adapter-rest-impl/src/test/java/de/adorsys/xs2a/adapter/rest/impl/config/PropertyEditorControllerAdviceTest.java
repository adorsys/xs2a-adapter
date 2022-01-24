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

package de.adorsys.xs2a.adapter.rest.impl.config;

import de.adorsys.xs2a.adapter.api.AccountInformationService;
import de.adorsys.xs2a.adapter.api.RequestHeaders;
import de.adorsys.xs2a.adapter.api.RequestParams;
import de.adorsys.xs2a.adapter.api.exception.NotAcceptableException;
import de.adorsys.xs2a.adapter.api.model.BookingStatusCard;
import de.adorsys.xs2a.adapter.api.model.BookingStatusGeneric;
import de.adorsys.xs2a.adapter.mapper.HeadersMapper;
import de.adorsys.xs2a.adapter.rest.impl.controller.ConsentController;
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
        assertEquals(BookingStatusGeneric.BOOKED.toString(), actualParams.bookingStatus());
    }

    @Test
    void setAsText_cardBookingStatus() throws Exception {
        when(accountInformationService.getCardAccountTransactionList(anyString(), any(RequestHeaders.class), paramsCaptor.capture()))
            .thenThrow(new NotAcceptableException("error message"));

        mockMvc.perform(get("/v1/card-accounts/1/transactions")
                .accept(APPLICATION_JSON_VALUE)
                .param("bookingStatus", "booked"))
            .andExpect(status().isNotAcceptable());

        verify(propertyEditorControllerAdvice, atLeastOnce()).initBinder(any());

        RequestParams actualParams = paramsCaptor.getValue();
        assertNotNull(actualParams);
        assertEquals(BookingStatusCard.BOOKED.toString(), actualParams.bookingStatus());
    }
}
