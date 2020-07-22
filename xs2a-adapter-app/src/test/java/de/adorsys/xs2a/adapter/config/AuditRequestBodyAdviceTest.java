package de.adorsys.xs2a.adapter.config;

import de.adorsys.xs2a.adapter.api.model.AccountAccess;
import de.adorsys.xs2a.adapter.api.model.AccountReference;
import de.adorsys.xs2a.adapter.api.model.Consents;
import de.adorsys.xs2a.adapter.service.AccountInformationService;
import de.adorsys.xs2a.adapter.service.RequestHeaders;
import de.adorsys.xs2a.adapter.service.RequestParams;
import org.junit.jupiter.api.Test;
import org.slf4j.MDC;
import org.springframework.core.MethodParameter;

import java.lang.reflect.Method;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

class AuditRequestBodyAdviceTest {

    private final MethodParameter methodParameter = mock(MethodParameter.class);
    private final AuditRequestBodyAdvice advice = new AuditRequestBodyAdvice();

    @Test
    void supportsMethodIsNull() {

        AuditRequestBodyAdvice advice = new AuditRequestBodyAdvice();

        boolean supports = advice.supports(methodParameter, null, null);

        assertThat(supports).isFalse();
    }

    @Test
    void supportsMethodIsDifferent() throws NoSuchMethodException {
        Method method = AccountInformationService.class.getMethod("getConsentStatus",
                                                                  String.class,
                                                                  RequestHeaders.class,
                                                                  RequestParams.class);
        doReturn(method).when(methodParameter).getMethod();

        boolean supports = advice.supports(methodParameter, null, null);

        assertThat(supports).isFalse();

        method = AccountInformationService.class.getMethod("createConsent",
                                                           RequestHeaders.class,
                                                           RequestParams.class,
                                                           Consents.class);
        doReturn(method).when(methodParameter).getMethod();

        supports = advice.supports(methodParameter, null, null);

        assertThat(supports).isTrue();
    }

    @Test
    void supports() throws NoSuchMethodException {
        AuditRequestBodyAdvice advice = new AuditRequestBodyAdvice();

        Method method = AccountInformationService.class.getMethod("createConsent",
                                                                  RequestHeaders.class,
                                                                  RequestParams.class,
                                                                  Consents.class);
        doReturn(method).when(methodParameter).getMethod();

        boolean supports = advice.supports(methodParameter, null, null);

        assertThat(supports).isTrue();
    }

    @Test
    void afterBodyReadGetIbanAndDetailsConsent() {
        String iban1 = "account-iban";
        String iban2 = "balance-iban";
        String iban3 = "trx-iban";

        Consents consents = new Consents();
        consents.setAccess(buildAccess(iban1, iban2, iban3));

        advice.afterBodyRead(consents, null, null, null, null);

        String ibans = MDC.get("iban");
        assertThat(ibans)
            .contains(iban1)
            .contains(iban2)
            .contains(iban3);

        assertThat(MDC.get("consentModel")).isEqualTo("detailed");
    }

    @Test
    void afterBodyReadConsentModelBankOffered() {

        Consents consents = new Consents();
        consents.setAccess(new AccountAccess());
        advice.afterBodyRead(consents, null, null, null, null);

        assertThat(MDC.get("consentModel")).contains("bank-offered");
    }

    @Test
    void afterBodyReadConsentModelGlobal() {

        Consents consents = new Consents();
        AccountAccess access = new AccountAccess();
        access.setAllPsd2(AccountAccess.AllPsd2.ALLACCOUNTS);
        consents.setAccess(access);
        advice.afterBodyRead(consents, null, null, null, null);

        assertThat(MDC.get("consentModel")).contains("global");
    }

    @Test
    void afterBodyReadConsentModelUnknown() {

        advice.afterBodyRead(new Consents(), null, null, null, null);

        assertThat(MDC.get("consentModel")).contains("unknown");
    }

    @Test
    void afterBodyReadDifferentObject() {
        MDC.clear();

        advice.afterBodyRead(new Object(), null, null, null, null);

        assertThat(MDC.get("ibans")).isNull();
        assertThat(MDC.get("consentModel")).isNull();
    }

    private AccountReference buildReference(String iban) {
        AccountReference accounts = new AccountReference();
        accounts.setIban(iban);
        return accounts;
    }

    private AccountAccess buildAccess(String accIban, String blncIban, String trxIban) {
        AccountAccess accountAccess = new AccountAccess();
        accountAccess.setAccounts(Collections.singletonList(buildReference(accIban)));
        accountAccess.setBalances(Collections.singletonList(buildReference(blncIban)));
        accountAccess.setTransactions(Collections.singletonList(buildReference(trxIban)));
        return accountAccess;
    }
}
