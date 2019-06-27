package de.adorsys.xs2a.adapter.signing.header;

import de.adorsys.xs2a.adapter.service.RequestHeaders;
import de.adorsys.xs2a.adapter.service.exception.HttpRequestSigningException;
import de.adorsys.xs2a.adapter.signing.service.algorithm.EncodingAlgorithm;
import de.adorsys.xs2a.adapter.signing.service.algorithm.SigningAlgorithm;
import de.adorsys.xs2a.adapter.signing.service.encoding.EncodingService;
import de.adorsys.xs2a.adapter.signing.service.signing.SigningService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(PowerMockRunner.class)
@PrepareForTest({EncodingAlgorithm.class, SigningAlgorithm.class})
public class SignatureTest {
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

    @Before
    public void setup() {
        HEADERS_MAP.put(HELLO_HEADER_NAME, HELLO_HEADER_VALUE);
        HEADERS_MAP.put(BYE_HEADER_NAME, BYE_HEADER_VALUE);
    }

    @Test
    public void build() {
        PowerMockito.when(signingAlgorithm.getSigningService())
                .thenReturn(signingService);

        Mockito.when(signingService.sign(Mockito.eq(privateKey), Mockito.anyString(), Mockito.eq(UTF8_CHARSET)))
                .thenReturn(SIGNED_VALUE);

        PowerMockito.when(encodingAlgorithm.getEncodingService())
                .thenReturn(encodingService);

        Mockito.when(encodingService.encode(SIGNED_VALUE))
                .thenReturn(SIGNATURE_ATTRIBUTE_VALUE);

        PowerMockito.when(signingAlgorithm.getAlgorithmName())
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

    @Test(expected = HttpRequestSigningException.class)
    public void build_failure_privateKeyIsMissing() {
        Signature signature = Signature.builder()
                                      .keyId(KEY_ID)
                                      .headers(HEADERS_MAP)
                                      .signingAlgorithm(signingAlgorithm)
                                      .encodingAlgorithm(encodingAlgorithm)
                                      .charset(UTF8_CHARSET)
                                      .build();
    }

    @Test(expected = HttpRequestSigningException.class)
    public void build_failure_keyIdIsMissing() {
        Signature signature = Signature.builder()
                                      .headers(HEADERS_MAP)
                                      .signingAlgorithm(signingAlgorithm)
                                      .encodingAlgorithm(encodingAlgorithm)
                                      .charset(UTF8_CHARSET)
                                      .privateKey(privateKey)
                                      .build();
    }

    @Test(expected = HttpRequestSigningException.class)
    public void build_failure_headersAreMissing() {
        Signature signature = Signature.builder()
                                      .keyId(KEY_ID)
                                      .signingAlgorithm(signingAlgorithm)
                                      .encodingAlgorithm(encodingAlgorithm)
                                      .charset(UTF8_CHARSET)
                                      .privateKey(privateKey)
                                      .build();
    }

    @Test(expected = HttpRequestSigningException.class)
    public void build_failure_signingAlgorithmIsMissing() {
        Signature signature = Signature.builder()
                                      .keyId(KEY_ID)
                                      .headers(HEADERS_MAP)
                                      .signingAlgorithm(null)
                                      .encodingAlgorithm(encodingAlgorithm)
                                      .charset(UTF8_CHARSET)
                                      .privateKey(privateKey)
                                      .build();
    }
}
