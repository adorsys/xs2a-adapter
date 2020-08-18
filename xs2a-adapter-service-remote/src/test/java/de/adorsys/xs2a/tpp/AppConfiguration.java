/*
 * Copyright 2018-2018 adorsys GmbH & Co KG
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.adorsys.xs2a.tpp;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.adorsys.xs2a.adapter.mapper.HeadersMapper;
import de.adorsys.xs2a.adapter.remote.api.AccountInformationClient;
import de.adorsys.xs2a.adapter.remote.api.PaymentInitiationClient;
import de.adorsys.xs2a.adapter.remote.config.FeignConfiguration;
import de.adorsys.xs2a.adapter.remote.service.impl.RemoteAccountInformationService;
import de.adorsys.xs2a.adapter.remote.service.impl.RemotePaymentInitiationService;
import de.adorsys.xs2a.adapter.rest.impl.config.WebMvcConfig;
import de.adorsys.xs2a.adapter.rest.impl.controller.ConsentController;
import de.adorsys.xs2a.adapter.rest.impl.controller.PaymentController;
import de.adorsys.xs2a.adapter.service.AccountInformationService;
import de.adorsys.xs2a.adapter.service.PaymentInitiationService;
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
