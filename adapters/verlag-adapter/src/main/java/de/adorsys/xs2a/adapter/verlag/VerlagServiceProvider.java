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

package de.adorsys.xs2a.adapter.verlag;

import de.adorsys.xs2a.adapter.api.*;
import de.adorsys.xs2a.adapter.api.config.AdapterConfig;
import de.adorsys.xs2a.adapter.api.http.HttpClientFactory;
import de.adorsys.xs2a.adapter.api.link.LinksRewriter;
import de.adorsys.xs2a.adapter.api.model.Aspsp;
import de.adorsys.xs2a.adapter.impl.AbstractAdapterServiceProvider;
import de.adorsys.xs2a.adapter.impl.BaseDownloadService;

import java.util.AbstractMap;

public class VerlagServiceProvider extends AbstractAdapterServiceProvider implements DownloadServiceProvider {

    private static final String VERLAG_API_KEY_NAME = "verlag.apikey.name";
    private static final String VERLAG_API_KEY_VALUE = "verlag.apikey.value";
    static final String[] SUPPORTED_CIPHER_SUITES =
        {"TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384", "TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256"};

    private static final AbstractMap.SimpleImmutableEntry<String, String> apiKeyEntry;
    private final PsuIdTypeHeaderInterceptor psuIdTypeHeaderInterceptor = new PsuIdTypeHeaderInterceptor();

    static {
        String apiKeyName = AdapterConfig.readProperty(VERLAG_API_KEY_NAME, "");
        String apiKeyValue = AdapterConfig.readProperty(VERLAG_API_KEY_VALUE, "");
        apiKeyEntry = new AbstractMap.SimpleImmutableEntry<>(apiKeyName, apiKeyValue);
    }

    @Override
    public AccountInformationService getAccountInformationService(Aspsp aspsp,
                                                                  HttpClientFactory httpClientFactory,
                                                                  LinksRewriter linksRewriter) {
        return new VerlagAccountInformationService(aspsp,
                                                   apiKeyEntry,
                                                   httpClientFactory,
                                                   getInterceptors(aspsp, psuIdTypeHeaderInterceptor),
                                                   linksRewriter);
    }

    @Override
    public PaymentInitiationService getPaymentInitiationService(Aspsp aspsp,
                                                                HttpClientFactory httpClientFactory,
                                                                LinksRewriter linksRewriter) {
        return new VerlagPaymentInitiationService(aspsp,
                                                  apiKeyEntry,
                                                  httpClientFactory,
                                                  getInterceptors(aspsp, psuIdTypeHeaderInterceptor),
                                                  linksRewriter);
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
