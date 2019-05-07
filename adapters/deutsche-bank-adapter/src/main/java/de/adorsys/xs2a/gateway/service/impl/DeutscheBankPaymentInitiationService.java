package de.adorsys.xs2a.gateway.service.impl;

import de.adorsys.xs2a.gateway.adapter.BasePaymentInitiationService;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class DeutscheBankPaymentInitiationService extends BasePaymentInitiationService {
    private static final String DATE_HEADER = "Date";
    private static final String PIS_URI = "https://simulator-xs2a.db.com/pis/DE/SB-DB/v1/";

    @Override
    protected String getBaseUri() {
        return PIS_URI;
    }

    @Override
    protected Map<String, String> populateGetHeaders(Map<String, String> map) {
        Map<String, String> headers = super.populateGetHeaders(map);
        headers.put(DATE_HEADER, DateTimeFormatter.RFC_1123_DATE_TIME.format(ZonedDateTime.now()));
        headers.put(ACCEPT_HEADER, APPLICATION_JSON);

        return headers;
    }

    @Override
    protected Map<String, String> populatePostHeaders(Map<String, String> map) {
        Map<String, String> headers = super.populateGetHeaders(map);
        headers.put(DATE_HEADER, DateTimeFormatter.RFC_1123_DATE_TIME.format(ZonedDateTime.now()));
        headers.put(CONTENT_TYPE_HEADER, APPLICATION_JSON);

        return headers;
    }
}
