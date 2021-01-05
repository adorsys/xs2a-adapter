package de.adorsys.xs2a.adapter.impl;

import de.adorsys.xs2a.adapter.api.AccountInformationServiceProvider;
import de.adorsys.xs2a.adapter.api.PaymentInitiationServiceProvider;
import de.adorsys.xs2a.adapter.api.http.Interceptor;
import de.adorsys.xs2a.adapter.api.model.Aspsp;
import de.adorsys.xs2a.adapter.impl.http.wiremock.WiremockStubDifferenceDetectingInterceptor;

import java.util.*;

public abstract class AbstractAdapterServiceProvider implements AccountInformationServiceProvider, PaymentInitiationServiceProvider {

    private boolean wiremockValidationEnabled;

    @Override
    public void wiremockValidationEnabled(boolean value) {
        this.wiremockValidationEnabled = value;
    }

    public List<Interceptor> getInterceptors(Aspsp aspsp, Interceptor... interceptors) {
        List<Interceptor> list = new ArrayList<>(Optional.ofNullable(interceptors)
                                                     .map(Arrays::asList)
                                                     .orElseGet(Collections::emptyList));
        if (wiremockValidationEnabled && WiremockStubDifferenceDetectingInterceptor.isWiremockSupported(aspsp.getAdapterId())) {
            list.add(new WiremockStubDifferenceDetectingInterceptor(aspsp));
            return Collections.unmodifiableList(list);
        }
        return list;
    }
}
