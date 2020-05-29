package de.adorsys.xs2a.adapter.service.model;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public enum Scope {
    AIS("ais"),
    AIS_BALANCES("ais_balances"),
    AIS_TRANSACTIONS("ais_transactions"),
    PIS("pis");

    private static final Set<String> VALUES = getValues();
    private final String value;

    Scope(String value) {
        this.value = value;
    }

    public static Scope fromValue(String value) {
        for (Scope e : Scope.values()) {
            if (e.value.equals(value)) {
                return e;
            }
        }
        throw new IllegalArgumentException(value);
    }

    private static Set<String> getValues() {
        return Arrays.stream(Scope.values())
                   .map(scope -> scope.value)
                   .collect(Collectors.toSet());
    }

    public static boolean contains(String value) {
        return VALUES.contains(value);
    }

    @Override
    public String toString() {
        return value;
    }

    public String getValue() {
        return value;
    }
}
