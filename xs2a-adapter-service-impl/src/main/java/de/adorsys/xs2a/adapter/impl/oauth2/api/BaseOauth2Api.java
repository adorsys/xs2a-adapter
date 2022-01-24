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

package de.adorsys.xs2a.adapter.impl.oauth2.api;

import de.adorsys.xs2a.adapter.api.http.HttpClient;
import de.adorsys.xs2a.adapter.api.http.HttpLogSanitizer;
import de.adorsys.xs2a.adapter.api.oauth.Oauth2Api;
import de.adorsys.xs2a.adapter.impl.http.ResponseHandlers;
import de.adorsys.xs2a.adapter.impl.oauth2.api.model.AuthorisationServerMetaData;

public class BaseOauth2Api<T extends AuthorisationServerMetaData> implements Oauth2Api {

    private final HttpClient httpClient;
    private final Class<T> metaDataModelClass;
    private final ResponseHandlers responseHandlers;

    public BaseOauth2Api(HttpClient httpClient, Class<T> metaDataModelClass) {
        this(httpClient, metaDataModelClass, null);
    }

    public BaseOauth2Api(HttpClient httpClient, Class<T> metaDataModelClass, HttpLogSanitizer logSanitizer) {
        this.httpClient = httpClient;
        this.metaDataModelClass = metaDataModelClass;
        this.responseHandlers = new ResponseHandlers(logSanitizer);
    }

    @Override
    public String getAuthorisationUri(String scaOAuthUrl) {
        return getAuthorisationServerMetaData(scaOAuthUrl)
                   .getAuthorisationEndpoint();
    }

    @Override
    public String getTokenUri(String scaOAuthUrl) {
        return getAuthorisationServerMetaData(scaOAuthUrl)
                   .getTokenEndpoint();
    }

    private T getAuthorisationServerMetaData(String scaOAuthUrl) {
        return httpClient.get(scaOAuthUrl)
                   .send(responseHandlers.jsonResponseHandler(metaDataModelClass))
                   .getBody();
    }
}
