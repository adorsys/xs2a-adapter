package de.adorsys.xs2a.adapter.api;

import de.adorsys.xs2a.adapter.api.http.HttpClientFactory;

public interface DownloadServiceProvider extends AdapterServiceProvider {

    DownloadService getDownloadService(String baseUrl, HttpClientFactory httpClientFactory);
}
