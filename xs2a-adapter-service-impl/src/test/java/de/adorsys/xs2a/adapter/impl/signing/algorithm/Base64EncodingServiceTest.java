package de.adorsys.xs2a.adapter.impl.signing.algorithm;

import de.adorsys.xs2a.adapter.impl.signing.algorithm.EncodingAlgorithm.EncodingService;
import org.junit.jupiter.api.Test;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

class Base64EncodingServiceTest {
    private static final String STRING_TO_ENCODE = "Lorem ipsum dolor sit amet, consectetur adipiscing elit";
    private static final String EXPECTED_ENCODED_VALUE =
        "TG9yZW0gaXBzdW0gZG9sb3Igc2l0IGFtZXQsIGNvbnNlY3RldHVyIGFkaXBpc2NpbmcgZWxpdA==";
    private static final Charset UTF8_CHARSET = StandardCharsets.UTF_8;

    @Test
    void encode() {
        EncodingService encodingService = EncodingAlgorithm.BASE64.getEncodingService();

        String actualEncodedValue = encodingService.encode(STRING_TO_ENCODE.getBytes(UTF8_CHARSET));

        assertThat(actualEncodedValue).isEqualTo(EXPECTED_ENCODED_VALUE);
    }
}
