package de.adorsys.xs2a.gateway.http;

import de.adorsys.xs2a.gateway.signing.RequestSigningService;
import de.adorsys.xs2a.gateway.signing.exception.HttpRequestSigningException;
import de.adorsys.xs2a.gateway.signing.header.Digest;
import de.adorsys.xs2a.gateway.signing.header.Signature;
import de.adorsys.xs2a.gateway.signing.header.TppSignatureCertificate;
import org.apache.http.*;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static de.adorsys.xs2a.gateway.service.RequestHeaders.*;

public class RequestSigningInterceptor implements HttpRequestInterceptor {
    // according to BG spec 1.3 (chapter 12.2)
    private static final List<String> SIGNATURE_HEADERS
            = Arrays.asList(DIGEST, X_REQUEST_ID, PSU_ID, PSU_CORPORATE_ID, DATE, TPP_REDIRECT_URI);

    private final RequestSigningService requestSigningService = new RequestSigningService();

    @Override
    public void process(HttpRequest request, HttpContext context) {
        // Digest header computing and adding MUST BE BEFORE the Signature header, as Digest is used in Signature header computation
        populateDigest(request);
        populateSignature(request);
        populateTppSignatureCertificate(request);
    }

    private void populateDigest(HttpRequest request) {
        String requestBody = "";

        if (request instanceof HttpEntityEnclosingRequest) {
            HttpEntity requestEntity = ((HttpEntityEnclosingRequest) request).getEntity();
            try {
                requestBody = EntityUtils.toString(requestEntity);
            } catch (IOException e) {
                throw new HttpRequestSigningException("Exception during the request body reading: " + e);
            }
        }

        Digest digest = requestSigningService.buildDigest(requestBody);
        request.addHeader(digest.getHeaderName(), digest.getHeaderValue());
    }

    private void populateSignature(HttpRequest request) {
        Map<String, String> headersMap = SIGNATURE_HEADERS.stream()
                                              .map(request::getHeaders)
                                              .filter(headers -> headers.length > 0)
                                              .map(headers -> headers[0])
                                              .collect(Collectors.toMap(NameValuePair::getName, NameValuePair::getValue));

        Signature signature = requestSigningService.buildSignature(headersMap);
        request.addHeader(signature.getHeaderName(), signature.getHeaderValue());
    }

    private void populateTppSignatureCertificate(HttpRequest request) {
        TppSignatureCertificate tppSignatureCertificate = requestSigningService.buildTppSignatureCertificate();
        request.addHeader(tppSignatureCertificate.getHeaderName(), tppSignatureCertificate.getHeaderValue());
    }
}
