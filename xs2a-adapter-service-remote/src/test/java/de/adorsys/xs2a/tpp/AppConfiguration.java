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

package de.adorsys.xs2a.tpp;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.adorsys.xs2a.adapter.api.AccountInformationService;
import de.adorsys.xs2a.adapter.api.PaymentInitiationService;
import de.adorsys.xs2a.adapter.mapper.HeadersMapper;
import de.adorsys.xs2a.adapter.remote.RemoteAccountInformationService;
import de.adorsys.xs2a.adapter.remote.RemotePaymentInitiationService;
import de.adorsys.xs2a.adapter.remote.client.AccountInformationClient;
import de.adorsys.xs2a.adapter.remote.client.PaymentInitiationClient;
import de.adorsys.xs2a.adapter.remote.config.FeignConfiguration;
import de.adorsys.xs2a.adapter.rest.impl.config.WebMvcConfig;
import de.adorsys.xs2a.adapter.rest.impl.controller.ConsentController;
import de.adorsys.xs2a.adapter.rest.impl.controller.PaymentController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;

import java.util.Arrays;
import java.util.List;

@Configuration
@Import({WebMvcConfig.class, FeignConfiguration.class})
public class AppConfiguration {

    @Autowired
    AccountInformationClient accountInformationClient;

    @Autowired
    PaymentInitiationClient paymentInitiationClient;

    @Bean
    HeadersMapper getHeadersMapper() {
        return new HeadersMapper();
    }

    @Bean
    PaymentInitiationService paymentInitiationService() {
        return new RemotePaymentInitiationService(paymentInitiationClient);
    }

    @Bean
    AccountInformationService accountInformationService() {
        return new RemoteAccountInformationService(accountInformationClient);
    }

    @Bean
    HttpMessageConverter<String> objectStringHttpMessageConverter() {
        return new StringHttpMessageConverter() {
            @Override
            public boolean supports(Class<?> clazz) {
                return Object.class == clazz;
            }

            @Override
            public List<MediaType> getSupportedMediaTypes() {
                return Arrays.asList(MediaType.APPLICATION_XML, MediaType.TEXT_PLAIN);
            }
        };
    }

    @Bean
    ConsentController consentController(AccountInformationService accountInformationService,
                                        ObjectMapper objectMapper,
                                        HeadersMapper headersMapper) {
        return new ConsentController(accountInformationService, objectMapper, headersMapper);
    }

    @Bean
    PaymentController paymentController(PaymentInitiationService paymentInitiationService,
                                        ObjectMapper objectMapper,
                                        HeadersMapper headersMapper) {
        return new PaymentController(paymentInitiationService, headersMapper, objectMapper);
    }
}
