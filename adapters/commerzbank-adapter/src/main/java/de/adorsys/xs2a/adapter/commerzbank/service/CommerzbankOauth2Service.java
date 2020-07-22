package de.adorsys.xs2a.adapter.commerzbank.service;

import de.adorsys.xs2a.adapter.adapter.BaseOauth2Service;
import de.adorsys.xs2a.adapter.adapter.CertificateSubjectClientIdOauth2Service;
import de.adorsys.xs2a.adapter.adapter.PkceOauth2Service;
import de.adorsys.xs2a.adapter.http.HttpClient;
import de.adorsys.xs2a.adapter.http.StringUri;
import de.adorsys.xs2a.adapter.http.UriBuilder;
import de.adorsys.xs2a.adapter.service.Oauth2Service;
import de.adorsys.xs2a.adapter.service.PkceOauth2Extension;
import de.adorsys.xs2a.adapter.service.Pkcs12KeyStore;
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

import static de.adorsys.xs2a.adapter.validation.Validation.requireValid;

/**
 * @see <a href="https://psd2.developer.commerzbank.com/content/howto/ais-manage-consents">OAuth2 authorisation</a>
 * @see <a href="https://psd2.developer.commerzbank.com/content/howto/sandbox">2 - Authorize the payment</a>
 */
public class CommerzbankOauth2Service implements Oauth2Service, PkceOauth2Extension {

    protected static final String UNSUPPORTED_SCOPE_VALUE_ERROR_MESSAGE = "Scope value [%s] is not supported";
    protected static final String UNKNOWN_SCOPE_VALUE_ERROR_MESSAGE = "Unknown scope value";
    protected static final String CONSENT_OR_PAYMENT_ID_MISSING_ERROR_MESSAGE = "Either consent id or payment id should be provided";
    protected static final String PAYMENT_ID_MISSING_ERROR_MESSAGE = "Payment id should be provided for pis scope";
    protected static final String CONSENT_ID_MISSING_ERROR_MESSAGE = "Consent id should be provided for ais scope";
    private static final String AIS_SCOPE_PREFIX = "AIS:";
    private static final String PIS_SCOPE_PREFIX = "PIS:";

    private final Oauth2Service oauth2Service;
    private final String baseUrl;

    private CommerzbankOauth2Service(Oauth2Service oauth2Service, String baseUrl) {
        this.oauth2Service = oauth2Service;
        this.baseUrl = baseUrl;
    }

    public static CommerzbankOauth2Service create(Aspsp aspsp, HttpClient httpClient, Pkcs12KeyStore keyStore) {
        String baseUrl = aspsp.getIdpUrl() != null ? aspsp.getIdpUrl() : aspsp.getUrl();
        BaseOauth2Service baseOauth2Service = new BaseOauth2Service(aspsp, httpClient);
        CertificateSubjectClientIdOauth2Service clientIdOauth2Service =
            new CertificateSubjectClientIdOauth2Service(baseOauth2Service, keyStore);
        return new CommerzbankOauth2Service(new PkceOauth2Service(clientIdOauth2Service), baseUrl);
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
}
