package de.adorsys.xs2a.adapter.remote.config;

import de.adorsys.xs2a.adapter.model.BookingStatusTO;
import de.adorsys.xs2a.adapter.model.PaymentProductTO;
import de.adorsys.xs2a.adapter.model.PaymentServiceTO;
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

            addConverter(new Converter<BookingStatusTO, String>() {
                @Override
                public String convert(BookingStatusTO source) {
                    return source.toString();
                }
            });

            addConverter(new Converter<PaymentProductTO, String>() {
                @Override
                public String convert(PaymentProductTO source) {
                    return source.toString();
                }
            });

            addConverter(new Converter<PaymentServiceTO, String>() {
                @Override
                public String convert(PaymentServiceTO source) {
                    return source.toString();
                }
            });
        }
    }
}
