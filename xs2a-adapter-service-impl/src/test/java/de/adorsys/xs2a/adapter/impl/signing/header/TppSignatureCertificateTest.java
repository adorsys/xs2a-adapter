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
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TppSignatureCertificateTest {
    private static final String PUBLIC_KEY_AS_STRING = "Lorem ipsum dolor sit amet, consectetur adipiscing elit";

    @Test
    void build() {
        TppSignatureCertificate tppSignatureCertificate = TppSignatureCertificate.builder()
            .publicKeyAsString(PUBLIC_KEY_AS_STRING)
            .build();

        assertThat(tppSignatureCertificate.getHeaderName()).isEqualTo(RequestHeaders.TPP_SIGNATURE_CERTIFICATE);
        assertThat(tppSignatureCertificate.getHeaderValue()).isEqualTo(PUBLIC_KEY_AS_STRING);
    }
}
