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

package de.adorsys.xs2a.adapter.ing;

import de.adorsys.xs2a.adapter.api.config.AdapterConfig;
import de.adorsys.xs2a.adapter.api.http.HttpClient;
import de.adorsys.xs2a.adapter.api.http.HttpClientConfig;
import de.adorsys.xs2a.adapter.api.http.HttpClientFactory;
import de.adorsys.xs2a.adapter.api.model.Aspsp;

public class IngApiFactory {

    private IngApiFactory() { }

    public static IngAccountInformationApi getAccountInformationApi(Aspsp aspsp, HttpClientFactory httpClientFactory) {
        HttpClient httpClient = httpClient(aspsp, httpClientFactory);
        HttpClientConfig httpClientConfig = httpClientFactory.getHttpClientConfig();
        return new IngAccountInformationApi(aspsp.getUrl(), httpClient, httpClientConfig.getLogSanitizer());
    }

    public static IngPaymentInitiationApi getPaymentInitiationApi(Aspsp aspsp, HttpClientFactory httpClientFactory) {
        HttpClient httpClient = httpClient(aspsp, httpClientFactory);
        HttpClientConfig httpClientConfig = httpClientFactory.getHttpClientConfig();
        return new IngPaymentInitiationApi(aspsp.getUrl(), httpClient, httpClientConfig.getLogSanitizer());
    }

    public static  IngOauth2Api getOAuth2Api(Aspsp aspsp, HttpClientFactory httpClientFactory) {
        String baseUrl = aspsp.getIdpUrl() != null ? aspsp.getIdpUrl() : aspsp.getUrl();
        HttpClient httpClient = httpClient(aspsp, httpClientFactory);
        HttpClientConfig httpClientConfig = httpClientFactory.getHttpClientConfig();
        return new IngOauth2Api(baseUrl, httpClient, httpClientConfig.getLogSanitizer());
    }

    private static HttpClient httpClient(Aspsp aspsp, HttpClientFactory httpClientFactory) {
        return httpClientFactory.getHttpClient(aspsp.getAdapterId(), AdapterConfig.readProperty("ing.qwac.alias"));
    }
}
