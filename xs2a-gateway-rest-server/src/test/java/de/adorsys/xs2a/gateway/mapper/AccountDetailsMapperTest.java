package de.adorsys.xs2a.gateway.mapper;

import de.adorsys.xs2a.gateway.model.ais.AccountDetailsTO;
import de.adorsys.xs2a.gateway.model.ais.BalanceList;
import de.adorsys.xs2a.gateway.service.account.*;
import org.junit.Test;
import org.mapstruct.factory.Mappers;

import java.util.Collections;
import java.util.List;

import static de.adorsys.xs2a.gateway.mapper.BalanceMapperTest.buildBalance;
import static org.assertj.core.api.Assertions.assertThat;

public class AccountDetailsMapperTest {
    private static final String ASPSP_ACCOUNT_ID = "aspspAccountId";
    private static final String RESOURCE_ID = "resourceId";
    private static final String IBAN = "iban";
    private static final String BBAN = "bban";
    private static final String PAN = "pan";
    private static final String MASKED_PAN = "maskedPan";
    private static final String MSISDN = "msisdn";
    private static final String CURRENCY = "EUR";
    private static final String NAME = "name";
    private static final String PRODUCT = "product";
    private static final CashAccountType CASH_ACCOUNT_TYPE = CashAccountType.CASH;
    private static final AccountStatus STATUS = AccountStatus.ENABLED;
    private static final String BIC = "bic";
    private static final String LINKED_ACCOUNTS = "linkedAccounts";
    private static final UsageType USAGE_TYPE = UsageType.PRIV;
    private static final String DETAILS = "details";
    private static final List<Balance> BALANCE_LIST = Collections.singletonList(buildBalance());

    @Test
    public void toAccountDetailsTO() {
        AccountDetailsTO accountDetailsTO = Mappers.getMapper(AccountDetailsMapper.class).toAccountDetailsTO(buildAccountDetails());

        assertThat(accountDetailsTO).isNotNull();
        assertThat(accountDetailsTO.getResourceId()).isEqualTo(RESOURCE_ID);
        assertThat(accountDetailsTO.getIban()).isEqualTo(IBAN);
        assertThat(accountDetailsTO.getBban()).isEqualTo(BBAN);
        assertThat(accountDetailsTO.getMsisdn()).isEqualTo(MSISDN);
        assertThat(accountDetailsTO.getCurrency()).isEqualTo(CURRENCY);
        assertThat(accountDetailsTO.getName()).isEqualTo(NAME);
        assertThat(accountDetailsTO.getProduct()).isEqualTo(PRODUCT);
        assertThat(accountDetailsTO.getStatus().name()).isEqualTo(STATUS.name());
        assertThat(accountDetailsTO.getBic()).isEqualTo(BIC);
        assertThat(accountDetailsTO.getLinkedAccounts()).isEqualTo(LINKED_ACCOUNTS);
        assertThat(accountDetailsTO.getUsage().name()).isEqualTo(USAGE_TYPE.name());
        assertThat(accountDetailsTO.getDetails()).isEqualTo(DETAILS);

        BalanceList balanceList = accountDetailsTO.getBalances();
        assertThat(balanceList).isNotNull();
        assertThat(balanceList.size()).isEqualTo(BALANCE_LIST.size());
    }

    static AccountDetails buildAccountDetails() {
        AccountDetails accountDetails = new AccountDetails();

        accountDetails.setAspspAccountId(ASPSP_ACCOUNT_ID);
        accountDetails.setResourceId(RESOURCE_ID);
        accountDetails.setIban(IBAN);
        accountDetails.setBban(BBAN);
        accountDetails.setPan(PAN);
        accountDetails.setMaskedPan(MASKED_PAN);
        accountDetails.setMsisdn(MSISDN);
        accountDetails.setCurrency(CURRENCY);
        accountDetails.setName(NAME);
        accountDetails.setProduct(PRODUCT);
        accountDetails.setCashAccountType(CASH_ACCOUNT_TYPE);
        accountDetails.setStatus(STATUS);
        accountDetails.setBic(BIC);
        accountDetails.setLinkedAccounts(LINKED_ACCOUNTS);
        accountDetails.setUsageType(USAGE_TYPE);
        accountDetails.setDetails(DETAILS);
        accountDetails.setBalances(BALANCE_LIST);

        return accountDetails;
    }
}