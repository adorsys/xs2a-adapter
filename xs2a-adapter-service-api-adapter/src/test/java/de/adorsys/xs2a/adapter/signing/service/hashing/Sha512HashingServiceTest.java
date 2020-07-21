package de.adorsys.xs2a.adapter.signing.service.hashing;

import org.junit.jupiter.api.Test;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

class Sha512HashingServiceTest {
    private static final String STRING_TO_HASH = "Lorem ipsum dolor sit amet, consectetur adipiscing elit";
    private static final String EXPECTED_HASHED_VALUE = "d3b3d69ccc163089f91d42ebef0f01dd70ec40deb76a9da5ba63ef5b39601b41a34b04f8236ce9e59a150241716a5e2da9f1bdcc2734d4d7fcd8e69df36022f0";
    private static final Charset UTF8_CHARSET = StandardCharsets.UTF_8;

    @Test
    void hash() {
        HashingService hashingService = new Sha512HashingService();

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
