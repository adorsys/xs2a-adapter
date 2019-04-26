package de.adorsys.xs2a.gateway.service.provider;

import de.adorsys.xs2a.gateway.service.PaymentInitiationService;
import de.adorsys.xs2a.gateway.service.impl.SparkassePaymentInitiationService;

import java.util.Set;

import static de.adorsys.xs2a.gateway.service.provider.Sparkasse.BANK_CODES;

public class SparkassePaymentInitiationServiceProvider implements PaymentInitiationServiceProvider {

    private PaymentInitiationService paymentInitiationService;

    @Override
    public Set<String> getBankCodes() {
        return BANK_CODES;
    }

    @Override
    public PaymentInitiationService getPaymentInitiationService() {
        if (paymentInitiationService == null) {
            paymentInitiationService = new SparkassePaymentInitiationService();
        }
        return paymentInitiationService;
    }
}
