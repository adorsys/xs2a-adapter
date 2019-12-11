package de.adorsys.xs2a.adapter.adapter.oauth2.adjuster.impl;

import de.adorsys.xs2a.adapter.adapter.oauth2.adjuster.ParamConstraint;
import de.adorsys.xs2a.adapter.service.Oauth2Service.Parameters;
import de.adorsys.xs2a.adapter.service.oauth.ParamAdjuster;
import de.adorsys.xs2a.adapter.service.oauth.ParamAdjustingResultHolder;
import org.apache.commons.lang3.StringUtils;

public class RefreshTokenParamAdjuster implements ParamAdjuster {
    private final ParamConstraint constraint;

    private RefreshTokenParamAdjuster(RefreshTokenParamAdjusterBuilder builder) {
        this.constraint = builder.constraint;
    }

    public static RefreshTokenParamAdjusterBuilder builder() {
        return new RefreshTokenParamAdjusterBuilder();
    }

    @Override
    public ParamAdjustingResultHolder adjustParam(ParamAdjustingResultHolder adjustingResultHolder,
                                                  Parameters parametersFromTpp) {
        String refreshToken = parametersFromTpp.getRefreshToken();

        if (StringUtils.isNotBlank(refreshToken)) {
            adjustingResultHolder.addAdjustedParam(Parameters.REFRESH_TOKEN, refreshToken);
        } else {
            if (constraint == ParamConstraint.REQUIRED) {
                adjustingResultHolder.addMissingParam(Parameters.REFRESH_TOKEN);
            }
        }

        return adjustingResultHolder;
    }

    public static final class RefreshTokenParamAdjusterBuilder {
        private ParamConstraint constraint;

        private RefreshTokenParamAdjusterBuilder() {
        }

        public RefreshTokenParamAdjusterBuilder constraint(ParamConstraint constraint) {
            this.constraint = constraint;
            return this;
        }

        public RefreshTokenParamAdjuster build() {
            if (constraint == null) {
                constraint = ParamConstraint.REQUIRED;
            }

            return new RefreshTokenParamAdjuster(this);
        }
    }
}
