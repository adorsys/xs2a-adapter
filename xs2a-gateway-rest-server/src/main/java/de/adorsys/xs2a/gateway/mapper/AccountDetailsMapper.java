package de.adorsys.xs2a.gateway.mapper;

import de.adorsys.xs2a.gateway.model.ais.AccountDetailsTO;
import de.adorsys.xs2a.gateway.service.account.AccountDetails;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(uses = BalanceMapper.class)
public interface AccountDetailsMapper {

    @Mappings({
            @Mapping(source = "usageType", target = "usage")
    })
    AccountDetailsTO toAccountDetailsTO(AccountDetails accountDetails);
}
