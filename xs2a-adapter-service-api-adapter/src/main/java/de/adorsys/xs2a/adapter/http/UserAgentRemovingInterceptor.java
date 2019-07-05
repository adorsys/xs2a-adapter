package de.adorsys.xs2a.adapter.http;

import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.protocol.HttpContext;

public class UserAgentRemovingInterceptor implements HttpRequestInterceptor {
    private static final String USER_AGENT_HEADER = "User-Agent";

    @Override
    public void process(HttpRequest request, HttpContext context) {
        request.removeHeaders(USER_AGENT_HEADER);
    }
}
