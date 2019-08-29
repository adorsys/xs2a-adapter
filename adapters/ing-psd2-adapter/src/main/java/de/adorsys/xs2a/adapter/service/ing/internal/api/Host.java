package de.adorsys.xs2a.adapter.service.ing.internal.api;

import java.net.MalformedURLException;
import java.net.URL;

public class Host {
    private final URL host;

    public Host(String host) {
        try {
            this.host = new URL(host);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public String toString() {
        return host.toString();
    }
}
