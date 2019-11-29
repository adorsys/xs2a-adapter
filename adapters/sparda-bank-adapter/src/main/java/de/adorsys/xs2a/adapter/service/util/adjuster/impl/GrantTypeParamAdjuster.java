package de.adorsys.xs2a.adapter.service.util.adjuster.impl;

import de.adorsys.xs2a.adapter.service.util.adjuster.ParamAdjuster;
import de.adorsys.xs2a.adapter.service.util.adjuster.ParamAdjustingResultHolder;

import static de.adorsys.xs2a.adapter.service.Oauth2Service.Parameters;

public class GrantTypeParamAdjuster implements ParamAdjuster {
    private final String grantTypeRequiredValue;

    public GrantTypeParamAdjuster(String grantTypeRequiredValue) {
        this.grantTypeRequiredValue = grantTypeRequiredValue;
    }

    @Override
    public ParamAdjustingResultHolder adjustParam(ParamAdjustingResultHolder adjustingResultHolder,
                                                  Parameters parametersFromTpp) {
        adjustingResultHolder.addAdjustedParam(Parameters.GRANT_TYPE, grantTypeRequiredValue);
        return adjustingResultHolder;
    }
}
