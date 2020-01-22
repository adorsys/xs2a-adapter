package de.adorsys.xs2a.adapter.service;

import de.adorsys.xs2a.adapter.adapter.BaseAccountInformationService;
import de.adorsys.xs2a.adapter.http.HttpClient;
import de.adorsys.xs2a.adapter.service.model.Aspsp;

import java.util.Map;

public class ConsorsAccountInformationService extends BaseAccountInformationService {
    private static final String QUOTES = "\"\"";

    public ConsorsAccountInformationService(Aspsp aspsp, HttpClient httpClient) {
        super(aspsp, httpClient);
    }

    @Override
    protected Map<String, String> checkPsuIdHeaderForQuotes(Map<String, String> headers) {
        if (QUOTES.equals(headers.get(RequestHeaders.PSU_ID))) {
            headers.replace(RequestHeaders.PSU_ID, null);
        }

        return headers;
    }
}
