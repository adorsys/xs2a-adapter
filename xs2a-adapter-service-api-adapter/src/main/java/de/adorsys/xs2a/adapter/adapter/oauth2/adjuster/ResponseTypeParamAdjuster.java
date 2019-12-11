package de.adorsys.xs2a.adapter.adapter.oauth2.adjuster;

import de.adorsys.xs2a.adapter.service.oauth.ParamAdjuster;
import de.adorsys.xs2a.adapter.service.oauth.ParamAdjustingResultHolder;

import static de.adorsys.xs2a.adapter.service.Oauth2Service.Parameters;

public class ResponseTypeParamAdjuster implements ParamAdjuster {
    private static final String DEFAULT_RESPONSE_TYPE_PARAM_VALUE = "code";

    @Override
    public ParamAdjustingResultHolder adjustParam(ParamAdjustingResultHolder adjustingResultHolder,
                                                  Parameters parametersFromTpp) {
        adjustingResultHolder.addAdjustedParam(Parameters.RESPONSE_TYPE, DEFAULT_RESPONSE_TYPE_PARAM_VALUE);
        return adjustingResultHolder;
    }
}
