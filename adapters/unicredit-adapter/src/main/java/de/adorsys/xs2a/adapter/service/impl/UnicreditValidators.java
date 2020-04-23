package de.adorsys.xs2a.adapter.service.impl;

import de.adorsys.xs2a.adapter.service.RequestHeaders;
import de.adorsys.xs2a.adapter.validation.ValidationError;

import java.util.Collections;
import java.util.List;

class UnicreditValidators {
    private UnicreditValidators() {
    }

    static List<ValidationError> requireTppRedirectUri(RequestHeaders requestHeaders) {
        if (!requestHeaders.get(RequestHeaders.TPP_REDIRECT_URI).isPresent()) {
            return Collections.singletonList(new ValidationError(ValidationError.Code.REQUIRED,
                RequestHeaders.TPP_REDIRECT_URI,
                "TPP-Redirect-URI header is missing. It must be provided for this request"));
        }
        return Collections.emptyList();
    }
}
