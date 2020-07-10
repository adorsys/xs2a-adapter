package de.adorsys.xs2a.adapter.service.impl;

import de.adorsys.xs2a.adapter.service.RequestHeaders;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class UnicreditHeadersTest {

    @Test
    void addPsuIdTypeHeaderDefault() {

        Map<String, String> header = UnicreditHeaders.addPsuIdTypeHeader(new HashMap<>());

        assertThat(header).hasSize(1);
        assertThat(header).containsValues(UnicreditHeaders.DEFAULT_PSU_ID_TYPE);
    }

    @Test
    void addPsuIdTypeHeaderNotSupported() {

        HashMap<String, String> headers = new HashMap<>();
        headers.put(RequestHeaders.PSU_ID_TYPE, "UNSUPPORTED_PSU_ID_TYPE");
        Map<String, String> header = UnicreditHeaders.addPsuIdTypeHeader(headers);

        assertThat(header).hasSize(1);
        assertThat(header).containsValues(UnicreditHeaders.DEFAULT_PSU_ID_TYPE);
    }

    @Test
    void addPsuIdTypeHeaderGlobal() {

        HashMap<String, String> headers = new HashMap<>();
        headers.put(RequestHeaders.PSU_ID_TYPE, UnicreditHeaders.UCE_BANKING_GLOBAL);
        Map<String, String> header = UnicreditHeaders.addPsuIdTypeHeader(headers);

        assertThat(header).hasSize(1);
        assertThat(header).containsValues(UnicreditHeaders.UCE_BANKING_GLOBAL);
    }
}
