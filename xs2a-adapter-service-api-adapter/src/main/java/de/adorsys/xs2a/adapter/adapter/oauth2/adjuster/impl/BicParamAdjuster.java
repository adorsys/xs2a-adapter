package de.adorsys.xs2a.adapter.adapter.oauth2.adjuster.impl;

import de.adorsys.xs2a.adapter.adapter.oauth2.adjuster.ParamAdjuster;
import de.adorsys.xs2a.adapter.adapter.oauth2.adjuster.model.ParamAdjustingResultHolder;
import org.apache.commons.lang3.StringUtils;

import static de.adorsys.xs2a.adapter.service.Oauth2Service.Parameters;

public class BicParamAdjuster implements ParamAdjuster {
    private final String bicFromAspsp;

    public BicParamAdjuster(String bicFromAspsp) {
        this.bicFromAspsp = bicFromAspsp;
    }

    @Override
    public ParamAdjustingResultHolder adjustParam(ParamAdjustingResultHolder adjustingResultHolder,
                                                  Parameters parametersFromTpp) {
        String bic = parametersFromTpp.getBic();

        if (StringUtils.isBlank(bic)) {
            bic = bicFromAspsp;
        }

        if (StringUtils.isNotBlank(bic)) {
            adjustingResultHolder.addAdjustedParam(Parameters.BIC, bic);
        } else {
            adjustingResultHolder.addMissingParam(Parameters.BIC);
        }

        return adjustingResultHolder;
    }
}
