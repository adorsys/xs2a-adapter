package de.adorsys.xs2a.adapter.consors;

import de.adorsys.xs2a.adapter.http.Request;
import de.adorsys.xs2a.adapter.service.RequestHeaders;

public class PsuIdHeaderInterceptor implements Request.Builder.Interceptor {
    private static final String QUOTES = "\"\"";

    @Override
    public Request.Builder apply(Request.Builder builder) {
        if (QUOTES.equals(builder.headers().get(RequestHeaders.PSU_ID))) {
            builder.headers().replace(RequestHeaders.PSU_ID, null);
        }

        return builder;
    }
}
