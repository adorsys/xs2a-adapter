package de.adorsys.xs2a.adapter.crealogix.model;

public enum CrealogixReturnCode {
    NONE("NONE"),
    CORRECT("CORRECT"),
    CORRECT_PASSWORD_CHANGE("CORRECT_PASSWORD_CHANGE"),
    FAILED("FAILED"),
    LOCKED("LOCKED"),
    TEMPORARY_LOCKED("TEMPORARY_LOCKED"),
    PASSWORD_EXPIRED("PASSWORD_EXPIRED"),
    OFFLINE_TOOL_ONLY("OFFLINE_TOOL_ONLY"),
    NO_SUITABLE_AUTHENTICATION_TYPE("NO_SUITABLE_AUTHENTICATION_TYPE"),
    TAN_WRONG("TAN_WRONG"),
    TAN_INVALIDATED("TAN_INVALIDATED"),
    TAN_EXPIRED("TAN_EXPIRED"),
    TAN_NOT_ACTIVE("TAN_NOT_ACTIVE"),
    NOTIFICATION_UNDELIVERABLE("NOTIFICATION_UNDELIVERABLE"),
    NOTIFICATION_CONFIRMATION_REJECTED("NOTIFICATION_CONFIRMATION_REJECTED"),
    NOTIFICATION_EXPIRED("NOTIFICATION_EXPIRED"),
    AWAITING_USER_RESPONSE("AWAITING_USER_RESPONSE");

    private String value;

    CrealogixReturnCode(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    public static CrealogixReturnCode fromValue(String text) {
        for (CrealogixReturnCode b : CrealogixReturnCode.values()) {
            if (String.valueOf(b.value).equals(text)) {
                return b;
            }
        }
        return null;
    }
}
