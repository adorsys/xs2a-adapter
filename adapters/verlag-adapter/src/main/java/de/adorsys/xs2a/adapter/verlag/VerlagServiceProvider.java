/*
 * Copyright 2018-2018 adorsys GmbH & Co KG
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.adorsys.xs2a.adapter.verlag;

import de.adorsys.xs2a.adapter.api.*;
import de.adorsys.xs2a.adapter.api.config.AdapterConfig;
import de.adorsys.xs2a.adapter.api.http.HttpClientFactory;
import de.adorsys.xs2a.adapter.api.link.LinksRewriter;
import de.adorsys.xs2a.adapter.api.model.Aspsp;
import de.adorsys.xs2a.adapter.impl.BaseDownloadService;

import java.util.AbstractMap;

public class VerlagServiceProvider
    implements AccountInformationServiceProvider, PaymentInitiationServiceProvider, DownloadServiceProvider {

    private static final String VERLAG_API_KEY_NAME = "verlag.apikey.name";
    private static final String VERLAG_API_KEY_VALUE = "verlag.apikey.value";
    private static final String[] SUPPORTED_CIPHER_SUITES =
        {"TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384", "TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256"};

    private static AbstractMap.SimpleImmutableEntry<String, String> apiKeyEntry;
    private static final PsuIdTypeHeaderInterceptor psuIdTypeHeaderInterceptor = new PsuIdTypeHeaderInterceptor();

    static {
        String apiKeyName = AdapterConfig.readProperty(VERLAG_API_KEY_NAME, "");
        String apiKeyValue = AdapterConfig.readProperty(VERLAG_API_KEY_VALUE, "");
        apiKeyEntry = new AbstractMap.SimpleImmutableEntry<>(apiKeyName, apiKeyValue);
    }

    @Override
    public AccountInformationService getAccountInformationService(Aspsp aspsp,
                                                                  HttpClientFactory httpClientFactory,
                                                                  Pkcs12KeyStore keyStore,
                                                                  LinksRewriter linksRewriter) {
        return new VerlagAccountInformationService(aspsp,
            apiKeyEntry,
            httpClientFactory.getHttpClient(getAdapterId(), null, SUPPORTED_CIPHER_SUITES),
            psuIdTypeHeaderInterceptor,
            linksRewriter,
            null);
    }

    @Override
    public AccountInformationService getAccountInformationService(Aspsp aspsp,
                                                                  HttpClientFactory httpClientFactory,
                                                                  LinksRewriter linksRewriter) {
        return new VerlagAccountInformationService(aspsp,
            apiKeyEntry,
            httpClientFactory.getHttpClient(getAdapterId(), null, SUPPORTED_CIPHER_SUITES),
            psuIdTypeHeaderInterceptor,
            linksRewriter,
            httpClientFactory.getHttpClientConfig().getLogSanitizer());
    }

    @Override
    public PaymentInitiationService getPaymentInitiationService(Aspsp aspsp,
                                                                HttpClientFactory httpClientFactory,
                                                                Pkcs12KeyStore keyStore,
                                                                LinksRewriter linksRewriter) {
        return new VerlagPaymentInitiationService(aspsp,
            apiKeyEntry,
            httpClientFactory.getHttpClient(getAdapterId(), null, SUPPORTED_CIPHER_SUITES),
            psuIdTypeHeaderInterceptor,
            linksRewriter,
            null);
    }

    @Override
    public PaymentInitiationService getPaymentInitiationService(Aspsp aspsp,
                                                                HttpClientFactory httpClientFactory,
                                                                LinksRewriter linksRewriter) {
        return new VerlagPaymentInitiationService(aspsp,
            apiKeyEntry,
            httpClientFactory.getHttpClient(getAdapterId(), null, SUPPORTED_CIPHER_SUITES),
            psuIdTypeHeaderInterceptor,
            linksRewriter,
            httpClientFactory.getHttpClientConfig().getLogSanitizer());
    }

    @Override
    public DownloadService getDownloadService(String baseUrl, HttpClientFactory httpClientFactory, Pkcs12KeyStore keyStore) {
        return new BaseDownloadService(baseUrl, httpClientFactory.getHttpClient(getAdapterId(), null, SUPPORTED_CIPHER_SUITES));
    }

    @Override
    public DownloadService getDownloadService(String baseUrl,
                                              HttpClientFactory httpClientFactory) {
        return new BaseDownloadService(baseUrl,
            httpClientFactory.getHttpClient(getAdapterId(), null, SUPPORTED_CIPHER_SUITES),
            httpClientFactory.getHttpClientConfig().getLogSanitizer());
    }

    @Override
    public String getAdapterId() {
        return "verlag-adapter";
    }
}
