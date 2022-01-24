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

package de.adorsys.xs2a.adapter.impl.signing.algorithm;

import de.adorsys.xs2a.adapter.impl.signing.algorithm.HashingAlgorithm.HashingService;
import org.junit.jupiter.api.Test;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

class Sha256HashingServiceTest {
    private static final String STRING_TO_HASH = "Lorem ipsum dolor sit amet, consectetur adipiscing elit";
    private static final String EXPECTED_HASHED_VALUE =
        "07fe4d4a25718241af145a93f890eb5469052e251d199d173bd3bd50c3bb4da2";
    private static final Charset UTF8_CHARSET = StandardCharsets.UTF_8;

    @Test
    void hash() {
        HashingService hashingService = HashingAlgorithm.SHA256.getHashingService();

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
