package de.adorsys.xs2a.adapter.http;

import de.adorsys.xs2a.adapter.service.Response;
import org.apache.http.impl.client.CloseableHttpClient;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class ApacheHttpClientTest {

    public static final String URI = "https://test.uri";

    private CloseableHttpClient httpClient = mock(CloseableHttpClient.class);
    private ApacheHttpClient client = new ApacheHttpClient(httpClient);

    @Test
    void put() {
        Request.Builder actual = client.put(URI);

        assertEquals(actual.method(), "PUT");
        assertEquals(actual.uri(), URI);
    }

    @Test
    void delete() {
        Request.Builder actual = client.delete(URI);

        assertEquals(actual.method(), "DELETE");
        assertEquals(actual.uri(), URI);
    }

    @Test
    void content_getMethod(){
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
    void content_deleteMethod(){
        String actual = client.content(new RequestBuilderImpl(client, "DELETE", URI));

        assertNotNull(actual);
        assertTrue(actual.isEmpty());
    }

    @Test
    void content_throwsException() {
        Throwable exception
            = assertThrows(UnsupportedOperationException.class, () -> client.content(new RequestBuilderImpl(client, "METHOD", URI)));

        assertEquals(exception.getMessage(), "METHOD");
    }

    private Response<String> dummyResponse() {
        return new Response<>(-1, "body", null);
    }
}
