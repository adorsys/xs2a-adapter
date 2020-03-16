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

package de.adorsys.xs2a.adapter.http;

import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Xs2aHttpLogSanitizer {
    private static final Logger logger = LoggerFactory.getLogger(Xs2aHttpLogSanitizer.class);
    private static final String APPLICATION_JSON = "application/json";
    static final String REPLACEMENT = "******";
    private final Set<String> sanitizedHeaders = new HashSet<>();
    private final Set<String> nonSanitizedBodyProperties = new HashSet<>();
    private final List<Pattern> patterns = new ArrayList<>();
    private JsonMapper objectMapper = new JsonMapper();

    Xs2aHttpLogSanitizer() {
        patterns.add(Pattern.compile("(consents|accounts|authorisations)/[^/?\\s\\[\"]+(.*?)"));

        sanitizedHeaders.addAll(Arrays.asList(
            "Authorization",
            "PSU-ID",
            "PSU-Corporate-ID",
            "Consent-ID",
            "X-GTW-IBAN",
            "Location",
            "Signature",
            "TPP-Signature-Certificate",
            "Digest"));

        nonSanitizedBodyProperties.addAll(Arrays.asList(
            "recurringIndicator",
            "validUntil",
            "frequencyPerDay",
            "combinedServiceIndicator"));
    }

    public String sanitizeHeader(String name, String value) {
        if (sanitizedHeaders.contains(name)) {
            return REPLACEMENT;
        }
        return sanitize(value);
    }

    public String sanitize(String data) {
        String replacedData = data;
        for (Pattern pattern : patterns) {
            Matcher matcher = pattern.matcher(replacedData);
            if (matcher.find()) {
                replacedData = matcher.replaceAll("$1/" + REPLACEMENT + "$2");
            }
        }
        return replacedData;
    }

    public String sanitizeRequestBody(HttpEntity entity, String contentType) {
        if (contentType.startsWith(APPLICATION_JSON)) {
            try {
                return sanitizeStringifiedJsonBody(EntityUtils.toString(entity));
            } catch (Exception e) {
                logger.error("Can't parse request as json. It will be replaced with {}", REPLACEMENT);
            }

        }
        return REPLACEMENT;
    }

    public String sanitizeResponseBody(Object responseBody, String contentType) {
        if (contentType.startsWith(APPLICATION_JSON)) {
            try {
                String json;
                if (responseBody instanceof String) {
                    json = (String) responseBody;
                } else {
                    json = objectMapper.writeValueAsString(responseBody);
                }
                return sanitizeStringifiedJsonBody(json);
            } catch (Exception e) {
                logger.error("Can't parse response as json. It will be replaced with {}", REPLACEMENT);
            }

        }
        return REPLACEMENT;
    }

    private String sanitizeStringifiedJsonBody(String body) {
        Object responseMap = objectMapper.readValue(body, Object.class);
        sanitizeObject(responseMap);
        return objectMapper.writeValueAsString(responseMap);
    }

    private Map<String, Object> sanitizeMap(Map<String, Object> responseMap) {
        for (Map.Entry<String, Object> entry : responseMap.entrySet()) {
            if (!nonSanitizedBodyProperties.contains(entry.getKey())) {
                responseMap.replace(entry.getKey(), sanitizeObject(entry.getValue()));
            }
        }
        return responseMap;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private List sanitizeList(List list) {
        for (int i = 0; i < list.size(); i++) {
            list.set(i, sanitizeObject(list.get(i)));
        }
        return list;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private Object sanitizeObject(Object item) {
        if (item instanceof Map) {
            return sanitizeMap((Map<String, Object>) item);
        }
        if (item instanceof List) {
            return sanitizeList((List) item);
        }
        return REPLACEMENT;
    }
}
