/*
 * Copyright 2018-2022 adorsys GmbH & Co KG
 *
 * This program is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or (at
 * your option) any later version. This program is distributed in the hope that
 * it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see https://www.gnu.org/licenses/.
 *
 * This project is also available under a separate commercial license. You can
 * contact us at psd2@adorsys.com.
 */

package de.adorsys.xs2a.adapter.app.config;

import de.adorsys.xs2a.adapter.api.*;
import de.adorsys.xs2a.adapter.api.http.HttpClientConfig;
import de.adorsys.xs2a.adapter.api.http.HttpClientFactory;
import de.adorsys.xs2a.adapter.api.http.HttpLogSanitizer;
import de.adorsys.xs2a.adapter.api.link.LinksRewriter;
import de.adorsys.xs2a.adapter.impl.http.ApacheHttpClientFactory;
import de.adorsys.xs2a.adapter.impl.http.BaseHttpClientConfig;
import de.adorsys.xs2a.adapter.impl.http.Xs2aHttpLogSanitizer;
import de.adorsys.xs2a.adapter.impl.http.wiremock.WiremockHttpClientFactory;
import de.adorsys.xs2a.adapter.impl.link.identity.IdentityLinksRewriter;
import de.adorsys.xs2a.adapter.registry.LuceneAspspRepositoryFactory;
import de.adorsys.xs2a.adapter.serviceloader.*;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.List;

@Configuration
public class RestConfiguration {

    @Value("${xs2a-adapter.loader.choose-first-from-multiple-aspsps:false}")
    private boolean chooseFirstFromMultipleAspsps;

    @Value("${xs2a-adapter.wire-mock.mode:false}")
    private boolean wireMockEnabled;

    @Value("${xs2a-adapter.sanitizer.whitelist}")
    private List<String> sanitizerWhitelist;

    @Value("${xs2a-adapter.wire-mock.validation.enabled:false}")
    private boolean wiremockValidationEnabled;

    @Value("${xs2a-adapter.wire-mock.standalone.url:}")
    private String wiremockStandaloneUrl;

    @Bean
    HttpLogSanitizer xs2aHttpLogSanitizer() {
        return new Xs2aHttpLogSanitizer(sanitizerWhitelist);
    }

    @Bean
    HttpClientConfig httpClientConfig(HttpLogSanitizer logSanitizer, Pkcs12KeyStore keyStore) {
        return new BaseHttpClientConfig(logSanitizer, keyStore, wiremockStandaloneUrl);
    }

    @Bean
    PaymentInitiationService paymentInitiationService(AdapterServiceLoader adapterServiceLoader) {
        return new PaymentInitiationServiceImpl(adapterServiceLoader);
    }

    @Bean
    AccountInformationService accountInformationService(AdapterServiceLoader adapterServiceLoader) {
        return new AccountInformationServiceImpl(adapterServiceLoader);
    }

    @Bean
    AdapterServiceLoader adapterServiceLoader(LinksRewriter accountInformationLinksRewriter,
                                              LinksRewriter paymentInitiationLinksRewriter,
                                              HttpClientFactory httpClientFactory) {
        return new AdapterServiceLoader(aspspRepository(),
            httpClientFactory,
            accountInformationLinksRewriter,
            paymentInitiationLinksRewriter,
            chooseFirstFromMultipleAspsps,
            wiremockValidationEnabled);
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
    HttpClientFactory httpClientFactory(HttpClientBuilder httpClientBuilder, HttpClientConfig clientConfig) {
        return wireMockEnabled ? new WiremockHttpClientFactory(httpClientBuilder, clientConfig)
            : new ApacheHttpClientFactory(httpClientBuilder, clientConfig);
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

    @Bean
    EmbeddedPreAuthorisationService embeddedPreAuthorisationService(AdapterServiceLoader adapterServiceLoader) {
        return new EmbeddedPreAuthorisationServiceImpl(adapterServiceLoader);
    }
}
