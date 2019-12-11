package de.adorsys.xs2a.adapter.service.oauth;

import de.adorsys.xs2a.adapter.adapter.oauth2.adjuster.*;
import de.adorsys.xs2a.adapter.adapter.oauth2.adjuster.impl.*;
import de.adorsys.xs2a.adapter.service.Oauth2Service.Parameters;
import de.adorsys.xs2a.adapter.service.Pkcs12KeyStore;

import java.security.KeyStoreException;
import java.util.Map;

public class SparkasseOauthParamsAdjustingService implements OauthParamsAdjustingService {
    private static final String SPARKASSE_DEFAULT_CODE_VERIFIER_PROPERTY
        = "sparkasse.oauth_approach.default_code_verifier";
    private static final String GRANT_TYPE_PARAM_VALUE_FOR_GET_TOKEN_REQUEST
        = "authorization_code";
    private static final String CLIENT_ID_PARAM_NAME_FOR_GET_AUTHORISATION_REQUEST
        = "clientId";
    private static final String RESPONSE_TYPE_PARAM_NAME_FOR_GET_AUTHORISATION_REQUEST
        = "responseType";

    private final ParamAdjuster authorisationRequestParamAdjuster;
    private final ParamAdjuster tokenRequestParamAdjuster;

    public SparkasseOauthParamsAdjustingService(Pkcs12KeyStore keyStore) {
        String organizationIdentifier;
        try {
            organizationIdentifier = keyStore.getOrganizationIdentifier();
        } catch (KeyStoreException e) {
            throw new RuntimeException(e);
        }

        this.authorisationRequestParamAdjuster
            = new ResponseTypeParamAdjuster()
                  .andThen(new ClientIdParamAdjuster(organizationIdentifier))
                  .andThen(new ScopeParamAdjuster())
                  .andThen(new StateParamAdjuster())
                  .andThen(new CodeChallengeParamAdjuster(SPARKASSE_DEFAULT_CODE_VERIFIER_PROPERTY))
                  .andThen(new CodeChallengeMethodParamAdjuster());

        this.tokenRequestParamAdjuster
            = new CodeParamAdjuster()
                  .andThen(new ClientIdParamAdjuster(organizationIdentifier))
                  .andThen(new CodeVerifierParamAdjuster(SPARKASSE_DEFAULT_CODE_VERIFIER_PROPERTY))
                  .andThen(new GrantTypeParamAdjuster(GRANT_TYPE_PARAM_VALUE_FOR_GET_TOKEN_REQUEST));
    }

    @Override
    public Parameters adjustForGetAuthorizationRequest(Parameters parametersFromTpp) {
        Parameters parameters = adjustParams(parametersFromTpp, authorisationRequestParamAdjuster);
        return adjustParamsNaming(parameters);
    }

    @Override
    public Parameters adjustForGetTokenRequest(Parameters parametersFromTpp) {
        return adjustParams(parametersFromTpp, tokenRequestParamAdjuster);
    }

    @Override
    public Parameters adjustForRefreshTokenRequest(Parameters parametersFromTpp) {
        throw new UnsupportedOperationException();
    }

    // needed as according to Sparkasse documentation,
    // there is a difference in params naming for get authorisation requests
    // from OAuth2 specification namings:
    // 1. clientId vs client_id
    // 2. responseType vs response_type
    private Parameters adjustParamsNaming(Parameters parameters) {
        Map<String, String> parametersMap = parameters.asMap();

        if (parametersMap.containsKey(Parameters.CLIENT_ID)) {
            adjustClientIdNaming(parametersMap);
        }

        if (parametersMap.containsKey(Parameters.RESPONSE_TYPE)) {
            adjustResponseTypeNaming(parametersMap);
        }

        return new Parameters(parametersMap);
    }

    private void adjustClientIdNaming(Map<String, String> parametersMap) {
        adjustParamNaming(parametersMap, Parameters.CLIENT_ID,
            CLIENT_ID_PARAM_NAME_FOR_GET_AUTHORISATION_REQUEST);
    }

    private void adjustResponseTypeNaming(Map<String, String> parametersMap) {
        adjustParamNaming(parametersMap, Parameters.RESPONSE_TYPE,
            RESPONSE_TYPE_PARAM_NAME_FOR_GET_AUTHORISATION_REQUEST);
    }

    private void adjustParamNaming(Map<String, String> parametersMap,
                                   String specificationParamName,
                                   String customParamName) {
        String paramValue = parametersMap.get(specificationParamName);
        parametersMap.remove(specificationParamName);
        parametersMap.put(customParamName, paramValue);
    }
}
