package de.adorsys.xs2a.adapter.service.oauth;

import de.adorsys.xs2a.adapter.service.Oauth2Service.Parameters;
import de.adorsys.xs2a.adapter.service.exception.BadRequestException;

public interface OauthParamsAdjustingService {
    String QUERY_PARAMETERS_MISSING_ERROR_MESSAGE
        = "The following query parameters are missing or not valid: %s";

    Parameters adjustForGetAuthorizationRequest(Parameters parametersFromTpp);

    Parameters adjustForGetTokenRequest(Parameters parametersFromTpp);

    Parameters adjustForRefreshTokenRequest(Parameters parametersFromTpp);

    default Parameters adjustParams(Parameters parametersFromTpp, ParamAdjuster paramAdjuster) {
        ParamAdjustingResultHolder adjustingResult
            = paramAdjuster.adjustParam(new ParamAdjustingResultHolder(), parametersFromTpp);

        if (adjustingResult.containsMissingParams()) {
            throw new BadRequestException(
                String.format(QUERY_PARAMETERS_MISSING_ERROR_MESSAGE, adjustingResult.getMissingParameters())
            );
        }

        return new Parameters(adjustingResult.getParametersMap());
    }
}
