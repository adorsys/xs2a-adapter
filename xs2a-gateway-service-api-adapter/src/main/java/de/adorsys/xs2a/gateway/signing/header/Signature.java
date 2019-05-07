package de.adorsys.xs2a.gateway.signing.header;

import de.adorsys.xs2a.gateway.signing.exception.HttpRequestSigningException;
import de.adorsys.xs2a.gateway.signing.service.algorithm.EncodingAlgorithm;
import de.adorsys.xs2a.gateway.signing.service.algorithm.SigningAlgorithm;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import static de.adorsys.xs2a.gateway.signing.util.Constants.*;

public class Signature {
    private String headerValue;

    private Signature(String headerValue) {
        this.headerValue = headerValue;
    }

    public static SignatureBuilder builder() {
        return new SignatureBuilder();
    }

    public String getHeaderName() {
        return SIGNATURE_HEADER_NAME;
    }

    public String getHeaderValue() {
        return headerValue;
    }

    public static final class SignatureBuilder {
        private String keyId;
        private SigningAlgorithm signingAlgorithm = SigningAlgorithm.SHA256_WITH_RSA;
        private EncodingAlgorithm encodingAlgorithm = EncodingAlgorithm.BASE64;
        private Charset charset = StandardCharsets.UTF_8;
        private Map<String, String> headersMap;
        private PrivateKey privateKey;

        private SignatureBuilder() {
        }

        public SignatureBuilder keyId(String keyId) {
            this.keyId = keyId;
            return this;
        }

        public SignatureBuilder signingAlgorithm(SigningAlgorithm signingAlgorithm) {
            this.signingAlgorithm = signingAlgorithm;
            return this;
        }

        public SignatureBuilder encodingAlgorithm(EncodingAlgorithm encodingAlgorithm) {
            this.encodingAlgorithm = encodingAlgorithm;
            return this;
        }

        public SignatureBuilder charset(Charset charset) {
            this.charset = charset;
            return this;
        }

        public SignatureBuilder headers(Map<String, String> headers) {
            this.headersMap = new HashMap<>(headers);
            return this;
        }

        public SignatureBuilder privateKey(PrivateKey privateKey) {
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
                                KEY_ID_ATTRIBUTE_NAME, keyId,
                                ALGORITHM_ATTRIBUTE_NAME, signingAlgorithm,
                                HEADERS_ATTRIBUTE_NAME, headersMap
                        )
                );
            }

            List<Entry<String, String>> headersEntries = getSortedHeaderEntries(headersMap);

            String headersAttributeValue = buildHeaderAttributeValue(headersEntries);

            String signingString = buildSigningString(headersEntries);
            byte[] signedData = signingAlgorithm.getSigningService().sign(privateKey, signingString, charset);
            String signingAttributeValue = encodingAlgorithm.getEncodingService().encode(signedData);

            return new Signature(
                    buildSignatureHeader(keyId, signingAlgorithm.getAlgorithmName(), headersAttributeValue, signingAttributeValue)
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
                           .collect(Collectors.joining(SPACE_SEPARATOR));
        }

        private String buildSigningString(List<Entry<String, String>> headersEntries) {
            return headersEntries.stream()
                           .map(entry -> this.buildSigningLine(entry.getKey(), entry.getValue()))
                           .collect(Collectors.joining(LINE_BREAK_SEPARATOR));
        }

        private String buildSigningLine(String headerName, String headerValue) {
            return headerName.toLowerCase() + COLON_SEPARATOR + SPACE_SEPARATOR + headerValue;
        }

        private String buildSignatureHeader(String keyIdAttributeValue,
                                            String algorithmAttributeValue,
                                            String headersAttributeValue,
                                            String signingAttributeValue) {
            return buildAttribute(KEY_ID_ATTRIBUTE_NAME, keyIdAttributeValue)
                           + COMMA_SEPARATOR
                           + buildAttribute(ALGORITHM_ATTRIBUTE_NAME, algorithmAttributeValue)
                           + COMMA_SEPARATOR
                           + buildAttribute(HEADERS_ATTRIBUTE_NAME, headersAttributeValue)
                           + COMMA_SEPARATOR
                           + buildAttribute(SIGNATURE_ATTRIBUTE_NAME, signingAttributeValue);
        }

        private String buildAttribute(String attributeName, String attributeValue) {
            return attributeName
                           + EQUALS_SIGN_SEPARATOR
                           + QUOTE_SEPARATOR
                           + attributeValue
                           + QUOTE_SEPARATOR;
        }
    }
}
