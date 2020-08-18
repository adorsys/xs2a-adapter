package de.adorsys.xs2a.adapter.impl.http;

import de.adorsys.xs2a.adapter.http.HttpClient;
import de.adorsys.xs2a.adapter.http.Request;

public abstract class AbstractHttpClient implements HttpClient {
    protected static final String GET = "GET";
    protected static final String POST = "POST";
    protected static final String PUT = "PUT";
    protected static final String DELETE = "DELETE";

    @Override
    public Request.Builder get(String uri) {
        return new RequestBuilderImpl(this, GET, uri);
    }

    @Override
    public Request.Builder post(String uri) {
        return new RequestBuilderImpl(this, POST, uri);
    }

    @Override
    public Request.Builder put(String uri) {
        return new RequestBuilderImpl(this, PUT, uri);
    }

    @Override
    public Request.Builder delete(String uri) {
        return new RequestBuilderImpl(this, DELETE, uri);
    }
}
