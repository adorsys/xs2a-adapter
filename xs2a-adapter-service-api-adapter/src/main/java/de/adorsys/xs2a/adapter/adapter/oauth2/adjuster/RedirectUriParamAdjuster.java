package de.adorsys.xs2a.adapter.adapter.oauth2.adjuster;

import de.adorsys.xs2a.adapter.service.Oauth2Service.Parameters;
import de.adorsys.xs2a.adapter.service.oauth.ParamAdjuster;
import de.adorsys.xs2a.adapter.service.oauth.ParamAdjustingResultHolder;
import org.apache.commons.lang3.StringUtils;

public class RedirectUriParamAdjuster implements ParamAdjuster {

    @Override
    public ParamAdjustingResultHolder adjustParam(ParamAdjustingResultHolder adjustingResultHolder,
                                                  Parameters parametersFromTpp) {
        String redirectUriFromTpp = parametersFromTpp.getRedirectUri();

        if (StringUtils.isNotBlank(redirectUriFromTpp)) {
            adjustingResultHolder.addAdjustedParam(Parameters.REDIRECT_URI, redirectUriFromTpp);
        } else {
            adjustingResultHolder.addMissingParam(Parameters.REDIRECT_URI);
        }

        return adjustingResultHolder;
    }
}
