package de.adorsys.xs2a.adapter.adapter.oauth2.adjuster;

import de.adorsys.xs2a.adapter.adapter.oauth2.adjuster.model.ParamAdjustingResultHolder;
import de.adorsys.xs2a.adapter.service.Oauth2Service.Parameters;

public interface ParamAdjuster {

    ParamAdjustingResultHolder adjustParam(ParamAdjustingResultHolder adjustingResultHolder,
                                           Parameters parametersFromTpp);

    default ParamAdjuster andThen(ParamAdjuster paramAdjuster) {
        return (ParamAdjustingResultHolder adjustingResultHolder, Parameters parametersFromTpp)
                   -> paramAdjuster.adjustParam(adjustParam(adjustingResultHolder, parametersFromTpp), parametersFromTpp);
    }
}
