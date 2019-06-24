package de.adorsys.xs2a.adapter.service.provider;

import de.adorsys.xs2a.adapter.adapter.BaseAccountInformationService;
import de.adorsys.xs2a.adapter.service.PaymentInitiationService;
import de.adorsys.xs2a.adapter.service.ais.AccountInformationService;
import de.adorsys.xs2a.adapter.service.impl.FinApiPaymentInitiationService;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class FinApiServiceProvider implements AccountInformationServiceProvider, PaymentInitiationServiceProvider {

    private static final String BASE_URI = "https://banking.aab.de/xs2a-app/v1";
    private Set<String> bankCodes = Collections.unmodifiableSet(new HashSet<>(Collections.singletonList("444444")));
    private static final String BANK_NAME = "AAB Bank";
    private AccountInformationService accountInformationService;
    private PaymentInitiationService paymentInitiationService;

    @Override
    public Set<String> getBankCodes() {
        return bankCodes;
    }

    @Override
    public String getBankName() {
        return BANK_NAME;
    }

    @Override
    public AccountInformationService getAccountInformationService() {
        if (accountInformationService == null) {
            accountInformationService = new BaseAccountInformationService(BASE_URI);
        }
        return accountInformationService;
    }

    @Override
    public PaymentInitiationService getPaymentInitiationService() {
        if (paymentInitiationService == null) {
            paymentInitiationService = new FinApiPaymentInitiationService(BASE_URI);
        }
        return paymentInitiationService;
    }
}
