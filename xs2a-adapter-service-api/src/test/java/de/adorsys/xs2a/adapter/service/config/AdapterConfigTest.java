package de.adorsys.xs2a.adapter.service.config;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AdapterConfigTest {

    @Test
    public void readProperty() {
        String property = AdapterConfig.readProperty("adorsys.oauth_approach.header_name");
        assertThat(property).isEqualTo("X-OAUTH-PREFERRED");
    }

    @Test
    public void externalConfigFile() {
        String file = getClass().getResource("/external.adapter.config.properties").getFile();
        System.setProperty("adapter.config.file.path", file);

        AdapterConfig.reload();

        String property = AdapterConfig.readProperty("some-adapter-property");
        assertThat(property).isEqualTo("test");
    }
}
