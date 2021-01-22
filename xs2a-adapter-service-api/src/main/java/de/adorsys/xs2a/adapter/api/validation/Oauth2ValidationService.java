package de.adorsys.xs2a.adapter.api.validation;

import de.adorsys.xs2a.adapter.api.Oauth2Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public interface Oauth2ValidationService {
    default List<ValidationError> validateGetAuthorizationRequestUri(Map<String, String> headers,
                                                                     Oauth2Service.Parameters parameters) {
        return Collections.emptyList();
    }

    default List<ValidationError> validateGetToken(Map<String, String> headers, Oauth2Service.Parameters parameters) {
        return Collections.emptyList();
    }
}
