package de.adorsys.xs2a.adapter.service.util;

import de.adorsys.xs2a.adapter.service.Pkcs12KeyStore;
import de.adorsys.xs2a.adapter.service.exception.BadRequestException;
import de.adorsys.xs2a.adapter.service.model.Aspsp;
import de.adorsys.xs2a.adapter.service.util.adjuster.ParamAdjuster;
import de.adorsys.xs2a.adapter.service.util.adjuster.ParamAdjustingResultHolder;
import de.adorsys.xs2a.adapter.service.util.adjuster.impl.*;

import java.security.KeyStoreException;

import static de.adorsys.xs2a.adapter.service.Oauth2Service.Parameters;

public class SpardaOauthParamsAdjustingService {
    private static final String DEFAULT_GRANT_TYPE_PARAM_VALUE_FOR_GET_TOKEN_REQUEST
        = "authorization_code";
    private static final String QUERY_PARAMETERS_MISSING_ERROR_MESSAGE
        = "The following query parameters are missing or not valid: %s";

    private final ParamAdjuster authorisationRequestParamAdjuster;
    private final ParamAdjuster tokenRequestParamAdjuster;

    public SpardaOauthParamsAdjustingService(Aspsp aspsp, Pkcs12KeyStore keyStore) {
        String organizationIdentifier;
        try {
            organizationIdentifier = keyStore.getOrganizationIdentifier();
        } catch (KeyStoreException e) {
            throw new RuntimeException(e);
        }

        this.authorisationRequestParamAdjuster
            = new BicParamAdjuster(aspsp.getBic())
                  .andThen(new ClientIdParamAdjuster(organizationIdentifier))
                  .andThen(new RedirectUriParamAdjuster())
                  .andThen(new ScopeParamAdjuster())
                  .andThen(new StateParamAdjuster())
                  .andThen(new ResponseTypeParamAdjuster())
                  .andThen(new CodeChallengeParamAdjuster())
                  .andThen(new CodeChallengeMethodParamAdjuster());

        this.tokenRequestParamAdjuster
            = new GrantTypeParamAdjuster(DEFAULT_GRANT_TYPE_PARAM_VALUE_FOR_GET_TOKEN_REQUEST)
                  .andThen(new ClientIdParamAdjuster(organizationIdentifier))
                  .andThen(new RedirectUriParamAdjuster())
                  .andThen(new CodeParamAdjuster())
                  .andThen(new CodeVerifierParamAdjuster());
    }

    public Parameters adjustForGetAuthorizationRequest(Parameters parametersFromTpp) {
        ParamAdjustingResultHolder adjustingResult
            = authorisationRequestParamAdjuster.adjustParam(new ParamAdjustingResultHolder(), parametersFromTpp);

        if (adjustingResult.containsMissingParams()) {
            throw new BadRequestException(
                String.format(QUERY_PARAMETERS_MISSING_ERROR_MESSAGE, adjustingResult.getMissingParameters())
            );
        }

        return new Parameters(adjustingResult.getParametersMap());
    }

    public Parameters adjustForGetTokenRequest(Parameters parametersFromTpp) {
        ParamAdjustingResultHolder adjustingResult
            = tokenRequestParamAdjuster.adjustParam(new ParamAdjustingResultHolder(), parametersFromTpp);

        if (adjustingResult.containsMissingParams()) {
            throw new BadRequestException(
                String.format(QUERY_PARAMETERS_MISSING_ERROR_MESSAGE, adjustingResult.getMissingParameters())
            );
        }

        return new Parameters(adjustingResult.getParametersMap());
    }
}
