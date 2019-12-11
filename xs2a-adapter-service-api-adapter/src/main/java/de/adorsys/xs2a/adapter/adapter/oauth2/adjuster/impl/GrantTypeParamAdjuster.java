package de.adorsys.xs2a.adapter.adapter.oauth2.adjuster.impl;

import de.adorsys.xs2a.adapter.adapter.oauth2.adjuster.ParamConstraint;
import de.adorsys.xs2a.adapter.service.oauth.ParamAdjuster;
import de.adorsys.xs2a.adapter.service.oauth.ParamAdjustingResultHolder;
import org.apache.commons.lang3.StringUtils;

import static de.adorsys.xs2a.adapter.service.Oauth2Service.Parameters;

public class GrantTypeParamAdjuster implements ParamAdjuster {
    private final String grantTypeRequiredValue;
    private final ParamConstraint constraint;

    private GrantTypeParamAdjuster(GrantTypeParamAdjusterBuilder builder) {
        this.grantTypeRequiredValue = builder.grantTypeRequiredValue;
        this.constraint = builder.constraint;
    }

    public static GrantTypeParamAdjusterBuilder builder() {
        return new GrantTypeParamAdjusterBuilder();
    }

    @Override
    public ParamAdjustingResultHolder adjustParam(ParamAdjustingResultHolder adjustingResultHolder,
                                                  Parameters parametersFromTpp) {
        if (StringUtils.isNotBlank(grantTypeRequiredValue)) {
            adjustingResultHolder.addAdjustedParam(Parameters.GRANT_TYPE, grantTypeRequiredValue);
        } else {
            if (constraint == ParamConstraint.REQUIRED) {
                adjustingResultHolder.addMissingParam(Parameters.GRANT_TYPE);
            }
        }

        return adjustingResultHolder;
    }

    public static final class GrantTypeParamAdjusterBuilder {
        private String grantTypeRequiredValue;
        private ParamConstraint constraint;

        private GrantTypeParamAdjusterBuilder() {
        }

        public GrantTypeParamAdjusterBuilder grantTypeRequiredValue(String grantTypeRequiredValue) {
            this.grantTypeRequiredValue = grantTypeRequiredValue;
            return this;
        }

        public GrantTypeParamAdjusterBuilder constraint(ParamConstraint constraint) {
            this.constraint = constraint;
            return this;
        }

        public GrantTypeParamAdjuster build() {
            if (constraint == null) {
                constraint = ParamConstraint.REQUIRED;
            }

            return new GrantTypeParamAdjuster(this);
        }
    }
}
