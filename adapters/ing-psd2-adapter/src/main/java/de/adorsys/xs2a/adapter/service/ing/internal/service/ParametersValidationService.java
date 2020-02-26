package de.adorsys.xs2a.adapter.service.ing.internal.service;

import de.adorsys.xs2a.adapter.service.Oauth2Service;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static de.adorsys.xs2a.adapter.service.Oauth2Service.Parameters;

public class ParametersValidationService {

    private static final String DEFAULT_SCOPE = "payment-accounts:transactions:view";
    // todo add 'payment-accounts:orders:create' scope when PIS flow will be implemented
    private static final Set<String> availableScopes = new HashSet<>(Arrays.asList(DEFAULT_SCOPE, "payment-accounts:balances:view"));

    public static Oauth2Service.Parameters validateScope(Oauth2Service.Parameters params) {
        if (StringUtils.isBlank(params.get(Parameters.SCOPE))) {
            params.set(Parameters.SCOPE, DEFAULT_SCOPE);
            return params;
        } else if (!hasAvailableScopes(params)) {
            List<String> listOfInputScopes = scopesAsList(params);
            String outputScopes = listOfInputScopes.stream().filter(availableScopes::contains).collect(Collectors.joining(" "));

            params.set(Parameters.SCOPE, StringUtils.isBlank(outputScopes) ? DEFAULT_SCOPE : outputScopes);
            return params;
        }

        return params;
    }

    private static boolean hasAvailableScopes(Oauth2Service.Parameters params) {
        List<String> listOfScopes = scopesAsList(params);

        return availableScopes.containsAll(listOfScopes);
    }

    private static List<String> scopesAsList(Oauth2Service.Parameters params) {
        return Arrays.asList(params.getScope().split("\\s+"));
    }
}
