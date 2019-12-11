package de.adorsys.xs2a.adapter.adapter.oauth2.adjuster.impl;

import de.adorsys.xs2a.adapter.adapter.oauth2.adjuster.ParamConstraint;
import de.adorsys.xs2a.adapter.service.oauth.ParamAdjuster;
import de.adorsys.xs2a.adapter.service.oauth.ParamAdjustingResultHolder;
import org.apache.commons.lang3.StringUtils;

import static de.adorsys.xs2a.adapter.service.Oauth2Service.Parameters;

public class BicParamAdjuster implements ParamAdjuster {
    private final String bicFromAspsp;
    private final ParamConstraint constraint;

    private BicParamAdjuster(BicParamAdjusterBuilder builder) {
        this.bicFromAspsp = builder.bicFromAspsp;
        this.constraint = builder.constraint;
    }

    public static BicParamAdjusterBuilder builder() {
        return new BicParamAdjusterBuilder();
    }

    @Override
    public ParamAdjustingResultHolder adjustParam(ParamAdjustingResultHolder adjustingResultHolder,
                                                  Parameters parametersFromTpp) {
        String bic = parametersFromTpp.getBic();

        if (StringUtils.isBlank(bic)) {
            bic = bicFromAspsp;
        }

        if (StringUtils.isNotBlank(bic)) {
            adjustingResultHolder.addAdjustedParam(Parameters.BIC, bic);
        } else {
            if (constraint == ParamConstraint.REQUIRED) {
                adjustingResultHolder.addMissingParam(Parameters.BIC);
            }
        }

        return adjustingResultHolder;
    }

    public static final class BicParamAdjusterBuilder {
        private String bicFromAspsp;
        private ParamConstraint constraint;

        private BicParamAdjusterBuilder() {
        }

        public BicParamAdjusterBuilder bicFromAspsp(String bicFromAspsp) {
            this.bicFromAspsp = bicFromAspsp;
            return this;
        }

        public BicParamAdjusterBuilder constraint(ParamConstraint constraint) {
            this.constraint = constraint;
            return this;
        }

        public BicParamAdjuster build() {
            if (constraint == null) {
                constraint = ParamConstraint.REQUIRED;
            }

            return new BicParamAdjuster(this);
        }
    }
}
