package de.adorsys.xs2a.adapter.impl.http;

import de.adorsys.xs2a.adapter.api.Pkcs12KeyStore;
import de.adorsys.xs2a.adapter.api.http.HttpClientConfig;
import de.adorsys.xs2a.adapter.api.http.HttpLogSanitizer;

public class BaseHttpClientConfig implements HttpClientConfig {

    private final HttpLogSanitizer logSanitizer;
    private final Pkcs12KeyStore keyStore;
    private final String wiremockStandaloneUrl;

    public BaseHttpClientConfig(HttpLogSanitizer logSanitizer, Pkcs12KeyStore keyStore,
                                String wiremockStandaloneUrl) {
        this.logSanitizer = logSanitizer;
        this.keyStore = keyStore;
        this.wiremockStandaloneUrl = wiremockStandaloneUrl;
    }

    @Override
    public HttpLogSanitizer getLogSanitizer() {
        return logSanitizer;
    }

    @Override
    public Pkcs12KeyStore getKeyStore() {
        return keyStore;
    }

    public String getWiremockStandaloneUrl() {
        return wiremockStandaloneUrl;
    }
}
