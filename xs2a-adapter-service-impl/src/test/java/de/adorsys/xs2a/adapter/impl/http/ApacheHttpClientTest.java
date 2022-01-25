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

package de.adorsys.xs2a.adapter.impl.http;

import de.adorsys.xs2a.adapter.api.Response;
import de.adorsys.xs2a.adapter.api.http.Request;
import org.apache.http.impl.client.CloseableHttpClient;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class ApacheHttpClientTest {

    public static final String URI = "https://test.uri";

    private CloseableHttpClient httpClient = mock(CloseableHttpClient.class);
    private ApacheHttpClient client = new ApacheHttpClient(null, httpClient);

    @Test
    void put() {
        Request.Builder actual = client.put(URI);

        assertEquals("PUT", actual.method());
        assertEquals(URI, actual.uri());
    }

    @Test
    void delete() {
        Request.Builder actual = client.delete(URI);

        assertEquals("DELETE", actual.method());
        assertEquals(URI, actual.uri());
    }

    @Test
    void content_getMethod() {
        String actual = client.content(new RequestBuilderImpl(client, "GET", URI));

        assertNotNull(actual);
        assertTrue(actual.isEmpty());
    }

    @Test
    void content_postMethod() {
        Request.Builder request = new RequestBuilderImpl(client, "POST", URI);
        request.emptyBody(true);

        String actual = client.content(request);

        assertNotNull(actual);
        assertEquals("{}", actual);
    }

    @Test
    void content_putMethod() {
        Request.Builder request = new RequestBuilderImpl(client, "PUT", URI);
        request.jsonBody("body");

        String actual = client.content(request);

        assertNotNull(actual);
        assertEquals("body", actual);
    }

    @Test
    void content_deleteMethod() {
        String actual = client.content(new RequestBuilderImpl(client, "DELETE", URI));

        assertNotNull(actual);
        assertTrue(actual.isEmpty());
    }

    @Test
    void content_throwsException() {
        RequestBuilderImpl requestBuilder = new RequestBuilderImpl(client, "METHOD", URI);
        UnsupportedOperationException exception = assertThrows(UnsupportedOperationException.class,
            () -> client.content(requestBuilder));

        assertEquals("METHOD", exception.getMessage());
    }

    private Response<String> dummyResponse() {
        return new Response<>(-1, "body", null);
    }
}
