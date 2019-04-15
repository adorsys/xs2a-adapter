package de.adorsys.xs2a.gateway.service;

import java.util.HashMap;
import java.util.Map;

public class RequestParams {
    public static final String WITH_BALANCE = "withBalance";

    private Map<String, String> requestParams;

    private Boolean withBalance;

    private RequestParams() {
    }

    public Map<String, String> toMap() {
        if (requestParams == null) {
            requestParams = new HashMap<>();

            putIntoAs(withBalance, requestParams, WITH_BALANCE);
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
        private Boolean withBalance;

        private RequestParamsBuilder() {
        }

        public RequestParamsBuilder withBalance(Boolean withBalance) {
            this.withBalance = withBalance;
            return this;
        }

        public RequestParams build() {
            RequestParams requestParams = new RequestParams();
            requestParams.withBalance = this.withBalance;
            return requestParams;
        }
    }
}
