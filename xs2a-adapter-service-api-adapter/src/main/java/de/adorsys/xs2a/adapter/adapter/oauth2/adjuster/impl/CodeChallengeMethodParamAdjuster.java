package de.adorsys.xs2a.adapter.adapter.oauth2.adjuster.impl;

import de.adorsys.xs2a.adapter.adapter.oauth2.adjuster.ParamAdjuster;
import de.adorsys.xs2a.adapter.adapter.oauth2.adjuster.model.ParamAdjustingResultHolder;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static de.adorsys.xs2a.adapter.service.Oauth2Service.Parameters;

public class CodeChallengeMethodParamAdjuster implements ParamAdjuster {
    private static final Set<String> SUPPORTED_CODE_CHALLENGE_METHODS
        = Collections.unmodifiableSet(new HashSet<>(Collections.singletonList("S256")));

    @Override
    public ParamAdjustingResultHolder adjustParam(ParamAdjustingResultHolder adjustingResultHolder,
                                                  Parameters parametersFromTpp) {
        String codeChallengeMethodFromTpp = parametersFromTpp.getCodeChallengeMethod();

        if (SUPPORTED_CODE_CHALLENGE_METHODS.contains(codeChallengeMethodFromTpp)) {
            adjustingResultHolder.addAdjustedParam(Parameters.CODE_CHALLENGE_METHOD, codeChallengeMethodFromTpp);
        }

        return adjustingResultHolder;
    }
}
