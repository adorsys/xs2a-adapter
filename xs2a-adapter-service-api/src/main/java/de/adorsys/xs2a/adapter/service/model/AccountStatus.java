package de.adorsys.xs2a.adapter.service.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public enum AccountStatus {
    ENABLED("enabled"),
    DELETED("deleted"),
    BLOCKED("blocked");

    private final static Map<String, AccountStatus> container = new HashMap<>();

    static {
        for (AccountStatus accountStatus : values()) {
            container.put(accountStatus.getValue(), accountStatus);
        }
    }

    private String value;

    AccountStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @JsonIgnore
    public static Optional<AccountStatus> getByValue(String name) {
        return Optional.ofNullable(container.get(name));
    }

    @Override
    @JsonValue
    public String toString() {
        return value;
    }
}
