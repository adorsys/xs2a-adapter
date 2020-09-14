package de.adorsys.xs2a.adapter.ing;

import de.adorsys.xs2a.adapter.api.RequestHeaders;
import de.adorsys.xs2a.adapter.api.exception.Xs2aAdapterException;
import de.adorsys.xs2a.adapter.api.http.Request;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.Signature;
import java.security.SignatureException;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Locale;

public class IngClientAuthentication implements Request.Builder.Interceptor {

    // java.time.format.DateTimeFormatter.RFC_1123_DATE_TIME doesn't pad single digit day-of-month with a zero
    private static final DateTimeFormatter RFC_1123_DATE_TIME_FORMATTER =
        DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss O", Locale.US)
            .withZone(ZoneOffset.UTC);

    private final Signature signature;
    private final MessageDigest digest;
    private final String tppSignatureCertificate;
    private final String keyId;
    private final String accessToken;

    IngClientAuthentication(Signature signature, MessageDigest digest, String tppSignatureCertificate,
                            String keyId, String accessToken) {
        this.signature = signature;
        this.digest = digest;
        this.tppSignatureCertificate = tppSignatureCertificate;
        this.keyId = keyId;
        this.accessToken = accessToken;
    }

    @Override
    public void accept(Request.Builder requestBuilder) {
        String xRequestId = requestBuilder.headers().get(RequestHeaders.X_REQUEST_ID);
        String date = RFC_1123_DATE_TIME_FORMATTER.format(Instant.now());
        String digestValue = "SHA-256=" + base64(digest(requestBuilder.content()));
        String signingString = "(request-target): " + requestTarget(requestBuilder) + "\n"
            + (xRequestId != null ? "x-request-id: " + xRequestId + "\n" : "")
            + "date: " + date + "\n"
            + "digest: " + digestValue;
        String signatureValue = "keyId=\"" + keyId
            + "\",algorithm=\"rsa-sha256\",headers=\"(request-target)"
            + (xRequestId != null ? " x-request-id" : "") + " date digest\"," +
            "signature=\"" + base64(sign(signingString)) + "\"";

        if (accessToken == null) {
            requestBuilder.header("Authorization", "Signature " + signatureValue);
        } else {
            requestBuilder
                .header("Signature", signatureValue)
                .header("Authorization", "Bearer " + accessToken);
        }

        requestBuilder
            .header("Date", date)
            .header("Digest", digestValue)
            .header("TPP-Signature-Certificate", tppSignatureCertificate);
    }

    private String base64(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }

    private byte[] digest(String content) {
        digest.update(content.getBytes());
        return digest.digest();
    }

    private String requestTarget(Request.Builder requestBuilder) {
        try {
            return requestBuilder.method().toLowerCase() + " " + new URL(requestBuilder.uri()).getFile();
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private byte[] sign(String signingString) {
        try {
            signature.update(signingString.getBytes());
            return signature.sign();
        } catch (SignatureException e) {
            throw new Xs2aAdapterException(e);
        }
    }
}
