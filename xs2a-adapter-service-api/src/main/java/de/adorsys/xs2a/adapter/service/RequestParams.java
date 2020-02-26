package de.adorsys.xs2a.adapter.service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class RequestParams {
    public static final String WITH_BALANCE = "withBalance";
    public static final String BOOKING_STATUS = "bookingStatus";
    public static final String DATE_FROM = "dateFrom";
    public static final String DATE_TO = "dateTo";
    public static final String ENTRY_REFERENCE_FROM = "entryReferenceFrom";
    public static final String DELTA_LIST = "deltaList";

    private static final RequestParams EMPTY = new RequestParams(Collections.emptyMap());

    private final Map<String, String> requestParams;

    private RequestParams(Map<String, String> requestParams) {
        this.requestParams = Collections.unmodifiableMap(new LinkedHashMap<>(requestParams));
    }

    public static RequestParams fromMap(Map<String, String> map) {
        RequestParams requestParams = new RequestParams(map);
        verifyTypeBoolean(map.get(WITH_BALANCE));
        verifyTypeLocalDate(map.get(DATE_FROM));
        verifyTypeLocalDate(map.get(DATE_TO));
        verifyTypeBoolean(map.get(DELTA_LIST));
        return requestParams;
    }

    private static void verifyTypeLocalDate(String value) {
        if (value != null) {
            try {
                LocalDate.parse(value);
            } catch (DateTimeParseException e) {
                throw new IllegalArgumentException(e);
            }
        }
    }

    private static void verifyTypeBoolean(String value) {
        if (value != null) {
            if (!"true".equalsIgnoreCase(value) && !"false".equalsIgnoreCase(value)) {
                throw new IllegalArgumentException(value + " is not a boolean");
            }
        }
    }

    public static RequestParams empty() {
        return EMPTY;
    }

    public Map<String, String> toMap() {
        return requestParams;
    }

    public static RequestParamsBuilder builder() {
        return new RequestParamsBuilder();
    }

    public static final class RequestParamsBuilder {
        private Map<String, String> requestParams;
        private Boolean withBalance;
        private String bookingStatus;
        private LocalDate dateFrom;
        private LocalDate dateTo;
        private String entryReferenceFrom;
        private Boolean deltaList;

        private RequestParamsBuilder() {
        }

        public RequestParamsBuilder requestParams(Map<String, String> requestParams) {
            this.requestParams = new LinkedHashMap<>(requestParams);
            return this;
        }

        public RequestParamsBuilder withBalance(Boolean withBalance) {
            this.withBalance = withBalance;
            return this;
        }

        public RequestParamsBuilder bookingStatus(String bookingStatus) {
            this.bookingStatus = bookingStatus;
            return this;
        }

        public RequestParamsBuilder dateFrom(LocalDate dateFrom) {
            this.dateFrom = dateFrom;
            return this;
        }

        public RequestParamsBuilder dateTo(LocalDate dateTo) {
            this.dateTo = dateTo;
            return this;
        }

        public RequestParamsBuilder entryReferenceFrom(String entryReferenceFrom) {
            this.entryReferenceFrom = entryReferenceFrom;
            return this;
        }

        public RequestParamsBuilder deltaList(Boolean deltaList) {
            this.deltaList = deltaList;
            return this;
        }

        public RequestParams build() {
            if (requestParams == null) {
                requestParams = new HashMap<>();
            }
            putIntoAs(withBalance, requestParams, WITH_BALANCE);
            putIntoAs(bookingStatus, requestParams, BOOKING_STATUS);
            putIntoAs(dateFrom, requestParams, DATE_FROM);
            putIntoAs(dateTo, requestParams, DATE_TO);
            putIntoAs(entryReferenceFrom, requestParams, ENTRY_REFERENCE_FROM);
            putIntoAs(deltaList, requestParams, DELTA_LIST);

            return new RequestParams(requestParams);
        }

        private void putIntoAs(Object requestParamValue, Map<String, String> requestParams, String requestParamName) {
            if (requestParamValue != null) {
                requestParams.put(requestParamName, requestParamValue.toString());
            }
        }
    }
}
