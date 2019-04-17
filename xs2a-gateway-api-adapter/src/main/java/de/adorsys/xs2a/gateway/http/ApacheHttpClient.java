package de.adorsys.xs2a.gateway.http;

import org.apache.http.client.methods.*;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.Map;

class ApacheHttpClient implements HttpClient {

    private SSLContext sslContext;

    public ApacheHttpClient(SSLContext sslContext) {
        this.sslContext = sslContext;
    }

    @Override
    public <T> T post(String uri, String body, Map<String, String> headers, ResponseHandler<T> responseHandler) {
        HttpPost post = new HttpPost(uri);
        post.setEntity(new StringEntity(body, ContentType.APPLICATION_JSON));
        return execute(post, headers, responseHandler);
    }

    @Override
    public <T> T post(String uri, Map<String, String> headers, ResponseHandler<T> responseHandler) {
        return execute(new HttpPost(uri), headers, responseHandler);
    }

    @Override
    public <T> T get(String uri, Map<String, String> headers, ResponseHandler<T> responseHandler) {
        return execute(new HttpGet(uri), headers, responseHandler);
    }

    @Override
    public <T> T put(String uri, String body, Map<String, String> headers, ResponseHandler<T> responseHandler) {
        HttpPut put = new HttpPut(uri);
        put.setEntity(new StringEntity(body, ContentType.APPLICATION_JSON));
        return execute(put, headers, responseHandler);
    }

    private <T> T execute(HttpUriRequest request, Map<String, String> headers, ResponseHandler<T> responseHandler) {
        headers.forEach(request::addHeader);

        try (CloseableHttpClient httpClient = HttpClientBuilder.create()
                                                      .setSSLContext(sslContext)
                                                      .build()) {

            try (CloseableHttpResponse response = httpClient.execute(request)) {
                int statusCode = response.getStatusLine().getStatusCode();
                InputStream content = response.getEntity().getContent();

                return responseHandler.apply(statusCode, content);
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
