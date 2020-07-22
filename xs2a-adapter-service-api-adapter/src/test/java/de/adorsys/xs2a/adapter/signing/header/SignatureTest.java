package de.adorsys.xs2a.adapter.signing.header;

import de.adorsys.xs2a.adapter.service.RequestHeaders;
import de.adorsys.xs2a.adapter.service.exception.HttpRequestSigningException;
import de.adorsys.xs2a.adapter.signing.service.algorithm.EncodingAlgorithm;
import de.adorsys.xs2a.adapter.signing.service.algorithm.SigningAlgorithm;
import de.adorsys.xs2a.adapter.signing.service.encoding.EncodingService;
import de.adorsys.xs2a.adapter.signing.service.signing.SigningService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SignatureTest {
    private static final String KEY_ID = "keyIdValue";
    private static final Map<String, String> HEADERS_MAP = new HashMap<>();
    private static final String HELLO_HEADER_NAME = "HelloHeaderName";
    private static final String HELLO_HEADER_VALUE = "HelloHeaderValue";
    private static final String BYE_HEADER_NAME = "ByeHeaderName";
    private static final String BYE_HEADER_VALUE = "ByeHeaderValue";
    private static final Charset UTF8_CHARSET = StandardCharsets.UTF_8;
    private static final byte[] SIGNED_VALUE = "SignedValue".getBytes();
    private static final String SIGNATURE_ATTRIBUTE_VALUE = "SignatureAttributeValue";
    private static final String SIGNING_ALGORITHM_NAME = "SHA256withRSA";

    private static final String EXPECTED_SIGNATURE_VALUE = String.format("keyId=\"%s\",algorithm=\"%s\",headers=\"byeheadername helloheadername\",signature=\"%s\"", KEY_ID, SIGNING_ALGORITHM_NAME, SIGNATURE_ATTRIBUTE_VALUE);

    @Mock
    private SigningAlgorithm signingAlgorithm;
    @Mock
    private SigningService signingService;
    @Mock
    private EncodingAlgorithm encodingAlgorithm;
    @Mock
    private EncodingService encodingService;
    @Mock
    private PrivateKey privateKey;

    @BeforeEach
    public void setup() {
        HEADERS_MAP.put(HELLO_HEADER_NAME, HELLO_HEADER_VALUE);
        HEADERS_MAP.put(BYE_HEADER_NAME, BYE_HEADER_VALUE);
    }

    @Test
    void build() {
        when(signingAlgorithm.getSigningService())
            .thenReturn(signingService);

        when(signingService.sign(Mockito.eq(privateKey), Mockito.anyString(), Mockito.eq(UTF8_CHARSET)))
            .thenReturn(SIGNED_VALUE);

        when(encodingAlgorithm.getEncodingService())
            .thenReturn(encodingService);

        when(encodingService.encode(SIGNED_VALUE))
            .thenReturn(SIGNATURE_ATTRIBUTE_VALUE);

        when(signingAlgorithm.getAlgorithmName())
            .thenReturn(SIGNING_ALGORITHM_NAME);

        Signature signature = Signature.builder()
            .keyId(KEY_ID)
            .headers(HEADERS_MAP)
            .signingAlgorithm(signingAlgorithm)
            .encodingAlgorithm(encodingAlgorithm)
            .charset(UTF8_CHARSET)
            .privateKey(privateKey)
            .build();

        assertThat(signature.getHeaderName()).isEqualTo(RequestHeaders.SIGNATURE);
        assertThat(signature.getHeaderValue()).isEqualTo(EXPECTED_SIGNATURE_VALUE);
    }

    @Test
    void build_failure_privateKeyIsMissing() {
        Signature.SignatureBuilder builder = Signature.builder()
            .keyId(KEY_ID)
            .headers(HEADERS_MAP)
            .signingAlgorithm(signingAlgorithm)
            .encodingAlgorithm(encodingAlgorithm)
            .charset(UTF8_CHARSET);

        Assertions.assertThrows(HttpRequestSigningException.class, builder::build);
    }

    @Test
    void build_failure_keyIdIsMissing() {
        Signature.SignatureBuilder builder = Signature.builder()
            .headers(HEADERS_MAP)
            .signingAlgorithm(signingAlgorithm)
            .encodingAlgorithm(encodingAlgorithm)
            .charset(UTF8_CHARSET)
            .privateKey(privateKey);

        Assertions.assertThrows(HttpRequestSigningException.class, builder::build);
    }

    @Test
    void build_failure_headersAreMissing() {
        Signature.SignatureBuilder builder = Signature.builder()
            .keyId(KEY_ID)
            .signingAlgorithm(signingAlgorithm)
            .encodingAlgorithm(encodingAlgorithm)
            .charset(UTF8_CHARSET)
            .privateKey(privateKey);

        Assertions.assertThrows(HttpRequestSigningException.class, builder::build);
    }

    @Test
    void build_failure_signingAlgorithmIsMissing() {
        Signature.SignatureBuilder builder = Signature.builder()
            .keyId(KEY_ID)
            .headers(HEADERS_MAP)
            .signingAlgorithm(null)
            .encodingAlgorithm(encodingAlgorithm)
            .charset(UTF8_CHARSET)
            .privateKey(privateKey);

        Assertions.assertThrows(HttpRequestSigningException.class, builder::build);
    }
}
