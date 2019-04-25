package de.adorsys.xs2a.gateway.adapter;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AbstractServiceTest {

    @Test
    public void buildUri() {
        String uri = AbstractService.buildUri("/a", "b/", "/c/");
        assertThat(uri).isEqualTo("a/b/c");

        uri = AbstractService.buildUri("/d/","e","/f/","g/");
        assertThat(uri).isEqualTo("d/e/f/g");
    }
}