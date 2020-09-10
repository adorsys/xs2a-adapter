package de.adorsys.xs2a.adapter.impl.http.wiremock;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.Map;

class FlatMapUtilsTest {

    @Test
    void flatten() throws JsonProcessingException {
        String json = "{\"a\":1, \"b\":{\"b1\":2,\"b2\":3},\"c\":[{\"d\":4},5]}";
        Map map = FlatMapUtils.flatten(new ObjectMapper().readValue(json, Map.class));
        System.out.println("map = " + map.keySet());
    }
}
