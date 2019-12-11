package de.adorsys.xs2a.adapter.adapter.oauth2.adjuster;

import de.adorsys.xs2a.adapter.service.oauth.ParamAdjuster;
import de.adorsys.xs2a.adapter.service.oauth.ParamAdjustingResultHolder;

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
