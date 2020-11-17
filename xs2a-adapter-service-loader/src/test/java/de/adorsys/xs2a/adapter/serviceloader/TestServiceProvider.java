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

package de.adorsys.xs2a.adapter.serviceloader;

import de.adorsys.xs2a.adapter.api.*;
import de.adorsys.xs2a.adapter.api.http.HttpClientConfig;
import de.adorsys.xs2a.adapter.api.http.HttpClientFactory;
import de.adorsys.xs2a.adapter.api.link.LinksRewriter;
import de.adorsys.xs2a.adapter.api.model.Aspsp;

public class TestServiceProvider
    implements AccountInformationServiceProvider, PaymentInitiationServiceProvider, Oauth2ServiceProvider, DownloadServiceProvider, EmbeddedPreAuthorisationServiceProvider {

    @Override
    public AccountInformationService getAccountInformationService(Aspsp aspsp,
                                                                  HttpClientFactory httpClientFactory,
                                                                  Pkcs12KeyStore keyStore,
                                                                  LinksRewriter linksRewriter) {
        return new TestAccountInformationService();
    }

    @Override
    public AccountInformationService getAccountInformationService(Aspsp aspsp,
                                                                  LinksRewriter linksRewriter,
                                                                  HttpClientConfig httpClientConfig) {
        return new TestAccountInformationService();
    }

    @Override
    public PaymentInitiationService getPaymentInitiationService(Aspsp aspsp,
                                                                HttpClientFactory httpClientFactory,
                                                                Pkcs12KeyStore keyStore,
                                                                LinksRewriter linksRewriter) {
        return new TestPaymentInitiationService();
    }

    @Override
    public PaymentInitiationService getPaymentInitiationService(Aspsp aspsp,
                                                                HttpClientConfig clientConfig,
                                                                LinksRewriter linksRewriter) {
        return new TestPaymentInitiationService();
    }

    @Override
    public Oauth2Service getOauth2Service(Aspsp aspsp,
                                          HttpClientFactory httpClientFactory,
                                          Pkcs12KeyStore keyStore) {
        return new TestOauth2Service();
    }

    @Override
    public Oauth2Service getOauth2Service(Aspsp aspsp, HttpClientConfig httpClientConfig) {
        return new TestOauth2Service();
    }

    @Override
    public DownloadService getDownloadService(String baseUrl,
                                              HttpClientFactory httpClientFactory,
                                              Pkcs12KeyStore keyStore) {
        return new TestDownloadService();
    }

    @Override
    public DownloadService getDownloadService(String baseUrl, HttpClientConfig clientConfig) {
        return new TestDownloadService();
    }

    @Override
    public String getAdapterId() {
        return "test-adapter";
    }

    @Override
    public EmbeddedPreAuthorisationService getEmbeddedPreAuthorisationService(Aspsp aspsp,
                                                                              HttpClientFactory httpClientFactory) {
        return new TestEmbeddedPreAuthorisationService();
    }

    @Override
    public EmbeddedPreAuthorisationService getEmbeddedPreAuthorisationService(Aspsp aspsp,
                                                                              HttpClientConfig httpClientConfig) {
        return new TestEmbeddedPreAuthorisationService();
    }
}
