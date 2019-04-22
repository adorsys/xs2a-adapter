package de.adorsys.xs2a.gateway.service;

import java.util.*;

public class ResponseHeaders {
    private static final String LOCATION = "Location";
    private static final String X_REQUEST_ID = "X-Request-ID";
    private static final String ASPSP_SCA_APPROACH = "ASPSP-SCA-Approach";
    private static final String CONTENT_TYPE = "Content-Type";

    private static Map<String, String> headerNamesLowerCasedToBGSpecificationHeaderNames = new HashMap<>();
    static {
        headerNamesLowerCasedToBGSpecificationHeaderNames.put(LOCATION.toLowerCase(), LOCATION);
        headerNamesLowerCasedToBGSpecificationHeaderNames.put(X_REQUEST_ID.toLowerCase(), X_REQUEST_ID);
        headerNamesLowerCasedToBGSpecificationHeaderNames.put(ASPSP_SCA_APPROACH.toLowerCase(), ASPSP_SCA_APPROACH);
        headerNamesLowerCasedToBGSpecificationHeaderNames.put(CONTENT_TYPE.toLowerCase(), CONTENT_TYPE);
    }

    private Map<String, String> headers;

    private ResponseHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public static ResponseHeaders fromMap(Map<String, String> headersMap) {
        if (Objects.isNull(headersMap) || headersMap.isEmpty()) {
            return emptyResponseHeaders();
        }

        Map<String, String> headers = new HashMap<>();

        Set<String> headerNamesLowerCased = headerNamesLowerCasedToBGSpecificationHeaderNames.keySet();

        headersMap.forEach((name, value) -> {
            String headerNameInLowerCase = name.toLowerCase();
            if (isHeaderExistInBGSpecification(headerNamesLowerCased, headerNameInLowerCase)) {
                headers.put(headerNamesLowerCasedToBGSpecificationHeaderNames.get(headerNameInLowerCase), value);
            }
        });
        return new ResponseHeaders(headers);
    }

    public static ResponseHeaders emptyResponseHeaders() {
        return new ResponseHeaders(Collections.emptyMap());
    }

    private static boolean isHeaderExistInBGSpecification(Set<String> headerNames, String headerName) {
        return headerNames.contains(headerName);
    }

    public Map<String, String> getHeadersMap() {
        return new HashMap<>(headers);
    }

    public String getHeader(String headerName) {
        return headers.get(headerName);
    }
}
