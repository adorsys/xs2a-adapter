package de.adorsys.xs2a.adapter.service.util.adjuster.impl;

import de.adorsys.xs2a.adapter.service.Oauth2Service.Parameters;
import de.adorsys.xs2a.adapter.service.util.adjuster.ParamAdjuster;
import de.adorsys.xs2a.adapter.service.util.adjuster.ParamAdjustingResultHolder;
import org.apache.commons.lang3.StringUtils;

public class CodeParamAdjuster implements ParamAdjuster {

    @Override
    public ParamAdjustingResultHolder adjustParam(ParamAdjustingResultHolder adjustingResultHolder,
                                                  Parameters parametersFromTpp) {
        String codeFromTpp = parametersFromTpp.getAuthorizationCode();

        if (StringUtils.isNotBlank(codeFromTpp)) {
            adjustingResultHolder.addAdjustedParam(Parameters.CODE, codeFromTpp);
        } else {
            adjustingResultHolder.addMissingParam(Parameters.CODE);
        }

        return adjustingResultHolder;
    }
}
