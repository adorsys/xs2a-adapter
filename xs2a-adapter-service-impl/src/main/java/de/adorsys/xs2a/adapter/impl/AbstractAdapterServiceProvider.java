package de.adorsys.xs2a.adapter.impl;

import de.adorsys.xs2a.adapter.api.AccountInformationServiceProvider;
import de.adorsys.xs2a.adapter.api.PaymentInitiationServiceProvider;
import de.adorsys.xs2a.adapter.api.http.Interceptor;
import de.adorsys.xs2a.adapter.api.model.Aspsp;
import de.adorsys.xs2a.adapter.impl.http.wiremock.WiremockStubDifferenceDetectingInterceptor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public abstract class AbstractAdapterServiceProvider implements AccountInformationServiceProvider, PaymentInitiationServiceProvider {

    private boolean wiremockValidationEnabled;

    @Override
    public void wiremockValidationEnabled(boolean value) {
        this.wiremockValidationEnabled = value;
    }

    public List<Interceptor> getInterceptors(Aspsp aspsp, Interceptor... interceptors) {
        List<Interceptor> list = interceptors != null ? Arrays.asList(interceptors) : new ArrayList<>();
        if (wiremockValidationEnabled && WiremockStubDifferenceDetectingInterceptor.isWiremockSupported(aspsp.getAdapterId())) {
            list.add(new WiremockStubDifferenceDetectingInterceptor(aspsp));
            return Collections.unmodifiableList(list);
        }
        return list;
    }
}
