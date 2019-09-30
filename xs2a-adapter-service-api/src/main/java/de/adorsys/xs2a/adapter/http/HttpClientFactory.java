package de.adorsys.xs2a.adapter.http;

public interface HttpClientFactory {
    HttpClient getHttpClient(String adapterId, String qwacAlias);

    default HttpClient getHttpClient(String adapterId) {
        return getHttpClient(adapterId, null);
    }
}
