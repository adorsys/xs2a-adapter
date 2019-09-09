package de.adorsys.xs2a.adapter.config;

import de.adorsys.xs2a.adapter.mapper.PaymentInitiationScaStatusResponseMapper;
import de.adorsys.xs2a.adapter.registry.AspspCsvServiceImpl;
import de.adorsys.xs2a.adapter.registry.AspspSearchServiceImpl;
import de.adorsys.xs2a.adapter.registry.LuceneAspspRepositoryFactory;
import de.adorsys.xs2a.adapter.service.*;
import de.adorsys.xs2a.adapter.service.AccountInformationService;
import de.adorsys.xs2a.adapter.service.impl.AccountInformationServiceImpl;
import de.adorsys.xs2a.adapter.service.loader.AdapterDelegatingOauth2Service;
import de.adorsys.xs2a.adapter.service.loader.AdapterServiceLoader;
import de.adorsys.xs2a.adapter.service.impl.PaymentInitiationServiceImpl;
import de.adorsys.xs2a.adapter.service.loader.Psd2AdapterDelegatingAccountInformationService;
import de.adorsys.xs2a.adapter.service.loader.Psd2AdapterServiceLoader;
import de.adorsys.xs2a.adapter.service.psd2.Psd2AccountInformationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

@Configuration
public class RestConfiguration {

    @Bean
    PaymentInitiationService paymentInitiationService(AdapterServiceLoader adapterServiceLoader) {
        return new PaymentInitiationServiceImpl(adapterServiceLoader);
    }

    @Bean
    PaymentInitiationScaStatusResponseMapper getPaymentInitiationScaStatusResponseMapper() {
        return new PaymentInitiationScaStatusResponseMapper();
    }

    @Bean
    AccountInformationService accountInformationService(AdapterServiceLoader adapterServiceLoader) {
        return new AccountInformationServiceImpl(adapterServiceLoader);
    }

    @Bean
    AdapterServiceLoader adapterServiceLoader(Pkcs12KeyStore keyStore) {
        return new AdapterServiceLoader(aspspRepository(), keyStore);
    }

    @Bean
    AspspRepository aspspRepository() {
        return new LuceneAspspRepositoryFactory().newLuceneAspspRepository();
    }

    @Bean
    AspspSearchService aspspSearchService(AspspReadOnlyRepository aspspRepository) {
        return new AspspSearchServiceImpl(aspspRepository);
    }

    @Bean
    Psd2AccountInformationService psd2AccountInformationService(AspspReadOnlyRepository aspspRepository, Pkcs12KeyStore keyStore) {
        return new Psd2AdapterDelegatingAccountInformationService(new Psd2AdapterServiceLoader(aspspRepository, keyStore));
    }

    @Bean
    Oauth2Service oauth2Service(AdapterServiceLoader adapterServiceLoader) {
        return new AdapterDelegatingOauth2Service(adapterServiceLoader);
    }

    @Bean
    Pkcs12KeyStore pkcs12KeyStore() throws CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException {
        return new Pkcs12KeyStore(System.getProperty("pkcs12.keyStore"));
    }

    @Bean
    AspspCsvService aspspCsvService(AspspRepository aspspRepository) {
        return new AspspCsvServiceImpl(aspspRepository);
    }
}
