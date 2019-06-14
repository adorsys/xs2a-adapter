package de.adorsys.xs2a.adapter.service.account;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public enum BalanceType {
    CLOSINGBOOKED("closingBooked"),
    EXPECTED("expected"),
    AUTHORISED("authorised"),
    OPENINGBOOKED("openingBooked"),
    INTERIMAVAILABLE("interimAvailable"),
    FORWARDAVAILABLE("forwardAvailable"),
    NONINVOICED("nonInvoiced");

    private final static Map<String, BalanceType> container = new HashMap<>();

    static {
        for (BalanceType type : values()) {
            container.put(type.getValue(), type);
        }
    }

    private String value;

    BalanceType(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static Optional<BalanceType> fromValue(String name) {
        return Optional.ofNullable(container.get(name));
    }
}
