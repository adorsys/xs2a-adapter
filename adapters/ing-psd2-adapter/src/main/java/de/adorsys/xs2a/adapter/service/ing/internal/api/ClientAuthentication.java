package de.adorsys.xs2a.adapter.service.ing.internal.api;

import com.google.api.client.http.HttpContent;
import com.google.api.client.http.HttpExecuteInterceptor;
import com.google.api.client.http.HttpRequest;

import java.io.IOException;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.Signature;
import java.security.SignatureException;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Locale;

public class ClientAuthentication implements HttpExecuteInterceptor {

    // java.time.format.DateTimeFormatter.RFC_1123_DATE_TIME doesn't pad single digit day-of-month with a zero
    private static final DateTimeFormatter RFC_1123_DATE_TIME_FORMATTER =
        DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss O", Locale.US)
            .withZone(ZoneOffset.UTC);

    private final Signature signature;
    private final MessageDigest digest;
    private final String tppSignatureCertificate;
    private String keyId;
    private String accessToken;

    ClientAuthentication(Signature signature, MessageDigest digest, String tppSignatureCertificate,
                         String keyId, String accessToken) {
        this.signature = signature;
        this.digest = digest;
        this.tppSignatureCertificate = tppSignatureCertificate;
        this.keyId = keyId;
        this.accessToken = accessToken;
    }

    @Override
    public void intercept(HttpRequest request) throws IOException {
        String date = RFC_1123_DATE_TIME_FORMATTER.format(Instant.now());
        String digest = "SHA-256=" + base64(digest(request.getContent()));
        String signingString = "(request-target): " + requestTarget(request) + "\n"
            + "date: " + date + "\n"
            + "digest: " + digest;
        String signature = "keyId=\"" + keyId
            + "\",algorithm=\"rsa-sha256\",headers=\"(request-target) date digest\"," +
            "signature=\"" + base64(sign(signingString)) + "\"";

        if (accessToken == null) {
            request.getHeaders()
                .setAuthorization("Signature " + signature);
        } else {
            request.getHeaders()
                .set("Signature", signature)
                .setAuthorization("Bearer " + accessToken);
        }

        request.getHeaders()
            .setDate(date)
            .set("Digest", digest)
            .set("TPP-Signature-Certificate", tppSignatureCertificate);
    }

    private String base64(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }

    private byte[] digest(HttpContent content) throws IOException {
        if (content != null) {
            content.writeTo(new OutputStream() {
                @Override
                public void write(int b) throws IOException {
                    digest.update((byte) b);
                }

                @Override
                public void write(byte[] b, int off, int len) throws IOException {
                    digest.update(b, off, len);
                }
            });
        }
        return digest.digest();
    }

    private String requestTarget(HttpRequest request) {
        return request.getRequestMethod().toLowerCase() + " " + request.getUrl().toURL().getFile();
    }

    private byte[] sign(String signingString) {
        try {
            signature.update(signingString.getBytes());
            return signature.sign();
        } catch (SignatureException e) {
            throw new RuntimeException(e);
        }
    }
}
