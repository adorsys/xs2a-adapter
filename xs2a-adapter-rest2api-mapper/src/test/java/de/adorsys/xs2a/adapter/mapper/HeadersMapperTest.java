package de.adorsys.xs2a.adapter.mapper;

import de.adorsys.xs2a.adapter.http.ContentType;
import de.adorsys.xs2a.adapter.service.ResponseHeaders;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class HeadersMapperTest {

    private final HeadersMapper headersMapper = new HeadersMapper();
    private final ResponseHeaders responseHeaders = buildResponseHeaders();

    private static final String LOCATION = "Location";
    private static final String LOCATION_VALUE = "example.com";

    @Test
    void toHttpHeaders() {
        HttpHeaders actualHeaders = headersMapper.toHttpHeaders(responseHeaders);

        assertFalse(actualHeaders.isEmpty());
        assertTrue(actualHeaders.containsKey(ResponseHeaders.CONTENT_TYPE));
        assertEquals(MediaType.APPLICATION_JSON, actualHeaders.getContentType());
        assertTrue(actualHeaders.containsKey(LOCATION));
        assertEquals(URI.create(LOCATION_VALUE), actualHeaders.getLocation());
    }

    private ResponseHeaders buildResponseHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put(ResponseHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON);
        headers.put(LOCATION, LOCATION_VALUE);

        return ResponseHeaders.fromMap(headers);
    }
}
