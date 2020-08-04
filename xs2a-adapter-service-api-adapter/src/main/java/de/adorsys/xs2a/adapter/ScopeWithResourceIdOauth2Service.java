package de.adorsys.xs2a.adapter;

import de.adorsys.xs2a.adapter.adapter.Oauth2ServiceDecorator;
import de.adorsys.xs2a.adapter.http.UriBuilder;
import de.adorsys.xs2a.adapter.service.Oauth2Service;
import de.adorsys.xs2a.adapter.service.exception.BadRequestException;
import de.adorsys.xs2a.adapter.service.model.Scope;
import de.adorsys.xs2a.adapter.validation.ValidationError;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ScopeWithResourceIdOauth2Service extends Oauth2ServiceDecorator {
    private static final String UNSUPPORTED_SCOPE_VALUE_ERROR_MESSAGE = "Scope value [%s] is not supported";
    private static final String PAYMENT_ID_MISSING_ERROR_MESSAGE = "Payment id should be provided for pis scope";
    private static final String CONSENT_ID_MISSING_ERROR_MESSAGE = "Consent id should be provided for ais scope";
    private static final String UNKNOWN_SCOPE_VALUE_ERROR_MESSAGE = "Unknown scope value";
    private static final String CONSENT_OR_PAYMENT_ID_MISSING_ERROR_MESSAGE = "Either consent id or payment id should be provided";

    private final String aisScopePrefix;
    private final String pisScopePrefix;

    public ScopeWithResourceIdOauth2Service(Oauth2Service oauth2Service,
                                            String aisScopePrefix,
                                            String pisScopePrefix) {
        super(oauth2Service);
        this.aisScopePrefix = aisScopePrefix;
        this.pisScopePrefix = pisScopePrefix;
    }

    @Override
    public List<ValidationError> validateGetAuthorizationRequestUri(Map<String, String> headers,
                                                                    Parameters parameters) {
        List<ValidationError> validationErrors = new ArrayList<>();

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
            } else if (Scope.isPis(Scope.fromValue(scope)) && StringUtils.isBlank(parameters.getPaymentId())) {
                validationErrors.add(new ValidationError(ValidationError.Code.REQUIRED,
                    Parameters.PAYMENT_ID,
                    PAYMENT_ID_MISSING_ERROR_MESSAGE));
            }
        }

        return Collections.unmodifiableList(validationErrors);
    }

    @Override
    public URI getAuthorizationRequestUri(Map<String, String> headers, Parameters parameters) throws IOException {
        return UriBuilder.fromUri(oauth2Service.getAuthorizationRequestUri(headers, parameters))
            .queryParam("scope", scope(parameters))
            .build();
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
            return aisScopePrefix + parameters.getConsentId();
        }
        if (StringUtils.isNotBlank(parameters.getPaymentId())) {
            return pisScopePrefix + parameters.getPaymentId();
        }
        throw new BadRequestException(CONSENT_OR_PAYMENT_ID_MISSING_ERROR_MESSAGE);
    }

    private String mapScope(Parameters parameters) {
        Scope scope = Scope.fromValue(parameters.getScope());

        if (Scope.isAis(scope)) {
            return aisScopePrefix + parameters.getConsentId();
        } else if (Scope.isPis(scope)) {
            return pisScopePrefix + parameters.getPaymentId();
        }
        throw new BadRequestException(UNKNOWN_SCOPE_VALUE_ERROR_MESSAGE);
    }
}
