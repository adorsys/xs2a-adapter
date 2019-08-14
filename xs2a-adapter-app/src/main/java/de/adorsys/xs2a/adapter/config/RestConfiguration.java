package de.adorsys.xs2a.adapter.config;

import de.adorsys.xs2a.adapter.mapper.PaymentInitiationScaStatusResponseMapper;
import de.adorsys.xs2a.adapter.registry.AspspSearchServiceImpl;
import de.adorsys.xs2a.adapter.registry.AspspServiceImpl;
import de.adorsys.xs2a.adapter.registry.LuceneAspspRepositoryFactory;
import de.adorsys.xs2a.adapter.service.AspspReadOnlyRepository;
import de.adorsys.xs2a.adapter.service.AspspRepository;
import de.adorsys.xs2a.adapter.service.AspspSearchService;
import de.adorsys.xs2a.adapter.service.PaymentInitiationService;
import de.adorsys.xs2a.adapter.service.ais.AccountInformationService;
import de.adorsys.xs2a.adapter.service.impl.AccountInformationServiceImpl;
import de.adorsys.xs2a.adapter.service.impl.AdapterServiceLoader;
import de.adorsys.xs2a.adapter.service.impl.PaymentInitiationServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

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
        return new AdapterServiceLoader(aspspRepository());
    }

    @Bean
    AspspReadOnlyRepository aspspRepository() {
        return new LuceneAspspRepositoryFactory().newLuceneAspspRepository();
    }

    @Bean
    AspspSearchService aspspSearchService(AspspReadOnlyRepository aspspRepository) {
        return new AspspSearchServiceImpl(aspspRepository);
    }

    @Profile("dev")
    @Bean
    AspspRepository aspspModifyRepository() {
        return new AspspServiceImpl((AspspRepository) aspspRepository());
    }
}
