package de.adorsys.xs2a.adapter.service.provider;

import de.adorsys.xs2a.adapter.http.HttpClient;
import de.adorsys.xs2a.adapter.http.RequestSigningInterceptor;
import de.adorsys.xs2a.adapter.service.PaymentInitiationService;
import de.adorsys.xs2a.adapter.service.ais.AccountInformationService;
import de.adorsys.xs2a.adapter.service.impl.OlbAccountInformationService;
import de.adorsys.xs2a.adapter.service.impl.OlbPaymentInitiationService;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class OlbServiceProvider implements AccountInformationServiceProvider, PaymentInitiationServiceProvider {

    private static final String BASE_URI = "https://xs2a-int.olb.de/xs2a";
    private final RequestSigningInterceptor requestSigningInterceptor = new RequestSigningInterceptor();

    private Set<String> bankCodes = Collections.unmodifiableSet(new HashSet<>(Collections.singletonList("28020050")));
    private OlbAccountInformationService accountInformationService;
    private OlbPaymentInitiationService paymentInitiationService;

    @Override
    public Set<String> getBankCodes() {
        return bankCodes;
    }

    @Override
    public String getBankName() {
        return "Olb Bank";
    }

    @Override
    public AccountInformationService getAccountInformationService() {
        if (accountInformationService == null) {
            accountInformationService = new OlbAccountInformationService(BASE_URI);
            accountInformationService.setHttpClient(HttpClient.newHttpClientWithSignature(requestSigningInterceptor));
        }
        return accountInformationService;
    }

    @Override
    public PaymentInitiationService getPaymentInitiationService() {
        if (paymentInitiationService == null) {
            paymentInitiationService = new OlbPaymentInitiationService(BASE_URI);
            paymentInitiationService.setHttpClient(HttpClient.newHttpClientWithSignature(requestSigningInterceptor));
        }
        return paymentInitiationService;
    }
}
