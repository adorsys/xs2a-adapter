/*
 * Copyright 2018-2022 adorsys GmbH & Co KG
 *
 * This program is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or (at
 * your option) any later version. This program is distributed in the hope that
 * it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see https://www.gnu.org/licenses/.
 *
 * This project is also available under a separate commercial license. You can
 * contact us at psd2@adorsys.com.
 */

package de.adorsys.xs2a.adapter.impl.http;

import de.adorsys.xs2a.adapter.api.Pkcs12KeyStore;
import de.adorsys.xs2a.adapter.api.http.Interceptor;
import de.adorsys.xs2a.adapter.api.http.Request;
import de.adorsys.xs2a.adapter.impl.signing.RequestSigningService;
import de.adorsys.xs2a.adapter.impl.signing.header.Digest;
import de.adorsys.xs2a.adapter.impl.signing.header.Signature;
import de.adorsys.xs2a.adapter.impl.signing.header.TppSignatureCertificate;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static de.adorsys.xs2a.adapter.api.RequestHeaders.*;

public class RequestSigningInterceptor implements Interceptor {
    // according to BG spec 1.3 (chapter 12.2)
    // since BG spec 1.3.4 Date is not a mandatory field. A place for refactoring.
    private static final List<String> SIGNATURE_HEADERS
        = Arrays.asList(DIGEST, X_REQUEST_ID, PSU_ID, PSU_CORPORATE_ID, DATE, TPP_REDIRECT_URI);

    private final RequestSigningService requestSigningService;

    public RequestSigningInterceptor(Pkcs12KeyStore keyStore) {
        requestSigningService = new RequestSigningService(keyStore);
    }

    @Override
    public Request.Builder preHandle(Request.Builder requestBuilder) {
        // Digest header computing and adding MUST BE BEFORE the Signature header, as Digest is used in Signature header computation
        populateDigest(requestBuilder);
        populateSignature(requestBuilder);
        populateTppSignatureCertificate(requestBuilder);

        return requestBuilder;
    }

    private void populateDigest(Request.Builder requestBuilder) {
        String requestBody = requestBuilder.content();

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
