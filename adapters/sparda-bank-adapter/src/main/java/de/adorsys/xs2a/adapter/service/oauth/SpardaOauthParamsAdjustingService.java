package de.adorsys.xs2a.adapter.service.oauth;

import de.adorsys.xs2a.adapter.adapter.oauth2.adjuster.impl.*;
import de.adorsys.xs2a.adapter.service.Pkcs12KeyStore;
import de.adorsys.xs2a.adapter.service.model.Aspsp;

import java.security.KeyStoreException;
import java.util.Collections;
import java.util.HashSet;

import static de.adorsys.xs2a.adapter.adapter.oauth2.adjuster.ParamConstraint.OPTIONAL;
import static de.adorsys.xs2a.adapter.adapter.oauth2.adjuster.ParamConstraint.REQUIRED;
import static de.adorsys.xs2a.adapter.service.Oauth2Service.Parameters;

public class SpardaOauthParamsAdjustingService implements OauthParamsAdjustingService {
    private static final String SPARDA_DEFAULT_CODE_VERIFIER_PROPERTY
        = "sparda.oauth_approach.default_code_verifier";
    private static final String DEFAULT_GRANT_TYPE_PARAM_VALUE_FOR_GET_TOKEN_REQUEST
        = "authorization_code";
    private static final String DEFAULT_GRANT_TYPE_PARAM_VALUE_FOR_REFRESH_REQUEST
        = "refresh_token";
    private static final String DEFAULT_RESPONSE_TYPE_PARAM_VALUE = "code";
    private static final String DEFAULT_SCOPE_PARAM_VALUE = "ais";
    private static final String DEFAULT_CODE_CHALLENGE_METHOD = "S256";

    private final ParamAdjuster authorisationRequestParamAdjuster;
    private final ParamAdjuster tokenRequestParamAdjuster;
    private final ParamAdjuster refreshTokenRequestParamAdjuster;

    public SpardaOauthParamsAdjustingService(Aspsp aspsp, Pkcs12KeyStore keyStore) {
        String organizationIdentifier;
        try {
            organizationIdentifier = keyStore.getOrganizationIdentifier();
        } catch (KeyStoreException e) {
            throw new RuntimeException(e);
        }

        BicParamAdjuster bicParamAdjuster = BicParamAdjuster.builder()
                                                .bicFromAspsp(aspsp.getBic())
                                                .constraint(REQUIRED)
                                                .build();

        ClientIdParamAdjuster clientIdParamAdjuster = ClientIdParamAdjuster.builder()
                                                          .clientIdFromCertificate(organizationIdentifier)
                                                          .constraint(REQUIRED)
                                                          .build();

        RedirectUriParamAdjuster redirectUriParamAdjuster = RedirectUriParamAdjuster.builder()
                                                                .constraint(REQUIRED)
                                                                .build();

        ScopeParamAdjuster scopeParamAdjuster = ScopeParamAdjuster.builder()
                                                    .defaultScopeParamValue(DEFAULT_SCOPE_PARAM_VALUE)
                                                    .constraint(REQUIRED)
                                                    .build();

        StateParamAdjuster stateParamAdjuster = StateParamAdjuster.builder()
                                                    .constraint(OPTIONAL)
                                                    .build();

        ResponseTypeParamAdjuster responseTypeParamAdjuster
            = ResponseTypeParamAdjuster.builder()
                  .defaultResponseTypeParamValue(DEFAULT_RESPONSE_TYPE_PARAM_VALUE)
                  .constraint(REQUIRED)
                  .build();

        CodeChallengeParamAdjuster codeChallengeParamAdjuster
            = CodeChallengeParamAdjuster.builder()
                  .aspspDefaultCodeVerifierProperty(SPARDA_DEFAULT_CODE_VERIFIER_PROPERTY)
                  .constraint(REQUIRED)
                  .build();

        CodeChallengeMethodParamAdjuster codeChallengeMethodParamAdjuster
            = CodeChallengeMethodParamAdjuster.builder()
                  .supportedCodeChallengeMethods(new HashSet<>(Collections.singletonList("S256")))
                  .defaultCodeChallengeMethod(DEFAULT_CODE_CHALLENGE_METHOD)
                  .constraint(OPTIONAL)
                  .build();

        GrantTypeParamAdjuster grantTypeParamAdjusterForGetTokenRequest
            = GrantTypeParamAdjuster.builder()
                  .grantTypeRequiredValue(DEFAULT_GRANT_TYPE_PARAM_VALUE_FOR_GET_TOKEN_REQUEST)
                  .constraint(REQUIRED)
                  .build();

        CodeParamAdjuster codeParamAdjuster = CodeParamAdjuster.builder()
                                                  .constraint(REQUIRED)
                                                  .build();

        CodeVerifierParamAdjuster codeVerifierParamAdjuster
            = CodeVerifierParamAdjuster.builder()
                  .aspspDefaultCodeVerifierProperty(SPARDA_DEFAULT_CODE_VERIFIER_PROPERTY)
                  .constraint(REQUIRED)
                  .build();

        GrantTypeParamAdjuster grantTypeParamAdjusterForRefreshTokenRequest
            = GrantTypeParamAdjuster.builder()
                  .grantTypeRequiredValue(DEFAULT_GRANT_TYPE_PARAM_VALUE_FOR_REFRESH_REQUEST)
                  .constraint(REQUIRED)
                  .build();

        RefreshTokenParamAdjuster refreshTokenParamAdjuster = RefreshTokenParamAdjuster.builder()
                                                                  .constraint(REQUIRED)
                                                                  .build();

        this.authorisationRequestParamAdjuster
            = bicParamAdjuster
                  .andThen(clientIdParamAdjuster)
                  .andThen(redirectUriParamAdjuster)
                  .andThen(scopeParamAdjuster)
                  .andThen(stateParamAdjuster)
                  .andThen(responseTypeParamAdjuster)
                  .andThen(codeChallengeParamAdjuster)
                  .andThen(codeChallengeMethodParamAdjuster);

        this.tokenRequestParamAdjuster
            = grantTypeParamAdjusterForGetTokenRequest
                  .andThen(clientIdParamAdjuster)
                  .andThen(redirectUriParamAdjuster)
                  .andThen(codeParamAdjuster)
                  .andThen(codeVerifierParamAdjuster);

        this.refreshTokenRequestParamAdjuster
            = grantTypeParamAdjusterForRefreshTokenRequest
                  .andThen(clientIdParamAdjuster)
                  .andThen(refreshTokenParamAdjuster);
    }

    @Override
    public Parameters adjustForGetAuthorizationRequest(Parameters parametersFromTpp) {
        return adjustParams(parametersFromTpp, authorisationRequestParamAdjuster);
    }

    @Override
    public Parameters adjustForGetTokenRequest(Parameters parametersFromTpp) {
        return adjustParams(parametersFromTpp, tokenRequestParamAdjuster);
    }

    @Override
    public Parameters adjustForRefreshTokenRequest(Parameters parametersFromTpp) {
        return adjustParams(parametersFromTpp, refreshTokenRequestParamAdjuster);
    }
}
