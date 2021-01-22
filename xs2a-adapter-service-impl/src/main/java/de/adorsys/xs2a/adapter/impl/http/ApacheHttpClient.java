package de.adorsys.xs2a.adapter.impl.http;

import de.adorsys.xs2a.adapter.api.Response;
import de.adorsys.xs2a.adapter.api.ResponseHeaders;
import de.adorsys.xs2a.adapter.api.http.HttpLogSanitizer;
import de.adorsys.xs2a.adapter.api.http.Request;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.io.EmptyInputStream;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.stream.Collectors;

public class ApacheHttpClient extends AbstractHttpClient {
    private static final Logger logger = LoggerFactory.getLogger(ApacheHttpClient.class);
    private static final HttpLogSanitizer DEFAULT_LOG_SANITIZER = new Xs2aHttpLogSanitizer();

    private final HttpLogSanitizer logSanitizer;
    private final CloseableHttpClient httpClient;

    public ApacheHttpClient(HttpLogSanitizer logSanitizer, CloseableHttpClient httpClient) {
        this.logSanitizer = getOrDefaultLogSanitizer(logSanitizer);
        this.httpClient = httpClient;
    }

    private HttpLogSanitizer getOrDefaultLogSanitizer(HttpLogSanitizer logSanitizer) {
        return Optional.ofNullable(logSanitizer).orElse(DEFAULT_LOG_SANITIZER);
    }

    @Override
    public <T> Response<T> send(Request.Builder requestBuilder, ResponseHandler<T> responseHandler) {
        return execute(createRequest(requestBuilder), requestBuilder.headers(), responseHandler);
    }

    private HttpUriRequest createRequest(Request.Builder requestBuilder) {
        switch (requestBuilder.method()) {
            case GET:
                return new HttpGet(requestBuilder.uri());
            case POST:
                HttpPost post = new HttpPost(requestBuilder.uri());
                if (requestBuilder.jsonBody()) {
                    post.setEntity(new StringEntity(requestBuilder.body(), ContentType.APPLICATION_JSON));
                } else if (requestBuilder.xmlBody()) {
                    post.setEntity(new StringEntity(requestBuilder.body(), ContentType.APPLICATION_XML));
                } else if (requestBuilder.emptyBody()) {
                    post.setEntity(new StringEntity("{}", ContentType.APPLICATION_JSON));
                } else if (requestBuilder.urlEncodedBody() != null) {
                    List<NameValuePair> list = requestBuilder.urlEncodedBody().entrySet().stream()
                        .map(entry -> new BasicNameValuePair(entry.getKey(), entry.getValue()))
                        .collect(Collectors.toList());
                    try {
                        post.setEntity(new UrlEncodedFormEntity(list));
                    } catch (UnsupportedEncodingException e) {
                        throw new UncheckedIOException(e);
                    }
                } else if (requestBuilder.multipartBody()) {
                    MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create()
                        // static boundary for equal requests in send and content methods
                        .setBoundary("KfIPVSuloXL9RBOp_4z784vc73PWKzOrr6Ps");
                    requestBuilder.xmlParts().forEach((name, value) ->
                        multipartEntityBuilder.addPart(name, new StringBody(value, ContentType.APPLICATION_XML)));
                    requestBuilder.jsonParts().forEach((name, value) ->
                        multipartEntityBuilder.addPart(name, new StringBody(value, ContentType.APPLICATION_JSON)));
                    requestBuilder.plainTextParts().forEach((name, value) ->
                        multipartEntityBuilder.addPart(name, new StringBody(value, ContentType.TEXT_PLAIN)));
                    post.setEntity(multipartEntityBuilder.build());
                }
                return post;
            case PUT:
                HttpPut put = new HttpPut(requestBuilder.uri());
                if (requestBuilder.jsonBody()) {
                    put.setEntity(new StringEntity(requestBuilder.body(), ContentType.APPLICATION_JSON));
                }
                return put;
            case DELETE:
                return new HttpDelete(requestBuilder.uri());
            default:
                throw new UnsupportedOperationException(requestBuilder.method());
        }
    }

    @Override
    public String content(Request.Builder requestBuilder) {
        return getContent(createRequest(requestBuilder));
    }

    private String getContent(HttpUriRequest request) {
        Optional<HttpEntity> requestEntity = getRequestEntity(request);
        try {
            return requestEntity.isPresent() ? EntityUtils.toString(requestEntity.get()) : "";
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private Optional<HttpEntity> getRequestEntity(HttpUriRequest request) {
        HttpEntity entity = null;

        if (request instanceof HttpEntityEnclosingRequest) {
            entity = ((HttpEntityEnclosingRequest) request).getEntity();
        }

        return Optional.ofNullable(entity);
    }

    private <T> Response<T> execute(
        HttpUriRequest request,
        Map<String, String> headers,
        ResponseHandler<T> responseHandler
    ) {
        headers.forEach(request::addHeader);
        logRequest(request, headers);
        try (CloseableHttpResponse response = httpClient.execute(request)) {
            int statusCode = response.getStatusLine().getStatusCode();
            HttpEntity entity = response.getEntity();
            Map<String, String> responseHeadersMap = toHeadersMap(response.getAllHeaders());
            ResponseHeaders responseHeaders = ResponseHeaders.fromMap(responseHeadersMap);
            InputStream content = entity != null ? entity.getContent() : EmptyInputStream.INSTANCE;

            T responseBody = responseHandler.apply(statusCode, content, responseHeaders);

            logResponse(responseBody, response, responseHeadersMap);
            return new Response<>(statusCode, responseBody, responseHeaders);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private <T> void logResponse(T responseBody, CloseableHttpResponse response, Map<String, String> headers) {
        StringBuilder responseLogs = new StringBuilder("\n");
        String direction = "\t<--";
        responseLogs.append(logHttpEvent(headers, response.getStatusLine().toString(), direction));
        HttpEntity entity = response.getEntity();
        String contentType = entity != null && entity.getContentType() != null ? entity.getContentType().getValue() : "";
        String sanitizedResponseBody = logSanitizer.sanitizeResponseBody(responseBody, contentType);
        responseLogs.append(String.format("%s Response body [%s]: %s%n", direction, contentType, sanitizedResponseBody));
        responseLogs.append(direction);
        logger.debug("{}", responseLogs);
    }

    private void logRequest(HttpUriRequest request, Map<String, String> headers) {
        StringBuilder requestLogs = new StringBuilder("\n");
        String direction = "\t-->";
        requestLogs.append(logHttpEvent(headers, request.getRequestLine().toString(), direction));

        Optional<HttpEntity> requestEntityOptional = getRequestEntity(request);

        if (requestEntityOptional.isPresent()) {
            HttpEntity entity = requestEntityOptional.get();
            String contentType = entity.getContentType() != null ? entity.getContentType().getValue() : "";
            String sanitizedResponseBody = logSanitizer.sanitizeRequestBody(entity, contentType);
            requestLogs.append(String.format("%s Request body [%s]: %s%n", direction, contentType, sanitizedResponseBody));
            requestLogs.append(direction);
        }
        logger.debug("{}", requestLogs);
    }

    private StringBuilder logHttpEvent(Map<String, String> headers, String httpLine, String direction) {
        StringBuilder logs = new StringBuilder();
        logs.append(String.format("%s %s%n", direction, logSanitizer.sanitize(httpLine)));
        headers.forEach((key, value) -> logs.append(String.format("%s %s: %s%n", direction, key, logSanitizer.sanitizeHeader(key, value))));
        logs.append(direction).append("\n");
        return logs;
    }

    private Map<String, String> toHeadersMap(Header[] headers) {
        if (Objects.isNull(headers)) {
            return new HashMap<>();
        }

        // Don't override this to Stream API until staying on JDK 8, as JDK 8 has an issue for such a case
        // https://stackoverflow.com/questions/40039649/why-does-collectors-tomap-report-value-instead-of-key-on-duplicate-key-error
        Map<String, String> headersMap = new HashMap<>();

        for (Header header : headers) {
            headersMap.put(header.getName(), header.getValue());
        }

        return headersMap;
    }
}
