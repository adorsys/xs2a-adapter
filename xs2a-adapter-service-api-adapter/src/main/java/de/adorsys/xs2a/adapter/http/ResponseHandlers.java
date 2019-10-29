package de.adorsys.xs2a.adapter.http;

import de.adorsys.xs2a.adapter.service.RequestHeaders;
import de.adorsys.xs2a.adapter.service.ResponseHeaders;
import de.adorsys.xs2a.adapter.service.exception.ErrorResponseException;
import de.adorsys.xs2a.adapter.service.exception.NotAcceptableException;
import de.adorsys.xs2a.adapter.service.model.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static de.adorsys.xs2a.adapter.http.ContentType.APPLICATION_JSON;

public class ResponseHandlers {
    private static final Pattern CHARSET_PATTERN = Pattern.compile("charset=([^;]+)");

    private static final JsonMapper jsonMapper = new JsonMapper();
    private static final Logger log = LoggerFactory.getLogger(ResponseHandlers.class);

    private ResponseHandlers() {
    }

    public static <T> HttpClient.ResponseHandler<T> jsonResponseHandler(Class<T> klass) {
        return (statusCode, responseBody, responseHeaders) -> {
            if (statusCode == 204) {
                return null;
            }

            String contentType = responseHeaders.getHeader(RequestHeaders.CONTENT_TYPE);

            if (contentType != null && !contentType.startsWith(APPLICATION_JSON)) {
                throw new NotAcceptableException(String.format(
                    "Content type %s is not acceptable, has to start with %s", contentType, APPLICATION_JSON));
            }

            if (statusCode == 200 || statusCode == 201) {
                return jsonMapper.readValue(responseBody, klass);
            }

            throw responseException(statusCode, new PushbackInputStream(responseBody), responseHeaders);
        };
    }

    private static ErrorResponseException responseException(int statusCode,
                                                            PushbackInputStream responseBody,
                                                            ResponseHeaders responseHeaders) {
        if (isEmpty(responseBody)) {
            return new ErrorResponseException(statusCode, responseHeaders);
        }
        String originalResponse = toString(responseBody, responseHeaders);
        ErrorResponse errorResponse = jsonMapper.readValue(originalResponse, ErrorResponse.class);
        return new ErrorResponseException(statusCode, responseHeaders, errorResponse, originalResponse);
    }

    private static boolean isEmpty(PushbackInputStream responseBody) {
        try {
            int nextByte = responseBody.read();
            if (nextByte == -1) {
                return true;
            }
            responseBody.unread(nextByte);
            return false;
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public static HttpClient.ResponseHandler<String> stringResponseHandler() {
        return (statusCode, responseBody, responseHeaders) -> {
            if (statusCode == 200) {
                return toString(responseBody, responseHeaders);
            }

            throw responseException(statusCode, new PushbackInputStream(responseBody), responseHeaders);
        };
    }

    public static HttpClient.ResponseHandler<byte[]> byteArrayResponseHandler() {
        return (statusCode, responseBody, responseHeaders) -> {
            if (statusCode == 200) {
                return toByteArray(responseBody);
            }

            throw responseException(statusCode, new PushbackInputStream(responseBody), responseHeaders);
        };
    }

    private static String toString(InputStream responseBody, ResponseHeaders responseHeaders) {
        String charset = StandardCharsets.UTF_8.name();
        String contentType = responseHeaders.getHeader(RequestHeaders.CONTENT_TYPE);
        if (contentType != null) {
            Matcher matcher = CHARSET_PATTERN.matcher(contentType);
            if (matcher.find()) {
                charset = matcher.group(1);
            }
        }

        log.debug("{} charset will be used for response body parsing", charset);

        try {
            return readResponseBodyAsByteArrayOutputStream(responseBody)
                       .toString(charset);
        } catch (UnsupportedEncodingException e) {
            throw new UncheckedIOException(e);
        }
    }

    private static byte[] toByteArray(InputStream responseBody) {
        return readResponseBodyAsByteArrayOutputStream(responseBody)
            .toByteArray();
    }

    private static ByteArrayOutputStream readResponseBodyAsByteArrayOutputStream(InputStream responseBody) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = responseBody.read(buffer)) != -1) {
                baos.write(buffer, 0, length);
            }
            return baos;
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
