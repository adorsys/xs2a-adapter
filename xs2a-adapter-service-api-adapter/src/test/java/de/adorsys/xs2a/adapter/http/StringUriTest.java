package de.adorsys.xs2a.adapter.http;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StringUriTest {

    @Test
    public void fromElements() {
        String uri = StringUri.fromElements("/a", "b/", "/c/");
        assertThat(uri).isEqualTo("a/b/c");

        uri = StringUri.fromElements("/d/","e","/f/","g/");
        assertThat(uri).isEqualTo("d/e/f/g");
    }
}
