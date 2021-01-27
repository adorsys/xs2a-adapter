package de.adorsys.xs2a.adapter.crealogix.model;

import java.util.stream.Stream;

public enum CrealogixAuthType {
    PASSWORD,
    SMSTAN,
    MATRIX,
    FOTOTAN,
    PUSHTAN,
    DIGIPASS,
    CHIPTAN;

    public static CrealogixAuthType fromValue(String text) {
        return Stream.of(CrealogixAuthType.values())
            .filter(t -> t.name().equalsIgnoreCase(text))
            .findFirst().orElse(null);
    }
}
