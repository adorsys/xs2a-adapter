package de.adorsys.xs2a.adapter.service.util.adjuster.impl;

import de.adorsys.xs2a.adapter.service.util.adjuster.ParamAdjuster;
import de.adorsys.xs2a.adapter.service.util.adjuster.ParamAdjustingResultHolder;

import static de.adorsys.xs2a.adapter.service.Oauth2Service.Parameters;

public class StateParamAdjuster implements ParamAdjuster {

    @Override
    public ParamAdjustingResultHolder adjustParam(ParamAdjustingResultHolder adjustingResultHolder,
                                                  Parameters parametersFromTpp) {
        String stateFromTpp = parametersFromTpp.getState();

        if (stateFromTpp != null && !stateFromTpp.trim().isEmpty()) {
            adjustingResultHolder.addAdjustedParam(Parameters.STATE, stateFromTpp);
        }

        return adjustingResultHolder;
    }
}
