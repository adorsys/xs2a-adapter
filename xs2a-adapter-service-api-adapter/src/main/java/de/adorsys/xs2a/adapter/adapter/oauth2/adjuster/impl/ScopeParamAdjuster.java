package de.adorsys.xs2a.adapter.adapter.oauth2.adjuster.impl;

import de.adorsys.xs2a.adapter.adapter.oauth2.adjuster.ParamConstraint;
import de.adorsys.xs2a.adapter.service.oauth.ParamAdjuster;
import de.adorsys.xs2a.adapter.service.oauth.ParamAdjustingResultHolder;
import org.apache.commons.lang3.StringUtils;

import static de.adorsys.xs2a.adapter.service.Oauth2Service.Parameters;

public class ScopeParamAdjuster implements ParamAdjuster {
    private final String defaultScopeParamValue;
    private final ParamConstraint constraint;

    private ScopeParamAdjuster(ScopeParamAdjusterBuilder builder) {
        this.defaultScopeParamValue = builder.defaultScopeParamValue;
        this.constraint = builder.constraint;
    }

    public static ScopeParamAdjusterBuilder builder() {
        return new ScopeParamAdjusterBuilder();
    }

    @Override
    public ParamAdjustingResultHolder adjustParam(ParamAdjustingResultHolder adjustingResultHolder,
                                                  Parameters parametersFromTpp) {
        String scope = parametersFromTpp.getScope();

        if (StringUtils.isBlank(scope)) {
            scope = defaultScopeParamValue;
        }

        if (StringUtils.isNotBlank(scope)) {
            adjustingResultHolder.addAdjustedParam(Parameters.SCOPE, scope);
        } else {
            if (constraint == ParamConstraint.REQUIRED) {
                adjustingResultHolder.addMissingParam(Parameters.SCOPE);
            }
        }

        return adjustingResultHolder;
    }

    public static final class ScopeParamAdjusterBuilder {
        private String defaultScopeParamValue;
        private ParamConstraint constraint;

        private ScopeParamAdjusterBuilder() {
        }

        public ScopeParamAdjusterBuilder defaultScopeParamValue(String defaultScopeParamValue) {
            this.defaultScopeParamValue = defaultScopeParamValue;
            return this;
        }

        public ScopeParamAdjusterBuilder constraint(ParamConstraint constraint) {
            this.constraint = constraint;
            return this;
        }

        public ScopeParamAdjuster build() {
            if (constraint == null) {
                constraint = ParamConstraint.REQUIRED;
            }

            return new ScopeParamAdjuster(this);
        }
    }
}
