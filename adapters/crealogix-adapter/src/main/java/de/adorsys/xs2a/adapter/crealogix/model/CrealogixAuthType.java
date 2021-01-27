package de.adorsys.xs2a.adapter.crealogix.model;

public enum CrealogixAuthType {
    PASSWORD("PASSWORD"),
    SMSTAN("SMSTAN"),
    MATRIX("MATRIX"),
    FOTOTAN("FOTOTAN"),
    PUSHTAN("PUSHTAN"),
    DIGIPASS("DIGIPASS"),
    CHIPTAN("CHIPTAN");

    private String value;

    CrealogixAuthType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    public static CrealogixAuthType fromValue(String text) {
        for (CrealogixAuthType b : CrealogixAuthType.values()) {
            if (String.valueOf(b.value).equals(text)) {
                return b;
            }
        }
        return null;
    }
}
