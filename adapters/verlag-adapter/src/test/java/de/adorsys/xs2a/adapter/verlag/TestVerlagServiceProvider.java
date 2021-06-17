package de.adorsys.xs2a.adapter.verlag;

import de.adorsys.xs2a.adapter.api.AccountInformationService;
import de.adorsys.xs2a.adapter.api.PaymentInitiationService;
import de.adorsys.xs2a.adapter.api.http.HttpClientFactory;
import de.adorsys.xs2a.adapter.api.link.LinksRewriter;
import de.adorsys.xs2a.adapter.api.model.Aspsp;

import java.util.AbstractMap;
import java.util.Collections;

/**
 * The purpose of this test class is to substitute apiKeyEntry map with a mocked one for WireMock testing.
 */
public class TestVerlagServiceProvider extends VerlagServiceProvider {
    private final AbstractMap.SimpleImmutableEntry<String, String> apiKeyEntry
        = new AbstractMap.SimpleImmutableEntry<>("foo", "boo");

    @Override
    public AccountInformationService getAccountInformationService(Aspsp aspsp,
                                                                  HttpClientFactory httpClientFactory,
                                                                  LinksRewriter linksRewriter) {
        return new VerlagAccountInformationService(aspsp,
            apiKeyEntry,
            httpClientFactory,
            Collections.emptyList(),
            linksRewriter);
    }

    @Override
    public PaymentInitiationService getPaymentInitiationService(Aspsp aspsp,
                                                                HttpClientFactory httpClientFactory,
                                                                LinksRewriter linksRewriter) {
        return new VerlagPaymentInitiationService(aspsp,
            apiKeyEntry,
            httpClientFactory,
            Collections.emptyList(),
            linksRewriter);
    }
}
