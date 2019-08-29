package de.adorsys.xs2a.adapter.http;

import java.util.Arrays;
import java.util.HashMap;
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

    public static String withQuery(String uri, String paramName, String paramValue) {
        if (paramName == null || paramName.isEmpty()
                || paramValue == null || paramValue.isEmpty()) {
            return uri;
        }

        return uri + "?" + paramName + "=" + paramValue;
    }

    public static Map<String, String> getQueryParamsFromUri(String uri) {
        Map<String, String> queryParams = new HashMap<>();

        if (!uri.contains("?")) {
            return queryParams;
        }

        String paramsString = uri.split("\\?")[1];
        String[] paramsWithValues = paramsString.split("&");

        for (String paramWithValueString : paramsWithValues) {
            String[] paramAndValue = paramWithValueString.split("=");
            queryParams.put(paramAndValue[0], paramAndValue[1]);
        }

        return queryParams;
    }
}
