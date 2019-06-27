package de.adorsys.xs2a.adapter.service.provider;

import de.adorsys.xs2a.adapter.http.HttpClient;
import de.adorsys.xs2a.adapter.http.RequestSigningInterceptor;
import de.adorsys.xs2a.adapter.service.PaymentInitiationService;
import de.adorsys.xs2a.adapter.service.ais.AccountInformationService;
import de.adorsys.xs2a.adapter.service.impl.OlbAccountInformationService;
import de.adorsys.xs2a.adapter.service.impl.OlbPaymentInitiationService;

public class OlbServiceProvider implements AccountInformationServiceProvider, PaymentInitiationServiceProvider {

    private final RequestSigningInterceptor requestSigningInterceptor = new RequestSigningInterceptor();

    @Override
    public AccountInformationService getAccountInformationService(String baseUrl) {
        OlbAccountInformationService accountInformationService = new OlbAccountInformationService(baseUrl);
        accountInformationService.setHttpClient(HttpClient.newHttpClientWithSignature(requestSigningInterceptor));
        return accountInformationService;
    }

    @Override
    public PaymentInitiationService getPaymentInitiationService(String baseUrl) {
        OlbPaymentInitiationService paymentInitiationService = new OlbPaymentInitiationService(baseUrl);
        paymentInitiationService.setHttpClient(HttpClient.newHttpClientWithSignature(requestSigningInterceptor));
        return paymentInitiationService;
    }

    @Override
    public String getAdapterId() {
        return "olb-adapter";
    }
}
