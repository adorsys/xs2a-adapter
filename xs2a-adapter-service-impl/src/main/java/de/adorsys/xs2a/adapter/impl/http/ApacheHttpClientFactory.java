package de.adorsys.xs2a.adapter.impl.http;

import de.adorsys.xs2a.adapter.http.HttpClient;
import de.adorsys.xs2a.adapter.http.HttpClientFactory;
import de.adorsys.xs2a.adapter.service.Pkcs12KeyStore;
import de.adorsys.xs2a.adapter.service.exception.Xs2aAdapterException;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
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
    public HttpClient getHttpClient(String adapterId, String qwacAlias, String[] supportedCipherSuites) {
        return cache.computeIfAbsent(adapterId, key -> createHttpClient(qwacAlias, supportedCipherSuites));
    }

    private HttpClient createHttpClient(String qwacAlias, String[] supportedCipherSuites) {
        synchronized (this) {
            CloseableHttpClient httpClient;
            SSLContext sslContext = getSslContext(qwacAlias);
            SSLSocketFactory socketFactory = sslContext.getSocketFactory();
            SSLConnectionSocketFactory sslSocketFactory =
                new SSLConnectionSocketFactory(socketFactory, null, supportedCipherSuites, (HostnameVerifier) null);
            httpClientBuilder.setSSLSocketFactory(sslSocketFactory);
            httpClient = httpClientBuilder.build();
            return new ApacheHttpClient(httpClient);
        }
    }

    private SSLContext getSslContext(String qwacAlias) {
        try {
            return keyStore.getSslContext(qwacAlias);
        } catch (GeneralSecurityException e) {
            throw new Xs2aAdapterException(e);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
