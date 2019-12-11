package de.adorsys.xs2a.adapter.adapter.oauth2.adjuster;

import de.adorsys.xs2a.adapter.service.oauth.ParamAdjuster;
import de.adorsys.xs2a.adapter.service.oauth.ParamAdjustingResultHolder;
import org.apache.commons.lang3.StringUtils;

import static de.adorsys.xs2a.adapter.service.Oauth2Service.Parameters;

public class StateParamAdjuster implements ParamAdjuster {

    @Override
    public ParamAdjustingResultHolder adjustParam(ParamAdjustingResultHolder adjustingResultHolder,
                                                  Parameters parametersFromTpp) {
        String stateFromTpp = parametersFromTpp.getState();

        if (StringUtils.isNotBlank(stateFromTpp)) {
            adjustingResultHolder.addAdjustedParam(Parameters.STATE, stateFromTpp);
        }

        return adjustingResultHolder;
    }
}
