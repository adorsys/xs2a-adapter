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

package de.adorsys.xs2a.adapter.impl.http;

import de.adorsys.xs2a.adapter.api.http.HttpLogSanitizer;
import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Xs2aHttpLogSanitizer implements HttpLogSanitizer {
    private static final Logger logger = LoggerFactory.getLogger(Xs2aHttpLogSanitizer.class);
    private static final String APPLICATION_JSON = "application/json";
    static final String REPLACEMENT = "******";
    private final Set<String> sanitizeHeaders = new HashSet<>();
    private final Set<String> nonSanitizedBodyProperties = new HashSet<>();
    private final List<Pattern> patterns = new ArrayList<>();
    private final JsonMapper objectMapper = new JacksonObjectMapper();

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
        // To tackle DKB errors and Commerzbank Authorization URIs
        patterns.add(Pattern.compile("([a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12})"));

        List<String> headers = toLowerCase(Arrays.asList(
            "Authorization",
            "PSU-ID",
            "PSU-Corporate-ID",
            "Consent-ID",
            "X-GTW-IBAN",
            "Location",
            "Signature",
            "TPP-Signature-Certificate",
            "Digest",
            "X-dynatrace-Origin-URL", // Unicredit header
            "PSD2-AUTHORIZATION"));

        sanitizeHeaders.addAll(headers);
    }

    private List<String> toLowerCase(List<String> list) {
        return list.stream()
            .map(String::toLowerCase)
            .collect(Collectors.toList());
    }


    public String sanitizeHeader(String name, String value) {
        if (sanitizeHeaders.contains(name.toLowerCase())) {
            return REPLACEMENT;
        }
        return value;
    }

    public String sanitize(String data) {
        String replacedData = data;
        for (Pattern pattern : patterns) {
            Matcher matcher = pattern.matcher(replacedData);
            var groupCount = matcher.groupCount();
            if (matcher.find()) {
                if (2 == groupCount) {
                    replacedData = matcher.replaceAll("$1/" + REPLACEMENT + "$2");
                } else if (1 == groupCount) {
                    replacedData = matcher.replaceAll(REPLACEMENT);
                }
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
