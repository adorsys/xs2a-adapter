package de.adorsys.xs2a.gateway.mapper;

import de.adorsys.xs2a.gateway.model.ais.AccountAccessTO;
import de.adorsys.xs2a.gateway.service.consent.AccountAccess;
import org.junit.Test;
import org.mapstruct.factory.Mappers;

import java.util.Collections;

import static de.adorsys.xs2a.gateway.mapper.AccountReferenceMapperTest.buildAccountReference;
import static org.assertj.core.api.Assertions.assertThat;

public class AccountAccessMapperTest {

    @Test
    public void toAccountAccess() {
        AccountAccessMapper mapper = Mappers.getMapper(AccountAccessMapper.class);
        AccountAccess access = mapper.toAccountAccess(buildAccountAccess());
        assertThat(access.getAllPsd2().name()).isEqualTo(AccountAccess.AllPsd2Enum.ALLACCOUNTS.name());
        assertThat(access.getAvailableAccounts().name()).isEqualTo(AccountAccess.AvailableAccountsEnum.ALLACCOUNTSWITHBALANCES.name());
        assertThat(access.getAccounts()).hasSize(1);
        assertThat(access.getBalances()).hasSize(1);
        assertThat(access.getTransactions()).hasSize(1);
    }

    static AccountAccessTO buildAccountAccess() {
        AccountAccessTO access = new AccountAccessTO();
        access.setAllPsd2(AccountAccessTO.AllPsd2Enum.ALLACCOUNTS);
        access.setAvailableAccounts(AccountAccessTO.AvailableAccountsEnum.ALLACCOUNTSWITHBALANCES);
        access.setAccounts(Collections.singletonList(buildAccountReference()));
        access.setBalances(Collections.singletonList(buildAccountReference()));
        access.setTransactions(Collections.singletonList(buildAccountReference()));
        return access;
    }
}