package de.adorsys.xs2a.adapter.mapper;

import de.adorsys.xs2a.adapter.model.AccountAccessTO;
import de.adorsys.xs2a.adapter.service.model.AccountAccess;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.Collections;

import static de.adorsys.xs2a.adapter.mapper.AccountReferenceMapperTest.buildAccountReferenceTO;
import static org.assertj.core.api.Assertions.assertThat;

public class AccountAccessMapperTest {

    @Test
    public void toAccountAccess() {
        AccountAccessMapper mapper = Mappers.getMapper(AccountAccessMapper.class);
        AccountAccess access = mapper.toAccountAccess(buildAccountAccess());
        assertThat(access.getAllPsd2().name()).isEqualTo(AccountAccess.AllPsd2Enum.ALLACCOUNTS.name());
        assertThat(access.getAvailableAccounts().name()).isEqualTo(AccountAccess.AvailableAccountsWithBalance.ALLACCOUNTS.name());
        assertThat(access.getAccounts()).hasSize(1);
        assertThat(access.getBalances()).hasSize(1);
        assertThat(access.getTransactions()).hasSize(1);
    }

    static AccountAccessTO buildAccountAccess() {
        AccountAccessTO access = new AccountAccessTO();
        access.setAllPsd2(AccountAccessTO.AllPsd2TO.ALLACCOUNTS);
        access.setAvailableAccounts(AccountAccessTO.AvailableAccountsTO.ALLACCOUNTS);
        access.setAccounts(Collections.singletonList(buildAccountReferenceTO()));
        access.setBalances(Collections.singletonList(buildAccountReferenceTO()));
        access.setTransactions(Collections.singletonList(buildAccountReferenceTO()));
        return access;
    }
}
