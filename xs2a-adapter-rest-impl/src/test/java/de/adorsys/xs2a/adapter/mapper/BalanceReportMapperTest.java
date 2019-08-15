package de.adorsys.xs2a.adapter.mapper;

import de.adorsys.xs2a.adapter.model.AccountReferenceTO;
import de.adorsys.xs2a.adapter.model.BalanceTO;
import de.adorsys.xs2a.adapter.model.ReadAccountBalanceResponse200TO;
import de.adorsys.xs2a.adapter.service.AccountReference;
import de.adorsys.xs2a.adapter.service.account.Balance;
import de.adorsys.xs2a.adapter.service.account.BalanceReport;
import org.junit.Test;
import org.mapstruct.factory.Mappers;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class BalanceReportMapperTest {
    private static final Balance BALANCE = BalanceMapperTest.buildBalance();
    private static final List<Balance> BALANCE_LIST = Collections.singletonList(BALANCE);
    private static final AccountReference ACCOUNT_REFERENCE = AccountReferenceMapperTest.buildAccountReference();

    @Test
    public void toReadAccountBalanceResponse200TO() {
        ReadAccountBalanceResponse200TO balanceResponseTO
                = Mappers.getMapper(BalanceReportMapper.class).toReadAccountBalanceResponse200TO(buildBalanceReport());

        assertThat(balanceResponseTO).isNotNull();

        AccountReferenceTO accountTO = balanceResponseTO.getAccount();
        assertThat(accountTO).isNotNull();
        assertThat(accountTO).isEqualToComparingFieldByField(ACCOUNT_REFERENCE);

        List<BalanceTO> balancesListTO = balanceResponseTO.getBalances();
        assertThat(balancesListTO.size()).isEqualTo(BALANCE_LIST.size());

        BalanceTO balanceTO = balancesListTO.get(0);
        assertThat(balanceTO.getBalanceType().name()).isEqualTo(BALANCE.getBalanceType().name());
        assertThat(balanceTO.getLastChangeDateTime()).isEqualTo(BALANCE.getLastChangeDateTime());
        assertThat(balanceTO.getReferenceDate()).isEqualTo(BALANCE.getReferenceDate());
        assertThat(balanceTO.getLastCommittedTransaction()).isEqualTo(BALANCE.getLastCommittedTransaction());
    }

    private BalanceReport buildBalanceReport() {
        BalanceReport balanceReport = new BalanceReport();
        balanceReport.setAccount(ACCOUNT_REFERENCE);
        balanceReport.setBalances(BALANCE_LIST);
        return balanceReport;
    }
}
