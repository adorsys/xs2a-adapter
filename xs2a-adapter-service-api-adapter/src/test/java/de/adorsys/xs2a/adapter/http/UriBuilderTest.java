package de.adorsys.xs2a.adapter.http;

import org.junit.jupiter.api.Test;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UriBuilderTest {

    @Test
    void queryParamIsNotAddedIfValueIsNull() {
        URI uri = UriBuilder.fromUri("http://acme.com/")
            .queryParam("q", null)
            .build();
        assertEquals("http://acme.com/", uri.toString());
    }

    @Test
    void whitespaceInQueryIsPercentEncoded() {
        URI uri = UriBuilder.fromUri("http://acme.com/?q=two words").build();
        assertEquals("q=two words", uri.getQuery());
        assertEquals("q=two%20words", uri.getRawQuery());
    }

    @Test
    void whitespaceInQueryIsPercentEncoded2() {
        URI uri = UriBuilder.fromUri("http://acme.com/")
            .queryParam("q", "two words")
            .build();
        assertEquals("q=two words", uri.getQuery());
        assertEquals("q=two%20words", uri.getRawQuery());
    }
}
