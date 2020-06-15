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

import de.adorsys.xs2a.adapter.adapter.AbstractService;
import de.adorsys.xs2a.adapter.adapter.BaseOauth2Service;
import de.adorsys.xs2a.adapter.adapter.CertificateSubjectClientIdOauth2Service;
import de.adorsys.xs2a.adapter.adapter.PkceOauth2Service;
import de.adorsys.xs2a.adapter.http.HttpClient;
import de.adorsys.xs2a.adapter.http.StringUri;
import de.adorsys.xs2a.adapter.http.UriBuilder;
import de.adorsys.xs2a.adapter.service.exception.BadRequestException;
import de.adorsys.xs2a.adapter.service.model.Aspsp;
import de.adorsys.xs2a.adapter.service.model.Scope;
import de.adorsys.xs2a.adapter.service.model.TokenResponse;
import de.adorsys.xs2a.adapter.validation.ValidationError;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @see <a href="https://xs2a-developer.comdirect.de/content/howto/ais-manage-consents">Authorisation</a>
 */
public class ComdirectOauth2Service extends AbstractService implements Oauth2Service, PkceOauth2Extension {

    private static final String SCA_OAUTH_LINK_MISSING_ERROR_MESSAGE = "SCA OAuth link is missing or has a wrong format: " +
        "it has to be either provided as a request parameter or preconfigured for the current ASPSP";
    protected static final String UNSUPPORTED_SCOPE_VALUE_ERROR_MESSAGE = "Scope value [%s] is not supported";
    protected static final String UNKNOWN_SCOPE_VALUE_ERROR_MESSAGE = "Unknown scope value";
    protected static final String CONSENT_OR_PAYMENT_ID_MISSING_ERROR_MESSAGE = "Either consent id or payment id should be provided";
    protected static final String PAYMENT_ID_MISSING_ERROR_MESSAGE = "Payment id should be provided for pis scope";
    protected static final String CONSENT_ID_MISSING_ERROR_MESSAGE = "Consent id should be provided for ais scope";
    private static final String AIS_SCOPE_PREFIX = "AIS:";
    private static final String PIS_SCOPE_PREFIX = "PIS:";

    private final Oauth2Service oauth2Service;
    private final String baseUrl;
    private final Aspsp aspsp;

    private ComdirectOauth2Service(Oauth2Service oauth2Service, String baseUrl, Aspsp aspsp, HttpClient httpClient) {
        super(httpClient);
        this.oauth2Service = oauth2Service;
        this.baseUrl = baseUrl;
        this.aspsp = aspsp;
    }

    public static ComdirectOauth2Service create(Aspsp aspsp, HttpClient httpClient, Pkcs12KeyStore keyStore) {
        String baseUrl = aspsp.getIdpUrl() != null ? aspsp.getIdpUrl() : aspsp.getUrl();
        BaseOauth2Service baseOauth2Service = new BaseOauth2Service(aspsp, httpClient);
        CertificateSubjectClientIdOauth2Service clientIdOauth2Service =
            new CertificateSubjectClientIdOauth2Service(baseOauth2Service, keyStore);
        return new ComdirectOauth2Service(new PkceOauth2Service(clientIdOauth2Service), baseUrl, aspsp, httpClient);
    }

    @Override
    public URI getAuthorizationRequestUri(Map<String, String> headers, Parameters parameters) throws IOException {
        requireValid(validateGetAuthorizationRequestUri(headers, parameters));

        parameters.setAuthorizationEndpoint(parameters.removeScaOAuthLink());

        return UriBuilder.fromUri(oauth2Service.getAuthorizationRequestUri(headers, parameters))
            .queryParam(Parameters.SCOPE, scope(parameters))
            .build();
    }

    // TODO extract this to some service if the same logic appears for one more adapter https://jira.adorsys.de/browse/XS2AAD-548
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

        String scope = parameters.getScope();
        if (StringUtils.isNotEmpty(scope)) {
            if (!Scope.contains(scope)) {
                validationErrors.add(new ValidationError(ValidationError.Code.NOT_SUPPORTED,
                    Parameters.SCOPE,
                    String.format(UNSUPPORTED_SCOPE_VALUE_ERROR_MESSAGE, scope)));
            } else if (Scope.isAis(Scope.fromValue(scope))) {
                if (StringUtils.isBlank(parameters.getConsentId())) {
                    validationErrors.add(new ValidationError(ValidationError.Code.REQUIRED,
                        Parameters.CONSENT_ID,
                        CONSENT_ID_MISSING_ERROR_MESSAGE));
                }
            } else if (Scope.isPis(Scope.fromValue(scope))) {
                if (StringUtils.isBlank(parameters.getPaymentId())) {
                    validationErrors.add(new ValidationError(ValidationError.Code.REQUIRED,
                        Parameters.PAYMENT_ID,
                        PAYMENT_ID_MISSING_ERROR_MESSAGE));
                }
            }
        }

        return Collections.unmodifiableList(validationErrors);
    }

    private String scope(Parameters parameters) {
        if (StringUtils.isEmpty(parameters.getScope())) {
            return computeScope(parameters);
        } else {
            return mapScope(parameters);
        }
    }

    private String computeScope(Parameters parameters) {
        if (StringUtils.isNotBlank(parameters.getConsentId())) {
            return AIS_SCOPE_PREFIX + parameters.getConsentId();
        }
        if (StringUtils.isNotBlank(parameters.getPaymentId())) {
            return PIS_SCOPE_PREFIX + parameters.getPaymentId();
        }
        throw new BadRequestException(CONSENT_OR_PAYMENT_ID_MISSING_ERROR_MESSAGE);
    }

    private String mapScope(Parameters parameters) {
        Scope scope = Scope.fromValue(parameters.getScope());

        if (Scope.isAis(scope)) {
            return AIS_SCOPE_PREFIX + parameters.getConsentId();
        } else if (Scope.isPis(scope)) {
            return PIS_SCOPE_PREFIX + parameters.getPaymentId();
        }
        throw new BadRequestException(UNKNOWN_SCOPE_VALUE_ERROR_MESSAGE);
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
