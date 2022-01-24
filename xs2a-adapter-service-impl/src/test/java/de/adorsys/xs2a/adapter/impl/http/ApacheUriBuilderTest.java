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

import static org.assertj.core.api.Assertions.assertThat;

class ApacheUriBuilderTest {

    public static final URI baseUriWithQueryParam = URI.create("https://base.uri?param=param");
    private ApacheUriBuilder builder = new ApacheUriBuilder(baseUriWithQueryParam);

    @Test
    void renameQueryParam_newParameter() {
        UriBuilder actual = builder.renameQueryParam("param", "another");

        assertThat(actual.build()).hasToString("https://base.uri?another=param");
    }

    @Test
    void renameQueryParam_sameParameter() {
        UriBuilder actual = builder.renameQueryParam("another", "param");

        assertThat(actual.build()).isEqualTo(baseUriWithQueryParam);
    }

    @Test
    void queryParametersAreEncoded() {
        URI uri = new ApacheUriBuilder(URI.create("http://example.com"))
            .queryParam("uri", "https://acme.com?foo=bar&baz=asdf")
            .build();
        assertThat(uri).hasToString("http://example.com?uri=https%3A%2F%2Facme.com%3Ffoo%3Dbar%26baz%3Dasdf");
    }
}
