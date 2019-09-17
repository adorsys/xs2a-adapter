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
        System.setProperty("adapter.config.file.path","/Users/hiryu/projects/adorsys/xs2a-gateway/xs2a-adapter-service-api/src/test/resources/external.adapter.config.properties");
        AdapterConfig.reload();

        String property = AdapterConfig.readProperty("some-adapter-property");
        assertThat(property, is("test"));
    }
}
