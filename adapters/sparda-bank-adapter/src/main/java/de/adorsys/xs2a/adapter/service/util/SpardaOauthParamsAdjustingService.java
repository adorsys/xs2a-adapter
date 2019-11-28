package de.adorsys.xs2a.adapter.service.util;

import de.adorsys.xs2a.adapter.service.Pkcs12KeyStore;
import de.adorsys.xs2a.adapter.service.config.AdapterConfig;
import de.adorsys.xs2a.adapter.service.exception.BadRequestException;
import de.adorsys.xs2a.adapter.service.model.Aspsp;

import java.nio.charset.StandardCharsets;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.regex.Pattern;

import static de.adorsys.xs2a.adapter.service.Oauth2Service.Parameters;

public class SpardaOauthParamsAdjustingService {
    private static final String DEFAULT_SCOPE_PARAM_VALUE = "ais";
    private static final String DEFAULT_HASHING_ALGORITHM = "SHA-256";
    private static final String DEFAULT_RESPONSE_TYPE_PARAM_VALUE = "code";
    private static final String DEFAULT_GRANT_TYPE_PARAM_VALUE_FOR_GET_TOKEN_REQUEST = "authorization_code";

    private static final Set<String> SUPPORTED_CODE_CHALLENGE_METHODS
        = Collections.unmodifiableSet(new HashSet<>(Collections.singletonList("S256")));

    private static final String CODE_VERIFIER_REGEX = "^[\\w\\-._~]{44,127}$";
    private static final Pattern CODE_VERIFIER_PATTERN = Pattern.compile(CODE_VERIFIER_REGEX);
    private static final String SPARDA_DEFAULT_CODE_VERIFIER_PROPERTY
        = "sparda.oauth_approach.default_code_verifier";

    private static final String QUERY_PARAMETERS_MISSING_ERROR_MESSAGE
        = "The following query parameters are missing: %s";

    private final Aspsp aspsp;
    private final Pkcs12KeyStore keyStore;

    public SpardaOauthParamsAdjustingService(Aspsp aspsp, Pkcs12KeyStore keyStore) {
        this.aspsp = aspsp;
        this.keyStore = keyStore;
    }

    public Parameters adjustForGetAuthorizationRequest(Parameters parametersFromTpp) {
        Map<String, String> parametersMap = new HashMap<>();
        Set<String> missingParameters = new HashSet<>();

        adjustBic(parametersFromTpp.getBic(), parametersMap, missingParameters);
        adjustClientId(parametersFromTpp.getClientId(), parametersMap);
        adjustRedirectUri(parametersFromTpp.getRedirectUri(), parametersMap, missingParameters);
        adjustScope(parametersFromTpp.getScope(), parametersMap);
        adjustState(parametersFromTpp.getState(), parametersMap);
        adjustResponseType(parametersMap);
        adjustCodeChallenge(parametersFromTpp.getCodeChallenge(), parametersFromTpp.getCodeVerifier(),
            parametersMap, missingParameters);
        adjustCodeChallengeMethod(parametersFromTpp.getCodeChallengeMethod(), parametersMap);

        if (!missingParameters.isEmpty()) {
            throw new BadRequestException(String.format(QUERY_PARAMETERS_MISSING_ERROR_MESSAGE, missingParameters));
        }

        return new Parameters(parametersMap);
    }

    public Parameters adjustForGetTokenRequest(Parameters parametersFromTpp) {
        Map<String, String> parametersMap = new HashMap<>();
        Set<String> missingParameters = new HashSet<>();

        adjustGrantType(DEFAULT_GRANT_TYPE_PARAM_VALUE_FOR_GET_TOKEN_REQUEST, parametersMap);
        adjustClientId(parametersFromTpp.getClientId(), parametersMap);
        adjustRedirectUri(parametersFromTpp.getRedirectUri(), parametersMap, missingParameters);
        adjustCode(parametersFromTpp.getAuthorizationCode(), parametersMap, missingParameters);
        adjustCodeVerifier(parametersFromTpp.getCodeVerifier(), parametersMap, missingParameters);

        if (!missingParameters.isEmpty()) {
            throw new BadRequestException(String.format(QUERY_PARAMETERS_MISSING_ERROR_MESSAGE, missingParameters));
        }

        return new Parameters(parametersMap);
    }

    private void adjustBic(String bicFromTpp,
                           Map<String, String> parametersMap,
                           Set<String> missingParameters) {
        String bic = bicFromTpp;

        if (bic == null || bic.trim().isEmpty()) {
            bic = aspsp.getBic();
        }

        if (bic != null && !bic.trim().isEmpty()) {
            parametersMap.put(Parameters.BIC, bic);
        } else {
            missingParameters.add(Parameters.BIC);
        }
    }

    private void adjustClientId(String clientIdValueFromTpp,
                                Map<String, String> parametersMap) {
        String clientId = clientIdValueFromTpp;

        if (clientId == null || clientId.trim().isEmpty()) {
            try {
                clientId = keyStore.getOrganizationIdentifier();
            } catch (KeyStoreException e) {
                throw new RuntimeException(e);
            }
        }

        parametersMap.put(Parameters.CLIENT_ID, clientId);
    }

    private void adjustRedirectUri(String redirectUriFromTpp,
                                   Map<String, String> parametersMap,
                                   Set<String> missingParameters) {
        if (redirectUriFromTpp != null && !redirectUriFromTpp.trim().isEmpty()) {
            parametersMap.put(Parameters.REDIRECT_URI, redirectUriFromTpp);
        } else {
            missingParameters.add(Parameters.REDIRECT_URI);
        }
    }

    private void adjustScope(String scopeFromTpp,
                             Map<String, String> parametersMap) {
        String scope = scopeFromTpp;

        if (scope == null || scope.trim().isEmpty()) {
            scope = DEFAULT_SCOPE_PARAM_VALUE;
        }

        parametersMap.put(Parameters.SCOPE, scope);
    }

    private void adjustState(String stateFromTpp,
                             Map<String, String> parametersMap) {
        if (stateFromTpp != null && !stateFromTpp.trim().isEmpty()) {
            parametersMap.put(Parameters.STATE, stateFromTpp);
        }
    }

    private void adjustResponseType(Map<String, String> parametersMap) {
        parametersMap.put(Parameters.RESPONSE_TYPE, DEFAULT_RESPONSE_TYPE_PARAM_VALUE);
    }

    private void adjustCodeChallenge(String codeChallengeFromTpp,
                                     String codeVerifierFromTpp,
                                     Map<String, String> parametersMap,
                                     Set<String> missingParameters) {
        String codeChallenge = codeChallengeFromTpp;

        if (codeChallenge == null || codeChallenge.trim().isEmpty()) {
            String codeVerifier = codeVerifierFromTpp;

            if (codeVerifier == null || codeVerifier.trim().isEmpty()) {
                codeVerifier = AdapterConfig.readProperty(SPARDA_DEFAULT_CODE_VERIFIER_PROPERTY);
            }

            codeChallenge = computeCodeChallenge(codeVerifier);
        }

        if (codeChallenge != null && !codeChallenge.trim().isEmpty()) {
            parametersMap.put(Parameters.CODE_CHALLENGE, codeChallenge);
        } else {
            missingParameters.add(Parameters.CODE_CHALLENGE);
        }
    }

    private void adjustCodeChallengeMethod(String codeChallengeMethodFromTpp,
                                           Map<String, String> parametersMap) {
        if (SUPPORTED_CODE_CHALLENGE_METHODS.contains(codeChallengeMethodFromTpp)) {
            parametersMap.put(Parameters.CODE_CHALLENGE_METHOD, codeChallengeMethodFromTpp);
        }
    }

    private void adjustGrantType(String grantTypeRequiredValue,
                                 Map<String, String> parametersMap) {
        parametersMap.put(Parameters.GRANT_TYPE, grantTypeRequiredValue);
    }

    private void adjustCode(String codeFromTpp,
                            Map<String, String> parametersMap,
                            Set<String> missingParameters) {
        if (codeFromTpp != null && !codeFromTpp.trim().isEmpty()) {
            parametersMap.put(Parameters.CODE, codeFromTpp);
        } else {
            missingParameters.add(Parameters.CODE);
        }
    }

    private void adjustCodeVerifier(String codeVerifierFromTpp,
                                    Map<String, String> parametersMap,
                                    Set<String> missingParameters) {
        String codeVerifier = codeVerifierFromTpp;

        if (codeVerifier == null || codeVerifier.trim().isEmpty()) {
            codeVerifier = AdapterConfig.readProperty(SPARDA_DEFAULT_CODE_VERIFIER_PROPERTY);
        }

        if (codeVerifier != null && CODE_VERIFIER_PATTERN.matcher(codeVerifier).find()) {
            parametersMap.put(Parameters.CODE_VERIFIER, codeVerifier);
        } else {
            missingParameters.add(Parameters.CODE_VERIFIER);
        }
    }

    private String computeCodeChallenge(String codeVerifier) {
        if (codeVerifier == null
                || !CODE_VERIFIER_PATTERN.matcher(codeVerifier).find()) {
            return null;
        }

        byte[] bytes = codeVerifier.getBytes(StandardCharsets.US_ASCII);
        MessageDigest md;

        try {
            md = MessageDigest.getInstance(DEFAULT_HASHING_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        md.update(bytes, 0, bytes.length);
        byte[] digest = md.digest();
        return Base64.getEncoder().encodeToString(digest);
    }
}
