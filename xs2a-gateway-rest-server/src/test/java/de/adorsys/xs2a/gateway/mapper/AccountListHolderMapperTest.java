package de.adorsys.xs2a.gateway.mapper;

import de.adorsys.xs2a.gateway.model.ais.AccountDetailsTO;
import de.adorsys.xs2a.gateway.model.ais.AccountListTO;
import de.adorsys.xs2a.gateway.model.ais.BalanceList;
import de.adorsys.xs2a.gateway.service.account.AccountDetails;
import de.adorsys.xs2a.gateway.service.account.AccountListHolder;
import org.junit.Test;
import org.mapstruct.factory.Mappers;

import java.util.Collections;
import java.util.List;

import static de.adorsys.xs2a.gateway.mapper.AccountDetailsMapperTest.buildAccountDetails;
import static org.assertj.core.api.Assertions.assertThat;

public class AccountListHolderMapperTest {
    private static final AccountDetails ACCOUNT_DETAILS = buildAccountDetails();

    @Test
    public void toAccountListTO() {
        AccountListTO accountListTO = Mappers.getMapper(AccountListHolderMapper.class).toAccountListTO(buildAccountListHolder());

        assertThat(accountListTO).isNotNull();

        List<AccountDetailsTO> accounts = accountListTO.getAccounts();
        assertThat(accounts).isNotNull();

        AccountDetailsTO accountDetailsTO = accounts.get(0);
        assertThat(accountDetailsTO).isNotNull();
        assertThat(accountDetailsTO.getResourceId()).isEqualTo(ACCOUNT_DETAILS.getResourceId());
        assertThat(accountDetailsTO.getIban()).isEqualTo(ACCOUNT_DETAILS.getIban());
        assertThat(accountDetailsTO.getBban()).isEqualTo(ACCOUNT_DETAILS.getBban());
        assertThat(accountDetailsTO.getMsisdn()).isEqualTo(ACCOUNT_DETAILS.getMsisdn());
        assertThat(accountDetailsTO.getCurrency()).isEqualTo(ACCOUNT_DETAILS.getCurrency());
        assertThat(accountDetailsTO.getName()).isEqualTo(ACCOUNT_DETAILS.getName());
        assertThat(accountDetailsTO.getProduct()).isEqualTo(ACCOUNT_DETAILS.getProduct());
        assertThat(accountDetailsTO.getStatus().name()).isEqualTo(ACCOUNT_DETAILS.getStatus().name());
        assertThat(accountDetailsTO.getBic()).isEqualTo(ACCOUNT_DETAILS.getBic());
        assertThat(accountDetailsTO.getLinkedAccounts()).isEqualTo(ACCOUNT_DETAILS.getLinkedAccounts());
        assertThat(accountDetailsTO.getUsage().name()).isEqualTo(ACCOUNT_DETAILS.getUsageType().name());
        assertThat(accountDetailsTO.getDetails()).isEqualTo(ACCOUNT_DETAILS.getDetails());

        BalanceList balanceList = accountDetailsTO.getBalances();
        assertThat(balanceList).isNotNull();
        assertThat(balanceList.size()).isEqualTo(ACCOUNT_DETAILS.getBalances().size());
    }

    static AccountListHolder buildAccountListHolder() {
        AccountListHolder accountListHolder = new AccountListHolder();

        List<AccountDetails> accountDetailsList = Collections.singletonList(ACCOUNT_DETAILS);
        accountListHolder.setAccounts(accountDetailsList);

        return accountListHolder;
    }
}