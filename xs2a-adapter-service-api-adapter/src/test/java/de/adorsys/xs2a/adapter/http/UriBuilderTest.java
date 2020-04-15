package de.adorsys.xs2a.adapter.http;

import org.junit.jupiter.api.Test;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UriBuilderTest {

    @Test
    void queryParamIsNotAddedIfValueIsNull() {
        URI uri = UriBuilder.fromUri("http://acme.com/")
            .queryParam("q", null)
            .build();
        assertEquals("http://acme.com/", uri.toString());
    }

    @Test
    void stringUriMustBeValid() {
        assertThrows(IllegalArgumentException.class, () -> UriBuilder.fromUri("http://acme.com/?q=two words"));
    }

    @Test
    void whitespaceInQueryIsUrlFormEncoded() {
        URI uri = UriBuilder.fromUri("http://acme.com/")
            .queryParam("q", "two words")
            .build();
        assertEquals("q=two+words", uri.getQuery());
        assertEquals("q=two+words", uri.getRawQuery());
    }
}
