package de.adorsys.xs2a.gateway.signing.service.encoding;

import org.junit.Test;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

public class Base64EncodingServiceTest {
    private static final String STRING_TO_ENCODE = "Lorem ipsum dolor sit amet, consectetur adipiscing elit";
    private static final String EXPECTED_ENCODED_VALUE = "TG9yZW0gaXBzdW0gZG9sb3Igc2l0IGFtZXQsIGNvbnNlY3RldHVyIGFkaXBpc2NpbmcgZWxpdA==";
    private static final Charset UTF8_CHARSET = StandardCharsets.UTF_8;

    @Test
    public void encode() {
        EncodingService encodingService = new Base64EncodingService();

        String actualEncodedValue = encodingService.encode(STRING_TO_ENCODE.getBytes(UTF8_CHARSET));

        assertThat(actualEncodedValue).isEqualTo(EXPECTED_ENCODED_VALUE);
    }
}