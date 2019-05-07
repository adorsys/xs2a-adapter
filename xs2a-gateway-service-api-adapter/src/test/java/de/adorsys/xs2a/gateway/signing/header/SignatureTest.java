package de.adorsys.xs2a.gateway.signing.header;

import de.adorsys.xs2a.gateway.service.RequestHeaders;
import de.adorsys.xs2a.gateway.signing.exception.HttpRequestSigningException;
import de.adorsys.xs2a.gateway.signing.service.algorithm.EncodingAlgorithm;
import de.adorsys.xs2a.gateway.signing.service.algorithm.SigningAlgorithm;
import org.junit.Before;
import org.junit.Test;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.util.HashMap;
import java.util.Map;

import static de.adorsys.xs2a.gateway.signing.service.signing.Sha256WithRsaSigningServiceTest.readPrivateKey;
import static org.assertj.core.api.Assertions.assertThat;

public class SignatureTest {
    private static final String KEY_ID = "keyIdValue";
    private static final Map<String, String> HEADERS_MAP = new HashMap<>();
    private static final String HELLO_HEADER_NAME = "HelloHeaderName";
    private static final String HELLO_HEADER_VALUE = "HelloHeaderValue";
    private static final String BYE_HEADER_NAME = "ByeHeaderName";
    private static final String BYE_HEADER_VALUE = "ByeHeaderValue";
    private static final SigningAlgorithm SIGNING_ALGORITHM = SigningAlgorithm.SHA256_WITH_RSA;
    private static final EncodingAlgorithm ENCODING_ALGORITHM = EncodingAlgorithm.BASE64;
    private static final Charset UTF8_CHARSET = StandardCharsets.UTF_8;
    private static final PrivateKey PRIVATE_KEY = readPrivateKey();

    private static final String EXPECTED_SIGNATURE_VALUE = "keyId=\"keyIdValue\",algorithm=\"SHA256withRSA\",headers=\"byeheadername helloheadername\",signature=\"Q2IubweQPkMH/5aSwBoswwzQKqgWyeeApODM/FsDGR08tv8usgAmHjm+TLw8tlUiw+K7CZp7yuQS3wytVK+rb9hr2IM+y1Dr+Z8ohHlt51Sf+6vly7vhUIw01znN/XDCe47/cKqyOzycyM6Cr33mfiqyAC3n0qmWM5wJzh2S0p6DAwWpP45xqIy1FFxG7EDAuIUNMFUdcIXkCX8rzBFpx1DA/53yW7GqJ2g9HaA3LfqhK6FVdvHgEH1aOWuAJectltGUcNWBt0ttAyq5l+hfegpZ1XX9BRJtmTnOW4ATynPy2W7dVFhrVN0DQsvR19WxJThHWBiWbLoW9qu/YNJK7obZuOsiCJiA9VOJjj0gJEcKWvOFMtggCeMEfwRuNajaa74ZjyOg5HULlj6FM5vOnL7qgVQ1vrYuudYMlTHYZ6CEF64Z7Lt5KpWfdroEKCWOsnbWK6B/ySeqxjesSOBtOob2pgN0w/1KCRS6ezkrYkXRXcmPW6Hb593yIQQ9RVogFbY8u44CLxxwg3oNMEf7mMibXw5bML3LsTkPBQC4A14No0Y5J8g3/1FeNEKGJXpNWeqXZ8/oEREAGFnVqemo2sHtwPRhm2WipVriRY123MZzV/43ThuHoqw2GUO9WL6iN5IFmCdTDCCFA+SLRFpPDCnxiYqFZ8izkVU+O6KxG2E=\"";

    @Before
    public void setup() {
        HEADERS_MAP.put(HELLO_HEADER_NAME, HELLO_HEADER_VALUE);
        HEADERS_MAP.put(BYE_HEADER_NAME, BYE_HEADER_VALUE);
    }

    @Test
    public void build() {
        Signature signature = Signature.builder()
                .keyId(KEY_ID)
                .headers(HEADERS_MAP)
                .signingAlgorithm(SIGNING_ALGORITHM)
                .encodingAlgorithm(ENCODING_ALGORITHM)
                .charset(UTF8_CHARSET)
                .privateKey(PRIVATE_KEY)
                .build();

        assertThat(signature.getHeaderName()).isEqualTo(RequestHeaders.SIGNATURE);
        assertThat(signature.getHeaderValue()).isEqualTo(EXPECTED_SIGNATURE_VALUE);
    }

    @Test(expected = HttpRequestSigningException.class)
    public void build_failure_privateKeyIsMissing() {
        Signature signature = Signature.builder()
                                      .keyId(KEY_ID)
                                      .headers(HEADERS_MAP)
                                      .signingAlgorithm(SIGNING_ALGORITHM)
                                      .encodingAlgorithm(ENCODING_ALGORITHM)
                                      .charset(UTF8_CHARSET)
                                      .build();
    }

    @Test(expected = HttpRequestSigningException.class)
    public void build_failure_keyIdIsMissing() {
        Signature signature = Signature.builder()
                                      .headers(HEADERS_MAP)
                                      .signingAlgorithm(SIGNING_ALGORITHM)
                                      .encodingAlgorithm(ENCODING_ALGORITHM)
                                      .charset(UTF8_CHARSET)
                                      .privateKey(PRIVATE_KEY)
                                      .build();
    }

    @Test(expected = HttpRequestSigningException.class)
    public void build_failure_headersAreMissing() {
        Signature signature = Signature.builder()
                                      .keyId(KEY_ID)
                                      .signingAlgorithm(SIGNING_ALGORITHM)
                                      .encodingAlgorithm(ENCODING_ALGORITHM)
                                      .charset(UTF8_CHARSET)
                                      .privateKey(PRIVATE_KEY)
                                      .build();
    }

    @Test(expected = HttpRequestSigningException.class)
    public void build_failure_signingAlgorithmIsMissing() {
        Signature signature = Signature.builder()
                                      .keyId(KEY_ID)
                                      .headers(HEADERS_MAP)
                                      .signingAlgorithm(null)
                                      .encodingAlgorithm(ENCODING_ALGORITHM)
                                      .charset(UTF8_CHARSET)
                                      .privateKey(PRIVATE_KEY)
                                      .build();
    }
}