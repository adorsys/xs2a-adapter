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

package de.adorsys.xs2a.adapter.ing;

import de.adorsys.xs2a.adapter.api.Oauth2Service;
import de.adorsys.xs2a.adapter.api.http.Interceptor;
import de.adorsys.xs2a.adapter.api.model.Scope;
import de.adorsys.xs2a.adapter.api.validation.ValidationError;
import de.adorsys.xs2a.adapter.impl.http.StringUri;
import de.adorsys.xs2a.adapter.ing.model.IngApplicationTokenResponse;
import de.adorsys.xs2a.adapter.ing.model.IngAuthorizationURLResponse;
import de.adorsys.xs2a.adapter.ing.model.IngTokenResponse;
import org.apache.commons.lang3.StringUtils;

import java.net.URI;
import java.util.*;

import static de.adorsys.xs2a.adapter.api.Oauth2Service.ResponseType.CODE;
import static de.adorsys.xs2a.adapter.api.validation.Validation.requireValid;

public class IngOauth2Service {
    private static final String AIS_TRANSACTIONS_SCOPE = "payment-accounts:transactions:view";
    private static final String AIS_BALANCES_SCOPE = "payment-accounts:balances:view";
    private static final String PIS_SCOPE = "payment-accounts:balances:view";
    private static final EnumMap<Scope, String> SCOPE_MAPPING = initiateScopeMapping();

    protected static final String UNSUPPORTED_SCOPE_VALUE_ERROR_MESSAGE = "Scope value [%s] is not supported";

    private final IngOauth2Api oauth2Api;
    private final IngClientAuthenticationFactory clientAuthenticationFactory;
    private final List<Interceptor> interceptors;

    private IngApplicationTokenResponse applicationToken;

    public IngOauth2Service(IngOauth2Api oauth2Api,
                            IngClientAuthenticationFactory clientAuthenticationFactory,
                            List<Interceptor> interceptors) {
        this.oauth2Api = oauth2Api;
        this.clientAuthenticationFactory = clientAuthenticationFactory;
        this.interceptors = interceptors;
    }

    private static EnumMap<Scope, String> initiateScopeMapping() {
        EnumMap<Scope, String> scopeMapping = new EnumMap<>(Scope.class);
        scopeMapping.put(Scope.AIS_TRANSACTIONS, AIS_TRANSACTIONS_SCOPE);
        scopeMapping.put(Scope.AIS_BALANCES, AIS_BALANCES_SCOPE);
        scopeMapping.put(Scope.AIS, AIS_TRANSACTIONS_SCOPE + " " + AIS_BALANCES_SCOPE);
        scopeMapping.put(Scope.PIS, PIS_SCOPE);
        return scopeMapping;
    }

    public URI getAuthorizationRequestUri(Oauth2Service.Parameters parameters)  {
        requireValid(validateScope(parameters.getScope()));
        parameters.setScope(mapScope(parameters.getScope()));

        IngClientAuthentication clientAuthentication = getClientAuthentication();
        IngAuthorizationURLResponse authorizationUrlResponse =
            oauth2Api.getAuthorizationUrl(addInterceptor(clientAuthentication),
                parameters.getScope(),
                parameters.getRedirectUri())
            .getBody();

        parameters.setClientId(getClientId());
        parameters.setResponseType(CODE.toString());

        return URI.create(StringUri.withQuery(authorizationUrlResponse.getLocation(), parameters.asMap()));
    }

    private List<ValidationError> validateScope(String scope) {
        List<ValidationError> validationErrors = new ArrayList<>();

        if (StringUtils.isBlank(scope)) {
            validationErrors.add(ValidationError.required(Oauth2Service.Parameters.SCOPE));
        } else if (!Scope.contains(scope)) {
            validationErrors.add(new ValidationError(ValidationError.Code.NOT_SUPPORTED,
                Oauth2Service.Parameters.SCOPE,
                String.format(UNSUPPORTED_SCOPE_VALUE_ERROR_MESSAGE, scope)));
        }

        return validationErrors;
    }

    private String mapScope(String scope) {
        return SCOPE_MAPPING.get(Scope.fromValue(scope));
    }

    public IngClientAuthentication getClientAuthentication() {
        return clientAuthenticationFactory.newClientAuthentication(getApplicationToken());
    }

    private IngApplicationTokenResponse getApplicationToken() {
        if (applicationToken != null) {
            return applicationToken;
        }

        IngClientAuthentication clientAuthentication =
            clientAuthenticationFactory.newClientAuthenticationForApplicationToken();
        applicationToken = oauth2Api.getApplicationToken(addInterceptor(clientAuthentication))
            .getBody();
        return applicationToken;
    }

    private String getClientId() {
        return getApplicationToken().getClientId();
    }

    public IngTokenResponse getToken(Oauth2Service.Parameters parameters) {
        IngClientAuthentication clientAuthentication = getClientAuthentication();
        return oauth2Api.getCustomerToken(parameters, addInterceptor(clientAuthentication))
            .getBody();
    }

    public IngClientAuthentication getClientAuthentication(String accessToken) {
        return clientAuthenticationFactory.newClientAuthentication(getApplicationToken(), accessToken);
    }

    private List<Interceptor> addInterceptor(Interceptor interceptor) {
        List<Interceptor> tempList = new ArrayList<>(Optional.ofNullable(interceptors)
                                                        .orElseGet(Collections::emptyList));
        tempList.add(interceptor);
        return Collections.unmodifiableList(tempList);
    }
}
