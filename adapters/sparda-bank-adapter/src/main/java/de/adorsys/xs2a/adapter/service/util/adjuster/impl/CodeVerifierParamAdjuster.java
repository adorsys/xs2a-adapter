package de.adorsys.xs2a.adapter.service.util.adjuster.impl;

import de.adorsys.xs2a.adapter.service.Oauth2Service.Parameters;
import de.adorsys.xs2a.adapter.service.config.AdapterConfig;
import de.adorsys.xs2a.adapter.service.util.adjuster.ParamAdjuster;
import de.adorsys.xs2a.adapter.service.util.adjuster.ParamAdjustingResultHolder;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

public class CodeVerifierParamAdjuster implements ParamAdjuster {
    private static final String SPARDA_DEFAULT_CODE_VERIFIER_PROPERTY
        = "sparda.oauth_approach.default_code_verifier";

    private static final String CODE_VERIFIER_REGEX = "^[\\w\\-._~]{44,127}$";
    private static final Pattern CODE_VERIFIER_PATTERN = Pattern.compile(CODE_VERIFIER_REGEX);

    @Override
    public ParamAdjustingResultHolder adjustParam(ParamAdjustingResultHolder adjustingResultHolder,
                                                  Parameters parametersFromTpp) {
        String codeVerifier = parametersFromTpp.getCodeVerifier();

        if (StringUtils.isBlank(codeVerifier)) {
            codeVerifier = AdapterConfig.readProperty(SPARDA_DEFAULT_CODE_VERIFIER_PROPERTY);
        }

        if (codeVerifier != null && CODE_VERIFIER_PATTERN.matcher(codeVerifier).find()) {
            adjustingResultHolder.addAdjustedParam(Parameters.CODE_VERIFIER, codeVerifier);
        } else {
            adjustingResultHolder.addMissingParam(Parameters.CODE_VERIFIER);
        }

        return adjustingResultHolder;
    }
}
