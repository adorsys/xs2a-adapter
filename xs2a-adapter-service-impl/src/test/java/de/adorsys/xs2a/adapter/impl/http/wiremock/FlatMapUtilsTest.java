/*
 * Copyright 2018-2022 adorsys GmbH & Co KG
 *
 * This program is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or (at
 * your option) any later version. This program is distributed in the hope that
 * it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see https://www.gnu.org/licenses/.
 *
 * This project is also available under a separate commercial license. You can
 * contact us at psd2@adorsys.com.
 */

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
