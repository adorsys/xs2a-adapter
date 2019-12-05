package de.adorsys.xs2a.adapter.service.util.adjuster.impl;

import de.adorsys.xs2a.adapter.service.Oauth2Service.Parameters;
import de.adorsys.xs2a.adapter.service.util.adjuster.ParamAdjuster;
import de.adorsys.xs2a.adapter.service.util.adjuster.ParamAdjustingResultHolder;
import org.apache.commons.lang3.StringUtils;

public class RefreshTokenParamAdjuster implements ParamAdjuster {

    @Override
    public ParamAdjustingResultHolder adjustParam(ParamAdjustingResultHolder adjustingResultHolder,
                                                  Parameters parametersFromTpp) {
        String refreshToken = parametersFromTpp.getRefreshToken();

        if (StringUtils.isNotBlank(refreshToken)) {
            adjustingResultHolder.addAdjustedParam(Parameters.REFRESH_TOKEN, refreshToken);
        } else {
            adjustingResultHolder.addMissingParam(Parameters.REFRESH_TOKEN);
        }

        return adjustingResultHolder;
    }
}
