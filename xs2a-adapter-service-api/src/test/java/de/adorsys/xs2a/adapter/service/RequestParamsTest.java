package de.adorsys.xs2a.adapter.service;

import de.adorsys.xs2a.adapter.api.RequestParams;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static java.util.Collections.emptyMap;
import static java.util.Collections.singletonMap;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RequestParamsTest {

    @Test
    void fromMap() {
        RequestParams requestParams = RequestParams.fromMap(emptyMap());

        assertThat(requestParams.toMap()).isEqualTo(emptyMap());
    }

    @Test
    void dateFromIsTypeChecked() {
        Map<String, String> map = singletonMap("dateFrom", "asdf");

        assertThrows(IllegalArgumentException.class, () -> RequestParams.fromMap(map));
    }

    @Test
    void dateToIsTypeChecked() {
        Map<String, String> map = singletonMap("dateTo", "asdf");

        assertThrows(IllegalArgumentException.class, () -> RequestParams.fromMap(map));
    }

    @Test
    void withBalanceIsTypeChecked() {
        Map<String, String> map = singletonMap("withBalance", "asdf");

        assertThrows(IllegalArgumentException.class, () -> RequestParams.fromMap(map));
    }

    @Test
    void deltaListIsTypeChecked() {
        Map<String, String> map = singletonMap("deltaList", "asdf");

        assertThrows(IllegalArgumentException.class, () -> RequestParams.fromMap(map));
    }
}
