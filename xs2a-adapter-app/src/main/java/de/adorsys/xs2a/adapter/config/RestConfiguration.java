package de.adorsys.xs2a.adapter.config;

import de.adorsys.xs2a.adapter.mapper.PaymentInitiationScaStatusResponseMapper;
import de.adorsys.xs2a.adapter.service.PaymentInitiationService;
import de.adorsys.xs2a.adapter.service.ais.AccountInformationService;
import de.adorsys.xs2a.adapter.service.impl.AccountInformationServiceImpl;
import de.adorsys.xs2a.adapter.service.impl.PaymentInitiationServiceImpl;
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
