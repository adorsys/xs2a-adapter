package de.adorsys.xs2a.gateway.mapper;

import de.adorsys.xs2a.gateway.model.ais.ConsentsTO;
import de.adorsys.xs2a.gateway.service.ais.Consents;
import org.junit.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class ConsentMapperTest {

    private static final LocalDate VALID_UNTIL = LocalDate.now();
    private static final boolean COMBINED_SERVICE_INDICATOR = true;
    private static final int FREQUENCY_PER_DAY = 4;
    private static final Boolean RECURRING_INDICATOR = Boolean.FALSE;
    private ConsentMapper mapper = Mappers.getMapper(ConsentMapper.class);

    @Test
    public void toConsents() {
        Consents consents = mapper.toConsents(buildConsent());

        assertThat(consents.getFrequencyPerDay()).isEqualTo(FREQUENCY_PER_DAY);
        assertThat(consents.getRecurringIndicator()).isEqualTo(RECURRING_INDICATOR);
        assertThat(consents.getValidUntil()).isEqualTo(VALID_UNTIL);
        assertThat(consents.getCombinedServiceIndicator()).isEqualTo(COMBINED_SERVICE_INDICATOR);
        assertThat(consents.getAccess()).isNotNull();
    }

    private ConsentsTO buildConsent() {
        ConsentsTO consents = new ConsentsTO();
        consents.setValidUntil(VALID_UNTIL);
        consents.setCombinedServiceIndicator(COMBINED_SERVICE_INDICATOR);
        consents.setFrequencyPerDay(FREQUENCY_PER_DAY);
        consents.setRecurringIndicator(RECURRING_INDICATOR);
        consents.setAccess(AccountAccessMapperTest.buildAccountAccess());
        return consents;
    }
}