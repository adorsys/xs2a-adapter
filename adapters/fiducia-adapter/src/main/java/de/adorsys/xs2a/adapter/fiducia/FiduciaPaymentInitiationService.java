package de.adorsys.xs2a.adapter.fiducia;

import de.adorsys.xs2a.adapter.adapter.BasePaymentInitiationService;
import de.adorsys.xs2a.adapter.http.HttpClient;
import de.adorsys.xs2a.adapter.http.Request;
import de.adorsys.xs2a.adapter.service.link.LinksRewriter;
import de.adorsys.xs2a.adapter.service.model.Aspsp;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import static org.apache.http.protocol.HTTP.DATE_HEADER;

public class FiduciaPaymentInitiationService extends BasePaymentInitiationService {

    public FiduciaPaymentInitiationService(Aspsp aspsp,
                                           HttpClient httpClient,
                                           Request.Builder.Interceptor requestBuilderInterceptor,
                                           LinksRewriter linksRewriter) {
        super(aspsp, httpClient, requestBuilderInterceptor, linksRewriter);
    }

    @Override
    protected Map<String, String> populatePostHeaders(Map<String, String> headers) {
        headers.put(DATE_HEADER, DateTimeFormatter.RFC_1123_DATE_TIME.format(ZonedDateTime.now()));
        return headers;
    }

    @Override
    protected Map<String, String> populateGetHeaders(Map<String, String> headers) {
        headers.put(DATE_HEADER, DateTimeFormatter.RFC_1123_DATE_TIME.format(ZonedDateTime.now()));
        return headers;
    }

    @Override
    protected Map<String, String> populatePutHeaders(Map<String, String> headers) {
        headers.put(DATE_HEADER, DateTimeFormatter.RFC_1123_DATE_TIME.format(ZonedDateTime.now()));
        return headers;
    }
}
