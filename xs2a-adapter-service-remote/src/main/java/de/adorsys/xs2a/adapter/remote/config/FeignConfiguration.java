package de.adorsys.xs2a.adapter.remote.config;

import de.adorsys.xs2a.adapter.api.model.BookingStatus;
import de.adorsys.xs2a.adapter.api.model.PaymentProduct;
import de.adorsys.xs2a.adapter.api.model.PaymentService;
import feign.Contract;
import org.springframework.cloud.openfeign.support.SpringMvcContract;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.support.DefaultConversionService;

import java.util.Collections;

@Configuration
public class FeignConfiguration {

    @Bean
    public Contract feignContract() {
        return new SpringMvcContract(Collections.emptyList(), new CustomConversionService());
    }

    public static class CustomConversionService extends DefaultConversionService {

        public CustomConversionService() {

            addConverter(new Converter<BookingStatus, String>() {
                @Override
                public String convert(BookingStatus source) {
                    return source.toString();
                }
            });

            addConverter(new Converter<PaymentProduct, String>() {
                @Override
                public String convert(PaymentProduct source) {
                    return source.toString();
                }
            });

            addConverter(new Converter<PaymentService, String>() {
                @Override
                public String convert(PaymentService source) {
                    return source.toString();
                }
            });
        }
    }
}
