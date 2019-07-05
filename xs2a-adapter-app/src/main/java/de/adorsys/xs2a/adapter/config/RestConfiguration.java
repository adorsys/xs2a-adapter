package de.adorsys.xs2a.adapter.config;

import de.adorsys.xs2a.adapter.mapper.PaymentInitiationScaStatusResponseMapper;
import de.adorsys.xs2a.adapter.service.GeneralInformationService;
import de.adorsys.xs2a.adapter.service.PaymentInitiationService;
import de.adorsys.xs2a.adapter.service.ais.AccountInformationService;
import de.adorsys.xs2a.adapter.service.impl.AccountInformationServiceImpl;
import de.adorsys.xs2a.adapter.service.impl.AdapterServiceLoader;
import de.adorsys.xs2a.adapter.service.impl.GeneralInformationServiceImpl;
import de.adorsys.xs2a.adapter.service.impl.PaymentInitiationServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RestConfiguration {

    @Bean
    PaymentInitiationService paymentInitiationService() {
        return new PaymentInitiationServiceImpl(adapterServiceLoader());
    }

    @Bean
    PaymentInitiationScaStatusResponseMapper getPaymentInitiationScaStatusResponseMapper() {
        return new PaymentInitiationScaStatusResponseMapper();
    }

    @Bean
    AccountInformationService accountInformationService() {
        return new AccountInformationServiceImpl(adapterServiceLoader());
    }

    @Bean
    AdapterServiceLoader adapterServiceLoader() {
        return new AdapterServiceLoader();
    }

    @Bean
    GeneralInformationService generalInformationService() {
        return new GeneralInformationServiceImpl(adapterServiceLoader());
    }
}
