package de.adorsys.xs2a.gateway.service.impl;

import de.adorsys.xs2a.gateway.adapter.BasePaymentInitiationService;

import static de.adorsys.xs2a.gateway.service.provider.Sparkasse.BASE_URI;

public class SparkassePaymentInitiationService extends BasePaymentInitiationService {

    @Override
    protected String getBaseUri() {
        return BASE_URI;
    }
}
