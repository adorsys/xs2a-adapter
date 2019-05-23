package de.adorsys.xs2a.gateway.mapper;

import de.adorsys.xs2a.gateway.model.ais.AmountTO;
import de.adorsys.xs2a.gateway.model.ais.BalanceTO;
import de.adorsys.xs2a.gateway.service.Amount;
import de.adorsys.xs2a.gateway.service.account.Balance;
import de.adorsys.xs2a.gateway.service.account.BalanceType;
import org.junit.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;
import java.time.ZonedDateTime;

import static de.adorsys.xs2a.gateway.mapper.AmountMapperTest.buildAmount;
import static org.assertj.core.api.Assertions.assertThat;

public class BalanceMapperTest {
    private static final Amount AMOUNT = buildAmount();
    private static final BalanceType BALANCE_TYPE = BalanceType.AUTHORISED;
    private static final ZonedDateTime LAST_CHANGE_DATE_TIME = ZonedDateTime.now();
    private static final LocalDate REFERENCE_DATE = LocalDate.now();
    private static final String LAST_COMMITTED_TRANSACTION = "lastCommittedTransaction";

    @Test
    public void toBalanceTO() {
        BalanceTO balanceTO = Mappers.getMapper(BalanceMapper.class).toBalanceTO(buildBalance());

        assertThat(balanceTO).isNotNull();
        assertThat(balanceTO.getBalanceType().name()).isEqualTo(BALANCE_TYPE.name());
        assertThat(balanceTO.getLastChangeDateTime()).isEqualTo(LAST_CHANGE_DATE_TIME);
        assertThat(balanceTO.getReferenceDate()).isEqualTo(REFERENCE_DATE);
        assertThat(balanceTO.getLastCommittedTransaction()).isEqualTo(LAST_COMMITTED_TRANSACTION);

        AmountTO amountTO = balanceTO.getBalanceAmount();
        assertThat(amountTO).isNotNull();
        assertThat(amountTO.getCurrency()).isEqualTo(AMOUNT.getCurrency());
        assertThat(amountTO.getAmount()).isEqualTo(AMOUNT.getAmount());
    }

    static Balance buildBalance() {
        Balance balance = new Balance();

        balance.setBalanceAmount(AMOUNT);
        balance.setBalanceType(BALANCE_TYPE);
        balance.setLastChangeDateTime(LAST_CHANGE_DATE_TIME);
        balance.setReferenceDate(REFERENCE_DATE);
        balance.setLastCommittedTransaction(LAST_COMMITTED_TRANSACTION);

        return balance;
    }
}