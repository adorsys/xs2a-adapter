package de.adorsys.xs2a.adapter.impl.http;

import de.adorsys.xs2a.adapter.api.RequestHeaders;
import de.adorsys.xs2a.adapter.api.ResponseHeaders;
import de.adorsys.xs2a.adapter.api.exception.ErrorResponseException;
import de.adorsys.xs2a.adapter.api.exception.NotAcceptableException;
import de.adorsys.xs2a.adapter.api.exception.OAuthException;
import de.adorsys.xs2a.adapter.api.exception.Xs2aAdapterException;
import de.adorsys.xs2a.adapter.api.http.HttpClient;
import de.adorsys.xs2a.adapter.api.model.ErrorResponse;
import de.adorsys.xs2a.adapter.api.model.HrefType;
import de.adorsys.xs2a.adapter.api.model.TppMessage;
import de.adorsys.xs2a.adapter.api.model.TppMessageCategory;
import org.apache.commons.fileupload.MultipartStream;
import org.apache.commons.fileupload.ParameterParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static de.adorsys.xs2a.adapter.api.http.ContentType.*;

public class ResponseHandlers {
    private static final Pattern CHARSET_PATTERN = Pattern.compile("charset=([^;]+)");
    private static final ErrorResponse EMPTY_ERROR_RESPONSE = new ErrorResponse();

    private static final JsonMapper jsonMapper = new JacksonObjectMapper();
    private static final Logger log = LoggerFactory.getLogger(ResponseHandlers.class);
    private static final Xs2aHttpLogSanitizer logSanitizer = new Xs2aHttpLogSanitizer();

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

    public static <T> HttpClient.ResponseHandler<T> paymentInitiationResponseHandler(String scaOAuthUrl, Class<T> klass) {
        return consentCreationResponseHandler(scaOAuthUrl, klass);
    }

    public static <T> HttpClient.ResponseHandler<T> consentCreationResponseHandler(String scaOAuthUrl, Class<T> klass) {
        return (statusCode, responseBody, responseHeaders) -> {
            if (statusCode == 401 || statusCode == 403) {
                String contentType = responseHeaders.getHeader(RequestHeaders.CONTENT_TYPE);
                PushbackInputStream pushbackResponseBody = new PushbackInputStream(responseBody);

                // this statement is needed as error response handling is different from the successful response
                if ((contentType == null || !contentType.startsWith(APPLICATION_JSON)) && isNotJson(pushbackResponseBody)) {
                    // needed to avoid org.springframework.http.converter.HttpMessageNotWritableException:
                    // No converter for [class de.adorsys.xs2a.adapter.api.model.ErrorResponse]
                    // with preset Content-Type 'application/xml;charset=UTF-8'
                    Map<String, String> headersMap = responseHeaders.getHeadersMap();
                    headersMap.put(ResponseHeaders.CONTENT_TYPE, APPLICATION_JSON);

                    throw oAuthException(pushbackResponseBody, ResponseHeaders.fromMap(headersMap), scaOAuthUrl,
                        ResponseHandlers::buildErrorResponseForOAuthNonJsonCase);
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
            originalResponse = logSanitizer.sanitize(originalResponse);
            errorResponse = errorResponseBuilder.apply(originalResponse);
        }

        if (scaOAuthUrl != null) {
            HrefType scaOAuth = new HrefType();
            scaOAuth.setHref(scaOAuthUrl);
            Map<String, HrefType> links = Collections.singletonMap("scaOAuth", scaOAuth);
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
            return true;
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
        originalResponse = logSanitizer.sanitize(originalResponse);
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
        tppMessage.setCategory(TppMessageCategory.ERROR);
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

    public static <T> HttpClient.ResponseHandler<T> multipartFormDataResponseHandler(Class<T> bodyClass) {
        return (statusCode, responseBody, responseHeaders) -> {
            if (statusCode != 200) {
                throw responseException(statusCode,
                    new PushbackInputStream(responseBody),
                    responseHeaders,
                    ResponseHandlers::buildErrorResponseFromString);
            }
            try {
                return parseResponseIntoObject(bodyClass, responseBody, responseHeaders);
            } catch (ReflectiveOperationException | IntrospectionException e) {
                throw new Xs2aAdapterException(e);
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        };
    }

    protected static <T> T parseResponseIntoObject(Class<T> bodyClass,
                                                   InputStream responseBody,
                                                   ResponseHeaders responseHeaders)
        throws ReflectiveOperationException, IOException, IntrospectionException {

        T bodyInstance = bodyClass.getDeclaredConstructor().newInstance();

        String contentType = responseHeaders.getHeader(RequestHeaders.CONTENT_TYPE);
        if (contentType == null || !contentType.startsWith(MULTIPART_FORM_DATA)) {
            throw new HttpClientException("Unexpected content type: " + contentType);
        }
        ParameterParser parser = new ParameterParser();
        Map<String, String> params = parser.parse(contentType, new char[] {';', ','});
        String boundary = params.get("boundary");
        if (boundary == null) {
            throw new HttpClientException("Failed to parse boundary from the Content-Type header");
        }
        MultipartStream multipartStream = new MultipartStream(responseBody, boundary.getBytes());
        boolean nextPart = multipartStream.skipPreamble();
        while (nextPart) {
            HttpHeaders headers = HttpHeaders.parse(multipartStream.readHeaders());
            String partContentType = headers.getContentType()
                .orElseThrow(() -> new HttpClientException("Body part has unspecified content type"));
            String partContentDisposition = headers.getContentDisposition()
                .orElseThrow(() -> new HttpClientException("Body part has unspecified content disposition"));
            String partName = parser.parse(partContentDisposition, ';').get("name");
            if (partName == null) {
                throw new HttpClientException("Body part has unspecified name");
            }
            PropertyDescriptor propertyDescriptor = new PropertyDescriptor(partName, bodyClass);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            multipartStream.readBodyData(out);
            if (partContentType.startsWith(APPLICATION_XML)) {
                propertyDescriptor.getWriteMethod().invoke(bodyInstance, out.toString());
            } else if (partContentType.startsWith(APPLICATION_JSON)) {
                Class<?> propertyType = propertyDescriptor.getPropertyType();
                Object json = jsonMapper.readValue(new ByteArrayInputStream(out.toByteArray()), propertyType);
                propertyDescriptor.getWriteMethod().invoke(bodyInstance, json);
            } else {
                Class<?> propertyType = propertyDescriptor.getPropertyType();
                Object json = jsonMapper.convertValue(out.toString(), propertyType);
                propertyDescriptor.getWriteMethod().invoke(bodyInstance, json);
            }

            nextPart = multipartStream.readBoundary();
        }
        return bodyInstance;
    }
}
