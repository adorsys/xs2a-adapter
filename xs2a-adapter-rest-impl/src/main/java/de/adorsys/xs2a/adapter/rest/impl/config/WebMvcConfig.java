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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import de.adorsys.xs2a.adapter.api.model.BookingStatusCard;
import de.adorsys.xs2a.adapter.api.model.BookingStatusGeneric;
import de.adorsys.xs2a.adapter.api.model.PaymentProduct;
import de.adorsys.xs2a.adapter.api.model.PaymentService;
import de.adorsys.xs2a.adapter.api.model.PeriodicPaymentInitiationXmlPart2StandingorderTypeJson;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private static final String SWAGGER_URL = "/swagger-ui.html";

    private final ObjectMapper objectMapper = newConfiguredObjectMapper();

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("", SWAGGER_URL);
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        DateTimeFormatterRegistrar registrar = new DateTimeFormatterRegistrar();
        registrar.setUseIsoFormat(true);
        registrar.registerFormatters(registry);

        registry.addConverter(new Converter<String, BookingStatusGeneric>() {

            @Override
            public BookingStatusGeneric convert(String source) {
                return BookingStatusGeneric.fromValue(source);
            }
        });

        registry.addConverter(new Converter<BookingStatusGeneric, String>() {

            @Override
            public String convert(BookingStatusGeneric source) {
                return source.toString();
            }
        });

        registry.addConverter(new Converter<String, BookingStatusCard>() {

            @Override
            public BookingStatusCard convert(String source) {
                return BookingStatusCard.fromValue(source);
            }
        });

        registry.addConverter(new Converter<BookingStatusCard, String>() {

            @Override
            public String convert(BookingStatusCard source) {
                return source.toString();
            }
        });

        registry.addConverter(new Converter<String, PaymentService>() {

            @Override
            public PaymentService convert(String source) {
                return PaymentService.fromValue(source);
            }
        });

        registry.addConverter(new Converter<PaymentProduct, String>() {

            @Override
            public String convert(PaymentProduct source) {
                return source.toString();
            }
        });

        registry.addConverter(new Converter<PaymentService, String>() {

            @Override
            public String convert(PaymentService source) {
                return source.toString();
            }
        });

        registry.addConverter(new Converter<String, PaymentProduct>() {

            @Override
            public PaymentProduct convert(String source) {
                return PaymentProduct.fromValue(source);
            }
        });

        registry.addConverter(new Converter<String, PeriodicPaymentInitiationXmlPart2StandingorderTypeJson>() {
            @Override
            public PeriodicPaymentInitiationXmlPart2StandingorderTypeJson convert(String source) {
                try {
                    return objectMapper.readValue(source, PeriodicPaymentInitiationXmlPart2StandingorderTypeJson.class);
                } catch (JsonProcessingException e) {
                    throw new IllegalArgumentException(e);
                }
            }
        });
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(0, new PeriodicPaymentInitiationMultipartBodyHttpMessageConverter(objectMapper));
    }

    @Bean
    public ObjectMapper objectMapper() {
        return objectMapper;
    }

    public static ObjectMapper newConfiguredObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        return objectMapper;
    }
}
