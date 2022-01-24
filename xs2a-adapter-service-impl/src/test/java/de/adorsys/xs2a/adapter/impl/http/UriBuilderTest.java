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

import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UriBuilderTest {

    @Test
    void queryParamIsNotAddedIfValueIsNull() {
        URI uri = UriBuilder.fromUri("http://acme.com/")
            .queryParam("q", null)
            .build();
        assertEquals("http://acme.com/", uri.toString());
    }

    @Test
    void stringUriMustBeValid() {
        assertThrows(IllegalArgumentException.class, () -> UriBuilder.fromUri("http://acme.com/?q=two words"));
    }

    @Test
    void whitespaceInQueryIsUrlFormEncoded() {
        URI uri = UriBuilder.fromUri("http://acme.com/")
            .queryParam("q", "two words")
            .build();
        assertEquals("q=two+words", uri.getQuery());
        assertEquals("q=two+words", uri.getRawQuery());
    }
}
