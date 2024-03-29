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

package de.adorsys.xs2a.adapter.commerzbank;

import de.adorsys.xs2a.adapter.api.Oauth2Service;
import de.adorsys.xs2a.adapter.api.PkceOauth2Extension;
import de.adorsys.xs2a.adapter.api.Pkcs12KeyStore;
import de.adorsys.xs2a.adapter.api.http.HttpClient;
import de.adorsys.xs2a.adapter.api.http.HttpClientConfig;
import de.adorsys.xs2a.adapter.api.http.HttpClientFactory;
import de.adorsys.xs2a.adapter.api.http.HttpLogSanitizer;
import de.adorsys.xs2a.adapter.api.model.Aspsp;
import de.adorsys.xs2a.adapter.api.model.TokenResponse;
import de.adorsys.xs2a.adapter.impl.*;
import de.adorsys.xs2a.adapter.impl.http.StringUri;

import java.io.IOException;
import java.net.URI;
import java.util.Map;

import static de.adorsys.xs2a.adapter.api.validation.Validation.requireValid;

/**
 * @see <a href="https://psd2.developer.commerzbank.com/content/howto/ais-manage-consents">OAuth2 authorisation</a>
 * @see <a href="https://psd2.developer.commerzbank.com/content/howto/sandbox">2 - Authorize the payment</a>
 */
public class CommerzbankOauth2Service extends Oauth2ServiceDecorator implements PkceOauth2Extension {
    private static final String AIS_SCOPE_PREFIX = "AIS:";
    private static final String PIS_SCOPE_PREFIX = "PIS:";

    private final String baseUrl;

    private CommerzbankOauth2Service(Oauth2Service oauth2Service, String baseUrl) {
        super(oauth2Service);
        this.baseUrl = baseUrl;
    }

    public static CommerzbankOauth2Service create(Aspsp aspsp, HttpClientFactory httpClientFactory) {
        HttpClient httpClient = httpClientFactory.getHttpClient(aspsp.getAdapterId());
        HttpClientConfig httpClientConfig = httpClientFactory.getHttpClientConfig();
        HttpLogSanitizer logSanitizer = httpClientConfig.getLogSanitizer();
        Pkcs12KeyStore keyStore = httpClientConfig.getKeyStore();

        String baseUrl = aspsp.getIdpUrl() != null ? aspsp.getIdpUrl() : aspsp.getUrl();
        BaseOauth2Service baseOauth2Service = new BaseOauth2Service(aspsp, httpClient, logSanitizer);
        CertificateSubjectClientIdOauth2Service clientIdOauth2Service =
            new CertificateSubjectClientIdOauth2Service(baseOauth2Service, keyStore);
        PkceOauth2Service pkceOauth2Service = new PkceOauth2Service(clientIdOauth2Service);
        ScopeWithResourceIdOauth2Service scopeOauth2Service =
            new ScopeWithResourceIdOauth2Service(pkceOauth2Service, AIS_SCOPE_PREFIX, PIS_SCOPE_PREFIX);
        return new CommerzbankOauth2Service(scopeOauth2Service, baseUrl);
    }

    @Override
    public URI getAuthorizationRequestUri(Map<String, String> headers, Parameters parameters) throws IOException {
        requireValid(validateGetAuthorizationRequestUri(headers, parameters));
        return oauth2Service.getAuthorizationRequestUri(headers, parameters);
    }

    @Override
    public TokenResponse getToken(Map<String, String> headers, Parameters parameters) throws IOException {
        parameters.removeScaOAuthLink();
        parameters.setTokenEndpoint(StringUri.fromElements(baseUrl, "/v1/token"));
        return oauth2Service.getToken(headers, parameters);
    }
}
