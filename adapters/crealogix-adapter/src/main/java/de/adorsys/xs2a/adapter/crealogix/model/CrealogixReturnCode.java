package de.adorsys.xs2a.adapter.crealogix.model;

import java.util.stream.Stream;

public enum CrealogixReturnCode {
    NONE,
    CORRECT,
    CORRECT_PASSWORD_CHANGE,
    FAILED,
    LOCKED,
    TEMPORARY_LOCKED,
    PASSWORD_EXPIRED,
    OFFLINE_TOOL_ONLY,
    NO_SUITABLE_AUTHENTICATION_TYPE,
    TAN_WRONG,
    TAN_INVALIDATED,
    TAN_EXPIRED,
    TAN_NOT_ACTIVE,
    NOTIFICATION_UNDELIVERABLE,
    NOTIFICATION_CONFIRMATION_REJECTED,
    NOTIFICATION_EXPIRED,
    AWAITING_USER_RESPONSE;

    public static CrealogixReturnCode fromValue(String text) {
        return Stream.of(CrealogixReturnCode.values())
            .filter(c -> c.name().equalsIgnoreCase(text))
            .findFirst().orElse(null);
    }
}
