package de.adorsys.xs2a.adapter.http;

import de.adorsys.xs2a.adapter.service.Response;
import de.adorsys.xs2a.adapter.service.ResponseHeaders;

import java.io.InputStream;

public interface HttpClient {

    Request.Builder get(String uri);

    Request.Builder post(String uri);

    Request.Builder put(String uri);

    Request.Builder delete(String uri);

    <T> Response<T> send(Request.Builder requestBuilder, ResponseHandler<T> responseHandler);

    @FunctionalInterface
    interface ResponseHandler<T> {
        T apply(int statusCode, InputStream responseBody, ResponseHeaders responseHeaders);
    }
}

