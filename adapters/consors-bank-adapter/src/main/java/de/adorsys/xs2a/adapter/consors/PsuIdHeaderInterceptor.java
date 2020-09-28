package de.adorsys.xs2a.adapter.consors;

import de.adorsys.xs2a.adapter.api.RequestHeaders;
import de.adorsys.xs2a.adapter.api.http.Interceptor;
import de.adorsys.xs2a.adapter.api.http.Request;

public class PsuIdHeaderInterceptor implements Interceptor {
    private static final String QUOTES = "\"\"";

    @Override
    public Request.Builder preHandle(Request.Builder builder) {
        if (QUOTES.equals(builder.headers().get(RequestHeaders.PSU_ID))) {
            builder.headers().replace(RequestHeaders.PSU_ID, null);
        }
        return builder;
    }
}
