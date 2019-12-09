package de.adorsys.xs2a.adapter.adapter.oauth2.adjuster.impl;

import de.adorsys.xs2a.adapter.adapter.oauth2.adjuster.ParamAdjuster;
import de.adorsys.xs2a.adapter.adapter.oauth2.adjuster.model.ParamAdjustingResultHolder;
import de.adorsys.xs2a.adapter.service.Oauth2Service.Parameters;
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
