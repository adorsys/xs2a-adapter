package de.adorsys.xs2a.adapter.service.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public enum UsageType {
    PRIV("PRIV"),
    ORGA("ORGA");

    private final static Map<String, UsageType> container = new HashMap<>();

    static {
        for (UsageType usageType : values()) {
            container.put(usageType.getValue(), usageType);
        }
    }

    private String value;

    UsageType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static Optional<UsageType> getByValue(String name) {
        return Optional.ofNullable(container.get(name));
    }
}
