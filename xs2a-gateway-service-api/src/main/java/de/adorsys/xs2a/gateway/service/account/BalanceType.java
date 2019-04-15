package de.adorsys.xs2a.gateway.service.account;

import com.fasterxml.jackson.annotation.JsonIgnore;

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

    public String getValue() {
        return value;
    }

    @JsonIgnore
    public static Optional<BalanceType> getByValue(String name) {
        return Optional.ofNullable(container.get(name));
    }
}
