package de.adorsys.xs2a.adapter.http;

import de.adorsys.xs2a.adapter.service.Pkcs12KeyStore;
import de.adorsys.xs2a.adapter.signing.RequestSigningService;
import de.adorsys.xs2a.adapter.signing.header.Digest;
import de.adorsys.xs2a.adapter.signing.header.Signature;
import de.adorsys.xs2a.adapter.signing.header.TppSignatureCertificate;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static de.adorsys.xs2a.adapter.service.RequestHeaders.*;

public class RequestSigningInterceptor implements Request.Builder.Interceptor {
    // according to BG spec 1.3 (chapter 12.2)
    private static final List<String> SIGNATURE_HEADERS
            = Arrays.asList(DIGEST, X_REQUEST_ID, PSU_ID, PSU_CORPORATE_ID, DATE, TPP_REDIRECT_URI);

    private final RequestSigningService requestSigningService;

    public RequestSigningInterceptor(Pkcs12KeyStore keyStore) {
        requestSigningService = new RequestSigningService(keyStore);
    }

    @Override
    public Request.Builder apply(Request.Builder requestBuilder) {
        // Digest header computing and adding MUST BE BEFORE the Signature header, as Digest is used in Signature header computation
        populateDigest(requestBuilder);
        populateSignature(requestBuilder);
        populateTppSignatureCertificate(requestBuilder);

        return requestBuilder;
    }

    private void populateDigest(Request.Builder requestBuilder) {
        String requestBody = requestBuilder.body();

        if (requestBody == null) {
            requestBody = "";
        }

        Digest digest = requestSigningService.buildDigest(requestBody);
        requestBuilder.header(digest.getHeaderName(), digest.getHeaderValue());
    }

    private void populateSignature(Request.Builder requestBuilder) {
        Map<String, String> headersMap = requestBuilder.headers().entrySet().stream()
            .filter(e -> SIGNATURE_HEADERS.contains(e.getKey()))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        Signature signature = requestSigningService.buildSignature(headersMap);
        requestBuilder.header(signature.getHeaderName(), signature.getHeaderValue());
    }

    private void populateTppSignatureCertificate(Request.Builder requestBuilder) {
        TppSignatureCertificate tppSignatureCertificate = requestSigningService.buildTppSignatureCertificate();
        requestBuilder.header(tppSignatureCertificate.getHeaderName(), tppSignatureCertificate.getHeaderValue());
    }
}
