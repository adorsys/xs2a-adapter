package de.adorsys.xs2a.adapter.crealogix.model;

public enum CrealogixActionCode {
    PROMPT_FOR_LOGIN_NAME_PASSWORD("PROMPT_FOR_LOGIN_NAME_PASSWORD"),
    PROMPT_FOR_AUTH_METHOD_SELECTION("PROMPT_FOR_AUTH_METHOD_SELECTION"),
    PROMPT_FOR_TAN("PROMPT_FOR_TAN"),
    POLL_FOR_TAN("POLL_FOR_TAN"),
    STOP_POLL_FOR_TAN("STOP_POLL_FOR_TAN"),
    PROMPT_FOR_PASSWORD_CHANGE("PROMPT_FOR_PASSWORD_CHANGE"),
    PROMPT_FOR_SCOPE_ACCEPTANCE("PROMPT_FOR_SCOPE_ACCEPTANCE"),
    SEND_REQUEST("SEND_REQUEST");

    private String value;

    CrealogixActionCode(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    public static CrealogixActionCode fromValue(String text) {
        for (CrealogixActionCode b : CrealogixActionCode.values()) {
            if (String.valueOf(b.value).equals(text)) {
                return b;
            }
        }
        return null;
    }
}
