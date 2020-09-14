package de.adorsys.xs2a.adapter.consors;

import de.adorsys.xs2a.adapter.api.RequestHeaders;
import de.adorsys.xs2a.adapter.api.http.Request;

public class PsuIdHeaderInterceptor implements Request.Builder.Interceptor {
    private static final String QUOTES = "\"\"";

    @Override
    public void accept(Request.Builder builder) {
        if (QUOTES.equals(builder.headers().get(RequestHeaders.PSU_ID))) {
            builder.headers().replace(RequestHeaders.PSU_ID, null);
        }
    }
}
