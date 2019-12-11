package de.adorsys.xs2a.adapter.adapter.oauth2.adjuster.impl;

import de.adorsys.xs2a.adapter.adapter.oauth2.adjuster.ParamConstraint;
import de.adorsys.xs2a.adapter.service.Oauth2Service.Parameters;
import de.adorsys.xs2a.adapter.service.oauth.ParamAdjuster;
import de.adorsys.xs2a.adapter.service.oauth.ParamAdjustingResultHolder;
import org.apache.commons.lang3.StringUtils;

public class CodeParamAdjuster implements ParamAdjuster {
    private final ParamConstraint constraint;

    private CodeParamAdjuster(CodeParamAdjusterBuilder builder) {
        this.constraint = builder.constraint;
    }

    public static CodeParamAdjusterBuilder builder() {
        return new CodeParamAdjusterBuilder();
    }

    @Override
    public ParamAdjustingResultHolder adjustParam(ParamAdjustingResultHolder adjustingResultHolder,
                                                  Parameters parametersFromTpp) {
        String codeFromTpp = parametersFromTpp.getAuthorizationCode();

        if (StringUtils.isNotBlank(codeFromTpp)) {
            adjustingResultHolder.addAdjustedParam(Parameters.CODE, codeFromTpp);
        } else {
            if (constraint == ParamConstraint.REQUIRED) {
                adjustingResultHolder.addMissingParam(Parameters.CODE);
            }
        }

        return adjustingResultHolder;
    }

    public static final class CodeParamAdjusterBuilder {
        private ParamConstraint constraint;

        private CodeParamAdjusterBuilder() {
        }

        public CodeParamAdjusterBuilder constraint(ParamConstraint constraint) {
            this.constraint = constraint;
            return this;
        }

        public CodeParamAdjuster build() {
            if (constraint == null) {
                constraint = ParamConstraint.REQUIRED;
            }

            return new CodeParamAdjuster(this);
        }
    }
}
