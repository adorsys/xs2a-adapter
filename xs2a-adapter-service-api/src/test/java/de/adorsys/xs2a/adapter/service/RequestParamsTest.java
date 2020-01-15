package de.adorsys.xs2a.adapter.service;

import org.junit.jupiter.api.Test;

import static java.util.Collections.emptyMap;
import static org.assertj.core.api.Assertions.assertThat;

public class RequestParamsTest {

    @Test
    public void fromMap() {
        RequestParams requestParams = RequestParams.fromMap(emptyMap());

        assertThat(requestParams.toMap()).isEqualTo(emptyMap());
    }
}
