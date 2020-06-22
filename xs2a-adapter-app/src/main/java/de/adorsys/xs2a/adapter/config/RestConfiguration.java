package de.adorsys.xs2a.adapter.config;

import de.adorsys.xs2a.adapter.adapter.link.identity.IdentityLinksRewriter;
import de.adorsys.xs2a.adapter.http.ApacheHttpClientFactory;
import de.adorsys.xs2a.adapter.http.HttpClientFactory;
import de.adorsys.xs2a.adapter.registry.LuceneAspspRepositoryFactory;
import de.adorsys.xs2a.adapter.service.*;
import de.adorsys.xs2a.adapter.service.impl.AccountInformationServiceImpl;
import de.adorsys.xs2a.adapter.service.impl.DownloadServiceImpl;
import de.adorsys.xs2a.adapter.service.impl.PaymentInitiationServiceImpl;
import de.adorsys.xs2a.adapter.service.link.LinksRewriter;
import de.adorsys.xs2a.adapter.service.loader.AdapterDelegatingOauth2Service;
import de.adorsys.xs2a.adapter.service.loader.AdapterServiceLoader;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

@Configuration
public class RestConfiguration {

    @Value("${xs2a-adapter.loader.choose-first-from-multiple-aspsps:false}")
    private boolean chooseFirstFromMultipleAspsps;

    @Bean
    PaymentInitiationService paymentInitiationService(AdapterServiceLoader adapterServiceLoader) {
        return new PaymentInitiationServiceImpl(adapterServiceLoader);
    }

    @Bean
    AccountInformationService accountInformationService(AdapterServiceLoader adapterServiceLoader) {
        return new AccountInformationServiceImpl(adapterServiceLoader);
    }

    @Bean
    AdapterServiceLoader adapterServiceLoader(Pkcs12KeyStore keyStore,
                                              LinksRewriter accountInformationLinksRewriter,
                                              LinksRewriter paymentInitiationLinksRewriter,
                                              HttpClientFactory httpClientFactory) {
        return new AdapterServiceLoader(aspspRepository(), keyStore, httpClientFactory,
            accountInformationLinksRewriter, paymentInitiationLinksRewriter,
            chooseFirstFromMultipleAspsps);
    }

    @Bean
    LinksRewriter accountInformationLinksRewriter() {
        return new IdentityLinksRewriter();
    }

    @Bean
    LinksRewriter paymentInitiationLinksRewriter() {
        return new IdentityLinksRewriter();
    }

    @Bean
    HttpClientFactory httpClientFactory(HttpClientBuilder httpClientBuilder, Pkcs12KeyStore pkcs12KeyStore) {
        return new ApacheHttpClientFactory(httpClientBuilder, pkcs12KeyStore);
    }

    @Bean
    @Profile("!dev")
    HttpClientBuilder httpClientBuilder() {
        return httpClientBuilderWithSharedConfiguration();
    }

    @Bean
    @Profile("dev")
    HttpClientBuilder httpClientBuilderWithDisabledCompression() {
        return httpClientBuilderWithSharedConfiguration()
            .disableContentCompression();
    }

    private HttpClientBuilder httpClientBuilderWithSharedConfiguration() {
        return HttpClientBuilder.create()
            .disableDefaultUserAgent();
    }

    @Bean
    AspspRepository aspspRepository() {
        return new LuceneAspspRepositoryFactory().newLuceneAspspRepository();
    }

    @Bean
    Oauth2Service oauth2Service(AdapterServiceLoader adapterServiceLoader) {
        return new AdapterDelegatingOauth2Service(adapterServiceLoader);
    }

    @Bean
    Pkcs12KeyStore pkcs12KeyStore() throws CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException {
        char[] keyStorePassword = System.getProperty("pkcs12.keyStorePassword", "").toCharArray();
        return new Pkcs12KeyStore(System.getProperty("pkcs12.keyStore"), keyStorePassword);
    }

    @Bean
    DownloadService downloadService(AdapterServiceLoader adapterServiceLoader) {
        return new DownloadServiceImpl(adapterServiceLoader);
    }
}
