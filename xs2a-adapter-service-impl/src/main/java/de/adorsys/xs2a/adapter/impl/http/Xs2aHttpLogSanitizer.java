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

package de.adorsys.xs2a.adapter.impl.http;

import de.adorsys.xs2a.adapter.api.http.HttpLogSanitizer;
import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Xs2aHttpLogSanitizer implements HttpLogSanitizer {
    private static final Logger logger = LoggerFactory.getLogger(Xs2aHttpLogSanitizer.class);
    private static final String APPLICATION_JSON = "application/json";
    static final String REPLACEMENT = "******";
    private final Set<String> sanitizedHeaders = new HashSet<>();
    private final Set<String> nonSanitizedBodyProperties = new HashSet<>();
    private final List<Pattern> patterns = new ArrayList<>();
    private JsonMapper objectMapper = new JacksonObjectMapper();

    /**
     * @param whitelist is a list of request/response body fields that a client wants to be unveiled in logs.
     *                  Empty list may be passed. Field names must conform Berlin Group specification and written
     *                  in camelCase, otherwise it will have no effect and Xs2aHttpLogSanitizer will still mask values.
     */
    public Xs2aHttpLogSanitizer(List<String> whitelist) {
        this();
        if (whitelist != null) {
            nonSanitizedBodyProperties.addAll(whitelist);
        }
    }

    public Xs2aHttpLogSanitizer() {
        patterns.add(Pattern.compile("(consents|accounts|authorisations|credit-transfers|target-2-payments)/[^/?\\s\\[\"]+(.*?)"));

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

    public String sanitizeRequestBody(Object httpEntity, String contentType) {
        if (contentType.startsWith(APPLICATION_JSON)) {
            try {
                return sanitizeStringifiedJsonBody(EntityUtils.toString((HttpEntity) httpEntity));
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
