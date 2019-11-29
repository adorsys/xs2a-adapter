package de.adorsys.xs2a.adapter.service.util.adjuster.impl;

import de.adorsys.xs2a.adapter.service.util.adjuster.ParamAdjuster;
import de.adorsys.xs2a.adapter.service.util.adjuster.ParamAdjustingResultHolder;

import static de.adorsys.xs2a.adapter.service.Oauth2Service.Parameters;

public class ScopeParamAdjuster implements ParamAdjuster {
    private static final String DEFAULT_SCOPE_PARAM_VALUE = "ais";

    @Override
    public ParamAdjustingResultHolder adjustParam(ParamAdjustingResultHolder adjustingResultHolder,
                                                  Parameters parametersFromTpp) {
        String scope = parametersFromTpp.getScope();

        if (scope == null || scope.trim().isEmpty()) {
            scope = DEFAULT_SCOPE_PARAM_VALUE;
        }

        adjustingResultHolder.addAdjustedParam(Parameters.SCOPE, scope);

        return adjustingResultHolder;
    }
}
