package de.adorsys.xs2a.adapter.deutschebank;

import de.adorsys.xs2a.adapter.api.RequestHeaders;
import de.adorsys.xs2a.adapter.api.http.ContentType;
import de.adorsys.xs2a.adapter.api.http.HttpClient;
import de.adorsys.xs2a.adapter.api.http.HttpLogSanitizer;
import de.adorsys.xs2a.adapter.api.http.Interceptor;
import de.adorsys.xs2a.adapter.api.link.LinksRewriter;
import de.adorsys.xs2a.adapter.api.model.Aspsp;
import de.adorsys.xs2a.adapter.impl.BasePaymentInitiationService;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class DeutscheBankPaymentInitiationService extends BasePaymentInitiationService {
    private static final String DATE_HEADER = "Date";

    public DeutscheBankPaymentInitiationService(Aspsp aspsp,
                                                HttpClient httpClient,
                                                Interceptor interceptor,
                                                LinksRewriter linksRewriter,
                                                HttpLogSanitizer logSanitizer) {
        super(aspsp, httpClient, interceptor, linksRewriter, logSanitizer);
    }

    @Override
    protected Map<String, String> populateGetHeaders(Map<String, String> map) {
        Map<String, String> headers = super.populateGetHeaders(map);
        headers.put(DATE_HEADER, DateTimeFormatter.RFC_1123_DATE_TIME.format(ZonedDateTime.now()));
        headers.put(ACCEPT_HEADER, ContentType.APPLICATION_JSON);

        return headers;
    }

    @Override
    protected Map<String, String> populatePostHeaders(Map<String, String> map) {
        Map<String, String> headers = super.populatePostHeaders(map);
        headers.put(DATE_HEADER, DateTimeFormatter.RFC_1123_DATE_TIME.format(ZonedDateTime.now()));
        headers.put(RequestHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON);

        return headers;
    }

    @Override
    protected Map<String, String> populatePutHeaders(Map<String, String> headers) {
        headers.put(DATE_HEADER, DateTimeFormatter.RFC_1123_DATE_TIME.format(ZonedDateTime.now()));
        headers.put(RequestHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON);
        return headers;
    }
}
