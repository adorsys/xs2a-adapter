package de.adorsys.xs2a.adapter.service.impl.mapper;

import de.adorsys.xs2a.adapter.service.ais.AccountAccess;
import de.adorsys.xs2a.adapter.service.ais.ConsentInformation;
import de.adorsys.xs2a.adapter.service.ais.ConsentStatus;
import de.adorsys.xs2a.adapter.service.impl.model.DeutscheBankConsentInformation;
import de.adorsys.xs2a.adapter.service.model.Link;
import org.junit.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class DeutscheBankConsentInformationMapperTest {
    private static final AccountAccess ACCOUNT_ACCESS = new AccountAccess();
    private static final LocalDate VALID_UNTIL = LocalDate.of(2121, 12, 12);
    private static final int FREQUENCY_PER_DAY = 10;
    private static final LocalDate LAST_ACTION_DATE = LocalDate.of(2021, 10, 10);
    private static final Map<String, Link> LINKS = new HashMap<>();

    private DeutscheBankConsentInformationMapper deutscheBankConsentInformationMapper =
            Mappers.getMapper(DeutscheBankConsentInformationMapper.class);

    @Test
    public void toConsentInformation() {
        ConsentInformation consentInformation =
                deutscheBankConsentInformationMapper.toConsentInformation(deutscheBankConsentInformation());

        assertThat(consentInformation.getAccess()).isEqualTo(ACCOUNT_ACCESS);
        assertThat(consentInformation.getValidUntil()).isEqualTo(VALID_UNTIL);
        assertThat(consentInformation.getFrequencyPerDay()).isEqualTo(FREQUENCY_PER_DAY);
        assertThat(consentInformation.getLastActionDate()).isEqualTo(LAST_ACTION_DATE);
        assertThat(consentInformation.getConsentStatus()).isEqualTo(ConsentStatus.RECEIVED);
        assertThat(consentInformation.getRecurringIndicator()).isTrue();
        assertThat(consentInformation.getLinks()).isEqualTo(LINKS);
    }

    private DeutscheBankConsentInformation deutscheBankConsentInformation() {
        DeutscheBankConsentInformation deutscheBankConsentInformation = new DeutscheBankConsentInformation();
        deutscheBankConsentInformation.setAccess(ACCOUNT_ACCESS);
        deutscheBankConsentInformation.setValidUntil(VALID_UNTIL);
        deutscheBankConsentInformation.setFrequencyPerDay(FREQUENCY_PER_DAY);
        deutscheBankConsentInformation.setLastActionDate(LAST_ACTION_DATE);
        deutscheBankConsentInformation.setConsentStatus(ConsentStatus.RECEIVED);
        deutscheBankConsentInformation.setRecurringIndicator(true);
        deutscheBankConsentInformation.setLinks(LINKS);
        return deutscheBankConsentInformation;
    }
}
