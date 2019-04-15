package de.adorsys.xs2a.gateway.config;

import de.adorsys.xs2a.gateway.mapper.PaymentInitiationScaStatusResponseMapper;
import de.adorsys.xs2a.gateway.service.PaymentInitiationService;
import de.adorsys.xs2a.gateway.service.ais.AccountInformationService;
import de.adorsys.xs2a.gateway.service.impl.AccountInformationServiceImpl;
import de.adorsys.xs2a.gateway.service.impl.PaymentInitiationServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RestConfiguration {
    @Bean
    PaymentInitiationService paymentService() {
        return new PaymentInitiationServiceImpl();
    }

    @Bean
    PaymentInitiationScaStatusResponseMapper getPaymentInitiationScaStatusResponseMapper() {
        return new PaymentInitiationScaStatusResponseMapper();
    }

    @Bean
    AccountInformationService consentService() {
        return new AccountInformationServiceImpl();
    }
}
