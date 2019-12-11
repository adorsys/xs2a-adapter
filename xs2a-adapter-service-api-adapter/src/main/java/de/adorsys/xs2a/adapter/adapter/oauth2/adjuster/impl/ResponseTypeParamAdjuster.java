package de.adorsys.xs2a.adapter.adapter.oauth2.adjuster.impl;

import de.adorsys.xs2a.adapter.adapter.oauth2.adjuster.ParamConstraint;
import de.adorsys.xs2a.adapter.service.oauth.ParamAdjuster;
import de.adorsys.xs2a.adapter.service.oauth.ParamAdjustingResultHolder;
import org.apache.commons.lang3.StringUtils;

import static de.adorsys.xs2a.adapter.service.Oauth2Service.Parameters;

public class ResponseTypeParamAdjuster implements ParamAdjuster {
    private final String defaultResponseTypeParamValue;
    private final ParamConstraint constraint;

    private ResponseTypeParamAdjuster(ResponseTypeParamAdjusterBuilder builder) {
        this.defaultResponseTypeParamValue = builder.defaultResponseTypeParamValue;
        this.constraint = builder.constraint;
    }

    public static ResponseTypeParamAdjusterBuilder builder() {
        return new ResponseTypeParamAdjusterBuilder();
    }

    @Override
    public ParamAdjustingResultHolder adjustParam(ParamAdjustingResultHolder adjustingResultHolder,
                                                  Parameters parametersFromTpp) {
        if (StringUtils.isNotBlank(defaultResponseTypeParamValue)) {
            adjustingResultHolder.addAdjustedParam(Parameters.RESPONSE_TYPE, defaultResponseTypeParamValue);
        } else {
            if (constraint == ParamConstraint.REQUIRED) {
                adjustingResultHolder.addMissingParam(Parameters.RESPONSE_TYPE);
            }
        }

        return adjustingResultHolder;
    }

    public static final class ResponseTypeParamAdjusterBuilder {
        private String defaultResponseTypeParamValue;
        private ParamConstraint constraint;

        private ResponseTypeParamAdjusterBuilder() {
        }

        public ResponseTypeParamAdjusterBuilder defaultResponseTypeParamValue(String defaultResponseTypeParamValue) {
            this.defaultResponseTypeParamValue = defaultResponseTypeParamValue;
            return this;
        }

        public ResponseTypeParamAdjusterBuilder constraint(ParamConstraint constraint) {
            this.constraint = constraint;
            return this;
        }

        public ResponseTypeParamAdjuster build() {
            if (constraint == null) {
                constraint = ParamConstraint.REQUIRED;
            }

            return new ResponseTypeParamAdjuster(this);
        }
    }
}
