package de.adorsys.xs2a.gateway.mapper;

import de.adorsys.xs2a.gateway.model.AccountListTO;
import de.adorsys.xs2a.gateway.service.account.AccountListHolder;
import org.mapstruct.Mapper;

@Mapper(uses = {AccountDetailsMapper.class})
public interface AccountListHolderMapper {

    AccountListTO toAccountListTO(AccountListHolder accountListHolder);
}
