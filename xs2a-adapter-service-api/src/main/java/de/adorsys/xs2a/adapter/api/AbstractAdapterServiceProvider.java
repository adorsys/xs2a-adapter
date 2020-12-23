package de.adorsys.xs2a.adapter.api;

public abstract class AbstractAdapterServiceProvider implements AccountInformationServiceProvider, PaymentInitiationServiceProvider {

    private boolean wiremockValidationEnabled;

    @Override
    public void wiremockValidationEnabled(boolean value) {
        this.wiremockValidationEnabled = value;
    }

    public boolean isWiremockValidationEnabled() {
        return this.wiremockValidationEnabled;
    }
}
