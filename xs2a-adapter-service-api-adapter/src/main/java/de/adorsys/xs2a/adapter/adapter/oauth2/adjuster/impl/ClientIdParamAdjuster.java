package de.adorsys.xs2a.adapter.adapter.oauth2.adjuster.impl;

import de.adorsys.xs2a.adapter.adapter.oauth2.adjuster.ParamAdjuster;
import de.adorsys.xs2a.adapter.adapter.oauth2.adjuster.model.ParamAdjustingResultHolder;
import org.apache.commons.lang3.StringUtils;

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

        if (StringUtils.isBlank(clientId)) {
            clientId = clientIdFromCertificate;
        }

        adjustingResultHolder.addAdjustedParam(Parameters.CLIENT_ID, clientId);

        return adjustingResultHolder;
    }
}
