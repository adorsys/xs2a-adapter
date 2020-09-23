package de.adorsys.xs2a.adapter.impl.http.wiremock;

import org.junit.jupiter.api.Test;

import static de.adorsys.xs2a.adapter.impl.http.wiremock.WiremockFileResolver.*;
import static org.junit.jupiter.api.Assertions.*;

class WiremockFileResolverTest {
    private static final String DELETE_METHOD = "DELETE";
    private static final String POST_METHOD = "POST";
    private static final String PUT_METHOD = "PUT";
    private static final String GET_METHOD = "GET";
    private static final String CONSENTS_URI = "/v1/consents";
    private static final String ACCOUNTS_URI = "/v1/accounts";
    private static final String PAYMENTS_SCT_URI = "/v1/payments/sepa-credit-transfers";
    private static final String PAYMENTS_PAIN001_SCT_URI = "/v1/payments/pain.001-sepa-credit-transfers";
    private static final String PERIODIC_SCT_URI = "/v1/periodic-payments/sepa-credit-transfers";
    private static final String PERIODIC_PAIN001_CST_URI = "/v1/periodic-payments/pain.001-sepa-credit-transfers";
    // Flawed data
    private static final String FLAWED_URI = "/foo/boo";
    private static final String FLAWED_BODY = "{\"foo\": \"boo\"}";
    private static final String FLAWED_METHOD = "FOO";
    // Decent data
    private static final String BOO_AUTHORISATIONS_URI = "/boo/authorisations";
    private static final String BOO_STATUS_URI = "/boo/status";
    // AIS
    private static final String CONSENT_STATUS_URI = CONSENTS_URI + BOO_STATUS_URI;
    private static final String AIS_START_PSU_AUTHORISATION_URI = CONSENTS_URI + BOO_AUTHORISATIONS_URI;
    private static final String AIS_SCA_URI = AIS_START_PSU_AUTHORISATION_URI + "/foo";
    private static final String TRANSACTIONS_URI = ACCOUNTS_URI + "/boo/transactions";
    // PIS
    private static final String PAYMENTS_SCT_AUTHORISATIONS_URI = PAYMENTS_SCT_URI + BOO_AUTHORISATIONS_URI;
    private static final String PAYMENTS_PAIN001_SCT_AUTHORISATIONS_URI = PAYMENTS_PAIN001_SCT_URI + BOO_AUTHORISATIONS_URI;
    private static final String PERIODIC_SCT_AUTHORISATIONS_URI = PERIODIC_SCT_URI + BOO_AUTHORISATIONS_URI;
    private static final String PERIODIC_PAIN001_SCT_AUTHORISATIONS_URI = PERIODIC_PAIN001_CST_URI + BOO_AUTHORISATIONS_URI;
    private static final String PAYMENTS_SCT_PAYMENT_STATUS_URI = PAYMENTS_SCT_URI + BOO_STATUS_URI;
    private static final String PAYMENTS_PAIN001_SCT_PAYMENT_STATUS_URI = PAYMENTS_PAIN001_SCT_URI + BOO_STATUS_URI;
    private static final String PERIODIC_SCT_PAYMENT_STATUS_URI = PERIODIC_SCT_URI + BOO_STATUS_URI;
    private static final String PERIODIC_PAIN001_SCT_PAYMENT_STATUS_URI = PERIODIC_PAIN001_CST_URI + BOO_STATUS_URI;
    private static final String PAYMENTS_SCT_SCA_STATUS_URI = PAYMENTS_SCT_URI + BOO_AUTHORISATIONS_URI;
    private static final String PAYMENTS_PAIN001_SCT_SCA_STATUS_URI = PAYMENTS_PAIN001_SCT_URI + BOO_AUTHORISATIONS_URI;
    private static final String PERIODIC_SCT_SCA_STATUS_URI = PERIODIC_SCT_URI + BOO_AUTHORISATIONS_URI;
    private static final String PERIODIC_PAIN001_SCT_SCA_STATUS_URI = PERIODIC_PAIN001_CST_URI + BOO_AUTHORISATIONS_URI;
    // Both
    private static final String PSU_DATA_BODY = "{\"psuData\": \"\"}";
    private static final String AUTHENTICATION_METHOD_ID_BODY = "{\"authenticationMethodId\": \"\"}";
    private static final String SCA_AUTHENTICATION_DATA_BODY = "{\"scaAuthenticationData\": \"\"}";

    @Test
    void aisCreateConsent() {
        assertTrue(AIS_CREATE_CONSENT.check(CONSENTS_URI, POST_METHOD, null));

        assertFalse(AIS_CREATE_CONSENT.check(FLAWED_URI, POST_METHOD, null));
        assertFalse(AIS_CREATE_CONSENT.check(CONSENTS_URI, FLAWED_METHOD, null));
    }

    @Test
    void aisStartPsuAuthentication() {
        assertTrue(AIS_START_PSU_AUTHENTICATION.check(AIS_START_PSU_AUTHORISATION_URI, POST_METHOD, null));

        assertFalse(AIS_START_PSU_AUTHENTICATION.check(FLAWED_URI, POST_METHOD, null));
        assertFalse(AIS_START_PSU_AUTHENTICATION.check(AIS_START_PSU_AUTHORISATION_URI, FLAWED_METHOD, null));
    }

    @Test
    void aisUpdatePsuAuthentication() {
        assertTrue(AIS_UPDATE_PSU_AUTHENTICATION.check(CONSENTS_URI, PUT_METHOD, PSU_DATA_BODY));

        assertFalse(AIS_UPDATE_PSU_AUTHENTICATION.check(FLAWED_URI, PUT_METHOD, PSU_DATA_BODY));
        assertFalse(AIS_UPDATE_PSU_AUTHENTICATION.check(CONSENTS_URI, FLAWED_METHOD, PSU_DATA_BODY));
        assertFalse(AIS_UPDATE_PSU_AUTHENTICATION.check(CONSENTS_URI, PUT_METHOD, FLAWED_BODY));
    }

    @Test
    void aisSelectScaMethod() {
        assertTrue(AIS_SELECT_SCA_METHOD.check(CONSENTS_URI, PUT_METHOD, AUTHENTICATION_METHOD_ID_BODY));

        assertFalse(AIS_SELECT_SCA_METHOD.check(FLAWED_URI, PUT_METHOD, AUTHENTICATION_METHOD_ID_BODY));
        assertFalse(AIS_SELECT_SCA_METHOD.check(CONSENTS_URI, FLAWED_METHOD, AUTHENTICATION_METHOD_ID_BODY));
        assertFalse(AIS_SELECT_SCA_METHOD.check(CONSENTS_URI, PUT_METHOD, FLAWED_BODY));
    }

    @Test
    void aisSendOtp() {
        assertTrue(AIS_SEND_OTP.check(CONSENTS_URI, PUT_METHOD, SCA_AUTHENTICATION_DATA_BODY));

        assertFalse(AIS_SEND_OTP.check(FLAWED_URI, PUT_METHOD, SCA_AUTHENTICATION_DATA_BODY));
        assertFalse(AIS_SEND_OTP.check(CONSENTS_URI, FLAWED_METHOD, SCA_AUTHENTICATION_DATA_BODY));
        assertFalse(AIS_SEND_OTP.check(CONSENTS_URI, PUT_METHOD, FLAWED_BODY));
    }

    @Test
    void aisGetAccounts() {
        assertTrue(AIS_GET_ACCOUNTS.check(ACCOUNTS_URI, GET_METHOD, null));

        assertFalse(AIS_GET_ACCOUNTS.check(FLAWED_URI, GET_METHOD, null));
        assertFalse(AIS_GET_ACCOUNTS.check(ACCOUNTS_URI, FLAWED_METHOD, null));
    }

    @Test
    void aisGetTransactions() {
        assertTrue(AIS_GET_TRANSACTIONS.check(TRANSACTIONS_URI, GET_METHOD, null));

        assertFalse(AIS_GET_TRANSACTIONS.check(FLAWED_URI, GET_METHOD, null));
        assertFalse(AIS_GET_TRANSACTIONS.check(TRANSACTIONS_URI, FLAWED_METHOD, null));
    }

    @Test
    void aisDeleteConsent() {
        assertTrue(AIS_DELETE_CONSENT.check(CONSENTS_URI, DELETE_METHOD, null));

        assertFalse(AIS_DELETE_CONSENT.check(FLAWED_URI, DELETE_METHOD, null));
        assertFalse(AIS_DELETE_CONSENT.check(CONSENTS_URI, FLAWED_METHOD, null));
    }

    @Test
    void aisGetScaStatus() {
        assertTrue(AIS_GET_SCA_STATUS.check(AIS_SCA_URI, GET_METHOD, null));

        assertFalse(AIS_GET_SCA_STATUS.check(FLAWED_URI, GET_METHOD, null));
        assertFalse(AIS_GET_SCA_STATUS.check(AIS_SCA_URI, FLAWED_METHOD, null));
    }

    @Test
    void aisGetConsentStatus() {
        assertTrue(AIS_GET_CONSENT_STATUS.check(CONSENT_STATUS_URI, GET_METHOD, null));

        assertFalse(AIS_GET_CONSENT_STATUS.check(FLAWED_URI, GET_METHOD, null));
        assertFalse(AIS_GET_CONSENT_STATUS.check(CONSENT_STATUS_URI, FLAWED_METHOD, null));
    }

    @Test
    void pisPaymentsSctInitiatePayments() {
        assertTrue(PIS_PAYMENTS_SCT_INITIATE_PAYMENT.check(PAYMENTS_SCT_URI, POST_METHOD, null));

        assertFalse(PIS_PAYMENTS_SCT_INITIATE_PAYMENT.check(FLAWED_URI, POST_METHOD, null));
        assertFalse(PIS_PAYMENTS_SCT_INITIATE_PAYMENT.check(PAYMENTS_SCT_URI, FLAWED_METHOD, null));
    }

    @Test
    void pisPaymentsPain001SctInitiatePayments() {
        assertTrue(PIS_PAYMENTS_PAIN001_SCT_INITIATE_PAYMENT.check(PAYMENTS_PAIN001_SCT_URI, POST_METHOD, null));

        assertFalse(PIS_PAYMENTS_PAIN001_SCT_INITIATE_PAYMENT.check(FLAWED_URI, POST_METHOD, null));
        assertFalse(PIS_PAYMENTS_PAIN001_SCT_INITIATE_PAYMENT.check(PAYMENTS_PAIN001_SCT_URI, FLAWED_METHOD, null));
    }

    @Test
    void pisPeriodicSctInitiatePayments() {
        assertTrue(PIS_PERIODIC_SCT_INITIATE_PAYMENT.check(PERIODIC_SCT_URI, POST_METHOD, null));

        assertFalse(PIS_PERIODIC_SCT_INITIATE_PAYMENT.check(FLAWED_URI, POST_METHOD, null));
        assertFalse(PIS_PERIODIC_SCT_INITIATE_PAYMENT.check(PERIODIC_SCT_URI, FLAWED_METHOD, null));
    }

    @Test
    void pisPeriodicPain001SctInitiatePayments() {
        assertTrue(PIS_PERIODIC_PAIN001_SCT_INITIATE_PAYMENT.check(PERIODIC_PAIN001_CST_URI, POST_METHOD, null));

        assertFalse(PIS_PERIODIC_PAIN001_SCT_INITIATE_PAYMENT.check(FLAWED_URI, POST_METHOD, null));
        assertFalse(PIS_PERIODIC_PAIN001_SCT_INITIATE_PAYMENT.check(PERIODIC_PAIN001_CST_URI, FLAWED_METHOD, null));
    }

    @Test
    void pisPaymentsSctStartPsuAuthentication() {
        assertTrue(PIS_PAYMENTS_SCT_START_PSU_AUTHENTICATION.check(PAYMENTS_SCT_AUTHORISATIONS_URI, POST_METHOD, null));

        assertFalse(PIS_PAYMENTS_SCT_START_PSU_AUTHENTICATION.check(FLAWED_URI, POST_METHOD, null));
        assertFalse(PIS_PAYMENTS_SCT_START_PSU_AUTHENTICATION.check(PAYMENTS_SCT_AUTHORISATIONS_URI, FLAWED_METHOD, null));
    }

    @Test
    void pisPaymentsPain001SctStartPsuAuthentication() {
        assertTrue(PIS_PAYMENTS_PAIN001_SCT_START_PSU_AUTHENTICATION.check(PAYMENTS_PAIN001_SCT_AUTHORISATIONS_URI, POST_METHOD, null));

        assertFalse(PIS_PAYMENTS_PAIN001_SCT_START_PSU_AUTHENTICATION.check(FLAWED_URI, POST_METHOD, null));
        assertFalse(PIS_PAYMENTS_PAIN001_SCT_START_PSU_AUTHENTICATION.check(PAYMENTS_PAIN001_SCT_AUTHORISATIONS_URI, FLAWED_METHOD, null));
    }

    @Test
    void pisPeriodicSctStartPsuAuthentication() {
        assertTrue(PIS_PERIODIC_SCT_START_PSU_AUTHENTICATION.check(PERIODIC_SCT_AUTHORISATIONS_URI, POST_METHOD, null));

        assertFalse(PIS_PERIODIC_SCT_START_PSU_AUTHENTICATION.check(FLAWED_URI, POST_METHOD, null));
        assertFalse(PIS_PERIODIC_SCT_START_PSU_AUTHENTICATION.check(PERIODIC_SCT_AUTHORISATIONS_URI, FLAWED_METHOD, null));
    }

    @Test
    void pisPeriodicPain001SctStartPsuAuthentication() {
        assertTrue(PIS_PERIODIC_PAIN001_SCT_START_PSU_AUTHENTICATION.check(PERIODIC_PAIN001_SCT_AUTHORISATIONS_URI, POST_METHOD, null));

        assertFalse(PIS_PERIODIC_PAIN001_SCT_START_PSU_AUTHENTICATION.check(FLAWED_URI, POST_METHOD, null));
        assertFalse(PIS_PERIODIC_PAIN001_SCT_START_PSU_AUTHENTICATION.check(PERIODIC_PAIN001_SCT_AUTHORISATIONS_URI, FLAWED_METHOD, null));
    }

    @Test
    void pisPaymentsSctUpdatePsuAuthentication() {
        assertTrue(PIS_PAYMENTS_SCT_UPDATE_PSU_AUTHENTICATION.check(PAYMENTS_SCT_URI, PUT_METHOD, PSU_DATA_BODY));

        assertFalse(PIS_PAYMENTS_SCT_UPDATE_PSU_AUTHENTICATION.check(FLAWED_URI, PUT_METHOD, PSU_DATA_BODY));
        assertFalse(PIS_PAYMENTS_SCT_UPDATE_PSU_AUTHENTICATION.check(PAYMENTS_SCT_URI, FLAWED_METHOD, PSU_DATA_BODY));
        assertFalse(PIS_PAYMENTS_SCT_UPDATE_PSU_AUTHENTICATION.check(PAYMENTS_SCT_URI, PUT_METHOD, FLAWED_BODY));
    }

    @Test
    void pisPaymentsPain001SctUpdatePsuAuthentication() {
        assertTrue(PIS_PAYMENTS_PAIN001_SCT_UPDATE_PSU_AUTHENTICATION.check(PAYMENTS_PAIN001_SCT_URI, PUT_METHOD, PSU_DATA_BODY));

        assertFalse(PIS_PAYMENTS_PAIN001_SCT_UPDATE_PSU_AUTHENTICATION.check(FLAWED_URI, PUT_METHOD, PSU_DATA_BODY));
        assertFalse(PIS_PAYMENTS_PAIN001_SCT_UPDATE_PSU_AUTHENTICATION.check(PAYMENTS_PAIN001_SCT_URI, FLAWED_METHOD, PSU_DATA_BODY));
        assertFalse(PIS_PAYMENTS_PAIN001_SCT_UPDATE_PSU_AUTHENTICATION.check(PAYMENTS_PAIN001_SCT_URI, PUT_METHOD, FLAWED_BODY));
    }

    @Test
    void pisPeriodicSctUpdatePsuAuthentication() {
        assertTrue(PIS_PERIODIC_SCT_UPDATE_PSU_AUTHENTICATION.check(PERIODIC_SCT_URI, PUT_METHOD, PSU_DATA_BODY));

        assertFalse(PIS_PERIODIC_SCT_UPDATE_PSU_AUTHENTICATION.check(FLAWED_URI, PUT_METHOD, PSU_DATA_BODY));
        assertFalse(PIS_PERIODIC_SCT_UPDATE_PSU_AUTHENTICATION.check(PERIODIC_SCT_URI, FLAWED_METHOD, PSU_DATA_BODY));
        assertFalse(PIS_PERIODIC_SCT_UPDATE_PSU_AUTHENTICATION.check(PERIODIC_SCT_URI, PUT_METHOD, FLAWED_BODY));
    }

    @Test
    void pisPeriodicPain001SctUpdatePsuAuthentication() {
        assertTrue(PIS_PERIODIC_PAIN001_SCT_UPDATE_PSU_AUTHENTICATION.check(PERIODIC_PAIN001_CST_URI, PUT_METHOD, PSU_DATA_BODY));

        assertFalse(PIS_PERIODIC_PAIN001_SCT_UPDATE_PSU_AUTHENTICATION.check(FLAWED_URI, PUT_METHOD, PSU_DATA_BODY));
        assertFalse(PIS_PERIODIC_PAIN001_SCT_UPDATE_PSU_AUTHENTICATION.check(PERIODIC_PAIN001_CST_URI, FLAWED_METHOD, PSU_DATA_BODY));
        assertFalse(PIS_PERIODIC_PAIN001_SCT_UPDATE_PSU_AUTHENTICATION.check(PERIODIC_PAIN001_CST_URI, PUT_METHOD, FLAWED_BODY));
    }

    @Test
    void pisPaymentsSctSelectScaMethod() {
        assertTrue(PIS_PAYMENTS_SCT_SELECT_SCA_METHOD.check(PAYMENTS_SCT_URI, PUT_METHOD, AUTHENTICATION_METHOD_ID_BODY));

        assertFalse(PIS_PAYMENTS_SCT_SELECT_SCA_METHOD.check(FLAWED_URI, PUT_METHOD, AUTHENTICATION_METHOD_ID_BODY));
        assertFalse(PIS_PAYMENTS_SCT_SELECT_SCA_METHOD.check(PAYMENTS_SCT_URI, FLAWED_METHOD, AUTHENTICATION_METHOD_ID_BODY));
        assertFalse(PIS_PAYMENTS_SCT_SELECT_SCA_METHOD.check(PAYMENTS_SCT_URI, PUT_METHOD, FLAWED_BODY));
    }

    @Test
    void pisPaymentsPain001SctSelectScaMethod() {
        assertTrue(PIS_PAYMENTS_PAIN001_SCT_SELECT_SCA_METHOD.check(PAYMENTS_PAIN001_SCT_URI, PUT_METHOD, AUTHENTICATION_METHOD_ID_BODY));

        assertFalse(PIS_PAYMENTS_PAIN001_SCT_SELECT_SCA_METHOD.check(FLAWED_URI, PUT_METHOD, AUTHENTICATION_METHOD_ID_BODY));
        assertFalse(PIS_PAYMENTS_PAIN001_SCT_SELECT_SCA_METHOD.check(PAYMENTS_PAIN001_SCT_URI, FLAWED_METHOD, AUTHENTICATION_METHOD_ID_BODY));
        assertFalse(PIS_PAYMENTS_PAIN001_SCT_SELECT_SCA_METHOD.check(PAYMENTS_PAIN001_SCT_URI, PUT_METHOD, FLAWED_BODY));
    }

    @Test
    void pisPeriodicSctSelectScaMethod() {
        assertTrue(PIS_PERIODIC_SCT_SELECT_SCA_METHOD.check(PERIODIC_SCT_URI, PUT_METHOD, AUTHENTICATION_METHOD_ID_BODY));

        assertFalse(PIS_PERIODIC_SCT_SELECT_SCA_METHOD.check(FLAWED_URI, PUT_METHOD, AUTHENTICATION_METHOD_ID_BODY));
        assertFalse(PIS_PERIODIC_SCT_SELECT_SCA_METHOD.check(PERIODIC_SCT_URI, FLAWED_METHOD, AUTHENTICATION_METHOD_ID_BODY));
        assertFalse(PIS_PERIODIC_SCT_SELECT_SCA_METHOD.check(PERIODIC_SCT_URI, PUT_METHOD, FLAWED_BODY));
    }

    @Test
    void pisPeriodicsPain001SctSelectScaMethod() {
        assertTrue(PIS_PERIODIC_PAIN001_SCT_SELECT_SCA_METHOD.check(PERIODIC_PAIN001_CST_URI, PUT_METHOD, AUTHENTICATION_METHOD_ID_BODY));

        assertFalse(PIS_PERIODIC_PAIN001_SCT_SELECT_SCA_METHOD.check(FLAWED_URI, PUT_METHOD, AUTHENTICATION_METHOD_ID_BODY));
        assertFalse(PIS_PERIODIC_PAIN001_SCT_SELECT_SCA_METHOD.check(PERIODIC_PAIN001_CST_URI, FLAWED_METHOD, AUTHENTICATION_METHOD_ID_BODY));
        assertFalse(PIS_PERIODIC_PAIN001_SCT_SELECT_SCA_METHOD.check(PERIODIC_PAIN001_CST_URI, PUT_METHOD, FLAWED_BODY));
    }

    @Test
    void pisPaymentsScaSendOtp() {
        assertTrue(PIS_PAYMENTS_SCT_SEND_OTP.check(PAYMENTS_SCT_URI, PUT_METHOD, SCA_AUTHENTICATION_DATA_BODY));

        assertFalse(PIS_PAYMENTS_SCT_SEND_OTP.check(FLAWED_URI, PUT_METHOD, SCA_AUTHENTICATION_DATA_BODY));
        assertFalse(PIS_PAYMENTS_SCT_SEND_OTP.check(PAYMENTS_SCT_URI, FLAWED_METHOD, SCA_AUTHENTICATION_DATA_BODY));
        assertFalse(PIS_PAYMENTS_SCT_SEND_OTP.check(PAYMENTS_SCT_URI, PUT_METHOD, FLAWED_BODY));
    }

    @Test
    void pisPaymentsPain001ScaSendOtp() {
        assertTrue(PIS_PAYMENTS_PAIN001_SCT_SEND_OTP.check(PAYMENTS_PAIN001_SCT_URI, PUT_METHOD, SCA_AUTHENTICATION_DATA_BODY));

        assertFalse(PIS_PAYMENTS_PAIN001_SCT_SEND_OTP.check(FLAWED_URI, PUT_METHOD, SCA_AUTHENTICATION_DATA_BODY));
        assertFalse(PIS_PAYMENTS_PAIN001_SCT_SEND_OTP.check(PAYMENTS_PAIN001_SCT_URI, FLAWED_METHOD, SCA_AUTHENTICATION_DATA_BODY));
        assertFalse(PIS_PAYMENTS_PAIN001_SCT_SEND_OTP.check(PAYMENTS_PAIN001_SCT_URI, PUT_METHOD, FLAWED_BODY));
    }

    @Test
    void pisPeriodicScaSendOtp() {
        assertTrue(PIS_PERIODIC_SCT_SEND_OTP.check(PERIODIC_SCT_URI, PUT_METHOD, SCA_AUTHENTICATION_DATA_BODY));

        assertFalse(PIS_PERIODIC_SCT_SEND_OTP.check(FLAWED_URI, PUT_METHOD, SCA_AUTHENTICATION_DATA_BODY));
        assertFalse(PIS_PERIODIC_SCT_SEND_OTP.check(PERIODIC_SCT_URI, FLAWED_METHOD, SCA_AUTHENTICATION_DATA_BODY));
        assertFalse(PIS_PERIODIC_SCT_SEND_OTP.check(PERIODIC_SCT_URI, PUT_METHOD, FLAWED_BODY));
    }

    @Test
    void pisPeriodicPain001ScaSendOtp() {
        assertTrue(PIS_PERIODIC_PAIN001_SCT_SEND_OTP.check(PERIODIC_PAIN001_CST_URI, PUT_METHOD, SCA_AUTHENTICATION_DATA_BODY));

        assertFalse(PIS_PERIODIC_PAIN001_SCT_SEND_OTP.check(FLAWED_URI, PUT_METHOD, SCA_AUTHENTICATION_DATA_BODY));
        assertFalse(PIS_PERIODIC_PAIN001_SCT_SEND_OTP.check(PERIODIC_PAIN001_CST_URI, FLAWED_METHOD, SCA_AUTHENTICATION_DATA_BODY));
        assertFalse(PIS_PERIODIC_PAIN001_SCT_SEND_OTP.check(PERIODIC_PAIN001_CST_URI, PUT_METHOD, FLAWED_BODY));
    }

    @Test
    void pisPaymentsSctGetTransactionStatus() {
        assertTrue(PIS_PAYMENTS_SCT_GET_TRANSACTION_STATUS.check(PAYMENTS_SCT_PAYMENT_STATUS_URI, GET_METHOD, null));

        assertFalse(PIS_PAYMENTS_SCT_GET_TRANSACTION_STATUS.check(FLAWED_URI, GET_METHOD, null));
        assertFalse(PIS_PAYMENTS_SCT_GET_TRANSACTION_STATUS.check(PAYMENTS_SCT_PAYMENT_STATUS_URI, FLAWED_METHOD, null));
    }

    @Test
    void pisPaymentsPain001SctGetTransactionStatus() {
        assertTrue(PIS_PAYMENTS_PAIN001_SCT_GET_TRANSACTION_STATUS.check(PAYMENTS_PAIN001_SCT_PAYMENT_STATUS_URI, GET_METHOD, null));

        assertFalse(PIS_PAYMENTS_PAIN001_SCT_GET_TRANSACTION_STATUS.check(FLAWED_URI, GET_METHOD, null));
        assertFalse(PIS_PAYMENTS_PAIN001_SCT_GET_TRANSACTION_STATUS.check(PAYMENTS_PAIN001_SCT_PAYMENT_STATUS_URI, FLAWED_METHOD, null));
    }

    @Test
    void pisPeriodicSctGetTransactionStatus() {
        assertTrue(PIS_PERIODIC_SCT_GET_TRANSACTION_STATUS.check(PERIODIC_SCT_PAYMENT_STATUS_URI, GET_METHOD, null));

        assertFalse(PIS_PERIODIC_SCT_GET_TRANSACTION_STATUS.check(FLAWED_URI, GET_METHOD, null));
        assertFalse(PIS_PERIODIC_SCT_GET_TRANSACTION_STATUS.check(PERIODIC_SCT_PAYMENT_STATUS_URI, FLAWED_METHOD, null));
    }

    @Test
    void pisPeriodicPain001SctGetTransactionStatus() {
        assertTrue(PIS_PERIODIC_PAIN001_SCT_GET_TRANSACTION_STATUS.check(PERIODIC_PAIN001_SCT_PAYMENT_STATUS_URI, GET_METHOD, null));

        assertFalse(PIS_PERIODIC_PAIN001_SCT_GET_TRANSACTION_STATUS.check(FLAWED_URI, GET_METHOD, null));
        assertFalse(PIS_PERIODIC_PAIN001_SCT_GET_TRANSACTION_STATUS.check(PERIODIC_PAIN001_SCT_PAYMENT_STATUS_URI, FLAWED_METHOD, null));
    }

    @Test
    void pisPaymentsSctGetScaStatus() {
        assertTrue(PIS_PAYMENTS_SCT_GET_SCA_STATUS.check(PAYMENTS_SCT_SCA_STATUS_URI, GET_METHOD, null));

        assertFalse(PIS_PAYMENTS_SCT_GET_SCA_STATUS.check(FLAWED_URI, GET_METHOD, null));
        assertFalse(PIS_PAYMENTS_SCT_GET_SCA_STATUS.check(PAYMENTS_SCT_SCA_STATUS_URI, FLAWED_METHOD, null));
    }

    @Test
    void pisPaymentsPain001SctGetScaStatus() {
        assertTrue(PIS_PAYMENTS_PAIN001_SCT_GET_SCA_STATUS.check(PAYMENTS_PAIN001_SCT_SCA_STATUS_URI, GET_METHOD, null));

        assertFalse(PIS_PAYMENTS_PAIN001_SCT_GET_SCA_STATUS.check(FLAWED_URI, GET_METHOD, null));
        assertFalse(PIS_PAYMENTS_PAIN001_SCT_GET_SCA_STATUS.check(PAYMENTS_PAIN001_SCT_SCA_STATUS_URI, FLAWED_METHOD, null));
    }

    @Test
    void pisPeriodicSctGetScaStatus() {
        assertTrue(PIS_PERIODIC_SCT_GET_SCA_STATUS.check(PERIODIC_SCT_SCA_STATUS_URI, GET_METHOD, null));

        assertFalse(PIS_PERIODIC_SCT_GET_SCA_STATUS.check(FLAWED_URI, GET_METHOD, null));
        assertFalse(PIS_PERIODIC_SCT_GET_SCA_STATUS.check(PERIODIC_SCT_SCA_STATUS_URI, FLAWED_METHOD, null));
    }

    @Test
    void pisPeriodicPain001SctGetScaStatus() {
        assertTrue(PIS_PERIODIC_PAIN001_SCT_GET_SCA_STATUS.check(PERIODIC_PAIN001_SCT_SCA_STATUS_URI, GET_METHOD, null));

        assertFalse(PIS_PERIODIC_PAIN001_SCT_GET_SCA_STATUS.check(FLAWED_URI, GET_METHOD, null));
        assertFalse(PIS_PERIODIC_PAIN001_SCT_GET_SCA_STATUS.check(PERIODIC_PAIN001_SCT_SCA_STATUS_URI, FLAWED_METHOD, null));
    }

    @Test
    void resolve_throwException() {
        assertThrows(IllegalStateException.class, () -> WiremockFileResolver.resolve(FLAWED_URI, FLAWED_METHOD, FLAWED_BODY));
    }
}
