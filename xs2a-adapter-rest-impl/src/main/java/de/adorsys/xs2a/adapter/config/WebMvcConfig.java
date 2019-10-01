package de.adorsys.xs2a.adapter.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import de.adorsys.xs2a.adapter.model.BookingStatusTO;
import de.adorsys.xs2a.adapter.model.PaymentProductTO;
import de.adorsys.xs2a.adapter.model.PaymentServiceTO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private static final String SWAGGER_URL = "/swagger-ui.html";

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("", SWAGGER_URL);
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        DateTimeFormatterRegistrar registrar = new DateTimeFormatterRegistrar();
        registrar.setUseIsoFormat(true);
        registrar.registerFormatters(registry);

        registry.addConverter(new Converter<String, BookingStatusTO>() {

            @Override
            public BookingStatusTO convert(String source) {
                return BookingStatusTO.fromValue(source);
            }
        });

        registry.addConverter(new Converter<BookingStatusTO, String>() {

            @Override
            public String convert(BookingStatusTO source) {
                return source.toString();
            }
        });

        registry.addConverter(new Converter<String, PaymentServiceTO>() {

            @Override
            public PaymentServiceTO convert(String source) {
                return PaymentServiceTO.fromValue(source);
            }
        });

        registry.addConverter(new Converter<PaymentProductTO, String>() {

            @Override
            public String convert(PaymentProductTO source) {
                return source.toString();
            }
        });

        registry.addConverter(new Converter<PaymentServiceTO, String>() {

            @Override
            public String convert(PaymentServiceTO source) {
                return source.toString();
            }
        });

        registry.addConverter(new Converter<String, PaymentProductTO>() {

            @Override
            public PaymentProductTO convert(String source) {
                return PaymentProductTO.fromValue(source);
            }
        });

    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        return objectMapper;
    }
}
