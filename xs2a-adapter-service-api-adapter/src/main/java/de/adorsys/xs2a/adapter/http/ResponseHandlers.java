package de.adorsys.xs2a.adapter.http;

import de.adorsys.xs2a.adapter.service.RequestHeaders;
import de.adorsys.xs2a.adapter.service.ResponseHeaders;
import de.adorsys.xs2a.adapter.service.exception.ErrorResponseException;
import de.adorsys.xs2a.adapter.service.exception.NotAcceptableException;
import de.adorsys.xs2a.adapter.service.exception.OAuthException;
import de.adorsys.xs2a.adapter.service.model.ErrorResponse;
import de.adorsys.xs2a.adapter.service.model.Link;
import de.adorsys.xs2a.adapter.service.model.Links;
import de.adorsys.xs2a.adapter.service.model.TppMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static de.adorsys.xs2a.adapter.http.ContentType.APPLICATION_JSON;

public class ResponseHandlers {
    private static final Pattern CHARSET_PATTERN = Pattern.compile("charset=([^;]+)");
    private static final ErrorResponse EMPTY_ERROR_RESPONSE = new ErrorResponse();

    private static final JsonMapper jsonMapper = new JsonMapper();
    private static final Logger log = LoggerFactory.getLogger(ResponseHandlers.class);

    private ResponseHandlers() {
    }

    public static <T> HttpClient.ResponseHandler<T> jsonResponseHandler(Class<T> klass) {
        return (statusCode, responseBody, responseHeaders) -> {
            if (statusCode == 204) {
                return null;
            }

            PushbackInputStream pushbackResponseBody = new PushbackInputStream(responseBody);
            String contentType = responseHeaders.getHeader(RequestHeaders.CONTENT_TYPE);

            if (statusCode >= 400) {
                // this statement is needed as error response handling is different from the successful response
                if ((contentType == null || !contentType.startsWith(APPLICATION_JSON)) && isNotJson(pushbackResponseBody)) {
                        throw responseException(statusCode, pushbackResponseBody, responseHeaders,
                            ResponseHandlers::buildEmptyErrorResponse);
                }
                throw responseException(statusCode, pushbackResponseBody, responseHeaders,
                    ResponseHandlers::buildErrorResponseFromString);
            }

            if (contentType != null && !contentType.startsWith(APPLICATION_JSON)) {
                throw new NotAcceptableException(String.format(
                    "Content type %s is not acceptable, has to start with %s", contentType, APPLICATION_JSON));
            }

            switch (statusCode) {
                case 200:
                case 201:
                case 202:
                    return jsonMapper.readValue(responseBody, klass);
                default:
                    throw responseException(statusCode, pushbackResponseBody, responseHeaders,
                                            ResponseHandlers::buildErrorResponseFromString);
            }
        };
    }

    public static <T> HttpClient.ResponseHandler<T> consentCreationResponseHandler(String scaOAuthUrl, Class<T> klass) {
        return (statusCode, responseBody, responseHeaders) -> {
            if (statusCode == 401 || statusCode == 403) {
                String contentType = responseHeaders.getHeader(RequestHeaders.CONTENT_TYPE);
                PushbackInputStream pushbackResponseBody = new PushbackInputStream(responseBody);

                // this statement is needed as error response handling is different from the successful response
                if (contentType == null || !contentType.startsWith(APPLICATION_JSON)) {
                    if (isNotJson(pushbackResponseBody)) {
                        throw oAuthException(pushbackResponseBody, responseHeaders, scaOAuthUrl,
                            ResponseHandlers::buildErrorResponseForOAuthNonJsonCase);
                    }
                }

                throw oAuthException(pushbackResponseBody, responseHeaders, scaOAuthUrl,
                    ResponseHandlers::buildErrorResponseFromString);
            }

            return jsonResponseHandler(klass)
                       .apply(statusCode, responseBody, responseHeaders);
        };
    }

    private static OAuthException oAuthException(PushbackInputStream responseBody,
                                                 ResponseHeaders responseHeaders,
                                                 String scaOAuthUrl,
                                                 Function<String, ErrorResponse> errorResponseBuilder) {
        ErrorResponse errorResponse;
        String originalResponse = null;

        if (isEmpty(responseBody)) {
            errorResponse = new ErrorResponse();
        } else {
            originalResponse = toString(responseBody, responseHeaders);
            errorResponse = errorResponseBuilder.apply(originalResponse);
        }

        if (scaOAuthUrl != null) {
            Links links = new Links();
            links.setScaOAuth(new Link(scaOAuthUrl));
            errorResponse.setLinks(links);
        }

        return new OAuthException(responseHeaders, errorResponse, originalResponse);
    }

    private static boolean isNotJson(PushbackInputStream responseBody) {
        try {
            int data = responseBody.read();
            responseBody.unread(data);

            if (data != -1) {
                char firstChar = (char) data;
                // There are only 2 possible char options here: '{' (JSON) and '<' (XML and HTML).
                // ASCII code of '{' is 123 and ASCII code of '<' is 66,
                // so the scope of the byte is enough for this purpose.
                return firstChar != '{';
            }

            return true;
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private static ErrorResponseException responseException(int statusCode,
                                                            PushbackInputStream responseBody,
                                                            ResponseHeaders responseHeaders,
                                                            Function<String, ErrorResponse> errorResponseBuilder) {
        if (isEmpty(responseBody)) {
            return new ErrorResponseException(statusCode, responseHeaders);
        }
        String originalResponse = toString(responseBody, responseHeaders);
        ErrorResponse errorResponse = errorResponseBuilder.apply(originalResponse);
        return new ErrorResponseException(statusCode, responseHeaders, errorResponse, originalResponse);
    }

    private static ErrorResponse buildErrorResponseFromString(String originalResponse) {
        return jsonMapper.readValue(originalResponse, ErrorResponse.class);
    }

    private static ErrorResponse buildEmptyErrorResponse(String originalResponse) {
        return EMPTY_ERROR_RESPONSE;
    }

    private static ErrorResponse buildErrorResponseForOAuthNonJsonCase(String originalResponse) {
        ErrorResponse errorResponse = new ErrorResponse();

        TppMessage tppMessage = new TppMessage();
        tppMessage.setCategory("ERROR");
        tppMessage.setCode("UNAUTHORIZED");
        tppMessage.setText(originalResponse);

        errorResponse.setTppMessages(Collections.singletonList(tppMessage));

        return errorResponse;
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

            throw responseException(statusCode, new PushbackInputStream(responseBody), responseHeaders,
                ResponseHandlers::buildEmptyErrorResponse);
        };
    }

    public static HttpClient.ResponseHandler<byte[]> byteArrayResponseHandler() {
        return (statusCode, responseBody, responseHeaders) -> {
            switch (statusCode) {
                case 200:
                case 201:
                case 202:
                case 204:
                    return toByteArray(responseBody);
                default:
                    throw responseException(statusCode, new PushbackInputStream(responseBody), responseHeaders,
                                            ResponseHandlers::buildEmptyErrorResponse);
            }
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
