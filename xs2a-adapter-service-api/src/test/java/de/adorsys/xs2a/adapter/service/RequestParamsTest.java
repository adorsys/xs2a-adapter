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

package de.adorsys.xs2a.adapter.service;

import de.adorsys.xs2a.adapter.api.RequestParams;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static java.util.Collections.emptyMap;
import static java.util.Collections.singletonMap;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RequestParamsTest {

    @Test
    void fromMap() {
        RequestParams requestParams = RequestParams.fromMap(emptyMap());

        assertThat(requestParams.toMap()).isEqualTo(emptyMap());
    }

    @Test
    void dateFromIsTypeChecked() {
        Map<String, String> map = singletonMap("dateFrom", "asdf");

        assertThrows(IllegalArgumentException.class, () -> RequestParams.fromMap(map));
    }

    @Test
    void dateToIsTypeChecked() {
        Map<String, String> map = singletonMap("dateTo", "asdf");

        assertThrows(IllegalArgumentException.class, () -> RequestParams.fromMap(map));
    }

    @Test
    void withBalanceIsTypeChecked() {
        Map<String, String> map = singletonMap("withBalance", "asdf");

        assertThrows(IllegalArgumentException.class, () -> RequestParams.fromMap(map));
    }

    @Test
    void deltaListIsTypeChecked() {
        Map<String, String> map = singletonMap("deltaList", "asdf");

        assertThrows(IllegalArgumentException.class, () -> RequestParams.fromMap(map));
    }
}
