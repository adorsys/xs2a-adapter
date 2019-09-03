package de.adorsys.xs2a.adapter.service.psd2;

import de.adorsys.xs2a.adapter.service.psd2.model.*;

import java.util.Map;

public interface Psd2ConsentService {

    ConsentsResponse createConsent(Map<String, String> headers, Consents consents);

    ConsentInformationResponse getConsentInformation(String consentId, Map<String, String> headers);

    void deleteConsent(String consentId, Map<String, String> headers);

    ConsentStatusResponse getConsentStatus(String consentId, Map<String, String> headers);

    ScaStatusResponse getConsentScaStatus(String consentId,
                                          String authorisationId,
                                          Map<String, String> headers);

    StartScaprocessResponse startConsentAuthorisation(String consentId,
                                                      Map<String, String> headers,
                                                      UpdateAuthorisation updateAuthentication);

    UpdateAuthorisationResponse updateConsentsPsuData(String consentId,
                                                      String authorisationId,
                                                      Map<String, String> headers,
                                                      UpdateAuthorisation updateAuthentication);
}
