package de.adorsys.xs2a.adapter.service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class RequestParams {
    private static final String WITH_BALANCE = "withBalance";
    private static final String BOOKING_STATUS = "bookingStatus";
    private static final String DATE_FROM = "dateFrom";
    private static final String DATE_TO = "dateTo";
    private static final String ENTRY_REFERENCE_FROM = "entryReferenceFrom";
    private static final String DELTA_LIST = "deltaList";

    private Map<String, String> requestParams;

    private Boolean withBalance;
    private String bookingStatus;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private String entryReferenceFrom;
    private Boolean deltaList;

    private RequestParams() {
    }

    public Map<String, String> toMap() {
        if (requestParams == null) {
            requestParams = new HashMap<>();

            putIntoAs(withBalance, requestParams, WITH_BALANCE);
            putIntoAs(bookingStatus, requestParams, BOOKING_STATUS);
            putIntoAs(dateFrom, requestParams, DATE_FROM);
            putIntoAs(dateTo, requestParams, DATE_TO);
            putIntoAs(entryReferenceFrom, requestParams, ENTRY_REFERENCE_FROM);
            putIntoAs(deltaList, requestParams, DELTA_LIST);
        }

        return new HashMap<>(requestParams);
    }

    private void putIntoAs(Object requestParamValue, Map<String, String> requestParams, String requestParamName) {
        if (requestParamValue != null) {
            requestParams.put(requestParamName, requestParamValue.toString());
        }
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
            this.requestParams = requestParams;
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
            RequestParams requestParams = new RequestParams();
            requestParams.dateTo = this.dateTo;
            requestParams.dateFrom = this.dateFrom;
            requestParams.deltaList = this.deltaList;
            requestParams.requestParams = this.requestParams;
            requestParams.withBalance = this.withBalance;
            requestParams.entryReferenceFrom = this.entryReferenceFrom;
            requestParams.bookingStatus = this.bookingStatus;
            return requestParams;
        }
    }
}
