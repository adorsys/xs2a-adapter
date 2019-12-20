package de.adorsys.xs2a.adapter.http;

import org.apache.http.client.utils.URIBuilder;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;

import static java.nio.charset.StandardCharsets.UTF_8;

// intentionally package private
class ApacheUriBuilder extends UriBuilder {

    private final URIBuilder uriBuilder;

    ApacheUriBuilder(URI baseUri) {
        uriBuilder = new URIBuilder(baseUri);
    }

    @Override
    public UriBuilder queryParam(String name, String value) {
        if (name != null && value != null) {
            uriBuilder.setParameter(name, value);
        }
        return this;
    }

    @Override
    public URI build() {
        try {
            return rfc2396Encoded(uriBuilder.build());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private static URI rfc2396Encoded(URI uri) {
        try {
            String query = uri.getRawQuery() != null ? URLDecoder.decode(uri.getRawQuery(), UTF_8.name()) : null;
            return new URI(uri.getScheme(), uri.getUserInfo(), uri.getHost(), uri.getPort(),
                uri.getPath(), query, uri.getFragment());
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException(e);
        } catch (UnsupportedEncodingException e) {
            // Every implementation of the Java platform is required to support UTF-8
            // see java.nio.charset.Charset
            throw new RuntimeException(e);
        }
    }
}
