package de.adorsys.xs2a.adapter.api.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import javax.annotation.Generated;

@Generated("xs2a-adapter-codegen")
public enum FrequencyCode {
    DAILY("Daily"),

    WEEKLY("Weekly"),

    EVERYTWOWEEKS("EveryTwoWeeks"),

    MONTHLY("Monthly"),

    EVERYTWOMONTHS("EveryTwoMonths"),

    QUARTERLY("Quarterly"),

    SEMIANNUAL("SemiAnnual"),

    ANNUAL("Annual"),

    MONTHLYVARIABLE("MonthlyVariable");

    private String value;

    FrequencyCode(String value) {
        this.value = value;
    }

    @JsonCreator
    public static FrequencyCode fromValue(String value) {
        for (FrequencyCode e : FrequencyCode.values()) {
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
