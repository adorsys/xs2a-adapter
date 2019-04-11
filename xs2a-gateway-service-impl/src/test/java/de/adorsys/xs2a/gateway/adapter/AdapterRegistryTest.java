package de.adorsys.xs2a.gateway.adapter;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AdapterRegistryTest {

    @Test
    public void getAdapterManager() {
        AdapterRegistry registry = AdapterRegistry.getInstance();
        AdapterManager manager = registry.getAdapterManager("test");

        assertThat(manager).isNotNull();
    }

    @Test(expected = BankNotSupportedException.class)
    public void getAdapterManagerForUnsupportedBank() {
        AdapterRegistry.getInstance().getAdapterManager("unsupported-bank-code");
    }
}