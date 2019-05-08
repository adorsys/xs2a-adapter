package de.adorsys.xs2a.gateway.signing.header;

import de.adorsys.xs2a.gateway.service.RequestHeaders;
import de.adorsys.xs2a.gateway.signing.service.algorithm.EncodingAlgorithm;
import de.adorsys.xs2a.gateway.signing.service.algorithm.HashingAlgorithm;
import de.adorsys.xs2a.gateway.signing.service.encoding.EncodingService;
import de.adorsys.xs2a.gateway.signing.service.hashing.HashingService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(PowerMockRunner.class)
@PrepareForTest({EncodingAlgorithm.class, HashingAlgorithm.class})
public class DigestTest {
    private static final String REQUEST_BODY = "{\"hello\": \"world\"}";
    private static final Charset UTF8_CHARSET = StandardCharsets.UTF_8;
    private static final byte[] HASHED_REQUEST_BODY = REQUEST_BODY.getBytes();

    private static final String HASHING_ALGORITHM_NAME = "SHA-256";
    private static final String DIGEST_VALUE = "X48E9qOokqqrvdts8nOJRJN3OWDUoyWxBf7kbu9DBPE=";
    private static final String EQUAL_SEPARATOR = "=";

    private static final String EXPECTED_DIGEST_HEADER_VALUE = HASHING_ALGORITHM_NAME + EQUAL_SEPARATOR + DIGEST_VALUE;

    @Mock
    private EncodingAlgorithm encodingAlgorithm;
    @Mock
    private EncodingService encodingService;
    @Mock
    private HashingAlgorithm hashingAlgorithm;
    @Mock
    private HashingService hashingService;

    @Test
    public void build() {
        PowerMockito.when(hashingAlgorithm.getHashingService())
                .thenReturn(hashingService);

        Mockito.when(hashingService.hash(REQUEST_BODY, UTF8_CHARSET))
                .thenReturn(HASHED_REQUEST_BODY);

        PowerMockito.when(encodingAlgorithm.getEncodingService())
                .thenReturn(encodingService);

        Mockito.when(encodingService.encode(HASHED_REQUEST_BODY))
                .thenReturn(DIGEST_VALUE);

        PowerMockito.when(hashingAlgorithm.getAlgorithmName())
                .thenReturn(HASHING_ALGORITHM_NAME);

        Digest digest = Digest.builder()
                .requestBody(REQUEST_BODY)
                .hashingAlgorithm(hashingAlgorithm)
                .encodingAlgorithm(encodingAlgorithm)
                .charset(UTF8_CHARSET)
                .build();

        assertThat(digest.getHeaderName()).isEqualTo(RequestHeaders.DIGEST);
        assertThat(digest.getHeaderValue()).isEqualTo(EXPECTED_DIGEST_HEADER_VALUE);
    }
}