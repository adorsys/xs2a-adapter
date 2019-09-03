package de.adorsys.xs2a.adapter.http;

import de.adorsys.xs2a.adapter.service.Response;
import de.adorsys.xs2a.adapter.service.ResponseHeaders;
import de.adorsys.xs2a.adapter.service.exception.UncheckedSSLHandshakeException;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.*;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.io.EmptyInputStream;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLHandshakeException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

class ApacheHttpClient implements HttpClient {

    private SSLContext sslContext;
    private RequestSigningInterceptor requestSigningInterceptor;
    private UserAgentRemovingInterceptor userAgentRemovingInterceptor = new UserAgentRemovingInterceptor();

    ApacheHttpClient(SSLContext sslContext) {
        this.sslContext = sslContext;
    }

    ApacheHttpClient(SSLContext sslContext, RequestSigningInterceptor requestSigningInterceptor) {
        this.sslContext = sslContext;
        this.requestSigningInterceptor = requestSigningInterceptor;
    }

    @Override
    public <T> Response<T> post(String uri, String body, Map<String, String> headers, ResponseHandler<T> responseHandler) {
        HttpPost post = new HttpPost(uri);
        post.setEntity(new StringEntity(body, ContentType.APPLICATION_JSON));
        return execute(post, headers, responseHandler);
    }

    @Override
    public <T> Response<T> post(String uri, Map<String, String> headers, ResponseHandler<T> responseHandler) {
        HttpPost post = new HttpPost(uri);
        post.setEntity(new StringEntity("{}", ContentType.APPLICATION_JSON));
        return execute(post, headers, responseHandler);
    }

    @Override
    public <T> Response<T> get(String uri, Map<String, String> headers, ResponseHandler<T> responseHandler) {
        return execute(new HttpGet(uri), headers, responseHandler);
    }

    @Override
    public <T> Response<T> put(String uri, String body, Map<String, String> headers, ResponseHandler<T> responseHandler) {
        HttpPut put = new HttpPut(uri);
        put.setEntity(new StringEntity(body, ContentType.APPLICATION_JSON));
        return execute(put, headers, responseHandler);
    }

    @Override
    public <T> Response<T> delete(String uri, Map<String, String> headers, ResponseHandler<T> responseHandler) {
        return execute(new HttpDelete(uri), headers, responseHandler);
    }

    private <T> Response<T> execute(HttpUriRequest request, Map<String, String> headers, ResponseHandler<T> responseHandler) {
        headers.forEach(request::addHeader);

        try (CloseableHttpClient httpClient = createHttpClient()) {

            try (CloseableHttpResponse response = httpClient.execute(request)) {
                int statusCode = response.getStatusLine().getStatusCode();
                HttpEntity entity = response.getEntity();
                Map<String, String> responseHeadersMap = toHeadersMap(response.getAllHeaders());
                ResponseHeaders responseHeaders = ResponseHeaders.fromMap(responseHeadersMap);
                InputStream content = entity != null ? entity.getContent() : EmptyInputStream.INSTANCE;

                T responseBody = responseHandler.apply(statusCode, content, responseHeaders);
                return new Response<>(statusCode, responseBody, responseHeaders);
            }
        } catch (SSLHandshakeException e) {
            throw new UncheckedSSLHandshakeException(e);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private CloseableHttpClient createHttpClient() {
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create()
                                                  .setSSLContext(sslContext)
                                                  .addInterceptorLast(userAgentRemovingInterceptor);

        if (requestSigningInterceptor != null) {
            httpClientBuilder.addInterceptorFirst(requestSigningInterceptor);
        }

        return httpClientBuilder
                       .build();
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
