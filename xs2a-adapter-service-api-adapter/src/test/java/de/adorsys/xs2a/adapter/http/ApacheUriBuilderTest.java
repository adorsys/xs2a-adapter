package de.adorsys.xs2a.adapter.http;

import org.junit.jupiter.api.Test;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

class ApacheUriBuilderTest {

    public static final URI baseUriWithQueryParam = URI.create("https://base.uri?param=param");
    private ApacheUriBuilder builder = new ApacheUriBuilder(baseUriWithQueryParam);

    @Test
    void renameQueryParam_newParameter() {
        UriBuilder actual = builder.renameQueryParam("param", "another");

        assertThat(actual.build()).hasToString("https://base.uri?another=param");
    }

    @Test
    void renameQueryParam_sameParameter() {
        UriBuilder actual = builder.renameQueryParam("another", "param");

        assertThat(actual.build()).isEqualTo(baseUriWithQueryParam);
    }

    @Test
    void queryParametersAreEncoded() {
        URI uri = new ApacheUriBuilder(URI.create("http://example.com"))
            .queryParam("uri", "https://acme.com?foo=bar&baz=asdf")
            .build();
        assertThat(uri).hasToString("http://example.com?uri=https%3A%2F%2Facme.com%3Ffoo%3Dbar%26baz%3Dasdf");
    }
}
