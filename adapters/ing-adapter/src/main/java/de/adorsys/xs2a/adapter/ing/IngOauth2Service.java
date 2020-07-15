package de.adorsys.xs2a.adapter.ing;

import de.adorsys.xs2a.adapter.http.StringUri;
import de.adorsys.xs2a.adapter.ing.model.ApplicationTokenResponse;
import de.adorsys.xs2a.adapter.ing.model.AuthorizationURLResponse;
import de.adorsys.xs2a.adapter.ing.model.TokenResponse;
import de.adorsys.xs2a.adapter.service.Oauth2Service;
import de.adorsys.xs2a.adapter.service.model.Scope;
import de.adorsys.xs2a.adapter.validation.ValidationError;
import org.apache.commons.lang3.StringUtils;

import java.net.URI;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

import static de.adorsys.xs2a.adapter.service.Oauth2Service.ResponseType.CODE;
import static de.adorsys.xs2a.adapter.validation.Validation.requireValid;

public class IngOauth2Service {
    private static final String AIS_TRANSACTIONS_SCOPE = "payment-accounts:transactions:view";
    private static final String AIS_BALANCES_SCOPE = "payment-accounts:balances:view";
    private static final String PIS_SCOPE = "payment-accounts:balances:view";
    private static final EnumMap<Scope, String> SCOPE_MAPPING = initiateScopeMapping();

    protected static final String UNSUPPORTED_SCOPE_VALUE_ERROR_MESSAGE = "Scope value [%s] is not supported";

    private final Oauth2Api oauth2Api;
    private final ClientAuthenticationFactory clientAuthenticationFactory;

    private ApplicationTokenResponse applicationToken;

    public IngOauth2Service(Oauth2Api oauth2Api,
                            ClientAuthenticationFactory clientAuthenticationFactory) {
        this.oauth2Api = oauth2Api;
        this.clientAuthenticationFactory = clientAuthenticationFactory;
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

        ClientAuthentication clientAuthentication = getClientAuthentication();
        AuthorizationURLResponse authorizationUrlResponse =
            oauth2Api.getAuthorizationUrl(clientAuthentication,
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

    public ClientAuthentication getClientAuthentication() {
        return clientAuthenticationFactory.newClientAuthentication(getApplicationToken());
    }

    private ApplicationTokenResponse getApplicationToken() {
        if (applicationToken != null) {
            return applicationToken;
        }

        ClientAuthentication clientAuthentication =
            clientAuthenticationFactory.newClientAuthenticationForApplicationToken();
        applicationToken = oauth2Api.getApplicationToken(clientAuthentication)
            .getBody();
        return applicationToken;
    }

    private String getClientId() {
        return getApplicationToken().getClientId();
    }

    public TokenResponse getToken(Oauth2Service.Parameters parameters) {
        ClientAuthentication clientAuthentication = getClientAuthentication();
        return oauth2Api.getCustomerToken(parameters, clientAuthentication)
            .getBody();
    }

    public ClientAuthentication getClientAuthentication(String accessToken) {
        return clientAuthenticationFactory.newClientAuthentication(getApplicationToken(), accessToken);
    }
}
