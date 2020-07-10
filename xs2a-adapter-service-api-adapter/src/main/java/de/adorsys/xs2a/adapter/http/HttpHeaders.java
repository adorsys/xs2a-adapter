package de.adorsys.xs2a.adapter.http;

import org.apache.commons.lang3.StringEscapeUtils;

import java.util.*;

import static java.lang.String.CASE_INSENSITIVE_ORDER;

/**
 * @see <a href="https://tools.ietf.org/html/rfc7230>rfc7230</a>
 */
final class HttpHeaders {

    private static final String LF = "\n";

    private final Map<String, List<String>> headers;

    private HttpHeaders(Map<String, List<String>> headers) {
        this.headers = headers;
    }

    static HttpHeaders parse(String headers) {
        if (headers == null || headers.isEmpty()) {
            return new HttpHeaders(Collections.emptyMap());
        }

        TreeMap<String, List<String>> headersMap = new TreeMap<>(CASE_INSENSITIVE_ORDER);

        // HTTP-message = start-line *( header-field CRLF ) CRLF [ message-body ]

        // The line terminator for message-header fields is the sequence CRLF.
        // However, we recommend that applications, when parsing such headers,
        // recognize a single LF as a line terminator and ignore the leading CR.
        // https://www.w3.org/Protocols/rfc2616/rfc2616-sec19.html#sec19.3

        for (String header : headers.split(LF)) {
            if (header.trim().isEmpty()) {
                continue;
            }
            // header-field = field-name ":" OWS field-value OWS
            // OWS - optional whitespace
            String[] headerComponents = header.split(":");
            if (headerComponents.length != 2) {
                throw new IllegalArgumentException("Cannot parse \"" + escape(header) + "\" as a header");
            }
            String headerName = headerComponents[0];
            String headerValue = headerComponents[1].trim();
            headersMap.computeIfAbsent(headerName, k -> new ArrayList<>())
                .add(headerValue);
        }

        return new HttpHeaders(Collections.unmodifiableMap(headersMap));
    }

    @SuppressWarnings("deprecated")
    private static String escape(String s) {
        return StringEscapeUtils.escapeJava(s);
    }

    public int size() {
        return headers.size();
    }

    public Optional<String> getContentType() {
        // https://tools.ietf.org/html/rfc7231#section-3.1.1.5
        return getFirstValue("Content-Type");
    }

    private Optional<String> getFirstValue(String headerName) {
        return headers.get(headerName).stream().findFirst();
    }

    public Optional<String> getContentDisposition() {
        return getFirstValue("Content-Disposition");
    }
}
