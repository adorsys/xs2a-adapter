package de.adorsys.xs2a.adapter.api;

import de.adorsys.xs2a.adapter.api.http.HttpClientFactory;
import de.adorsys.xs2a.adapter.api.http.HttpLogSanitizer;

public interface DownloadServiceProvider extends AdapterServiceProvider {

    DownloadService getDownloadService(String baseUrl, HttpClientFactory httpClientFactory, Pkcs12KeyStore keyStore, HttpLogSanitizer logSanitizer);
}
