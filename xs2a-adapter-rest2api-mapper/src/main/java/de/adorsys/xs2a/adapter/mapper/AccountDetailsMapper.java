package de.adorsys.xs2a.adapter.mapper;

import de.adorsys.xs2a.adapter.model.AccountDetailsTO;
import de.adorsys.xs2a.adapter.service.model.AccountDetails;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = BalanceMapper.class)
public interface AccountDetailsMapper {

    @Mapping(source = "usageType", target = "usage")
    AccountDetailsTO toAccountDetailsTO(AccountDetails accountDetails);

    @InheritInverseConfiguration
    AccountDetails toAccountDetails(AccountDetailsTO to);
}
