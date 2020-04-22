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
    private BodyType bodyType;
    private Map<String, String> formData;

    private enum BodyType {
        JSON, EMPTY_JSON, XML
    }

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
        if (name != null && value != null) {
            headers.put(name, value);
        }
        return this;
    }

    @Override
    public Request.Builder jsonBody(String body) {
        this.body = body;
        bodyType = BodyType.JSON;
        return this;
    }

    @Override
    public boolean jsonBody() {
        return bodyType != null && bodyType == BodyType.JSON;
    }

    @Override
    public Request.Builder xmlBody(String body) {
        this.body = body;
        bodyType = BodyType.XML;
        return this;
    }

    @Override
    public boolean xmlBody() {
        return bodyType != null && bodyType == BodyType.XML;
    }

    @Override
    public String body() {
        return body;
    }

    @Override
    public Request.Builder emptyBody(boolean emptyBody) {
        if (emptyBody) {
            bodyType = BodyType.EMPTY_JSON;
        } else {
            bodyType = null;
        }
        return this;
    }

    @Override
    public boolean emptyBody() {
        return bodyType != null && bodyType == BodyType.EMPTY_JSON;
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
