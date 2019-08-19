package de.adorsys.xs2a.adapter.service.psd2;

import de.adorsys.xs2a.adapter.service.psd2.model.*;

import java.util.Map;

public interface Psd2ConsentService {

    ConsentsResponse createConsent(Map<String, String> headers, Consents consents);

    default ConsentInformationResponse getConsentInformation(String consentId, Map<String, String> headers) {
        throw new UnsupportedOperationException();
    }

    default void deleteConsent(String consentId, Map<String, String> headers) {
        throw new UnsupportedOperationException();
    }

    default ConsentStatusResponse getConsentStatus(String consentId, Map<String, String> headers) {
        throw new UnsupportedOperationException();
    }

    default ScaStatusResponse getConsentScaStatus(String consentId,
                                                  String authorisationId,
                                                  Map<String, String> headers) {
        throw new UnsupportedOperationException();
    }
}
