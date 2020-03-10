package de.adorsys.xs2a.adapter.adapter.oauth2.adjuster.impl;

import de.adorsys.xs2a.adapter.adapter.oauth2.adjuster.ParamConstraint;
import de.adorsys.xs2a.adapter.service.oauth.ParamAdjuster;
import de.adorsys.xs2a.adapter.service.oauth.ParamAdjustingResultHolder;
import org.apache.commons.lang3.StringUtils;

import static de.adorsys.xs2a.adapter.service.Oauth2Service.Parameters;

public class ClientIdParamAdjuster implements ParamAdjuster {
    private final String clientIdFromConfig;
    private final String clientIdFromCertificate;
    private final ParamConstraint constraint;

    private ClientIdParamAdjuster(ClientIdParamAdjusterBuilder builder) {
        this.clientIdFromCertificate = builder.clientIdFromCertificate;
        clientIdFromConfig = builder.clientIdFromConfig;
        this.constraint = builder.constraint;
    }

    public static ClientIdParamAdjusterBuilder builder() {
        return new ClientIdParamAdjusterBuilder();
    }

    @Override
    public ParamAdjustingResultHolder adjustParam(ParamAdjustingResultHolder adjustingResultHolder,
                                                  Parameters parametersFromTpp) {
        String clientId = parametersFromTpp.getClientId();

        if (StringUtils.isBlank(clientId)) {
            clientId = clientIdFromConfig;
        }

        if (StringUtils.isBlank(clientId)) {
            clientId = clientIdFromCertificate;
        }

        if (StringUtils.isNotBlank(clientId)) {
            adjustingResultHolder.addAdjustedParam(Parameters.CLIENT_ID, clientId);
        } else {
            if (constraint == ParamConstraint.REQUIRED) {
                adjustingResultHolder.addMissingParam(Parameters.CLIENT_ID);
            }
        }

        return adjustingResultHolder;
    }

    public static final class ClientIdParamAdjusterBuilder {
        private String clientIdFromConfig;
        private String clientIdFromCertificate;
        private ParamConstraint constraint;

        private ClientIdParamAdjusterBuilder() {
        }

        public ClientIdParamAdjusterBuilder clientIdFromConfig(String clientId) {
            this.clientIdFromConfig = clientId;
            return this;
        }

        public ClientIdParamAdjusterBuilder clientIdFromCertificate(String clientIdFromCertificate) {
            this.clientIdFromCertificate = clientIdFromCertificate;
            return this;
        }

        public ClientIdParamAdjusterBuilder constraint(ParamConstraint constraint) {
            this.constraint = constraint;
            return this;
        }

        public ClientIdParamAdjuster build() {
            if (constraint == null) {
                constraint = ParamConstraint.REQUIRED;
            }

            return new ClientIdParamAdjuster(this);
        }
    }
}
