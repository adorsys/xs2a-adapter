/*
 * Copyright 2018-2018 adorsys GmbH & Co KG
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.adorsys.xs2a.adapter.service;

import de.adorsys.xs2a.adapter.adapter.BaseOauth2Service;
import de.adorsys.xs2a.adapter.adapter.CertificateSubjectClientIdOauth2Service;
import de.adorsys.xs2a.adapter.adapter.PkceOauth2Service;
import de.adorsys.xs2a.adapter.http.HttpClient;
import de.adorsys.xs2a.adapter.http.StringUri;
import de.adorsys.xs2a.adapter.http.UriBuilder;
import de.adorsys.xs2a.adapter.service.exception.BadRequestException;
import de.adorsys.xs2a.adapter.service.model.Aspsp;
import de.adorsys.xs2a.adapter.service.model.TokenResponse;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.net.URI;
import java.util.Map;

/**
 * @see <a href="https://xs2a-developer.comdirect.de/content/howto/ais-manage-consents">Authorisation</a>
 */
public class ComdirectOauth2Service implements Oauth2Service, PkceOauth2Extension {

    private static final String SCA_OAUTH_LINK_MISSING_ERROR_MESSAGE = "SCA OAuth link is missing or has a wrong format: " +
        "it has to be either provided as a request parameter or preconfigured for the current ASPSP";

    private final Oauth2Service oauth2Service;
    private final String baseUrl;
    private final Aspsp aspsp;

    private ComdirectOauth2Service(Oauth2Service oauth2Service, String baseUrl, Aspsp aspsp) {
        this.oauth2Service = oauth2Service;
        this.baseUrl = baseUrl;
        this.aspsp = aspsp;
    }

    public static ComdirectOauth2Service create(Aspsp aspsp, HttpClient httpClient, Pkcs12KeyStore keyStore) {
        String baseUrl = aspsp.getIdpUrl() != null ? aspsp.getIdpUrl() : aspsp.getUrl();
        BaseOauth2Service baseOauth2Service = new BaseOauth2Service(aspsp, httpClient);
        CertificateSubjectClientIdOauth2Service clientIdOauth2Service =
            new CertificateSubjectClientIdOauth2Service(baseOauth2Service, keyStore);
        return new ComdirectOauth2Service(new PkceOauth2Service(clientIdOauth2Service), baseUrl, aspsp);
    }

    @Override
    public URI getAuthorizationRequestUri(Map<String, String> headers, Parameters parameters) throws IOException {
        if (StringUtils.isBlank(getScaOAuthUrl(parameters))) {
            throw new BadRequestException(SCA_OAUTH_LINK_MISSING_ERROR_MESSAGE);
        }

        parameters.setAuthorizationEndpoint(parameters.removeScaOAuthLink());

        return UriBuilder.fromUri(oauth2Service.getAuthorizationRequestUri(headers, parameters))
            .queryParam(Parameters.SCOPE, scope(parameters))
            .build();
    }

    private String scope(Parameters parameters) {
        if (parameters.getConsentId() != null) {
            return "AIS:" + parameters.getConsentId();
        }
        return null;
    }

    @Override
    public TokenResponse getToken(Map<String, String> headers, Parameters parameters) throws IOException {
        parameters.removeScaOAuthLink();
        parameters.setTokenEndpoint(StringUri.fromElements(baseUrl, "/v1/token"));
        return oauth2Service.getToken(headers, parameters);
    }

    private String getScaOAuthUrl(Parameters parameters) {
        String baseScaOAuthUrl = parameters.getScaOAuthLink();

        if (StringUtils.isBlank(baseScaOAuthUrl)) {
            baseScaOAuthUrl = aspsp.getIdpUrl();
        }

        return baseScaOAuthUrl;
    }
}
