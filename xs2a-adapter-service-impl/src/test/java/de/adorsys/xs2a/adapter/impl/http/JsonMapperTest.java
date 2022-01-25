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

import de.adorsys.xs2a.adapter.api.model.Aspsp;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.UncheckedIOException;

import static org.junit.jupiter.api.Assertions.assertThrows;

class JsonMapperTest {
    JsonMapper mapper = new JacksonObjectMapper();

    @Test
    void writeValueAsString_throwsException() {
        ByteArrayInputStream value = new ByteArrayInputStream(new byte[]{});

        assertThrows(UncheckedIOException.class, () -> mapper.writeValueAsString(value));
    }

    @Test
    void readValue_string_throwsException() {
        assertThrows(UncheckedIOException.class, () -> mapper.readValue("string", Aspsp.class));
    }

    @Test
    void readValue_inputStream_throwsException() {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(new byte[]{});
        assertThrows(UncheckedIOException.class, () -> mapper.readValue(inputStream, Aspsp.class));
    }
}
