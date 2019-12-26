package de.adorsys.xs2a.adapter.http;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public abstract class UriBuilder {

    public static UriBuilder fromUri(URI baseUri) {
        return new ApacheUriBuilder(baseUri);
    }

    public static UriBuilder fromUri(String baseUri) {
        try {
            URL url = new URL(baseUri);
            URI uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(),
                url.getPath(), url.getQuery(), url.getRef());
            return new ApacheUriBuilder(uri);
        } catch (MalformedURLException | URISyntaxException e) {
            throw new IllegalArgumentException(baseUri);
        }
    }

    public abstract UriBuilder queryParam(String name, String value);

    public abstract UriBuilder renameQueryParam(String currentName, String newName);

    public abstract URI build();
}
