package de.adorsys.xs2a.adapter.http;

import org.junit.jupiter.api.Test;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

public class ApacheUriBuilderTest {

    public static final URI baseUriWithQueryParam = URI.create("https://base.uri?param=param");
    private ApacheUriBuilder builder = new ApacheUriBuilder(baseUriWithQueryParam);

    @Test
    void renameQueryParam_newParameter() {
        UriBuilder actual = builder.renameQueryParam("param", "another");

        assertThat(actual.build().toString()).isEqualTo("https://base.uri?another=param");
    }

    @Test
    void renameQueryParam_sameParameter() {
        UriBuilder actual = builder.renameQueryParam("another", "param");

        assertThat(actual.build()).isEqualTo(baseUriWithQueryParam);
    }
}
