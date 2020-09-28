package de.adorsys.xs2a.adapter.impl.http.wiremock;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class FlatMapUtilsTest {

    @Test
    void flatten() throws JsonProcessingException {
        String json = "{\"a\":1, \"b\":{\"b1\":2,\"b2\":3},\"c\":[{\"d\":4},5]}";
        Map<String, Object> actualMap = (Map<String, Object>) FlatMapUtils.flatten(new ObjectMapper().readValue(json, Map.class));
        assertThat(actualMap)
            .hasSize(5)
            .containsKeys("/a", "/b/b1", "/b/b2", "/c/0/d", "/c/1")
            .containsValues(1, 2, 3, 4, 5);
    }
}
