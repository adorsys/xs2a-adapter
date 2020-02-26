package de.adorsys.xs2a.adapter.service.ing;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Currency;
import java.util.Map;
import java.util.function.Function;

class QueryParameters {
    private Map<String, String> queryParameters;

    QueryParameters(Map<String, String> queryParameters) {
        this.queryParameters = Collections.unmodifiableMap(queryParameters);
    }

    public <T> T get(String name, Function<? super String, T> parseFunction) {
        String value = queryParameters.get(name);
        if (value != null) {
            return parseFunction.apply(value);
        }
        return null;
    }

    public LocalDate getDateFrom() {
        return get("dateFrom", LocalDate::parse);
    }

    public LocalDate getDateTo() {
        return get("dateTo", LocalDate::parse);
    }

    public Currency getCurrency() {
        return get("currency", Currency::getInstance);
    }

    public Integer getLimit() {
        return get("limit", Integer::valueOf);
    }
}
