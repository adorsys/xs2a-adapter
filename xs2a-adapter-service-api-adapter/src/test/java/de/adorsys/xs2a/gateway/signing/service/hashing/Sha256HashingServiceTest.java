package de.adorsys.xs2a.gateway.signing.service.hashing;

import org.junit.Test;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

public class Sha256HashingServiceTest {
    private static final String STRING_TO_HASH = "Lorem ipsum dolor sit amet, consectetur adipiscing elit";
    private static final String EXPECTED_HASHED_VALUE = "07fe4d4a25718241af145a93f890eb5469052e251d199d173bd3bd50c3bb4da2";
    private static final Charset UTF8_CHARSET = StandardCharsets.UTF_8;

    @Test
    public void hash() {
        HashingService hashingService = new Sha256HashingService();

        byte[] actualHashedValue = hashingService.hash(STRING_TO_HASH, UTF8_CHARSET);

        assertThat(toHex(actualHashedValue)).isEqualTo(EXPECTED_HASHED_VALUE);
    }

    private String toHex(byte[] hashedValue) {
        StringBuilder sb = new StringBuilder();

        for (byte b : hashedValue) {
            sb.append(Integer.toHexString((b & 0xFF) | 0x100), 1, 3);
        }

        return sb.toString();
    }
}