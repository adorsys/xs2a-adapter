package de.adorsys.xs2a.adapter.commerzbank.mapper;

import de.adorsys.xs2a.adapter.api.model.RemittanceInformationStructured;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class RemittanceInformationStructuredMapperTest {

    public static final String REFERENCE = "reference";
    private final RemittanceInformationStructuredMapper mapper
        = new TestRemittanceInformationStructuredMapper();

    @Test
    void map() {
        String actual = mapper.map(getRemittanceInformationStructured());

        assertThat(actual)
            .isNotBlank()
            .isEqualTo(REFERENCE);
    }

    private RemittanceInformationStructured getRemittanceInformationStructured() {
        RemittanceInformationStructured instance = new RemittanceInformationStructured();
        instance.setReference(REFERENCE);
        return instance;
    }

    private static class TestRemittanceInformationStructuredMapper implements RemittanceInformationStructuredMapper { }
}
