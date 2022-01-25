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

import de.adorsys.xs2a.adapter.api.exception.Xs2aAdapterException;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

// intentionally package private
class ApacheUriBuilder extends UriBuilder {

    private final URIBuilder uriBuilder;

    ApacheUriBuilder(URI baseUri) {
        uriBuilder = new URIBuilder(baseUri);
    }

    @Override
    public UriBuilder queryParam(String name, String value) {
        if (name != null && value != null) {
            uriBuilder.setParameter(name, value);
        }
        return this;
    }

    @Override
    public UriBuilder renameQueryParam(String currentName, String newName) {
        List<NameValuePair> queryParams = uriBuilder.getQueryParams();
        uriBuilder.removeQuery();
        for (NameValuePair queryParam : queryParams) {
            if (queryParam.getName().equals(currentName)) {
                uriBuilder.addParameter(newName, queryParam.getValue());
            } else {
                uriBuilder.addParameter(queryParam.getName(), queryParam.getValue());
            }
        }
        return this;
    }

    @Override
    public URI build() {
        try {
            return uriBuilder.build();
        } catch (URISyntaxException e) {
            throw new Xs2aAdapterException(e);
        }
    }
}
