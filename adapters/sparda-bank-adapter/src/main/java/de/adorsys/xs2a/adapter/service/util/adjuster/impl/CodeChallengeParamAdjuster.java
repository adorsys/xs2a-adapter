package de.adorsys.xs2a.adapter.service.util.adjuster.impl;

import de.adorsys.xs2a.adapter.service.config.AdapterConfig;
import de.adorsys.xs2a.adapter.service.util.adjuster.ParamAdjuster;
import de.adorsys.xs2a.adapter.service.util.adjuster.ParamAdjustingResultHolder;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.regex.Pattern;

import static de.adorsys.xs2a.adapter.service.Oauth2Service.Parameters;

public class CodeChallengeParamAdjuster implements ParamAdjuster {
    private static final String SPARDA_DEFAULT_CODE_VERIFIER_PROPERTY
        = "sparda.oauth_approach.default_code_verifier";

    private static final String DEFAULT_HASHING_ALGORITHM = "SHA-256";
    private static final String CODE_VERIFIER_REGEX = "^[\\w\\-._~]{44,127}$";
    private static final Pattern CODE_VERIFIER_PATTERN = Pattern.compile(CODE_VERIFIER_REGEX);

    @Override
    public ParamAdjustingResultHolder adjustParam(ParamAdjustingResultHolder adjustingResultHolder,
                                                  Parameters parametersFromTpp) {
        String codeChallenge = parametersFromTpp.getCodeChallenge();

        if (codeChallenge == null || codeChallenge.trim().isEmpty()) {
            String codeVerifier = parametersFromTpp.getCodeVerifier();

            if (codeVerifier == null || codeVerifier.trim().isEmpty()) {
                codeVerifier = AdapterConfig.readProperty(SPARDA_DEFAULT_CODE_VERIFIER_PROPERTY);
            }

            codeChallenge = computeCodeChallenge(codeVerifier);
        }

        if (codeChallenge != null && !codeChallenge.trim().isEmpty()) {
            adjustingResultHolder.addAdjustedParam(Parameters.CODE_CHALLENGE, codeChallenge);
        } else {
            adjustingResultHolder.addMissingParam(Parameters.CODE_CHALLENGE);
        }

        return adjustingResultHolder;
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
