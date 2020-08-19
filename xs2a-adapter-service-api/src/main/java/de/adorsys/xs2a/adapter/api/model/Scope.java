package de.adorsys.xs2a.adapter.api.model;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.Set;
import java.util.stream.Collectors;

public enum Scope {
    AIS("ais"),
    AIS_BALANCES("ais_balances"),
    AIS_TRANSACTIONS("ais_transactions"),
    PIS("pis");

    private static final EnumSet<Scope> AIS_VALUES = EnumSet.of(AIS, AIS_BALANCES, AIS_TRANSACTIONS);
    private static final EnumSet<Scope> PIS_VALUES = EnumSet.of(PIS);
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

    public static boolean isAis(Scope scope) {
        return AIS_VALUES.contains(scope);
    }

    public static boolean isPis(Scope scope) {
        return PIS_VALUES.contains(scope);
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
