package de.adorsys.xs2a.adapter.api.exception;

import javax.net.ssl.SSLHandshakeException;

public class UncheckedSSLHandshakeException extends RuntimeException {

    public UncheckedSSLHandshakeException(SSLHandshakeException e) {
        super(e);
    }
}
