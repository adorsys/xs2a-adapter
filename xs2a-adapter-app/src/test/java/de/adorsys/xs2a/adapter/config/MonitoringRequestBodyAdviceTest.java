package de.adorsys.xs2a.adapter.config;

import de.adorsys.bg.monitoring.client.MonitoringContext;
import de.adorsys.xs2a.adapter.model.AccountAccessTO;
import de.adorsys.xs2a.adapter.model.AccountReferenceTO;
import de.adorsys.xs2a.adapter.model.ConsentsTO;
import org.junit.jupiter.api.Test;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class MonitoringRequestBodyAdviceTest {

    private static final String IBAN = "FR8230066631856742938741993";
    private static final String MASKED_IBAN = "FR82300666******";
    private final ConsentsTO consent = createConsentsTO();

    private MonitoringRequestBodyAdvice advice = new MonitoringRequestBodyAdvice();

    @Test
    void afterBodyRead() {
        ConsentsTO actual = (ConsentsTO) advice.afterBodyRead(consent, null, null, null, null);
        String actualIban = MonitoringContext.getIban();

        assertNotNull(actual);
        assertEquals(IBAN, actual.getAccess().getAccounts().get(0).getIban());
        assertEquals(MASKED_IBAN, actualIban);
    }

    private ConsentsTO createConsentsTO() {
        ConsentsTO consent = new ConsentsTO();
        consent.setAccess(createAccessTO());

        return consent;
    }

    private AccountAccessTO createAccessTO() {
        AccountAccessTO accountAccess = new AccountAccessTO();
        accountAccess.setAccounts(Collections.singletonList(createAccountReferenceTO()));

        return accountAccess;
    }

    private AccountReferenceTO createAccountReferenceTO() {
        AccountReferenceTO accountReference = new AccountReferenceTO();
        accountReference.setIban(IBAN);

        return accountReference;
    }
}
