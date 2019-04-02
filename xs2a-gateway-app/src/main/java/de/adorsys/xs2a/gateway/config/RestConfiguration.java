package de.adorsys.xs2a.gateway.config;

import de.adorsys.xs2a.gateway.service.PaymentService;
import de.adorsys.xs2a.gateway.service.impl.PaymentServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RestConfiguration {
    @Bean
    PaymentService paymentService() {
        return new PaymentServiceImpl();
    }
}
