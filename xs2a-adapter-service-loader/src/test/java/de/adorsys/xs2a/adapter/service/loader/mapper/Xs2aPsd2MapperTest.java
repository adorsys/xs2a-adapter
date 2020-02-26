package de.adorsys.xs2a.adapter.service.loader.mapper;

import de.adorsys.xs2a.adapter.service.model.*;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;

public class Xs2aPsd2MapperTest {

    private Xs2aPsd2Mapper mapper = Mappers.getMapper(Xs2aPsd2Mapper.class);

    @Test
    public void enumToStringMappingRespectsCase() {
        assertThat(mapper.map(OtpFormat.INTEGER)).isEqualTo("integer");
        assertThat(mapper.map(CashAccountType.CACC)).isEqualTo("Current");
        assertThat(mapper.map(ScaStatus.PSUAUTHENTICATED)).isEqualTo("psuAuthenticated");
        assertThat(mapper.map(BalanceType.CLOSINGBOOKED)).isEqualTo("closingBooked");
        assertThat(mapper.map(AccountStatus.BLOCKED)).isEqualTo("blocked");
        assertThat(mapper.map(ConsentStatus.REVOKEDBYPSU)).isEqualTo("revokedByPsu");
        assertThat(mapper.map(UsageType.PRIV)).isEqualTo("PRIV");
        assertThat(mapper.map(TransactionStatus.RCVD)).isEqualTo("Received");
        assertThat(mapper.map(AccountAccess.AvailableAccountsEnum.ALLACCOUNTS)).isEqualTo("allAccounts");
        assertThat(mapper.map(AccountAccess.AllPsd2Enum.ALLACCOUNTS)).isEqualTo("allAccounts");
        assertThat(mapper.map(MessageErrorCode.CERTIFICATE_MISSING)).isEqualTo("CERTIFICATE_MISSING");
    }
}
