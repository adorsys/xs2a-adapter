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

package de.adorsys.xs2a.adapter.sparkasse;

import de.adorsys.xs2a.adapter.api.Oauth2Service;
import de.adorsys.xs2a.adapter.api.PkceOauth2Extension;
import de.adorsys.xs2a.adapter.api.Pkcs12KeyStore;
import de.adorsys.xs2a.adapter.api.http.HttpClient;
import de.adorsys.xs2a.adapter.api.http.HttpClientFactory;
import de.adorsys.xs2a.adapter.api.http.HttpLogSanitizer;
import de.adorsys.xs2a.adapter.api.model.Aspsp;
import de.adorsys.xs2a.adapter.api.model.TokenResponse;
import de.adorsys.xs2a.adapter.impl.*;
import de.adorsys.xs2a.adapter.impl.http.UriBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.Map;

import static de.adorsys.xs2a.adapter.api.validation.Validation.requireValid;

public class SparkasseOauth2Service extends Oauth2ServiceDecorator implements PkceOauth2Extension {

    private static final String AIS_SCOPE_PREFIX = "AIS: ";
    private static final String PIS_SCOPE_PREFIX = "PIS: ";

    private SparkasseOauth2Service(Oauth2Service oauth2Service) {
        super(oauth2Service);
    }

    public static SparkasseOauth2Service create(Aspsp aspsp, HttpClientFactory httpClientFactory) {
        HttpClient httpClient = httpClientFactory.getHttpClient(aspsp.getAdapterId());
        HttpLogSanitizer logSanitizer = httpClientFactory.getHttpClientConfig().getLogSanitizer();
        Pkcs12KeyStore keyStore = httpClientFactory.getHttpClientConfig().getKeyStore();

        BaseOauth2Service baseOauth2Service = new BaseOauth2Service(aspsp, httpClient, logSanitizer);
        CertificateSubjectClientIdOauth2Service clientIdOauth2Service =
            new CertificateSubjectClientIdOauth2Service(baseOauth2Service, keyStore);
        PkceOauth2Service pkceOauth2Service = new PkceOauth2Service(clientIdOauth2Service);
        ScopeWithResourceIdOauth2Service scopeOauth2Service =
            new ScopeWithResourceIdOauth2Service(pkceOauth2Service, AIS_SCOPE_PREFIX, PIS_SCOPE_PREFIX);
        return new SparkasseOauth2Service(scopeOauth2Service);
    }

    @Override
    public URI getAuthorizationRequestUri(Map<String, String> headers, Parameters parameters) throws IOException {
        requireValid(validateGetAuthorizationRequestUri(headers, parameters));
        return UriBuilder.fromUri(oauth2Service.getAuthorizationRequestUri(headers, parameters))
            .renameQueryParam(Parameters.RESPONSE_TYPE, "responseType")
            .renameQueryParam(Parameters.CLIENT_ID, "clientId")
            .build();
    }

    @Override
    public TokenResponse getToken(Map<String, String> headers, Parameters parameters) throws IOException {
        return oauth2Service.getToken(headers, parameters);
    }
}
