package de.adorsys.xs2a.adapter.adapter.oauth2.adjuster;

import de.adorsys.xs2a.adapter.service.config.AdapterConfig;
import de.adorsys.xs2a.adapter.service.oauth.ParamAdjuster;
import de.adorsys.xs2a.adapter.service.oauth.ParamAdjustingResultHolder;
import org.apache.commons.lang3.StringUtils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.function.Function;
import java.util.regex.Pattern;

import static de.adorsys.xs2a.adapter.service.Oauth2Service.Parameters;

public class CodeChallengeParamAdjuster implements ParamAdjuster {
    private static final String DEFAULT_CODE_VERIFIER_REGEX = "^[\\w\\-._~]{44,127}$";
    private static final Pattern DEFAULT_CODE_VERIFIER_PATTERN
        = Pattern.compile(DEFAULT_CODE_VERIFIER_REGEX);

    private final String aspspDefaultCodeVerifierProperty;
    private final Function<String, String> codeChallengeComputingService;
    private final Pattern codeVerifierPattern;

    public CodeChallengeParamAdjuster(String aspspDefaultCodeVerifierProperty,
                                      Function<String, String> codeChallengeComputingService,
                                      Pattern codeVerifierPattern) {
        this.aspspDefaultCodeVerifierProperty = aspspDefaultCodeVerifierProperty;
        this.codeChallengeComputingService = codeChallengeComputingService;
        this.codeVerifierPattern = codeVerifierPattern;
    }

    public CodeChallengeParamAdjuster(String aspspDefaultCodeVerifierProperty,
                                      Function<String, String> codeChallengeComputingService) {
        this(aspspDefaultCodeVerifierProperty, codeChallengeComputingService, null);
    }

    public CodeChallengeParamAdjuster(Function<String, String> codeChallengeComputingService,
                                      Pattern codeVerifierPattern) {
        this(null, codeChallengeComputingService, codeVerifierPattern);
    }

    public CodeChallengeParamAdjuster(String aspspDefaultCodeVerifierProperty) {
        this(aspspDefaultCodeVerifierProperty, new Sha256CodeChallengeComputingService(), DEFAULT_CODE_VERIFIER_PATTERN);
    }

    public CodeChallengeParamAdjuster(Function<String, String> codeChallengeComputingService) {
        this(null, codeChallengeComputingService, DEFAULT_CODE_VERIFIER_PATTERN);
    }

    public CodeChallengeParamAdjuster(Pattern codeVerifierPattern) {
        this(null, new Sha256CodeChallengeComputingService(), codeVerifierPattern);
    }

    public CodeChallengeParamAdjuster() {
        this(null, new Sha256CodeChallengeComputingService(), DEFAULT_CODE_VERIFIER_PATTERN);
    }

    @Override
    public ParamAdjustingResultHolder adjustParam(ParamAdjustingResultHolder adjustingResultHolder,
                                                  Parameters parametersFromTpp) {
        String codeChallenge = parametersFromTpp.getCodeChallenge();

        if (StringUtils.isBlank(codeChallenge)) {
            String codeVerifier = parametersFromTpp.getCodeVerifier();

            if (StringUtils.isBlank(codeVerifier)) {
                if (aspspDefaultCodeVerifierProperty != null) {
                    codeVerifier = AdapterConfig.readProperty(aspspDefaultCodeVerifierProperty);
                }
            }

            if (codeVerifier != null
                    && codeVerifierPattern.matcher(codeVerifier).find()) {
                codeChallenge = codeChallengeComputingService.apply(codeVerifier);
            }
        }

        if (StringUtils.isNotBlank(codeChallenge)) {
            adjustingResultHolder.addAdjustedParam(Parameters.CODE_CHALLENGE, codeChallenge);
        } else {
            adjustingResultHolder.addMissingParam(Parameters.CODE_CHALLENGE);
        }

        return adjustingResultHolder;
    }

    static class Sha256CodeChallengeComputingService implements Function<String, String> {
        private static final String SHA_256_HASHING_ALGORITHM = "SHA-256";

        @Override
        public String apply(String codeVerifier) {
            byte[] bytes = codeVerifier.getBytes(StandardCharsets.US_ASCII);
            MessageDigest md;

            try {
                md = MessageDigest.getInstance(SHA_256_HASHING_ALGORITHM);
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }

            md.update(bytes, 0, bytes.length);
            byte[] digest = md.digest();
            return Base64.getEncoder().encodeToString(digest);
        }
    }
}
