package de.adorsys.xs2a.adapter.impl.signing.header;

import de.adorsys.xs2a.adapter.impl.signing.algorithm.EncodingAlgorithm;
import de.adorsys.xs2a.adapter.impl.signing.algorithm.HashingAlgorithm;
import de.adorsys.xs2a.adapter.impl.signing.util.Constants;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class Digest {
    private String headerValue;

    private Digest(String headerValue) {
        this.headerValue = headerValue;
    }

    public static DigestBuilder builder() {
        return new DigestBuilder();
    }

    public String getHeaderName() {
        return Constants.DIGEST_HEADER_NAME;
    }

    public String getHeaderValue() {
        return headerValue;
    }

    public static final class DigestBuilder {
        private String requestBody;
        private HashingAlgorithm hashingAlgorithm = HashingAlgorithm.SHA256;
        private EncodingAlgorithm encodingAlgorithm = EncodingAlgorithm.BASE64;
        private Charset charset = StandardCharsets.UTF_8;

        private DigestBuilder() {
        }

        public DigestBuilder requestBody(String requestBody) {
            this.requestBody = requestBody;
            return this;
        }

        public DigestBuilder hashingAlgorithm(HashingAlgorithm hashingAlgorithm) {
            this.hashingAlgorithm = hashingAlgorithm;
            return this;
        }

        public DigestBuilder encodingAlgorithm(EncodingAlgorithm encodingAlgorithm) {
            this.encodingAlgorithm = encodingAlgorithm;
            return this;
        }

        public DigestBuilder charset(Charset charset) {
            this.charset = charset;
            return this;
        }

        public Digest build() {
            byte[] digestBytes = hashingAlgorithm.getHashingService()
                                         .hash(requestBody, charset);

            String digestEncoded = encodingAlgorithm.getEncodingService()
                                           .encode(digestBytes);

            return new Digest(buildDigestHeader(hashingAlgorithm.getAlgorithmName(), digestEncoded));
        }

        private String buildDigestHeader(String algorithmName, String digestEncoded) {
            return algorithmName + Constants.EQUALS_SIGN_SEPARATOR + digestEncoded;
        }
    }
}
