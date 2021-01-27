package de.adorsys.xs2a.adapter.crealogix.model;

import java.util.stream.Stream;

public enum CrealogixActionCode {
    PROMPT_FOR_LOGIN_NAME_PASSWORD,
    PROMPT_FOR_AUTH_METHOD_SELECTION,
    PROMPT_FOR_TAN,
    POLL_FOR_TAN,
    STOP_POLL_FOR_TAN,
    PROMPT_FOR_PASSWORD_CHANGE,
    PROMPT_FOR_SCOPE_ACCEPTANCE,
    SEND_REQUEST;

    public static CrealogixActionCode fromValue(String text) {
        return Stream.of(CrealogixActionCode.values())
            .filter(c -> c.name().equalsIgnoreCase(text))
            .findFirst().orElse(null);
    }
}
