package de.adorsys.xs2a.adapter.service.provider;

import de.adorsys.xs2a.adapter.adapter.BaseAccountInformationService;
import de.adorsys.xs2a.adapter.service.PaymentInitiationService;
import de.adorsys.xs2a.adapter.service.ais.AccountInformationService;
import de.adorsys.xs2a.adapter.service.impl.FinApiPaymentInitiationService;

public class FinApiServiceProvider implements AccountInformationServiceProvider, PaymentInitiationServiceProvider {

    @Override
    public AccountInformationService getAccountInformationService(String baseUrl) {
        return new BaseAccountInformationService(baseUrl);
    }

    @Override
    public PaymentInitiationService getPaymentInitiationService(String baseUrl) {
        return new FinApiPaymentInitiationService(baseUrl);
    }

    @Override
    public String getAdapterId() {
        return "finapi-adapter";
    }
}
