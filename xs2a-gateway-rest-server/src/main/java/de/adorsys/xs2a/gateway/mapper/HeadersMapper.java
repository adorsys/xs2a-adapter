package de.adorsys.xs2a.gateway.mapper;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class HeadersMapper {

    public HttpHeaders toHttpHeaders(Map<String, String> headersMap) {
        HttpHeaders httpHeaders = new HttpHeaders();
        headersMap.forEach(httpHeaders::add);
        return httpHeaders;
    }
}
