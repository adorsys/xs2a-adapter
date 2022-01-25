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

package de.adorsys.xs2a.adapter.impl.signing.header;

import de.adorsys.xs2a.adapter.api.RequestHeaders;
import de.adorsys.xs2a.adapter.api.exception.HttpRequestSigningException;
import de.adorsys.xs2a.adapter.impl.signing.algorithm.EncodingAlgorithm;
import de.adorsys.xs2a.adapter.impl.signing.algorithm.EncodingAlgorithm.EncodingService;
import de.adorsys.xs2a.adapter.impl.signing.algorithm.SigningAlgorithm;
import de.adorsys.xs2a.adapter.impl.signing.algorithm.SigningAlgorithm.SigningService;
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

    private static final String EXPECTED_SIGNATURE_VALUE =
        String.format("keyId=\"%s\",algorithm=\"%s\",headers=\"byeheadername helloheadername\",signature=\"%s\"",
            KEY_ID, SIGNING_ALGORITHM_NAME, SIGNATURE_ATTRIBUTE_VALUE);

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
        Signature.Builder builder = Signature.builder()
            .keyId(KEY_ID)
            .headers(HEADERS_MAP)
            .signingAlgorithm(signingAlgorithm)
            .encodingAlgorithm(encodingAlgorithm)
            .charset(UTF8_CHARSET);

        Assertions.assertThrows(HttpRequestSigningException.class, builder::build);
    }

    @Test
    void build_failure_keyIdIsMissing() {
        Signature.Builder builder = Signature.builder()
            .headers(HEADERS_MAP)
            .signingAlgorithm(signingAlgorithm)
            .encodingAlgorithm(encodingAlgorithm)
            .charset(UTF8_CHARSET)
            .privateKey(privateKey);

        Assertions.assertThrows(HttpRequestSigningException.class, builder::build);
    }

    @Test
    void build_failure_headersAreMissing() {
        Signature.Builder builder = Signature.builder()
            .keyId(KEY_ID)
            .signingAlgorithm(signingAlgorithm)
            .encodingAlgorithm(encodingAlgorithm)
            .charset(UTF8_CHARSET)
            .privateKey(privateKey);

        Assertions.assertThrows(HttpRequestSigningException.class, builder::build);
    }

    @Test
    void build_failure_signingAlgorithmIsMissing() {
        Signature.Builder builder = Signature.builder()
            .keyId(KEY_ID)
            .headers(HEADERS_MAP)
            .signingAlgorithm(null)
            .encodingAlgorithm(encodingAlgorithm)
            .charset(UTF8_CHARSET)
            .privateKey(privateKey);

        Assertions.assertThrows(HttpRequestSigningException.class, builder::build);
    }
}
