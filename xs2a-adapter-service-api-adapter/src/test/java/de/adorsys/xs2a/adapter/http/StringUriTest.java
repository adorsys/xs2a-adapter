package de.adorsys.xs2a.adapter.http;

import org.junit.Test;

import java.time.LocalDate;

import static java.util.Collections.singletonMap;
import static org.assertj.core.api.Assertions.assertThat;

public class StringUriTest {

    @Test
    public void fromElements() {
        String uri = StringUri.fromElements("/a", "b/", "/c/");
        assertThat(uri).isEqualTo("a/b/c");

        uri = StringUri.fromElements("/d/", "e", "/f/", "g/");
        assertThat(uri).isEqualTo("d/e/f/g");
    }

    @Test
    public void withQueryIgnoresParametersWithNullValues() {
        assertThat(StringUri.withQuery("http://example.com", singletonMap("q", null)))
            .isEqualTo("http://example.com");
    }

    @Test
    public void withQueryCallsToStringOnValues() {
        assertThat(StringUri.withQuery("http://example.com", singletonMap("q", LocalDate.of(2012, 12, 21))))
            .isEqualTo("http://example.com?q=2012-12-21");
    }
}
