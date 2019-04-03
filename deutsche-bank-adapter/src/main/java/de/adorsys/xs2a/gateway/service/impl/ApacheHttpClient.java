package de.adorsys.xs2a.gateway.service.impl;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
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

        try (CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setSSLContext(sslContext)
                .build()) {

            HttpPost post = new HttpPost(uri);
            headers.forEach(post::addHeader);
            post.setEntity(new StringEntity(body, ContentType.APPLICATION_JSON));
            CloseableHttpResponse response = httpClient.execute(post);

            int statusCode = response.getStatusLine().getStatusCode();
            InputStream content = response.getEntity().getContent();

            return responseHandler.apply(statusCode)
                    .apply(content);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
