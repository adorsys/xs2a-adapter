package de.adorsys.xs2a.adapter.impl.http;

import java.net.URI;

public abstract class UriBuilder {

    public static UriBuilder fromUri(URI baseUri) {
        return new ApacheUriBuilder(baseUri);
    }

    public static UriBuilder fromUri(String baseUri) {
        return new ApacheUriBuilder(URI.create(baseUri));
    }

    public abstract UriBuilder queryParam(String name, String value);

    public abstract UriBuilder renameQueryParam(String currentName, String newName);

    public abstract URI build();
}
