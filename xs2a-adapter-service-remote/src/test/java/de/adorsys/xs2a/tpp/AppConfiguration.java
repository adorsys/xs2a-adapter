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
import de.adorsys.xs2a.adapter.controller.ConsentController;
import de.adorsys.xs2a.adapter.controller.Psd2AccountInformationController;
import de.adorsys.xs2a.adapter.mapper.HeadersMapper;
import de.adorsys.xs2a.adapter.mapper.PaymentInitiationScaStatusResponseMapper;
import de.adorsys.xs2a.adapter.remote.api.AccountInformationClient;
import de.adorsys.xs2a.adapter.remote.api.PaymentInitiationClient;
import de.adorsys.xs2a.adapter.remote.api.psd2.Psd2AccountInformationClient;
import de.adorsys.xs2a.adapter.remote.api.psd2.Psd2PaymentInitiationClient;
import de.adorsys.xs2a.adapter.remote.service.impl.RemoteAccountInformationService;
import de.adorsys.xs2a.adapter.remote.service.impl.RemotePaymentInitiationService;
import de.adorsys.xs2a.adapter.remote.service.impl.psd2.RemotePsd2AccountInformationService;
import de.adorsys.xs2a.adapter.remote.service.impl.psd2.RemotePsd2PaymentInitiationService;
import de.adorsys.xs2a.adapter.service.AccountInformationService;
import de.adorsys.xs2a.adapter.service.PaymentInitiationService;
import de.adorsys.xs2a.adapter.service.psd2.Psd2AccountInformationService;
import de.adorsys.xs2a.adapter.service.psd2.Psd2PaymentInitiationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;

import java.util.Arrays;
import java.util.List;

@Configuration
public class AppConfiguration {

    @Autowired
    AccountInformationClient accountInformationClient;

    @Autowired
    PaymentInitiationClient paymentInitiationClient;

    @Autowired
    Psd2AccountInformationClient psd2AccountInformationClient;

    @Autowired
    Psd2PaymentInitiationClient psd2PaymentInitiationClient;

    @Bean
    PaymentInitiationScaStatusResponseMapper getPaymentInitiationScaStatusResponseMapper() {
        return new PaymentInitiationScaStatusResponseMapper();
    }

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
    Psd2AccountInformationService psd2AccountInformationService() {
        return new RemotePsd2AccountInformationService(psd2AccountInformationClient);
    }

    @Bean
    Psd2PaymentInitiationService psd2PaymentInitiationService() {
        return new RemotePsd2PaymentInitiationService(psd2PaymentInitiationClient);
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
    Psd2AccountInformationController psd2AccountInformationController(Psd2AccountInformationService service,
                                                                      HeadersMapper headersMapper) {
        return new Psd2AccountInformationController(service, headersMapper);
    }
}
