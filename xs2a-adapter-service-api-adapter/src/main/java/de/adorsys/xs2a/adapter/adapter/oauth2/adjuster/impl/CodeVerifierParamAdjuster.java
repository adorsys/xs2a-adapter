package de.adorsys.xs2a.adapter.adapter.oauth2.adjuster.impl;

import de.adorsys.xs2a.adapter.adapter.oauth2.adjuster.ParamConstraint;
import de.adorsys.xs2a.adapter.service.Oauth2Service.Parameters;
import de.adorsys.xs2a.adapter.service.config.AdapterConfig;
import de.adorsys.xs2a.adapter.service.oauth.ParamAdjuster;
import de.adorsys.xs2a.adapter.service.oauth.ParamAdjustingResultHolder;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

public class CodeVerifierParamAdjuster implements ParamAdjuster {
    private final String aspspDefaultCodeVerifierProperty;
    private final Pattern codeVerifierPattern;
    private final ParamConstraint constraint;

    private CodeVerifierParamAdjuster(CodeVerifierParamAdjusterBuilder builder) {
        this.aspspDefaultCodeVerifierProperty = builder.aspspDefaultCodeVerifierProperty;
        this.codeVerifierPattern = builder.codeVerifierPattern;
        this.constraint = builder.constraint;
    }

    public static CodeVerifierParamAdjusterBuilder builder() {
        return new CodeVerifierParamAdjusterBuilder();
    }

    @Override
    public ParamAdjustingResultHolder adjustParam(ParamAdjustingResultHolder adjustingResultHolder,
                                                  Parameters parametersFromTpp) {
        String codeVerifier = parametersFromTpp.getCodeVerifier();

        if (StringUtils.isBlank(codeVerifier)) {
            if (aspspDefaultCodeVerifierProperty != null) {
                codeVerifier = AdapterConfig.readProperty(aspspDefaultCodeVerifierProperty);
            }
        }

        if (codeVerifier != null
                && (codeVerifierPattern == null || codeVerifierPattern.matcher(codeVerifier).find())) {
            adjustingResultHolder.addAdjustedParam(Parameters.CODE_VERIFIER, codeVerifier);
        } else {
            if (constraint == ParamConstraint.REQUIRED) {
                adjustingResultHolder.addMissingParam(Parameters.CODE_VERIFIER);
            }
        }

        return adjustingResultHolder;
    }

    public static final class CodeVerifierParamAdjusterBuilder {
        private static final String DEFAULT_CODE_VERIFIER_REGEX = "^[\\w\\-._~]{44,127}$";
        private static final Pattern DEFAULT_CODE_VERIFIER_PATTERN
            = Pattern.compile(DEFAULT_CODE_VERIFIER_REGEX);

        private String aspspDefaultCodeVerifierProperty;
        private Pattern codeVerifierPattern;
        private ParamConstraint constraint;
        private boolean withoutPattern;

        private CodeVerifierParamAdjusterBuilder() {
        }

        public CodeVerifierParamAdjusterBuilder aspspDefaultCodeVerifierProperty(String aspspDefaultCodeVerifierProperty) {
            this.aspspDefaultCodeVerifierProperty = aspspDefaultCodeVerifierProperty;
            return this;
        }

        public CodeVerifierParamAdjusterBuilder codeVerifierPattern(Pattern codeVerifierPattern) {
            this.codeVerifierPattern = codeVerifierPattern;
            return this;
        }

        public CodeVerifierParamAdjusterBuilder withoutPattern() {
            this.withoutPattern = true;
            return this;
        }

        public CodeVerifierParamAdjusterBuilder constraint(ParamConstraint constraint) {
            this.constraint = constraint;
            return this;
        }

        public CodeVerifierParamAdjuster build() {
            if (codeVerifierPattern == null && !withoutPattern) {
                codeVerifierPattern = DEFAULT_CODE_VERIFIER_PATTERN;
            }

            if (constraint == null) {
                constraint = ParamConstraint.REQUIRED;
            }

            return new CodeVerifierParamAdjuster(this);
        }
    }
}
