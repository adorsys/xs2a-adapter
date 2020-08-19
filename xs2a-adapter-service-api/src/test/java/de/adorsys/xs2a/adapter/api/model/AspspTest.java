package de.adorsys.xs2a.adapter.api.model;

import org.junit.jupiter.api.Test;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;

class AspspTest {

    private final Aspsp aspsp = new Aspsp();

    @Test
    void setIdTreatsEmptyAsNull() {
        aspsp.setId("");
        assertThat(aspsp.getId()).isNull();
    }

    @Test
    void setNameTreatsEmptyAsNull() {
        aspsp.setName("");
        assertThat(aspsp.getName()).isNull();
    }

    @Test
    void setBicTreatsEmptyAsNull() {
        aspsp.setBic("");
        assertThat(aspsp.getBic()).isNull();
    }

    @Test
    void setBankCodeTreatsEmptyAsNull() {
        aspsp.setBankCode("");
        assertThat(aspsp.getBankCode()).isNull();
    }

    @Test
    void setUrlTreatsEmptyAsNull() {
        aspsp.setUrl("");
        assertThat(aspsp.getUrl()).isNull();
    }

    @Test
    void setAdapterId() {
        aspsp.setAdapterId("");
        assertThat(aspsp.getAdapterId()).isNull();
    }

    @Test
    void setIdpUrl() {
        aspsp.setIdpUrl("");
        assertThat(aspsp.getIdpUrl()).isNull();
    }

    @Test
    void setScaApproaches() {
        aspsp.setScaApproaches(emptyList());
        assertThat(aspsp.getScaApproaches()).isNull();
    }
}
