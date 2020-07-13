package de.adorsys.xs2a.adapter.fiducia.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum FiduciaDayOfExecution {
    _1("01"),

    _2("02"),

    _3("03"),

    _4("04"),

    _5("05"),

    _6("06"),

    _7("07"),

    _8("08"),

    _9("09"),

    _10("10"),

    _11("11"),

    _12("12"),

    _13("13"),

    _14("14"),

    _15("15"),

    _16("16"),

    _17("17"),

    _18("18"),

    _19("19"),

    _20("20"),

    _21("21"),

    _22("22"),

    _23("23"),

    _24("24"),

    _25("25"),

    _26("26"),

    _27("27"),

    _28("28"),

    _29("29"),

    _30("30"),

    _31("31");

    private String value;

    FiduciaDayOfExecution(String value) {
        this.value = value;
    }

    @JsonCreator
    public static FiduciaDayOfExecution fromValue(String value) {
        for (FiduciaDayOfExecution e : FiduciaDayOfExecution.values()) {
            if (e.value.equals(value)) {
                return e;
            }
        }
        return null;
    }

    @Override
    @JsonValue
    public String toString() {
        return value;
    }
}
