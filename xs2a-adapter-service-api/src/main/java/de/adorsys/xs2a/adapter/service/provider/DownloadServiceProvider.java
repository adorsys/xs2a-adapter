package de.adorsys.xs2a.adapter.service.provider;

import de.adorsys.xs2a.adapter.http.HttpClientFactory;
import de.adorsys.xs2a.adapter.service.DownloadService;
import de.adorsys.xs2a.adapter.service.Pkcs12KeyStore;

public interface DownloadServiceProvider extends AdapterServiceProvider {

    DownloadService getDownloadService(String baseUrl, HttpClientFactory httpClientFactory, Pkcs12KeyStore keyStore);
}
