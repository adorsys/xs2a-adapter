package de.adorsys.xs2a.adapter.adapter.oauth2.adjuster.impl;

import de.adorsys.xs2a.adapter.adapter.oauth2.adjuster.ParamConstraint;
import de.adorsys.xs2a.adapter.service.oauth.ParamAdjuster;
import de.adorsys.xs2a.adapter.service.oauth.ParamAdjustingResultHolder;
import org.apache.commons.lang3.StringUtils;

import static de.adorsys.xs2a.adapter.service.Oauth2Service.Parameters;

public class StateParamAdjuster implements ParamAdjuster {
    private final ParamConstraint constraint;

    private StateParamAdjuster(StateParamAdjusterBuilder builder) {
        this.constraint = builder.constraint;
    }

    public static StateParamAdjusterBuilder builder() {
        return new StateParamAdjusterBuilder();
    }

    @Override
    public ParamAdjustingResultHolder adjustParam(ParamAdjustingResultHolder adjustingResultHolder,
                                                  Parameters parametersFromTpp) {
        String stateFromTpp = parametersFromTpp.getState();

        if (StringUtils.isNotBlank(stateFromTpp)) {
            adjustingResultHolder.addAdjustedParam(Parameters.STATE, stateFromTpp);
        } else {
            if (constraint == ParamConstraint.REQUIRED) {
                adjustingResultHolder.addMissingParam(Parameters.STATE);
            }
        }

        return adjustingResultHolder;
    }

    public static final class StateParamAdjusterBuilder {
        private ParamConstraint constraint;

        private StateParamAdjusterBuilder() {
        }

        public StateParamAdjusterBuilder constraint(ParamConstraint constraint) {
            this.constraint = constraint;
            return this;
        }

        public StateParamAdjuster build() {
            if (constraint == null) {
                constraint = ParamConstraint.REQUIRED;
            }

            return new StateParamAdjuster(this);
        }
    }
}
