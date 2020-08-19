package de.adorsys.xs2a.adapter.api.http;

import de.adorsys.xs2a.adapter.api.Response;
import de.adorsys.xs2a.adapter.api.ResponseHeaders;

import java.io.InputStream;

public interface HttpClient {

    Request.Builder get(String uri);

    Request.Builder post(String uri);

    Request.Builder put(String uri);

    Request.Builder delete(String uri);

    <T> Response<T> send(Request.Builder requestBuilder, ResponseHandler<T> responseHandler);

    String content(Request.Builder requestBuilder);

    @FunctionalInterface
    interface ResponseHandler<T> {
        T apply(int statusCode, InputStream responseBody, ResponseHeaders responseHeaders);
    }
}
