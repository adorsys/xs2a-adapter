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

import java.net.URI;

public abstract class UriBuilder {

    public static UriBuilder fromUri(URI baseUri) {
        return new ApacheUriBuilder(baseUri);
    }

    public static UriBuilder fromUri(String baseUri) {
        return new ApacheUriBuilder(URI.create(baseUri));
    }

    public abstract UriBuilder queryParam(String name, String value);

    public abstract UriBuilder renameQueryParam(String currentName, String newName);

    public abstract URI build();
}
