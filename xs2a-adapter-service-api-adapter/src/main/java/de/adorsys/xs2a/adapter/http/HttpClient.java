package de.adorsys.xs2a.adapter.http;

import de.adorsys.xs2a.adapter.service.Response;
import de.adorsys.xs2a.adapter.service.ResponseHeaders;

import javax.net.ssl.SSLContext;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

public interface HttpClient {
    <T> Response<T> post(String uri, String body, Map<String, String> headers, ResponseHandler<T> responseHandler);

    <T> Response<T> post(String uri, Map<String, String> headers, ResponseHandler<T> responseHandler);

    <T> Response<T> postForm(String uri, Map<String, String> headers, Map<String, String> params, ResponseHandler<T> responseHandler);

    <T> Response<T> get(String uri, Map<String, String> headers, ResponseHandler<T> responseHandler);

    <T> Response<T> put(String uri, String body, Map<String, String> headers, ResponseHandler<T> responseHandler);

    <T> Response<T> delete(String uri, Map<String, String> headers, ResponseHandler<T> responseHandler);

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

    static HttpClient newHttpClientWithSignature(RequestSigningInterceptor requestSigningInterceptor) {
        try {
            return new ApacheHttpClient(SSLContext.getDefault(), requestSigningInterceptor);
        } catch (NoSuchAlgorithmException e) {
            throw new HttpClientException(e);
        }
    }
}

