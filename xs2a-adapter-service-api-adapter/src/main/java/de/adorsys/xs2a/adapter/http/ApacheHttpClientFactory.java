package de.adorsys.xs2a.adapter.http;

import de.adorsys.xs2a.adapter.service.Pkcs12KeyStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.security.GeneralSecurityException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ApacheHttpClientFactory implements HttpClientFactory {

    private final HttpClientBuilder httpClientBuilder;
    private final Pkcs12KeyStore keyStore;
    private final ConcurrentMap<String, HttpClient> cache = new ConcurrentHashMap<>();

    public ApacheHttpClientFactory(HttpClientBuilder httpClientBuilder, Pkcs12KeyStore keyStore) {
        this.httpClientBuilder = httpClientBuilder;
        this.keyStore = keyStore;
    }

    @Override
    public HttpClient getHttpClient(String adapterId, String qwacAlias) {
        return cache.computeIfAbsent(adapterId, key -> createHttpClient(qwacAlias));
    }

    private HttpClient createHttpClient(String qwacAlias) {
        synchronized (this) {
            CloseableHttpClient httpClient;
            httpClientBuilder.setSSLContext(getSslContext(qwacAlias));
            httpClient = httpClientBuilder.build();
            return new ApacheHttpClient(httpClient);
        }
    }

    private SSLContext getSslContext(String qwacAlias) {
        try {
            if (qwacAlias == null) {
                return SSLContext.getDefault(); // fixme
            }
            return keyStore.getSslContext(qwacAlias);
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
