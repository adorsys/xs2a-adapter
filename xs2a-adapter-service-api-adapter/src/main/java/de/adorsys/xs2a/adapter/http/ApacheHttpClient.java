package de.adorsys.xs2a.adapter.http;

import de.adorsys.xs2a.adapter.service.Response;
import de.adorsys.xs2a.adapter.service.ResponseHeaders;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.io.EmptyInputStream;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class ApacheHttpClient implements HttpClient {
    private static final String GET = "GET";
    private static final String POST = "POST";
    private static final String PUT = "PUT";
    private static final String DELETE = "DELETE";

    private final CloseableHttpClient httpClient;

    public ApacheHttpClient(CloseableHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    public Request.Builder get(String uri) {
        return new RequestBuilderImpl(this, GET, uri);
    }

    @Override
    public Request.Builder post(String uri) {
        return new RequestBuilderImpl(this, POST, uri);
    }

    @Override
    public Request.Builder put(String uri) {
        return new RequestBuilderImpl(this, PUT, uri);
    }

    @Override
    public Request.Builder delete(String uri) {
        return new RequestBuilderImpl(this, DELETE, uri);
    }

    @Override
    public <T> Response<T> send(Request.Builder requestBuilder, ResponseHandler<T> responseHandler) {
        switch (requestBuilder.method()) {
            case GET:
                return execute(new HttpGet(requestBuilder.uri()), requestBuilder.headers(), responseHandler);
            case POST:
                HttpPost post = new HttpPost(requestBuilder.uri());
                if (requestBuilder.jsonBody() != null) {
                    post.setEntity(new StringEntity(requestBuilder.jsonBody(), ContentType.APPLICATION_JSON));
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
                }
                return execute(post, requestBuilder.headers(), responseHandler);
            case PUT:
                HttpPut put = new HttpPut(requestBuilder.uri());
                put.setEntity(new StringEntity(requestBuilder.jsonBody(), ContentType.APPLICATION_JSON));
                return execute(put, requestBuilder.headers(), responseHandler);
            case DELETE:
                return execute(new HttpDelete(requestBuilder.uri()), requestBuilder.headers(), responseHandler);
            default:
                throw new UnsupportedOperationException(requestBuilder.method());
        }
    }

    private <T> Response<T> execute(HttpUriRequest request, Map<String, String> headers, ResponseHandler<T> responseHandler) {
        headers.forEach(request::addHeader);

        try (CloseableHttpResponse response = httpClient.execute(request)) {
            int statusCode = response.getStatusLine().getStatusCode();
            HttpEntity entity = response.getEntity();
            Map<String, String> responseHeadersMap = toHeadersMap(response.getAllHeaders());
            ResponseHeaders responseHeaders = ResponseHeaders.fromMap(responseHeadersMap);
            InputStream content = entity != null ? entity.getContent() : EmptyInputStream.INSTANCE;

            T responseBody = responseHandler.apply(statusCode, content, responseHeaders);
            return new Response<>(statusCode, responseBody, responseHeaders);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
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
