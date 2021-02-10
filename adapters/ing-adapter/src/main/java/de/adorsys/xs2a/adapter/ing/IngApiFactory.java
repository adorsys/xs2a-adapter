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
