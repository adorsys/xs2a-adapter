package de.adorsys.xs2a.gateway.adapter;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DeutscheBankAdapterProviderTest {

    private AdapterProvider provider;

    @Before
    public void setUp() {
        provider = new DeutscheBankAdapterProvider();
    }

    @Test
    public void getBankCode() {
        assertThat(provider.getBankCode()).isEqualTo("50010517");
    }

    @Test
    public void getManager() {
        AdapterManager manager = provider.getManager();
        assertThat(manager).isExactlyInstanceOf(DeutscheBankAdapterManager.class);
    }
}