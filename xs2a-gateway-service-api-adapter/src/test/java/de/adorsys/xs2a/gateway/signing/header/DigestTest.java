package de.adorsys.xs2a.gateway.signing.header;

import de.adorsys.xs2a.gateway.service.RequestHeaders;
import de.adorsys.xs2a.gateway.signing.service.algorithm.EncodingAlgorithm;
import de.adorsys.xs2a.gateway.signing.service.algorithm.HashingAlgorithm;
import org.junit.Test;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

public class DigestTest {
    private static final String REQUEST_BODY = "{\"hello\": \"world\"}";
    private static final EncodingAlgorithm BASE64_ENCODING_ALGORITHM = EncodingAlgorithm.BASE64;
    private static final Charset UTF8_CHARSET = StandardCharsets.UTF_8;

    private static final HashingAlgorithm SHA256_HASHING_ALGORITHM = HashingAlgorithm.SHA256;
    private static final String EXPECTED_SHA256_DIGEST_VALUE = "SHA-256=X48E9qOokqqrvdts8nOJRJN3OWDUoyWxBf7kbu9DBPE=";

    @Test
    public void build() {
        Digest digest = Digest.builder()
                .requestBody(REQUEST_BODY)
                .hashingAlgorithm(SHA256_HASHING_ALGORITHM)
                .encodingAlgorithm(BASE64_ENCODING_ALGORITHM)
                .charset(UTF8_CHARSET)
                .build();

        assertThat(digest.getHeaderName()).isEqualTo(RequestHeaders.DIGEST);
        assertThat(digest.getHeaderValue()).isEqualTo(EXPECTED_SHA256_DIGEST_VALUE);
    }
}