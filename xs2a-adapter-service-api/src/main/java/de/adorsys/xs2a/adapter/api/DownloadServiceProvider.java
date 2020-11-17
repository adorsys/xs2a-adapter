package de.adorsys.xs2a.adapter.api;

import de.adorsys.xs2a.adapter.api.http.HttpClientConfig;
import de.adorsys.xs2a.adapter.api.http.HttpClientFactory;

public interface DownloadServiceProvider extends AdapterServiceProvider {
    @Deprecated
    DownloadService getDownloadService(String baseUrl, HttpClientFactory httpClientFactory, Pkcs12KeyStore keyStore);

    DownloadService getDownloadService(String baseUrl, HttpClientConfig clientConfig);
}
