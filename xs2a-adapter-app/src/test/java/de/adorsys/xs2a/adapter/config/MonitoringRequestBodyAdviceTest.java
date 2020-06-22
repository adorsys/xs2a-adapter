package de.adorsys.xs2a.adapter.config;

import de.adorsys.bg.monitoring.client.MonitoringContext;
import de.adorsys.xs2a.adapter.api.model.AccountAccess;
import de.adorsys.xs2a.adapter.api.model.AccountReference;
import de.adorsys.xs2a.adapter.api.model.Consents;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class MonitoringRequestBodyAdviceTest {

    private static final String IBAN = "FR8230066631856742938741993";
    private static final String MASKED_IBAN = "FR82300666******";
    private final Consents consent = createConsents();

    private MonitoringRequestBodyAdvice advice = new MonitoringRequestBodyAdvice();

    @Test
    void afterBodyRead() {
        Consents actual = (Consents) advice.afterBodyRead(consent, null, null, null, null);
        String actualIban = MonitoringContext.getIban();

        assertNotNull(actual);
        assertEquals(IBAN, actual.getAccess().getAccounts().get(0).getIban());
        assertEquals(MASKED_IBAN, actualIban);
    }

    private Consents createConsents() {
        Consents consent = new Consents();
        consent.setAccess(createAccess());

        return consent;
    }

    private AccountAccess createAccess() {
        AccountAccess accountAccess = new AccountAccess();
        accountAccess.setAccounts(Collections.singletonList(createAccountReference()));

        return accountAccess;
    }

    private AccountReference createAccountReference() {
        AccountReference accountReference = new AccountReference();
        accountReference.setIban(IBAN);

        return accountReference;
    }
}
