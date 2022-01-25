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
