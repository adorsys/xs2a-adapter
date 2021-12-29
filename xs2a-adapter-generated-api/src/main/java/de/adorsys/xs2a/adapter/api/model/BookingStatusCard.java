package de.adorsys.xs2a.adapter.api.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import javax.annotation.Generated;

@Generated("xs2a-adapter-codegen")
public enum BookingStatusCard {
    BOOKED("booked"),

    PENDING("pending"),

    BOTH("both");

    private String value;

    BookingStatusCard(String value) {
        this.value = value;
    }

    @JsonCreator
    public static BookingStatusCard fromValue(String value) {
        for (BookingStatusCard e : BookingStatusCard.values()) {
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
