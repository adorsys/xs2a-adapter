package de.adorsys.xs2a.adapter.adapter.oauth2.adjuster;

import de.adorsys.xs2a.adapter.service.Oauth2Service.Parameters;
import de.adorsys.xs2a.adapter.service.config.AdapterConfig;
import de.adorsys.xs2a.adapter.service.oauth.ParamAdjuster;
import de.adorsys.xs2a.adapter.service.oauth.ParamAdjustingResultHolder;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

public class CodeVerifierParamAdjuster implements ParamAdjuster {
    private static final String DEFAULT_CODE_VERIFIER_REGEX = "^[\\w\\-._~]{44,127}$";
    private static final Pattern DEFAULT_CODE_VERIFIER_PATTERN
        = Pattern.compile(DEFAULT_CODE_VERIFIER_REGEX);

    private final String aspspDefaultCodeVerifierProperty;
    private final Pattern codeVerifierPattern;

    public CodeVerifierParamAdjuster(String aspspDefaultCodeVerifierProperty, Pattern codeVerifierPattern) {
        this.aspspDefaultCodeVerifierProperty = aspspDefaultCodeVerifierProperty;
        this.codeVerifierPattern = codeVerifierPattern;
    }

    public CodeVerifierParamAdjuster(String aspspDefaultCodeVerifierProperty) {
        this(aspspDefaultCodeVerifierProperty, DEFAULT_CODE_VERIFIER_PATTERN);
    }

    public CodeVerifierParamAdjuster(Pattern codeVerifierPattern) {
        this(null, codeVerifierPattern);
    }

    public CodeVerifierParamAdjuster() {
        this(null, DEFAULT_CODE_VERIFIER_PATTERN);
    }

    @Override
    public ParamAdjustingResultHolder adjustParam(ParamAdjustingResultHolder adjustingResultHolder,
                                                  Parameters parametersFromTpp) {
        String codeVerifier = parametersFromTpp.getCodeVerifier();

        if (StringUtils.isBlank(codeVerifier)) {
            if (aspspDefaultCodeVerifierProperty != null) {
                codeVerifier = AdapterConfig.readProperty(aspspDefaultCodeVerifierProperty);
            }
        }

        if (codeVerifier != null && codeVerifierPattern.matcher(codeVerifier).find()) {
            adjustingResultHolder.addAdjustedParam(Parameters.CODE_VERIFIER, codeVerifier);
        } else {
            adjustingResultHolder.addMissingParam(Parameters.CODE_VERIFIER);
        }

        return adjustingResultHolder;
    }
}
