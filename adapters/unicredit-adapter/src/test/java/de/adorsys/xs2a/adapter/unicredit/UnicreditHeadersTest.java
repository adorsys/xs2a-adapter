package de.adorsys.xs2a.adapter.unicredit;

import de.adorsys.xs2a.adapter.api.RequestHeaders;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static de.adorsys.xs2a.adapter.unicredit.UnicreditHeaders.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UnicreditHeadersTest {

    @Test
    void addPsuIdTypeHeaderDefault() {

        Map<String, String> header = addPsuIdTypeHeader(new HashMap<>());

        assertThat(header).hasSize(1)
            .containsValues(DEFAULT_PSU_ID_TYPE);
    }

    @Test
    void addPsuIdTypeHeaderNotSupported() {

        HashMap<String, String> headers = new HashMap<>();
        headers.put(RequestHeaders.PSU_ID_TYPE, "UNSUPPORTED_PSU_ID_TYPE");
        Map<String, String> header = addPsuIdTypeHeader(headers);

        assertThat(header).hasSize(1)
            .containsValues(DEFAULT_PSU_ID_TYPE);
    }

    @Test
    void addPsuIdTypeHeaderGlobal() {

        HashMap<String, String> headers = new HashMap<>();
        headers.put(RequestHeaders.PSU_ID_TYPE, UCE_BANKING_GLOBAL);
        Map<String, String> header = addPsuIdTypeHeader(headers);

        assertThat(header).hasSize(1)
            .containsValues(UCE_BANKING_GLOBAL);
    }

    @Test
    void addPossibleValues() {
        assertThrows(UnsupportedOperationException.class, () -> POSSIBLE_PSU_ID_TYPE_VALUES.add("new-item"));
    }
}
