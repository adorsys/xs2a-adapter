package de.adorsys.xs2a.adapter.rest.impl.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import de.adorsys.xs2a.adapter.api.model.BookingStatus;
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

        registry.addConverter(new Converter<String, BookingStatus>() {

            @Override
            public BookingStatus convert(String source) {
                return BookingStatus.fromValue(source);
            }
        });

        registry.addConverter(new Converter<BookingStatus, String>() {

            @Override
            public String convert(BookingStatus source) {
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
