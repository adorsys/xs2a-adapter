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

package de.adorsys.xs2a.adapter.unicredit;

import de.adorsys.xs2a.adapter.api.RequestHeaders;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static de.adorsys.xs2a.adapter.unicredit.UnicreditHeaders.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UnicreditHeadersTest {

    @Test
    void addPsuIdTypeHeaderDefault() {

        Map<String, String> header = addPsuIdTypeHeader(new HashMap<>());

        assertThat(header).hasSize(1)
            .containsValues(DEFAULT_PSU_ID_TYPE);
    }

    @Test
    void addPsuIdTypeHeaderNotSupported() {

        HashMap<String, String> headers = new HashMap<>();
        headers.put(RequestHeaders.PSU_ID_TYPE, "UNSUPPORTED_PSU_ID_TYPE");
        Map<String, String> header = addPsuIdTypeHeader(headers);

        assertThat(header).hasSize(1)
            .containsValues(DEFAULT_PSU_ID_TYPE);
    }

    @Test
    void addPsuIdTypeHeaderGlobal() {

        HashMap<String, String> headers = new HashMap<>();
        headers.put(RequestHeaders.PSU_ID_TYPE, UCE_BANKING_GLOBAL);
        Map<String, String> header = addPsuIdTypeHeader(headers);

        assertThat(header).hasSize(1)
            .containsValues(UCE_BANKING_GLOBAL);
    }

    @Test
    void addPossibleValues() {
        assertThrows(UnsupportedOperationException.class, () -> POSSIBLE_PSU_ID_TYPE_VALUES.add("new-item"));
    }
}
