package de.adorsys.xs2a.adapter.config;

import de.adorsys.xs2a.adapter.api.model.BookingStatus;
import de.adorsys.xs2a.adapter.api.model.PaymentProduct;
import de.adorsys.xs2a.adapter.api.model.PaymentService;
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
    }

}
