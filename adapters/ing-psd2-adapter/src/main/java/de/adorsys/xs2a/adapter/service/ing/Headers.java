package de.adorsys.xs2a.adapter.service.ing;

import de.adorsys.xs2a.adapter.service.exception.BadRequestException;

import java.util.Map;
import java.util.TreeMap;

class Headers {
    public static final String BEARER_ = "Bearer ";

    private Map<String, String> headers;

    Headers(Map<String, String> headers) {
        this.headers = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        this.headers.putAll(headers);
    }

    public String getAccessToken() {
        String authorization = headers.get("Authorization");
        if (authorization == null || !authorization.startsWith(BEARER_)) {
            throw new BadRequestException("Authorization header value must have type \"Bearer\"");
        }
        return authorization.substring(BEARER_.length());
    }
}
