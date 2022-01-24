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

package de.adorsys.xs2a.adapter.impl.http;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HttpHeadersTest {

    @Test
    void parse() {
        HttpHeaders httpHeaders = HttpHeaders.parse("Content-Disposition: form-data; name=\"xml_sct\"\r\n" +
            "Content-Type: application/xml\r\n" +
            "Content-Length: 1434\r\n\r\n");

        assertThat(httpHeaders.size()).isEqualTo(3);
    }

    @Test
    void parseToleratesLFsInsteadOfCRLFs() {
        HttpHeaders httpHeaders = HttpHeaders.parse("Content-Disposition: form-data; name=\"xml_sct\"\n" +
            "Content-Type: application/xml\n" +
            "Content-Length: 1434\n\n");

        assertThat(httpHeaders.size()).isEqualTo(3);
    }
}
