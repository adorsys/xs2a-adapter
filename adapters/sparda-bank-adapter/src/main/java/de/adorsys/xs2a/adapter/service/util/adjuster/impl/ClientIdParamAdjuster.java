package de.adorsys.xs2a.adapter.service.util.adjuster.impl;

import de.adorsys.xs2a.adapter.service.util.adjuster.ParamAdjuster;
import de.adorsys.xs2a.adapter.service.util.adjuster.ParamAdjustingResultHolder;

import static de.adorsys.xs2a.adapter.service.Oauth2Service.Parameters;

public class ClientIdParamAdjuster implements ParamAdjuster {
    private final String clientIdFromCertificate;

    public ClientIdParamAdjuster(String clientIdFromCertificate) {
        this.clientIdFromCertificate = clientIdFromCertificate;
    }

    @Override
    public ParamAdjustingResultHolder adjustParam(ParamAdjustingResultHolder adjustingResultHolder,
                                                  Parameters parametersFromTpp) {
        String clientId = parametersFromTpp.getClientId();

        if (clientId == null || clientId.trim().isEmpty()) {
            clientId = clientIdFromCertificate;
        }

        adjustingResultHolder.addAdjustedParam(Parameters.CLIENT_ID, clientId);

        return adjustingResultHolder;
    }
}
