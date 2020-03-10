package de.adorsys.xs2a.adapter.adapter.oauth2.adjuster.impl;

import de.adorsys.xs2a.adapter.adapter.oauth2.adjuster.ParamConstraint;
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
    private final String aspspDefaultCodeVerifierProperty;
    private final Function<String, String> codeChallengeComputingService;
    private final Pattern codeVerifierPattern;
    private final ParamConstraint constraint;

    private CodeChallengeParamAdjuster(CodeChallengeParamAdjusterBuilder builder) {
        this.aspspDefaultCodeVerifierProperty = builder.aspspDefaultCodeVerifierProperty;
        this.codeChallengeComputingService = builder.codeChallengeComputingService;
        this.codeVerifierPattern = builder.codeVerifierPattern;
        this.constraint = builder.constraint;
    }

    public static CodeChallengeParamAdjusterBuilder builder() {
        return new CodeChallengeParamAdjusterBuilder();
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
                    && (codeVerifierPattern == null || codeVerifierPattern.matcher(codeVerifier).find())) {
                codeChallenge = codeChallengeComputingService.apply(codeVerifier);
            }
        }

        if (StringUtils.isNotBlank(codeChallenge)) {
            adjustingResultHolder.addAdjustedParam(Parameters.CODE_CHALLENGE, codeChallenge);
        } else {
            if (constraint == ParamConstraint.REQUIRED) {
                adjustingResultHolder.addMissingParam(Parameters.CODE_CHALLENGE);
            }
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
            return Base64.getUrlEncoder().withoutPadding().encodeToString(digest);
        }
    }

    public static final class CodeChallengeParamAdjusterBuilder {
        private static final String DEFAULT_CODE_VERIFIER_REGEX = "^[\\w\\-._~]{43,127}$";
        private static final Pattern DEFAULT_CODE_VERIFIER_PATTERN
            = Pattern.compile(DEFAULT_CODE_VERIFIER_REGEX);

        private String aspspDefaultCodeVerifierProperty;
        private Function<String, String> codeChallengeComputingService;
        private Pattern codeVerifierPattern;
        private ParamConstraint constraint;
        private boolean withoutPattern;

        private CodeChallengeParamAdjusterBuilder() {
        }

        public CodeChallengeParamAdjusterBuilder aspspDefaultCodeVerifierProperty(String aspspDefaultCodeVerifierProperty) {
            this.aspspDefaultCodeVerifierProperty = aspspDefaultCodeVerifierProperty;
            return this;
        }

        public CodeChallengeParamAdjusterBuilder codeChallengeComputingService(Function<String, String> codeChallengeComputingService) {
            this.codeChallengeComputingService = codeChallengeComputingService;
            return this;
        }

        public CodeChallengeParamAdjusterBuilder codeVerifierPattern(Pattern codeVerifierPattern) {
            this.codeVerifierPattern = codeVerifierPattern;
            return this;
        }

        public CodeChallengeParamAdjusterBuilder withoutPattern() {
            this.withoutPattern = true;
            return this;
        }

        public CodeChallengeParamAdjusterBuilder constraint(ParamConstraint constraint) {
            this.constraint = constraint;
            return this;
        }

        public CodeChallengeParamAdjuster build() {
            if (codeVerifierPattern == null && !withoutPattern) {
                codeVerifierPattern = DEFAULT_CODE_VERIFIER_PATTERN;
            }

            if (codeChallengeComputingService == null) {
                codeChallengeComputingService = new Sha256CodeChallengeComputingService();
            }

            if (constraint == null) {
                constraint = ParamConstraint.REQUIRED;
            }

            return new CodeChallengeParamAdjuster(this);
        }
    }
}
