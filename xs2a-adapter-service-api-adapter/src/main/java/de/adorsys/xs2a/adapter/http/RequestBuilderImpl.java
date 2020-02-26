package de.adorsys.xs2a.adapter.http;

import de.adorsys.xs2a.adapter.service.Response;

import java.util.LinkedHashMap;
import java.util.Map;

public class RequestBuilderImpl implements Request.Builder {
    private final HttpClient httpClient;
    private String method;
    private String uri;
    private Map<String, String> headers = new LinkedHashMap<>();
    private String body;
    private boolean emptyBody;
    private Map<String, String> formData;

    public RequestBuilderImpl(HttpClient httpClient, String method, String uri) {
        this.httpClient = httpClient;
        this.method = method;
        this.uri = uri;
    }

    @Override
    public String method() {
        return method;
    }

    @Override
    public String uri() {
        return uri;
    }

    public Request.Builder uri(String uri) {
        this.uri = uri;
        return this;
    }

    @Override
    public Request.Builder headers(Map<String, String> headers) {
        this.headers = headers;
        return this;
    }

    @Override
    public Map<String, String> headers() {
        return headers;
    }

    @Override
    public Request.Builder header(String name, String value) {
        headers.put(name, value);
        return this;
    }

    @Override
    public Request.Builder jsonBody(String body) {
        this.body = body;
        return this;
    }

    @Override
    public String jsonBody() {
        return body;
    }

    @Override
    public Request.Builder emptyBody(boolean emptyBody) {
        this.emptyBody = emptyBody;
        return this;
    }

    @Override
    public boolean emptyBody() {
        return emptyBody;
    }

    @Override
    public Request.Builder urlEncodedBody(Map<String, String> formData) {
        this.formData = formData;
        return this;
    }

    @Override
    public Map<String, String> urlEncodedBody() {
        return formData;
    }

    @Override
    public <T> Response<T> send(Interceptor interceptor, HttpClient.ResponseHandler<T> responseHandler) {
        return httpClient.send(interceptor != null ? interceptor.apply(this) : this, responseHandler);
    }

    @Override
    public String content() {
        return httpClient.content(this);
    }
}
