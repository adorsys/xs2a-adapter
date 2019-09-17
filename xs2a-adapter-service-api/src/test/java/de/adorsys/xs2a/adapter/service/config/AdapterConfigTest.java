package de.adorsys.xs2a.adapter.service.config;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class AdapterConfigTest {

    @Test
    public void readProperty() {
        String property = AdapterConfig.readProperty("verlag.apikey.name");
        assertThat(property, is("X-bvpsd2-test-apikey"));
    }

    @Test
    public void externalConfigFile() {
        String file = getClass().getResource("/external.adapter.config.properties").getFile();
        System.setProperty("adapter.config.file.path", file);

        AdapterConfig.reload();

        String property = AdapterConfig.readProperty("some-adapter-property");
        assertThat(property, is("test"));
    }
}
