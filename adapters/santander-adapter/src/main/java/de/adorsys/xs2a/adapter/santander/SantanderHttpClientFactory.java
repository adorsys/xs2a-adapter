package de.adorsys.xs2a.adapter.santander;

import de.adorsys.xs2a.adapter.api.Pkcs12KeyStore;
import de.adorsys.xs2a.adapter.api.http.HttpClient;
import de.adorsys.xs2a.adapter.api.http.HttpClientConfig;
import de.adorsys.xs2a.adapter.api.http.HttpClientFactory;

public class SantanderHttpClientFactory {

    private SantanderHttpClientFactory() {
    }

    public static HttpClient getHttpClient(String adapterId, HttpClientFactory httpClientFactory) {
        HttpClientConfig config = httpClientFactory.getHttpClientConfig();
        Pkcs12KeyStore keyStore = config.getKeyStore();
        String qwacAlias = SantanderCertificateAliasResolver.getCertificateAlias(keyStore); // null allowed, will load default certificate

        return httpClientFactory.getHttpClient(adapterId, qwacAlias);
    }
}
