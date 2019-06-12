package de.adorsys.xs2a.gateway.http;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class StringUri {

    public static String fromElements(String... elements) {
        return Arrays.stream(elements)
                .map(StringUri::trimUri)
                .collect(Collectors.joining("/"));
    }

    private static String trimUri(String str) {
        if (str == null || str.isEmpty()) {
            return "";
        }
        str = str.startsWith("/") ? str.substring(1) : str;
        return str.endsWith("/") ? str.substring(0, str.length() - 1) : str;
    }

    public static String withQuery(String uri, Map<String, String> requestParams) {
        if (requestParams.isEmpty()) {
            return uri;
        }

        String requestParamsString = requestParams.entrySet().stream()
                .map(entry -> entry.getKey() + "=" + entry.getValue())
                .collect(Collectors.joining("&", "?", ""));

        return uri + requestParamsString;
    }
}
