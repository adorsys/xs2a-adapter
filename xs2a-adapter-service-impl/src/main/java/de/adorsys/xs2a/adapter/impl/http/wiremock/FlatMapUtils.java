/*
 * Copyright 2018-2018 adorsys GmbH & Co KG
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.adorsys.xs2a.adapter.impl.http.wiremock;

import java.util.AbstractMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class FlatMapUtils {
    private FlatMapUtils() {
        throw new AssertionError("No instances for you!");
    }

    public static Map<String, Object> flatten(Map<String, Object> map) {
        return map.entrySet()
                   .stream()
                   .flatMap(FlatMapUtils::flatten)
                   .collect(LinkedHashMap::new, (m, e) -> m.put("/" + e.getKey(), e.getValue()), LinkedHashMap::putAll);
    }

    private static Stream<Map.Entry<String, Object>> flatten(Map.Entry<String, Object> entry) {

        if (entry == null) {
            return Stream.empty();
        }

        if (entry.getValue() instanceof Map<?, ?>) {
            Map<?, ?> properties = (Map<?, ?>) entry.getValue();
            return properties.entrySet()
                       .stream()
                       .flatMap(e -> flatten(new AbstractMap.SimpleEntry<>(entry.getKey() + "/" + e.getKey(), e.getValue())));
        }

        if (entry.getValue() instanceof List<?>) {
            List<?> list = (List<?>) entry.getValue();
            return IntStream.range(0, list.size())
                       .mapToObj(i -> new AbstractMap.SimpleEntry<String, Object>(entry.getKey() + "/" + i, list.get(i)))
                       .flatMap(FlatMapUtils::flatten);
        }

        return Stream.of(entry);
    }

}
