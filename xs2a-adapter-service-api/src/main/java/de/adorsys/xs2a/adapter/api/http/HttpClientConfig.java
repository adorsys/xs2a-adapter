package de.adorsys.xs2a.adapter.api.http;

import de.adorsys.xs2a.adapter.api.Pkcs12KeyStore;

public interface HttpClientConfig {

    HttpLogSanitizer getLogSanitizer();
    Pkcs12KeyStore getKeyStore();
    HttpClientFactory getClientFactory();
}
