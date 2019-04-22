package de.adorsys.xs2a.gateway.http;

import de.adorsys.xs2a.gateway.service.GeneralResponse;
import de.adorsys.xs2a.gateway.service.ResponseHeaders;

import javax.net.ssl.SSLContext;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

public interface HttpClient {
    <T> GeneralResponse<T> post(String uri, String body, Map<String, String> headers, ResponseHandler<T> responseHandler);

    <T> GeneralResponse<T> post(String uri, Map<String, String> headers, ResponseHandler<T> responseHandler);

    <T> GeneralResponse<T> get(String uri, Map<String, String> headers, ResponseHandler<T> responseHandler);

    <T> GeneralResponse<T> put(String uri, String body, Map<String, String> headers, ResponseHandler<T> responseHandler);

    @FunctionalInterface
    interface ResponseHandler<T> {
        T apply(int statusCode, InputStream responseBody, ResponseHeaders responseHeaders);
    }

    static HttpClient newHttpClient() {
        try {
            return new ApacheHttpClient(SSLContext.getDefault());
        } catch (NoSuchAlgorithmException e) {
            throw new HttpClientException(e);
        }
    }
}

