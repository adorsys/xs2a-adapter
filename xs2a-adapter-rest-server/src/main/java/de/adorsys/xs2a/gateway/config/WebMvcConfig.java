package de.adorsys.xs2a.gateway.config;

import de.adorsys.xs2a.gateway.model.BookingStatusTO;
import de.adorsys.xs2a.gateway.model.PaymentProductTO;
import de.adorsys.xs2a.gateway.model.PaymentServiceTO;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

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

        registry.addConverter(new Converter<String, PaymentServiceTO>() {

            @Override
            public PaymentServiceTO convert(String source) {
                return PaymentServiceTO.fromValue(source);
            }
        });

        registry.addConverter(new Converter<String, PaymentProductTO>() {

            @Override
            public PaymentProductTO convert(String source) {
                return PaymentProductTO.fromValue(source);
            }
        });
    }
}
