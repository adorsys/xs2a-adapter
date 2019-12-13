package de.adorsys.xs2a.adapter.adapter.oauth2.adjuster.impl;

import de.adorsys.xs2a.adapter.adapter.oauth2.adjuster.ParamConstraint;
import de.adorsys.xs2a.adapter.service.oauth.ParamAdjuster;
import de.adorsys.xs2a.adapter.service.oauth.ParamAdjustingResultHolder;
import org.apache.commons.lang3.StringUtils;

import java.util.Set;

import static de.adorsys.xs2a.adapter.service.Oauth2Service.Parameters;

public class CodeChallengeMethodParamAdjuster implements ParamAdjuster {
    private final Set<String> supportedCodeChallengeMethods;
    private final String defaultCodeChallengeMethod;
    private final ParamConstraint constraint;

    private CodeChallengeMethodParamAdjuster(CodeChallengeMethodParamAdjusterBuilder builder) {
        this.supportedCodeChallengeMethods = builder.supportedCodeChallengeMethods;
        this.defaultCodeChallengeMethod = builder.defaultCodeChallengeMethod;
        this.constraint = builder.constraint;
    }

    public static CodeChallengeMethodParamAdjusterBuilder builder() {
        return new CodeChallengeMethodParamAdjusterBuilder();
    }

    @Override
    public ParamAdjustingResultHolder adjustParam(ParamAdjustingResultHolder adjustingResultHolder,
                                                  Parameters parametersFromTpp) {
        String codeChallengeMethod = parametersFromTpp.getCodeChallengeMethod();

        if (!supportedCodeChallengeMethods.contains(codeChallengeMethod)) {
            codeChallengeMethod = defaultCodeChallengeMethod;
        }

        if (StringUtils.isNotBlank(codeChallengeMethod)) {
            adjustingResultHolder.addAdjustedParam(Parameters.CODE_CHALLENGE_METHOD, codeChallengeMethod);
        } else {
            if (constraint == ParamConstraint.REQUIRED) {
                adjustingResultHolder.addMissingParam(Parameters.CODE_CHALLENGE_METHOD);
            }
        }

        return adjustingResultHolder;
    }

    public static final class CodeChallengeMethodParamAdjusterBuilder {
        private Set<String> supportedCodeChallengeMethods;
        private String defaultCodeChallengeMethod;
        private ParamConstraint constraint;

        private CodeChallengeMethodParamAdjusterBuilder() {
        }

        public CodeChallengeMethodParamAdjusterBuilder supportedCodeChallengeMethods(Set<String> supportedCodeChallengeMethods) {
            this.supportedCodeChallengeMethods = supportedCodeChallengeMethods;
            return this;
        }

        public CodeChallengeMethodParamAdjusterBuilder defaultCodeChallengeMethod(String defaultCodeChallengeMethod) {
            this.defaultCodeChallengeMethod = defaultCodeChallengeMethod;
            return this;
        }

        public CodeChallengeMethodParamAdjusterBuilder constraint(ParamConstraint constraint) {
            this.constraint = constraint;
            return this;
        }

        public CodeChallengeMethodParamAdjuster build() {
            if (constraint == null) {
                constraint = ParamConstraint.REQUIRED;
            }

            return new CodeChallengeMethodParamAdjuster(this);
        }
    }
}
