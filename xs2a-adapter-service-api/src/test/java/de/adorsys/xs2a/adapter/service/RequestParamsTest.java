package de.adorsys.xs2a.adapter.service;

import org.junit.jupiter.api.Test;

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
        assertThrows(IllegalArgumentException.class, () -> RequestParams.fromMap(singletonMap("dateFrom", "asdf")));
    }

    @Test
    void dateToIsTypeChecked() {
        assertThrows(IllegalArgumentException.class, () -> RequestParams.fromMap(singletonMap("dateTo", "asdf")));
    }

    @Test
    void withBalanceIsTypeChecked() {
        assertThrows(IllegalArgumentException.class, () -> RequestParams.fromMap(singletonMap("withBalance", "asdf")));
    }

    @Test
    void deltaListIsTypeChecked() {
        assertThrows(IllegalArgumentException.class, () -> RequestParams.fromMap(singletonMap("deltaList", "asdf")));
    }
}
