package de.adorsys.xs2a.adapter.service.model;

import org.junit.jupiter.api.Test;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;

public class AspspTest {

    private final Aspsp aspsp = new Aspsp();

    @Test
    public void setIdTreatsEmptyAsNull() {
        aspsp.setId("");
        assertThat(aspsp.getId()).isNull();
    }

    @Test
    public void setNameTreatsEmptyAsNull() {
        aspsp.setName("");
        assertThat(aspsp.getName()).isNull();
    }

    @Test
    public void setBicTreatsEmptyAsNull() {
        aspsp.setBic("");
        assertThat(aspsp.getBic()).isNull();
    }

    @Test
    public void setBankCodeTreatsEmptyAsNull() {
        aspsp.setBankCode("");
        assertThat(aspsp.getBankCode()).isNull();
    }

    @Test
    public void setUrlTreatsEmptyAsNull() {
        aspsp.setUrl("");
        assertThat(aspsp.getUrl()).isNull();
    }

    @Test
    public void setAdapterId() {
        aspsp.setAdapterId("");
        assertThat(aspsp.getAdapterId()).isNull();
    }

    @Test
    public void setIdpUrl() {
        aspsp.setIdpUrl("");
        assertThat(aspsp.getIdpUrl()).isNull();
    }

    @Test
    public void setScaApproaches() {
        aspsp.setScaApproaches(emptyList());
        assertThat(aspsp.getScaApproaches()).isNull();
    }
}
