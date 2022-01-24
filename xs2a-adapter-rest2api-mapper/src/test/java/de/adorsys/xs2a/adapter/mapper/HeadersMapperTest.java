/*
 * Copyright 2018-2022 adorsys GmbH & Co KG
 *
 * This program is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or (at
 * your option) any later version. This program is distributed in the hope that
 * it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see https://www.gnu.org/licenses/.
 *
 * This project is also available under a separate commercial license. You can
 * contact us at psd2@adorsys.com.
 */

package de.adorsys.xs2a.adapter.mapper;

import de.adorsys.xs2a.adapter.api.ResponseHeaders;
import de.adorsys.xs2a.adapter.api.http.ContentType;
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
