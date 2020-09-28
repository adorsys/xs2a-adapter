package de.adorsys.xs2a.adapter.adorsys;

import de.adorsys.xs2a.adapter.api.RequestHeaders;
import de.adorsys.xs2a.adapter.api.config.AdapterConfig;
import de.adorsys.xs2a.adapter.api.http.Interceptor;
import de.adorsys.xs2a.adapter.api.http.Request;
import de.adorsys.xs2a.adapter.impl.http.RequestSigningInterceptor;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class AdorsysSigningHeadersInterceptor implements Interceptor {

    private final RequestSigningInterceptor signingInterceptor;
    private final boolean requestSigningEnabled =
        Boolean.parseBoolean(AdapterConfig.readProperty("adorsys.request_signing.enabled", "false"));

    public AdorsysSigningHeadersInterceptor(RequestSigningInterceptor signingInterceptor) {
        this.signingInterceptor = signingInterceptor;
    }

    @Override
    public Request.Builder preHandle(Request.Builder requestBuilder) {
        if (requestSigningEnabled) {
            setDateHeader(requestBuilder);
            setSignatureCertificateHeader(requestBuilder);
            return requestBuilder;
        }
        return requestBuilder;
    }

    private void setDateHeader(Request.Builder requestBuilder) {
        requestBuilder.header(RequestHeaders.DATE,
            DateTimeFormatter.RFC_1123_DATE_TIME.format(ZonedDateTime.now()));
    }

    private void setSignatureCertificateHeader(Request.Builder requestBuilder) {
        signingInterceptor.preHandle(requestBuilder);
        String certificate = requestBuilder.headers().get(RequestHeaders.TPP_SIGNATURE_CERTIFICATE);
        requestBuilder.header(RequestHeaders.TPP_SIGNATURE_CERTIFICATE,
            "-----BEGIN CERTIFICATE-----" + certificate + "-----END CERTIFICATE-----");
    }
}
