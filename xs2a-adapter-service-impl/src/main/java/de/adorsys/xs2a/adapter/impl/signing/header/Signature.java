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

package de.adorsys.xs2a.adapter.impl.signing.header;

import de.adorsys.xs2a.adapter.api.exception.HttpRequestSigningException;
import de.adorsys.xs2a.adapter.impl.signing.algorithm.EncodingAlgorithm;
import de.adorsys.xs2a.adapter.impl.signing.algorithm.SigningAlgorithm;
import de.adorsys.xs2a.adapter.impl.signing.util.Constants;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class Signature {
    private final String headerValue;

    private Signature(String headerValue) {
        this.headerValue = headerValue;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getHeaderName() {
        return Constants.SIGNATURE_HEADER_NAME;
    }

    public String getHeaderValue() {
        return headerValue;
    }

    public static final class Builder {
        private String keyId;
        private SigningAlgorithm signingAlgorithm = SigningAlgorithm.SHA256_WITH_RSA;
        private EncodingAlgorithm encodingAlgorithm = EncodingAlgorithm.BASE64;
        private Charset charset = StandardCharsets.UTF_8;
        private Map<String, String> headersMap;
        private PrivateKey privateKey;

        private Builder() {
        }

        public Builder keyId(String keyId) {
            this.keyId = keyId;
            return this;
        }

        public Builder signingAlgorithm(SigningAlgorithm signingAlgorithm) {
            this.signingAlgorithm = signingAlgorithm;
            return this;
        }

        public Builder encodingAlgorithm(EncodingAlgorithm encodingAlgorithm) {
            this.encodingAlgorithm = encodingAlgorithm;
            return this;
        }

        public Builder charset(Charset charset) {
            this.charset = charset;
            return this;
        }

        public Builder headers(Map<String, String> headers) {
            this.headersMap = new HashMap<>(headers);
            return this;
        }

        public Builder privateKey(PrivateKey privateKey) {
            this.privateKey = privateKey;
            return this;
        }

        public Signature build() {
            if (privateKeyNotExist()) {
                throw new HttpRequestSigningException("Private key is missing: it is impossible to create a signature without such a key");
            }

            if (requiredAttributesNotExist()) {
                throw new HttpRequestSigningException(
                        String.format("Required Signature header attributes must be present. Current values [%s] = %s, [%s] = %s, [%s] = %s)",
                                      Constants.KEY_ID_ATTRIBUTE_NAME, keyId,
                                      Constants.ALGORITHM_ATTRIBUTE_NAME, signingAlgorithm,
                                      Constants.HEADERS_ATTRIBUTE_NAME, headersMap
                        )
                );
            }

            List<Entry<String, String>> headersEntries = getSortedHeaderEntries(headersMap);

            String headersAttributeValue = buildHeaderAttributeValue(headersEntries);

            String signingString = buildSigningString(headersEntries);
            byte[] signedData = signingAlgorithm.getSigningService().sign(privateKey, signingString, charset);
            String signatureAttributeValue = encodingAlgorithm.getEncodingService().encode(signedData);

            return new Signature(
                    buildSignatureHeader(keyId, signingAlgorithm.getAlgorithmName(), headersAttributeValue, signatureAttributeValue)
            );
        }

        private boolean privateKeyNotExist() {
            return privateKey == null;
        }

        private boolean requiredAttributesNotExist() {
            return keyId == null || keyId.isEmpty()
                           || signingAlgorithm == null
                           || headersMap == null || headersMap.isEmpty();
        }

        private List<Entry<String, String>> getSortedHeaderEntries(Map<String, String> headersMap) {
            // important, as the order must be strict
            return headersMap.entrySet().stream()
                           .sorted(Comparator.comparing(Entry::getKey))
                           .collect(Collectors.toList());
        }

        private String buildHeaderAttributeValue(List<Entry<String, String>> headersEntries) {
            return headersEntries.stream()
                           .map(Entry::getKey)
                           .map(String::toLowerCase)
                           .collect(Collectors.joining(Constants.SPACE_SEPARATOR));
        }

        private String buildSigningString(List<Entry<String, String>> headersEntries) {
            return headersEntries.stream()
                           .map(entry -> this.buildSigningLine(entry.getKey(), entry.getValue()))
                           .collect(Collectors.joining(Constants.LINE_BREAK_SEPARATOR));
        }

        private String buildSigningLine(String headerName, String headerValue) {
            return headerName.toLowerCase() + Constants.COLON_SEPARATOR + Constants.SPACE_SEPARATOR + headerValue;
        }

        private String buildSignatureHeader(String keyIdAttributeValue,
                                            String algorithmAttributeValue,
                                            String headersAttributeValue,
                                            String signatureAttributeValue) {
            return buildAttribute(Constants.KEY_ID_ATTRIBUTE_NAME, keyIdAttributeValue)
                           + Constants.COMMA_SEPARATOR
                           + buildAttribute(Constants.ALGORITHM_ATTRIBUTE_NAME, algorithmAttributeValue)
                           + Constants.COMMA_SEPARATOR
                           + buildAttribute(Constants.HEADERS_ATTRIBUTE_NAME, headersAttributeValue)
                           + Constants.COMMA_SEPARATOR
                           + buildAttribute(Constants.SIGNATURE_ATTRIBUTE_NAME, signatureAttributeValue);
        }

        private String buildAttribute(String attributeName, String attributeValue) {
            return attributeName
                           + Constants.EQUALS_SIGN_SEPARATOR
                           + Constants.QUOTE_SEPARATOR
                           + attributeValue
                           + Constants.QUOTE_SEPARATOR;
        }
    }
}
