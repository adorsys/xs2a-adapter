package de.adorsys.xs2a.adapter.service.ing.internal.service;

import de.adorsys.xs2a.adapter.service.Oauth2Service;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ParametersValidationService {

    private static final String SCOPE = Oauth2Service.Parameters.SCOPE;
    private static final String DEFAULT_SCOPE = "payment-accounts:transactions:view";
    private static final List<String> scopes = Arrays.asList(DEFAULT_SCOPE, "payment-accounts:balances:view");

    public static Oauth2Service.Parameters validateScope(Oauth2Service.Parameters params) {
        if (params.get(SCOPE) == null) {
            params.set(SCOPE, DEFAULT_SCOPE);
            return params;
        }

        if (!hasScopes(params)) {
            List<String> listOfInputScopes = scopesAsList(params);
            String outputScopes = listOfInputScopes.stream().filter(scopes::contains).collect(Collectors.joining(" "));

            params.set(SCOPE, StringUtils.isBlank(outputScopes) ? DEFAULT_SCOPE : outputScopes);
            return params;
        }

        return params;
    }

    private static boolean hasScopes(Oauth2Service.Parameters params) {
        List<String> listOfScopes = scopesAsList(params);

        return scopes.containsAll(listOfScopes);
    }

    private static List<String> scopesAsList(Oauth2Service.Parameters params) {
        return Arrays.asList(params.getScope().split(" "));
    }
}
