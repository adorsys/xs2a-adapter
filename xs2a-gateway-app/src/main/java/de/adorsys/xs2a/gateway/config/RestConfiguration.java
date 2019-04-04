package de.adorsys.xs2a.gateway.config;

import de.adorsys.xs2a.gateway.mapper.PaymentInitiationScaStatusResponseMapper;
import de.adorsys.xs2a.gateway.service.PaymentService;
import de.adorsys.xs2a.gateway.service.consent.ConsentService;
import de.adorsys.xs2a.gateway.service.impl.ConsentServiceImpl;
import de.adorsys.xs2a.gateway.service.impl.PaymentServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RestConfiguration {
    @Bean
    PaymentService paymentService() {
        return new PaymentServiceImpl();
    }

    @Bean
    PaymentInitiationScaStatusResponseMapper getPaymentInitiationScaStatusResponseMapper() {
        return new PaymentInitiationScaStatusResponseMapper();
    }

    @Bean
    ConsentService consentService() {
        return new ConsentServiceImpl();
    }
}
