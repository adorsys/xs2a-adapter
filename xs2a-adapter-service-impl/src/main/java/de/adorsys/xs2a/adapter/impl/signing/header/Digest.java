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
