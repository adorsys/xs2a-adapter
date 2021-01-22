package de.adorsys.xs2a.adapter.api.http;

import de.adorsys.xs2a.adapter.api.Pkcs12KeyStore;

/**
 * Container for passing necessary configuration into {@link HttpClientFactory} for creating and setting up an {@link HttpClient}.
 */
public interface HttpClientConfig {

    HttpLogSanitizer getLogSanitizer();

    /**
     * @return Key Store with Application Certificates
     */
    Pkcs12KeyStore getKeyStore();
}
