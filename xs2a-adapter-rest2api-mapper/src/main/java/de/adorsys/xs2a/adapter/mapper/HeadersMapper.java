package de.adorsys.xs2a.adapter.mapper;

import de.adorsys.xs2a.adapter.service.ResponseHeaders;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Component
public class HeadersMapper {

    public HttpHeaders toHttpHeaders(ResponseHeaders responseHeaders) {
        HttpHeaders httpHeaders = new HttpHeaders();
        responseHeaders.getHeadersMap().forEach(httpHeaders::add);
        return httpHeaders;
    }
}
