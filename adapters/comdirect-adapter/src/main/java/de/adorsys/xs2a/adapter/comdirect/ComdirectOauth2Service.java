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

package de.adorsys.xs2a.adapter.comdirect;

import de.adorsys.xs2a.adapter.api.Oauth2Service;
import de.adorsys.xs2a.adapter.api.PkceOauth2Extension;
import de.adorsys.xs2a.adapter.api.Pkcs12KeyStore;
import de.adorsys.xs2a.adapter.api.http.HttpClient;
import de.adorsys.xs2a.adapter.api.http.HttpLogSanitizer;
import de.adorsys.xs2a.adapter.api.model.Aspsp;
import de.adorsys.xs2a.adapter.api.model.TokenResponse;
import de.adorsys.xs2a.adapter.api.validation.ValidationError;
import de.adorsys.xs2a.adapter.impl.*;
import de.adorsys.xs2a.adapter.impl.http.StringUri;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static de.adorsys.xs2a.adapter.api.validation.Validation.requireValid;

/**
 * @see <a href="https://xs2a-developer.comdirect.de/content/howto/ais-manage-consents">Authorisation</a>
 */
public class ComdirectOauth2Service extends Oauth2ServiceDecorator implements PkceOauth2Extension {

    private static final String SCA_OAUTH_LINK_MISSING_ERROR_MESSAGE = "SCA OAuth link is missing or has a wrong format: " +
        "it has to be either provided as a request parameter or preconfigured for the current ASPSP";
    private static final String AIS_SCOPE_PREFIX = "AIS:";
    private static final String PIS_SCOPE_PREFIX = "PIS:";

    private final String baseUrl;
    private final Aspsp aspsp;

    private ComdirectOauth2Service(Oauth2Service oauth2Service, String baseUrl, Aspsp aspsp) {
        super(oauth2Service);
        this.baseUrl = baseUrl;
        this.aspsp = aspsp;
    }

    public static ComdirectOauth2Service create(Aspsp aspsp, HttpClient httpClient, Pkcs12KeyStore keyStore, HttpLogSanitizer logSanitizer) {
        String baseUrl = aspsp.getIdpUrl() != null ? aspsp.getIdpUrl() : aspsp.getUrl();
        BaseOauth2Service baseOauth2Service = new BaseOauth2Service(aspsp, httpClient, logSanitizer);
        CertificateSubjectClientIdOauth2Service clientIdOauth2Service =
            new CertificateSubjectClientIdOauth2Service(baseOauth2Service, keyStore);
        PkceOauth2Service pkceOauth2Service = new PkceOauth2Service(clientIdOauth2Service);
        ScopeWithResourceIdOauth2Service scopeOauth2Service =
            new ScopeWithResourceIdOauth2Service(pkceOauth2Service, AIS_SCOPE_PREFIX, PIS_SCOPE_PREFIX);
        return new ComdirectOauth2Service(scopeOauth2Service, baseUrl, aspsp);
    }

    @Override
    public URI getAuthorizationRequestUri(Map<String, String> headers, Parameters parameters) throws IOException {
        requireValid(validateGetAuthorizationRequestUri(headers, parameters));

        parameters.setAuthorizationEndpoint(parameters.removeScaOAuthLink());

        return oauth2Service.getAuthorizationRequestUri(headers, parameters);
    }

    @Override
    public List<ValidationError> validateGetAuthorizationRequestUri(Map<String, String> headers,
                                                                    Parameters parameters) {
        List<ValidationError> validationErrors =
            new ArrayList<>(oauth2Service.validateGetAuthorizationRequestUri(headers, parameters));
        if (StringUtils.isBlank(getScaOAuthUrl(parameters))) {
            validationErrors.add(new ValidationError(ValidationError.Code.REQUIRED,
                Parameters.SCA_OAUTH_LINK,
                SCA_OAUTH_LINK_MISSING_ERROR_MESSAGE));
        }

        return Collections.unmodifiableList(validationErrors);
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
