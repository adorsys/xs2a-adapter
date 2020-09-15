package de.adorsys.xs2a.adapter.impl.http.wiremock;

import org.junit.jupiter.api.Test;

import static de.adorsys.xs2a.adapter.impl.http.wiremock.WiremockFileResolver.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class WiremockFileResolverTest {
    private static final String DELETE_METHOD = "DELETE";
    private static final String POST_METHOD = "POST";
    private static final String PUT_METHOD = "PUT";
    private static final String GET_METHOD = "GET";
    private static final String CONSENTS_URI = "/v1/consents";
    private static final String ACCOUNTS_URI = "/v1/accounts";
    private static final String FLAWED_URI = "/foo/boo";

    @Test
    void aisCreateConsent() {
        assertTrue(AIS_CREATE_CONSENT.check(CONSENTS_URI, POST_METHOD, null));
        assertFalse(AIS_CREATE_CONSENT.check(FLAWED_URI, POST_METHOD, null));
    }

    // todo: add start psu authentication
    @Test
    void aisStartPsuAuthentication() {
    }

    @Test
    void aisUpdatePsuAuthentication() {
        // todo: update after branch refresh
        assertTrue(AIS_AUTHORISE_PSU.check(CONSENTS_URI, PUT_METHOD, "{\"psuData\": {}}"));
        assertFalse(AIS_AUTHORISE_PSU.check(FLAWED_URI, POST_METHOD, null));
    }

    @Test
    void aisSelectScaMethod() {
        assertTrue(AIS_SELECT_SCA_METHOD.check(CONSENTS_URI, PUT_METHOD, "{\"authenticationMethodId\": {}}"));
        assertFalse(AIS_SELECT_SCA_METHOD.check(FLAWED_URI, GET_METHOD, null));
    }

    @Test
    void aisSendOtp() {
        assertTrue(AIS_SEND_OTP.check(CONSENTS_URI, PUT_METHOD, "{\"scaAuthenticationData\": {}}"));
        assertFalse(AIS_SEND_OTP.check(FLAWED_URI, GET_METHOD, null));
    }

    @Test
    void aisGetAccounts() {
        assertTrue(AIS_GET_ACCOUNTS.check(ACCOUNTS_URI, GET_METHOD, null));
        assertFalse(AIS_GET_ACCOUNTS.check(FLAWED_URI, POST_METHOD, null));
    }

    @Test
    void aisGetTransactions() {
        assertTrue(AIS_GET_TRANSACTIONS.check(ACCOUNTS_URI + "/boo/transactions", GET_METHOD, null));
        assertFalse(AIS_GET_TRANSACTIONS.check(FLAWED_URI, POST_METHOD, "{\"foo\":{}}"));
    }

    @Test
    void aisDeleteConsent() {
        assertTrue(AIS_DELETE_CONSENT.check(CONSENTS_URI, DELETE_METHOD, null));
        assertFalse(AIS_DELETE_CONSENT.check(FLAWED_URI, POST_METHOD, "{\"foo\":{}}"));
    }

    @Test
    void aisGetScaStatus() {
        assertTrue(AIS_GET_SCA_STATUS.check(CONSENTS_URI + "/boo/authorisations/foo", GET_METHOD, null));
        assertFalse(AIS_GET_SCA_STATUS.check(FLAWED_URI, POST_METHOD, "{\"foo\":{}}"));
    }

    @Test
    void aisGetConsentStatus() {
        assertTrue(AIS_GET_CONSENT_STATUS.check(CONSENTS_URI + "/boo/status", GET_METHOD, null));
        assertFalse(AIS_GET_CONSENT_STATUS.check(FLAWED_URI, POST_METHOD, "{\"foo\":{}}"));
    }
}
