package de.adorsys.xs2a.gateway.service.impl;

import javax.net.ssl.SSLContext;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

interface HttpClient {
    <T> T post(String uri, String body, Map<String, String> headers, ResponseHandler<T> responseHandler);

    <T> T get(String uri, Map<String, String> headers, ResponseHandler<T> responseHandler);

    <T> T put(String uri, String body, Map<String, String> headers, ResponseHandler<T> responseHandler);

    @FunctionalInterface
    interface ResponseHandler<T> {
        BodyHandler<T> apply(int statusCode);
    }

    @FunctionalInterface
    interface BodyHandler<T> {
        T apply(InputStream responseBody);
    }

    static HttpClient newHttpClient() {
        try {
            return new ApacheHttpClient(SSLContext.getDefault());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}

