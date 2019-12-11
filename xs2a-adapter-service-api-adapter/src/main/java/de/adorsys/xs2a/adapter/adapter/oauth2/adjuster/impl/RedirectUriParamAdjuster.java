package de.adorsys.xs2a.adapter.adapter.oauth2.adjuster.impl;

import de.adorsys.xs2a.adapter.adapter.oauth2.adjuster.ParamConstraint;
import de.adorsys.xs2a.adapter.service.Oauth2Service.Parameters;
import de.adorsys.xs2a.adapter.service.oauth.ParamAdjuster;
import de.adorsys.xs2a.adapter.service.oauth.ParamAdjustingResultHolder;
import org.apache.commons.lang3.StringUtils;

public class RedirectUriParamAdjuster implements ParamAdjuster {
    private final ParamConstraint constraint;

    private RedirectUriParamAdjuster(RedirectUriParamAdjusterBuilder builder) {
        this.constraint = builder.constraint;
    }

    public static RedirectUriParamAdjusterBuilder builder() {
        return new RedirectUriParamAdjusterBuilder();
    }

    @Override
    public ParamAdjustingResultHolder adjustParam(ParamAdjustingResultHolder adjustingResultHolder,
                                                  Parameters parametersFromTpp) {
        String redirectUriFromTpp = parametersFromTpp.getRedirectUri();

        if (StringUtils.isNotBlank(redirectUriFromTpp)) {
            adjustingResultHolder.addAdjustedParam(Parameters.REDIRECT_URI, redirectUriFromTpp);
        } else {
            if (constraint == ParamConstraint.REQUIRED) {
                adjustingResultHolder.addMissingParam(Parameters.REDIRECT_URI);
            }
        }

        return adjustingResultHolder;
    }

    public static final class RedirectUriParamAdjusterBuilder {
        private ParamConstraint constraint;

        private RedirectUriParamAdjusterBuilder() {
        }

        public RedirectUriParamAdjusterBuilder constraint(ParamConstraint constraint) {
            this.constraint = constraint;
            return this;
        }

        public RedirectUriParamAdjuster build() {
            if (constraint == null) {
                constraint = ParamConstraint.REQUIRED;
            }

            return new RedirectUriParamAdjuster(this);
        }
    }
}
