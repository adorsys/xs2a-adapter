package de.adorsys.xs2a.gateway.service.impl;

import de.adorsys.xs2a.gateway.adapter.BasePaymentInitiationService;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class DeutscheBankPaymentInitiationService extends BasePaymentInitiationService {
    private static final String DATE_HEADER = "Date";
    private static final String BASE_URI = "https://simulator-xs2a.db.com/";
    private static final String PIS_URI = BASE_URI + "pis/DE/SB-DB/v1/";
    private static final String PAYMENTS_SEPA_CREDIT_TRANSFERS_URI = PIS_URI + "payments/sepa-credit-transfers";

    @Override
    protected String getSingleSepaCreditTransferUri() {
        return PAYMENTS_SEPA_CREDIT_TRANSFERS_URI;
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
