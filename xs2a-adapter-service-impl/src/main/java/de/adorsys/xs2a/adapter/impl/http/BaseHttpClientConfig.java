package de.adorsys.xs2a.adapter.impl.http;

import de.adorsys.xs2a.adapter.api.Pkcs12KeyStore;
import de.adorsys.xs2a.adapter.api.http.HttpClientConfig;
import de.adorsys.xs2a.adapter.api.http.HttpClientFactory;
import de.adorsys.xs2a.adapter.api.http.HttpLogSanitizer;

public class BaseHttpClientConfig implements HttpClientConfig {

    private final HttpLogSanitizer logSanitizer;
    private final Pkcs12KeyStore keyStore;
    private final HttpClientFactory clientFactory;

    public BaseHttpClientConfig(HttpLogSanitizer logSanitizer, Pkcs12KeyStore keyStore, HttpClientFactory clientFactory) {
        this.logSanitizer = logSanitizer;
        this.keyStore = keyStore;
        this.clientFactory = clientFactory;
    }

    @Override
    public HttpLogSanitizer getLogSanitizer() {
        return logSanitizer;
    }

    @Override
    public Pkcs12KeyStore getKeyStore() {
        return keyStore;
    }

    @Override
    public HttpClientFactory getClientFactory() {
        return clientFactory;
    }
}
