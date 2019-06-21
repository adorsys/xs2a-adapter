package de.adorsys.xs2a.adapter.service.impl;

import de.adorsys.xs2a.adapter.adapter.BasePaymentInitiationService;
import de.adorsys.xs2a.adapter.service.FinApiSinglePaymentInitiationBody;

public class FinApiPaymentInitiationService extends BasePaymentInitiationService {

    public FinApiPaymentInitiationService(String baseUri) {
        super(baseUri);

    }

    @Override
    protected Class<?> getSinglePaymentInitiationBodyClass() {
        return FinApiSinglePaymentInitiationBody.class;
    }

}
