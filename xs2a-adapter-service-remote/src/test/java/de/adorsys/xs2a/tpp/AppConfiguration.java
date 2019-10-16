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

import de.adorsys.xs2a.adapter.mapper.HeadersMapper;
import de.adorsys.xs2a.adapter.mapper.PaymentInitiationScaStatusResponseMapper;
import de.adorsys.xs2a.adapter.remote.api.AccountInformationClient;
import de.adorsys.xs2a.adapter.remote.api.PaymentInitiationClient;
import de.adorsys.xs2a.adapter.remote.api.psd2.Psd2AccountInformationClient;
import de.adorsys.xs2a.adapter.remote.service.impl.RemoteAccountInformationService;
import de.adorsys.xs2a.adapter.remote.service.impl.RemotePaymentInitiationService;
import de.adorsys.xs2a.adapter.remote.service.impl.psd2.RemotePsd2AccountInformationService;
import de.adorsys.xs2a.adapter.service.AccountInformationService;
import de.adorsys.xs2a.adapter.service.PaymentInitiationService;
import de.adorsys.xs2a.adapter.service.psd2.Psd2AccountInformationService;
import feign.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.converter.GenericHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
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
    public GenericHttpMessageConverter<Object> transactionsResponseConverter() {
        return new GenericHttpMessageConverter<Object>() {

            @Override
            public List<MediaType> getSupportedMediaTypes() {
                return Arrays.asList(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_PLAIN);
            }

            @Override
            public Object read(Class clazz,
                               HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
                throw new UnsupportedOperationException();
            }

            @Override
            public void write(Object o,
                              MediaType contentType,
                              HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
                throw new UnsupportedOperationException();
            }

            @Override
            public boolean canWrite(Class clazz, MediaType mediaType) {
                return false;
            }

            @Override
            public boolean canRead(Class clazz, MediaType mediaType) {
                return false;
            }

            @Override
            public void write(Object o,
                              Type type,
                              MediaType contentType,
                              HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
                throw new UnsupportedOperationException();
            }

            @Override
            public boolean canWrite(Type type, Class clazz, MediaType mediaType) {
                return false;
            }

            @Override
            public Object read(Type type,
                               Class contextClass,
                               HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
                if (!(inputMessage instanceof ClientHttpResponse)) {
                    throw new IllegalArgumentException();
                }
                ClientHttpResponse response = (ClientHttpResponse) inputMessage;
                return Util.toString(new InputStreamReader(response.getBody(), Util.UTF_8));
            }

            @Override
            public boolean canRead(Type type, Class contextClass, MediaType mediaType) {
                return "?".equals(type.getTypeName());
            }
        };
    }
}
