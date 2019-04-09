package de.adorsys.xs2a.gateway.service.impl;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DeutscheBankObjectMapperTest {

    @Test
    public void configuration() {
        ObjectMapper mapper = new DeutscheBankObjectMapper();
        JsonInclude.Value inclusion = mapper.getSerializationConfig().getDefaultPropertyInclusion();

        assertThat(inclusion.getValueInclusion()).isEqualTo(JsonInclude.Include.NON_NULL);
    }
}