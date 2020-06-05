package de.adorsys.xs2a.adapter.service;

import de.adorsys.xs2a.adapter.adapter.AbstractService;
import de.adorsys.xs2a.adapter.adapter.BaseOauth2Service;
import de.adorsys.xs2a.adapter.adapter.CertificateSubjectClientIdOauth2Service;
import de.adorsys.xs2a.adapter.adapter.PkceOauth2Service;
import de.adorsys.xs2a.adapter.http.HttpClient;
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

public class SparkasseOauth2Service extends AbstractService implements Oauth2Service, PkceOauth2Extension {

    protected static final String UNSUPPORTED_SCOPE_VALUE_ERROR_MESSAGE = "Scope value [%s] is not supported";
    protected static final String UNKNOWN_SCOPE_VALUE_ERROR_MESSAGE = "Unknown scope value";
    protected static final String CONSENT_OR_PAYMENT_ID_MISSING_ERROR_MESSAGE = "Either consent id or payment id should be provided";
    protected static final String PAYMENT_ID_MISSING_ERROR_MESSAGE = "Payment id should be provided for pis scope";
    protected static final String CONSENT_ID_MISSING_ERROR_MESSAGE = "Consent id should be provided for ais scope";
    private static final String AIS_SCOPE_PREFIX = "AIS: ";
    private static final String PIS_SCOPE_PREFIX = "PIS: ";

    private final Oauth2Service oauth2Service;

    private SparkasseOauth2Service(Oauth2Service oauth2Service, HttpClient httpClient) {
        super(httpClient);
        this.oauth2Service = oauth2Service;
    }

    public static SparkasseOauth2Service create(Aspsp aspsp, HttpClient httpClient, Pkcs12KeyStore keyStore) {
        BaseOauth2Service baseOauth2Service = new BaseOauth2Service(aspsp, httpClient);
        CertificateSubjectClientIdOauth2Service clientIdOauth2Service =
            new CertificateSubjectClientIdOauth2Service(baseOauth2Service, keyStore);
        PkceOauth2Service pkceOauth2Service = new PkceOauth2Service(clientIdOauth2Service);
        return new SparkasseOauth2Service(pkceOauth2Service, httpClient);
    }

    @Override
    public URI getAuthorizationRequestUri(Map<String, String> headers, Parameters parameters) throws IOException {
        requireValid(validateGetAuthorizationRequestUri(headers, parameters));
        return UriBuilder.fromUri(oauth2Service.getAuthorizationRequestUri(headers, parameters))
            .renameQueryParam(Parameters.RESPONSE_TYPE, "responseType")
            .renameQueryParam(Parameters.CLIENT_ID, "clientId")
            .queryParam("scope", scope(parameters))
            .build();
    }

    // TODO extract this to some service if the same logic appears for one more adapter https://jira.adorsys.de/browse/XS2AAD-548
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
                if (StringUtils.isBlank(parameters.getPaymentId())) {
                    validationErrors.add(new ValidationError(ValidationError.Code.REQUIRED,
                        Parameters.CONSENT_ID,
                        CONSENT_ID_MISSING_ERROR_MESSAGE));
                }
            } else if (Scope.isPis(Scope.fromValue(scope))) {
                if (StringUtils.isBlank(parameters.getConsentId())) {
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
        return oauth2Service.getToken(headers, parameters);
    }
}
